'use strict';

(function ()
{
    angular.module('game',[]).controller('gameCntl', gameCntl);

    function gameCntl($scope)
    {
        $scope.board = [[]];

        var stateChanged = function(state)
        {
            $scope.board = state.board;
            $scope.spieler1 = state.spieler1;
            $scope.spieler2 = state.spieler2;
            $scope.$apply();
        };

        var messageChanged = function(message)
        {
            $scope.message = message;
            angular.element("#state").append(message);
            $scope.$apply();
        };

        var websocket = gameWebsocket(stateChanged, messageChanged);

        $scope.userClicked = function (x, y)
        {
            websocket.userClicked(x, y);
        };

        $scope.ready = function ()
        {
            websocket.ready();
        };
    }
})();