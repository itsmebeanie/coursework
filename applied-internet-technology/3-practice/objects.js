//objects.js
function Player(name){
	this.name = name;
	this.movehistory = [];
	this.opponentsmoves = [];
}

Player.prototype.move = function(){
	var move = this.getNextMove();
	this.movehistory.push(move);
	return move;
}

Player.prototype.getNextMove = function(){
	return 'R';
}

Player.prototype.recordOpponentMove= function(opponentMove){	
	this.opponentsmoves.push(opponentMove);
}

function HistoryRepeatsItself(name){
	Player.call(this,name);
}
HistoryRepeatsItself.prototype = Object.create(Player.prototype);
HistoryRepeatsItself.prototype.move = function(){
	var length = this.opponentsmoves.length; 
	var move = "";
	var possiblemoves = ['R','P','S'];
	if (length == 0){
		var randomMove = parseInt(Math.random()*3,10);
		move = possiblemoves[randomMove];
		this.movehistory.push(move);
	}
	else { 
		var lastMove = this.opponentsmoves[length-1];
		switch (lastMove){
			case 'R':
				move = 'P';

				break;
			case 'P':
				move = 'S';
				break;
			case 'S':
				move = 'R';
				break;

		}
		this.movehistory.push(move);

	}
	return move;


}


var p = new Player("Normal Nancy");
console.log(p.name);
console.log("---------");
console.log("Nancy always plays rock: " + p.move());
p.recordOpponentMove("P");
console.log("Nancy always plays rock, regardless of her opponent's last move: " + p.move());
console.log("All of Nancy's moves so far",p.movehistory);
console.log("All of her opponent's move so far:.", p.opponentsmoves);
var s = new HistoryRepeatsItself("Timely Tabatha");
console.log("\n");
console.log(s.name);
console.log("---------");
console.log("Tabitha's first move should be random: " + s.move());
s.recordOpponentMove("R");
console.log("If her last opponent's move was rock, she'll play paper " + s.move());
s.recordOpponentMove("P");
console.log("If her last opponent's move was paper, she'll play scissors " + s.move());
console.log("All of Tabitha's moves so far:", s.movehistory);
console.log("All of her opponent's moves so far: ", s.opponentsmoves)
console.log("\n");

// Compositions

function StrategyPlayer(name, StrategyObject) {
	this.name = name;
	this.StrategyObject = StrategyObject;

	this.movehistory = [];
	this.opponentsmove = [];
}


StrategyPlayer.prototype.recordOpponentMove= function(opponentMove){	
	this.opponentsmove.push(opponentMove);
}

StrategyPlayer.prototype.move = function() {
	var move = this.StrategyObject.getNextMove(this.opponentsmove);
	this.movehistory.push(move);
	return move;
}


function LookAtPreviousMoveStrategy() {}
LookAtPreviousMoveStrategy.prototype.getNextMove = function(moves) { 
	var length = moves.length; 
	var move = "";
	var possiblemoves = ['R','P','S'];
	if (length == 0){
		var randomMove = parseInt(Math.random()*3,10);
		move = possiblemoves[randomMove];
	}
	else { 
		var lastMove = moves[length-1];
		switch (lastMove){
			case 'R':
				move = 'P';
				break;
			case 'P':
				move = 'S';
				break;
			case 'S':
				move = 'R';
				break;

		}

	}
	return move;
}

obj = new StrategyPlayer("Previous Patty", new LookAtPreviousMoveStrategy());
console.log(obj.name);
console.log("---------");
console.log("Patty's first move should be random: " + obj.move());
obj.recordOpponentMove("R");
console.log("If her last opponent's move was rock she'll play paper: " + obj.move());
obj.recordOpponentMove("P");
console.log("If her last opponent's move was paper she'll play scissors: " + obj.move());

console.log("All of Patty's mmoves so far", obj.movehistory);
console.log("All of her opponent's mmoves so far", obj.opponentsmove);
