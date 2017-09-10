'use strict';

angular.module('home', []);

angular.
module('home').
component('home', {
    controller: ['$http', '$rootScope',
        function HomeController($http,  $rootScope) {

            $http.get('/currentUser').then(function(response) {
                $rootScope.user = response.data;
                if (isAuthority('ROLE_ANONYMOUS')) {
                    window.location.replace('#!/hello');
                } else {
                    window.location.replace('#!/id' + $rootScope.user.id);
                }
            });

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