var DrawUtil = {
    drawLine: function (ctx, startX, startY, endX, endY, color) {
        ctx.save();
        if (color != null) {
            ctx.strokeStyle = color;
        }
        ctx.beginPath();
        ctx.moveTo(startX, startY);
        ctx.lineTo(endX, endY);
        ctx.stroke();
        ctx.restore();
    },
    drawArc: function (ctx, centerX, centerY, radius, startAngle, endAngle) {
        ctx.beginPath();
        ctx.arc(centerX, centerY, radius, startAngle, endAngle);
        ctx.stroke();
    },
    drawPieSlice: function (ctx, centerX, centerY, radius, startAngle, endAngle, color) {
        ctx.fillStyle = color;
        ctx.beginPath();
        ctx.moveTo(centerX, centerY);
        ctx.arc(centerX, centerY, radius, startAngle, endAngle);
        ctx.closePath();
        ctx.fill();
    },
    drawBar: function (ctx, upperLeftCornerX, upperLeftCornerY, width, height, color) {
        ctx.save();
        ctx.fillStyle = color;
        ctx.fillRect(upperLeftCornerX, upperLeftCornerY, width, height);
        ctx.restore();
    },
}
var BarChart = function (canvas, data, legend, chartName, scale,colors) {
    this.canvas = canvas;
    this.ctx = canvas.getContext('2d');
    this.data = data;
    this.chartName = chartName;
    this.legend = legend;
    this.padding = 20;
    this.gridScale = scale;
    this.gridColor = '#eeeeee';
    this.colors = ["#2d0f41", "#3d1459", "#4d1a70", "#5e1f88", "#742796", "#973490", "#b8428c", "#db5087", "#e96a8d", "#ee8b97", "#f3aca2", "#f9cdac"];

    this.draw = function () {
        var maxValue = 0;
        for (let i = 0; i < this.data.length; i++) {
            maxValue = Math.max(maxValue, this.data[i].count);
        }

        var canvasActualHeight = this.canvas.height;
        var canvasActualWidth = this.canvas.width - this.padding * 2;

        //drawing the grid lines
        var gridValue = 0;
        while (gridValue <= maxValue) {
            var gridY = canvasActualHeight * (1 - gridValue / maxValue);
            DrawUtil.drawLine(
                this.ctx,
                0,
                gridY,
                this.canvas.width,
                gridY,
                this.gridColor
            );

            //writing grid markers
            this.ctx.save();
            this.ctx.fillStyle = "#666";
            this.ctx.textBaseline = "bottom";
            this.ctx.font = "bold 10px";
            this.ctx.fillText(gridValue, 2, gridY-2);
            this.ctx.restore();

            gridValue += this.gridScale;
        }

        //drawing the bars
        var barIndex = 0;
        var numberOfBars = Object.keys(this.data).length;
        var barSize = (canvasActualWidth) / numberOfBars;

        //drawing chart name
        // this.ctx.save();
        // this.ctx.textBaseline = "bottom";
        // this.ctx.textAlign = "center";        
        // this.ctx.font = "bold 1.2rem";
        // this.ctx.fillText(this.chartName, this.canvas.width / 2, this.canvas.height);
        // this.ctx.restore();

        for (let i = 0; i < this.data.length; i++) {
            var val = this.data[i].count;
            var barHeight = Math.round(canvasActualHeight * val / maxValue);
            DrawUtil.drawBar(
                this.ctx,
                this.padding + barIndex * barSize,
                this.canvas.height - barHeight,
                barSize,
                barHeight,
                this.colors[barIndex % this.colors.length]
            );

            barIndex++;
        }

        //draw legend
        barIndex = 0;
        var title = document.createElement('p');
        title.textContent = this.chartName;
        title.style.marginBottom = "10px";
        title.style.textAlign = "center";
        title.style.fontWeight = "bold"; 
        title.style.fontSize = "1.2rem";

        var ul = document.createElement("ul");
        for (let i = 0; i < this.data.length; i++) {
            let categ = this.data[i].name;
            var li = document.createElement("li");
            li.style.display = "inline-block";
            li.style.listStyle = "none";
            li.style.borderLeft = "20px solid " + this.colors[barIndex % this.colors.length];
            li.style.padding = "5px";
            li.textContent = categ;
            li.setAttribute("class", "col-3");
            ul.append(li);
            barIndex++;
        }
        legend.append(title);
        legend.append(ul);
    }

}


var PieChart = function (canvas, data, legend, chartName, colors) {
    this.canvas = canvas;
    this.ctx = canvas.getContext("2d");
    this.data = data;
    this.legend = legend;
    this.chartName = chartName;
    this.colors = ["#b24775", "#f5104d", "#ff5277", "#ff8d73", "#f78bc1", "#5d4037", "#d32f2f"];


    this.addLegend = function () {
        let colorIndex = 0;
        let legendHTML = "";
        let title = "<p style='text-align:center;font-size:1.2rem;font-weight:bold'>" + this.chartName + "</p>";
        legendHTML += title;
        for (let i = 0; i < this.data.length; i++) {
            let name = this.data[i].name;
            let count = this.data[i].count;
            legendHTML += "<li><span style='display:inline-block;width:20px;background-color:" +
                this.colors[colorIndex++] +
                ";'>&nbsp;</span> " +
                name +
                "<span style='float:right'>" +
                count +
                "</span>" +
                "</li>";
        }        
        legend.innerHTML = legendHTML;
    };

    this.draw = function () {
        var total_value = 0;
        var color_index = 0;
        // CALCULATE SUM 
        for (let i = 0; i < this.data.length; i++) {
            var val = parseInt(this.data[i].count);
            total_value += val;
        }

        // DRAW EACH PIE OF CICLE
        var start_angle = 0;
        for (let i = 0; i < this.data.length; i++) {
            val = parseInt(this.data[i].count);
            var slice_angle = 2 * Math.PI * val / total_value;
            DrawUtil.drawPieSlice(
                this.ctx,
                this.canvas.width / 2,
                this.canvas.height / 2,
                Math.min(this.canvas.width / 2, this.canvas.height / 2),
                start_angle,
                start_angle + slice_angle,
                this.colors[color_index % this.colors.length]
            );

            start_angle += slice_angle;
            color_index++;
        }
        start_angle = 0;

        // WRITE PERCENT LABEL
        for (let i = 0; i < this.data.length; i++) {
            val = parseInt(this.data[i].count);
            slice_angle = 2 * Math.PI * val / total_value;
            var pieRadius = Math.min(this.canvas.width / 2, this.canvas.height / 2);
            var labelX = this.canvas.width / 2 + (pieRadius / 2) * Math.cos(start_angle + slice_angle / 2);
            var labelY = this.canvas.height / 2 + (pieRadius / 2) * Math.sin(start_angle + slice_angle / 2);
            var labelText = Math.round(100 * val / total_value);
            this.ctx.fillStyle = "white";
            this.ctx.font = "12pt bold arial";
            this.ctx.fillText(labelText + "%", labelX, labelY);
            start_angle += slice_angle;
        }

        // ADD LEGEND 
        this.addLegend();
    };
}