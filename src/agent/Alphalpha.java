package agent;

/**
 * @author mahdafr
 * @modified on Dec02 U by jimmyjoe
 *
 * Alphalpha is the champion Attacker agent that implements different strategies of the following attacker agents:
 *    Aardvark, ExMachina, and Inquirer.
 */

import apiaryparty.*;
import java.util.ArrayList;

public class Alphalpha extends Attacker {
    private final static String attackerName = "Alphalpha";

	boolean honey;

    /**
     * Constructor
     * @param defenderName defender's name
     * @param graphFile graph to read
     */
	public Alphalpha(String defenderName, String graphFile) {
		super(attackerName, defenderName, graphFile);
	}
	
	public Alphalpha(){
		super(attackerName);
	}
	
	/**
	 * If you need to initialize anything, do it  here
	 */
	protected void initialize() {
//		System.out.format("%s nodes in graph: %d\nParameter nodes in graph: %d\n",
//				this.getName(), this.net.getNodes().length, Parameters.NUMBER_OF_NODES);
		honey = net.getNodes().length > Parameters.NUMBER_OF_NODES;
	}

	@Override
	public AttackerAction makeAction() {
		//checks whether the strategy should be to probe for a honeypot or maximize expected value
		if (honey)
			return safeAttack();
		else
			return blindExVal();

    }

    // Minimizes regret by always probing for honeypot before attacking
	// Attacks node with lowest sv
    private AttackerAction safeAttack() {
	    ArrayList<Node> cleanNodes = new java.util.ArrayList<>();

	    //for every node, check if the node is a honeypot
	    for(Node n: net.getAvailableNodes()) {
		    if(!n.isHoneyPot())
			    cleanNodes.add(n);
	    }

	    //found purely honeypot nodes, so don't do anything
	    if(cleanNodes.isEmpty()) {
//		    System.out.println("All nodes are honeypots");
		    return new AttackerAction(AttackerActionType.INVALID, 0);
	    }

	    Node min = cleanNodes.get(0);

	    //found some nodes that are not honeypots, so find the smallest security value 
	    for(Node cn: cleanNodes) {
		    if(cn.getSv() < min.getSv())
			    min = cn;
	    }

	    //probe for a honeypot
	    if(min.getHoneyPot() == -1)
		    return new AttackerAction(AttackerActionType.PROBE_HONEYPOT, min.getNodeID());

	    //depending on the security value and budget, play a: Superattack, Attack, or EndTurn
	    if(min.getSv() > 15) {
		    if(this.budget >= Parameters.SUPERATTACK_RATE) {
			    return new AttackerAction(AttackerActionType.SUPERATTACK, min.getNodeID());
		    }
		    else if(this.budget >= Parameters.ATTACK_RATE) {
			    return new AttackerAction(AttackerActionType.ATTACK, min.getNodeID());
		    }
		    else return new AttackerAction(AttackerActionType.END_TURN, min.getNodeID());
	    }
	    else {
		    if(this.budget >= Parameters.ATTACK_RATE) {
			    return new AttackerAction(AttackerActionType.ATTACK, min.getNodeID());
		    }
		    else return new AttackerAction(AttackerActionType.END_TURN, min.getNodeID());
	    }
    }

    // Maximizes expected value by comparing all possible (attack) moves and choosing best
	// Blind refers to never probing, assumes sv is good predictor for pv
    private AttackerAction blindExVal() {
		double exVal = 0.0;
	    double maxExVal = 0.0;
	    ArrayList<Node> avNodes = this.net.getAvailableNodes();
	    AttackerAction action = new AttackerAction(AttackerActionType.ATTACK, avNodes.get(0).getNodeID());

	    //for all the nodes, calculate the expected value
	    for (Node an : avNodes) {
		    //E(attacking)
		    exVal = an.getSv() * (1 - (an.getSv() / (double) Parameters.ATTACK_ROLL));
		    exVal = exVal < 0 ? 0 : exVal;

		    //keep track of the maximum expected value: attacking
		    if (exVal > maxExVal) {
			    maxExVal = exVal;
			    action.move = AttackerActionType.ATTACK;
			    action.nodeID = an.getNodeID();
		    }

		    //E(superattacking)
		    exVal = an.getSv() * (1 - (an.getSv() / (double) Parameters.SUPERATTACK_ROLL));
		    exVal = exVal < 0 ? 0 : exVal;

		    //keep track of the maximum expected value: superattacking
		    if (exVal > maxExVal) {
			    maxExVal = exVal;
			    action.move = AttackerActionType.SUPERATTACK;
			    action.nodeID = an.getNodeID();
		    }
	    }
//	    System.out.format("Action: %s\tNodeID: %d\tExVal: %f\n", action.move, action.nodeID, maxExVal);
	    return action;
    }

	@Override
	protected void result(Node lastNode) {
		// TODO Auto-generated method stub
	}
}
