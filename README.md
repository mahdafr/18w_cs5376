# 18w_cs5376
## About the Agents
The `src` directory includes the following packages:
- `agent`
    - champion Defender, ___Derpy___, that makes decision depending on the parameters of the game in playing strategies, where each strategy is implemented by each of the driver agents (defenders) included in the `agent` package.
    - champion Attacker, ___Alphalpha___, that makes decision depending on the parameters of the game in playing strategies, where each strategy is implemented by each of the driver agents (attackers) included in the `agent` package.
- `apiaryparty` <br/>

In the agent package, we developed Defenders and Attackers that implement different strategies to defending and attacking nodes in the graph.
The choices for each Defender agent includes making a Honeypot node (which is a trigger to end the game), Strengthening a node (increase the Security Value of a node), or adding a Firewall (which reduces the path to a node).
The following agents are Defender agents:
1. ___Derpy___, who combines three different strategies depending on the parameterized cost of defender actions. First, it will determine whether it can afford to trap the attacker by forcing it to attack a honeypot: by firewalling all nodes other than a honeypot, the game is over in one action regardless of what the attacker chooses to do. If it can not, it favors deploying honeypots to the nodes with the lowest security value unless it deems them too costly. When all else fails, it tries to create as many nodes with a security value of 20 as it can, and uses leftover budget for strengthening the most valuable targets neighboring the public nodes.
2. __Dummy__, whose strategy requires no calculations. Simply put, the defense approach is to make decisions for each node by cycling through its list of choices (listed above); in other words, the agent will start off randomly choosing one of the 3 choices, then cycling through each of the available choices.
3. __Joker__, whose strategy is to find the node with the lowest security value and add a Honeypot, if it is possible with the budget. <br/>

The choices for each Attacker agent includes Attacking a node, Superattacking a node, Probe the point value of the node, and Probing whether the node is a Honeypot.
The following agents are Attacker agents:
1. ___Alphalpha___, who has two strategies in their arsenal: minimizing regret by always probing for honeypots before attacking an easy target and maximizing expected value by choosing the attacking action with the highest expected return. May it be noted that if it detects honeypots are present in the network it will always choose to probe for them before it attacks. When estimating expected value, it uses the security value as an indicator for actual point value, not wasting any of its budget on probing.<br/>
2. __ExMachina__ (driver: _Aardvark_), who calculates the expected value for each node in order to determine whether to Attack or Superattack.

Other agents in the `agent` directory are baseline agents.

## About the Project
CS5376 course project: Apiary Party <br/>
To work with the [Apiary Party](https://github.com/osveliz/ApiaryParty) library
- the source code is included as a package in this project (found in the `src/apiaryparty` directory)
- this package's source code is not modified

## Commit History
- removed print statements and combined various independent agent strategies to champion agents: Derpy (defender) and Alphalpha (attacker)
- created Scrounger and Joker agents (authored by @james)
- created Derpy agent skeleton code (@mahdokht to debug)
- updated Alphalpha agent (@james to debug)
- added Alphalpha agent skeleton code (@james to fix)
- added Dummy agent skeleton code

## Notes
- We separated our agents from the source code and placed them in a directory (package) named agents. For our agents to be compatible with the base code provided, simply delete the first two statements in each file.
