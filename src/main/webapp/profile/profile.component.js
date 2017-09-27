'use strict';

angular.module('profile', ['ngRoute']);

angular.
module('profile').
component('profile', {
    templateUrl: '/profile/profile.template.html',
    controller: ['$http', '$scope', '$routeParams', '$rootScope',
        function ProfileController($http, $scope, $routeParams, $rootScope) {

            $http.get('/user/' + $routeParams.userId + '?loadImage=true').then(function(response) {
                $scope.profile = response.data;
            });

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

            $scope.openChats = function () {
                window.location.replace('#!/messages/');
            };

            $scope.openFriendRequests = function () {
                window.location.replace('#!/friends/requests/incoming');
            }

            $scope.openFriends = function () {
                window.location.replace('#!/friends');
            };

        }
    ]
});