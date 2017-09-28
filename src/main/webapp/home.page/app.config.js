'use strict';

angular.module('messengerApp', [
    'ngRoute',
    'ui.bootstrap',
    'home',
    'helloPage',
    'signUp',
    'login',
    'profile',
    'searching',
    'chat',
    'incomingRequests',
    'outgoingRequests',
    'friends'

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
        .when('/search/:parameter', {
            template: '<searching></searching>'
        })
        .when('/messages', {
            template: '<chat></chat>'
        })
        .when('/messages/:chatId', {
            template: '<chat></chat>'
        })
        .when('/friends', {
            template: '<friends></friends>'
        })
        .when('/friends/requests/incoming', {
            template: '<incoming-requests></incoming-requests>'
        })
        .when('/friends/requests/outgoing', {
            template: '<outgoing-requests></outgoing-requests>'
        })
        .otherwise('/home');
    }
]);
