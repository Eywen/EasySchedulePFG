(function() {
    'use strict';

    angular
        .module('easyscheduleApp')
        .controller('EditAsignacionDialogController', EditAsignacionDialogController);

    EditAsignacionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Asignatura', 'Profesor','AsignaturaProfesor'];

    function EditAsignacionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Asignatura, Profesor, AsignaturaProfesor) {
        var vm = this;

        //vm.asignatura = entity;
        vm.clear = clear;
        vm.save = save;
        vm.asignaturas= Asignatura.query();
        vm.datos=[];
        vm.witoutAsig=[];
        vm.witoutProfAsig=[];
         vm.asigofprof =[];

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        vm.id_asignatura = $stateParams.id_asig;

        Profesor.get({id:  $stateParams.id_prof}, function (result) {
            vm.profesor = result;    
            vm.asigofprof = vm.profesor.asignaturas;
            vm.witoutAsig = vm.asignaturas;
            //elimino  de la lista las asignautras que ya tiene el profesor
            for (var z = 0; z < vm.witoutAsig.length; z ++){
                for (var i = 0; i < vm.asigofprof.length;  i++) {
                    if (vm.asigofprof[i].id == vm.witoutAsig[z].id){
                        vm.witoutAsig.splice(z,1);
                        console.log("entro ",i);
                    }
                }
            }

            Asignatura.get({id: vm.id_asignatura}, function (result) {
                vm.asig = result;
                var j=0;
                var z=0;
                for (var i = vm.asignaturas.length - 1; i >= 0; i--) {
                   /* if (vm.asignaturas[i].id != vm.asig.id ){
                        vm.witoutAsig[j] = vm.asignaturas[i];
                        j++;
                    }
                    else{
                        vm.oldAsig=vm.asignaturas[i];
                    }*/
                    if (vm.asignaturas[i].id == vm.asig.id ){
                        vm.oldAsig=vm.asignaturas[i];
                    }
                }

                console.log(" asig sin las q ya tiene el profe: ", vm.witoutProfAsig);
            });
        });
       
        

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }
        function findFirstLargeNumber(element) {
          return element = vm.oldAsig;
        }

        function save () {            
            vm.isSaving = true;
            //elimina la asignatura actual de la lista de asignaturas del profesor para enviarlo al back ya sin ella
            var indiceOldAsig = vm.profesor.asignaturas.findIndex(findFirstLargeNumber);
            vm.profesor.asignaturas.splice(indiceOldAsig, 1);
            
            console.log('profesor ',vm.profesor);
            console.log('vm.miNuevaAsig ',vm.miNuevaAsig.id);
            console.log('vm.profesor.asignaturas  nuevo ',vm.profesor.asignaturas);
            vm.asigAutomaticData = {
                id_profesor: vm.profesor.id,
                id_asignatura: vm.miNuevaAsig.id,
            };
            console.log('vm.asigAutomaticData: ',vm.asigAutomaticData);
            //AsignaturaProfesor.save(vm.asigAutomaticData,onSaveSuccess,onSaveError);
            if (vm.miNuevaAsig.id !== null && vm.id_asignatura !== null ){
                vm.profesor.asignaturas.push(vm.miNuevaAsig);
                console.log('vm.profesor.asignaturas  push ',vm.profesor.asignaturas);
                console.log('profesor ',vm.profesor);
                Profesor.update(vm.profesor,onSaveSuccess,onSaveError);
            }  
               
  
               /* AsignaturaProfesor.getconfirmacion({id_asignatura: vm.miNuevaAsig.id,prioridad_profesor: vm.profesor.prioridad},
                 function (result) {
                    vm.confirmation = result;
                    console.log('data from resource to json',  vm.confirmation.estado) ;
                    //console.log('data from resource to json',  vm.confirmation) ;
                    if (vm.confirmation.estado === false){
                        console.log('data en if ',vm.confirmation.estado);
                        AsignaturaProfesor.getconfirmacionremovemenorprior({id_asignatura: vm.miNuevaAsig.id,prioridad_profesor: vm.profesor.prioridad},
                         function (result) {
                            vm.asigborrada =  result;
                            console.log('vm.asigborrada ',vm.asigborrada);
                            if (vm.asigborrada.estado === true){
                                var indiceOldAsig = vm.profesor.asignaturas.findIndex(findFirstLargeNumber);
                                vm.profesor.asignaturas.splice(indiceOldAsig, 1);
                                vm.profesor.asignaturas.push(vm.miAsignatura); 
                                console.log('vm.profesor.prioridad ',vm.profesor.prioridad);
                                Profesor.update(vm.profesor,onSaveSuccess,onSaveError);
                            }else{
                                $scope.$emit('easyscheduleApp:asignaturaUpdate', result);
                                alert ("No se puede realizar la asignación, Los cursos están completos");
                                console.log('else de asigborrada ');
                                $uibModalInstance.close(result);
                            }
                        });
                    }else{
                        console.log('vm.miNuevaAsig ',vm.miNuevaAsig);
                        var indiceOldAsig = vm.profesor.asignaturas.findIndex(findFirstLargeNumber);
                        vm.profesor.asignaturas.splice(indiceOldAsig, 1)
                        vm.profesor.asignaturas.push(vm.miNuevaAsig); 
                        //agregamos la asignatura al profesor
                        Profesor.update(vm.profesor,onSaveSuccess,onSaveError);
                    }
                }); */
            
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
