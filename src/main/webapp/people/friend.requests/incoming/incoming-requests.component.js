'use strict';

angular.module('incomingRequests', []);

angular.
module('incomingRequests').
component('incomingRequests', {
    templateUrl: '/people/friend.requests/incoming/incoming-requests.template.html',
    controller: ['$http', '$scope',
        function IncomingRequestsController($http, $scope) {

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

        }
    ]
});