(function () {
    var Model = {
        guitars: [],
        objForm: [{
            type: 'radio',
            name: 'music-genre'
        },
        {
            type: 'radio',
            name: 'body-style'
        },
        {
            type: 'radio',
            name: 'price-level'
        },
        {
            type: 'radio',
            name: 'origin'
        },
        {
            type: 'hidden',
            name: 'btAction'
        }],
        init: function () {

        }
    };

    var HomeOctopus = {
        init: function () {
            Model.init();
            FormView.init();
        }
    }

    var FormView = {
        init: function () {
            this.searchForm = document.getElementById('filter-form');
            this.searchForm.addEventListener('submit', function (e) {
                e.preventDefault();
                let url = 'DispatchServlet';
                let params = MyUtils.getFormParams(searchForm, objForm);
                MyUtils.callXhr('POST', url, params, function (dom) {
                    let guitars = dom.getElementsByTagName("guitar");
                    console.log(guitars);
                    for (let i = 0; i < guitars.length; i++) {
                        console.log(guitars[i]);
                        var guitarName = MyUtils.getValueOfNodeDomByTagName(guitars[i], 'name');
                        var category = MyUtils.getValueOfNodeDomByTagName(guitars[i], 'category');
                        var price = MyUtils.getValueOfNodeDomByTagName(guitars[i], 'price');
                        var id = MyUtils.getValueOfNodeDomByTagName(guitars[i], 'id');
                        console.log(id + "-" + guitarName + " - " + category + "-" + price);
                    }
                });
            });
            FormView.render();
        },
        render: function () {
            // RENDER FORM VIEW 
        }
    }

    var ResultView = {
        init: function () {

        },
        render: function () {

        }
    }
})();