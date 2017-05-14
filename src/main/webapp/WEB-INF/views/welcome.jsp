<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<!doctype html>
<html ng-app="app">
  <head>
    <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.0/angular.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.0/angular-touch.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.0/angular-animate.js"></script>
    <script src="http://ui-grid.info/docs/grunt-scripts/csv.js"></script>
    <!-- <script src="http://ui-grid.info/docs/grunt-scripts/pdfmake.js"></script> -->
    <script src="http://ui-grid.info/docs/grunt-scripts/vfs_fonts.js"></script>
    <script src="http://ui-grid.info/release/ui-grid.js"></script>
    <link rel="stylesheet" href="http://ui-grid.info/release/ui-grid.css" type="text/css">
    <link rel="stylesheet" href="/DynamicTable/static/css/main.css" type="text/css">
  </head>
  <body>

<div ng-controller="MainCtrl">
	<h3>Welcome to Naughty Coders</h3>
	<form name="myForm" ng-submit="submitMyForm()" enctype="application/x-www-form-urlencoded">
		<input ng-model="fields.sql"  type="text" size=500 style="height:40px;width:100%" id = "sql"></input>
		<input type="submit" value="Load SQL"></input>
	</form>
  <div 
  	ui-if="gridOptions.data.length>0" 
  	id="grid1" 
  	ui-grid-auto-resize 
  	ng-style="getTableStyle()"
  	ui-grid="gridOptions" 
  	class="grid"
  	style="width:100%;" 
  	ui-grid-resize-columns ui-grid-move-columns>
  	</div>
</div> 

    <script src="/DynamicTable/static/js/app.js"></script>
  </body>
</html>