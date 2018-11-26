package apiaryparty;

/**
 * Creates timed threads for the Player. Called by GameMaster when updating the player and getting the player's action.
 */
public class AttackerDriver implements Runnable {
	
	/**Used to know which Player method to call*/
	public final PlayerState state;
	/**Used to know which Player subclass to communicate with*/
	private Attacker attacker;
	private Network net;
	/**Various variables needed to call the Player's methods*/
	
	/**
	 * Constructor used for Player's initialize(), makeAction(), and actionResult() methods
	 * @param state a PlayerState
	 * @param attacker a Player
	 * @param net  a Network
	 */
	public AttackerDriver(PlayerState state, Attacker attacker, Network net){
		this.state = state;
		this.attacker = attacker;
		this.net = net;
	}
	/**
	 * Constructor used for Player's initialize(), makeAction(), and actionResult() methods
	 * @param state a PlayerState
	 * @param attacker a Player
	 */
	public AttackerDriver(PlayerState state, Attacker attacker){
		this.state = state;
		this.attacker = attacker;
	}
	
	/**
	 * GameMaster will create a thread to run this class that will call a Attacker's subclass'
	 * methods. Any exceptions or time outs will only harm this thread and will not affect GameMaster
	 */
	public void run() {
		try{
			switch(state){
			case INIT:
				attacker.initialize();
				break;
			case RESULT:
				attacker.actionResult(net);
				break;
			case MAKE_ACTION:
				attacker.handleAction();
				break;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
