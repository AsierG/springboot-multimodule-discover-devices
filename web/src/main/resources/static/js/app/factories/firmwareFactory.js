"use strict";
(function () {

    var firmwareFactory = function ($http, $q, $log) {
        $log.info('firmwareFactory initizialed');
        var factory = {};

        factory.getFirmwares = function () {
            return $http.get('/firmware/findAll');
        };

        return factory;
    };

    angular.module('asiergApp').factory('firmwareFactory', firmwareFactory);
}());

