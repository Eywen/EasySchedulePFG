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

        var vm = this;

        vm.asignatura = entity;
        vm.clear = clear;
        vm.save = save;
        vm.profesors = Profesor.query();
        vm.asignaturas= Asignatura.query();
        vm.datosAsignacion=[];
        vm.profesoresAsignatura=[];
        vm.lowerPriority=[];
        vm.highestPriority=[];
        vm.checkAsignaturainProfesor = false;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }
        



        /*vm.profesor = entity;*/
     
        vm.asignaturaId = $stateParams.id_asig;
        vm.profesorId = $stateParams.id_prof;
        

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });
        console.log('vm prof sub: ', vm);
      

        function save () {
            vm.isSaving = true;
            //asignación sin ningun tipo de coprobacion de num de creidtos o asignaturas permitidas. Ya se hará más adelante.
           /* if (vm.profesorId !== null && vm.asignaturaId !==null) {
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
            }*/
            ////////////////////
            //--06-12-18-- UNION DE LOS ESTADOS DEL DIAGRAMA
            //--26-11-18 n:m OK-- ESTADO 1. COMPROBAR SI EL PROFESOR YA TIENE ESTA ASIGNATURA ASIGNADA 
            AsignaturaProfesor.checkAsignaturainProfesor({asignaturaId: vm.datos.id_asignatura, profesorId: vm.datos.id_profesor},function (result){
           
                console.log ("fuera de save", result);
                if (result = "1"){
                    ///CAMINO SI DEL ESTADO 1
                    vm.checkAsignaturainProfesor  = true;
                    Asignatura.get({id:vm.datos.id_asignatura}, function (result){
                        vm.miAsignatura = result;
                          //--02-12-18--  ESTADO 3. COMPROBAR SI EL PROFESOR TIENE EL MAXIMO DE ASIGNATURAS PERMITIDAS. num creditos disponibles
                          AsignaturaProfesor.getcreditosdisponibles({profesorId: vm.datos.id_profesor}, function (resultcreditosdisponibles){
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
                        }, onSaveSuccess, onSaveError);

                        //new Estado 3. COMPROBAR SI EL PROFESOR TIENE CREDITOS DISPONIBLES PARA SELECCIONAR LA ASIGNATURA
                    }, onSaveSuccess, onSaveError);

                      
                    
                }else{
                    //CAMINO NO DEL ESTADO 1. ESTADO 4
                    //numGrupos();
                    numCreditosTotalesAsig();
                }
            }, onSaveSuccess, onSaveError);
        
            
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

        /*function onSaveSuccess (result) {
            $scope.$emit('easyscheduleApp:profesorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }*/
        function asignacionAutomatica (){
            //ESTADO 5.  ASIGNACION AUTOMATICA
            console.log('vm.numCreditos',vm.numCreditos);
            if (vm.miAsignatura.id !== null && vm.datos.id_profesor !== null ){
                vm.datosAsignacion = {
                    profesorid:  vm.profesorId,
                    asignaturaId: vm.miAsignatura.id,
                    num_creditos: vm.numCreditos
                };
                console.log('vm.datosAsignacion',vm.datosAsignacion);
                AsignaturaProfesor.save(vm.datosAsignacion, onSaveSuccess, onSaveError);
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
            if (numCreditosAsignaturaCubiertos < (vm.miAsignatura.creditos_totales-vm.miAsignatura.creditos)){
                console.log("num de creditos de asignatura no está completo, asignación automatica entonces ", numCreditosAsignaturaCubiertos);
                asignacionAutomatica();
            }else{
                console.log("num de creditos de asignatura completo. Hay que comprobar prioridades de profesores por si hay que reasignar");
                //CAMINO DE NO DEL ESTADO 4, VA AL ESTADO 7: BUSCAR ASIGNATURA EN PROFESORES
                //ESTADO 8 OBTENGO LA LISTA DE PRIORIDADES MAYORES A LA DEL PROFESOR QUE SE QUIERE HACER LA ASIGNACION DE LA LISTA DE PROFESORES QUE TIENEN UNA ASIGNATURA
                AsignaturaProfesor.gethighestpriority({asignaturaId: vm.miAsignatura.id, profesorId: vm.datos.id_profesor}, function (result){
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
                                if (profesor.id != vm.datos.id_profesor){
                                    vm.prioridadIgual = false;
                                }
                            });
                            //si estan todos los cursos asignados, el profesor tiene un cursos de esos asignado y todos los demas que tiene seleccionada la
                            //asignatura tiene mas prioridad que el profesor
                            var ultimaPosicion = vm.asignaturainprofesors.length - 1;
                            if (vm.asignaturainprofesors[ultimaPosicion].id == vm.datos.id_profesor && vm.prioridadIgual === false){
                                alert ("No puede elegir esta asignatura. Los cursos están completos ");
                            }
                            if (vm.prioridadIgual === true){
                                alert ("No puede elegir esta asignatura. Los cursos están completos y están todos asignados a usted");
                            }else{
                                //--26-11-18 n:m OK-- ESTADO 10 OBTENGO LA LISTA DE PRIORIDADES MENORES A LA DEL PROFESOR QUE SE QUIERE HACER 
                                //LA ASIGNACION DE LA LISTA DE PROFESORES QUE TIENEN UNA ASIGNATURA
                                AsignaturaProfesor.getlowerpriority({asignaturaId: vm.miAsignatura.id, profesorId: vm.datos.id_profesor}, function (result){
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
                                            profesorid: vm.datos.id_profesor,
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



    }
})();
