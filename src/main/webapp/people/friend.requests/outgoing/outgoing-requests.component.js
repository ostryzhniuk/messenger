'use strict';

angular.module('outgoingRequests', []);

angular.
module('outgoingRequests').
component('outgoingRequests', {
    templateUrl: '/people/friend.requests/outgoing/outgoing-requests.template.html',
    controller: ['$http', '$scope',
        function IncomingRequestsController($http, $scope) {

            loadOutgoingRequests();

            function loadOutgoingRequests() {
                $http.get('/friend-requests/outgoing').then(function(response) {
                    $scope.outgoingRequests = response.data;
                });
            };

        }
    ]
});