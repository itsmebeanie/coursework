var mongoose = require('mongoose');

var movie = new mongoose.Schema({
	title: String,
	year: Number,
	director: String

})

mongoose.model('movie', movie);
mongoose.connect('mongodb://localhost/hw05');