'use strict';

angular.module('messengerApp', [
    'ngRoute',
    'ui.bootstrap',
    'home',
    'helloPage',
    'signUp',
    'login',
    'profile',
    'allUsers',
    'chat',
    'incomingRequests'

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
        .when('/all-users', {
            template: '<all-users></all-users>'
        })
        .when('/messages', {
            template: '<chat></chat>'
        })
        .when('/messages/:chatId', {
            template: '<chat></chat>'
        })
        .when('/friends/requests/incoming', {
            template: '<incoming-requests></incoming-requests>'
        })
        .otherwise('/home');
    }
]);
