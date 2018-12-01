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



.glyphicons-1{
	background-image: url("https://image.flaticon.com/icons/png/128/1152/1152810.png");
}

.glyphicons-2{
	background-image: url("https://img.icons8.com/cotton/2x/olympic-medal-silver.png");
}
.glyphicons-3{
	background-image: url("http://www.theverylittlewar.com/images/classement/medaillebronze.png");
}

.pos_3>td{
border-bottom:35px solid white;

}

</style>

</head>
<body>
	<div class="col-xs-12">
		<h1>Spieltag ${matchday.number}</h1>
	</div>
	
	<div class="col-xs-12">
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
					<tr class="pos_${x?counter}">
						<td>
						<#if x?counter == 1>
						<span class="glyphicon simbol glyphicons-1"></span>
						<#elseif x?counter == 2>
						<span class="glyphicon simbol glyphicons-2"></span>
						<#elseif x?counter == 3>
						<span class="glyphicon simbol glyphicons-3"></span>
						<#else>
						${x?counter}.
						</#if>
						 ${x.trainer.name}</td> 
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
</body>
</html>
