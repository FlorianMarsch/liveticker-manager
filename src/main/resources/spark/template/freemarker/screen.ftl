<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8"></meta>
<meta name="viewport" content="width=device-width, initial-scale=1"></meta>

</head>
<body>
    <div class="panel-body" ng-app="myApp" ng-controller="customersCtrl">
        <div class="row">
            <div >
                <div >
                    <div style="text-align: center;">
                        <div style="display: inline-flex;">
                            <p>
                                <span><img src="${x.home.imageUrl}" style="max-height: 9rem; max-width:9rem;"></img></span>
                            </p>
                            <p>
                                <span style="text-transform: uppercase; display: block;">${x.home.name}</span>
                                <span style="font-size: 3rem;">${x.homeGoals}</span>
                                <span style="font-size: 3rem;">:</span>
                                <span style="font-size: 3rem;">${x.guestGoals}</span>
                                <span style="text-transform: uppercase; display: block;">${x.guest.name}</span>
                            </p>
                            <p>
                                <span><img src="${x.guest.imageUrl}" style="max-height: 9rem; max-width:9rem;"></img></span>
                            </p>
                        </div>
                        <div>
                        <#list events as event>
                        <span>${event.name}</span>
                        </#list>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
