document.addEventListener('DOMContentLoaded', main);

var generateRepo;
function main() {
  var limitBtn = document.querySelector('#get-rate-limit'), 
    reposBtn = document.querySelector('#get-repos');  

  limitBtn.addEventListener('click', handleLimitClick);
  reposBtn.addEventListener('click', handleReposClick);
}

function handleLimitClick() {
  var url = 'http://api.github.com/rate_limit',
    req = new XMLHttpRequest();
  req.open('GET', url, true);
  req.addEventListener('load', handleRateLimitResponse);
  req.send();
}

function handleRateLimitResponse() {
  console.log(this.status);
  if(this.status >= 200 && this.status < 400) {
    var data = JSON.parse(this.responseText),
      pre = document.querySelector('#rate-limit'),
      msg = 'remaining: ' + data.resources.core.remaining;
    console.log(this.responseText, data, msg); 
    pre.textContent = msg;
  }
}

function handleReposClick() {
  var url = 'https://api.github.com/users/foureyes/repos',
    req = new XMLHttpRequest();
  req.open('GET', url, true);
  req.addEventListener('load', handleReposResponse);
  req.send();
}

function handleReposResponse() {
  console.log(this.status);
  if(this.status >= 200 && this.status < 400) {
    var data = JSON.parse(this.responseText),
      ul = document.querySelector('#container > ul');
      
    data.forEach(function(repo) {
      ul.appendChild(document.createElement('li')).textContent = repo.name;
    });
      
  }
}