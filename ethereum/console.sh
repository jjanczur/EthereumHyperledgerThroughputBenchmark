#!/bin/bash

KEY=$(cat ./keys.txt | head -"$1" | tail -1)

if [[ -z $KEY ]]
then
    echo "No key with the given ID found"
    exit 1
fi

BOOTNODEID=$(cat ./boot.key)
BOOTNODEIP="18.195.130.226"

geth --metrics --datadir "./node" --preload "./js/lib.js" --syncmode "full" --port 30300 --bootnodes "enode://$BOOTNODEID@$BOOTNODEIP:30310" --networkid 42 --gasprice "1" --etherbase "$KEY" --unlock "$KEY" --password "./password.txt" console
