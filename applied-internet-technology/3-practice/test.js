// test.js
var express = require('express');
//templating
var handlebars = require('express-handlebars').create({'defaultLayout':'main'}); // gives back an object
var app = express();


var path = require('path');

app.engine('handlebars', handlebars.engine);
app.set('view engine','handlebars');

app.get('/', function(req, res){
	var x = req.headers;
	res.render('index',{heading: "Browser Test Page",obj:req.headers});
});
app.get('/about', function(req, res){
	res.render('about',{heading: "About", greeting:'This time we\'re using Express!'});
});
 app.use(function(req, res) {
     res.status(404);
     res.render('404', {heading: "404",title: '404: File Not Found'});
 });
app.listen(3000);
console.log('Started server on port 3000');