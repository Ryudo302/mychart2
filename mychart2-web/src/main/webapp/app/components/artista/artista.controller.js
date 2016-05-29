angular.module('MyChart.artista')

/**
 * Controlador do módulo de Artista.
 */
.controller(
		'ArtistaController',
		[
				'$rootScope',
				'$scope',
				'$timeout',
				'$log',
				'$mdDialog',
				'$mdMedia',
				'Artista',
				function($rootScope, $scope, $timeout, $log, $mdDialog, $mdMedia, Artista) {
					'use strict';

					var bookmark;

					$rootScope.titulo = 'Artista';

					$scope.artistasPromise = null;
					$scope.artistas = [];
					$scope.artista = null;
					$scope.artistasSelecionados = [];

					$scope.filtrando = false;
					$scope.query = {
						filtro : '',
						ordem : 'nome',
						limite : 5,
						pagina : 1
					};

					$scope.filtro = {
						visivel : false
					};

					$scope.removerFiltro = function() {
						$scope.filtro.visivel = false;
						$scope.query.filtro = '';

						if ($scope.filtro.form.$dirty) {
							$scope.filtro.form.$setPristine();
						}
					}

					var filtroPromise;
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
							filtroPromise = $timeout($scope.recuperarArtistas, 500);
						} else {
							$scope.recuperarArtistas();
						}
					});

					$scope.recuperarArtistas = function() {
						$log.info('Recuperando artistas:', $scope.query);
						$scope.artistasPromise = Artista.getAll($scope.query, function(artistas) {
							$log.debug('Artistas:', artistas);
							$scope.artistas = artistas;
						}).$promise;
					};

					var crudSuccessCallback = function(result) {
						$scope.recuperarArtistas();
					};

					var abrirEdicaoArtista = function(evento) {
						return $mdDialog.show({
							controller : [ '$scope', '$mdDialog', function($scope, $mdDialog) {
								$scope.salvar = function() {
									$mdDialog.hide();
								};

								$scope.cancelar = function() {
									$mdDialog.cancel();
								};
							} ],
							templateUrl : 'app/components/artista/edicaoArtista.html',
							scope : $scope,
							preserveScope : true,
							parent : angular.element(document.body),
							targetEvent : evento,
							clickOutsideToClose : true,
							fullscreen : $scope.dialogsFullscreen
						});
					};

					$scope.adicionarArtista = function(evento) {
						$scope.artista = new Artista();

						abrirEdicaoArtista(evento).then(function() {
							$log.info('Salvando novo artista:', $scope.artista);
							$scope.artista.$save(null, crudSuccessCallback, function(reason) {
								// TODO Erro
							});
						}, function() {
							$scope.artista = null;
						});
					};

					$scope.editarArtista = function(evento) {
						$scope.artista = $scope.artistasSelecionados[0];

						abrirEdicaoArtista(evento).then(function() {
							$log.info('Salvando alterações no artista:', $scope.artista);
							$scope.artista.$update();
						}, function() {
							$scope.artista = null;
						});
					};

					$scope.removerArtista = function(evento) {
						var confirm = $mdDialog.confirm().textContent('Confirmar exclusão de ' + $scope.artistasSelecionados.length + ' artista(s)?')
								.ariaLabel('Confirmar exclusão').targetEvent(evento).ok('Excluir').cancel('Cancelar');
						$mdDialog.show(confirm).then(function() {
							$log.info('Excluindo artista(s):', $scope.artistasSelecionados);

							if ($scope.artistasSelecionados.length > 1) {
								Artista.deleteAll({
									id : $scope.artistasSelecionados.map(function(artista) {
										return artista.id;
									})
								}, crudSuccessCallback, function(reason) {
									// TODO Erro
								});
							} else {
								$scope.artistasSelecionados[0].$delete().then(crudSuccessCallback);
							}
						}, function() {

						});
					};
				} ]);
