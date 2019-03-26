(function () {

    var Model = {
        init: function () {

        }
    };

    var Octopus = {
        init: function () {
            AttributeView.init();
        }
    };

    var AttributeView = {
        init: function () {
            this.toogleLabel = document.querySelectorAll('.lbl-toggle');

            this.render();
        },
        render: function () {
            let $toggleLabels = this.toogleLabel;
            Array.from($toggleLabels).forEach(function (item) {
                item.addEventListener('keydown', e => {
                    // 32 === spacebar
                    // 13 === enter
                    if (e.which === 32 || e.which === 13) {
                        e.preventDefault();
                        item.click();
                    };
                });
            });           
        }
    }

    Octopus.init();
})();

