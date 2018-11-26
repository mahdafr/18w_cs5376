package apiaryparty;

import java.util.Random;

/**
 * Example attacker agent.
 * IMPORTANT NOTE: 	Your attacker object will be recreated for every action. Because of this,
 * 					model your Attacker to only make a decision on the current information. Do
 * 					not try to use variables that will carry on in to the next makeSingleAction()
 * 
 * Make use of the three protected variables inherited from Attacker. These variables include:
 * protected ArrayList&lt;Node&gt; capturedNodes - a list of the already captured nodes
 * protected ArrayList&lt;Node&gt; availableNodes - a list of the available nodes for attacking and probing.
 * protected int budget - the current budget of the Attacker. Be careful that your next move will not cost more than your budget.
 * 
 * @author Porag - updated by Oscar
 */
public class Beedrill extends Attacker {

    private final static String attackerName = "Beedrill";
    
    public Random r;

    /**
     * Constructor
     * @param defenderName defender's name
     * @param graphFile graph to read
     */
	public Beedrill(String defenderName, String graphFile) {
		super(attackerName, defenderName, graphFile);
	}
	/**
	 * Default constructor do not change
	 */
	public Beedrill(){
		super(attackerName);
	}
	
	/**
	 * If you need to initialize anything, do it  here
	 */
	protected void initialize(){
		r = new Random();
	}

	/**
	 * This is called by the game master while your agent still has funds. You return an action.
	 * @return your action
	 */
	public AttackerAction makeAction() {
		AttackerActionType type;		
		int nodeID = -1;
		
		for(Node x: availableNodes)
		{
			if (x.getSv() == -1)
			{
				nodeID = x.getNodeID();
				type = AttackerActionType.PROBE_POINTS;
				return new AttackerAction(type, nodeID);
			}			
		}
		int nodeIDmaxSV = -1;
		int nodeIDminmaxSV = -1;
		int maxSV = Integer.MIN_VALUE;
		int minmaxSV = Integer.MAX_VALUE;
		for(Node x: availableNodes)
		{
			if (x.getSv() <=10 && (maxSV<x.getSv()))
			{
				maxSV = x.getSv();
				nodeIDmaxSV = x.getNodeID();
			}
			else if(x.getSv()>10 &&  x.getSv()<minmaxSV)
			{
				nodeIDminmaxSV = x.getNodeID();
				minmaxSV = x.getSv();
			}
		}
		
		type = AttackerActionType.ATTACK;
		if(nodeIDmaxSV != -1)
			return new AttackerAction(type, nodeIDmaxSV);
		else
			return new AttackerAction(type, nodeIDminmaxSV);
	}

	/**
	 * The game master is giving you the result of the action.
	 * @param  lastNode the node successfully attacked
	 */
	protected void result(Node lastNode) {
		
	}
}
