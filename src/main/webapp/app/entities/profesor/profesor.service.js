(function() {
    'use strict';
    angular
        .module('easyscheduleApp')
        .factory('Profesor', Profesor);

    Profesor.$inject = ['$resource'];

    function Profesor ($resource) {
        var resourceUrl =  'api/profesors/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    console.log("profesor: ",data);
                    return data;
                }
            },
            'getByLogin': {
                method: 'GET',
                url: 'api/profesors/login/:login',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'getAsignaturasProfesor': {
                method: 'GET',
                isArray: true,
                url: 'api/profesors/asgnaturasprofesor/:id',
                transformResponse: function (data) {
                    //console.log("return service data getAsignaturasProfesor: ", data);
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    //console.log("return service fromJson(data) getAsignaturasProfesor: ", data);
                    return data;
                }
            },
            'getAsignaturas': {
                method: 'GET',
                isArray: true,
                url: 'api/profesors/asignaturas/:id',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    //console.log("service asignaturas profesor listado dto: ", data);
                    return data;
                }
            },
            'getSubjects': {//obtine los registros asignaturaProfesor de un porfesor
                method: 'GET',
                isArray: true,
                url: 'api/profesors/subjects/:id',
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
