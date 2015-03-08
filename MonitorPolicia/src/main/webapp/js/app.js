/**
 * 
 */

var app = angular.module('MonitorApp', []);
var markers = [];
var map;
app.controller('MonitorController', [ '$scope', '$http',
		function($scope, $http) {
			$scope.resume = [];
			$http({
				method : "GET",
				url : "/api/monitor/resume"
			}).success(function(data, status) {
				$scope.resume = data;
			}).error(function(data, status) {

			});
			$scope.loadCategory = function(category) {
				$http({
					method : "GET",
					url : "/api/monitor/category/" + category
				}).success(function(data, status) {
					clearMarkers();
					$scope.loadMarkers(data);
				}).error(function(data, status) {

				});
			};
			$scope.loadMarkers = function(newmarkers) {
				$.each(newmarkers, function(k, v) {
					var data = v;
					var pos = new google.maps.LatLng(data.lat, data.lon);
					var marker = new google.maps.Marker({
						position : pos,
						title : data.description,
						map : map
					});
					google.maps.event.addListener(marker, 'click', function() {

						$scope.$apply(function() {
							$scope.selectedReport = data;							
						});
						
						$("#imageModal").modal();
					});
				});
			}
		} ]);

function clearMarkers() {
	for (m in markers) {
		markers[m].setMap(null);
	}
	markers = [];
}

function init() {
	var mapOptions = {
		center : new google.maps.LatLng(19.432923, -99.133152),
		zoom : 10,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	};
	map = new google.maps.Map(document.getElementById("map"), mapOptions);
}
