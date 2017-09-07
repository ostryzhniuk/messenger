'use strict';

angular.module('messengerApp', [
    'ngRoute',
    'ui.bootstrap',
    'helloPage'
]);

angular.
module('messengerApp').
config(['$locationProvider' ,'$routeProvider', '$qProvider',
    function config($locationProvider, $routeProvider, $qProvider) {
        $locationProvider.hashPrefix('!');
        $qProvider.errorOnUnhandledRejections(false);

        $routeProvider.
        when('/hello', {
            template: '<hello-page></hello-page>'
        });
        // .otherwise('/');
    }
]);
