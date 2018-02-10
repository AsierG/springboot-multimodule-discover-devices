"use strict";
(function () {

    function interceptorsConfiguration($httpProvider) {
        $httpProvider.interceptors.push(logInterceptor);
    };

    function logInterceptor($log) {
        var interceptor = {};
        interceptor.request = function (request) {
            $log.info('=============================================================================================');
            $log.info('REQUEST => url:' + request.url + ' , method: ' + request.method +
                ' , headers.Accept: ' + request.headers.Accept);
            if (request.data) {
                $log.info('REQUEST DATA:');
                angular.forEach(request.data, function (value, key) {
                    $log.info(key + " : " + value);
                });
            }
            $log.info('=============================================================================================');
            return request;
        };

        interceptor.responseError = function (response) {
            $log.error("exception: " + response.status + " , url :" + response.config.url);
        };


        return interceptor;
    };

    angular.module('asiergApp').config(interceptorsConfiguration);
}());