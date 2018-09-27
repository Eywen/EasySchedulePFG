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
                method: 'DELETE',
                url: 'api/asignaturaprofesors/deleteselection/:id_profesor/:id_asignatura',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    console.log('After from Jsondata delete selection: ',data);
                    return data;
                }

            },
            'update': { method:'PUT' }
        });
    }
})();