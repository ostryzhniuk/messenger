'use strict';

angular.module('incomingRequests', []);

angular.
module('incomingRequests').
component('incomingRequests', {
    templateUrl: '/people/friend.requests/incoming/incoming-requests.template.html',
    controller: ['$http', '$scope',
        function IncomingRequestsController($http, $scope) {

            $http.get('/friend-requests/incoming/not-reviewed').then(function(response) {
                $scope.people = response.data;
            });

        }
    ]
});