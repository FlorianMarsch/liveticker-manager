<html>

<head>
<title></title>
</head>
<style type="text/css">
    fieldset{
         display: inline-block;
         float:left;
    }
</style>

<body>
	<h1>Spieltag ${matchday.number}</h1>
	<h2>${matchday.status}</h2>
	
	
	 <fieldset>
		<legend>Divisions</legend>
<table border="0">
	<#list divisionalTables as item>
    <#assign division = item.getKey()/>
    <#assign table = item.getValue()/>
			<tbody>
			<tr>
						<td>
							Division ${division.name}
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
						
					</tr>
		
<#list table as x>
					<tr>
						<td>
							${x?counter}. ${x.trainer.name}
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
						
					</tr>
</#list>
			<tr>
						<td>
							&nbsp;
						</td>
						<td>
							&nbsp;
						</td>
						<td>
							&nbsp;
						</td>
						<td>
							&nbsp;
						</td>
						
					</tr>	
	</#list>
	</tbody>
			</table>
	</fieldset>
	
	 <fieldset>
	<legend>Results</legend>
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
						<td>
							<a href="/screen/${x.id}"> zur Übersicht</a>
						</td>
					</tr>
</#list>
				</tbody>
			</table>
		</fieldset>
		 <fieldset>
		<legend>Events</legend>
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
							${x.trainer.name}
						</td>
					</tr>
</#list>
				</tbody>
			</table>
	 </fieldset>
	 <fieldset>
<legend>Table</legend>
		<table border="0">
			<thead>
			<tr>
						<td>
							Platz
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
							${x?counter}. ${x.trainer.name}
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
	</fieldset>
</body>

</html>