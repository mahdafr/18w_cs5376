package agent;

/**
 * @author mahdafr
 * @created Nov21 W
 * @modified Nov22 R
 *
 * Dummy agent SAD chooses to add honeypots, strengthen, or do nothing depending its previous action.
 */

import apiaryparty.*;

public class Dummy extends Defender{

    public Dummy(String graphFile)
    {
        super("SAD",graphFile);
    }

    @Override
    public void initialize() {}

    @Override
    public void actionResult(boolean actionSuccess) {}

    @Override
    public DefenderAction makeAction() {
        java.util.Random r = new java.util.Random();
        switch ( getLastAction().getType() ) {
            case STRENGTHEN:
                //previously strengthened so we add a honeypot
                return honeypot(r);
            case HONEYPOT:
                //previously added a honeypot so we strengthen
                return strengthen(r);
            default:
                //did nothing, so this is first action, randomly decide to:
                if ( r.nextInt(100000)%2==0 )
                    return strengthen(r);
                return honeypot(r);
        }
    }

    /**
     *
     * @param r is the Random generator
     * @return the strengthened node with a randomly-generated value
     */
    private DefenderAction honeypot(java.util.Random r) {
        int honeyNode = r.nextInt(net.getAvailableNodes().size());
        int honeypotCost = honeypotCost(honeyNode);
        if ( getBudget()<honeypotCost ) //not enough money
            return new DefenderAction(DefenderActionType.INVALID);
        return new DefenderAction(DefenderActionType.HONEYPOT, honeyNode);
    }

    /**
     *
     * @param r is the Random generator
     * @return the added honeypot with a randomly-generated value
     */
    private DefenderAction strengthen(java.util.Random r) {
        if( getBudget()<Parameters.STRENGTHEN_RATE )
            return new DefenderAction(false);
        int tries = 0;
        int node = r.nextInt(net.getSize());
        while( !isValidStrengthen(node) && tries++<10 )
            node = r.nextInt(net.getSize());
        return new DefenderAction(DefenderActionType.STRENGTHEN, node);
    }
}
