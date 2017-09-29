'use strict';

angular.module('profileEditor', ['ngRoute']);

angular.
module('profileEditor').
component('profileEditor', {
    templateUrl: '/profile/profile.editor/profile-editor.template.html',
    controller: ['$http', '$scope', '$routeParams', '$rootScope',
        function ProfileEditorController($http, $scope, $routeParams, $rootScope) {

            var friendshipStatus = '';
            $scope.statusFriends = 'FRIENDS';
            $scope.statusRequested = 'REQUESTED';
            $scope.statusNotReviewed = 'NOT_REVIEWED';
            $scope.statusRejected = 'REJECTED';
            $scope.statusNotFriends = 'NOT_FRIENDS';

            loadProfile();

            function loadProfile() {
                $http.get('/user/' + $routeParams.userId + '?loadImage=true').then(function(response) {
                    $scope.profile = response.data;
                    loadFriendshipStatus();
                });
            }

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

            function loadFriendRequests() {
                $http.get('/friend-requests/incoming/not-reviewed').then(function(response) {
                    $rootScope.newFriendRequests = response.data.length;
                    console.log('$rootScope.newFriendRequests: ' + $rootScope.newFriendRequests);
                });
            }

            $scope.addFriend = function (userId) {
                $http({
                    method: 'POST',
                    url: '/friend/request',
                    data: userId
                }).then(function(response) {
                    loadProfile();
                });
            };

            $scope.removeFriendRequest = function (userId) {
                $http({
                    method: 'PUT',
                    url: '/friend/request/outgoing/reject',
                    data: userId
                }).then(function(response) {
                    loadProfile();
                });
            };

            $scope.confirmFriendRequest = function (userId) {
                $http({
                    method: 'PUT',
                    url: '/friend/request/confirm',
                    data: userId
                }).then(function(response) {
                    loadProfile();
                    loadFriendRequests();
                    $rootScope.chats.push(response.data);
                });
            };

            $scope.removeFriend = function (userId) {
                $http({
                    method: 'PUT',
                    url: '/friends/remove',
                    data: userId
                }).then(function(response) {
                    loadProfile();
                });
            };

            $scope.rejectFriendRequest = function (userId) {
                $http({
                    method: 'PUT',
                    url: '/friend/request/reject',
                    data: userId
                }).then(function(response) {
                    loadProfile();
                    loadFriendRequests();
                });
            };

        }
    ]
});