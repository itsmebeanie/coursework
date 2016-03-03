// when play now is clicked
document.getElementById("startButton").addEventListener("click",generateGameBoard);

// getters
function getNumSymbols(){
	// number of symbols
	var numSymbols = Number(document.getElementById("numSymbols").value);

	// ensure max
	if (numSymbols > 8){
		numSymbols = 8;
	}
	return numSymbols; 
}


/* Shuffled a given array*/
function shuffle(Array){
	var shuffled = [];
	var length = Array.length;
	while (length > 0){
		var i = parseInt(Math.random()*length,10);
		shuffled.push(Array[i]);;
		Array.splice(i,1);
		length--;
	}
	return shuffled;
}


function getGameSymbols(){
	// symbols in the game
	var numSymbols = getNumSymbols();
	//make visible

	// look up table: max 8 symbols
	var symbolLookUp = ["🍕","🍆","🍑", "🍍", "🍇", "🍙","🍒", "🍜" ];
	var gameSymbols = [];
	while (numSymbols > 0){
		var random = parseInt(Math.random()*symbolLookUp.length,10);
		gameSymbols.push(symbolLookUp[random]);
		gameSymbols.push(symbolLookUp[random]);
		numSymbols--;
	}


	return gameSymbols;
}

function removeForm(field){
	var element = document.getElementById(field);
	element.parentNode.removeChild(element);
}


function generateGameBoard(){
	var numSymbols = getNumSymbols();
	var clicks = 0;
	var guesses = 0;
	var matches = 0;
	var pairs = [];
	var pair1;
	var pair2;
	var onClick = function(event){
		var current = this;
		this.classList.add("clicked");
		this.classList.remove("data");

		pairs.push(current);
		var change = document.getElementById("g");
		//console.log(change);
		g.textContent = guesses;
		current.childNodes[0].style.visibility = "visible";


		if (clicks%2 === 0){
			guesses = guesses + 1;
		}

		clicks++;

		// compare the elements
		if (pairs.length == 2){
			pair1 = pairs.pop();
			pair2 = pairs.pop();
			//console.log(pair1.textContent);
			//console.log(pair2.textContent);

			if (pair1.textContent === pair2.textContent){
				//console.log("yo these match");
				pair1.childNodes[0].style.visibility = "visible";
				pair2.childNodes[0].style.visibility = "visible";

				
				pair1.removeEventListener('click', onClick);
				pair2.removeEventListener('click', onClick);

				matches++;

				if (matches === numSymbols){
					var winning = document.createElement("p");
					var content = document.createTextNode("You're d☺ne! Thx for playing");
					setTimeout(function(){
						removeForm("board");
						winning.appendChild(content);
						winning.classList.add("winText");
						body.appendChild(winning);
					}, 1200);
				}


			}
			else {
				setTimeout(function(){
					pair1.classList.remove("clicked");
					pair2.classList.remove("clicked");
					pair1.classList.add("data");
					pair2.classList.add("data");
					pair1.childNodes[0].style.visibility = "hidden";
					pair2.childNodes[0].style.visibility = "hidden";
				
				}, 1000);
			}
		}
		
	}

	// create table dimensions
	var row;
	var column;
	var totalCards = numSymbols *2;
	var gameSymbols = shuffle(getGameSymbols());

	// generate table based on elements

	// if the cards can be generated into a square
	// 4 and 16 should be squares
	if (totalCards === 16){
		row = 4;
		column = 4;
	}
	else if (totalCards === 4){
		row = 2;
		column = 2;
	}
	else if (totalCards ===  12){
		row = 3;
		column = 4;
	}
	else {
		row = 2;
		column = numSymbols;
	}

	// target body html
	var body = document.getElementsByTagName("body")[0];

	// Create display for number of guesses
	var guessP = document.createElement('p');
	var guessNum = document.createElement('span');
	guessNum.id = "g"
	var beg = document.createTextNode("#?'s: ");
	var end = document.createTextNode(guesses);
	guessNum.appendChild(end);
	guessNum.classList.add("guess");
	guessP.appendChild(beg);
	guessP.appendChild(guessNum);
	// Create table for symbols
	var symbolTable = document.createElement("table");
	symbolTable.id = "board";
	symbolTable.classList.add("grid");

	// so that i can remove this form when user wins

	for(var i= 0; i<row; i++){
		var tr = document.createElement("tr");
		for (var j = 0; j < column; j++){
			var td = document.createElement("td");
			td.classList.add("data");
			var span = document.createElement("span");
			span.classList.add("elements");
			span.style.visibility = "hidden";
			var text = document.createTextNode(gameSymbols.pop());
			span.appendChild(text);
			td.appendChild(span);
			tr.appendChild(td);

			td.addEventListener("click", onClick);



	
			// append to symbol table
		}
		symbolTable.appendChild(tr);

	}
	// get info from page
	//remove form
	removeForm('startForm');


	body.appendChild(guessP);

	body.appendChild(symbolTable);
   

}