var express = require('express');
var request = require('request');
var mongoose = require('mongoose');

var User = mongoose.model('User');
var Hack = mongoose.model('Hack');
var Sponsor = mongoose.model('Sponsor');


var router = express.Router();
/*Replace this with db*/
var sponsors = ['arup','controlgroup','etsy','mailchimp','mongodb','pegg','spotify']; 
router.get('/', function(req, res, next) {
	res.render('index',{layout:"other.hbs"});
});

router.get('/home',function(req,res){
	res.render('home');
});
// about
router.get('/about',function(req,res){
	User.find({}, function(err,userObj,count){
		console.log(userObj);
		res.render('about',{user: userObj});
	});
});



router.get('/rsvp',function(req,res){
	var object;
	request("https://www.eventbriteapi.com/v3/users/me/owned_events/?token=CPV5FGIESFCIJLRN5IQQ",function(error, response,body){
	object = JSON.parse(body);
	console.log(object.events[0]);
		res.render('rsvp', {events:object.events, coming:object.events[0]});

	});

});

// Generate hack
router.get('/submit', function(req,res){
	Hack.find(function(err,hack,count){
	res.render('submit',{hack: hack});
	});
});

router.post('/submit',function(req,res){
	new Hack({
		name: req.body.name,
		description: req.body.description,
		url: req.body.address

	}).save(function (err,hack,count){
		res.redirect('/submit')
	});

});

router.get('/sponsors',function(req,res){
	Sponsor.find({}, function(err,sponsors,count){
		console.log(sponsors);
		res.render('sponsors',{sponsors: sponsors});
	});
});

router.get('/contact', function(req,res){
	res.render('contact');
});

module.exports = router;
