var app = angular.module('asiergApp', ['ui.router','angularUtils.directives.dirPagination']);

app.config(function ($stateProvider, $urlRouterProvider, $locationProvider) {

    //	configuración para url amigables
    // $locationProvider.html5Mode(true);
    $locationProvider.html5Mode({
        enabled: true,
        requireBase: true
    });

    // En caso de rutas no validas, vamos a la página raiz
    $urlRouterProvider.otherwise('/discover');

    $stateProvider
        .state('default', {
            url: '/',
            controller: 'HomeCtrl as home',
            templateUrl: '/js/app/home/home.html'
        })
        .state('discover', {
            url: '/discover',
            controller: 'DiscoverCtrl as discover',
            templateUrl: '/js/app/discover/discover.html'
        })
        .state('update', {
            url: '/update',
            controller: 'UpdateCtrl as update',
            templateUrl: '/js/app/update/update.html'
        })
        .state('pruebas', {
            url: '/pruebas',
            controller: 'PruebasCtrl as ctrl',
            templateUrl: '/js/app/pruebas/pruebas.html'
        });

});
