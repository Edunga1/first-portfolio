'use strict';

/* Directives */


angular.module('App.directives', []).
	directive('navbar', function($window){
		return {
			restrict: 'A',
			link: function(scope, element, attrs){
				var position = element[0].style.position;
				var offsetTop;
				element.css('z-index', 999);
				element.css('top', 0);
				element.css('left', 0);
				$window.addEventListener('scroll', function(){
					if(element.css('position') != 'fixed'){
						var elem = element[0];
						offsetTop = element[0].offsetTop;
						while(elem = elem.offsetParent){
							offsetTop += elem.offsetTop;
						}
					}
					if(this.pageYOffset > offsetTop){
						element[0].style.position = 'fixed';
						element[0].classList.add('docked');
					}else{
						element[0].style.position = position;
						element[0].classList.remove('docked');
					}
				});
			}
		}
	});
