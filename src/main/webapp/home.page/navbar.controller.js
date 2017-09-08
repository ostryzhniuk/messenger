'use strict';

angular
.module('messengerApp')
.controller('navbarCtrl', function ($http, $scope, $rootScope, $uibModal) {

    $http.get('/currentUser').then(function(response) {
        $rootScope.user = response.data;
    });

    $rootScope.isAuthority = function (role) {
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

    $scope.logout = function () {
        $http.get('/logout');
        window.location.replace('#!/home');
    };

});