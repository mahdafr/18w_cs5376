package agent;

/**
 * @author jimmyjoe
 * @created Dec01 S
 * @modified Dec01 S by mahdafr
 *
 * Dummy agent Alphalpha plans moves based on the list of nodes to attack.
 */
import apiaryparty.*;

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
	protected void initialize(){ }

	@Override
	public AttackerAction makeAction() {
	    //if there are no nodes, return Invalid
        if (availableNodes.isEmpty())
            return new AttackerAction(AttackerActionType.INVALID, 0);

        java.util.ArrayList<Node> cleanNodes = new java.util.ArrayList<>();

        //check if any node is a honeypot
        for (Node n : availableNodes) {
            if (n.getHoneyPot() != 1)
                cleanNodes.add(n);
        }
        if (cleanNodes.isEmpty())
            return new AttackerAction(AttackerActionType.INVALID, 0);

        //find minimum SV in the list of nodes to attack
        Node min = cleanNodes.get(0);
        for (Node n : cleanNodes) {
            if (n.getSv() < min.getSv())
                min = n;
        }

        //determine Attacker action
        if (min.getHoneyPot() == -1)
            return new AttackerAction(AttackerActionType.PROBE_HONEYPOT, min.getNodeID());
        else if ( min.getSv() < 15 )
            return new AttackerAction(AttackerActionType.ATTACK, min.getNodeID());
        else
            return new AttackerAction(AttackerActionType.SUPERATTACK, min.getNodeID());
    }

	@Override
	protected void result(Node lastNode) {
		// TODO Auto-generated method stub
	}
}
