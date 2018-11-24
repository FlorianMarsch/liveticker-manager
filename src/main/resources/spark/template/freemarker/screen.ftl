<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8"></meta>
<meta name="viewport" content="width=device-width, initial-scale=1"></meta>

</head>
<body style="margin:0px;">
    <div class="panel-body" ng-app="myApp" ng-controller="customersCtrl" style="padding-top: 50vh;min-height: 100vh;margin: auto 0;color:white;text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;background-image:url(https://images.pexels.com/photos/112786/pexels-photo-112786.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260);background-position:center;background-size:cover;">
        <div class="row" style="    margin-top: -90px;">
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
