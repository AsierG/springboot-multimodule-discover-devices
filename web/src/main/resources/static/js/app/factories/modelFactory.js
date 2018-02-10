"use strict";
(function () {

    var modelFactory = function ($http, $q, $log) {
        $log.info('modelFactory initizialed');
        var factory = {};

        factory.getModels = function () {
            return $http.get('/model/findAll');
        };

        return factory;
    };

    angular.module('asiergApp').factory('modelFactory', modelFactory);
}());

