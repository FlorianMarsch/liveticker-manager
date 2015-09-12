<!DOCTYPE HTML>
<!--
	Prologue by HTML5 UP
	html5up.net | @n33co
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html>
	<head>
		<title>Please login</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<!--[if lte IE 8]><script src="assets/js/ie/html5shiv.js"></script><![endif]-->
		<link rel="stylesheet" href="assets/css/main.css" />
		<!--[if lte IE 8]><link rel="stylesheet" href="assets/css/ie8.css" /><![endif]-->
		<!--[if lte IE 9]><link rel="stylesheet" href="assets/css/ie9.css" /><![endif]-->
	</head>
	<body>

		<!-- Header -->
			<div id="header">

				<div class="top">

					<!-- Logo -->
						<div id="logo">
							<span class="image avatar48"><img src="images/avatar.jpg" alt="" /></span>
							<h1 id="title">Fussballmanager</h1>
							<p>Login</p>
						</div>

					<!-- Nav -->
						<nav id="nav">
							<!--

								Prologue's nav expects links in one of two formats:

								1. Hash link (scrolls to a different section within the page)

								   <li><a href="#foobar" id="foobar-link" class="icon fa-whatever-icon-you-want skel-layers-ignoreHref"><span class="label">Foobar</span></a></li>

								2. Standard link (sends the user to another page/site)

								   <li><a href="http://foobar.tld" id="foobar-link" class="icon fa-whatever-icon-you-want"><span class="label">Foobar</span></a></li>

							-->
							<ul>
								<li><a href="#top" id="top-link" class="skel-layers-ignoreHref"><span class="icon fa-home">Login</span></a></li>
								<li><a href="#about" id="about-link" class="skel-layers-ignoreHref"><span class="icon fa-user">About</span></a></li>
							</ul>
						</nav>

				</div>

				<div class="bottom">

					<!-- Social Icons -->
						<ul class="icons">
							<li><a target="_blank" href="https://twitter.com/hashtag/comunioLDC?src=hash" class="icon fa-twitter"><span class="label">Twitter</span></a></li>
							<li><a target="_blank" href="https://twitter.com/hashtag/comunioLDC?src=hash">Twitter</a></li>
						</ul>

				</div>

			</div>

		<!-- Main -->
			<div id="main">

				<!-- Intro -->
					<section id="top" class="three">
						<div class="container">

							<header>
								<h2 class="alt">Hi! Ich bin dein <strong>Fussballmanager</strong>
							</header>
							<p>${message}</p>
							<form class="loginform">
								<div class="row">
									<div class="6u 12u$(mobile)"><input type="text" id="name" name="name" placeholder="Name" /></div>
									<div class="6u$ 12u$(mobile)"><input type="password" id="password" name="password" placeholder="Password" /></div>
									<div class="12u$">
										<input type="button" value="login" class="loginbutton"/>
									</div>
								</div>
							</form>

						</div>
					</section>

				<!-- About Me -->
					<section id="about" class="two">
						<div class="container">

							<header>
								<h2>About Me</h2>
							</header>


							<p>Der Fussballmanager ist derzeit in einer Testphase und kann daher noch
							Fehler enthalten. Funktionen werden Pö-a-Pö hinzugefügt.
							Der Server ist maximal 18 Stunden am Tag verfügbar</p>

						</div>
					</section>

			
			

			</div>

		<!-- Footer -->
			<div id="footer">

				<!-- Copyright -->
					<ul class="copyright">
						<li>&copy; Untitled. All rights reserved.</li><li>Design: <a href="http://html5up.net">HTML5 UP</a></li>
					</ul>

			</div>

		<!-- Scripts -->
			<script src="assets/js/jquery.min.js"></script>
			<script src="assets/js/jquery.scrolly.min.js"></script>
			<script src="assets/js/jquery.scrollzer.min.js"></script>
			<script src="assets/js/skel.min.js"></script>
			<script src="assets/js/util.js"></script>
			<!--[if lte IE 8]><script src="assets/js/ie/respond.min.js"></script><![endif]-->
			<script src="assets/js/main.js"></script>
			
			<script>
			$(".loginbutton").click(function  (){
					alert('click');
					var formData = JSON.stringify($(".loginform").serializeArray());
					$.ajax({
						type: "POST",
						url: "loginaction",
						data: formData,
		  				dataType: "json",
		  				contentType : "application/json",
						success: function( data, textStatus, jqXHR  ) {
					    	if(data.redirect){
					    		window.location.replace(data.redirect);
					    	}
					    },
					    error: function( jqXHR, textStatus, errorThrown ) {
					    	alert( errorThrown );
					    }
					});
				});
			
			</script>

	</body>
</html>