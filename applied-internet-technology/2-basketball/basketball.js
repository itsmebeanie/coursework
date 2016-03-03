var request = require('request');
console.log("Start");
request('http://foureyes.github.io/csci-ua.0480-fall2015-001/homework/02/2014-06-15-heat-spurs.json', function (error, response, body) {
   	handleResponse(JSON.parse(body));
})

request('http://foureyes.github.io/csci-ua.0480-fall2015-001/homework/02/2014-04-09-thunder-clippers.json', function (error, response, body) {
   	handleResponse(JSON.parse(body));
})


function handleResponse(players){
	function getPlayerTeams(player){
		return player.team;
	}
	// Get unique team names 
	var teamNames = players.map(getPlayerTeams);
	var team1 = teamNames[0];
	var team2 = teamNames[teamNames.length-1];
	function inTeam(teamName){
		return function(player){return player.team === teamName;}
	}
	function finalScore(player){
		return 2*(player.fieldGoalsMade - player.threesMade) + (3*player.threesMade) + player.freeThrowsMade;
	}
	function sum(curTotal, num){
		return curTotal+num;
	}
	function total(arr){
		return arr.reduce(sum,0);
	}

	function maximum(a,b){
		return a>b;
	}
	// Final Score
	var team1Total = total(players.filter(inTeam(team1)).map(finalScore));
	var team2Total = total(players.filter(inTeam(team2)).map(finalScore));

	console.log("Final Score: " +  team1 + " " + team1Total + ", " + team2 + " " + team2Total);
	console.log("=====")
	// Player with Highest Percentage'
	var max = 0;
	var playerM = "";

	players.forEach(function greaterthan10(player){
	var playerScore = finalScore(player);
	var threePercent = (3*player.threesMade/playerScore) * 100
	if (playerScore >10){
		if (threePercent>max){
			max = threePercent;
			playerM = player.name;
		}
	}
	});

	console.log("*Player with highest percentage of points from three pointers " + playerM);

	// Team with Most Rebounds
	function rebound(player){
		return player.defensiveRebounds +player.offensiveRebounds;
	}

	var team1Rebounds = total(players.filter(inTeam(team1)).map(rebound));
	var team2Rebounds = total(players.filter(inTeam(team2)).map(rebound));

	var maxRebounds = maximum(team1Rebounds,team2Rebounds);
	console.log(maxRebounds ? team1 + " with " + team1Rebounds : team2 + " with " + team2Rebounds);


	//Non Guard Player with Most Assists 

	function isGuard(player){
		return player.position !== 'G';
	}
	var nonGuardPlayers = players.filter(isGuard);
	var maxAssists = 0; 
	var playerMostAssists = "";
	nonGuardPlayers.forEach(function mostAssists(player){
		if (player.assists>maxAssists){
			maxAssists = player.assists;
			playerMostAssists = player.name;
		}
	});
	console.log ("*Non guard player with most assists: " + playerMostAssists + " with " + maxAssists);

	// Players with More Turnovers than Assists
	function moreTurnovers(player){
		return player.turnovers>player.assists;
	}

	function playerName (player){
		return player.name;
	}

	mostTurnovers = players.filter(moreTurnovers).map(playerName);
	console.log("*Players with more turnovers than assists");
	mostTurnovers.forEach(function x(a){
		console.log(a);
	})
}
console.log("Done!");


// Hardcoding a JSON string

