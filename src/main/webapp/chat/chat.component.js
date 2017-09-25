'use strict';

angular.module('chat', []);

angular.
module('chat').
component('chat', {
    templateUrl: '/chat/chat.template.html',
    controller: ['$http', '$scope', '$routeParams', '$rootScope',
        function ChatController ($http, $scope, $routeParams, $rootScope) {

            var stompClient = $rootScope.stompClient;

            $scope.isSelectedChat = false;
            $scope.model = {};
            $scope.model.newMessage = '';

            configureStompClient();
            loadChat();
            onDestroy();

            function onDestroy() {
                $scope.$on('$destroy', function(){
                    $http({
                        method: 'PUT',
                        url: '/chat/lastVisit/update',
                        data: $scope.chatId
                    });
                });
            }

            function loadChat() {

                if ($routeParams.chatId != undefined) {
                    $scope.chatId = $routeParams.chatId;
                    $scope.isSelectedChat = true;

                    $http.get('/messages/' + $scope.chatId).then(function(response) {
                        $scope.messages = response.data;
                        scrollToBottom();
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
                    var message = JSON.stringify({chatId : $scope.chatId, body : newMessage});
                    stompClient.send("/app/message", {}, message);
                }
            }

            function showMessage(message) {
                $scope.messages.push(JSON.parse(message));
                $scope.model.newMessage = '';
                $scope.$apply();
                scrollToBottom();
            }
        }
    ]
});