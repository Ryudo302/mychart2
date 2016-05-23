angular.module('MyChart.artista')

/**
 * Resource com operações relacionadas a Artista.
 */
.factory('Artista', [ '$resource', function($resource) {
	'use strict';

	return $resource('/rest/artista/:id', {
		id : '@id'
	}, {
		getAll : {
			url : 'rest/artistas',
			method : 'GET',
			isArray : true
		},
		save : {
			url : 'rest/artistas',
			method : 'POST'
		},
		update : {
			method : 'PUT'
		},
		deleteAll : {
			url : '/rest/artistas',
			method : 'DELETE',
			isArray : true
		}
	});
} ]);