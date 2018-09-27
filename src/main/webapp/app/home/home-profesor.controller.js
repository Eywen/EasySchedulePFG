(function() {
    'use strict';

    angular
        .module('easyscheduleApp')
        .controller('HomeProfesorController', HomeProfesorController);

    HomeProfesorController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Profesor', 'Asignatura'];

    function HomeProfesorController($scope, $rootScope, $stateParams, previousState, entity, Profesor, Asignatura) {
        var vm = this;

        vm.profesor = entity;
        vm.previousState = previousState.name;
        console.log('vm: ',vm.profesor);
        var unsubscribe = $rootScope.$on('easyscheduleApp:profesorUpdate', function(event, result) {
            vm.profesor = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();