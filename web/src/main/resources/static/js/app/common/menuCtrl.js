(function () {
    var menuCtrl = function ($state) {
        this.isActive = function (state) {
            return $state.is(state);
        }
    };
    angular.module('asiergApp').controller('MenuCtrl', menuCtrl);
}());