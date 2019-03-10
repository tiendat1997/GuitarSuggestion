(function () {
    var Model = {
        guitars: [],
        currentPage: 0,
        paging: {
            numberOfPages: 0,
            pageSize: 10,
        },
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
            this.currentPage = 0;
            this.paging.pageSize = 10;
        }
    };

    var HomeOctopus = {
        init: function () {
            Model.init();
            FormView.init();
            PagingView.init();
            ResultView.init();
        },
        newGuitar(id, name, category, price, imageUrl, weightedScore, attributes) {
            let newGuitar = {
                id: id,
                name: name,
                category: category,
                imageUrl: imageUrl,
                price: price,
                weightedScore: weightedScore
            }
            return newGuitar;
        },
        getGuitars: function () {
            return Model.guitars;
        },
        recommendGuitars: function (form) {
            let url = 'DispatchServlet';
            let params = MyUtils.getFormParams(form, Model.objForm);
            var self = this;
            MyUtils.callXhr('POST', url, params, function (dom) {
                let guitars = self.parseGuitarFromDOM(dom);                
                self.bindGuitarToModel(guitars);
                self.setNumberOfPages();
                Model.currentPage = 0;
                PagingView.render();
                ResultView.render();
                window.location.href = "#result";
            });
        },
        parseGuitarFromDOM: function (dom) {
            let guitarDoms = dom.getElementsByTagName("guitar");
            let guitars = [];
            for (let i = 0; i < guitarDoms.length; i++) {
                let id = MyUtils.getValueOfNodeDomByTagName(guitarDoms[i], 'id');
                let guitarName = MyUtils.getValueOfNodeDomByTagName(guitarDoms[i], 'name');
                let category = MyUtils.getValueOfNodeDomByTagName(guitarDoms[i], 'category');
                let price = MyUtils.getValueOfNodeDomByTagName(guitarDoms[i], 'price');
                let imageUrl = MyUtils.getValueOfNodeDomByTagName(guitarDoms[i], 'imageUrl');
                let weightedScore = MyUtils.getValueOfNodeDomByTagName(guitarDoms[i], 'weightedScore');
                let guitar = this.newGuitar(id, guitarName, category, price, imageUrl, weightedScore, null);
                guitars.push(guitar);
            }

            return guitars;
        },
        bindGuitarToModel: function (list) {
            Model.guitars = list;
        },
        createGuitarItem(guitar) {
            let wrapper = document.createElement('div');
            wrapper.setAttribute('class', 'item');
            wrapper.setAttribute('data-guitarid', guitar.id);

            let score = document.createElement('span');
            score.setAttribute('class', 'score');
            score.textContent = guitar.weightedScore;

            let imgCover = document.createElement('img');
            imgCover.setAttribute('src', guitar.imageUrl);

            let title = document.createElement('h2');
            title.setAttribute('class', 'item-title');
            title.textContent = guitar.name;

            let detail = document.createElement('div');
            detail.setAttribute('class', 'item-detail');

            let price = document.createElement('span');
            price.setAttribute('class', 'item-price');
            price.textContent = parseInt(guitar.price).toLocaleString('vi', { style: 'currency', currency: 'VND' });;

            detail.append(price);

            wrapper.append(score, imgCover, title, detail);
            return wrapper;
        },
        setNumberOfPages: function () {
            let paging = Model.paging;
            paging.numberOfPages = Math.ceil(Model.guitars.length / paging.pageSize);
        },
        getNumberOfPages: function () {
            return Model.paging.numberOfPages;
        },
        setCurrentPage: function (index) {
            Model.currentPage = index;
            ResultView.render();
            PagingView.render();
        },
        getCurrentPage: function(){
            return Model.currentPage;
        },
        loadGuitarPerPage: function () {
            let guitarLength = Model.guitars.length;
            let index = Model.currentPage;
            let numPages = Model.paging.numberOfPages;
            let size = Model.paging.pageSize;
            let start = index * size;
            let end = start + size;
            let page = []; // guitar in a page
            if (index == numPages) { // LAST PAGE
                end = guitarLength;
            }
            page = Model.guitars.slice(start, end);            
            return page;
        }
    }

    var FormView = {
        init: function () {
            this.searchForm = document.getElementById('filter-form');
            this.searchForm.addEventListener('submit', function (e) {
                e.preventDefault();
                HomeOctopus.recommendGuitars(this); // recommend recommend guitar with form 
            });
            FormView.render();
        },
        render: function () {
            // RENDER FORM VIEW 
        }
    }

    var ResultView = {
        init: function () {
            this.container = document.getElementById('guitar-container');
            ResultView.render();
        },
        render: function () {
            let $container = this.container;

            let guitars = HomeOctopus.loadGuitarPerPage();
            // CLEAR VIEW 
            $container.innerHTML = '';

            guitars.forEach(function (guitar) {
                let item = HomeOctopus.createGuitarItem(guitar);
                item.addEventListener('click', (function (copy) {
                    return function () {
                        alert("CLICK");
                        console.log(guitar);
                    }
                })(guitar));
                $container.appendChild(item);
            });

        }
    }

    var PagingView = {
        init: function () {
            this.paging = document.getElementById('guitar-paging');
        },
        render: function () {
            let $paging = this.paging;
            let numOfPages = HomeOctopus.getNumberOfPages();
            let currentPage = HomeOctopus.getCurrentPage();

            // CLEAR PAGING LIST 
            $paging.innerHTML = '';

            for (let i = 0; i < numOfPages; i++) {
                let pageItem = document.createElement('a');
                pageItem.setAttribute('href', '#' + i);
                pageItem.style.margin = '5px';
                pageItem.textContent = i + 1;
                pageItem.style.fontWeight = 'normal';
                if (i == currentPage) {
                    pageItem.style.fontWeight = 'bolder';
                }
                
                pageItem.addEventListener('click', function (e) {
                    e.preventDefault();
                    HomeOctopus.setCurrentPage(i);
                    window.location.href = '#result';
                });
                $paging.appendChild(pageItem);
            }
        }
    }

    HomeOctopus.init();
})();