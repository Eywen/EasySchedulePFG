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
        /*usar este metodo para obtener las asignaturas de un profesor, no puedo usar un finad all profesor con sus asignaturas 
        ** pq en find all profesor de la opcion de menu profesor no se cargan las asignaturas q tiene
        */
        Profesor.getSubjects({id: vm.profesor.id}, function (result){
            console.log ("getAsignaturas: ", result);
            vm.profesor.asignaturas = result;
            console.log ("vm.profesors.asignaturas: ", vm.profesor.asignaturas);
            console.log("profesorasignatura a listar: ", vm.profesor);
        });
    }

})();