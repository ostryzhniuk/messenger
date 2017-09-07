'use strict';

angular.module('messengerApp', [
    'ngRoute',
    'ui.bootstrap',
    'hello'
]);

angular.
module('messengerApp').
config(['$locationProvider' ,'$routeProvider', '$qProvider',
    function config($locationProvider, $routeProvider, $qProvider) {
        $locationProvider.hashPrefix('!');
        $qProvider.errorOnUnhandledRejections(false);

        $routeProvider.
        when('/hello', {
            template: '<hello></hello>'
        })
        .otherwise('/');
    }
]);
