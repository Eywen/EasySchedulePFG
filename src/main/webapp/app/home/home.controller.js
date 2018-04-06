(function() {
    'use strict';

    angular
        .module('easyscheduleApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'Profesor'];

    function HomeController ($scope, Principal, LoginService, $state, Profesor) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.userLogin = null;
        vm.login = LoginService.open;
        vm.register = register;
       
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();
        
        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                console.log(vm.account);
                console.log(vm.account.login);
                
                console.log(Profesor.getByLogin({login: vm.account.login}));
                Profesor.getByLogin({login: vm.account.login}, function (result) {
                            vm.userLogin = result;
                             console.log ('user login ',vm.userLogin);
                           
           
                            console.log(parseInt($scope.vm.userLogin.id));
                 });



               
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        

        function register () {
            $state.go('register');
        }
    }
})();
