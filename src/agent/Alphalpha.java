package apiaryparty;

public class Alphalpha extends Attacker {

    private final static String attackerName = "Alphalpha";

    /**
     * Constructor
     * @param defenderName defender's name
     * @param graphFile graph to read
     */
	public GreenHornet(String defenderName, String graphFile) {
		super(attackerName, defenderName, graphFile);
	}
	
	public Alphalpha(){
		super(attackerName);
	}
	
	/**
	 * If you need to initialize anything, do it  here
	 */
	protected void initialize(){
	}


	@Override
	public AttackerAction makeAction() {

	    if(availableNodes.isEmpty())
		return new AttackerAction(AttackerActionType.INVALID, 0);

	    ArrayList<Node> cleanNodes = new ArrayList<>();
	    
	    for(Node n : availableNodes){
		if(n.getHoneyPot() != 1)
		    cleanNodes.add(n);
	    }

	    if(cleanNodes.isEmpty())
		return new AttackerAction(AttackerActionType.INVALID, 0);
	    
	    Node min = cleanNodes.get(0);
	    for(Node n : cleanNodes){
		if(n.getSv() < min.getSv())
		    min = n;
	    }

	    if(min.getHoneyPot() == -1)
		return new AttackerAction(AttackerActionType.PROBE_HONEYPOT, min.getNodeID());
	    else if(min.getSv() <= 15)
		return new AttackerAction(AttackerActionType.ATTACK, min.getNodeID());
	    else
		return new AttackerAction(AttackerActionType.SUPERATTACK, min.getNodeID());

	@Override
	protected void result(Node lastNode) {
		// TODO Auto-generated method stub
		
	}
}
