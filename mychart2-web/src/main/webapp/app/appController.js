angular.module('MyChart')

/**
 * Controlador geral da aplicação.
 */
.controller('AppController',
		[ '$rootScope', '$scope', '$location', '$log', '$mdMedia', '$mdSidenav', function($rootScope, $scope, $location, $log, $mdMedia, $mdSidenav) {
			'use strict';

			$rootScope.titulo = $scope.app.nome;

			$scope.dialogsFullscreen = $mdMedia('xs') || $mdMedia('sm');
			$scope.$watch(function() {
				return $mdMedia('xs') || $mdMedia('sm');
			}, function(wantsFullScreen) {
				$scope.dialogsFullscreen = (wantsFullScreen === true);
			});

			$scope.toggleMenu = function() {
				$mdSidenav('menu').toggle();
			};

			$scope.fecharMenu = function() {
				$mdSidenav('menu').close();
			};

			$scope.visualizarParada = function() {
				$location.path('/parada');
				$scope.fecharMenu();
			};

			$scope.visualizarArtista = function() {
				$location.path('/artista');
				$scope.fecharMenu();
			};

			$scope.visualizarCancao = function() {
				$location.path('/cancao');
				$scope.fecharMenu();
			};
		} ]);