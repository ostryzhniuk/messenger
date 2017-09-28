'use strict';

angular.module('incomingRequests', []);

angular.
module('incomingRequests').
component('incomingRequests', {
    templateUrl: '/people/friend.requests/incoming/incoming-requests.template.html',
    controller: ['$http', '$scope', '$rootScope',
        function IncomingRequestsController($http, $scope, $rootScope) {

            loadNewRequests();
            loadRejectedRequests();

            function loadNewRequests() {
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
                    loadNewRequests();
                    loadFriendRequests();
                });
            };

            $scope.reject = function (userId) {

            };

        }
    ]
});