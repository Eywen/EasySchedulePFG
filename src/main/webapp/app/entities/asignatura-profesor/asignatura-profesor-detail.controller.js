(function() {
    'use strict';

    angular
        .module('easyscheduleApp')
        .controller('AsignaturasProfesorDetailController', AsignaturasProfesorDetailController);

    AsignaturasProfesorDetailController.$inject = ['$state', '$stateParams', 'entity', 'Asignatura','AsignaturaProfesor', 'Profesor', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams'];

    function AsignaturasProfesorDetailController($state, $stateParams, entity, Asignatura, AsignaturaProfesor, Profesor, ParseLinks, AlertService, paginationConstants, pagingParams) {

        var vm = this;
        vm.profesor = entity;
        vm.profesor.asignaturas=[];
        vm.asignaturaProfesor=[];
        vm.asignaturas = [];
        /**
         * Esta llamada es necesaria para obtener los datos de la asignaturas ya que al modificar una o eliminarla hay q mandar la pk: idprofesor, idasignatura, fechaseleccion
         */

       /* Profesor.getAsignaturasProfesor({id: vm.profesor.id}, function (result){
            console.log ("getAsignaturasProfesor: ", result);
            vm.asignaturaProfesor = result;
            console.log ("vm.asignaturaProfesor: ", vm.asignaturaProfesor);
        });*/

         /**
         * Esta llamada es necesaria para obtener los datos de la asignaturas ya que al modificar una o eliminarla hay q mandar la pk: idprofesor, idasignatura, fechaseleccion
         */
        Profesor.getAsignaturasProfesor({id: vm.profesor.id}, function (result){
            console.log ("getAsignaturasProfesor: ", result);
            vm.asignaturaProfesor = result;
            var i =0;
            var encontrado = false;
            console.log ("vm.asignaturaProfesor EN DETAILS: ", vm.asignaturaProfesor);
            
            vm.asignaturaProfesor.forEach(asignaturaProfesor => {
                console.log("entro en  vm.asignaturaProfesor.forEach(asignaturaProfesor =>",asignaturaProfesor );
                vm.datosAsignaturaProfesor = {
                    id_profesor:asignaturaProfesor.id_profesor,//vm.asignaturaProfesor.profAsigpk.id_profesor,
                    id_asignatura: asignaturaProfesor.id_asig,
                    //id_asignatura_antigua: vm.asignaturaAntigua, //vm.asignaturaProfesor.profAsigpk.id_asignatura,
                    fecha_seleccion: asignaturaProfesor.fecha_seleccion,//vm.asignaturaProfesor.profAsigpk.fechaSeleccion
                    //num_creditos: vm.num_creditos
                };
                AsignaturaProfesor.getSubject(vm.datosAsignaturaProfesor, function (result){
                    console.log ("AsignaturaProfesor.getSubject Post  EN DETAILS: ", result);
                    //vm.profesor.asignaturas.push(result);
                    //Array.prototype.push.apply(vegetables, moreVegs);
                    
                    //console.log("profesorasignatura a listar EN DETAILS: ", vm.profesor);
                    vm.profesor.asignaturas.push(result);
                    console.log ("vm.profesors.asignaturas EN DETAILS: ", vm.profesor.asignaturas);
                });

            });

            
            
            /*while (i < vm.asignaturaProfesor.length && !encontrado){
                if (vm.asignaturaProfesor[i].id_profesor == vm.profesor.id){
                    encontrado = true;
                    vm.fechaSeleccion =vm.asignaturaProfesor[i].fecha_seleccion;
                    vm.profesorId = vm.asignaturaProfesor[i].id_profesor;
                    vm.num_creditos = vm.asignaturaProfesor[i].num_creditos;
                }
            }*/
            /*vm.datosmodificacion = {
                id_profesor:vm.profesor.id,//vm.asignaturaProfesor.profAsigpk.id_profesor,
                id_asignatura_nueva: vm.miNuevaAsig.id,
                id_asignatura_antigua: vm.asignaturaAntigua, //vm.asignaturaProfesor.profAsigpk.id_asignatura,
                fecha_seleccion: vm.fechaSeleccion,//vm.asignaturaProfesor.profAsigpk.fechaSeleccion
                num_creditos: vm.num_creditos
            };*/
            console.log("id_profesor ",vm.datosmodificacion.id_profesor);
            console.log("id_asignatura_nueva ",vm.datosmodificacion.id_asignatura_nueva);
            console.log("id_asignatura_antigua ",vm.datosmodificacion.id_asignatura_antigua);
            console.log("fecha_seleccion ",vm.datosmodificacion.fecha_seleccion);
            //console.log('vm.asigAutomaticData: ',vm.asigAutomaticData);
            //AsignaturaProfesor.save(vm.asigAutomaticData,onSaveSuccess,onSaveError);
            /*
            **11-11-18. Modificacion automatica. sin verificaciones para asignar la asignatura al profesor. falta hacerlo.
            */
            //if (vm.miNuevaAsig.id !== null /*&& vm.id_asignatura !== null */){
                //vm.profesor.asignaturas.push(vm.miNuevaAsig);
                //console.log('vm.profesor.asignaturas  push ',vm.profesor.asignaturas);
                //console.log('profesor ',vm.profesor);
                //Profesor.update(vm.profesor,onSaveSuccess,onSaveError);
               // AsignaturaProfesor.update(vm.datosmodificacion,onSaveSuccess,onSaveError);
           // }  
           
        });



        /** usar este metodo para obtener las asignaturas de un profesor, no puedo usar un finad all profesor con sus asignaturas 
        * pq en find all profesor de la opcion de menu profesor no se cargan las asignaturas q tiene
        * 17-11-18: probablemente no sirva ya que necesito obtener todos los datos de asignaturaprofesor y no solo el objeto asignatura
        */
       // Profesor.getSubjects({id: vm.profesor.id}, function (result){
            //console.log ("getAsignaturas: ", result);
            //vm.profesor.asignaturas = result;
           // console.log ("vm.profesors.asignaturas: ", vm.profesor.asignaturas);
            //console.log("profesorasignatura a listar: ", vm.profesor);
      //  });
    }

})();