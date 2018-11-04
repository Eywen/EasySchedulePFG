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
            /*'getsubjects': {
                method: 'POST',
                url: 'api/profesors/getsubjects',
                isArray: true,
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },*/
            'getSubjects': {
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
