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
    private java.util.ArrayDeque<DefenderAction> actionList;

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

        if(cost <= getBudget())
            System.out.format("Cost: %d\nBudget: %d\nEXECUTING ORDER 66!\n\n", cost, this.getBudget());
            order66();
        //if honeypotting costs little, execute Order66
    }

    @Override
    public void actionResult(boolean actionSuccess) {}

    @Override
    public DefenderAction makeAction() {

        ArrayList<Node> cleanNodes = new java.util.ArrayList<>();

        for(Node n: net.getAvailableNodes()) {
            if(!n.isHoneyPot())
                cleanNodes.add(n);
        }

        if(cleanNodes.isEmpty())
            System.out.println("All nodes are honeypots wtf");

        System.out.format("Available non hp nodes: %s\n", cleanNodes.toString());

        DefenderAction action = actionList.poll();

        if (action == null)
            action = new DefenderAction(DefenderActionType.END_TURN);

        return action;
    }

    private void order66() {

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
//                        System.out.println(isValidFirewall(n.getNodeID(), ne.getNodeID()));
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
}
