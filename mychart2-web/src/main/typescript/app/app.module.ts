/// <reference path="../typings/angularjs/angular.d.ts" />
/// <reference path="../typings/angularjs/angular-resource.d.ts" />

module MyChart {
    'use strict';

    angular.module('MyChart', ['ngRoute', 'ngResource', 'ngSanitize', 'ngMaterial', 'MyChart.directives', 'MyChart.parada', 'MyChart.artista', 'MyChart.cancao'])

        .config(['$routeProvider', function($routeProvider: ng.route.IRouteProvider) {
            'use strict';

            $routeProvider.when('/parada', {
                templateUrl: 'app/components/parada/parada.html',
                controller: 'ParadaController'
            }).when('/artista', {
                templateUrl: 'app/components/artista/artista.html',
                controller: 'ArtistaController'
            }).when('/cancao', {
                templateUrl: 'app/components/cancao/cancao.html',
                controller: 'CancaoController'
            }).otherwise({
                redirectTo: '/parada'
            });
        }])

        .run(['$rootScope', '$http', function($rootScope, $http) {
            $rootScope.app = {
                nome: 'MyChart',
                versao: '1.0.0-SNAPSHOT'
            };
        }]);
}