'use strict';

angular.module('outgoingRequests', []);

angular.
module('outgoingRequests').
component('outgoingRequests', {
    templateUrl: '/people/friend.requests/outgoing/outgoing-requests.template.html',
    controller: ['$http', '$scope', '$rootScope',
        function OutgoingRequestsController($http, $scope, $rootScope) {

            checkAuthority();
            loadOutgoingRequests();

            function loadOutgoingRequests() {
                $http.get('/friend-requests/outgoing').then(function(response) {
                    $scope.outgoingRequests = response.data;
                });
            };

            $scope.reject = function (userId) {
                $http({
                    method: 'PUT',
                    url: '/friend/request/outgoing/reject',
                    data: userId
                }).then(function(response) {
                    loadOutgoingRequests();
                });
            };

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