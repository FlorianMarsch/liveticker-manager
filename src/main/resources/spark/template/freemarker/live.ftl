<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>

<script
	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
	integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7"
	crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css"
	integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">



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

		





		<div class="panel panel-default">
			<div class="panel-heading">Spieltag ${matchday.number} : ${matchday.modus}</div>
			<div class="panel-body" id="${matchday.number}">




				<div class="row">
				<#list results as x>
					<div class="col-sm-12 col-md-4">
						<div class="thumbnail">
							<div class="caption overview">
								<h2>
									<a target="_blank" href="https://twitter.com/search?q=%23${x.home.hashTag}vs${x.guest.hashTag}&src=typd&lang=de">
									#${x.home.hashTag}vs${x.guest.hashTag}
									</a>
								</h2>
								<div class="result">
									<p>
										<span><img
											src="${x.home.imageUrl}"></img></span>
									</p>
									<p>
										<span class="trainer">${x.home.name}</span>
										<span class="gameresult">${x.homeGoals}</span> <span
											class="gameresult">:</span> <span class="gameresult">${x.guestGoals}</span>
										<span class="trainer">${x.guest.name}</span>
									</p>
									<p>
										<span><img
											src="${x.guest.imageUrl}"></img></span>
									</p>
								</div>
							</div>
						</div>
					</div>
					</#list>
				</div>


			</div>
		</div>
	</div>


</body>
</html>
