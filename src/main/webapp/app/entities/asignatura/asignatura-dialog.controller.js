(function() {
    'use strict';

    angular
        .module('easyscheduleApp')
        .controller('AsignaturaDialogController', AsignaturaDialogController);

    AsignaturaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Asignatura', 'Profesor'];

    function AsignaturaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Asignatura, Profesor) {
        var vm = this;

        vm.asignatura = entity;
        vm.clear = clear;
        vm.save = save;
        vm.profesors = Profesor.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            console.log (vm.profesors);
            console.log (vm.asignatura);
            vm.isSaving = true;
            if (vm.asignatura.id !== null) {
                Asignatura.update(vm.asignatura, onSaveSuccess, onSaveError);
            } else {
                Asignatura.save(vm.asignatura, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('easyscheduleApp:asignaturaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
