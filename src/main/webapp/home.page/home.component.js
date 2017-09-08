'use strict';

angular.module('home', []);

angular.
module('home').
component('home', {
    controller: ['$http', '$rootScope',
        function CartController($http,  $rootScope) {

            $http.get('/currentUser').then(function(response) {
                $rootScope.user = response.data;
                if (isAuthority('ROLE_ANONYMOUS')) {
                    window.location.replace('#!/hello');
                } else {
                    $http.get('/userId?email=' + $rootScope.user.username).then(function(response) {
                        window.location.replace('#!/id' + response.data);
                    });
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