<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8"></meta>
<meta name="viewport" content="width=device-width, initial-scale=1"></meta>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
	integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7"
	crossorigin="anonymous"></link>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css"
	integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r"
	crossorigin="anonymous"></link>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
	</link>



<!-- Latest compiled and minified JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
	integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
	crossorigin="anonymous"></script>

<style>
.overview {
	text-align: center;
}

.result {
	display: inline-flex;
}

.result img {
	max-height: 9rem;
	max-width:9rem;
}

.gameresult {
	font-size: 3rem;
}

.trainer {
	text-transform: uppercase;
	display: block;
}

@media print {
	.noprint {
		display: none;
	}
}
</style>
</head>
<body>
    <div class="panel-body" ng-app="myApp" ng-controller="customersCtrl">
        <div class="row">
            <div class="col-sm-12 col-md-4">
                <div class="thumbnail">
                    <div class="caption overview">
                        <h2>${x.displayValue}</h2>
                        <div class="result">
                            <p>
                                <span><img src="${x.home.imageUrl}"></img></span>
                            </p>
                            <p>
                                <span class="trainer">${x.home.name}</span>
                                <span class="gameresult">${x.homeGoals}</span>
                                <span class="gameresult">:</span>
                                <span class="gameresult">${x.guestGoals}</span>
                                <span class="trainer">${x.guest.name}</span>
                            </p>
                            <p>
                                <span><img src="${x.guest.imageUrl}"></img></span>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
