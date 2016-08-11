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
            $scope.spieler1 = state.player1;
            $scope.spieler2 = state.player2;
            $scope.$apply();
        };

        var messageChanged = function(message)
        {
            $scope.message = message;
            $scope.$apply();
        };

        var websocket = gameWebsocket(stateChanged, messageChanged);

        var mousedown = false;
        var oldSetPos = {x: -1,y: -1};
        $(document).mousedown(() => {mousedown = true;})
        $(document).mouseup(() => {mousedown = false;})
        $scope.userSet = function (x, y, force)
        {
            if((mousedown || force) && (oldSetPos.x != x || oldSetPos.y != y))
            {
                oldSetPos.x = x;
                oldSetPos.y = y;
                websocket.userClicked(x, y);
            }
        };

        $scope.ready = function ()
        {
            websocket.ready();
        };
    }
})();