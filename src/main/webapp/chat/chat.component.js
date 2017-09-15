'use strict';

angular.module('chat', []);

angular.
module('chat').
component('chat', {
    templateUrl: '/chat/chat.template.html',
    controller: ['$http', '$scope', '$routeParams',
        function ChatController($http, $scope, $routeParams) {

            var stompClient = null;

            $scope.chatId = $routeParams.chatId;

            $http.get('/chats').then(function(response) {
                $scope.chats = response.data;
                connect();
            });

            $http.get('/messages/' + $scope.chatId).then(function(response) {
                $scope.messages = response.data;
            });

            $scope.action = function () {
                console.log('ENTER');
                sendMessage();
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
                var socket = new SockJS('/gs-guide-websocket');
                stompClient = Stomp.over(socket);
                stompClient.connect({}, function (frame) {
                    setConnected(true);
                    console.log('Connected: ' + frame);
                    stompClient.subscribe('/topic/greetings', function (greeting) {
                        console.log(greeting);
                        // showGreeting(JSON.parse(greeting.body).content);
                    });
                });
            }

            function sendMessage() {
                stompClient.send("/send/message", {}, $scope.newMessage);
            }


        }
    ]
});