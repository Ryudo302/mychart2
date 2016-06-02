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
			url : 'rest/artista',
			method : 'GET',
			isArray : true
		},
		save : {
			url : 'rest/artista',
			method : 'POST'
		},
		update : {
			method : 'PUT'
		},
		deleteAll : {
			url : '/rest/artista',
			method : 'DELETE',
			isArray : true
		},
		getCancoes : {
			url : 'rest/artista/:id/cancoes',
			method : 'GET',
			isArray : true
		}
	});
} ]);