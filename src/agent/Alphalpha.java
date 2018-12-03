package agent;


import apiaryparty.*;
import java.util.ArrayList;

public class Alphalpha extends Attacker {
    private final static String attackerName = "Alphalpha";

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
		System.out.format("%s nodes in graph: %d\nParameter nodes in graph: %d\n",
				this.getName(), this.net.getNodes().length, Parameters.NUMBER_OF_NODES);
	}

	@Override
	public AttackerAction makeAction() {

	    ArrayList<Node> cleanNodes = new java.util.ArrayList<>();

        for(Node n: net.getAvailableNodes()) {
            if(!n.isHoneyPot())
                cleanNodes.add(n);
        }

        if(cleanNodes.isEmpty()) {
        	System.out.println("All nodes are honeypots wtf");
	        return new AttackerAction(AttackerActionType.INVALID, 0);
        }


        Node min = cleanNodes.get(0);

        for(Node cn: cleanNodes) {
            if(cn.getSv() < min.getSv())
                min = cn;
        }

        if(min.getHoneyPot() == -1)
            return new AttackerAction(AttackerActionType.PROBE_HONEYPOT, min.getNodeID());

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

	@Override
	protected void result(Node lastNode) {
		// TODO Auto-generated method stub
	}
}
