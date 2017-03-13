(function() {
    'use strict';

    angular
        .module('easyscheduleApp')
        .controller('ProfesorDetailController', ProfesorDetailController);

    ProfesorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Profesor'];

    function ProfesorDetailController($scope, $rootScope, $stateParams, previousState, entity, Profesor) {
        var vm = this;

        vm.profesor = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('easyscheduleApp:profesorUpdate', function(event, result) {
            vm.profesor = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
