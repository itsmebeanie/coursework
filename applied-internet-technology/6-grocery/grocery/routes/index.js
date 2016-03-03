var express = require('express');
var mongoose = require('mongoose');
var router = express.Router();
var List = mongoose.model('List');
var Item = mongoose.model('Item');

/* GET home page. */
router.get('/', function(req, res){
	res.redirect('/list');
});

// grocery list 
router.get('/list',function(req,res){
	List.find(function(err,list,count){
		res.render('list',{list: list});
	});
});

// form 
router.get('/list/create', function(req,res){
	res.render('create');
});

router.post('/list/create', function(req,res){
	new List({
		name: req.body.listName,
		created: req.body.createName,
		items:[]
	}).save(function(err,list,count){
			res.redirect('/list/'+ list.slug);
	});
});

//specific grocery list
router.get('/list/:slug',function(req,res){
	List.findOne({slug:req.params.slug}, function (err,list,count){
		res.render('items', {listName:list.name, createdBy:list.created, groceryItems: list.items, slug: list.slug});
	});
});

router.post('/item/create', function(req,res){
	List.findOneAndUpdate(
		{slug: req.body.slug},
		{$push:{items: new Item({name: req.body.name,quantity: req.body.quantity,checked: false})}},
		function(err, list, count){
			res.redirect('/list/'+ req.body.slug);
		});
});


router.post('/item/check', function(req, res){
	var slug = req.body.slug;	
	// array of checked item
	var checkedItems = [];

	if (req.body.check == undefined){
		res.redirect('/list/'+ slug);

	}
	// if theres only one item
	if(typeof(req.body.check) === 'string'){
		checkedItems.push(req.body.check);
	}

	// otherwise push all the other checked items
	else{
		for(var i = 0; i < req.body.check.length; i++){
			checkedItems.push(req.body.check[i]);
		}
	}


	List.findOne({slug: slug}, function(err, list, count){
		for(var i = 0; i < list.items.length; i++){
			for(var j = 0; j < checkedItems.length; j++){
				if(list.items[i].name === checkedItems[j]){
					list.items[i].checked = true;
					list.save(function(err, list, count){});
				}
			}
		}
		res.redirect('/list/'+ slug);
	});
});


module.exports = router;
