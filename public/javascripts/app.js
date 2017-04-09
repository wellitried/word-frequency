var app = angular.module('app', []);
app.controller('ctrl', function ($scope, $http) {
    $http.get('/json/0').success(function (data) {
        $scope.json = data;
    });
});