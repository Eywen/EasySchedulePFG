(function() {
    'use strict';

    angular
        .module('easyscheduleApp')
        .controller('ProfesorDialogController', ProfesorDialogController);

    ProfesorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Profesor', 'Asignatura'];

    function ProfesorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Profesor, Asignatura) {
        var vm = this;

        vm.profesor = entity;
        vm.clear = clear;
        vm.save = save;
        vm.asignaturas = Asignatura.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.profesor.id !== null) {
                Profesor.update(vm.profesor, onSaveSuccess, onSaveError);
            } else {
                Profesor.save(vm.profesor, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('easyscheduleApp:profesorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
