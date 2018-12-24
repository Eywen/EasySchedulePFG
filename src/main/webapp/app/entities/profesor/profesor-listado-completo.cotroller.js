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
        /*vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;*/

        loadAll();

        function loadAll () {
            console.log ("vm: ", vm);
            vm.profesors.forEach(profesor => {
                //var creditosCubiertos = 0;
                Profesor.getAsignaturasProfesor({id: profesor.id}, function (result){
                    console.log ("getAsignaturasProfesor: ", result);
                    //vm.asignaturaProf = result;
                   
                    creditos(result, profesor);
                },onSuccess, onError);
            }); 
           
        }

        function creditos(asigProf, profesor){
           console.log("function creditos: asigProf: ", asigProf);
           
            
            AsignaturaProfesor.getcreditosdisponibles({profesorId: profesor.id},function(result){
                console.log("creditos disponibles: ",result);
                var numCreditosDisponibles = parseInt(result[0]);
                listado (asigProf, profesor, numCreditosDisponibles);
            });
            
        }

        function listado(asigProf, profesor,numCreditosDisponibles){
            var creditosCubiertos = 0;
            asigProf.forEach(ap => {
                creditosCubiertos = creditosCubiertos + parseInt(ap.num_creditos);
            });
            
            console.log("creditosCubiertos: ",creditosCubiertos);
            var creditosTotalesImp = parseInt(profesor.numCreditosImpartir);
            var creditosCubrir = creditosTotalesImp - creditosCubiertos;
            var datosListado = {
                prof : profesor,
                asigProf : asigProf,
                credCubiertos: creditosCubiertos,
                credTotalesImpartir: creditosTotalesImp,
                credCubrir: creditosCubrir
            }
            console.log ("push: ", datosListado);
            
            vm.listado.push (datosListado);
            console.log("listado: ",vm.listado);
        }

        function onSuccess(data) {
            console.log('onSuccess: ', data);
            console.log('vm.listado: ',vm.listado);
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
