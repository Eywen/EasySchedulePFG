(function() {
    'use strict';

    angular
        .module('easyscheduleApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('teacher', {
            abstract: true,
            parent: 'app'
        });
    }
})();