package agent;

import apiaryparty.*;

public class Inquirer extends Attacker {
	private final static String attackerName = "Inquirer";

	/**
	 * Constructor
	 * @param defenderName defender's name
	 * @param graphFile graph to read
	 */
	public Inquirer(String defenderName, String graphFile) {
		super(attackerName, defenderName, graphFile);
	}

	public Inquirer(){
		super(attackerName);
	}

	/**
	 * If you need to initialize anything, do it  here
	 */
	protected void initialize(){

	}

	@Override
	public AttackerAction makeAction() {
		return new AttackerAction(AttackerActionType.INVALID, 0);
	}

	@Override
	protected void result(Node lastNode) {
		// TODO Auto-generated method stub
	}
}
