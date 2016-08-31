'use strict';

function getQueryVariable(variable) {
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if (pair[0] == variable) {
            return pair[1];
        }
    }
    alert('Query Variable ' + variable + ' not found');
}

(function ()
{
    angular.module('gameLobby',[]).controller('gameLobbyCntl', gameLobbyCntl);

    function gameLobbyCntl($scope)
    {

        function userChanged(user){
            $scope.team1 = user.team1;
            $scope.team2 = user.team2;
            $scope.$apply();
        }

        function msgChanged(msg){
            $scope.message = msg;
            $scope.$apply();
        }

        var websocket = gameLobbyWebsocket(userChanged, msgChanged);

        $scope.joinTeamRed = function(){

        }

        $scope.joinTeamBlue = function(){

        }
    }
})();