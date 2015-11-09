var roblink = {};
roblink.data={};
roblink.edit={};
	
	roblink.attributeChange= function (aBase,aAttribute, aValue){
		var tempValue = aValue;
		if(aAttribute === 'password'){
			//TODO : do md5 or other stuff;
			tempValue = aValue;
		}
		aBase[aAttribute] = tempValue;
	}


	roblink.clearBody = function () {
		$('body').removeClass('dialogIsOpen');
		$('body').removeClass('loadingIsOpen');
		$('body').removeClass('removeDialogIsOpen');
		$('body').removeClass('productDialogIsOpen');
		$('body').removeClass('errorMessageIsOpen');
		$('body').removeClass('loginDialogIsOpen');
	};


	roblink.doProductDialog = function () {		
    	$('body').toggleClass('dialogIsOpen');
		$('body').toggleClass('productDialogIsOpen');
	};
	
	roblink.doErrorMessage = function () {
    	$('body').toggleClass('dialogIsOpen');
		$('body').toggleClass('errorMessageIsOpen');
	};
	
	roblink.doLoadingMessage = function () {
    	$('body').toggleClass('dialogIsOpen');
		$('body').toggleClass('loadingIsOpen');
	};
	
	roblink.clone = function(aObject){
		var json = JSON.stringify(aObject);
		return JSON.parse(json);
	};
	roblink.modify= function(aID){
		$(roblink.data).each(function(tempIndex,tempEntry){
			if(tempEntry.id === aID){
				roblink.edit = roblink.clone(tempEntry);
			}
		});
		roblink.doProductDialog();
	};
	roblink.add= function(){
		roblink.edit = {};
		roblink.edit.id = 'new';
		roblink.doProductDialog();
	};


	
	roblink.initContent = function(json){
		var data = JSON.parse(json);
		roblink.data = data.data;
		var tbody = $('#tbody');
		tbody.html('');

		$(roblink.data).each(function(tempIndex,tempEntry){
			var row = $('<tr/>');
			var cell = $('<td/>');
			cell.html(tempEntry.number);
			cell.appendTo(row);
			
			var cell = $('<td/>');
			cell.html(tempEntry.processed);
			cell.appendTo(row);
			

			
			row.appendTo(tbody);
		});

		
	};
	
	roblink.save = function save(aValue, aOnSuccess, aOnError){
		$.ajax({
			method: "put",
			url: "/matchday/"+aValue.id,
			data : JSON.stringify(aValue)
		}).done(aOnSuccess);
	};
	
$( document ).ready(function() {
	
	$.ajax({
		method: "get",
		url: "/matchday"
	}).done(function( msg ) {
		roblink.initContent(msg);
	});
	
	$('.cancel').on('click', roblink.clearBody);
	$('.add').on('click', roblink.add);
	
	$('#observbutton').on('click', function (){
    	roblink.clearBody();
		roblink.doLoadingMessage();
		var onSuccess = function(json){
			window.location.reload(true);
		};
		roblink.save(roblink.edit,onSuccess,roblink.doErrorMessage);
	});
	
	
});

