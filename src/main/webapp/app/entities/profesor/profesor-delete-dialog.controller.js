(function() {
    'use strict';

    angular
        .module('easyscheduleApp')
        .controller('ProfesorDeleteController',ProfesorDeleteController);

    ProfesorDeleteController.$inject = ['$uibModalInstance', 'entity', 'Profesor'];

    function ProfesorDeleteController($uibModalInstance, entity, Profesor) {
        var vm = this;

        vm.profesor = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Profesor.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
