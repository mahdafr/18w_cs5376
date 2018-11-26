package agent;

/**
 * @author mahdafr
 * @created Nov21 W
 * @modified Nov22 R
 *
 * Alphalpha agent DAS chooses to attack honeypots, strengthen, or do nothing depending its previous action.
 */

import apiaryparty.*;

public class Alphalpha extends Attacker {
    private final static String attackerName = "DAS";

    public Alphalpha(String defenderName, String graphFile) {
        super(attackerName, defenderName, graphFile);
    }

    public Alphalpha(){
        super(attackerName);
    }

    protected void initialize() {}

    public AttackerAction makeAction() {
        AttackerActionType type;
        int nodeID = -1;

        for( Node x: availableNodes ) {
            if ( x.getSv()==-1 ) {
                nodeID = x.getNodeID();
                type = AttackerActionType.PROBE_POINTS;
                return new AttackerAction(type, nodeID);
            }
        }
        int nodeIDmaxSV = -1;
        int nodeIDminmaxSV = -1;
        int maxSV = Integer.MIN_VALUE;
        int minmaxSV = Integer.MAX_VALUE;
        for( Node x: availableNodes ) {
            if ( (x.getSv()<=10) && (maxSV<x.getSv()) ) {
                maxSV = x.getSv();
                nodeIDmaxSV = x.getNodeID();
            } else if ( x.getSv()>10 && x.getSv()<minmaxSV ) {
                nodeIDminmaxSV = x.getNodeID();
                minmaxSV = x.getSv();
            }
        }

        type = AttackerActionType.ATTACK;
        if( nodeIDmaxSV!=-1 )
            return new AttackerAction(type, nodeIDmaxSV);
        return new AttackerAction(type, nodeIDminmaxSV);
    }

    /**
     * The game master is giving you the result of the action.
     * @param lastNode the node successfully attacked
     */
    protected void result(Node lastNode) {

    }
}
