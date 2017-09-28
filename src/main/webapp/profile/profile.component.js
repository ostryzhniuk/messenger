'use strict';

angular.module('profile', ['ngRoute']);

angular.
module('profile').
component('profile', {
    templateUrl: '/profile/profile.template.html',
    controller: ['$http', '$scope', '$routeParams', '$rootScope',
        function ProfileController($http, $scope, $routeParams, $rootScope) {

            var friendshipStatus = '';
            $scope.statusFriends = 'FRIENDS';
            $scope.statusRequested = 'REQUESTED';
            $scope.statusNotReviewed = 'NOT_REVIEWED';
            $scope.statusRejected = 'REJECTED';
            $scope.statusNotFriends = 'NOT_FRIENDS';

            $http.get('/user/' + $routeParams.userId + '?loadImage=true').then(function(response) {
                $scope.profile = response.data;
            });

            loadFriendshipStatus();

            $http.get('/currentUser').then(function(response) {
                $rootScope.user = response.data;
            });

            $scope.myProfile = function () {
                if ($rootScope.user == undefined || $scope.profile == undefined) {
                    return false;
                }
                if ($rootScope.user.id == $scope.profile.id) {
                    return true;
                }
                return false;
            };

            function loadFriendshipStatus() {
                $http.get('/friendship/status/?friendUserId=' + $routeParams.userId).then(function(response) {
                    friendshipStatus = response.data.value;
                });
            };

            $scope.isFriendshipStatus = function (status) {
                if (friendshipStatus == status) {
                    return true;
                } else {
                    return false;
                }
            };

            $scope.openChat = function () {
                $http.get('/chat/id' + '?interlocutor=' + $scope.profile.id).then(function(response) {
                    if (response.data == -1) {
                        console.log(response.data);
                    } else {
                        $scope.chatId = response.data;
                        window.location.replace('#!/messages/' + $scope.chatId);
                    }
                });
            };

            $scope.openFriendRequests = function () {
                window.location.replace('#!/friends/requests/incoming');
            }

            $scope.openFriends = function () {
                window.location.replace('#!/friends');
            };

            $scope.addFriend = function (friendUserId) {
                $http({
                    method: 'POST',
                    url: '/friend/request',
                    data: friendUserId
                });
            };

        }
    ]
});