'use strict';

angular.module('allUsers', []);

angular.
module('allUsers').
component('allUsers', {
    templateUrl: '/people/all.users/all-users.template.html',
    controller: ['$http', '$scope',
        function AllUsersController($http, $scope) {

            $http.get('/all-users').then(function(response) {
                $scope.people = response.data;
            });

            $scope.addFriend = function (friendUserId) {

                $http({
                    method: 'POST',
                    url: '/friend/request',
                    data: friendUserId
                });
            }

        }
    ]
});