'use strict';

(function ()
{
    angular.module('game',[]).controller('gameCntl', gameCntl);

    gameCntl.$inject = ['$scope', '$http', '$interval'];

    function gameCntl($scope, $http, $interval)
    {
        $scope.board = [[]];

        var stateChanged = function(state)
        {
            $scope.board = state.board;
            $scope.$apply();
        };

        var websocket = gameWebsocket(stateChanged);

        $scope.userClicked = function (x, y)
        {
            websocket.userClicked(x, y);
        };
    }
})();