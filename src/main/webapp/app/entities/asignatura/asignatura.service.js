(function() {
    'use strict';
    angular
        .module('easyscheduleApp')
        .factory('Asignatura', Asignatura);

    Asignatura.$inject = ['$resource'];

    function Asignatura ($resource) {
        var resourceUrl =  'api/asignaturas/:id';

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
            'update': { method:'PUT' }
        });
    }
})();
