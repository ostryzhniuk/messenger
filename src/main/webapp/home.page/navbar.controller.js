'use strict';

angular
.module('messengerApp')
.controller('navbarCtrl', function ($http, $scope, $rootScope, $routeParams) {

    $scope.searchParameter = '';

    $http.get('/currentUser').then(function(response) {
        $rootScope.user = response.data;
        connect();
        loadFriendRequests();
    });

    $http.get('/chats').then(function(response) {
        $scope.chats = response.data;
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
        window.location.reload();
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

                $rootScope.stompClient.subscribe('/user/queue/new-friend-request', function (message) {
                    loadFriendRequests();
                });
            }, 500);
        });
    }

    function loadFriendRequests() {
        $http.get('/friend-requests/incoming/not-reviewed').then(function(response) {
            $rootScope.newFriendRequests = response.data.length;
        });
    }

    $scope.openChat = function (chat) {
        window.location.replace('#!/messages/' + chat.id);
        chat.unreadMessages = 0;
    }

    $scope.myProfile = function () {
        window.location.replace('#!/id' + $rootScope.user.id);
    }

    $scope.search = function () {
        if ($scope.searchParameter != undefined && $scope.searchParameter != '') {
            $http.get('/search/?parameter=' + $scope.searchParameter).then(function(response) {
                
            });
        }
    };

});