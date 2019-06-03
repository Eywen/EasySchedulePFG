(function() {
    'use strict';

    angular
        .module('easyscheduleApp')
        .controller('EditAsignacionDialogController', EditAsignacionDialogController);

    EditAsignacionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Asignatura', 'Profesor','AsignaturaProfesor'];

    function EditAsignacionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Asignatura, Profesor, AsignaturaProfesor) {
        var vm = this;

        vm.asignatura = entity;
        vm.clear = clear;
        vm.save = save;
        vm.asignaturas= Asignatura.query();
        vm.datos=[];
        vm.witoutAsig=[];
        vm.witoutProfAsig=[];
        vm.asigdeprof =[];
        vm.asignaturaProfesor=[];
        vm.fechaSeleccion =null;
        vm.asignaturaAntiguaId = null;
        vm.profesorId = null;
        vm.num_creditos = null;
        vm.asignaturaAntigua=null;
         

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        Profesor.get({id:  $stateParams.id_prof}, function (result) {
            vm.profesor = result;    
            //console.log("get profesor: ",result);
            
            Profesor.getSubjects({id: $stateParams.id_prof}, function (result) {
                //console.log("asignaturas del profesor: ", result);
                vm.asigdeprof = result;
                //console.log("todas las asignaturas : ", vm.asignaturas);
                //elimino  de la lista a mostrar para elegir la asignautra que esta modificando el profesor.
                
                //vm.witoutAsig = vm.asignaturas;
                for (var z = 0; z < vm.asignaturas.length; z ++){
                    if (vm.asignaturas[z].id != $stateParams.id_asig) {
                        vm.witoutAsig.push(vm.asignaturas[z]);
                    }
                    /*for (var i = 0; i < result.length;  i++) {
                        console.log("result ", result[i]);
                        if (result[i].id == vm.asignaturas[z].id){
                            vm.witoutAsig.splice(z,1);
                            console.log("entro ",i);
                        }
                    }*/
                }
                //console.log("lista de asignaturas que puede elegir (si la que está modificando): ", vm.witoutAsig);
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
            
            //console.log('profesor ',vm.profesor);
            //console.log('vm.miNuevaAsig ',vm.miNuevaAsig.id);
            //console.log('vm.profesor.asignaturas  nuevo ',vm.profesor.asignaturas);
            /**
             * Esta llamada es necesaria para obtener los datos de la asignaturas ya que al modificar una o eliminarla hay q mandar la pk: idprofesor, idasignatura, fechaseleccion
             */

            Profesor.getAsignaturasProfesor({id: vm.profesor.id}, function (result){
                //console.log ("getAsignaturasProfesor: ", result);
                vm.asignaturaProfesor = result;
                var i =0;
                var encontrado = false;
                console.log ("vm.asignaturaProfesor: ", result);
                console.log("vm.asignatura: ",vm.asignatura);
                while (i < vm.asignaturaProfesor.length && !encontrado){
                    if (vm.asignaturaProfesor[i].id_profesor == vm.profesor.id){
                        encontrado = true;
                        vm.fechaSeleccion =vm.asignaturaProfesor[i].fecha_seleccion;
                        vm.asignaturaAntigua = vm.asignaturaProfesor[i];
                        vm.asignaturaAntiguaId = vm.asignaturaProfesor[i].id_asig;
                        vm.profesorId = vm.asignaturaProfesor[i].id_profesor;
                        vm.num_creditos = vm.asignaturaProfesor[i].num_creditos;
                    }
                }
                vm.datosmodificacion = {
                    id_profesor:vm.profesor.id,//vm.asignaturaProfesor.profAsigpk.id_profesor,
                    id_asignatura_nueva: vm.miNuevaAsig.id,
                    id_asignatura_antigua: vm.asignaturaAntiguaId, //vm.asignaturaProfesor.profAsigpk.id_asignatura,
                    fecha_seleccion: vm.fechaSeleccion,//vm.asignaturaProfesor.profAsigpk.fechaSeleccion
                    num_creditos: vm.num_creditos
                };
                console.log('vm.datosmodificacion',vm.datosmodificacion);
                //console.log("id_profesor ",vm.profesor.id);
                //console.log("id_asignatura_nueva ",vm.datosmodificacion.id_asignatura_nueva);
                //console.log("id_asignatura_antigua ",vm.datosmodificacion.id_asignatura_antigua);
                //console.log("fecha_seleccion ",vm.datosmodificacion.fecha_seleccion);
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

                    ///////****************************** */
                    
                   // numGrupos ();
                   numCreditosTotalesAsig();
                    ///*************************************** */
                    //AsignaturaProfesor.update(vm.datosmodificacion,onSaveSuccess,onSaveError);
                }  
            });
            
            
            /*function numGrupos (){
                //--0612-18--  ESTADO 4. NUMERO GRUPOS COMPLETO? 
                AsignaturaProfesor.getasignaturainprof( vm.miNuevaAsig, function (result){
                    //console.log("getasignaturainprof para sabe cuantos grupos hay ocupados de esta asignatura ", result);
                    vm.asignaturainprofesors = result;        
                    if (vm.miNuevaAsig.num_grupos > vm.asignaturainprofesors.length){
                        //console.log("num de grupo de asignatura no está completo, asignación automatica entonces ", vm.asignaturainprofesors.length);
                        //asignacionAutomatica();
                        AsignaturaProfesor.update(vm.datosmodificacion,onSaveSuccess,onSaveError);
                    }else{
                        //ESTADO 8 OBTENGO LA LISTA DE PRIORIDADES MAYORES A LA DEL PROFESOR QUE SE QUIERE HACER LA ASIGNACION DE LA LISTA DE PROFESORES QUE TIENEN UNA ASIGNATURA
                        AsignaturaProfesor.gethighestpriority({asignaturaId: vm.miNuevaAsig.id, profesorId: vm.profesor.id}, function (result){
                            //console.log('gethighestpriority ',result);
                            vm.highestPriority = result;
                            var menorMayorPriridad = vm.highestPriority.length - 1;
                            if (vm.highestPriority.length == 0){
                                console.log("no hay prioridades mayores a la del profesor");
                                
                            }
                            // si la longitud del attay de prioridades mayores a la del profesor es igual al numero de grupos de la asignatura. todas las prioridades son mayores
                            if (vm.highestPriority.length >= vm.miNuevaAsig.num_grupos){
                                //console.log("todas las prioridades son mayores a la del profesor");
                                //--PRUEBA 09-12-18 OK--26-11-18 n:m OK-- ESTADO 9 DEL DIAGRAMA DE ESTADOS DE ASIGNACION 
                                alert ("No puede elegir esta asignatura. Los cursos están completos");
                            }else {
                                //si todas los grupos están llenos y los tiene asignados el profesor que esta haciendo la seleccion
                                vm.prioridadIgual = true;
                                vm.asignaturainprofesors.forEach(profesor => {
                                    if (profesor.id != vm.profesor.id){
                                        vm.prioridadIgual = false;
                                    }
                                });
                                //si estan todos los cursos asignados, el profesor tiene un cursos de esos asignados y todos los demas que tiene seleccionada la
                                //asignatura tiene mas prioridad que el profesor
                                var ultimaPosicion = vm.asignaturainprofesors.length - 1;
                                if (vm.asignaturainprofesors[ultimaPosicion].id == vm.profesor.id && vm.prioridadIgual === false){
                                    alert ("No puede elegir esta asignatura. Los cursos están completos ");
                                }
                                if (vm.prioridadIgual === true){
                                    alert ("No puede elegir esta asignatura. Los cursos están completos y están todos asignados a usted");
                                }else{
                                    //--26-11-18 n:m OK-- ESTADO 10 OBTENGO LA LISTA DE PRIORIDADES MENORES A LA DEL PROFESOR QUE SE QUIERE HACER LA ASIGNACION DE LA LISTA DE PROFESORES QUE TIENEN UNA ASIGNATURA
                                    AsignaturaProfesor.getlowerpriority({asignaturaId: vm.miNuevaAsig.id, profesorId: vm.profesor.id}, function (result){
                                        //console.log('getlowerpriority ',result);
                                        vm.lowerPriority = result;
                                    // console.log ("longitud de lower array: ",vm.lowerPriority.length);
                                        //--26-11-18 n:m -- ahora mismo sale aunque el frupo no este lleno, solo con q no haya nadiecon menos prioridad. revisar
                                        if (vm.lowerPriority.length == 0){
                                            console.log("array lower vacio");
                                            //alert ("No puede elegir esta asignatura. Los cursos están completos");
                                        }else{
                                            var menorPriridad = vm.lowerPriority.length - 1;
                                            //--02-12-18-- ESTADO 11 Y 12-- borrar asignatura al profesor con prioridad menor en la lista lowerpriority y asignarsela al profesor que está haciendo la selección
                                            vm.datosReasignacion = {
                                                profmenorprioridadId: vm.lowerPriority[menorPriridad].id, 
                                                profesorid: vm.profesor.id,
                                                asignaturaId: vm.miNuevaAsig.id,
                                                num_creditos: vm.misNuevosCreditos,
                                            };
                                            
                                            vm.datoseliminar = {
                                                id_profesor:vm.profesor.id,
                                                id_asignatura: vm.asignaturaAntiguaId, 
                                                fecha_seleccion: vm.fechaSeleccion,
                                            };
                                            
                                            AsignaturaProfesor.reasignacion(vm.datosReasignacion, function (result){
                                                //console.log("reasignación ", result);
                                                AsignaturaProfesor.delete(vm.datoseliminar,
                                                    function () {
                                                    // console.log("borrada asignatura antigua", vm.asignaturaAntigua);
                                                    
                                                    });
                                            },onSaveSuccess, onSaveError);
                                            
                                        }     
                                    },onSaveSuccess, onSaveError);
                                }
                            }
                        },onSaveSuccess, onSaveError);
                    }
                },onSaveSuccess, onSaveError);
            } */
  
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

        /////////14-14-19
        function numCreditosTotalesAsig (){
            console.log("nueva comprobacion por num creditos totales");
            ///210419---- ESTADO 4.  Numero de creditos totales de la asignatura cubierto?
            AsignaturaProfesor.getNumCredAsigSeleccionados(vm.miNuevaAsig, function (result){
                console.log(" para saber cuantos creditos hay libres de esta asignatura ", result);
                //var numCreditosAsignaturaCubiertos =  parseInt(result[0]);
                var numCreditosAsignaturaCubiertos = result.numCreditosSeleccionadosAsignatura;
                if (numCreditosAsignaturaCubiertos < (vm.miNuevaAsig.creditos_totales-vm.miNuevaAsig.creditos)){
                    /*console.log("num de creditos de asignatura no está completo, asignación automatica entonces ", numCreditosAsignaturaCubiertos);
                    asignacionAutomatica();*/
                    AsignaturaProfesor.update(vm.datosmodificacion,onSaveSuccess,onSaveError);
                }else{
                    console.log("num de creditos de asignatura completo. Hay que comprobar prioridades de profesores por si hay que reasignar");
                    //CAMINO DE NO DEL ESTADO 4, VA AL ESTADO 7: BUSCAR ASIGNATURA EN PROFESORES
                    //ESTADO 8 OBTENGO LA LISTA DE PRIORIDADES MAYORES A LA DEL PROFESOR QUE SE QUIERE HACER LA ASIGNACION DE LA LISTA DE PROFESORES QUE TIENEN UNA ASIGNATURA
                    AsignaturaProfesor.gethighestpriority({asignaturaId: vm.miNuevaAsig.id, profesorId: vm.profesor.id}, function (result){
                        console.log('gethighestpriority ',result);
                        vm.highestPriority = result;
                        var menorMayorPriridad = vm.highestPriority.length - 1;
                        if (vm.highestPriority.length <= 0){
                            console.log("no hay prioridades mayores a la del profesor");
                            
                        }
                        // si la longitud del array de prioridades mayores a la del profesor es igual al numero de grupos de la asignatura. 
                        //todas las prioridades son mayores
                        if (vm.highestPriority.length >= vm.miNuevaAsig.num_grupos){
                            console.log("todas las prioridades son mayores a la del profesor");
                            //--PRUEBA 09-12-18 OK--26-11-18 n:m OK-- ESTADO 9 DEL DIAGRAMA DE ESTADOS DE ASIGNACION 
                            alert ("No puede elegir esta asignatura. Los cursos están completos");
                        }else {
                            //getasignaturainprof para sabe cuantos grupos hay ocupados de esta asignatura
                            AsignaturaProfesor.getasignaturainprof( vm.miNuevaAsig, function (result){
                                console.log("getasignaturainprof para sabe cuantos grupos hay ocupados de esta asignatura ", result);
                                //si todas los grupos están llenos y los tiene asignados el profesor que esta haciendo la seleccion
                                vm.asignaturainprofesors = result;
                                vm.prioridadIgual = true;
                                vm.asignaturainprofesors.forEach(profesor => {
                                    if (profesor.id !=vm.profesor.id){
                                        vm.prioridadIgual = false;
                                    }
                                });
                                //si estan todos los cursos asignados, el profesor tiene un cursos de esos asignado y todos los demas que tiene seleccionada la
                                //asignatura tiene mas prioridad que el profesor
                                var ultimaPosicion = vm.asignaturainprofesors.length - 1;
                                if (vm.asignaturainprofesors[ultimaPosicion].id == vm.profesor.id && vm.prioridadIgual === false){
                                    alert ("No puede elegir esta asignatura. Los cursos están completos ");
                                }
                                if (vm.prioridadIgual === true){
                                    alert ("No puede elegir esta asignatura. Los cursos están completos y están todos asignados a usted");
                                }else{
                                    //--26-11-18 n:m OK-- ESTADO 10 OBTENGO LA LISTA DE PRIORIDADES MENORES A LA DEL PROFESOR QUE SE QUIERE HACER 
                                    //LA ASIGNACION DE LA LISTA DE PROFESORES QUE TIENEN UNA ASIGNATURA
                                    AsignaturaProfesor.getlowerpriority({asignaturaId: vm.miNuevaAsig.id, profesorId: vm.profesor.id}, function (result){
                                        console.log('getlowerpriority ',result);
                                        vm.lowerPriority = result;
                                        console.log ("longitud de lower array: ",vm.lowerPriority.length);
                                        //--26-11-18 n:m -- ahora mismo sale aunque el frupo no este lleno, solo con q no haya nadiecon menos prioridad. revisar
                                        if (vm.lowerPriority.length == 0){
                                            console.log("array lower vacio");
                                            //alert ("No puede elegir esta asignatura. Los cursos están completos");
                                        }else{
                                            var menorPriridad = vm.lowerPriority.length - 1;
                                            //--02-12-18-- ESTADO 11 Y 12-- borrar asignatura al profesor con prioridad menor en la lista lowerpriority y asignarsela al profesor que está haciendo la selección
                                            vm.datosReasignacion = {
                                                profmenorprioridadId: vm.lowerPriority[menorPriridad].id, 
                                                profesorid: vm.profesor.id,
                                                asignaturaId: vm.miNuevaAsig.id,
                                                num_creditos: vm.misNuevosCreditos,
                                            };
                                            //datos de la asgnatura modificada que se deben borrar
                                         /*    vm.datoseliminar = {
                                                id_profesor:vm.profesor.id, 
                                                id_asignatura: vm.asignaturaAntiguaId, 
                                                fecha_seleccion: vm.fechaSeleccion,
                                            }; */
                                            AsignaturaProfesor.reasignacion(vm.datosReasignacion, function (result){
                                                console.log("reasignación ", result);
                                                AsignaturaProfesor.delete(vm.asignatura,
                                                    function () {
                                                     console.log("borrada asignatura antigua", vm.asignaturaAntigua);
                                                    
                                                });
                                            });                                            
                                        }     
                                    });
                                }
                            });
                        }
                    });
                }
                
            },onSaveSuccess, onSaveError)
            ///210419---- ESTADO 3.
     

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
