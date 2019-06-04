(function() {
    'use strict';

    angular
        .module('easyscheduleApp')
        .controller('AsignaturasAll', AsignaturasAll);

    AsignaturasAll.$inject = [ '$state', '$stateParams' ,'Asignatura', 'Profesor', 'entity', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams'];

    function AsignaturasAll( $state, $stateParams, Asignatura, Profesor, entity, ParseLinks, AlertService, paginationConstants, pagingParams) {

        var vm = this;
        console.log ("asignatura.all")
        vm.asignaturasMostrar = entity;
        console.log ("asignaturas: ", vm.asignaturasMostrar);
       
        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.profesor = $stateParams.id;
        vm.asignaturasMostrar = [];
        
        console.log('stateParams controller ',$stateParams.id );
        console.log('vm.profesor ',vm.profesor);
        loadAll();

        function loadAll () {
            // Profesor.get({id: vm.profesor}, function (result) {
            //     vm.prof = result;
                
            // });
            Asignatura.query({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
            function onSuccess(data, headers) {
                console.log('asignatura query all ',data);
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.asignaturas = data;
                //11-05-19. Ya no es necesario seleccionar las asignaturas que se le pueden mostrar porque puede elegir la misma varias veces
                /*for (var i = vm.asignaturas.length - 1; i >= 0; i--) {
                    for (var j = vm.prof.asignaturas.length - 1; j >= 0; j--) {
                        if (vm.prof.asignaturas[j].id != vm.asignaturas[i].id){
                            vm.asignaturasMostrar = vm.asignaturas[i];
                        }
                    }
                    
                }
               console.log('vm.asignaturasMostrar ',vm.asignaturasMostrar);*/
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            
        }

        /*vm.seleccionarAsignatura = function (idAsignatura) {
            console.log(' idAsignatura ',idAsignatura);
            $uibModal.open({
                templateUrl: 'app/entities/profesor/profesor-select-subject.html',
                controller: 'ProfesorSelectSubjectController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        console.log('idAsignatura: ',idAsignatura);
                        return 
                        id_profesor: $stateParams.id,
                        id_asignatura: idAsignatura
                    }
                }
            }).result.then(function (autor) {
                $state.go('asignatura.all', null, { reload: 'asignatura.all' });
            }, function () {
                $state.go('asignatura.all');
            });
        };    */
   
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
 