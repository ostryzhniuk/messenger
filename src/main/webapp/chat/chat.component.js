'use strict';

angular.module('chat', []);

angular.
module('chat').
component('chat', {
    templateUrl: '/chat/chat.template.html',
    controller: ['$http', '$scope', '$routeParams', '$rootScope', '$location',
        function ChatController ($http, $scope, $routeParams, $rootScope, $location) {

            var stompClient = $rootScope.stompClient;

            $scope.isSelectedChat = false;
            $scope.model = {};
            $scope.model.newMessage = '';
            var chatId;

            configureStompClient();
            loadChat();

            function loadChat() {

                if ($routeParams.chatId != undefined) {
                    chatId = $routeParams.chatId;
                    $scope.isSelectedChat = true;

                    $http.get('/messages/' + chatId).then(function(response) {
                        $scope.messages = response.data;
                        scrollToBottom();
                        updateLastReadMessage($scope.messages[$scope.messages.length - 1]);
                    });
                }
            }

            function scrollToBottom() {
                var div = document.getElementById('messages-container');

                $('#messages-container').stop().animate({
                    scrollTop: Math.pow(div.scrollHeight, 3)
                }, 10);
            }

            function configureStompClient() {
                setTimeout(function() {
                    $rootScope.stompClient.subscribe('/user/queue/reply', function (message) {
                        showMessage(message.body);
                    });

                    $rootScope.stompClient.subscribe('/user/queue/return', function (message) {
                        showMessage(message.body);
                    });
                }, 500);
            }

            $scope.sendMessage = function () {
                var newMessage = $scope.model.newMessage;
                if (newMessage != undefined && newMessage != '' && newMessage != '\n') {
                    var message = JSON.stringify({chatId : chatId, body : newMessage});
                    stompClient.send("/app/message", {}, message);
                }
            }

            function showMessage(message) {
                var parsedMessage = JSON.parse(message);

                if (parsedMessage.chat.id == chatId) {
                    $scope.messages.push(parsedMessage);
                    $scope.model.newMessage = '';
                    $scope.$apply();
                    scrollToBottom();

                    updateLastReadMessage(parsedMessage);
                }
            }

            function updateLastReadMessage(message) {
                if (window.location.href.indexOf('/messages/' + message.chat.id) != -1) {
                    $http({
                        method: 'PUT',
                        url: '/message/last',
                        data: message.id
                    });
                }
            }
        }
    ]
});