(function() {
    'use strict';

    angular
        .module('easyscheduleApp')
        .controller('ProfesorListadoCompletoController', ProfesorListadoCompletoController);

        ProfesorListadoCompletoController.$inject = ['$state', 'entity', 'Profesor', 'AsignaturaProfesor', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams'];

    function ProfesorListadoCompletoController($state, entity,Profesor, AsignaturaProfesor, ParseLinks, AlertService, paginationConstants, pagingParams) {

        var vm = this;
       // vm.listado = entity;
       vm.listado = [];
        vm.profesors = entity;
        vm.asignaturaProfesorListDto = [];
        /*vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;*/

        loadAll();

        function loadAll () {
            //console.log ("vm: ", vm);
            vm.profesors.forEach(profesor => {
                //var creditosCubiertos = 0;
                Profesor.getAsignaturasProfesor({id: profesor.id}, function (result){
                    //console.log (": ", result);
                    //vm.asignaturaProf = result;
                        //vm.asignaturaProfesor = result;
                    var i =0;
                    var encontrado = false;
                    var asignaturas = [];
                    //console.log ("vm.asignaturaProfesor EN listado completo: ", result);
                    
                    result.forEach(asignaturaProfesor => {
                        //console.log("entro en  vm.asignaturaProfesor.forEach(asignaturaProfesor =>",asignaturaProfesor );
                        var getasigprof = {
                            id_profesor:asignaturaProfesor.id_profesor,//vm.asignaturaProfesor.profAsigpk.id_profesor,
                            id_asignatura: asignaturaProfesor.id_asig,
                            //id_asignatura_antigua: vm.asignaturaAntigua, //vm.asignaturaProfesor.profAsigpk.id_asignatura,
                            fecha_seleccion: asignaturaProfesor.fecha_seleccion,//vm.asignaturaProfesor.profAsigpk.fechaSeleccion
                            //num_creditos: vm.num_creditos
                        };
                        AsignaturaProfesor.getSubject(getasigprof, function (result){
                            //console.log ("AsignaturaProfesor.getSubject Post  EN listado: ", result);
                            //vm.profesor.asignaturas.push(result);
                            //Array.prototype.push.apply(vegetables, moreVegs);
                            
                            //console.log("profesorasignatura a listar EN DETAILS: ", vm.profesor);
                            asignaturas.push(result);
                            //console.log ("vm.profesors.asignaturas EN listado: ", asignaturas);
                        });
                        /* Profesor.getAsignaturasProfesor({id:asignaturaProfesor.id_profesor}, function (result){
                            //console.log ("getAsignaturasProfesor   front dto para listado : ", result);
                        }); */
                        AsignaturaProfesor.getasigprof(getasigprof, function (result){
                            //console.log ("getasigprof   front dto para listado : ", result);
                            vm.asignaturaProfesorListDto.push(result);
                        },onSuccess,onError);   
                    });
                    //////////
                    //console.log ("llamando a creditos ", asignaturas);
                    creditos(result, profesor, asignaturas);
                },onSuccess, onError);
            }); 
           
        }

        function creditos(asigProf, profesor, asignaturas){
           //console.log("function creditos: asigProf: ", asigProf);
            AsignaturaProfesor.getcreditosdisponibles({profesorId: profesor.id},function(result){
                //console.log("creditos disponibles: ",result);
                var numCreditosDisponibles = parseInt(result[0]);
                listado (asigProf, profesor, numCreditosDisponibles, asignaturas);
            });
        }

        function listado(asigProf, profesor,numCreditosDisponibles, asigs){
            var creditosCubiertos = 0;
            var asignaturasList = [];
            asigProf.forEach(ap => {
                creditosCubiertos = creditosCubiertos + parseInt(ap.num_creditos);
                /*asigs.forEach(asignatura =>{
                    if (ap.id_asig == asignatura.id){
                        console.log("ap.id_asig: ",ap.id_asig);
                        console.log("asignatura.id: ",asignatura.id);
                        var datosAsigMostar = {
                            nombre: asignatura.nombre,
                            numCreditos : ap.num_creditos
                        };
                        //asignaturasList.push(datosAsigMostar);
                    }
                });*/
            });
            
            //console.log("asignaturasList: ",asignaturasList);
            var creditosTotalesImp = parseInt(profesor.numCreditosImpartir);
            var creditosCubrir = creditosTotalesImp - creditosCubiertos;
            var datosListado = {
                prof : profesor,
                asigProf : asigProf,
                credCubiertos: creditosCubiertos,
                credTotalesImpartir: creditosTotalesImp,
                credCubrir: creditosCubrir,
                //asignaturas: asignaturasList
            }
           // console.log ("push: ", datosListado);
            
            vm.listado.push (datosListado);
            //console.log("listado: ",vm.listado);
        }

        function onSuccess(data) {
            //console.log('onSuccess: ', data);
            //console.log('vm.listado: ',vm.listado);
            //console.log ("getasigprof   front dto para listado : ", vm.asignaturaProfesorListDto);
        }
        function onError(error) {
            AlertService.error(error.data.message);
        }
        
        function loadPage(page) {
            vm.page = page;
            vm.transition();
        }

        function transition() {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }
    }
})();