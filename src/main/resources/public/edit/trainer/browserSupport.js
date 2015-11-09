if(!localStorage){
	var localStorage = {};
	localStorage.setItem = function(aKey,aValue){};
	localStorage.getItem = function(aKey){};
	localStorage.removeItem = function(aKey){};
}
if(!console){
	var console = {};
	console.log = function(aValue){};
}
