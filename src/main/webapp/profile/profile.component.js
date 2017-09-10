'use strict';

angular.module('profile', ['ngRoute']);

angular.
module('profile').
component('profile', {
    templateUrl: '/profile/profile.template.html',
    controller: ['$http', '$scope', '$routeParams',
        function LoginController($http, $scope, $routeParams) {

            $http.get('/user/' + $routeParams.userId + '?loadImage=true').then(function(response) {
                $scope.user = response.data;
            });

        }
    ]
});