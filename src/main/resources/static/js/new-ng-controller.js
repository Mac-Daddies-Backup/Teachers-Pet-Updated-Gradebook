angular.module('TeachersPetApp', [])
   .controller('NewController', function($scope, $http, $window){
        $('.collapse').on('show.bs.collapse', function () {
            $('.collapse.in').collapse('hide');
        });
 }
