var app = angular.module('app', ["ngTable"]);
app.controller('ctrl', function ($scope, $http, NgTableParams) {

    $scope.actualJson = getJson(0);
    $scope.tableIsShown = function () {
        return $scope.actualJson ? true : false;
    };

    $scope.tableParams = new NgTableParams({}, {dataset: $scope.actualJson});

    function getJson(index) {
        $http.get('/json/' + index).success(function (data) {
            $scope.actualJson = data;
        });
    }
});