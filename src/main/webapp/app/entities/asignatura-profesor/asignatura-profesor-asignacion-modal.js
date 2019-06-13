(function() {
    'use strict';

    angular
        .module('easyscheduleApp')
        .controller('NewAsignacionDialogController', NewAsignacionDialogController);

    NewAsignacionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Asignatura', 'Profesor','AsignaturaProfesor'];

    function NewAsignacionDialogController ($timeout, $scope, $stateParams,  $uibModalInstance, entity, Asignatura, Profesor, AsignaturaProfesor) {
        var vm = this;

        vm.asignatura = entity;
        vm.clear = clear;
        vm.save = save;
        vm.profesors = Profesor.query();
        vm.asignaturas= Asignatura.query();
        vm.datos=[];
        vm.profesoresAsignatura=[];
        vm.lowerPriority=[];
        vm.highestPriority=[];
        vm.checkAsignaturainProfesor = false;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }


        function save () {            
            vm.isSaving = true;
            ////////////////////
            //--06-12-18-- UNION DE LOS ESTADOS DEL DIAGRAMA
            //--26-11-18 n:m OK-- ESTADO 1. COMPROBAR SI EL PROFESOR YA TIENE ESTA ASIGNATURA ASIGNADA 
            AsignaturaProfesor.checkAsignaturainProfesor({asignaturaId: vm.miAsignatura.id, profesorId: vm.miProfesor.id},function (result){
           
                console.log ("fuera de save", result);
                if (result = "1"){
                    ///CAMINO SI DEL ESTADO 1
                    vm.checkAsignaturainProfesor  = true;
                    //--26-11-18 n:m OK-- ESTADO 2. OBTENER EL NUMERO DE VECES QUE UN PROFESOR TIENE ASIGNADA UNA ASIGNATURA
                    AsignaturaProfesor.countsubject({asignaturaId: vm.miAsignatura.id, profesorId: vm.miProfesor.id}, function (resultcount){
                        vm.countsubject =  parseInt(resultcount[0]);

                        //--02-12-18--  ESTADO 3. COMPROBAR SI EL PROFESOR TIENE EL MAXIMO DE ASIGNATURAS PERMITIDAS
                        AsignaturaProfesor.getcreditosdisponibles({profesorId: vm.miProfesor.id}, function (resultcreditosdisponibles){
                            // 210419 ---vm.maxAsignaturas = parseInt(resultcreditosdisponibles[0]);
                            vm.profNumCreditosLibres = resultcreditosdisponibles.creditosLibres;
                            //--02-12-18-- ESTADO 6 . NO PUEDE ELEGIR MAS ESTA ASIGNATURA NO TIENE LOS CREDITOS LIBRES SUFICIENTES PARA ELEGIRLA
                            //210419----if (vm.maxAsignaturas == 0){
                            if (vm.profNumCreditosLibres == 0 || vm.profNumCreditosLibres < vm.miAsignatura.creditos){
                                //210419--CAMINO SI DEL ESTADO 3
                                alert ("No puede elegir esta asignatura. No tiene los créditos libres suficientes para elegirla");
                            }else{
                                //ESTADO 4
                                //210419----numGrupos();
                                //210419--CAMINO NO DEL ESTADO 3
                                numCreditosTotalesAsig();
                            }
                        });

                        //new Estado 3. COMPROBAR SI EL PROFESOR TIENE CREDITOS DISPONIBLES PARA SELECCIONAR LA ASIGNATURA
                    });
                }else{
                    //CAMINO NO DEL ESTADO 1. ESTADO 4
                    //numGrupos();
                    numCreditosTotalesAsig();
                }
            });
        }
        ///////////

      /*  function numGrupos (){
            //--0612-18--  ESTADO 4. NUMERO GRUPOS COMPLETO? 
            AsignaturaProfesor.getasignaturainprof( vm.miAsignatura, function (result){
                console.log("getasignaturainprof para sabe cuantos grupos hay ocupados de esta asignatura ", result);
                vm.asignaturainprofesors = result;        
                if (vm.miAsignatura.num_grupos > vm.asignaturainprofesors.length){
                    console.log("num de grupo de asignatura no está completo, asignación automatica entonces ", vm.asignaturainprofesors.length);
                    asignacionAutomatica();
                }else{
                    //ESTADO 8 OBTENGO LA LISTA DE PRIORIDADES MAYORES A LA DEL PROFESOR QUE SE QUIERE HACER LA ASIGNACION DE LA LISTA DE PROFESORES QUE TIENEN UNA ASIGNATURA
                    AsignaturaProfesor.gethighestpriority({asignaturaId: vm.miAsignatura.id, profesorId: vm.miProfesor.id}, function (result){
                        console.log('gethighestpriority ',result);
                        vm.highestPriority = result;
                        var menorMayorPriridad = vm.highestPriority.length - 1;
                        if (vm.highestPriority.length == 0){
                            console.log("no hay prioridades mayores a la del profesor");
                            
                        }
                        // si la longitud del array de prioridades mayores a la del profesor es igual al numero de grupos de la asignatura. todas las prioridades son mayores
                        if (vm.highestPriority.length >= vm.miAsignatura.num_grupos){
                            console.log("todas las prioridades son mayores a la del profesor");
                            //--PRUEBA 09-12-18 OK--26-11-18 n:m OK-- ESTADO 9 DEL DIAGRAMA DE ESTADOS DE ASIGNACION 
                            alert ("No puede elegir esta asignatura. Los cursos están completos");
                        }else {
                            //si todas los grupos están llenos y los tiene asignados el profesor que esta haciendo la seleccion
                            vm.prioridadIgual = true;
                            vm.asignaturainprofesors.forEach(profesor => {
                                if (profesor.id != vm.miProfesor.id){
                                    vm.prioridadIgual = false;
                                }
                            });
                            //si estan todos los cursos asignados, el profesor tiene un cursos de esos asignados y todos los demas que tiene seleccionada la
                            //asignatura tiene mas prioridad que el profesor
                            var ultimaPosicion = vm.asignaturainprofesors.length - 1;
                            if (vm.asignaturainprofesors[ultimaPosicion].id == vm.miProfesor.id && vm.prioridadIgual === false){
                                alert ("No puede elegir esta asignatura. Los cursos están completos ");
                            }
                            if (vm.prioridadIgual === true){
                                alert ("No puede elegir esta asignatura. Los cursos están completos y están todos asignados a usted");
                            }else{
                                //--26-11-18 n:m OK-- ESTADO 10 OBTENGO LA LISTA DE PRIORIDADES MENORES A LA DEL PROFESOR QUE SE QUIERE HACER LA ASIGNACION DE LA LISTA DE PROFESORES QUE TIENEN UNA ASIGNATURA
                                AsignaturaProfesor.getlowerpriority({asignaturaId: vm.miAsignatura.id, profesorId: vm.miProfesor.id}, function (result){
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
                                            profesorid: vm.miProfesor.id,
                                            asignaturaId: vm.miAsignatura.id,
                                            num_creditos: vm.numCreditos,
                                        };
                                        AsignaturaProfesor.reasignacion(vm.datosReasignacion, function (result){
                                            console.log("reasignación ", result);
                                        });
                                        
                                    }     
                                });
                            }
                        }
                    });
                }
            },onSaveSuccess, onSaveError);
        }*/
        
        function asignacionAutomatica (){
                //ESTADO 5.  ASIGNACION AUTOMATICA
                if (vm.miAsignatura.id !== null && vm.miProfesor.id !== null ){
                    vm.datos = {
                        profesorid: vm.miProfesor.id,
                        asignaturaId: vm.miAsignatura.id,
                        num_creditos: vm.numCreditos
                    };
                    //metodo sin implementar
                    AsignaturaProfesor.save(vm.datos, onSaveSuccess, onSaveError);
                    /*vm.miProfesor.asignaturas.push(vm.miAsignatura);
                    console.log('vm.profesor.asignaturas  push ',vm.miProfesor.asignaturas);
                    Profesor.update(vm.miProfesor,onSaveSuccess,onSaveError);*/
                } 
        }

        /////////14-14-19
        function numCreditosTotalesAsig (){
            console.log("nueva comprobacion por num creditos totales");
            ///210419---- ESTADO 4.  Numero de creditos totales de la asignatura cubierto?
            AsignaturaProfesor.getNumCredAsigSeleccionados(vm.miAsignatura, function (result){
                console.log(" para saber cuantos creditos hay libres de esta asignatura ", result);
                //var numCreditosAsignaturaCubiertos =  parseInt(result[0]);
                var numCreditosAsignaturaCubiertos = result.numCreditosSeleccionadosAsignatura;
                if (numCreditosAsignaturaCubiertos <= (vm.miAsignatura.creditos_totales-vm.miAsignatura.creditos)){
                    console.log("num de creditos de asignatura no está completo, asignación automatica entonces ", numCreditosAsignaturaCubiertos);
                    asignacionAutomatica();
                }else{
                    console.log("num de creditos de asignatura completo. Hay que comprobar prioridades de profesores por si hay que reasignar");
                    //CAMINO DE NO DEL ESTADO 4, VA AL ESTADO 7: BUSCAR ASIGNATURA EN PROFESORES
                    //ESTADO 8 OBTENGO LA LISTA DE PRIORIDADES MAYORES A LA DEL PROFESOR QUE SE QUIERE HACER LA ASIGNACION DE LA LISTA DE PROFESORES QUE TIENEN UNA ASIGNATURA
                    AsignaturaProfesor.gethighestpriority({asignaturaId: vm.miAsignatura.id, profesorId: vm.miProfesor.id}, function (result){
                        console.log('gethighestpriority ',result);
                        vm.highestPriority = result;
                        var menorMayorPriridad = vm.highestPriority.length - 1;
                        if (vm.highestPriority.length <= 0){
                            console.log("no hay prioridades mayores a la del profesor");
                            
                        }
                        // si la longitud del array de prioridades mayores a la del profesor es igual al numero de grupos de la asignatura. 
                        //todas las prioridades son mayores
                        if (vm.highestPriority.length >= vm.miAsignatura.num_grupos){
                            console.log("todas las prioridades son mayores a la del profesor");
                            //--PRUEBA 09-12-18 OK--26-11-18 n:m OK-- ESTADO 9 DEL DIAGRAMA DE ESTADOS DE ASIGNACION 
                            alert ("No puede elegir esta asignatura. Los cursos están completos");
                        }else {
                            //getasignaturainprof para sabe cuantos grupos hay ocupados de esta asignatura
                            AsignaturaProfesor.getasignaturainprof( vm.miAsignatura, function (result){
                                console.log("getasignaturainprof para sabe cuantos grupos hay ocupados de esta asignatura ", result);
                                //si todas los grupos están llenos y los tiene asignados el profesor que esta haciendo la seleccion
                                vm.asignaturainprofesors = result;
                                vm.prioridadIgual = true;
                                vm.asignaturainprofesors.forEach(profesor => {
                                    if (profesor.id != vm.miProfesor.id){
                                        vm.prioridadIgual = false;
                                    }
                                });
                                //si estan todos los cursos asignados, el profesor tiene un cursos de esos asignado y todos los demas que tiene seleccionada la
                                //asignatura tiene mas prioridad que el profesor
                                var ultimaPosicion = vm.asignaturainprofesors.length - 1;
                                if (vm.asignaturainprofesors[ultimaPosicion].id == vm.miProfesor.id && vm.prioridadIgual === false){
                                    alert ("No puede elegir esta asignatura. Los cursos están completos ");
                                }
                                if (vm.prioridadIgual === true){
                                    alert ("No puede elegir esta asignatura. Los cursos están completos y están todos asignados a usted");
                                }else{
                                    //--26-11-18 n:m OK-- ESTADO 10 OBTENGO LA LISTA DE PRIORIDADES MENORES A LA DEL PROFESOR QUE SE QUIERE HACER 
                                    //LA ASIGNACION DE LA LISTA DE PROFESORES QUE TIENEN UNA ASIGNATURA
                                    AsignaturaProfesor.getlowerpriority({asignaturaId: vm.miAsignatura.id, profesorId: vm.miProfesor.id}, function (result){
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
                                                profesorid: vm.miProfesor.id,
                                                asignaturaId: vm.miAsignatura.id,
                                                num_creditos: vm.numCreditos,
                                            };
                                            AsignaturaProfesor.reasignacion(vm.datosReasignacion, function (result){
                                                console.log("reasignación ", result);
                                            });
                                            
                                        }     
                                    });
                                }
                            });
                        }
                    });
                }
                $uibModalInstance.close(result);
            },onSaveSuccess, onSaveError)
             ///210419---- ESTADO 3.
             

        }
        ////
        function onSaveSuccess (result) {
            $scope.$emit('easyscheduleApp:asignaturaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }
    
        function onSaveError () {
            vm.isSaving = false;
        }

        

    };
})
();
