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
            'checkAsignaturainProfesor': { 
                method: 'GET',
                url: 'api/asignaturaprofesors/checkAsignaturainProfesor/:asignaturaId/:profesorId',
                transformResponse: function (data){
                    console.log('service  ',data); 
                    return data; 
                }
            },
            'countsubject': { 
                method: 'GET',
                url: 'api/asignaturaprofesors/countsubject/:asignaturaId/:profesorId',
                transformResponse: function (data){
                    console.log('service  countsubject ',data);
                    return data;
                }
            },
            'getcreditosdisponibles': {
                method: 'GET',
                url: 'api/asignaturaprofesors/creditosdisponibles/:profesorId',
                transformResponse: function (data) {
                    return data;
                }
            },
            'getasignaturainprof': { //ok 09-12-08
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
            'getlowerpriority': { 
                method: 'GET',
                isArray:true,
                url: 'api/asignaturaprofesors/getlowerpriority/:asignaturaId/:profesorId',
                transformResponse: function (data){
                    if (data){
                        data = angular.fromJson(data);
                    }
                    console.log('SERVICE  getlowerpriority ',data);
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
        });
    }
})();