function basketballgame_heatvspurs(){
	var players = JSON.parse('[{"position": "F", "freeThrowsAttempted": 9, "defensiveRebounds": 9, "offensiveRebounds": 1, "threesMade": 3, "turnovers": 1, "freeThrowsMade": 8, "assists": 5, "threesAttempted": 9, "fieldGoalsMade": 10, "fieldGoalsAttempted": 21, "name": "L. James", "steals": 0, "team": "Heat"}, {"position": "F", "freeThrowsAttempted": 0, "defensiveRebounds": 2, "offensiveRebounds": 0, "threesMade": 1, "turnovers": 0, "freeThrowsMade": 0, "assists": 1, "threesAttempted": 2, "fieldGoalsMade": 1, "fieldGoalsAttempted": 2, "name": "R. Lewis", "steals": 0, "team": "Heat"}, {"position": "C", "freeThrowsAttempted": 2, "defensiveRebounds": 7, "offensiveRebounds": 0, "threesMade": 0, "turnovers": 0, "freeThrowsMade": 1, "assists": 2, "threesAttempted": 5, "fieldGoalsMade": 6, "fieldGoalsAttempted": 14, "name": "C. Bosh", "steals": 1, "team": "Heat"}, {"position": "G", "freeThrowsAttempted": 4, "defensiveRebounds": 3, "offensiveRebounds": 0, "threesMade": 1, "turnovers": 3, "freeThrowsMade": 2, "assists": 1, "threesAttempted": 2, "fieldGoalsMade": 4, "fieldGoalsAttempted": 12, "name": "D. Wade", "steals": 0, "team": "Heat"}, {"position": "G", "freeThrowsAttempted": 2, "defensiveRebounds": 4, "offensiveRebounds": 1, "threesMade": 1, "turnovers": 4, "freeThrowsMade": 2, "assists": 2, "threesAttempted": 3, "fieldGoalsMade": 1, "fieldGoalsAttempted": 8, "name": "R. Allen", "steals": 1, "team": "Heat"}, {"position": "F", "freeThrowsAttempted": 0, "defensiveRebounds": 0, "offensiveRebounds": 0, "threesMade": 0, "turnovers": 1, "freeThrowsMade": 0, "assists": 0, "threesAttempted": 0, "fieldGoalsMade": 0, "fieldGoalsAttempted": 0, "name": "S. Battier", "steals": 1, "team": "Heat"}, {"position": "C", "freeThrowsAttempted": 0, "defensiveRebounds": 5, "offensiveRebounds": 1, "threesMade": 0, "turnovers": 0, "freeThrowsMade": 0, "assists": 0, "threesAttempted": 0, "fieldGoalsMade": 0, "fieldGoalsAttempted": 1, "name": "C. Andersen", "steals": 1, "team": "Heat"}, {"position": "G", "freeThrowsAttempted": 2, "defensiveRebounds": 1, "offensiveRebounds": 0, "threesMade": 0, "turnovers": 0, "freeThrowsMade": 2, "assists": 1, "threesAttempted": 1, "fieldGoalsMade": 0, "fieldGoalsAttempted": 2, "name": "N. Cole", "steals": 0, "team": "Heat"}, {"position": "F", "freeThrowsAttempted": 0, "defensiveRebounds": 1, "offensiveRebounds": 0, "threesMade": 0, "turnovers": 0, "freeThrowsMade": 0, "assists": 0, "threesAttempted": 0, "fieldGoalsMade": 1, "fieldGoalsAttempted": 2, "name": "U. Haslem", "steals": 0, "team": "Heat"}, {"position": "F", "freeThrowsAttempted": 3, "defensiveRebounds": 1, "offensiveRebounds": 2, "threesMade": 0, "turnovers": 1, "freeThrowsMade": 1, "assists": 1, "threesAttempted": 0, "fieldGoalsMade": 4, "fieldGoalsAttempted": 7, "name": "M. Beasley", "steals": 0, "team": "Heat"}, {"position": "G", "freeThrowsAttempted": 5, "defensiveRebounds": 1, "offensiveRebounds": 0, "threesMade": 0, "turnovers": 0, "freeThrowsMade": 4, "assists": 0, "threesAttempted": 0, "fieldGoalsMade": 2, "fieldGoalsAttempted": 3, "name": "M. Chalmers", "steals": 1, "team": "Heat"}, {"position": "F", "freeThrowsAttempted": 0, "defensiveRebounds": 1, "offensiveRebounds": 0, "threesMade": 0, "turnovers": 0, "freeThrowsMade": 0, "assists": 0, "threesAttempted": 1, "fieldGoalsMade": 0, "fieldGoalsAttempted": 1, "name": "J. Jones", "steals": 0, "team": "Heat"}, {"position": "G", "freeThrowsAttempted": 0, "defensiveRebounds": 1, "offensiveRebounds": 0, "threesMade": 1, "turnovers": 0, "freeThrowsMade": 0, "assists": 1, "threesAttempted": 2, "fieldGoalsMade": 1, "fieldGoalsAttempted": 2, "name": "T. Douglas", "steals": 0, "team": "Heat"}, {"position": "F", "freeThrowsAttempted": 6, "defensiveRebounds": 8, "offensiveRebounds": 2, "threesMade": 3, "turnovers": 3, "freeThrowsMade": 5, "assists": 2, "threesAttempted": 4, "fieldGoalsMade": 7, "fieldGoalsAttempted": 10, "name": "K. Leonard", "steals": 1, "team": "Spurs"}, {"position": "F", "freeThrowsAttempted": 6, "defensiveRebounds": 6, "offensiveRebounds": 2, "threesMade": 0, "turnovers": 0, "freeThrowsMade": 4, "assists": 2, "threesAttempted": 0, "fieldGoalsMade": 5, "fieldGoalsAttempted": 10, "name": "T. Duncan", "steals": 0, "team": "Spurs"}, {"position": "C", "freeThrowsAttempted": 0, "defensiveRebounds": 8, "offensiveRebounds": 1, "threesMade": 1, "turnovers": 2, "freeThrowsMade": 0, "assists": 6, "threesAttempted": 3, "fieldGoalsMade": 2, "fieldGoalsAttempted": 7, "name": "B. Diaw", "steals": 1, "team": "Spurs"}, {"position": "G", "freeThrowsAttempted": 0, "defensiveRebounds": 2, "offensiveRebounds": 0, "threesMade": 0, "turnovers": 1, "freeThrowsMade": 0, "assists": 2, "threesAttempted": 3, "fieldGoalsMade": 0, "fieldGoalsAttempted": 5, "name": "D. Green", "steals": 2, "team": "Spurs"}, {"position": "G", "freeThrowsAttempted": 2, "defensiveRebounds": 1, "offensiveRebounds": 0, "threesMade": 0, "turnovers": 0, "freeThrowsMade": 2, "assists": 2, "threesAttempted": 1, "fieldGoalsMade": 7, "fieldGoalsAttempted": 18, "name": "T. Parker", "steals": 0, "team": "Spurs"}, {"position": "G", "freeThrowsAttempted": 5, "defensiveRebounds": 4, "offensiveRebounds": 0, "threesMade": 3, "turnovers": 2, "freeThrowsMade": 4, "assists": 4, "threesAttempted": 6, "fieldGoalsMade": 6, "fieldGoalsAttempted": 11, "name": "M. Ginobili", "steals": 0, "team": "Spurs"}, {"position": "C", "freeThrowsAttempted": 2, "defensiveRebounds": 2, "offensiveRebounds": 0, "threesMade": 0, "turnovers": 0, "freeThrowsMade": 1, "assists": 2, "threesAttempted": 0, "fieldGoalsMade": 1, "fieldGoalsAttempted": 1, "name": "T. Splitter", "steals": 1, "team": "Spurs"}, {"position": "G", "freeThrowsAttempted": 0, "defensiveRebounds": 1, "offensiveRebounds": 0, "threesMade": 5, "turnovers": 0, "freeThrowsMade": 0, "assists": 2, "threesAttempted": 8, "fieldGoalsMade": 6, "fieldGoalsAttempted": 10, "name": "P. Mills", "steals": 0, "team": "Spurs"}, {"position": "F", "freeThrowsAttempted": 0, "defensiveRebounds": 0, "offensiveRebounds": 0, "threesMade": 0, "turnovers": 0, "freeThrowsMade": 0, "assists": 2, "threesAttempted": 0, "fieldGoalsMade": 0, "fieldGoalsAttempted": 0, "name": "M. Bonner", "steals": 0, "team": "Spurs"}, {"position": "G", "freeThrowsAttempted": 0, "defensiveRebounds": 2, "offensiveRebounds": 0, "threesMade": 0, "turnovers": 0, "freeThrowsMade": 0, "assists": 1, "threesAttempted": 0, "fieldGoalsMade": 2, "fieldGoalsAttempted": 3, "name": "M. Belinelli", "steals": 0, "team": "Spurs"}, {"position": "C", "freeThrowsAttempted": 0, "defensiveRebounds": 0, "offensiveRebounds": 0, "threesMade": 0, "turnovers": 0, "freeThrowsMade": 0, "assists": 0, "threesAttempted": 0, "fieldGoalsMade": 1, "fieldGoalsAttempted": 1, "name": "J. Ayres", "steals": 0, "team": "Spurs"}, {"position": "G", "freeThrowsAttempted": 0, "defensiveRebounds": 0, "offensiveRebounds": 0, "threesMade": 0, "turnovers": 0, "freeThrowsMade": 0, "assists": 0, "threesAttempted": 1, "fieldGoalsMade": 0, "fieldGoalsAttempted": 2, "name": "C. Joseph", "steals": 0, "team": "Spurs"}, {"position": "F", "freeThrowsAttempted": 2, "defensiveRebounds": 0, "offensiveRebounds": 1, "threesMade": 0, "turnovers": 0, "freeThrowsMade": 2, "assists": 0, "threesAttempted": 0, "fieldGoalsMade": 0, "fieldGoalsAttempted": 0, "name": "A. Baynes", "steals": 0, "team": "Spurs"} ]')

	function inTeam(teamName){
		return function(player){return player.team === teamName;}
	}
	function finalScore(player){
		return 2*(player.fieldGoalsMade - player.threesMade) + (3*player.threesMade) + player.freeThrowsMade;
	}
	function sum(curTotal, num){
		return curTotal+num;
	}
	function total(arr){
		return arr.reduce(sum,0);
	}
	function maximum(a,b){
		return a>b;
	}
	// Final Score
	var heatTotal = total(players.filter(inTeam('Heat')).map(finalScore));
	var spursTotal = total(players.filter(inTeam('Spurs')).map(finalScore));

	console.log("Final Score: Heat " + heatTotal + ", Spurs " + spursTotal);
	console.log("=====")
	// Player with Highest Percentage'
	var max = 0;
	var playerM = "";

	players.forEach(function greaterthan10(player){
	var playerScore = finalScore(player);
	var threePercent = (3*player.threesMade/playerScore) * 100
	if (playerScore >10){
		if (threePercent>max){
			max = threePercent;
			playerM = player.name;
		}
	}
	});

	console.log("*Player with highest percentage of points from three pointers " + playerM);

	// Team with Most Rebounds
	function rebound(player){
		return player.defensiveRebounds +player.offensiveRebounds;
	}

	var heatRebounds = total(players.filter(inTeam('Heat')).map(rebound));
	var spurRebounds = total(players.filter(inTeam('Spurs')).map(rebound));

	var maxRebounds = maximum(heatRebounds,spurRebounds);
	console.log(maxRebounds ? "Heat with " + heatRebounds : "Spur with " + spurRebounds);


	//Non Guard Player with Most Assists 

	function isGuard(player){
		return player.position !== 'G';
	}
	var nonGuardPlayers = players.filter(isGuard);
	var maxAssists = 0; 
	var playerMostAssists = "";
	nonGuardPlayers.forEach(function mostAssists(player){
		if (player.assists>maxAssists){
			maxAssists = player.assists;
			playerMostAssists = player.name;
		}
	});
	console.log ("*Non guard player with most assists: " + playerMostAssists + " with " + maxAssists);

	// Players with More Turnovers than Assists
	function moreTurnovers(player){
		return player.turnovers>player.assists;
	}

	function playerName (player){
		return player.name;
	}

	mostTurnovers = players.filter(moreTurnovers).map(playerName);
	console.log("*Players with more turnovers than assists");
	mostTurnovers.forEach(function x(a){
		console.log(a);
	});
}
