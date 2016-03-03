var request = require('request');

console.log("Start");

request('https://www.eventbriteapi.com/v3/users/me/?token=CPV5FGIESFCIJLRN5IQQ
', function (error, response, body) {
   	handleResponse(JSON.parse(body));
});