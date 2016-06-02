/// <reference path="../../../typings/angularjs/angular.d.ts" />
/// <reference path="../../../typings/angularjs/angular-resource.d.ts" />

module MyChart.cancao {
    'use strict';

    angular.module('MyChart.cancao')

        .controller('CancaoController', ['$rootScope', '$scope', '$timeout', '$log', 'Cancao', function($rootScope, $scope, $timeout, $log, Cancao) {

            $rootScope.titulo = 'Canção';

            $scope.filtrando = false;
            $scope.query = {
                filtro: '',
                ordem: 'titulo',
                limite: 5,
                pagina: 1
            };

            $scope.filtro = {
                visivel: false
            };

            $scope.recuperarCancoes = function() {
                $log.info('Recuperando canções:', $scope.query);
                $scope.cancoesPromise = Cancao.getAll($scope.query, function(cancoes) {
                    $log.debug('Canções:', cancoes);
                    $scope.cancoes = cancoes;
                }).$promise;
            };

            var filtroPromise;
            var bookmark;

            $scope.$watch('query.filtro', function(newValue, oldValue) {
                if (!oldValue) {
                    bookmark = $scope.query.pagina;
                }
                if (newValue !== oldValue) {
                    $scope.query.pagina = 1;
                }
                if (!newValue) {
                    $scope.query.pagina = bookmark;
                    $scope.filtrando = false;
                } else {
                    $scope.filtrando = true;
                }

                $timeout.cancel(filtroPromise);

                if ($scope.filtrando) {
                    filtroPromise = $timeout($scope.recuperarCancoes, 500);
                } else {
                    $scope.recuperarCancoes();
                }
            });
        }]);
}