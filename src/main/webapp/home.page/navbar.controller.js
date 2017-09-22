'use strict';

angular
.module('messengerApp')
.controller('navbarCtrl', function ($http, $scope, $rootScope, $uibModal) {

    $http.get('/currentUser').then(function(response) {
        $rootScope.user = response.data;
        connect();
    });

    $rootScope.isAuthority = function (role) {
        if ($rootScope.user == undefined) {
            return false;
        }
        var authorities = $rootScope.user.authorities;
        for (var authority in authorities) {
            if (authorities[authority].authority == role) {
                return true;
            }
        }
        return false;
    };

    $scope.logout = function () {
        $http.get('/logout');
        window.location.replace('#!/home');
    };

    function setConnected(connected) {
        $("#connect").prop("disabled", connected);
        $("#disconnect").prop("disabled", !connected);
        if (connected) {
            $("#conversation").show();
        }
        else {
            $("#conversation").hide();
        }
        $("#greetings").html("");
    }

    function connect() {
        var socket = new SockJS('/gs-messenger-websocket');
        $rootScope.stompClient = Stomp.over(socket);
        $rootScope.stompClient.connect({}, function (frame) {
            setConnected(true);
            setTimeout(function() {
                $rootScope.stompClient.subscribe('/user/queue/reply', function (message) {
                    console.log('yes!');
                });
            }, 500);
        });
    }

});