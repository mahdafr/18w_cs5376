package agent;

/**
 * @author mahdafr
 * @created Dec01 S
 * @modified Dec02 U by mahdafr
 *
 * Attacker agent Aardvark finds the expected value of attacking each node.
 */
import apiaryparty.*;

public class Aardvark extends Attacker {
    private final static String attackerName = "Aardvark";
    //calculate expected value for attacking and super-attacking
    java.util.PriorityQueue<Double> attack = new java.util.PriorityQueue<>();
    java.util.PriorityQueue<Double> superattack = new java.util.PriorityQueue<>();
    Node[] nodeList;

    /**
     * Constructor
     * @param defenderName defender's name
     * @param graphFile graph to read
     */
    public Aardvark(String defenderName, String graphFile) {
        super(attackerName, defenderName, graphFile);
    }

    public Aardvark(){
        super(attackerName);
    }

    /**
     * If you need to initialize anything, do it  here
     */
    protected void initialize(){
        //if probing costs too much, calculate expected value of attacks

        nodeList = net.getNodes();
        double[] probAttack = new double[nodeList.length]; //probabilities of picking each node
        double[] probSupAtt = new double[nodeList.length];

        for ( int i=0 ; i<probAttack.length ; i++ ) {
            probAttack[i] = 1 - nodeList[i].getSv() / Parameters.ATTACK_ROLL; //prob of each node when attacking
            probSupAtt[i] = 1 - nodeList[i].getSv() / Parameters.SUPERATTACK_ROLL; //prob of each node when superattacking
        }

        //calculate the expected value of each node
        for ( int i=0 ; i<nodeList.length ; i++ ) {
            attack.add(calcExpectedVal(nodeList[i], probAttack[i]));
            superattack.add(calcExpectedVal(nodeList[i],probSupAtt[i]));
        }
    }

    //calculate the expected value of each node
    private double calcExpectedVal(Node rt, double p) {
        return p*rt.getSv(); //assumption: sv=pv
    }

    @Override
    public AttackerAction makeAction() {
        System.out.println("----------------------------------------------------------AARDVARK " + attack.size() + superattack.size());

        double att = attack.remove();
        double supAtt = superattack.remove();
        int index = nodeList.length - attack.size();

        //expected value of attacking is more rewarding
        if ( att>supAtt && isValidAttack(nodeList[index].getNodeID()) )
            return new AttackerAction(AttackerActionType.ATTACK,nodeList[index].getNodeID());
        //expected value of superattacking is more rewarding
        if ( isValidSuperAttack(nodeList[index].getNodeID()) )
            return new AttackerAction(AttackerActionType.SUPERATTACK,nodeList[index].getNodeID());

        return new AttackerAction(AttackerActionType.INVALID,0);
    }

    @Override
    protected void result(Node lastNode) {
        // TODO Auto-generated method stub
    }
}
