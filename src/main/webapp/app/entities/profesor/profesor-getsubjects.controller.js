(function() {
    'use strict';

    angular
        .module('easyscheduleApp')
        .controller('ProfesorGetSubjectsController', ProfesorGetSubjectsController);

    ProfesorGetSubjectsController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Profesor', 
    'Asignatura', 'AlertService', 'paginationConstants', 'pagingParams','previousState' ];

    function ProfesorGetSubjectsController($scope, $rootScope, $stateParams, entity, Profesor, Asignatura,
        AlertService, paginationConstants, pagingParams,previousState) {
        var vm = this;
        vm.asigprof = entity;
        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;

        //loadAll();

       /* function loadAll () {
            Profesor.query({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.profesors = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }*/

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
        var vm = this;
        vm.asigprof = entity;
        vm.previousState = previousState.name;
        console.log('vm: ',vm.asigprof);

        
        
        vm.clear = function () {
            $uibModalInstance.dismiss('cancel');
        };

        var unsubscribe = $rootScope.$on('easyscheduleApp:profesorUpdate', function(event, result) {
            vm.profesor = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();