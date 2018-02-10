"use strict";
(function () {

    var homeCtrl = function ($log, firmwareFactory, modelFactory, equipmentFactory) {
        $log.info('homeCtrl initizialed');
        var vm = this;

        vm.models = [];
        vm.firmwares = [];
        vm.equipments = [];
        vm.selectedFirmware;
        vm.selectedModel;
        vm.ipRange;

        vm.onInit = function () {
            getFirmwares();
            getModels();
        };

        function getFirmwares() {
            firmwareFactory.getFirmwares().then(function (response) {
                vm.firmwares = response.data;
            }, function (error) {
                $log.info('Error with status: ' + error.statusText);
            });
        };

        function getModels() {
            modelFactory.getModels().then(function (response) {
                vm.models = response.data;
            }, function (error) {
                $log.info('Error with status: ' + error.statusText);
            });
        };

        vm.clickSearchEquipments = function () {
            var firmwareVersion = '';
            if (typeof vm.selectedFirmware !== 'undefined') {
                firmwareVersion = vm.selectedFirmware.firmwareVersion;
            }
            var modelProductIdentifier = '';
            if (typeof vm.selectedModel !== 'undefined') {
                modelProductIdentifier = vm.selectedModel.productIdentifier;
            }
            var ipRange = '';
            if (typeof vm.ipRange !== 'undefined') {
                ipRange = vm.ipRange;
            }

            equipmentFactory.getEquipments(modelProductIdentifier, firmwareVersion, ipRange).then(function (response) {
                $log.info('getEquipments: ' + response.data);
                vm.equipments = response.data;
            }, function (error) {
                $log.info('Error with status: ' + error.statusText);
                vm.equipments = [];
            });
        };

        vm.sort = function (keyname) {
            vm.sortKey = keyname;   //set the sortKey to the param passed
            vm.reverse = !vm.reverse; //if true make it false and vice versa
        };


    };

    angular.module('asiergApp').controller('HomeCtrl', homeCtrl);
}());