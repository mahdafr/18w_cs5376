package apiaryparty;

/**
 * Defender Action.
 * You specify which action you want by calling the proper constructor
 */

public class DefenderAction {

	private DefenderActionType type;
	private int strengthenedNode;
	private int fwallNode1;
	private int fwallNode2;
	//private HoneypotType hpType;
	private int[] hpNeighbors;
	private int honeyNode;
	private int pv;
	private int sv;
	
	/**
	 * Returns the action type
	 * @return the action type
	 */
	public DefenderActionType getType(){
		return type;
	}
	
	/**
	 * Constructor for node invalid moves
	 */
	public DefenderAction(){
		type = DefenderActionType.INVALID;
	}
	
	/**
	 * Constructor to end turn and invalid moves
	 * @param actionType the action type
	 */
	public DefenderAction(DefenderActionType actionType){
		if(actionType == DefenderActionType.END_TURN)
			type = DefenderActionType.END_TURN;
		else
			type = DefenderActionType.INVALID;
	}
	
	/**
	 * Constructor for node strengthen and placing honeypots
	 * @param actionType the action type
	 * @param node node id
	 */
	public DefenderAction(DefenderActionType actionType, int node){
		if(actionType == DefenderActionType.STRENGTHEN){
			strengthenedNode = node;
			type = DefenderActionType.STRENGTHEN;
		}else if(actionType == DefenderActionType.HONEYPOT){
			type = DefenderActionType.HONEYPOT;
			honeyNode = node;
		}else{
			type = DefenderActionType.INVALID;
		}
	}
	/**
	 * Gets the node id that was strengthened
	 * @return the node id that was strengthened
	 */
	public int getSNode(){
		return strengthenedNode;
	}
	
	/**
	 * Constructor for firewall
	 * @param node1 id of first node
	 * @param node2 id of second node
	 */
	public DefenderAction(int node1, int node2){
		fwallNode1 = node1;
		fwallNode2 = node2;
		type = DefenderActionType.FIREWALL;
	}
	/**
	 * Returns id of node in firewall
	 * @return id of node in firewall
	 */
	public int getFwall1(){
		return fwallNode1;
	}
	/**
	 * Returns id of node in firewall
	 * @return id of node in firewall
	 */
	public int getFwall2(){
		return fwallNode2;
	}
	
	/**
	 * Constructor for honeypot
	 * @param hp the type of honeypot
	 * @param neighbors array of node id's to connect to the honeypot
	 */
	/*public DefenderAction(HoneypotType hp, int[] neighbors){
		hpType = hp;
		hpNeighbors = neighbors;
		type = DefenderActionType.HONEYPOT;
	}*/
	
	/**
	 * Constructor for ending turns
	 * @param done can be either true or false
	 */
	public DefenderAction(boolean done){
		type = DefenderActionType.END_TURN;
	}
	
	/**
	 * Returns the honeypot type
	 * @return honeypot type
	 */
	/*public HoneypotType getHPType(){
		return hpType;
	}*/
	
	/**
	 * Returns the source node of a honeypot
	 * @return node honeypot is generated off of
	 */
	public int getHoneyNode(){
		return honeyNode;
	}
	
	public void setNeighbors(int[] neighbors){
		hpNeighbors = neighbors;
	}
	
	/**
	 * Returns the neighbors for the new honeypot
	 * @return array of neighbors for the new honeypot
	 */
	public int[] getNeighbors(){
		return hpNeighbors;
	}
	
	/**
	 * Used for setting the honeypot pv and sv
	 * Can be called by defender agent but values will be overwritten by Monitor based on the honeypot type
	 * @param p point value
	 * @param s security value
	 */
	public void setHPValues(int p, int s){
		pv = p;
		sv = s;
	}
	/**
	 * Used for serializing the action
	 * @return a String representing the action
	 */
	public String toString(){
		String s = "";
		switch(type){
		case STRENGTHEN:
			s += "0,";
			s += strengthenedNode+"\n";
			break;
		case FIREWALL:
			s += "1,";
			s += fwallNode1+","+fwallNode2+"\n";
			break;
		case HONEYPOT:
			s += "2";
			s += ","+pv;
			s += ","+sv;
			for(int i = 0; i < hpNeighbors.length; i++)
				s+=","+hpNeighbors[i];
			s += "\n";
			break;
		case END_TURN:
			break;
		default://invalid
			s = "-1\n";
			break;
		}
		return s;
	}
	
}
