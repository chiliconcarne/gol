'use strict';

(function ()
{
    angular.module('game',[]).controller('gameCntl', gameCntl);

    gameCntl.$inject = ['$scope', '$http', '$interval'];

    function gameCntl($scope, $http, $interval)
    {
        $scope.board = [[]];

        var boardChanged = function(board)
        {
            $scope.board = board;
            $scope.$apply();
        };

        var websocket = gameWebsocket(boardChanged);

        $scope.userClicked = function (x, y)
        {
            websocket.userClicked(x, y);
        };
    }
})();