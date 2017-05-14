var app = angular.module('app', ['ngTouch', 'ui.grid', 'ui.grid.resizeColumns', 'ui.grid.moveColumns']);

app.controller('MainCtrl', ['$scope', '$http', '$interval',function ($scope, $http, $interval) {
  $scope.gridOptions = {
    enableSorting: false,
    enableHorizontalScrollbar : 1,
    enableVerticalScrollbar : 0,
    onRegisterApi: function (gridApi) {
        $scope.gridApi = gridApi;
        // below mentioned code resolve my issue(window resizer issue) 
        $interval( function() {
            $scope.gridApi.core.handleWindowResize();
        }, 10, 500);
     }
   
  };
  
  $scope.submitMyForm=function(){
      /* while compiling form , angular created this object*/
      $http({
          method: 'POST',
          url: 'http://localhost:8080/DynamicTable/api/post-dtable',
          data:$scope.fields,
          headers: {'Content-Type': 'application/json'}
      }).then(function mySucces(response) {
    	  
    	  $scope.gridOptions.columnDefs = response.data.columnDefs
    	  $scope.gridOptions.data = response.data.data;
    	  
    	  $scope.tableWidth = 0;
    	  response.data.columnDefs.forEach(function buildWidth(item,index){
    		  $scope.tableWidth = $scope.tableWidth + item.field.length;
    	  });
    	  $scope.tableWidth = $scope.tableWidth*6;
    	  $scope.rowlength = response.data.data.length;
    	  
      }, function myError(response) {
    	  $scope.gridOptions.data = response.statusText;
      });
      
  }
  
  $http({
      method : "GET",
      url : "http://localhost:8080/DynamicTable/api/dtable"
  }).then(function mySucces(response) {
	  $scope.gridOptions.columnDefs = response.data.columnDefs
	  $scope.gridOptions.data = response.data.data;
	  
	  $scope.tableWidth = 0;
	  response.data.columnDefs.forEach(function buildWidth(item,index){
		  $scope.tableWidth = $scope.tableWidth + item.field.length;
	  });
	  $scope.tableWidth = $scope.tableWidth*6;
	  $scope.rowlength = response.data.data.length;
	  
  }, function myError(response) {
	  $scope.gridOptions.data = response.statusText;
  });
  
  $scope.getTableStyle= function() {
      var marginHeight = 20; // optional
      var length =  $scope.rowlength; // this is unique to my cellTemplate
      //alert((length * $scope.gridOptions.rowHeight + $scope.gridOptions.headerRowHeight + marginHeight ) + "px");
      return {
          height: (length * $scope.gridOptions.rowHeight + $scope.gridOptions.headerRowHeight + marginHeight ) + "px"
      };
  };
  
}]);
