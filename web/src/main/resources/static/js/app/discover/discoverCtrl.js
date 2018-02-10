"use strict";
(function () {

    var discoverCtrl = function ($log, discoverFactory, $filter, $interval, $scope) {
        $log.info('discoverCtrl initizialed');
        var vm = this;
        vm.cidrPattern = /^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])(\/([0-9]|[1-2][0-9]|3[0-2]))$/i;
        vm.ipRange = '192.168.5.0-192.168.5.100';

        vm.Timer = null;
        vm.process;
        vm.processStatus;
        vm.finishedFutures = 0;
        vm.futuresSize = 0;

        vm.onInit = function () {
            vm.getDiscoverProcessStatus();
        };

        vm.getDiscoverProcessStatus = function () {
            discoverFactory.getDiscoverStatus().then(function (response) {
                vm.process = response.data;
                vm.processStatus = vm.process.operationStatus;
                vm.finishedFutures = vm.process.finishedFutures;
                vm.futuresSize = vm.process.futuresSize;
            }, function (error) {
                $log.info('Error with status: ' + error.statusText);
                vm.process.operationStatus = 'NOT_AVAILABLE';
            });
        };

        vm.startCheckingProcessStatus = function () {
            vm.processStatus = 'PROCESSING';
            vm.finishedFutures = 0;
            vm.futuresSize = 0;
            vm.startTime = $filter('date')(new Date(), 'HH:mm:ss');
            vm.endTime = null;
            vm.Timer = $interval(function () {
                vm.getDiscoverProcessStatus();
            }, 1500);
        };

        vm.stopCheckingStatus = function () {
            $log.info('stop checking status');
            vm.endTime = $filter('date')(new Date(), 'HH:mm:ss');
            if (angular.isDefined(vm.Timer)) {
                $interval.cancel(vm.Timer);
            }
        };

        $scope.$on("$destroy", function () {
            vm.stopCheckingStatus();
        });

        $scope.$watch(function () {
            return vm.processStatus;
        }, function (current, previous) {
            $log.info('vm.processStatus was %s', previous);
            $log.info('vm.processStatus is now %s', current);
            if (current === 'FINALIZED') {
                vm.stopCheckingStatus();
            }
        });

        vm.clickStartDiscover = function () {
            discoverFactory.startDiscover(vm.ipRange).then(function (response) {
                $log.info('startDiscover: ' + response.data);
                if (response.data) {
                    vm.startCheckingProcessStatus();
                }
            }, function (error) {
                $log.info('Error with status: ' + error.statusText);
            });
        };

    };

    angular.module('asiergApp').controller('DiscoverCtrl', discoverCtrl);
}());
