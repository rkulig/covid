angular.module('app', ['ngResource'])
    .controller('SimulationController', function($http, $resource) {
        var vm = this;
        var Simulation = $resource('api/simulations/:simulationId');
        vm.simulation = new Simulation();

        function refreshData() {
            vm.simulations = Simulation.query(
                function success(data, headers) {
                    console.log('Pobrano dane: ' +  data);
                    console.log(headers('Content-Type'));
                },
                function error(reponse) {
                    console.log(response.status);
                });
        }

        vm.addSimulation = function(simulation) {
            console.log(vm.simulation.__proto__);
            vm.simulation.$save(function(data) {
                refreshData();
                vm.simulation = new Simulation();
            });
        }

        vm.loadData = function(id) {
            vm.details = Simulation.get({simulationId: id});
        }

        vm.appName = 'Simulation Manager';
        refreshData();
    });