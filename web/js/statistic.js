(function () {

    var Model = {
        statistics: [],
        init: function () {
            Octopus.loadUserTrend();
        }
    };

    var Octopus = {
        init: function () {
            Model.init();
            UserTrendView.init();
            GuitarStatisticView.init();
        },
        getStatistics: function () {
            return Model.statistics;
        },
        loadUserTrend: function () {
            let url = 'api/statistic/trend'; // OLD- ./?btAction=statistic&type=trend
            MyUtils.callXhr('GET', url, null, function (dom) {
                let statisticsElem = dom.getElementsByTagName("statistic");
                for (let i = 0; i < statisticsElem.length; i++) {
                    let typeName = statisticsElem[i].getAttribute('type');
                    let attributes = [];
                    let attrsElem = statisticsElem[i].getElementsByTagName('attribute');
                    for (j = 0; j < attrsElem.length; j++) {
                        let name = attrsElem[j].getAttribute('name');
                        let count = attrsElem[j].getAttribute('count');
                        let attribute = {
                            name: name,
                            count: count
                        };
                        attributes.push(attribute);
                    }
                    let statistic = {
                        type: typeName,
                        attributes: attributes
                    }
                    Model.statistics.push(statistic);
                }
                UserTrendView.render();
                GuitarStatisticView.render();
            });

        }
    };

    var UserTrendView = {
        init: function () {
            this.row = document.getElementById('user-trend-view');
            this.render();
        },
        render: function () {
            let $row = this.row;
            let statistics = Octopus.getStatistics();
            if (statistics.length > 0) {
                let trendStatistic = statistics.filter(function (statistic) {
                    if (statistic.type == 'TrendGenre' || statistic.type == 'TrendBodyStyle' || statistic.type == 'TrendPriceLevel' || statistic.type == 'TrendOrigin') {
                        return statistic;
                    }
                });
                
                for (let i = 0; i < trendStatistic.length; i++) {
                    let item = trendStatistic[i];
                    let col = document.createElement('div');
                    col.setAttribute('class', 'col-3');
                    let canvas = document.createElement('canvas');
                    canvas.style.width = '100%';
                    canvas.style.height = 'auto';
                    let legend = document.createElement('ul');
                    legend.setAttribute('class', 'chart-detail');
                    let chartName = '';
                    switch (item.type) {
                        case 'TrendGenre':
                            chartName = 'Xu hướng theo loại đàn';
                            break;
                        case 'TrendBodyStyle':
                            chartName = 'Xu hướng theo dáng đàn';
                            break;
                        case 'TrendPriceLevel':
                            chartName = 'Xu hướng theo giá';
                            break;
                        case 'TrendOrigin':
                            chartName = "Xu hướng theo xuất xứ";
                            break; 
                    }

                    let pieChart = new PieChart(canvas, item.attributes, legend, chartName, null);
                    col.appendChild(canvas);
                    col.appendChild(legend);
                    pieChart.draw();
                    $row.appendChild(col);
                }
            }


        }
    };

    var GuitarStatisticView = {
        init: function () {
            this.row = document.getElementById('guitar-statistic');
            this.render();
        },
        render: function () {
            let $row = this.row;
            let statistics = Octopus.getStatistics();
            if (statistics.length > 0) {
                let pieStatistic = statistics.filter(function (statistic) {
                    if (statistic.type == 'Category' || statistic.type == 'Price' || statistic.type == 'Origin') {
                        return statistic;
                    }
                })

                for (let i = 0; i < pieStatistic.length; i++) {
                    let item = pieStatistic[i];
                    let col = document.createElement('div');
                    col.setAttribute('class', 'col-4');
                    let canvas = document.createElement('canvas');
                    canvas.style.width = '100%';
                    canvas.style.height = 'auto';
                    let legend = document.createElement('ul');
                    legend.setAttribute('class', 'chart-detail');
                    let chartName = '';
                    switch (item.type) {
                        case 'Category':
                            chartName = 'Loại Guitar';
                            break;
                        case 'Price':
                            chartName = 'Mức giá';
                            break;
                        case 'Origin':
                            chartName = 'Xuất xứ';
                            break;
                    }

                    let pieChart = new PieChart(canvas, item.attributes, legend, chartName, null);
                    col.appendChild(canvas);
                    col.appendChild(legend);
                    pieChart.draw();
                    $row.appendChild(col);
                }

                let barStatistic = statistics.filter(function (statistic) {
                    if (statistic.type == 'Brand' || statistic.type == 'Wood') {
                        return statistic;
                    }
                });

                for (let i = 0; i < barStatistic.length; i++) {
                    let item = barStatistic[i];
                    let col = document.createElement('div');
                    col.setAttribute('class', 'col-6');
                    let canvas = document.createElement('canvas');
                    canvas.style.width = '100%';
                    canvas.style.height = 'auto';
                    let legend = document.createElement('div');
                    let chartName = '';
                    let scale = 10;
                    switch (item.type) {
                        case 'Brand':
                            chartName = "Thương hiệu guitar";
                            scale = 10;
                            break;
                        case 'Wood':
                            chartName = "Loại gỗ";
                            scale = 50;
                            break;
                    }
                    let barChart = new BarChart(canvas, item.attributes, legend, chartName, scale, null);
                    col.append(canvas);
                    col.appendChild(legend);
                    barChart.draw();
                    $row.appendChild(col);
                }
            }
        }
    }

    Octopus.init();
})();


