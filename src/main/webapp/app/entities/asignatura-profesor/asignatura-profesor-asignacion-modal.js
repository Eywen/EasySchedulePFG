(function() {
    'use strict';

    angular
        .module('easyscheduleApp')
        .controller('NewAsignacionDialogController', NewAsignacionDialogController);

    NewAsignacionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Asignatura', 'Profesor','AsignaturaProfesor'];

    function NewAsignacionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Asignatura, Profesor, AsignaturaProfesor) {
        var vm = this;

        //vm.asignatura = entity;
        vm.clear = clear;
        vm.save = save;
        vm.profesors = Profesor.query();
        vm.asignaturas= Asignatura.query();
        vm.datos=[];

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });


        vm.id_asignatura = $stateParams.id_asig;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {            
            vm.isSaving = true;
            AsignaturaProfesor.getasignaturainprof(vm.miAsignatura, function (result){
            	console.log('getasignaturainprof ',result);
            },onSaveSuccess, onSaveError);
            //COMENTADO TEMPORALMENTE PARA HACER LAS PRUEBAS DE VALIDACIONES, PERO ESTE IF ES EL CODIGO DE ASIGNACION AUTOMATICA
            /*if (vm.miAsignatura.id !== null && vm.miProfesor.id !== null ){
		        vm.miProfesor.asignaturas.push(vm.miAsignatura);
		        console.log('vm.profesor.asignaturas  push ',vm.miProfesor.asignaturas);
              	Profesor.update(vm.miProfesor,onSaveSuccess,onSaveError);
            } */ 
            
            //console.log("en guardar asignación");
           /* Profesor.get({id:   vm.miProfesor.id}, function (result) {
            	vm.profesor = result;  
            	//console.log('profesor ',vm.profesor);  
            	//obtener el numero de cursos que tiene esta asignatura
            	AsignaturaProfesor.getconfirmacion({id_asignatura: vm.miAsignatura.id,prioridad_profesor: vm.profesor.prioridad},
            	 function (result) {
		            vm.confirmation = result;
		            //console.log('data from resource to json',  vm.confirmation.estado) ;
		            //si el numero de cursos para esta asignatura está completo
		            if (vm.confirmation.estado === false){
		            	console.log('data en if ',vm.confirmation.estado);
		            	//Elimina la asignatura del profesor con menor prioridad
		            	AsignaturaProfesor.getconfirmacionremovemenorprior({id_asignatura: vm.miAsignatura.id,prioridad_profesor: vm.profesor.prioridad},
		            	 function (result) {
				            vm.asigborrada =  result;
				            console.log('vm.asigborrada ',vm.asigborrada);
				            if (vm.asigborrada.estado === true){
				            	vm.profesor.asignaturas.push(vm.miAsignatura); 
				            	//agregamos la asignatura al profesor
				            	Profesor.update(vm.profesor,onSaveSuccess,onSaveError);
				            }else{
				            	$scope.$emit('easyscheduleApp:asignaturaUpdate', result);
				            	alert ("No se puede realizar la asignación, Los cursos están completos");
				            	console.log('else de asigborrada ');
				            	$uibModalInstance.close(result);
				            }
				        });
		            }else{
		            	console.log('vm.miAsignatura ',vm.miAsignatura);
		            	vm.profesor.asignaturas.push(vm.miAsignatura); 
		            	//agregamos la asignatura al profesor
		            	Profesor.update(vm.profesor,onSaveSuccess,onSaveError);
		            }
		        }); 
        	});	*/
        }

        function onSaveSuccess (result) {
            $scope.$emit('easyscheduleApp:asignaturaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
