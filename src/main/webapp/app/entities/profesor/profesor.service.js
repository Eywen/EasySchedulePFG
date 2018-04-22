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
                        console.log('profesor vm get: ', data);
                    }
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
                    console.log('data: ',data);
                    return data;
                }
            },
            'getSubjects': {
                method: 'GET',
                isArray: true,
                url: 'api/asignaturaprofesors/subjects/:id',
                transformResponse: function (data) {
                    if (data) {
                        console.log ("data  : ", data);
                        console.log ("data toJson : ", angular.toJson(data));
                        console.log('before fromJson data subjects: ',data);
                        data = angular.fromJson(data);
                    }
                    console.log('After from Jsondata subjects: ',data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
