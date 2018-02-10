"use strict";
(function () {

    var discoverFactory = function ($http, $q, $log) {
        $log.info('discoverFactory initizialed');
        var factory = {};

        factory.getDiscoverStatus = function () {
            return $http.get('/discover/status');
        };

        factory.startDiscover = function (ipRange) {
            var config = {
                params: {ipRange: ipRange}
            };
            return $http.get('/discover/start', config);
        };

        factory.pruebaPost = function (data) {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post('/pruebaPost', data).then(function (response) {
                defered.resolve(response);
            }, function error(err) {
                defered.reject(err);
            });
            return promise;
        };

        return factory;
    };

    angular.module('asiergApp').factory('discoverFactory', discoverFactory);
}());

