(function() {
    'use strict';

    angular
        .module('easyscheduleApp')
        .controller('AsignaturaDeleteController',AsignaturaDeleteController);

    AsignaturaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Asignatura'];

    function AsignaturaDeleteController($uibModalInstance, entity, Asignatura) {
        var vm = this;

        vm.asignatura = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Asignatura.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
