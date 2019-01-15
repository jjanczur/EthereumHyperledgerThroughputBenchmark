#!/bin/bash

KEY=$(cat ./keys.txt | head -"$1" | tail -1)

if [[ -z $KEY ]]
then
    echo "No key with the given ID found"
    exit 1
fi

BOOTNODEID=$(cat ./boot.key)

# TODO: Get the public IP of the bootnode from its hostname
BOOTNODEIP="18.194.232.161"

geth --datadir "./node" --preload "./js/lib.js" --syncmode "full" --port 30300 --rpc --rpcport 8500 --rpcapi 'personal,db,eth,net,web3,txpool,miner' --bootnodes "enode://$BOOTNODEID@$BOOTNODEIP:30310" --networkid 42 --gasprice "1" --etherbase "$KEY" --unlock "$KEY" --password "./password.txt" console
