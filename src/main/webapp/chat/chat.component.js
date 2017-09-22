'use strict';

angular.module('chat', []);

angular.
module('chat').
component('chat', {
    templateUrl: '/chat/chat.template.html',
    controller: ['$http', '$scope', '$routeParams',
        function ChatController ($http, $scope, $routeParams) {

            var stompClient = null;
            $scope.messages = [];

            $scope.chatId = $routeParams.chatId;

            $http.get('/chats').then(function(response) {
                $scope.chats = response.data;
                connect();
            });

            $http.get('/messages/' + $scope.chatId).then(function(response) {
                $scope.messages = response.data;
                scrollToBottom();
            });

            function scrollToBottom() {
                var div = document.getElementById('messages-container');

                $('#messages-container').stop().animate({
                    scrollTop: Math.pow(div.scrollHeight, 3)
                }, 10);
            };

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
                var socket = new SockJS('/gs-messenger-websocket');
                stompClient = Stomp.over(socket);
                stompClient.connect({}, function (frame) {
                    setConnected(true);
                    stompClient.subscribe('/user/queue/reply', function (message) {
                        showMessage(message.body);
                    });

                    stompClient.subscribe('/user/queue/return', function (message) {
                        showMessage(message.body);
                    });
                });
            }

            function showMessage(message) {
                $scope.messages.push(JSON.parse(message));
                $scope.newMessage = '';
                $scope.$apply();
                scrollToBottom();
            }

            function sendMessage() {
                var message = JSON.stringify({chatId : $scope.chatId, body : $scope.newMessage});
                stompClient.send("/app/message", {}, message).then(function (response) {
                    $scope.messages.push(JSON.parse(response.body));
                    console.log($scope.messages);
                    $scope.newMessage = '';
                    $scope.$apply();
                    scrollToBottom();
                })
            }
        }
    ]
});