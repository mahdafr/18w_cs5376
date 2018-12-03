package agent;

import apiaryparty.*;

import java.util.ArrayList;

public class ExMachina extends Attacker {

	private final static String attackerName = "ExMachina";

	// Arrays with nodeID as index and ExVal as value
	ArrayList<Node> avNodes;
	ArrayList<Double> attack, superattack;
	Double exVal, maxExVal;

	/**
	 * Constructor
	 * @param defenderName defender's name
	 * @param graphFile graph to read
	 */
	public ExMachina(String defenderName, String graphFile) {
		super(attackerName, defenderName, graphFile);
	}

	public ExMachina(){
		super(attackerName);
	}

	/**
	 * If you need to initialize anything, do it  here
	 */
	protected void initialize() {

	}

	@Override
	public AttackerAction makeAction() {

		maxExVal = 0.0;
		avNodes = this.net.getAvailableNodes();
		AttackerAction action = new AttackerAction(AttackerActionType.ATTACK, avNodes.get(0).getNodeID());

		for (Node an : avNodes) {
			exVal = an.getSv() * (1 - (an.getSv() / (double) Parameters.ATTACK_ROLL));
			exVal = exVal < 0 ? 0 : exVal;

			if (exVal > maxExVal) {
				maxExVal = exVal;
				action.move = AttackerActionType.ATTACK;
				action.nodeID = an.getNodeID();
			}

			exVal = an.getSv() * (1 - (an.getSv() / (double) Parameters.SUPERATTACK_ROLL));
			exVal = exVal < 0 ? 0 : exVal;

			if (exVal > maxExVal) {
				maxExVal = exVal;
				action.move = AttackerActionType.SUPERATTACK;
				action.nodeID = an.getNodeID();
			}
		}
		System.out.format("Action: %s\tNodeID: %d\tExVal: %f\n", action.move, action.nodeID, maxExVal);
		return action;
	}

	@Override
	protected void result(Node lastNode) {
		// TODO Auto-generated method stub
	}
}
