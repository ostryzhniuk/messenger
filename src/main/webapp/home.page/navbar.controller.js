'use strict';

angular
.module('messengerApp')
.controller('navbarCtrl', function ($http, $scope, $rootScope, $routeParams) {

    $http.get('/currentUser').then(function(response) {
        $rootScope.user = response.data;
        connect();
    });

    $http.get('/chats').then(function(response) {
        $scope.chats = response.data;
        console.log('$routeParams.chatId: ' + $routeParams.chatId);
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

                    var parsedMessage = JSON.parse(message.body);

                    if (window.location.href.indexOf('/messages/' + parsedMessage.chat.id) == -1) {
                        $scope.chats.forEach(function(chat) {
                            if (chat.id == parsedMessage.chat.id) {
                                $http.get('/unreadMessages/count/' + chat.id).then(function(response) {
                                    chat.unreadMessages = response.data;
                                });
                            }
                        });
                    }
                });
            }, 500);
        });
    }

    $scope.openChat = function (chat) {
        window.location.replace('#!/messages/' + chat.id);
        chat.unreadMessages = 0;
    }

    $scope.myProfile = function () {
        window.location.replace('#!/id' + $rootScope.user.id);
    }

});