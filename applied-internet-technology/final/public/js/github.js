document.addEventListener('DOMContentLoaded', main);

function main() {

  var lookUp = document.getElementById("lookUp");  
  lookUp.addEventListener('click', handleLookUp);
}


function handleLookUp() {
    var repoName = document.getElementById("testing").value;
    console.log(repoName);
    var url = "https://api.github.com/users/";
    url = url + repoName + "/repos";
  // how do you generate a specific user??
    req = new XMLHttpRequest();
  req.open('GET', url, true);
  req.addEventListener('load', handleReposResponse);
  req.send();
}

function handleReposResponse() {
  console.log(this.status);
  if(this.status >= 200 && this.status < 400) {
    var data = JSON.parse(this.responseText),
     ul = document.querySelector('.container > ul');
    
  while (ul.firstChild) {
      ul.removeChild(ul.firstChild);
  }
  if (data[0] == null){
      ul.appendChild(document.createElement('li')).textContent = "No repos available";
  }
  else{
    data.forEach(function(repo) {
      ul.appendChild(document.createElement('li')).textContent = repo.name;
    });
  }
      
  }
  else{
         ul = document.querySelector('.container > ul');
          while (ul.firstChild) {
              ul.removeChild(ul.firstChild);
           }
        ul.appendChild(document.createElement('li')).textContent = "Repo not found";

  }
}