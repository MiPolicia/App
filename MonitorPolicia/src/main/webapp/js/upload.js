/**
 * 
 */


var app = angular.module('UploadApp', []);

app.controller('UploadController',['$scope', '$http', function ($scope,$http) {
  $scope.categories=[];
  $scope.data={};
  
  $scope.loadGeopos = function(){
	  navigator.geolocation.getCurrentPosition(function(pos){
		  var crd = pos.coords;
		  $scope.$apply(function() { 
			$scope.data.lat = crd.latitude;
		  	$scope.data.lon = crd.longitude;
		  });
		  var pos = new google.maps.LatLng(crd.latitude,crd.longitude);
		  var mapOptions = {
	   	          center: pos,
	   	          zoom: 14,
	   	          mapTypeId: google.maps.MapTypeId.ROADMAP
		  };
		  var map = new google.maps.Map(
				  document.getElementById("map"),
            mapOptions);
		  var marker = new google.maps.Marker({
			  position:pos,
			  title:"Tu posición",
			  draggable:true,
			  map:map			  
		  });
		  google.maps.event.addListener(marker, 'dragend', function() {
		  	$scope.$apply(function() { 
			  $scope.data.lat = marker.getPosition().lat();
			  $scope.data.lon = marker.getPosition().lng();
		  	});
		  });
		  
	  }, function(){
		  
	  });
  }
  $scope.upload = function() {
	  $http({method: "POST", url:"/api/admin/categories"}).
      success(function(data, status) {
      	$scope.categories=data;
      }).
      error(function(data, status) {
        
    });
  }
  $scope.loadCategories = function() {
      
      $http({method: "GET", url:"/api/admin/categories"}).
        success(function(data, status) {
        	$scope.categories=data;
        }).
        error(function(data, status) {
          
      });
    };
    $scope.loadGeopos();
    $scope.loadCategories();
    
   
   	        
   
}]);