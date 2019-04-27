(function() {
    'use strict';
    angular
        .module('easyscheduleApp')
        .factory('AsignaturaProfesor', AsignaturaProfesor);

        AsignaturaProfesor.$inject = ['$resource'];

    function AsignaturaProfesor ($resource) {
        var resourceUrl =  'api/asignaturaprofesors';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true, transformResponse: function (data) {
                if (data) {
                    data = angular.fromJson(data);
                   // console.log('service AsignaturaProfesor all: ', data);
                }
                
                return data;
            }},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' },
            'checkAsignaturainProfesor': { //ok 09-12-18
                method: 'GET',
                url: 'api/asignaturaprofesors/checkAsignaturainProfesor/:asignaturaId/:profesorId',
                transformResponse: function (data){
                    //console.log('service  ',data); 
                    return data; 
                }
            },
            'countsubject': {//ok 09-12-18 
                method: 'GET',
                url: 'api/asignaturaprofesors/countsubject/:asignaturaId/:profesorId',
                transformResponse: function (data){
                    if (data){
                        data = angular.fromJson(data);
                    }
                    //console.log('service  countsubject ',data);
                    return data;
                }
            },
            'getcreditosdisponibles': {//ok 09-12-18
                method: 'GET',
                url: 'api/asignaturaprofesors/creditosdisponibles/:profesorId',
                transformResponse: function (data) {
                    if (data){
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'getasignaturainprof': { //ok 09-12-18
                method: 'POST',
                isArray:true,
                url: 'api/asignaturaprofesors/asignaturainprof',
                transformResponse: function (data){
                    if (data){
                        data = angular.fromJson(data);
                    }
                    //console.log('getconfirmacion ',data);
                    return data;
                }
            },
            'gethighestpriority': { //ok 09-12-18
                method: 'GET',
                isArray:true,
                url: 'api/asignaturaprofesors/gethighestpriority/:asignaturaId/:profesorId',
                transformResponse: function (data){
                    if (data){
                        data = angular.fromJson(data);
                    }
                    //console.log('gethighestpriority ',data);
                    return data;
                }
            },
            'getlowerpriority': {//ok 09-12-18 
                method: 'GET',
                isArray:true,
                url: 'api/asignaturaprofesors/getlowerpriority/:asignaturaId/:profesorId',
                transformResponse: function (data){
                    if (data){
                        data = angular.fromJson(data);
                    }
                    //console.log('SERVICE  getlowerpriority ',data);
                    return data;
                }
            }, 
            'getNumCredAsigSeleccionados': { //21-04-19
                method: 'POST',
                url: 'api/asignaturaprofesors/numCreditosSeleccionadosAsignatura',
                transformResponse: function (data){
                    if (data){
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'reasignacion': { //OK 09-12-18
                method: 'POST',
                url: 'api/asignaturaprofesors/reasignacion',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'getSubject': {
                method: 'POST',
                url: 'api/asignaturaprofesors/getsubject',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    //console.log("service get subject: ", data);
                    return data; 
                }
            },
            'getasigprof': {//devuelve un asignaturafrontdto con sus datos mas el nombre del profesor y la asignatura
                method: 'POST',
                url: 'api/asignaturaprofesors/getasigprof',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    //console.log("service get subject: ", data);
                    return data; 
                }
            },
            'delete':{//OK 09-12-18
                method: 'POST',
                url: 'api/asignaturaprofesors/deleteselection/:id_profesor/:id_asignatura/:fecha',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    //console.log('After from Jsondata delete selection: ',data);
                    return data;
                }

            },
        });
    }
})();