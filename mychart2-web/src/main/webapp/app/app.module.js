/// <reference path="../typings/angularjs/angular.d.ts" />
/// <reference path="../typings/angularjs/angular-resource.d.ts" />
var MyChart;
(function (MyChart) {
    'use strict';

    angular.module('MyChart', ['ngRoute', 'ngResource', 'ngSanitize', 'ngMaterial', 'MyChart.directives', 'MyChart.parada', 'MyChart.artista']).config([
        '$routeProvider', function ($routeProvider) {
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
        }]).run([
        '$rootScope', '$http', function ($rootScope, $http) {
            $rootScope.app = {
                nome: 'MyChart',
                versao: '1.0.0-SNAPSHOT'
            };
        }]);
})(MyChart || (MyChart = {}));
