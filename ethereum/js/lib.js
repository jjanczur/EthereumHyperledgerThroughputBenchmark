// Account list

var node0 = "0x909913a250c7087d426c20b46b913d4dd9b526a8";
var node1 = "0x42246e58c85037f28177254f352fee4f2bd792ff";


// Some function to make life easier

function getBalance(acc)
{
    return web3.fromWei(eth.getBalance(acc));
}

function send(value)
{
	if (eth.coinbase==node0) {
			return eth.sendTransaction({
        		"from" : eth.coinbase,
        		"to" : node1,
        		"value" : web3.toWei(value, "ether")
        	});
	} else {
		 	return eth.sendTransaction({
       			"from" : eth.coinbase,
        		"to" : node0,
        		"value" : web3.toWei(value, "ether")
       		});
	}
}

function sendToNode0(value)
{
    return eth.sendTransaction({
        "from" : eth.coinbase,
        "to" : node0,
        "value" : web3.toWei(value, "ether")
        });
}

function sendToNode1(value)
{
    return eth.sendTransaction({
        "from" : eth.coinbase,
        "to" : node1,
        "value" : web3.toWei(value, "ether")
        });
}

function sendTransaction(to, value)
{
    return eth.sendTransaction({
        "from" : eth.coinbase,
        "to" : to,
        "value" : web3.toWei(value, "ether")
        });
}
