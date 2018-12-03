# 18w_cs5376
## About the Agents
The `src` directory includes the following packages:
- `agent`
    - champion Defender: ___Derpy___
    - champion Attacker: ___Alphalpha___
- `apiaryparty` <br/>

In the agent package, we developed Defenders and Attackers that implement different strategies to defending and attacking nodes in the graph.
The choices for each Defender agent includes making a Honeypot node (which is a trigger to end the game), Strengthening a node (increase the Security Value of a node), or adding a Firewall (which reduces the path to a node).
The following agents are Defender agents:
1. ___Derpy___, whose strategy to defense is a straightforward one. For each node in the list, the agent makes decisions to Firewall all neighbors of each node, except one. The one node that is not firewalled is instead made into a Honeypot (until the cost is over the budget). This means that ___Derpy___ will first attempt to execute what we call 'Order 66', where it will 'trap' the Attacker agent.
2. __Dummy__, whose strategy requires no calculations. Simply put, the defense approach is to make decisions for each node by cycling through its list of choices (listed above); in other words, the agent will start off randomly choosing one of the 3 choices, then cycling through each of the available choices.
3. __Joker__, whose strategy is to check the budget to find the lowest-cost security value and adds a Honeypot, if it is possible with the budget. <br/>

The choices for each Attacker agent includes Attacking a node, Superattacking a node, Probe the point value of the node, and Probing whether the node is a Honeypot.
The following agents are Attacker agents:
1. ___Alphalpha___, who checks whether a node is a Honeypot (if detected, it will choose to minimize the regret), and if not, it probes whether the budget allotted allows the agent to determine whether to Superattack or Attack the node (maximizing the expected value). <br/>
2. __ExMachina__ (driver: _Aardvark_), who calculates the expected value for each node in order to determine whether to Attack or Superattack.

Other agents in the `agent` directory are drivers.

## About the Project
CS5376 course project: Apiary Party <br/>
To work with the [Apiary Party](https://github.com/osveliz/ApiaryParty) library
- the source code is included as a package in this project (found in the `src/apiaryparty` directory)
- this package's source code is not modified

# Commit History
- removed print statements and combined strategies to champion agents: Derpy (defender) and Alphalpha (attacker)
- created Scrounger and Joker agents (authored by @james)
- created Derpy agent skeleton code (@mahdokht to debug)
- updated Alphalpha agent (@james to debug)
- added Alphalpha agent skeleton code (@james to fix)
- added Dummy agent skeleton code
