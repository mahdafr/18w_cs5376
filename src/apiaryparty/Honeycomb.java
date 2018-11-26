package apiaryparty;

/**
 * Benchmark Defender Agent that does nothing to secure the network
 * @author Oscar Veliz
 */


public class Honeycomb extends Defender{
	
	   public Honeycomb(String graphFile)
	    {
	        super("Honeycomb",graphFile);
	    }

	   @Override
		public void initialize() {
		}

		@Override
		public void actionResult(boolean actionSuccess) {
			
		}

		@Override
		public DefenderAction makeAction() {
			return new DefenderAction(false);
		}
}
