window.onload = function() {
	if(navigator.onLine){
		document.getElementsByTagName('body')[0].className += 'pageoffline';
	}else{
		document.getElementsByTagName('body')[0].className += 'clientoffline';
	}
	document.title='Oops!';
}