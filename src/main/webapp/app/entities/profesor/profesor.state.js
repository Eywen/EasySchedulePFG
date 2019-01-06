(function() {
    'use strict';

    angular
        .module('easyscheduleApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('profesor', {
            parent: 'entity',
            url: '/profesor?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'easyscheduleApp.profesor.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/profesor/profesors.html',
                    controller: 'ProfesorController',
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
                    $translatePartialLoader.addPart('profesor');
                    $translatePartialLoader.addPart('asignatura');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('profesor-detail', {
            parent: 'profesor',
            url: '/profesor/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'easyscheduleApp.profesor.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/profesor/profesor-detail.html',
                    controller: 'ProfesorDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('profesor');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Profesor', function($stateParams, Profesor) {
                    return Profesor.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'profesor',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('profesor-detail.edit', {
            parent: 'profesor-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/profesor/profesor-dialog.html',
                    controller: 'ProfesorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Profesor', function(Profesor) {
                            return Profesor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('profesor.new', {
            parent: 'profesor',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/profesor/profesor-dialog.html',
                    controller: 'ProfesorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                primerApellido: null,
                                segundoApellido: null,
                                codProfesor: null,
                                email: null,
                                categoria: null,
                                numCreditosImpartir: null,
                                prioridad: null,
                                usuAlta: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('profesor', null, { reload: 'profesor' });
                }, function() {
                    $state.go('profesor');
                });
            }]
        })
        .state('profesor.edit', {
            parent: 'profesor',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/profesor/profesor-dialog.html',
                    controller: 'ProfesorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Profesor', function(Profesor) {
                            return Profesor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('profesor', null, { reload: 'profesor' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('profesor.getsubjects', {
            parent: 'profesor',
            url: '/{id}/getsubjects',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'easyscheduleApp.profesor.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/profesor/profesor-getsubjects.html',
                    controller: 'ProfesorGetSubjectsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('profesor');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Profesor', function($stateParams, Profesor) {
                    console.log('getsubjectstate: ', $stateParams);

                    return Profesor.getSubjects({id : $stateParams.id}).$promise;
                    console.log ('after getsubject service');
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'profesor',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('list_profesor_info', {
            parent: 'home',
            url: '/listinginfoteacher',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'global.menu.lists.list'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/profesor/profesor-listado-completo.html',
                    controller: 'ProfesorListadoCompletoController',
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
                }],
                entity: ['$stateParams', 'Profesor', function($stateParams, Profesor) {
                    var listado = [];
                    
                    return Profesor.query().$promise;
                }]
            }
        })
        .state('profesor.delete', {
            parent: 'profesor',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/profesor/profesor-delete-dialog.html',
                    controller: 'ProfesorDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Profesor', function(Profesor) {
                            return Profesor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('profesor', null, { reload: 'profesor' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
