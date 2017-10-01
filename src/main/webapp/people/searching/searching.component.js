'use strict';

angular.module('searching', []);

angular.
module('searching').
component('searching', {
    templateUrl: '/people/searching/searching.template.html',
    controller: ['$http', '$scope', '$rootScope', '$routeParams',
        function SearchingController($http, $scope, $rootScope, $routeParams) {

            checkAuthority();

            $http.get('/search/?parameter=' + $routeParams.parameter).then(function(response) {
                $scope.people = response.data;
            });

            $scope.openProfile = function(userId) {
                window.location.replace('#!/id' + userId);
            }

            function checkAuthority() {
                if (isAuthority('ROLE_ANONYMOUS')) {
                    window.location.replace('#!/hello');
                }
            };

            function isAuthority (role) {
                if ($rootScope.user == undefined) {
                    return false;
                }
                var authorities = $rootScope.user.authorities;
                for (var authority in authorities) {
                    if (authorities[authority].authority == role) {
                        return true;
                    }
                }
                return false;
            };

        }
    ]
});