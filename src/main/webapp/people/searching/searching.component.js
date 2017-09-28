'use strict';

angular.module('searching', []);

angular.
module('searching').
component('searching', {
    templateUrl: '/people/searching/searching.template.html',
    controller: ['$http', '$scope', '$rootScope', '$routeParams',
        function SearchingController($http, $scope, $rootScope, $routeParams) {

            $http.get('/search/?parameter=' + $routeParams.parameter).then(function(response) {
                $scope.people = response.data;
            });

            $scope.openProfile = function(userId) {
                window.location.replace('#!/id' + userId);
            }

        }
    ]
});