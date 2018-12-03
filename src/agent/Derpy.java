package agent;

import apiaryparty.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

public class Derpy extends Defender {

    public Derpy(String graphFile)
    {
        super("Derpy",graphFile);
    }

    private ArrayDeque<DefenderAction> actionList;
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

        if (cost <= getBudget()) {
            System.out.format("Cost: %d\nBudget: %d\nEXECUTING ORDER 66!\n\n", cost, this.getBudget());
            preorder66();
            strat = Strategy.TRAP;
        } else if (hpPercentage < 0.333) {
            System.out.format("HP cost reasonable at %f of budget\n", hpPercentage);
            strat = Strategy.HONEY;
        } else {
            System.out.format("HP cost very high at %f of budget", hpPercentage);
            strat = Strategy.STRENGTHEN;
        }
    }

    @Override
    public void actionResult(boolean actionSuccess) {}

    @Override
    public DefenderAction makeAction() {

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

        boolean first;
        actionList = new ArrayDeque<>();

        System.out.format("All Nodes: %s\n", Arrays.toString(net.getNodes()));

        for (Node n : net.getNodes()) {
            if (n.getSv() == 0) {
                System.out.format("Public Node Found: Node %d\n", n.getNodeID());
                System.out.format("Neighbors: %s\n", n.neighbor.toString());
                first = true;
                for (Node ne : n.neighbor) {
                    if (first) {

                        System.out.format("HoneyPotting: Node %d\n", n.getNodeID());
                        System.out.println(isValidHP(n.getNodeID()));
                        actionList.add(new DefenderAction(DefenderActionType.HONEYPOT, n.getNodeID()));

                        System.out.format("Firewalling: Node %d, Node %d\n", n.getNodeID(), ne.getNodeID());
                        actionList.add(new DefenderAction(n.getNodeID(), ne.getNodeID()));

                        first = false;
                    } else {
                        System.out.format("Firewalling: Node %d, Node %d\n", n.getNodeID(), ne.getNodeID());
                        actionList.add(new DefenderAction(n.getNodeID(), ne.getNodeID()));
                    }
                }
            }
        }
    }

    private DefenderAction order66() {
        ArrayList<Node> cleanNodes = new java.util.ArrayList<>();

        for(Node n: net.getAvailableNodes()) {
            if(!n.isHoneyPot())
                cleanNodes.add(n);
        }

        if(cleanNodes.isEmpty()) {
            System.out.println("All nodes are honeypots");
            return new DefenderAction(DefenderActionType.INVALID);
        }

        System.out.format("Available non hp nodes: %s\n", cleanNodes.toString());

        DefenderAction action = actionList.poll();

        if (action == null)
            action = new DefenderAction(DefenderActionType.END_TURN);

        return action;
    }

    private DefenderAction noProbeBestResponse() {
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
            return new DefenderAction(DefenderActionType.STRENGTHEN, min.getNodeID());
        else return new DefenderAction(DefenderActionType.INVALID);
    }

    private DefenderAction strengthen() {

        Node[] nodes = this.net.getNodes();

        for (Node n : nodes) {
            // If it's close to being 20, strengthen
            if (n.getSv() < 20 && n.getSv() > 14) {
                return new DefenderAction(DefenderActionType.STRENGTHEN, n.getNodeID());
            }
        }

        ArrayList<Node> avNodes = this.net.getAvailableNodes();
        Node maxPv = avNodes.get(0);

        for (Node n : avNodes) {
            if (n.getPv() < maxPv.getPv())
                maxPv = n;
        }

        return new DefenderAction(DefenderActionType.STRENGTHEN, maxPv.getNodeID());
    }
}
