'use strict';

angular.module('friends', []);

angular.
module('friends').
component('friends', {
    templateUrl: '/people/friends/friends.template.html',
    controller: ['$http', '$scope', '$rootScope',
        function FriendsController($http, $scope, $rootScope) {

            checkAuthority();
            loadFriends();

            function loadFriends() {
                $http.get('/friends').then(function(response) {
                    $scope.friends = response.data;
                });
            };

            $scope.openChat = function (userId) {
                $http.get('/chat/id' + '?interlocutor=' + userId).then(function(response) {
                    if (response.data == -1) {
                        console.log(response.data);
                    } else {
                        $scope.chatId = response.data;
                        window.location.replace('#!/messages/' + $scope.chatId);
                    }
                });
            };

            $scope.remove = function (userId) {
                $http({
                    method: 'PUT',
                    url: '/friends/remove',
                    data: userId
                }).then(function(response) {
                    loadFriends();
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