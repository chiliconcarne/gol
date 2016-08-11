'use strict';

(function ()
{
    angular.module('boerse',[]).controller('boerseCntl', boerseCntl);

    function boerseCntl($scope)
    {
        var listChanged = function(list)
        {
            $scope.$apply();
        };

        var messageChanged = function(message)
        {
            $scope.message = message;
            $scope.$apply();
        };

        var websocket = boerseWebsocket(listChanged, messageChanged);
    }
})();