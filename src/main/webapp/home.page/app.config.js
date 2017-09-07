'use strict';

angular.module('messengerApp', [
    'ngRoute',
    'ui.bootstrap',
    'cart'
]);

angular.
module('messengerApp').
config(['$locationProvider' ,'$routeProvider', '$qProvider',
    function config($locationProvider, $routeProvider, $qProvider) {
        $locationProvider.hashPrefix('!');
        $qProvider.errorOnUnhandledRejections(false);

        /*$routeProvider.
        when('/catalog', {
            template: '<catalog-categories></catalog-categories>'
        })
        .otherwise('/');*/
    }
]);
