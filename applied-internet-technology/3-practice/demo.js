// demo.js
var http = require('http');
var port = 3000;
var fs = require('fs');
http.createServer(handleRequest).listen(port);
console.log("Starting server on " + port);

function serveStatic (res, path, contentType, resCode){
	fs.readFile(path,function(err,data){
		if (err){
			res.writeHead(500,{'Content-Type':'text/plain'});
			res.end('500 - Internal Error');
		}
		else{
			res.writeHead(resCode,{'Content-Type': contentType});
			res.end(data);
		}
	});
}


function handleRequest(req,res){
	var date = new Date();
	date = date.toString();
	var path = req.url.replace(/\/?(?:\?.*)?$/, '').toLowerCase(); 
	if (path === '' || path === '/home'){
		serveStatic(res, './public/index.html','text/html', 200);
		console.log(date.toLocaleString() + " " + req.method + " " + req.url +" " + res.statusCode + " " + http.STATUS_CODES[res.statusCode]);

	}
	else if (path === '/about'){
		serveStatic(res, './public/about.html','text/html',200);
		console.log(date.toLocaleString() + " " + req.method + " " + req.url +" " + res.statusCode + " " + http.STATUS_CODES[res.statusCode]);

	}

	else if (path === '/me'){
		res.writeHead(301, {'Location':'/about'});
		res.end();
		console.log(date.toLocaleString() + " " + req.method + " " + req.url +" " + res.statusCode + " " + http.STATUS_CODES[res.statusCode]);

	}

	else if (path === '/img/image1.png'){
		serveStatic(res, './public/img/image1.png','image/png',200);
		console.log(date.toLocaleString() + " " + req.method + " " + req.url +" " + res.statusCode + " " + http.STATUS_CODES[res.statusCode]);

	}

	else if (path === '/img/image2.png'){
		serveStatic(res, './public/img/image2.png','image/png',200);
		console.log(date.toLocaleString() + " " + req.method + " " + req.url +" " + res.statusCode + " " + http.STATUS_CODES[res.statusCode]);

	}
	else if (path === "/css/base.css"){
		serveStatic(res, './public/css/base.css','text/css',200);
		console.log(date.toLocaleString() + " " + req.method + " " + req.url +" " + res.statusCode + " " + http.STATUS_CODES[res.statusCode]);

	}
	else { 

		serveStatic(res,'./public/404.html','text/html',404);
		console.log(date.toLocaleString() + " " + req.method + " " + req.url +" 404 Not Found");

	}
	

}
