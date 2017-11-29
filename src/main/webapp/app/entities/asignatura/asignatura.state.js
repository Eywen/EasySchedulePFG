(function() {
    'use strict';

    angular
        .module('easyscheduleApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('asignatura', {
            parent: 'entity',
            url: '/asignatura?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'easyscheduleApp.asignatura.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/asignatura/asignaturas.html',
                    controller: 'AsignaturaController',
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
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('asignatura-detail', {
            parent: 'asignatura',
            url: '/asignatura/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'easyscheduleApp.asignatura.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/asignatura/asignatura-detail.html',
                    controller: 'AsignaturaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('asignatura');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Asignatura', function($stateParams, Asignatura) {
                    return Asignatura.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'asignatura',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('asignatura-detail.edit', {
            parent: 'asignatura-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/asignatura/asignatura-dialog.html',
                    controller: 'AsignaturaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Asignatura', function(Asignatura) {
                            return Asignatura.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('asignatura.new', {
            parent: 'asignatura',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/asignatura/asignatura-dialog.html',
                    controller: 'AsignaturaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                plan: null,
                                titulacion: null,
                                creditos: null,
                                num_grupos: null,
                                creditos_teoricos: null,
                                creditos_practicas: null,
                                num_grupos_teoricos: null,
                                num_grupos_practicas: null,
                                usu_alta: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('asignatura', null, { reload: 'asignatura' });
                }, function() {
                    $state.go('asignatura');
                });
            }]
        })
        .state('asignatura.edit', {
            parent: 'asignatura',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/asignatura/asignatura-dialog.html',
                    controller: 'AsignaturaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Asignatura', function(Asignatura) {
                            return Asignatura.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('asignatura', null, { reload: 'asignatura' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('asignatura.delete', {
            parent: 'asignatura',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/asignatura/asignatura-delete-dialog.html',
                    controller: 'AsignaturaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Asignatura', function(Asignatura) {
                            return Asignatura.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('asignatura', null, { reload: 'asignatura' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
