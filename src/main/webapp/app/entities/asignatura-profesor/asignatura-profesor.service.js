(function() {
    'use strict';
    angular
        .module('easyscheduleApp')
        .factory('AsignaturaProfesor', AsignaturaProfesor);

    AsignaturaProfesor.$inject = ['$resource'];

    function AsignaturaProfesor ($resource) {
        var resourceUrl =  'api/asignaturaprofesors';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            /*'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    console.log(data);
                    return data;
                }
            },*/
            /*'getasignaturainprof': { 
                method: 'GET',
                //isArray:true,
                url: 'api/asignaturaprofesors/asignaturainprof/:asignatura',
                transformResponse: function (data){
                    if (data){
                        data = angular.fromJson(data);
                    }
                    console.log('getconfirmacion ',data);
                    return data;
                }*/
            /*},*/

            ///////////////////////////////////////////
            'getconfirmacion': {
                method: 'GET',
                url: 'api/asignaturaprofesors/:id_asignatura/:prioridad_profesor',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    console.log('getconfirmacion ',data);
                    return data;
                }
            },
            'getconfirmacionremovemenorprior': {
                method: 'GET',
                url: 'api/asignaturaprofesors/elimnarMenosPrioridad/:id_asignatura/:prioridad_profesor',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'delete':{
                method: 'POST',
                url: 'api/asignaturaprofesors/deleteselection/:id_profesor/:id_asignatura/:fecha',
                //url: 'api/asignaturaprofesors/deleteselection/asignaturaprofesors/deleteselection',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    console.log('After from Jsondata delete selection: ',data);
                    return data;
                }

            },
            //////////////////////////////////////gethighestpriority
            'getlowerpriority': { 
                method: 'GET',
                isArray:true,
                url: 'api/asignaturaprofesors/getlowerpriority/:asignaturaId/:profesorId',
                transformResponse: function (data){
                    if (data){
                        data = angular.fromJson(data);
                    }
                    console.log('getlowerpriority ',data);
                    return data;
                }
            },
                'checkAsignaturainProfesor': { 
                method: 'GET',
                url: 'api/asignaturaprofesors/checkAsignaturainProfesor/:asignaturaId/:profesorId',
                transformResponse: function (data){
                    if (data){
                        data = angular.fromJson(data);
                    }
                    console.log('checkAsignaturainProfesor ',data);
                    return data;
                }
            },
            'gethighestpriority': { 
                method: 'GET',
                isArray:true,
                url: 'api/asignaturaprofesors/gethighestpriority/:asignaturaId/:profesorId',
                transformResponse: function (data){
                    if (data){
                        data = angular.fromJson(data);
                    }
                    console.log('gethighestpriority ',data);
                    return data;
                }
            },
            'countsubject': { 
                method: 'GET',
                url: 'api/asignaturaprofesors/countsubject/:asignaturaId/:profesorId',
                transformResponse: function (data){
                    if (data){
                        data = angular.fromJson(data);
                    }
                    console.log('gethighestpriority ',data);
                    return data;
                }
            },
            'getasignaturainprof': { 
                method: 'POST',
                isArray:true,
                url: 'api/asignaturaprofesors/asignaturainprof',
                transformResponse: function (data){
                    if (data){
                        data = angular.fromJson(data);
                    }
                    console.log('getconfirmacion ',data);
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
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();