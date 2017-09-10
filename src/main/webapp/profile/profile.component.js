'use strict';

angular.module('profile', ['ngRoute']);

angular.
module('profile').
component('profile', {
    templateUrl: '/profile/profile.template.html',
    controller: ['$http', '$scope', '$routeParams', '$rootScope',
        function LoginController($http, $scope, $routeParams, $rootScope) {

            $http.get('/user/' + $routeParams.userId + '?loadImage=true').then(function(response) {
                $scope.profile = response.data;
            });

            $http.get('/currentUser').then(function(response) {
                $rootScope.user = response.data;
            });

            $scope.myProfile = function () {
                if ($rootScope.user == undefined || $scope.profile == undefined) {
                    return false;
                }
                if ($rootScope.user.id == $scope.profile.id) {
                    return true;
                }
                return false;
            };

        }
    ]
});