(function() {
    'use strict';

    angular
        .module('easyscheduleApp')
        .controller('AsignacionDeleteController',AsignacionDeleteController);

    AsignacionDeleteController.$inject = ['$uibModalInstance', 'entity', 'AsignaturaProfesor', 'Profesor', 'Asignatura'];

    function AsignacionDeleteController($uibModalInstance, entity, AsignaturaProfesor, Profesor, Asignatura) {
        var vm = this;

        vm.asignacion = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        console.log('vm.asignacion ', vm.asignacion);
        /*Profesor.get({id: vm.asignacion.id_profesor}, function (result) {
            vm.profesor = result;
        });*/
        Asignatura.get({id: vm.asignacion.id_asignatura}, function (result) {
            vm.asignatura = result;
            console.log('vm.asignatura ',vm.asignatura);
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id_prof,id_asig) {
            console.log("id_prof ",id_prof);
            console.log("id_asig ",id_asig);
           AsignaturaProfesor.delete({id_profesor: id_prof,id_asignatura :id_asig},
                function () {
                    $uibModalInstance.close(true);
                });
            // AsignaturaProfesor.delete(vm.datos);
            
                
        }
    }
})();