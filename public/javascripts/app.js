(function () {
    "use strict";
    var app = angular.module('app', ['angularUtils.directives.dirPagination']);

    app.directive('uploadDirective', function (httpPostFactory) {
        return {
            restrict: 'A',
            scope: false,
            link: function (scope, element, attr) {
                element.bind('change', function () {
                    var formData = new FormData();
                    formData.append('file', element[0].files[0]);
                    httpPostFactory(formData, function (response) {
                        console.log(response);
                        scope.serverData = response;
                        scope.$apply();
                    });
                });
            }
        };
    });

    app.factory('httpPostFactory', function ($http, $timeout) {
        return function (formData, callback) {
            $http({
                url: '/upload',
                method: "POST",
                data: formData,
                headers: {'Content-Type': undefined}
            }).then(function (response) {
                $timeout(function () {
                    callback(response.data);
                });
            });
        };
    });

    app.controller('tableController', function ($scope) {

        $scope.wordsPerPage = 15;
        $scope.pagination = {
            current: 1
        };
        //$scope.currentPageWords - массив объектов для данной страницы


    });
}());

