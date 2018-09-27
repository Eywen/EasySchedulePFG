(function() {
    'use strict';

    angular
        .module('easyscheduleApp')
        .controller('ProfesorAsignaturaConflictosController', ProfesorAsignaturaConflictosController);

    ProfesorAsignaturaConflictosController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Profesor', 'Asignatura'];

    function ProfesorAsignaturaConflictosController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Profesor, Asignatura) {

    }
})();