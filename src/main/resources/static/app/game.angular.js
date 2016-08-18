'use strict';

(function ()
{
    angular.module('game',[]).controller('gameCntl', gameCntl);

    function gameCntl($scope)
    {
        $scope.board = [[]];
        $scope.spieler1Punkte = 0;
        $scope.spieler2Punkte = 0;

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
                        if($scope.board[y][x] != state.board[y][x]) $scope.board[y][x] = state.board[y][x];
                    }
                }
            }
            var max = parseInt(state.width) * parseInt(state.height);
            $scope.player1 = state.player1;
            $scope.player2 = state.player2;
            $scope.player1.energieP = Math.round(parseInt(state.player1.energy) * 100 / max);
            $scope.player2.energieP = Math.round(parseInt(state.player2.energy) * 100 / max);
            if(state.phase=="Start") {
                $scope.maxCells = max / 5;
                $scope.player1.lager = $scope.maxCells - $scope.player1.cells;
                $scope.player2.lager = $scope.maxCells - $scope.player2.cells;
            } else
                $scope.maxCells = max * ( parseInt(state.winCondition) / 100.0 );
            $scope.player1.cellsP = $scope.player1.cells * 100 / $scope.maxCells;
            $scope.player2.cellsP = $scope.player2.cells * 100 / $scope.maxCells;
            $scope.$apply();
        };

        $scope.message = [];

        var messageChanged = function(message)
        {
            $scope.message[$scope.message.length] = message.body;
            $scope.$apply();
        };

        var websocket = gameWebsocket(stateChanged, messageChanged);

        $scope.userSet = function (x, y)
        {
           websocket.userClicked(x, y);
        };

        $scope.ready = function ()
        {
            websocket.ready();
            jQuery(".ready").attr("disabled","disabled");
        };

        $scope.end = function ()
        {
            websocket.end();
        };
    }
})();