/* Dependencies */
var mongoose = require('mongoose');
var express = require('express');
var app = express();

/* Enabling static files  */ 
var path = require("path");
var publicPath = path.resolve(__dirname, "public");
app.use(express.static(publicPath));


var handlebars = require('express-handlebars').create({defaultLayout:'main'});
app.engine('handlebars', handlebars.engine);
app.set('view engine', 'handlebars');

require( './db' );

var Movie = mongoose.model('movie');

var filtered = "";
app.get('/movies', function(req,res){
	Movie.find(function(err, moviesobj, count) {
		if (req.query.director == "" || req.query.director  === undefined){
			res.render('movies', {'movie': moviesobj});
		}
		else {
			filtered = moviesobj.filter(function(Movie){
				return req.query.director === Movie.director;
			});
			res.render('movies', {'movie': filtered});
		}
	});
 });

app.listen(3000);
console.log('Started server on port 3000');