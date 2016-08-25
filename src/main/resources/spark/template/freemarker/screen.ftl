<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8"></meta>
<meta name="viewport" content="width=device-width, initial-scale=1"></meta>






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
