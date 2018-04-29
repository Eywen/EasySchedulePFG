(function() {
    'use strict';

    angular
        .module('easyscheduleApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state','Profesor'];

    function HomeController ($scope, Principal, LoginService, $state,Profesor) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                Profesor.getByLogin({login: vm.account.login}, function (result) {
                            vm.userLogin = result;
                             
                 });
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
    }
})();
