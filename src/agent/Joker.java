package agent;

import apiaryparty.*;
import java.util.ArrayList;

public class Joker extends Defender {

	public Joker(String graphFile)
	{
		super("Joker",graphFile);
	}

	@Override
	public void initialize() {

	}

	@Override
	public void actionResult(boolean actionSuccess) {

	}

	@Override
	public DefenderAction makeAction() {

		ArrayList<Node> cleanNodes = new ArrayList<>();

		for(Node n : net.getAvailableNodes()) {
			if(!n.isHoneyPot())
				cleanNodes.add(n);
		}

		Node min = cleanNodes.get(0);

		for(Node cn : cleanNodes) {
			if(cn.getSv() < min.getSv())
				min = cn;
		}

		if(this.getBudget() >= Parameters.HONEYPOT_RATE)
			return new DefenderAction(DefenderActionType.HONEYPOT, min.getNodeID());
		else if(this.getBudget() >= Parameters.STRENGTHEN_RATE)
			return new DefenderAction(DefenderActionType.STRENGTHEN);
		else return new DefenderAction(DefenderActionType.INVALID);
	}
}
