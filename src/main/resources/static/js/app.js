'use strict';

angular.module('app', ['ngRoute', 'ngResource', 'jcs-autoValidate'])
    .run([
        'defaultErrorMessageResolver',
        function (defaultErrorMessageResolver) {
            defaultErrorMessageResolver.getErrorMessages().then(function (errorMessages) {
                errorMessages['errorTypeN'] = 'Musisz nazwać swoją symulację';
                errorMessages['errorTypeP'] = 'Musisz podać liczbę całkowitą, większą od 1 i mniejszą od 10 mld';
                errorMessages['errorTypeI'] = 'Musisz podać jakąś liczbę całkowitą większą od zera';
                errorMessages['errorTypeR'] = 'Musisz podać jakąś liczbe różną od zera, nie musi być całkowita';
                errorMessages['errorTypeM'] = 'Musisz podać liczbę z przedziału (0,1), np. 0.2 będzie oznaczać że 20% chorych umrze';
                errorMessages['errorTypeTi'] = 'Musisz podać jakąś liczbę całkowitą większą od zera';
                errorMessages['errorTypeTm'] = 'Musisz podać jakąś liczbę całkowitą większą od zera';
                errorMessages['errorTypeTs'] = 'Musisz podać jakąś liczbę całkowitą większą od zera';
            });
        }
    ])
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
                redirectTo: '/home',
                templateUrl: 'partials/home.html',
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
            let days = data.daysOfSimulation;
            console.log(data.ts);

            let piSet = new Array(0)  // pi   liczba osób zarażonych od początku pandemii
            let pvSet = new Array(0)  // pv   liczba osób zdrowych podatnych na infekcje
            let pmSet = new Array(0)  // pm   liczba osób zmarłych od początku pandemii
            let prSet = new Array(0)  // pr   liczba osób które wyzdrowiały i nabyły odporność od początku pandemii
            let piaSet = new Array(0) // pia   liczba wszystkich zakazonych (aktywnie zarażających) danego dnia
            let pidSet = new Array(0) // pid   liczba nowych zakazonych danego dnia (przyrost)
            let pmdSet = new Array(0) //pmd  liczba zmarlych danego dnia (przyrost)
            let prdSet = new Array(0) // prd   liczba osob ktore wyzdrowialy danego dnia(przyrost)
            let daySet = new Array(0) //day  dzień symulacji

            for (let i = 0; i < data.ts; i++) {
                piSet.push(days[i].pi)
                pvSet.push(days[i].pv)
                pmSet.push(days[i].pm)
                prSet.push(days[i].pr)
                piaSet.push(days[i].pia)
                pidSet.push(days[i].pid)
                pmdSet.push(days[i].pmd)
                prdSet.push(days[i].prd)
                daySet.push(days[i].day)
            }
            console.log(days)
            console.log(days[2])
            console.log(daySet)
            console.log(piSet)
            console.log(pvSet)
            console.log(pmSet)
            console.log(prSet)
            console.log(piaSet)
            console.log(pidSet)
            console.log(pmdSet)
            console.log(prdSet)
            console.log(pvSet[data.ts-1])
            console.log(piSet[data.ts-1])
            console.log(pmSet[data.ts-1])
            console.log(prSet[data.ts-1])

            const CHART1 = angular.element(document.getElementById('lineChart1'));
            const CHART2 = angular.element(document.getElementById('lineChart2'));
            const CHART3 = angular.element(document.getElementById('lineChart3'));
            let lineChart1 = new Chart(CHART1, {
                type: 'line',
                data: {
                    label:'dzień',
                    labels: daySet,
                    datasets: [
                        {
                            label: 'dzienny przyrost zarażonych',
                            data: pidSet,
                            backgroundColor: ['rgba(255, 99, 132, 0.2)'],
                            borderColor: ['rgba(255, 99, 132, 1)'],
                            borderWidth: 1,
                            fill:false
                        },
                        {
                            label: 'dzienny przyrost zmarłych',
                            data: pmdSet,
                            backgroundColor: ['rgba(54, 162, 235, 0.2)'],
                            borderColor: ['rgba(54, 162, 235, 1)'],
                            borderWidth: 1,
                            fill:false
                        },
                        {
                            label: 'dzienny przyrost ozdrowieńców',
                            data: prdSet,
                            backgroundColor: ['rgba(255, 206, 86, 0.2)'],
                            borderColor: ['rgba(255, 206, 86, 1)'],
                            borderWidth: 1,
                            fill:false
                        },
                        {
                            label: 'liczba aktywnych roznosicieli ',
                            data: prdSet,
                            backgroundColor: ['rgba(75, 192, 192, 0.2)'],
                            borderColor: ['rgba(75, 192, 192, 1)'],
                            borderWidth: 1,
                            fill:false
                        }
                    ]
                },
                options: {
                    scales: {
                        yAxes: [{
                            ticks: {
                                beginAtZero: true
                            }
                        }]
                    }
                }
            });
            let lineChart2 = new Chart(CHART2, {
                type: 'line',
                data: {
                    labels: daySet,
                    datasets: [
                        {
                            label: 'liczba zarażonych od początku',
                            data: piSet,
                            backgroundColor: ['rgba(255, 99, 132, 0.2)'],
                            borderColor: ['rgba(255, 99, 132, 1)'],
                            borderWidth: 1,
                            fill:false
                        },
                        {
                            label: 'liczba zmarłych od początku',
                            data: pmSet,
                            backgroundColor: ['rgba(54, 162, 235, 0.2)'],
                            borderColor: ['rgba(54, 162, 235, 1)'],
                            borderWidth: 1,
                            fill:false
                        },
                        {
                            label: 'liczba ozdrowieńców od początku',
                            data: prSet,
                            backgroundColor: ['rgba(255, 206, 86, 0.2)'],
                            borderColor: ['rgba(255, 206, 86, 1)'],
                            borderWidth: 1,
                            fill:false
                        }
                    ]
                },
                options: {
                    scales: {
                        yAxes: [{
                            ticks: {
                                beginAtZero: true
                            }
                        }]
                    }
                }
            });
            let lineChart3 = new Chart(CHART3, {
                type: 'doughnut',
                data: {
                    datasets: [{
                        data: [pvSet[data.ts-1], piSet[data.ts-1], pmSet[data.ts-1], prSet[data.ts-1]],
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(255, 206, 86, 0.2)',
                            'rgba(75, 192, 192, 0.2)'
                        ],
                        borderColor: [
                            'rgba(255, 99, 132, 1)',
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 206, 86, 1)',
                            'rgba(75, 192, 192, 1)'
                        ],
                    }],

                    // These labels appear in the legend and in the tooltips when hovering different arcs
                    labels: [
                        'Osoby niezarażone',
                        'Osoby zarażone',
                        'Osoby zmarłe',
                        'Ozdrowieńcy'
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


