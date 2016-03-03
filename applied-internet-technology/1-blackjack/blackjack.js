/*

Kaitlin Gu
Applied Internet Technology

This program is a commandline Blackjack game with 2 players - the user and the computer.

>each player is dealt 2 cards from a 52 card deck, with each card representing 
some numeric value
>each player can choose to be dealt more cards ("hit") or stop being dealt cards 
("stand")
>the sum of the numeric values of the cards in a player's hand determines if they've 
won
>once both players have either chosen to stand or have a hand with a total that's 
more than 21 ("bust"), the hands are compared
>the player with the hand that's closest to 21 without going over wins
ties are possible (either same total, or both player "bust")

*/

var readlineSync = require('readline-sync');

/* Generates a deck of 52 cards */
var generateCards = function(){
	var cards =[];
	suit = ["♠", "♥", "♦", "♣"];
	face = ["2","3","4","5","6","7","8","9","10","J","Q","K","A"]
	for (i = 0; i<suit.length;i++){
		for (j = 0; j<face.length;j++){
			cards.push({suit:suit[i],face:face[j]});
		}
 	}
	return cards;
};

/* Shuffles a given array */
var shuffle = function(Array){
	var temp = [];
	var length = Array.length;
	while (length > 0){
		var i = parseInt(Math.random()*length,10);
		temp.push(Array[i]);
		Array.splice(i,1);
		length = length-1;
	}
	return temp;
};


/* Calculates hand total */ 
var calculateHand = function(Array){
	var sum = 0;
	var Aces = 0;
	for (var i = 0; i <Array.length;i++){
		if (Array[i].face == "J" || Array[i].face == "Q" || Array[i].face == "K"){
			sum+=10;
		}
		else if (Array[i].face == "A"){
			sum+=11;
			Aces += 1;
		}
		else{
			sum += parseInt(Array[i].face,10);
		}
	}

	while (sum > 21 && Aces > 0){
		sum -= 10;
		Aces--;
	}
	return sum;
};

/* Determines the winner given 2 different numbers */ 
var determineWinner = function(number1,number2){
	var winner;
	if (number2 > 21 && number1 <= 21){
		winner = "Player";
	}
	else if (number1 > 21 && number2 <= 21){
		winner = "Computer";
	}
	else if (number1 == number2 || number1 > 21 && number2 > 21){
		winner = "Tie";
	}
	else{
		if (21 - number2 < 21 - number1){
			winner = "Computer";
		}
		else if (21 - number1 < 21 - number2){
			winner = "Player";
		}
	}
	return winner;
};


/* Helper method to produce a string representation of given hand */ 
var cardString = function(hand){
	var handString = "";
	for (var i = 0; i <hand.length;i++){
		handString += hand[i].suit + hand[i].face + " ";

	}
	return handString;
};

/* Interactive game between computer and player */
var main = function(){
	var deck = shuffle(generateCards());

	while (deck.length>26){
		var playerHand = []; 
		var computerHand = []; 

		// Deal 2 cards to the player and computer
		playerHand.push(deck.pop());
		playerHand.push(deck.pop());

		computerHand.push(deck.pop());
		computerHand.push(deck.pop());
		
		
		console.log("Your hand is ", cardString(playerHand), "with a total of", calculateHand(playerHand));
		
		var letter = readlineSync.question('type h to (h)it or s to (s)tay: ');

		var game = true;
		while (game == true){
			var bust = false; 
			while (bust == false && letter == "h"){
					playerHand.push(deck.pop()); 
					if (calculateHand(playerHand)>=21){
						bust = true;
						break;
					}
					console.log("Your hand:", cardString(playerHand), "(" + calculateHand(playerHand) + ")");					
					var letter = readlineSync.question('type h to (h)it or s to (s)tay: ');
				
			}
			

			if (letter == "s" || bust == true) {
				while (calculateHand(computerHand)<17){
					computerHand.push(deck.pop());
				}
				console.log("Your hand:" + cardString(playerHand) + "(" + calculateHand(playerHand) + "),","Computer hand:"+ cardString(computerHand)+ "(" + calculateHand(computerHand) + ")");
				game = false;
			}
		}
		var winner = determineWinner(calculateHand(playerHand), calculateHand(computerHand)); 
		if (winner == "Tie"){
			console.log("Tie!\n");

		}
		else{
			console.log(winner,"Wins\n");
		}

		console.log("There are", deck.length, "cards left in the deck");
		console.log("-----------------------\n")

	}

	console.log("Less than 26 cards left. Game over!");

};

main();

