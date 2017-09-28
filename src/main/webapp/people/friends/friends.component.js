'use strict';

angular.module('friends', []);

angular.
module('friends').
component('friends', {
    templateUrl: '/people/friends/friends.template.html',
    controller: ['$http', '$scope',
        function FriendsController($http, $scope) {

            loadFriends();

            function loadFriends() {
                $http.get('/friends').then(function(response) {
                    $scope.friends = response.data;
                });
            };

            $scope.openChat = function (userId) {
                $http.get('/chat/id' + '?interlocutor=' + userId).then(function(response) {
                    if (response.data == -1) {
                        console.log(response.data);
                    } else {
                        $scope.chatId = response.data;
                        window.location.replace('#!/messages/' + $scope.chatId);
                    }
                });
            };

        }
    ]
});