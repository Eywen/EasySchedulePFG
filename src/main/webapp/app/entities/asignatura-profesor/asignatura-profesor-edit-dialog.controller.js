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
        vm.asigdeprof =[];
        vm.asignaturaProfesor=[];
        vm.fechaSeleccion =null;
        vm.asignaturaAntigua = null;
        vm.profesorId = null;
        vm.num_creditos = null;
         

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        Profesor.get({id:  $stateParams.id_prof}, function (result) {
            vm.profesor = result;    
            console.log("get profesor: ",result);
            
            Profesor.getSubjects({id: $stateParams.id_prof}, function (result) {
                console.log("asignaturas del profesor: ", result);
                vm.asigdeprof = result;
                console.log("todas las asignaturas : ", vm.asignaturas);
                //elimino  de la lista a mostrar para elegir las asignautras que ya tiene el profesor.
                //TODO.  falta comprobar que le liste una asignatura que ya tiene el número de veces que pueda seleccionarla.
                vm.witoutAsig = vm.asignaturas;
                for (var z = 0; z < vm.asignaturas.length; z ++){
                    for (var i = 0; i < result.length;  i++) {
                        console.log("result ", result[i]);
                        if (result[i].id == vm.asignaturas[z].id){
                            vm.witoutAsig.splice(z,1);
                            console.log("entro ",i);
                        }
                    }
                }
                console.log("lista de asignaturas que puede elegir (sin las que ya tiene): ", vm.witoutAsig);
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
            /*var indiceOldAsig = vm.profesor.asignaturas.findIndex(findFirstLargeNumber);
            vm.profesor.asignaturas.splice(indiceOldAsig, 1);*/
            
            console.log('profesor ',vm.profesor);
            console.log('vm.miNuevaAsig ',vm.miNuevaAsig.id);
            //console.log('vm.profesor.asignaturas  nuevo ',vm.profesor.asignaturas);
            /**
             * Esta llamada es necesaria para obtener los datos de la asignaturas ya que al modificar una o eliminarla hay q mandar la pk: idprofesor, idasignatura, fechaseleccion
             */

            Profesor.getAsignaturasProfesor({id: vm.profesor.id}, function (result){
                console.log ("getAsignaturasProfesor: ", result);
                vm.asignaturaProfesor = result;
                var i =0;
                var encontrado = false;
                console.log ("vm.asignaturaProfesor: ", result);
                while (i < vm.asignaturaProfesor.length && !encontrado){
                    if (vm.asignaturaProfesor[i].id_profesor == vm.profesor.id){
                        encontrado = true;
                        vm.fechaSeleccion =vm.asignaturaProfesor[i].fecha_seleccion;
                        vm.asignaturaAntigua = vm.asignaturaProfesor[i].id_asig;
                        vm.profesorId = vm.asignaturaProfesor[i].id_profesor;
                        vm.num_creditos = vm.asignaturaProfesor[i].num_creditos;
                    }
                }
                vm.datosmodificacion = {
                    id_profesor:vm.profesor.id,//vm.asignaturaProfesor.profAsigpk.id_profesor,
                    id_asignatura_nueva: vm.miNuevaAsig.id,
                    id_asignatura_antigua: vm.asignaturaAntigua, //vm.asignaturaProfesor.profAsigpk.id_asignatura,
                    fecha_seleccion: vm.fechaSeleccion,//vm.asignaturaProfesor.profAsigpk.fechaSeleccion
                    num_creditos: vm.num_creditos
                };
                console.log("id_profesor ",vm.datosmodificacion.id_profesor);
                console.log("id_asignatura_nueva ",vm.datosmodificacion.id_asignatura_nueva);
                console.log("id_asignatura_antigua ",vm.datosmodificacion.id_asignatura_antigua);
                console.log("fecha_seleccion ",vm.datosmodificacion.fecha_seleccion);
                //console.log('vm.asigAutomaticData: ',vm.asigAutomaticData);
                //AsignaturaProfesor.save(vm.asigAutomaticData,onSaveSuccess,onSaveError);
                /*
                **11-11-18. Modificacion automatica. sin verificaciones para asignar la asignatura al profesor. falta hacerlo.
                */
                if (vm.miNuevaAsig.id !== null /*&& vm.id_asignatura !== null */){
                    //vm.profesor.asignaturas.push(vm.miNuevaAsig);
                    //console.log('vm.profesor.asignaturas  push ',vm.profesor.asignaturas);
                    //console.log('profesor ',vm.profesor);
                    //Profesor.update(vm.profesor,onSaveSuccess,onSaveError);
                    AsignaturaProfesor.update(vm.datosmodificacion,onSaveSuccess,onSaveError);
                }  
            });
            
            
               
  
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
