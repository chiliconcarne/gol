'use strict';

(function ()
{
    angular.module('game',[]).controller('gameCntl', gameCntl);

    function gameCntl($scope)
    {
        $scope.board = [[]];

        var stateChanged = function(state)
        {
            if($scope.board.length != state.board.length || $scope.board[0].length != state.board[0].length)
            {
                $scope.board = state.board;
            }
            else
            {
                for(var y = 0; y < $scope.board.length; y++)
                {
                    for(var x = 0; x < $scope.board[0].length; x++)
                    {
                        if($scope.board[x][y] != state.board[x][y]) $scope.board[x][y] = state.board[x][y];
                    }
                }
            }
            $scope.spieler1 = state.player1;
            $scope.spieler2 = state.player2;
            $scope.$apply();
        };

        $scope.message = [];

        var messageChanged = function(message)
        {
            $scope.message[$scope.message.length] = message;
            $scope.$apply();
        };

        var websocket = gameWebsocket(stateChanged, messageChanged);

        var mousedown = false;
        var oldSetPos = {x: -1,y: -1};
        $(document).mousedown(() => {mousedown = true;})
        $(document).mouseup(() => {mousedown = false;})
        $scope.userSet = function (x, y, force)
        {
            if(force || (mousedown && (oldSetPos.x != x || oldSetPos.y != y)))
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

        $scope.end = function ()
        {
            websocket.end();
        };
    }
})();