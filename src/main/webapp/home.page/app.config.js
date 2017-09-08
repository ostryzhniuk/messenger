'use strict';

angular.module('messengerApp', [
    'ngRoute',
    'ui.bootstrap',
    'home',
    'helloPage',
    'signUp',
    'login',
    'profile'
]);

angular.
module('messengerApp').
config(['$locationProvider' ,'$routeProvider', '$qProvider',
    function config($locationProvider, $routeProvider, $qProvider) {
        $locationProvider.hashPrefix('!');
        $qProvider.errorOnUnhandledRejections(false);

        $routeProvider.
        when('/home', {
            template: '<home></home>'
        })
        .when('/hello', {
            template: '<hello-page></hello-page>'
        })
        .when('/signUp', {
            template: '<sign-up></sign-up>'
        })
        .when('/login', {
            template: '<login></login>'
        })
        .when('/id:userId', {
            template: '<profile></profile>'
        })
        .otherwise('/home');
    }
]);
