(function() {
    'use strict';

    angular
        .module('easyscheduleApp')
        .controller('AsignaturasProfesorController', AsignaturasProfesorController);

    AsignaturasProfesorController.$inject = ['$state', 'Asignatura','AsignaturaProfesor', 'Profesor', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams'];

    function AsignaturasProfesorController($state, Asignatura, AsignaturaProfesor, Profesor, ParseLinks, AlertService, paginationConstants, pagingParams) {

        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.profesors=[];
       // vm.AsignaturasProfesorList = [];
     
        
        loadAll();

        function loadAll () {
            Profesor.query({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
               //subject: getsubjects(data),
                sort: sort(),
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function getsubjects(page){
                console.log("page ",page);
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.profesors = data;
                vm.page = pagingParams.page;
                var profesorId;
                console.log("Profesores: ",vm.profesors);
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
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
