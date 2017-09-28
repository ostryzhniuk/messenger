'use strict';

angular.module('outgoingRequests', []);

angular.
module('outgoingRequests').
component('outgoingRequests', {
    templateUrl: '/people/friend.requests/outgoing/outgoing-requests.template.html',
    controller: ['$http', '$scope',
        function OutgoingRequestsController($http, $scope) {

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

        }
    ]
});