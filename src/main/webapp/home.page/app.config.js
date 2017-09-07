'use strict';

angular.module('messengerApp', [
    'ngRoute',
    'ui.bootstrap',
    'helloPage',
    'signUp',
    'login'
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
        })
        .when('/signUp', {
            template: '<sign-up></sign-up>'
        })
        .when('/login', {
            template: '<login></login>'
        });
        // .otherwise('/');
    }
]);
