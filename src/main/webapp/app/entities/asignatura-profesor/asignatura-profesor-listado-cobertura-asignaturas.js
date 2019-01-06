(function() {
    'use strict';

    angular
        .module('easyscheduleApp')
        .controller('AsignaturasListadoCoberturaController', AsignaturasListadoCoberturaController);

        AsignaturasListadoCoberturaController.$inject = ['$state', 'AsignaturaProfesor' , 'Asignatura', 'entity', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams'];

    function AsignaturasListadoCoberturaController($state, AsignaturaProfesor, Asignatura, entity, ParseLinks, AlertService, paginationConstants, pagingParams) {

        var vm = this;
        vm.AsignaturaProfesorList = entity;
        vm.num_creditos = 0;
        vm.asigTotalCreditosCubiertos = []; //array con las asignaturas que tienen todos los creditos posibles cubiertos
        vm.asigCreditosTotalNoCubiertos = []; //array con las asignaturas que no tienen todos los creditos cubiertos
        vm.asigCreditosTotalSobreCubiertos = []; //array con las asignaturas que tienen mas creditos de los permitidos
        vm.asignaturas= Asignatura.query();
        vm.asignaturasList = [];
        vm.asignaturasNoSeleccionadasList = [];
        vm.asigsSeleccionadasList = [];//almacena la lista con el id de las asignaturas seleccionadas y el total de creditos de ésta
        loadAll();

        function loadAll () {
            /*vm.AsignaturaProfesorList.forEach(element => {
                vm.num_creditos = vm.num_creditos + element.num_creditos;
            });*/

            vm.asignaturas.$promise.then(function(asignaturasList) {
                
                asignaturasList.forEach(asignatura=> {
                    var asignaturaProfesorList = vm.AsignaturaProfesorList;
                    asignaturaProfesorList.forEach(asignaturaProfesor => {
                        //guardo las asignaturas que han sido seleccionadas al menos una vez y las que no han sido seleccionada ninguna
                        if (asignatura.id == asignaturaProfesor.profAsigpk.id_asignatura){
                            var index = 0;
                            var esta = false;
                                //si es el primer elemento de la lista
                                if (vm.asigsSeleccionadasList.length == 0){
                                    var datos = {
                                        asig: asignatura,
                                        idAsignatura: asignatura.id,
                                        numCreditos: asignaturaProfesor.num_creditos,
                                        credPermitidos: parseInt(asignatura.num_grupos) * parseInt(asignatura.creditos)
                                    }
                                    vm.asigsSeleccionadasList.push(datos);
                                }else{
                                    var encontrada = false;
                                    var posicionEncontrada = null;
                                        while (index < vm.asigsSeleccionadasList.length){
                                            if (asignatura.id == vm.asigsSeleccionadasList[index].idAsignatura){
                                                console.log("esta");
                                                encontrada = true;
                                                posicionEncontrada = index;
                                                index = vm.asigsSeleccionadasList.length;
                                            }
                                            index++;
                                        }
                                        if(encontrada === true){
                                            console.log("encontrada "+encontrada+" posicionEncontrada: "+posicionEncontrada+" asignatura.id: "+asignatura.id);
                                            var datos = {
                                                asig: asignatura,
                                                idAsignatura: asignatura.id,
                                                numCreditos: asignaturaProfesor.num_creditos + vm.asigsSeleccionadasList[posicionEncontrada].numCreditos,
                                                credPermitidos: parseInt(asignatura.num_grupos) * parseInt(asignatura.creditos)
                                            }
                                            //elimino la asignatura que ya estaba
                                            vm.asigsSeleccionadasList.splice(posicionEncontrada, 1);
                                            //agrego la asignatura con el numero de creditos actualizado
                                            vm.asigsSeleccionadasList.push(datos);
                                        }else{
                                            var datos = {
                                                asig: asignatura,
                                                idAsignatura: asignatura.id,
                                                numCreditos: asignaturaProfesor.num_creditos,
                                                credPermitidos: parseInt(asignatura.num_grupos) * parseInt(asignatura.creditos)
                                            }
                                            vm.asigsSeleccionadasList.push(datos);
                                        }
                                }
                        }
                    });
                    //recorro el array de las asignaturas que han sido seleccinadas para buscar las que no tengan todos los creditos cubiertos
                });
                console.log("vm.asigsSeleccionadasList: ", vm.asigsSeleccionadasList);
                //almacenar en un array las asignaturas que tienen todos los creditos cubiertos
                if (vm.asigsSeleccionadasList.length > 0){
                    var asigsSeleccionadasList = vm.asigsSeleccionadasList;
                    asigsSeleccionadasList.forEach(asigSeleccionada => {
                        if (asigSeleccionada.numCreditos == asigSeleccionada.credPermitidos){
                            var datos = {
                                asignatura: asigSeleccionada.asig,
                                creditosCubiertos: asigSeleccionada.numCreditos,
                                creditosPermitidos: asigSeleccionada.credPermitidos
                            }
                            vm.asigTotalCreditosCubiertos.push(datos);
                            console.log("vm.asigTotalCreditosCubiertos: ", vm.asigTotalCreditosCubiertos);
                        }else if (asigSeleccionada.numCreditos < asigSeleccionada.credPermitidos){
                            var datos = {
                                asignatura: asigSeleccionada.asig,
                                creditosCubiertos: asigSeleccionada.numCreditos,
                                creditosPermitidos: asigSeleccionada.credPermitidos
                            }
                            vm.asigCreditosTotalNoCubiertos.push(datos);
                            console.log("vm.asigCreditosTotalNoCubiertos: ", vm.asigCreditosTotalNoCubiertos);
                        }/*else{
                            var datos = {
                                asignatura: asigSeleccionada.asig,
                                creditosCubiertos: asigSeleccionada.numCreditos,
                                creditosPermitidos: asigSeleccionada.credPermitidos
                            }
                            vm.asigCreditosTotalSobreCubiertos.push(datos);
                            console.log("vm.asigCreditosTotalSobreCubiertos: ", vm.asigCreditosTotalSobreCubiertos);
                        }*/
                    });
                }

            });
            
            
        }

    }
})();
