(function() {
    'use strict';

    angular
        .module('easyscheduleApp')
        .controller('AsignacionDeleteController',AsignacionDeleteController);

    AsignacionDeleteController.$inject = ['$uibModalInstance', '$stateParams','entity', 'AsignaturaProfesor', 'Profesor', 'Asignatura'];

    function AsignacionDeleteController($uibModalInstance, $stateParams, entity, AsignaturaProfesor, Profesor, Asignatura) {
        var vm = this;

        vm.asignacion = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        vm.asignaturaProfesor=[];
        vm.fechaSeleccion =null;
        vm.asignaturaAntigua = null;
        vm.profesorId = null;
        vm.num_creditos = null;
        vm.id_profesor= $stateParams.id_prof;
        vm.id_asignatura=  $stateParams.id_asig;
        vm.fecha_seleccion = $stateParams.fechaSeleccion;

        //console.log('vm.asignacion ', vm.asignacion);
        /*Profesor.get({id: vm.asignacion.id_profesor}, function (result) {
            vm.profesor = result;
        });*/
       /* Asignatura.get({id: vm.asignacion.id_asignatura}, function (result) {
            vm.asignatura = result;
            console.log('vm.asignatura ',vm.asignatura);
            console.log("DATOS EN DELETE VM $stateParams.id_prof:  ", $stateParams.id_prof);
            console.log("DATOS EN DELETE VM id_asig:  ", $stateParams.id_asig);
            console.log("DATOS EN DELETE VM fechaSeleccion:  ", $stateParams.fechaSeleccion);
            
        });*/

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id_prof,id_asig,fechaSeleccion) {
            
            /**
             * Esta llamada es necesaria para obtener los datos de la asignaturas ya que al modificar una o eliminarla hay q mandar la pk: idprofesor, idasignatura, fechaseleccion
                18-11-18. ahora se pasan los datos de pk de asignaturaprofesor desde el state en la llamada del boton elimiar o update en datailscontroller.
             */
            /*Profesor.getAsignaturasProfesor({id: id_prof}, function (result){
                console.log ("getAsignaturasProfesor: ", result);
                vm.asignaturaProfesor = result;
                var i =0;
                var encontrado = false;
                console.log ("vm.asignaturaProfesor: ", result);
                while (i < vm.asignaturaProfesor.length && !encontrado){
                    if (vm.asignaturaProfesor[i].id_profesor == vm.profesor.id){
                        encontrado = true;
                        vm.fechaSeleccion =vm.asignaturaProfesor[i].fecha_seleccion;
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
              /*  if (vm.miNuevaAsig.id !== null /*&& vm.id_asignatura !== null *///){
                    //vm.profesor.asignaturas.push(vm.miNuevaAsig);
                    //console.log('vm.profesor.asignaturas  push ',vm.profesor.asignaturas);
                    //console.log('profesor ',vm.profesor);
                    //Profesor.update(vm.profesor,onSaveSuccess,onSaveError);
                  /*  AsignaturaProfesor.update(vm.datosmodificacion,onSaveSuccess,onSaveError);
                }  
            });*/
            
            ////////////////////////////////////
            console.log("id_prof ",id_prof);
            console.log("id_asig ",id_asig);
            console.log("fecha_seleccion ",fechaSeleccion);
            vm.asignaturaBorrar = {
                id_profesor: id_prof,
                id_asignatura :id_asig, 
                fecha_seleccion:fechaSeleccion
            }
           AsignaturaProfesor.delete(vm.asignaturaBorrar,
                function () {
                    $uibModalInstance.close(true);
                });
            // AsignaturaProfesor.delete(vm.datos);
            
                
        }
    }
})();