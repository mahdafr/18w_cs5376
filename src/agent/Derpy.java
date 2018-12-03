package agent;

/**
 * @author mahdafr
 * @created Dec01 S
 * @modified Dec01 S
 *
 * Defender agent Derpy executes Order 66.
 */

import apiaryparty.*;

public class Derpy extends Defender{
    public Derpy(String graphFile)
    {
        super("Derpy",graphFile);
    }
    private java.util.ArrayDeque<DefenderAction> actionList;

    @Override
    public void initialize() {
        actionList = new java.util.ArrayDeque();
        int cost = 0;

        for ( Node n : net.getAvailableNodes() )
            if ( n.getSv()==0 ) {
                int i = 0;
                cost += Parameters.HONEYPOT_RATE;
                cost += Parameters.FIREWALL_RATE*(n.getNeighborList().size()-1);
            }

        if ( cost<=getBudget() )
            order66();
        //if honeypotting costs little, execute Order66
    }

    @Override
    public void actionResult(boolean actionSuccess) {}

    @Override
    public DefenderAction makeAction() {
        if ( !actionList.isEmpty() )
            return actionList.removeFirst(); //dequeue
        return new DefenderAction(DefenderActionType.INVALID);
    }

    private void order66() {
        for ( Node n : net.getAvailableNodes() ) {
            if ( n.getSv()==0 ) {
                int i = 0;
                for ( Node ney : n.getNeighborList()) {
                    if ( i==0)
                        actionList.addLast(new DefenderAction(DefenderActionType.HONEYPOT,n.getNodeID()));
                    else
                        actionList.addLast(new DefenderAction(DefenderActionType.FIREWALL,n.getNodeID()));
                    i++;
                }
            }
        }
    }
}
