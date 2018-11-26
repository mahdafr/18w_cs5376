package apiaryparty;

import java.util.Random;

/**
 * Example Defender Agent that strengthens at random
 * @author Porag Chowdhury
 */
public class WorkerBee extends Defender
{
	
	Random r;
	
    public WorkerBee(String graphFile)
    {
        super("WorkerBee",graphFile);
    }

	@Override
	public void initialize() {
		r = new Random();
	}

	@Override
	public void actionResult(boolean actionSuccess) {
		
	}

	@Override
	public DefenderAction makeAction() {
		if(this.getBudget()<Parameters.STRENGTHEN_RATE)
			return new DefenderAction(false);
		int tries = 0;
        int node = r.nextInt(net.getSize());
        while(!isValidStrengthen(node) && tries++ < 10)
            node = r.nextInt(net.getSize());
        return new DefenderAction(DefenderActionType.STRENGTHEN, node);
	}
}
