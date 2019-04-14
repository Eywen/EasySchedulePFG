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
        //vm.AsignaturaProfesorList = AsignaturaProfesor.query().$promise.then(function(data){console.log ('AsignaturaProfesor: ', data);});
       
        loadAll();

        function loadAll () {
            //console.log('vm.AsignaturaProfesorList: ', vm.AsignaturaProfesorList);
            vm.profesores.$promise.then(function(ProfesoresList) {
                //console.log('ProfesoresList: ', ProfesoresList);
                ProfesoresList.forEach(profesor=> {
                    var asignaturaProfesorList = vm.AsignaturaProfesorList;
                    //console.log ('asignaturaProfesorList.lenght: ', asignaturaProfesorList.length);
                    //console.log('profesor: ',profesor.id);
                    var agregar = false;
                    var encontrado = false;
                    var index = 0;
                    asignaturaProfesorList.forEach(asignaturaProfesor => {
                       // console.log ('asignaturaProfesor: ', asignaturaProfesor.profAsigpk.id_profesor);
                        //guardo las asignaturas que han sido seleccionadas al menos una vez y las que no han sido seleccionada ninguna
                        
                        if (profesor.id != asignaturaProfesor.profAsigpk.id_profesor ){
                            agregar = true;
                        }else{
                            encontrado = true;
                        }
                        if ((index == (asignaturaProfesorList.length-1)) && (encontrado == false)){
                            //console.log ('index: ', index);
                            //console.log('agregar: ', profesor.id);
                            //console.log('profesor: ', profesor);
                            vm.profesoresNoSeleccion.push(profesor);
                        }

                        index++; 
                    });
                   // console.log("vm.profesoresNoSeleccion: ",vm.profesoresNoSeleccion);
                });

            });
        }

    }
})();
