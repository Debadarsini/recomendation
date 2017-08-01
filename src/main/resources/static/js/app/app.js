var app = angular.module('crudApp',['ui.router','ngStorage']);

app.constant('urls', {
    BASE: 'http://localhost:8080/reco/',
    FEATURE_SERVICE_API : 'http://localhost:8080/reco/feature/'
});

app.config(['$stateProvider', '$urlRouterProvider',
    function($stateProvider, $urlRouterProvider) {

        $stateProvider
            .state('home', {
                url: '/',
                templateUrl: 'partials/list',
                controller:'FeatureController',
                controllerAs:'ctrl',
                resolve: {
                    features: function ($q, FeatureService) {
                        console.log('Load all features');
                        var deferred = $q.defer();
                        FeatureService.loadAllFeatures().then(deferred.resolve, deferred.resolve);
                        return deferred.promise;
                    }
            		 
                }
            });
        $urlRouterProvider.otherwise('/');
    }]);

