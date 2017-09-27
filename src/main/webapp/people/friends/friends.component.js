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

        }
    ]
});