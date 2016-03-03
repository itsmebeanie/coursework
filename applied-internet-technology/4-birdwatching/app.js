/* Dependencies */
var express = require('express');
var app = express();

/* Enabling static files  */ 
var path = require("path");
var publicPath = path.resolve(__dirname, "public");
app.use(express.static(publicPath));

var bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({extended: false}));

var handlebars = require('express-handlebars').create({defaultLayout:'main'});

var session = require('express-session');
app.engine('handlebars', handlebars.engine);
app.set('view engine', 'handlebars');

 
var sessionOptions = {
	secret: 'secret cookie thang',
	resave: true,
	saveUninitialized: true
};

app.use(session(sessionOptions));

//store birdwatching data in a global array of objects
var birdsArray = new Array({name:"Bald Eagle", number:3}, {name: "Yellow Billed Duck", number: 7}, {name:"Great Cormorant", number:4});

/* find birds based on number */
function findBirds(num){
	var temp = [];
	for(var i = 0; i< birdsArray.length; i++){
		if(birdsArray[i].number >= num){
			temp.push(birdsArray[i]);
		}
	};
	return temp;
}

/* add to bird list */
function filter(bird){
	var alreadyThere = false;
	for (var i = 0; i<birdsArray.length;i++){
		if (birdsArray[i].name === bird){
			birdsArray[i].number++;
			alreadyThere = true;
		}
	}
	if (alreadyThere === false){
		birdsArray.push({name:bird, number:1});
	}
}

/* home page */
app.get('/', function(req,res){
	res.render('index',{listUrl:'/birds', settingUrl:'/settings'});
});

/* middle ware function for logging*/ 
app.use(function(req,res,next){
	console.log(req.method,req.path);
	console.log("=====");
	console.log("req.body: ",req.body);
	if (req.session.min != undefined){
		console.log("req.session.min: ",req.session.min);
	}
	next();

});
/* bird list */ 
app.get('/birds',function(req,res){
	req.session.birdsArray = findBirds(req.session.min);
	var birds = birdsArray;
	if (req.session.birdsArray.length != 0){
		birds = req.session.birdsArray;
	}
	res.render('birds',{'name':birds,'settingUrl':'/settings','add': 'Add Another Bird'});
	
});


app.post('/birds', function(req,res){
	filter(req.body.name);
	req.session.name = req.body.name;
	res.redirect('/birds');
});


app.use(function(req,res,next){
	if (req.session.min === undefined){
		console.log("req.session.min: ", req.session.min);
	}
	//otherwise the session min is defined and is printed
	next();

});

/*settings */
app.get('/settings', function(req,res){
	res.render('settings',{'min': req.session.min, 'birdUrl':'/birds','value':req.session.min, 'add': 'Set It!'});
});

app.post('/settings',function(req,res){
	req.session.min = req.body.min;
	res.redirect('/birds');
	req.session.birdsArray = findBirds(req.session.min);

});

app.listen(3000);
console.log('Started server on port 3000');