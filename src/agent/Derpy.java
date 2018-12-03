package agent;

/**
 * @author mahdafr
 * @modified Dec02 U by jimmyjoe
 *
 * Derpy is the champion Defender agent that implements different strategies of the following defender agents:
 *    Dummy and Joker.
 */

import apiaryparty.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

public class Derpy extends Defender {

    public Derpy(String graphFile)
    {
        super("Derpy",graphFile);
    }

    //the lsit of actions to take in a queue
    private ArrayDeque<DefenderAction> actionList;
    //which strategy to play?
    private enum Strategy {
        TRAP, HONEY, STRENGTHEN
    }
    private Strategy strat;

    @Override
    public void initialize() {
        int cost = 0;

        // Estimate the cost of a guaranteed trap
        for (Node n : net.getNodes()) {
//            System.out.format("Node %d sv = %d\n", n.getNodeID(), n.getSv());
            if (n.getSv() == 0) {
                // Honeypot first, firewall all other paths
                boolean first = true;
                //for every neighbor
                for (Node ne : n.neighbor) {
                    // If it's not a public node update cost
                    if (ne.getSv() != 0) {
                        if (first) {
                            cost += Parameters.HONEYPOT_RATE;
                            cost += Parameters.FIREWALL_RATE;
                            first = false;
                        } else
                            cost += Parameters.FIREWALL_RATE;
                    }
                }
            }
        }

        double hpPercentage = (double)Parameters.HONEYPOT_RATE / getBudget();

        //if the action is affordable, execute order66 and trap the attacker (honeypot all nodes)
        if (cost <= getBudget()) {
//            System.out.format("Cost: %d\nBudget: %d\nEXECUTING ORDER 66!\n\n", cost, this.getBudget());
            preorder66();
            strat = Strategy.TRAP;
        } else if (hpPercentage < 0.333) {
//            System.out.format("HP cost reasonable at %f of budget\n", hpPercentage);
            strat = Strategy.HONEY;
        } else {
//            System.out.format("HP cost very high at %f of budget", hpPercentage);
            strat = Strategy.STRENGTHEN;
        }
    }

    @Override
    public void actionResult(boolean actionSuccess) {}

    @Override
    public DefenderAction makeAction() {
        //depending on the strategy decided by on initialize, play a move
        switch (strat) {
            case TRAP:
                return order66();
            case HONEY:
                return noProbeBestResponse();
            case STRENGTHEN:
                return strengthen();
            default:
                return noProbeBestResponse();
        }
    }

    private void preorder66() {
        //trap the attacker by building the list of moves to play
        //that is, for every node, firewall all but one neighbor and honeypot that neighbor (without duplicating)
        boolean first;
        actionList = new ArrayDeque<>();

//        System.out.format("All Nodes: %s\n", Arrays.toString(net.getNodes()));

        //for every node
        for (Node n : net.getNodes()) {
            if (n.getSv() == 0) {
//                System.out.format("Public Node Found: Node %d\n", n.getNodeID());
//                System.out.format("Neighbors: %s\n", n.neighbor.toString());
                first = true;
                //for every neighbor
                for (Node ne : n.neighbor) {
                    //honeypot the first one (arbitrary)
                    if (first) {

//                        System.out.format("HoneyPotting: Node %d\n", n.getNodeID());
//                        System.out.println(isValidHP(n.getNodeID()));
                        actionList.add(new DefenderAction(DefenderActionType.HONEYPOT, n.getNodeID()));

//                        System.out.format("Firewalling: Node %d, Node %d\n", n.getNodeID(), ne.getNodeID());
                        actionList.add(new DefenderAction(n.getNodeID(), ne.getNodeID()));

                        first = false;
                    } else {
                        //firewall every other neighbor
//                        System.out.format("Firewalling: Node %d, Node %d\n", n.getNodeID(), ne.getNodeID());
                        actionList.add(new DefenderAction(n.getNodeID(), ne.getNodeID()));
                    }
                }
            }
        }
    }

    //trap the attacker by building the list of moves to play
    //that is, for every node, firewall all but one neighbor and honeypot that neighbor (without duplicating)
    private DefenderAction order66() {
        ArrayList<Node> cleanNodes = new java.util.ArrayList<>();

        for(Node n: net.getAvailableNodes()) {
            if(!n.isHoneyPot())
                cleanNodes.add(n);
        }

        //no nodes to honeypot
        if(cleanNodes.isEmpty()) {
//            System.out.println("All nodes are honeypots");
            return new DefenderAction(DefenderActionType.INVALID);
        }

//        System.out.format("Available non hp nodes: %s\n", cleanNodes.toString());

        DefenderAction action = actionList.poll();

        //end the turn
        if (action == null)
            action = new DefenderAction(DefenderActionType.END_TURN);

        return action;
    }

    //play the best response
    private DefenderAction noProbeBestResponse() {
        ArrayList<Node> cleanNodes = new ArrayList<>();

        //for every node, if it is not a honeypot, save it
        for(Node n : net.getAvailableNodes()) {
            if(!n.isHoneyPot())
                cleanNodes.add(n);
        }

        Node min = cleanNodes.get(0);

        //for all the non-honeypots, find the minimum security value
        for(Node cn : cleanNodes) {
            if(cn.getSv() < min.getSv())
                min = cn;
        }

        //add a honeypot, if the budget allows
        if(this.getBudget() >= Parameters.HONEYPOT_RATE)
            return new DefenderAction(DefenderActionType.HONEYPOT, min.getNodeID());
        else if(this.getBudget() >= Parameters.STRENGTHEN_RATE)
            return new DefenderAction(DefenderActionType.STRENGTHEN, min.getNodeID());
        else return new DefenderAction(DefenderActionType.INVALID);
    }

    //strategy is to strengthen the nodes
    private DefenderAction strengthen() {
        Node[] nodes = this.net.getNodes();

        //strengthen the nodes
        for (Node n : nodes) {
            // If it's close to being 20, strengthen
            if (n.getSv() < 20 && n.getSv() > 14) {
                return new DefenderAction(DefenderActionType.STRENGTHEN, n.getNodeID());
            }
        }

        //for every node, find the maximum point value
        ArrayList<Node> avNodes = this.net.getAvailableNodes();
        Node maxPv = avNodes.get(0);

        for (Node n : avNodes) {
            if (n.getPv() < maxPv.getPv())
                maxPv = n;
        }

        //strengthen that node
        return new DefenderAction(DefenderActionType.STRENGTHEN, maxPv.getNodeID());
    }
}
