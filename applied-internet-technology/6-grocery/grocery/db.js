var mongoose = require('mongoose'),
  Schema = mongoose.Schema;
  URLSlugs = require('mongoose-url-slugs');

var Item = new mongoose.Schema({
  name: String,
  quantity: Number, 
  checked: Boolean
});


var List = new mongoose.Schema({
  name: String,
  created: String,
  items: [Item]
});

List.plugin(URLSlugs('name'));


mongoose.model('List', List);
mongoose.model('Item', Item);

mongoose.connect('mongodb://localhost/grocerydb');