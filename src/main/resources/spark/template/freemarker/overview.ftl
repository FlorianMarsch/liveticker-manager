<html>

<head>
<title></title>
</head>


<body>
	<h1>Spieltag ${matchday}</h1>
	<div style="float:left">
		<div>
			<table border="0">
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
		<div>
			<table border="0">
				<tbody>
<#list events as x>
					<tr>
						<td>
							${x.event}
						</td>
						<td>
							${x.name}
						</td>
						<td>
							${x.trainer}
						</td>
					</tr>
</#list>
				</tbody>
			</table>
		</div>
	</div>
	<div style="float:left">
		<table border="0">
			<thead>
			<tr>
						<td>
							Platz
						</td>
						<td>
							
						</td>
						<td>
							T
						</td>
						<td>
							G
						</td>
						<td>
							P
						</td>
						<td>
							S/U/N
						</td>
					</tr>
			</thead>
				<tbody>
<#list allTimeTable as x>
					<tr>
						<td>
							${x?counter}
						</td>
						<td>
							${x.trainer.name}
						</td>
						<td>
							${x.goals}
						</td>
						<td>
							${x.goalsAgainst}
						</td>
						<td>
							${x.points}
						</td>
						<td>
							${x.won}/${x.draw}/${x.loose}
						</td>
					</tr>
</#list>
				</tbody>
			</table>
	</div>
</body>

</html>