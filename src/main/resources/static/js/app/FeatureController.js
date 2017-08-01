'use strict';

angular.module('crudApp').controller('FeatureController',
    ['FeatureService', '$scope',  function( FeatureService, $scope) {

        var self = this;
        self.feature = {};
        self.features=[];

        self.submit = submit;
        self.getAllFeatures = getAllFeatures;
        self.createFeature = createFeature;
        self.updateFeature = updateFeature;
        self.removeFeature = removeFeature;
        self.editFeature = editFeature;
        self.reset = reset;

        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;

        self.onlyIntegers = /^\d+$/;
        self.onlyNumbers = /^\d+([,.]\d+)?$/;

        function submit() {
            console.log('Submitting');
            if (self.feature.id === undefined || self.feature.id === null) {
                console.log('Saving New Feature', self.feature);
                createFeature(self.feature);
            } else {
                updateFeature(self.feature, self.feature.id);
                console.log('Feature updated with id ', self.feature.id);
            }
        }

        function createFeature(feature) {
            console.log('About to create feature');
            FeatureService.createFeature(feature)
                .then(
                    function (response) {
                        console.log('Feature created successfully');
                        self.successMessage = 'Feature created successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.feature={};
                        $scope.myForm.$setPristine();
                    },
                    function (errResponse) {
                        console.error('Error while creating Feature');
                        self.errorMessage = 'Error while creating Feature: ' + errResponse.data.errorMessage;
                        self.successMessage='';
                    }
                );
        }


        function updateFeature(feature, id){
            console.log('About to update feature');
            FeatureService.updateFeature(feature)
                .then(
                    function (response){
                        console.log('Feature updated successfully');
                        self.successMessage='Feature updated successfully';
                        self.errorMessage='';
                        self.done = true;
                        $scope.myForm.$setPristine();
                    },
                    function(errResponse){
                        console.error('Error while updating Feature');
                        self.errorMessage='Error while updating Feature '+errResponse.data;
                        self.successMessage='';
                    }
                );
        }


        function removeFeature(id){
            console.log('About to remove Feature with id '+id);
            FeatureService.removeFeature(id)
                .then(
                    function(){
                        console.log('Feature '+id + ' removed successfully');
                    },
                    function(errResponse){
                        console.error('Error while removing feature '+id +', Error :'+errResponse.data);
                    }
                );
        }


        function getAllFeatures(){
            return FeatureService.getAllFeatures();
        }

        function editFeature(id) {
            self.successMessage='';
            self.errorMessage='';
            FeatureService.getFeature(id).then(
                function (feature) {
                    self.feature = feature;
                },
                function (errResponse) {
                    console.error('Error while removing feature ' + id + ', Error :' + errResponse.data);
                }
            );
        }
        function reset(){
            self.successMessage='';
            self.errorMessage='';
            self.feature={};
            $scope.myForm.$setPristine(); //reset Form
        }
    }


    ]);