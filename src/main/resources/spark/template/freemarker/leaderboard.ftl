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
.green {
    color: lightgreen; 
}
.red {
    color: lightcoral; 
}
.grey {
    color: silver; 
}
.simbol{
	    background-repeat: no-repeat;
    background-size: auto 16px;
    max-width: 20px;
    min-height: 16px;
    min-width: 16px;
    width: 100%;
}

.Meisterschale{
	background-image: url("http://classic.comunio.de/getImg.phtml?cid=1");
}

.ManagerDesJahres{
	background-image: url("http://classic.comunio.de/getImg.phtml?cid=24");
}
.ChampionsLeague{
	background-image: url("http://classic.comunio.de/getImg.phtml?cid=5");
}
.Ligapokal{
	background-image: url("http://classic.comunio.de/getImg.phtml?cid=14");
}
.Tippkonig{
	background-image: url("http://classic.comunio.de/getImg.phtml?cid=10");
}
.Torschutzenkonig{
	max-width: 32px;
	background-image: url("http://classic.comunio.de/getImg.phtml?cid=15");
}

.Transferkonig{
	background-image: url("http://classic.comunio.de/getImg.phtml?cid=38");
}



.glyphicons-cup{
	background-image: url("http://classic.comunio.de/getImg.phtml?cid=1");
}

.glyphicons-batman{
	background-image: url("https://vignette3.wikia.nocookie.net/batmantheanimatedseries/images/6/64/Favicon.ico/revision/latest?cb=20130302054027");
}
.glyphicons-centipede{
	background-image: url("https://www.pokewiki.de/images/2/20/Pok%C3%A9monicon_010.png");
}
</style>

</head>
<body>
	<div class="col-xs-12">
		<h1>Spieltag ${matchday.number}</h1>
	</div>
	<div class="col-xs-7">
		<div class="">
		
		<#list divisionalTables as item>
    <#assign division = item.getKey()/>
    <#assign table = item.getValue()/>
		
			<table class="table table-striped">
				<thead>
					<tr>
						<th width="85%">${division.description} Division <span class="glyphicon simbol ${division.icon}"></span></th>
						
						<th>Punkte</th>
						<th>Tore</th>


					</tr>
				</thead>
				<tbody>
				<#list table as x>
					<tr>
						<td>${x?counter}. ${x.trainer.name}
						<#list x.trainer.archivments as a>
							<span class="glyphicon simbol ${a}"></span>
						</#list>
						</td>
						
						<td>${x.points}</td>
						
						
						<td>${x.goals} : ${x.goalsAgainst}</td>


					</tr>
					
					</#list>
				</tbody>
			</table>
</#list>
			

		</div>
	</div>


	<div class="col-xs-5">
		<div class="">
			<table class="table table-striped">
				<thead>
					<tr>
						<th width="85%">Unsere Tabelle</th>
						<th></th>
						<th>Punkte</th>

					</tr>
				</thead>
				<tbody>
				<#list allTimeTable as x>
					<tr>
						<td>${x?counter}. ${x.trainer.name}</td> 
						<td>
							<#if x.better == 0>
								<!-- gleich gut -->
							<#elseif x.better lt 0>
								<!-- schlechter -->
								<span class="glyphicon glyphicon-arrow-down red"></span>
							<#elseif x.better gt 0 >
								<!-- besser -->
								<span class="glyphicon glyphicon-arrow-up green"></span>
							</#if>
						</td>

						<td>${x.points} <span class="grey">(+${x.betterPoints})</span></td>

					</tr>
					</#list>
				</tbody>
			</table>
		</div>
	</div>


	<div class="col-xs-12">
		<h2>Spielstand</h2>
	</div>

	<div class="col-xs-7">
		<div class="">
			<table class="table table-striped">
				<tbody>
					<#list results as x>
						<tr>
							<td>
								${x.home.name}
							</td>
							<td>
								${x.homeGoals}:${x.guestGoals}
							</td>
							<td>
								${x.guest.name}
							</td>
						</tr>
					</#list>
				</tbody>
			</table>
		</div>
	</div>


	




</body>
</html>
