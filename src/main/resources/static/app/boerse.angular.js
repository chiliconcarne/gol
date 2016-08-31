'use strict';

(function ()
{
    angular.module('boerse',[]).controller('boerseCntl', boerseCntl);

    function boerseCntl($scope)
    {
        var listChanged = function(list)
        {
            $scope.offer = list;
            $scope.$apply();
            console.info($scope.offer);
        };

        var messageChanged = function(message)
        {
            $scope.message = message.body;
            $scope.$apply();
            $('#boerseMsg').modal('show');
        };

        var websocket = boerseWebsocket(listChanged, messageChanged);

        $scope.add = function(){
            websocket.add();
        }

        $scope.remove = function(){
            websocket.remove();
        }

        $scope.accept = function(username){
            websocket.accept(username);
        }

        $scope.leave = function(){
            websocket.leave();
            location.href='/profile';
        }
    }
})();