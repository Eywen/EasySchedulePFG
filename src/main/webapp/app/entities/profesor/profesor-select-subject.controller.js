(function() {
    'use strict';

    angular
        .module('easyscheduleApp')
        .controller('ProfesorSelectSubjectController', ProfesorSelectSubjectController);

    ProfesorSelectSubjectController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Profesor', 'Asignatura'];

    function ProfesorSelectSubjectController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Profesor, Asignatura) {
        var vm = this;

        vm.datos = entity;
        vm.clear = clear;
        vm.save = save;




        console.log('vm: ', vm);
        console.log('stateParams: ', $stateParams  );
        console.log('vm.datos: ', vm.datos);

        vm.asignaturaId = $stateParams.id_asig;
        vm.profesorId = $stateParams.id_prof;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });
    
        function save () {
            vm.isSaving = true;
            if ( vm.profesorId !== null && vm.asignaturaId !==null) {
                console.log('lista para llamar service de select asig');
                console.log('vm.profesorId = ',vm.id_profesor);
                console.log('vm.asignaturaId  = ',vm.id_asignatura );

                //Profesor.update(vm.profesor, onSaveSuccess, onSaveError);
                Profesor.addasigProf(vm.profesorId,vm.asignaturaId,onSaveSuccess,onSaveError);
                //AsignaturaProfesor.update(vm.asignaturaId,vm.profesorId, onSaveSuccess, onSaveError);
            } else {
                 console.log('No está lista para llamar service de select asig');
                //Profesor.save(vm.profesor, onSaveSuccess, onSaveError);
                //AsignaturaProfesor.save(vm.asignaturaId,vm.profesorId,onSaveSuccess,onSaveError);
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
        function clear () {
            $uibModalInstance.dismiss('cancel',vm.idp); 
        }


    }
})();
