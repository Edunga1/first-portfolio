'use strict';

// Declare app level module which depends on filters, and services
angular.module('App', ['ngRoute','App.filters', 'App.services', 'App.directives']).
	config(
		function($routeProvider) {
			$routeProvider.when('/home', {templateUrl: 'partials/home.html', controller: GenericViewCtrl});
			$routeProvider.otherwise({redirectTo: '/home'});
		}
	);