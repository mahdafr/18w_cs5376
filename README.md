# 18w_cs5376
## About the Agents
The `src` directory includes the following packages:
- `agent`
- `apiaryparty` <br/>

In the agent package, we developed Defenders and Attackers that implement different strategies to defending and attacking nodes in the graph.
The choices for each Defender agent includes making a Honeypot node (which is a trigger to end the game), Strengthening a node (increase the Security Value of a node), or adding a Firewall (which reduces the path to a node).
Defender agents include those whose names begin with the letter _D_:
1. __Derpy__, whose approach to defense is a straightforward one. For each node in the list, the agent makes decisions to Firewall all neighbors of each node, except one. The one node that is not firewalled is instead made into a Honeypot.
2. __Dummy__, whose approach requires no calculations. Simply put, the defense approach is to make decisions for each node by cycling through its list of choices (listed above); in other words, the agent will start off randomly choosing one of the 3 choices, then cycling through each of the available choices.
3. __Joker__, whose approach is to check the budget to decide between making a Honeypot or Strengthening a node. <br/>

The choices for each Attacker agent includes Attacking a node, Superattacking a node, Probe the point value of the node, and Probing whether the node is a Honeypot. Attacker agents include those whose names begin with the letter _A_:
1. __Aardvark__, who calculates the expected value for each node in order to determine whether to Attack or Superattack.
2. __Alphalpha__, who checks whether a node is a Honeypot, and if not, it probes whether the budget allotted allows the agent to determine whether to Superattack or Attack the node. <br/>

## About the Project
CS5376 course project: Apiary Party <br/>
To work with the [Apiary Party](https://github.com/osveliz/ApiaryParty) library
- the source code is included as a package in this project (found in the `src/apiaryparty` directory)
- this package's source code is not modified

# Commit History
- created Scrounger and Joker agents (authored by @james)
- created Derpy agent skeleton code (@mahdokht to debug)
- updated Alphalpha agent (@james to debug)
- added Alphalpha agent skeleton code (@james to fix)
- added Dummy agent skeleton code