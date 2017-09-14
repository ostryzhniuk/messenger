'use strict';

angular.module('chat', []);

angular.
module('chat').
component('chat', {
    templateUrl: '/chat/chat.template.html',
    controller: ['$http', '$scope', '$routeParams',
        function ChatController($http, $scope, $routeParams) {

            $scope.chatId = $routeParams.chatId;

            $http.get('/chats').then(function(response) {
                $scope.chats = response.data;
            });

        }
    ]
});