"use strict";
(function () {

    var equipmentFactory = function ($http, $q, $log) {
        $log.info('equipment initizialed');
        var factory = {};

        factory.getEquipments = function (model, firmware, ipRange) {
            var config = {
                params: {model: model, firmware: firmware, ipRange: ipRange}
            };
            return $http.get('/equipment/find', config);
        };

        return factory;
    };

    angular.module('asiergApp').factory('equipmentFactory', equipmentFactory);
}());

