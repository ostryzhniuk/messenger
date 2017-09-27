'use strict';

angular.module('allUsers', []);

angular.
module('allUsers').
component('allUsers', {
    templateUrl: '/people/all.users/all-users.template.html',
    controller: ['$http', '$scope', '$rootScope',
        function AllUsersController($http, $scope, $rootScope) {

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

            $scope.myProfile = function (userId) {
                if ($rootScope.user == undefined) {
                    return false;
                }
                if ($rootScope.user.id == userId) {
                    return true;
                }
                return false;
            };

        }
    ]
});