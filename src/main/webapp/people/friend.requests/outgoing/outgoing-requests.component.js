'use strict';

angular.module('outgoingRequests', []);

angular.
module('outgoingRequests').
component('outgoingRequests', {
    templateUrl: '/people/friend.requests/outgoing/outgoing-requests.template.html',
    controller: ['$http', '$scope',
        function IncomingRequestsController($http, $scope) {

            /*loadNewRequests();
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
            };*/

        }
    ]
});