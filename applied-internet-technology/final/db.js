var mongoose = require('mongoose');
  //URLSlugs = require('mongoose-url-slugs');

// for the team at spotify
var User = new mongoose.Schema({
	name: String,
	website: String,
	picture: String,
	description: String
});

// feedback
var Sponsor = new mongoose.Schema({
	name: String,
});

// submitting hackathons
var Hack = new mongoose.Schema({
	name: String,
	description: String,
	url: String
});

mongoose.model('User', User);
mongoose.model('Hack', Hack);
mongoose.model('Sponsor', Sponsor)

mongoose.connect('mongodb://localhost/userdb');
