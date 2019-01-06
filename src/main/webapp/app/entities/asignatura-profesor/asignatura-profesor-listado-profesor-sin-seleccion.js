(function() {
    'use strict';

    angular
        .module('easyscheduleApp')
        .controller('AsignaturasListadoProfesorNoSeleccionController', AsignaturasListadoProfesorNoSeleccionController);

        AsignaturasListadoProfesorNoSeleccionController.$inject = ['$state', 'AsignaturaProfesor' , 'Profesor', 'entity', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams'];

    function AsignaturasListadoProfesorNoSeleccionController($state, AsignaturaProfesor, Profesor, entity, ParseLinks, AlertService, paginationConstants, pagingParams) {

        var vm = this;
        vm.AsignaturaProfesorList = entity;
        vm.profesoresNoSeleccion = [];
        vm.profesores= Profesor.query();
        loadAll();

        function loadAll () {
            vm.profesores.$promise.then(function(ProfesoresList) {
                
                ProfesoresList.forEach(profesor=> {
                    var asignaturaProfesorList = vm.AsignaturaProfesorList;
                    asignaturaProfesorList.forEach(asignaturaProfesor => {
                        //guardo las asignaturas que han sido seleccionadas al menos una vez y las que no han sido seleccionada ninguna
                        if (profesor.id != asignaturaProfesor.profAsigpk.id_profesor){
                            vm.profesoresNoSeleccion.push(profesor);
                        }
                        console.log("vm.profesoresNoSeleccion: ",vm.profesoresNoSeleccion);
                    });
                });

            });
        }

    }
})();
