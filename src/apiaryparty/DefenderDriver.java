package apiaryparty;

/**
 * Creates timed threads for the Player. Called by GameMaster when updating the player and getting the player's action.
 */
public class DefenderDriver implements Runnable {
	
	/**Used to know which Player method to call*/
	public final PlayerState state;
	/**Used to know which Player subclass to communicate with*/
	private Defender defender;
	
	/**Various variables needed to call the Player's methods*/
	private boolean success;

	/**
	 * Constructor used for Player's initialize() and makeAction() methods
	 * @param state a PlayerState
	 * @param defender a Defender
	 */
	public DefenderDriver(PlayerState state, Defender defender){
		this.state = state;
		this.defender = defender;
	}
	
	/**
	 * Constructor used for Player's actionResult() method
	 * @param state a PlayerState
	 * @param defender a Defender
	 * @param success if successful
	 */
	public DefenderDriver(PlayerState state, Defender defender, boolean success){
		this.state = state;
		this.defender = defender;
		this.success = success;
	}
	
	/**
	 * GameMaster will create a thread to run this class that will call a Defender's subclass'
	 * methods. Any exceptions or time outs will only harm this thread and will not affect GameMaster
	 */
	public void run() {
		try{
			switch(state){
			case INIT:
				defender.initialize();
				break;
			case RESULT:
				defender.actionResult(success);
				break;
			case MAKE_ACTION:
				defender.handleAction();
				break;
			default:
				defender.endGame();
				break;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
