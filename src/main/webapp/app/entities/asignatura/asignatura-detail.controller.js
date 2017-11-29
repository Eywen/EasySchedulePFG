(function() {
    'use strict';

    angular
        .module('easyscheduleApp')
        .controller('AsignaturaDetailController', AsignaturaDetailController);

    AsignaturaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Asignatura', 'Profesor'];

    function AsignaturaDetailController($scope, $rootScope, $stateParams, previousState, entity, Asignatura, Profesor) {
        var vm = this;

        vm.asignatura = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('easyscheduleApp:asignaturaUpdate', function(event, result) {
            vm.asignatura = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
