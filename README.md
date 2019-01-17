# Enterprise Computing Project

#### Group D - Topic #3

## Topic:
Etherium (private net) vs Hyperledger - Throughput for non-conflicting transactions

## Collaborators:

| Name  |  Mart. nr | git  | Mail  |
| :------------ | :------------ | :------------ | :------------ |
| Jacek Janczura  | 404975  | jjanczur  |  janczura.jacek@gmail.com |
| Igor Molcean  | 398366  | imolcean  | igor.molcean@gmail.com  |
| Julian Valentino Weigel  | 340560  | degreeno  | valewei@gmx.de  |
| Kim Janik Jasun Herter  | 398731  | kimherter  | kim_herter@web.de  |

## Github repo:
https://github.com/jjanczur/EthereumHyperledgerThroughputBenchmark.git

## Expectations:
- Report
- Poster - Template on ISIS
- Presentation of poster during poster session

## Grading:
- Poster (document): 6 Points
- Presentation during poster session: 4 Points
- Engagement during feedback session: 3 Points - Done! 3 / 3
- Report (and Git Repository, if applicable): 8 Points
- Overall: up to 21 Points

## Ethereum private blockchain network
To build Ethereum private blockchain network **go** and **geth** need to be installed
+ MacOS 

```
	brew tap ethereum/ethereum
	brew install ethereum
	# go to some directory 
	git clone https://github.com/ethereum/go-ethereum
	brew install go
	cd go-ethereum 
	make geth
	# Geth is installed
```

## Hyperledger Burrow private network
To build Hyperledger Burrow private network go, solidity and hyperledger burrow needs to be installed

- [Install go](https://golang.org/doc/install) version 1.10 or above and have `$GOPATH` set

```
go get github.com/hyperledger/burrow
cd $GOPATH/src/github.com/hyperledger/burrow
make build
```


### Install Solidity
https://solidity.readthedocs.io/en/v0.5.2/installing-solidity.html
```
sudo add-apt-repository ppa:ethereum/ethereum
sudo apt-get update
sudo apt-get install solc
```



## Throughput
Since all the transactions are stored in blocks (this are not a normal transactions as in ACID or BASE where they are expected to be executed right away), we decided to measure the throughput as blocks per time and blocks per time, divided by the number of transactions in a block.

## Blockchain benchmarking tools
Below are listed benchmarking tools that were tried by us. Since One benchmarking tool cannot be used to benchmark both blockchains we decided to write our own benchmarking tool based on out throughput definition.
### Ethereum - Chainhammer
+ Installation:
```
	git clone https://gitlab.com/electronDLT/chainhammer
	cd chainhammer/
	sudo apt install python3-pip libssl-dev
	sudo pip3 install virtualenv 
	virtualenv -p python3 py3eth
	source py3eth/bin/activate
	pip install ipython
	pip install ipykernel
	pip3 install requests
	pip3 install web3
	pip install py-solc
	pip install jsonrpclib
	python3 -m pip install --upgrade pip==18.0
	pip3 install --upgrade py-solc==2.1.0 web3==4.3.0 web3[tester]==4.3.0 rlp==0.6.0 eth-testrpc==1.3.4 requests pandas jupyter ipykernel matplotlib
	ipython kernel install --user --name="Python.3.py3eth"
```

### Hyperledger 
 **Calipher** - a benchmark tool for performance measurement of specific blockchain implementation using a set of predefined use cases.
 + Installation:

	```
	git clone https://github.com/hyperledger/caliper
	cd caliper/
	Install npm AND npm install --save fs-extra
	npm install moment, winston, winston-daily-rotate-file
	npm run burrow-deps 
	```


Conflicting:
Should be the double spending problem mentioned here:
https://isis.tu-berlin.de/pluginfile.php/1135652/mod_resource/content/0/06%20-%20SALT2.pdf
How to measure it on both blockchains (tools, approaches)
Prepare basic testing script for ETH throughput
Script to start up bootnode and N peer nodes
Script to issue M transactions measuring time (time at what point?)
Prepare basic testing script for Hyperledger throughput
Run the full tests on both blockchains
Prepare the documentation

## 	Interesting links
[Conflicting vs non-conflicting transactions](http://https://iota.stackexchange.com/questions/1604/what-is-a-conflicting-transaction "Conflicting vs non-conflicting transactions")

[Tool to measure blockchain performance](http://https://medium.com/coinmonks/tool-to-measure-blockchain-performance-hyperledger-caliper-f192adfba52  "Tool to measure blockchain performance")

 [Proof of Authority private ETH network with Parity](http://https://wiki.parity.io/Proof-of-Authority-Chains "Proof of Authority private ETH network with Parity")
 
 [Proof of Authority private ETH network with Geth](https://hackernoon.com/setup-your-own-private-proof-of-authority-ethereum-network-with-geth-9a0a3750cda8 "Proof of Authority private ETH network with Geth")

[ETH PoA on AWS](https://medium.com//collin.cusce/using-puppeth-to-manually-create-an-ethereum-proof-of-authority-clique-network-on-aws-ae0d7c906cce "ETH PoA on AWS")




