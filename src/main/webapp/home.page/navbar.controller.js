'use strict';

angular
.module('messengerApp')
.controller('navbarCtrl', function ($http, $scope, $rootScope, $uibModal) {

    $scope.logout = function () {
        $http.get('/logout');
        window.location.replace('#!/home');
    };

});