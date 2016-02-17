'use strict';

// Declare app level module which depends on filters, and services
angular.module('App', ['ngRoute','App.filters', 'App.services', 'App.directives']).
	config(
		function($routeProvider) {
			$routeProvider.when('/home', {templateUrl: 'partials/home.html', controller: GenericViewCtrl});
			$routeProvider.when('/test', {templateUrl: 'partials/test.html', controller: GenericViewCtrl});
			$routeProvider.when('/reference', {templateUrl: 'partials/reference.html', controller: GenericViewCtrl});
			$routeProvider.when('/kpool', {templateUrl: 'partials/kpool.html', controller: GenericViewCtrl});
			$routeProvider.when('/travelmaker', {templateUrl: 'partials/travelmaker.html', controller: GenericViewCtrl});
			$routeProvider.when('/cctv', {templateUrl: 'partials/cctv.html', controller: GenericViewCtrl});
			$routeProvider.when('/etc', {templateUrl: 'partials/etc.html', controller: GenericViewCtrl});
			$routeProvider.otherwise({redirectTo: '/home'});
		}
	);