'use strict';

angular.module('app', ['ngRoute', 'ngResource'])
    .config(function ($routeProvider) {
        $routeProvider
            .when('/list', {
                templateUrl: 'partials/list.html',
                controller: 'ListController',
                controllerAs: 'listCtrl'
            })
            .when('/details/:id', {
                templateUrl: 'partials/details.html',
                controller: 'DetailsController',
                controllerAs: 'detailsCtrl'
            })
            .when('/new', {
                templateUrl: 'partials/new.html',
                controller: 'NewController',
                controllerAs: 'newCtrl'
            })
            .otherwise({
                redirectTo: '/list'
            });
    })
    .constant('SIMULATION_ENDPOINT', '/api/simulations/:id')
    .factory('Simulation', function ($resource, SIMULATION_ENDPOINT) {
        return $resource(SIMULATION_ENDPOINT);
    })
    .service('Simulations', function (Simulation) {
        this.getAll = function () {
            return Simulation.query();
        }
        this.get = function (index) {
            return Simulation.get({id: index});
        }
        this.add = function (simulation) {
            simulation.$save();
        }
    })
    .controller('ListController', function (Simulations) {
        var vm = this;
        vm.simulations = Simulations.getAll();
    })
    .controller('DetailsController', function ($routeParams, Simulations) {
        var vm = this;
        var simulationindex = $routeParams.id;
        vm.simulation = Simulations.get(simulationindex);

        var op = Simulations.get(simulationindex);

        op.$promise.then(function (data) {
            console.log(data);
           let days= data.daysOfSimulation;
            console.log(data.ts);

            let piSet = new Array(data.ts)  // pi   liczba osób zarażonych od początku pandemii
            let pvSet = new Array(data.ts)  // pv   liczba osób zmarłych od początku pandemii
            let pmSet = new Array(data.ts)  // pv   liczba osób zmarłych od początku pandemii
            let prSet = new Array(data.ts)  // pr   liczba osób które wyzdrowiały i nabyły odporność od początku pandemii
            let piaSet = new Array(data.ts) // pia   liczba wszystkich zakazonych (aktywnie zarażających) danego dnia
            let pidSet = new Array(data.ts) // pid   liczba nowych zakazonych danego dnia (przyrost)
            let pmdSet = new Array(data.ts) //pmd  liczba zmarlych danego dnia (przyrost)
            let prdSet = new Array(data.ts) // prd   liczba osob ktore wyzdrowialy danego dnia(przyrost)
            let daySet = new Array(data.ts) //day  dzień symulacji
            for (let i = 0; i <data.ts; i++) {
                piSet.push(days[i].pi)
                pvSet.push(days[i].pi)
                pmSet.push(days[i].pi)
                prSet.push(days[i].pi)
                piaSet.push(days[i].pi)
                pidSet.push(days[i].pi)
                pmdSet.push(days[i].pi)
                prdSet.push(days[i].pi)
                daySet.push(days[i].pi)
            }
            console.log(piSet)
            const CHART = angular.element(document.getElementById('lineChart'));
            let lineChart = new Chart(CHART, {
                type: 'line',
                data: {
                    labels: daySet,
                    datasets: [{
                        label: 'Osoby zarażone',
                        data: piaSet,
                    },
                        {
                            label: 'Osoby zmarłe',
                            data: pmSet,
                        }
                    ]
                }
            });
        });



    })
    .controller('NewController', function (Simulations, Simulation) {
        var vm = this;
        vm.simulation = new Simulation();
        vm.saveSimulation = function () {
            Simulations.add(vm.simulation);
            vm.simulation = new Simulation();
        }
    });

//const CHART = document.getElementById('lineChart');
