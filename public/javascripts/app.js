(function () {
    "use strict";
    var app = angular.module('app', ['angularUtils.directives.dirPagination']);

    app.config(['$compileProvider', function ($compileProvider) {
        $compileProvider.aHrefSanitizationWhitelist(/^\s*(|blob|):/);
    }]);

    app.directive('uploadDirective', function (httpPostFactory) {
        return {
            restrict: 'A',
            scope: false,
            link: function (scope, element, attr) {
                element.bind('change', function () {
                    var formData = new FormData();
                    formData.append('file', element[0].files[0]);
                    httpPostFactory('/processBook', formData, function (response) {
                        scope.serverData = response;
                        scope.$apply();
                    });
                });
            }
        };
    });

    app.factory('httpPostFactory', function ($http, $timeout) {
        return function (url, data, callback) {
            $http({
                url: url,
                method: "POST",
                data: data,
                headers: {'Content-Type': undefined}
            }).then(function (response) {
                $timeout(function () {
                    callback(response.data);
                });
            });
        };
    });

    app.controller('tableController', function ($scope, httpPostFactory) {
        $scope.serverData = [];
        $scope.wordsPerPage = 15;
        $scope.pagination = {current: 1};
        $scope.currentPageWords = [];

        var init = true;
        var clearWatch = $scope.$watch(function (scope) {
                return scope.serverData.dictionary;
            },
            function () {
                if (init) {
                    init = false;
                } else {
                    $scope.currentPageWords = $scope.serverData.dictionary.slice(0, $scope.wordsPerPage);
                    $scope.totalWords = $scope.serverData.dictionary.length;
                    clearWatch();
                }
            });

        $scope.changePage = function (pageNumber) {
            $scope.currentPageWords = $scope.serverData.dictionary.slice(
                pageNumber * $scope.wordsPerPage,
                pageNumber * $scope.wordsPerPage + $scope.wordsPerPage);
        };

        $scope.reloadPage = function () {
            window.location.reload();
        };

        $scope.getExcel = function () {
            httpPostFactory('/toExcel', $scope.serverData.dictionary, function (response) {
                console.log(response);
            });
        }

    });
}());

