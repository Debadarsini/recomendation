'use strict';

angular.module('crudApp').factory('FeatureService',
    ['$localStorage', '$http', '$q', 'urls',
        function ($localStorage, $http, $q, urls) {

            var factory = {
                loadAllFeatures: loadAllFeatures,
                getAllFeatures: getAllFeatures,
                getFeature: getFeature,
                createFeature: createFeature,
                updateFeature: updateFeature,
                removeFeature: removeFeature
            };

            return factory;

            function loadAllFeatures() {
                console.log('Fetching all features');
                var deferred = $q.defer();
                $http.get(urls.FEATURE_SERVICE_API)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all features');
                            $localStorage.features = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading features');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function getAllFeatures(){
                return $localStorage.features;
            }

            function getFeature(id) {
                console.log('Fetching Feature with id :'+id);
                var deferred = $q.defer();
                $http.get(urls.FEATURE_SERVICE_API + id)
                    .then(
                        function (response) {
                            console.log('Fetched successfully Feature with id :'+id);
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while loading feature with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function createFeature(feature) {
                console.log('Creating Feature');
                var deferred = $q.defer();
                $http.post(urls.FEATURE_SERVICE_API, feature)
                    .then(
                        function (response) {
                            loadAllFeatures();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                           console.error('Error while creating Feature : '+errResponse.data.errorMessage);
                           deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function updateFeature(feature, id) {
                console.log('Updating Feature with id '+id);
                var deferred = $q.defer();
                $http.put(urls.FEATURE_SERVICE_API, feature)
                    .then(
                        function (response) {
                            loadAllFeatures();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while updating Feature with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function removeFeature(id) {
                console.log('Removing Feature with id '+id);
                var deferred = $q.defer();
                $http.delete(urls.FEATURE_SERVICE_API + id)
                    .then(
                        function (response) {
                            loadAllFeatures();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while removing Feature with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

        }
    ]);