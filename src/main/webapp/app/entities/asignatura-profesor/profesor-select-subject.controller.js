(function() {
    'use strict';

    angular
        .module('easyscheduleApp')
        .controller('ProfesorSelectSubjectController', ProfesorSelectSubjectController);

    ProfesorSelectSubjectController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'AsignaturaProfesor','Profesor', 'Asignatura'];

    function ProfesorSelectSubjectController ($timeout, $scope, $stateParams, $uibModalInstance, entity, AsignaturaProfesor, Profesor, Asignatura) {
        var vm = this;
       
        vm.datos = entity;
        vm.clear = clear;
        vm.save = save;
        console.log('vm: ', vm);
        console.log('stateParams: ', $stateParams  );
        console.log('vm.datos: ', vm.datos);
        



        /*vm.profesor = entity;*/
     
        vm.asignaturaId = $stateParams.id_asig;
        vm.profesorId = $stateParams.id_prof;
        

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });
        console.log('vm prof sub: ', vm);
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            //asignación sin ningun tipo de coprobacion de num de creidtos o asignaturas permitidas. Ya se hará más adelante.
            if (vm.profesorId !== null && vm.asignaturaId !==null) {
                Profesor.get({id:  vm.profesorId}, function (result) {
                    vm.profesor = result;  
                    console.log('profesor ',vm.profesor);
                    Asignatura.get({id:  vm.asignaturaId}, function (result) {
                        vm.miAsignatura = result;
                        console.log('asignatura ',vm.miAsignatura);
                        vm.profesor.asignaturas.push(vm.miAsignatura); 
                        console.log('vm.profesor.asignaturas ',vm.profesor.asignaturas);
                        Profesor.update(vm.profesor,onSaveSuccess,onSaveError);
                    });
                });
            }
            
            /*if (vm.profesorId !== null && vm.asignaturaId !==null) {
                AsignaturaProfesor.addasigProf(vm.datos,onSaveSuccess,onSaveError);
                AsignaturaProfesor.addasigProf(vm.profesorId,vm.asignaturaId,onSaveSuccess,onSaveError);
            } else {
                 console.log('No está lista para llamar service de select asig');
            }*/
            /*Profesor.get({id:  vm.profesorId}, function (result) {
                vm.profesor = result;  
                //console.log('profesor ',vm.profesor);  
                //obtener el numero de cursos que tiene esta asignatura
                AsignaturaProfesor.getconfirmacion({id_asignatura: vm.asignaturaId,prioridad_profesor: vm.profesor.prioridad},
                 function (result) {
                    vm.confirmation = result;
                    //console.log('data from resource to json',  vm.confirmation.estado) ;
                    //si el numero de cursos para esta asignatura está completo
                    if (vm.confirmation.estado === false){
                        console.log('data en if ',vm.confirmation.estado);
                        //Elimina la asignatura del profesor con menor prioridad
                        AsignaturaProfesor.getconfirmacionremovemenorprior({id_asignatura: vm.asignaturaId,prioridad_profesor: vm.profesor.prioridad},
                         function (result) {
                            vm.asigborrada =  result;
                            console.log('vm.asigborrada ',vm.asigborrada);
                            if (vm.asigborrada.estado === true){
                                vm.profesor.asignaturas.push(vm.miAsignatura); 
                                //agregamos la asignatura al profesor
                                Profesor.update(vm.profesor,onSaveSuccess,onSaveError);
                            }else{
                                $scope.$emit('easyscheduleApp:asignaturaUpdate', result);
                                alert ("No se puede realizar la asignación, Los cursos están completos");
                                console.log('else de asigborrada ');
                                $uibModalInstance.close(result);
                            }
                        });
                    }else{
                        console.log('vm.miAsignatura ',vm.miAsignatura);
                        vm.profesor.asignaturas.push(vm.miAsignatura); 
                        //agregamos la asignatura al profesor
                        Profesor.update(vm.profesor,onSaveSuccess,onSaveError);
                    }
                }); 
            }); */
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
