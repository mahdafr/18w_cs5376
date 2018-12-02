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
	    java.util.ArrayList<Node> cleanNodes = new java.util.ArrayList<>();

        for(Node n: net.getAvailableNodes()) {
            if(!n.isHoneyPot())
                cleanNodes.add(n);
        }

        if(cleanNodes.isEmpty())
            return new AttackerAction(AttackerActionType.INVALID, 0);

        Node min = cleanNodes.get(0);

        for(Node cn: cleanNodes) {
            if(cn.getSv() < min.getSv())
                min = cn;
        }

        if( min.getHoneyPot() == -1 )
            return new AttackerAction(AttackerActionType.PROBE_HONEYPOT, min.getNodeID());

        if( min.getSv() > 16 ) {
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
