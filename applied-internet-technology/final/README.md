Run nodemon bin/www
localhost:3000

Final Project : Monthly Music Hackathon Site
-----------
###Description 
A website created for Monthly Music Hackathon, a monthly hackathon that hosted at Spotify. This site will include information about the hackathon such as what it is about and who is involved. In addition, there will be a list of events hosted by the hackathon. This site will allow organizers to sign in and add events to the calendar. In addition, it will allow users to register for selected events.

User Stories:
-----------
###Visitor
As a site visitor, I want to be able to be informed about the Monthly Music Hackathon. I want to be able to get a sense of who the organizers are and what the hackathon is about so that I can register and participate in future events.

tl;dr: users can register for events they like
###Organizer
As an organizer, I want to be able to have access to edit the events so that I can add upcoming events.

tl;dr organizers can add events to the calendar

Sitemap:
-----------
![Site Map](sitemap/mmhtemplate.png?raw=true "sitemap")

Wireframe:
-----------
### Home
![Home](wireframe/about.png?raw=true "home")
### RSVP
![RSVP](wireframe/rsvp.png?raw=true "rsvp")
### 2015 Season
![2015 Season](wireframe/2015season.png?raw=true "2015season")
### Sponsors
![Sponsors](wireframe/sponsors.png?raw=true "sponsors")
### Contact Page
![Contact](wireframe/contact.png?raw=true "contact")
##Team Page
![Team](wireframe/teamexample.png?raw=true "teamexample")

Mongoose Draft 1:
-----------
```javascript

var User = new mongoose.Schema({
	// username, password provided by plugin
	lists:  [{ type: mongoose.Schema.Types.ObjectId, ref: 'Event' }]
});

var RegisteredUser = new mongoose.Schema({
	name: {type: String, required: true},
	email: {type: String, required: true},
});

var TeamMember = new mongoose.Schema({
	name: {type: String, required: true},
	bio: {type: String, required: true},
	website: {type: String, required: true},
	contact: {type: String, required: true},
});

var Event = new mongoose.Schema({
	user: {type: mongoose.Schema.Types.ObjectId, ref:'User'},
  	name: {type: String, required: true},
    date: {type: Date, required: true},
    description: {type: String, required: true}
});

var Calender = new mongoose.Schema({
	name: {type: String, required: true},
    year: {type: Number, required: true},
	events: [Event]
});

TeamMember.plugin(URLSlugs('name'));

mongoose.model('Calendar', Calendar);
mongoose.model('Event', Event);
mongoose.model('RegisteredUser', RegisteredUser);
mongoose.model('User', User);

mongoose.connect('mongodb://localhost/mmhdb');

```

Express Routes Draft 1:
-----------
```
var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.redirect('about');
});

// about
router.get('/about',function(req,res){
	
});

// form 
router.get('/rsvp', function(req,res){
});

router.post('/rsvp', function(req,res){

});

// form for organizers 

router.get('/login', function(req,res){
});

router.post('/2015season', function(req,res){

});

router.get('/2015season', function(req,res){

});

router.get('/contact', function(req,res){
});

router.get('/team', function(req,res){

});

router.get('/team/:slug', function(req,res){

});

module.exports = router;


```


Modules / Concepts (8 Point Totals):
-----------
### Bootstrap (1 point)
Bootstrap is a HTML, CSS and JS framework. I will use it for front-end development. 

### User Authentication (3 points)
I will be using passport, an authentication middle ware for Node.js, to allow for user authentication. It is important to use so that organizers of the event can update the calendar of events. It will be a challenge to limit login for organizers.

### JSHint (1 Point)
JSHint is a JavaScript tool that helps to detect errors and potentital problems in JavaScript code. I will use JSHint for debugging my code. I think that this will allow for better quality control.  

### Unit Testing (2 points)
I will use Mocha or Jasmine for Unit Testing so that I can get accurate reporting on my code through my development process. Jasmine is a testing framework that follows behavior-driven development whereas Mocha framework that seems to be mostly Test Driven. In terms of theoretical underpnnings, I am unsure of whether or not it is more appropriate for me to use Behavior-Driven JavaScript or Test-Driven Javascript.

### Pre-built Express project templates (1 point)
I will use express-generator to create a bare-bones application. It will be useful to template my project for organization. 

### Integrate Visual Effects (1 point)
I want to use a variation of visual effects to make my website more interactive. I want to use Snap.svg to create graphics that will scale for screen size and three.js to create 3D graphics. It will be difficult to decide where they will go. 
