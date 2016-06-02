/// <reference path="../../../typings/angularjs/angular.d.ts" />
/// <reference path="../../../typings/angularjs/angular-resource.d.ts" />

module MyChart.cancao {
    'use strict';

    angular.module('MyChart.cancao')

        .factory('Cancao', ['$resource', function($resource) {
            return $resource('/rest/cancao/:id', {
                id: '@id'
            }, {
                    getAll: {
                        url: 'rest/cancao',
                        method: 'GET',
                        isArray: true
                    }
                })
        }]);
}