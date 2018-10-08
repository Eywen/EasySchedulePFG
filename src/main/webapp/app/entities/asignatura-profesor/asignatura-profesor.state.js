(function() {
    'use strict';

    angular
        .module('easyscheduleApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('asignatura-profesor', {
            parent: 'entity',
            url: '/asignaturaprofesor?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'easyscheduleApp.asignatura.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/asignatura-profesor/asignatura-profesor.html',
                    controller: 'AsignaturasProfesorController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('asignatura');
                    $translatePartialLoader.addPart('profesor');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
      .state('asignatura-profesor.selectsubject', {
            //parent: 'asignatura-profesor',
            parent: 'home',
            url: '/{id_prof}/selectsubject/{id_asig}',
            data: {
                authorities: ['ROLE_USER'],
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/asignatura-profesor/profesor-select-subject.html',
                    controller: 'ProfesorSelectSubjectController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id_profesor: $stateParams.id_prof,
                                id_asignatura: $stateParams.id_asig,
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('home', null, { reload: 'home' });
                }, function() {
                    $state.go('home');
                });
            }]
        })
        .state('asignatura-profesor.delete', {
            parent: 'asignatura-profesor',
            url: '/{id_prof}/delete/{id_asig}',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/asignatura-profesor/asignatura-profesor-delete-dialog.html',
                    controller: 'AsignacionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: function () {
                            return {                                
                                id_profesor: $stateParams.id_prof,
                                id_asignatura: $stateParams.id_asig,
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('asignatura-profesor', null, { reload: 'asignatura-profesor' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('asignatura-profesor.edit', {
            parent: 'asignatura-profesor',
            url: '/{id_prof}/edit/{id_asig}',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/asignatura-profesor/asignatura-profesor-edit-dialog.html',
                    controller: 'EditAsignacionDialogController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: function () {
                            return {                                
                                id_profesor: $stateParams.id_prof,
                                id_asignatura: $stateParams.id_asig,
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('asignatura-profesor', null, { reload: 'asignatura-profesor' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('asignatura-profesor.conflictos', {
            parent: 'asignatura-profesor',
            url: 'conflictos',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/asignatura-profesor/asignatura-profesor-conflictos.html',
                    controller: 'ProfesorAsignaturaConflictosController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: function () {
                            return {                                
                                
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('asignatura-profesor', null, { reload: 'asignatura-profesor' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('asignatura-profesor.new', {
            parent: 'asignatura-profesor',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/asignatura-profesor/asignatura-profesor-asignacion-modal.html',
                    controller: 'NewAsignacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('asignatura-profesor', null, { reload: 'asignatura-profesor' });
                }, function() {
                    $state.go('asignatura-profesor');
                });
            }]
        })
        
        ;
    }

})();