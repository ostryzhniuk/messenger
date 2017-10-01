'use strict';

angular.module('incomingRequests', []);

angular.
module('incomingRequests').
component('incomingRequests', {
    templateUrl: '/people/friend.requests/incoming/incoming-requests.template.html',
    controller: ['$http', '$scope', '$rootScope',
        function IncomingRequestsController($http, $scope, $rootScope) {

            checkAuthority();
            loadNotReviewedRequests();
            loadRejectedRequests();

            function loadNotReviewedRequests() {
                $http.get('/friend-requests/incoming/not-reviewed').then(function(response) {
                    $scope.newRequests = response.data;
                });
            };

            function loadRejectedRequests() {
                $http.get('/friend-requests/incoming/rejected').then(function(response) {
                    $scope.rejectedRequests = response.data;
                });
            };

            function loadFriendRequests() {
                $http.get('/friend-requests/incoming/not-reviewed').then(function(response) {
                    $rootScope.newFriendRequests = response.data.length;
                });
            }

            $scope.confirm = function (userId) {
                $http({
                    method: 'PUT',
                    url: '/friend/request/confirm',
                    data: userId
                }).then(function(response) {
                    loadNotReviewedRequests();
                    loadRejectedRequests();
                    loadFriendRequests();
                    $rootScope.chats.push(response.data);
                });
            };

            $scope.reject = function (userId) {
                $http({
                    method: 'PUT',
                    url: '/friend/request/reject',
                    data: userId
                }).then(function(response) {
                    loadNotReviewedRequests();
                    loadRejectedRequests();
                    loadFriendRequests();
                });
            };

            function checkAuthority() {
                if (isAuthority('ROLE_ANONYMOUS')) {
                    window.location.replace('#!/hello');
                }
            }

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