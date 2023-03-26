window.onload = function () {
    const uzCard = document.getElementById("uzCard").value;
    const humo = document.getElementById("humo").value;
    var uzCardJson = JSON.parse(uzCard);
    var humoJson = JSON.parse(humo);
    var listFewDaysUzCard = [];
    var listFewDaysHumo = [];
    let listUzCard = uzCardJson.list;
    let listHumo = humoJson.list;
    listUzCard.forEach(element => {
        let obj = {
            x: new Date(element.localDate[0] - 1, element.localDate[1] - 1, element.localDate[2] - 1),
            y: element.amount,
            indexLabel: element.indexLabel,
            markerColor: element.markerColor
        }
        listFewDaysUzCard.push(obj)
    })
    listHumo.forEach(element => {
        let obj = {
            x: new Date(element.localDate[0] - 1, element.localDate[1] - 1, element.localDate[2] - 1),
            y: element.amount,
            indexLabel: element.indexLabel,
            markerColor: element.markerColor
        }
        listFewDaysHumo.push(obj)
    })
    // 2
    const MQValue = document.getElementById("MQ").value;
    var MQ = JSON.parse(MQValue);
    var listMQ = [];
    MQ.forEach(element => {
        let obj = {y: element.amount, label: element.label, indexLabel: element.indexLabel}
        listMQ.push(obj)
    })

    /// 3

    const valueFewMonthHumo = document.getElementById("fewMonthHumo").value;
    var fewMonthHumo = JSON.parse(valueFewMonthHumo);
    var listFewMonthHumo = [];
    fewMonthHumo.forEach(element => {
        let obj = {y: element.amount, label: element.label, indexLabel: element.indexLabel}
        listFewMonthHumo.push(obj)
    })

    const valueFewMonthUzCard = document.getElementById("fewMonthUzCard").value;
    var fewMonthUzCard = JSON.parse(valueFewMonthUzCard);
    var listFewMonthUzCard = [];
    fewMonthUzCard.forEach(element => {
        let obj = {y: element.amount, label: element.label, indexLabel: element.indexLabel}
        listFewMonthUzCard.push(obj)
    })


    var chartDaily = new CanvasJS.Chart("chartDaily", {
        animationEnabled: true,
        title: {
            text: "Company Revenue by Daily"
        },
        axisX: {
            minimum: new Date(uzCardJson.minimum[0] - 1, uzCardJson.minimum[1] - 1, uzCardJson.minimum[2] - 1),
            maximum: new Date(humoJson.maximum[0] - 1, humoJson.maximum[1] - 1, humoJson.maximum[2] - 1),
            valueFormatString: "DD MMM",
            labelFontSize: 18,
            labelFontWeight: "bold",
            labelFontColor: "black"

        },
        axisY: {
            title: "Umumiy miqdor"
        },
        legend: {
            verticalAlign: "top",
            horizontalAlign: "right",
            dockInsidePlotArea: true
        },
        toolTip: {
            shared: true
        },
        data: [{
            name: "UZCARD",
            showInLegend: true,
            legendMarkerType: "square",
            indexLabelFontColor: "blue",
            indexLabelFontSize: 16,
            type: "splineArea",
            color: "rgba(40,175,101,0.6)",
            markerSize: 2,
            dataPoints: listFewDaysUzCard
        },
            {
                name: "HUMO",
                showInLegend: true,
                legendMarkerType: "square",
                indexLabelFontColor: "dark",
                indexLabelFontSize: 16,
                type: "splineArea",
                color: "rgba(0,75,141,0.7)",
                markerSize: 2,
                dataPoints: listFewDaysHumo
            }]
    });
    chartDaily.render();

    // chart 2

    var chartMQ = new CanvasJS.Chart("chartMQ", {
        animationEnabled: true,
        exportEnabled: true,
        theme: "light2", // "light1", "light2", "dark1", "dark2"
        title: {
            text: "ActiveMQ Artemis"

        },
        axisY: {
            title: "Number of Messages"

        },
        axisX: {
            labelFontSize: 18,
            labelFontWeight: "bold",
            labelFontColor: "black"
        },
        data: [{
            type: "column",
            showInLegend: true,
            legendMarkerColor: "grey",
            indexLabelFontColor: "blue",
            indexLabelFontSize: 18,
            legendText: "Real time",
            dataPoints: listMQ
        }]
    });
    chartMQ.render();


    /// 3

    var chartMonthly = new CanvasJS.Chart("chartMonthly", {
        animationEnabled: true,
        exportEnabled: true,
        title: {
            text: "Company Revenue by Month"

        },
        axisY: {
            title: "Revenue in USD",
            valueFormatString: "#0,.",
            suffix: "mn",
            prefix: "$"
        },
        axisX: {
            valueFormatString: "MMMM",
            labelFontSize: 18,
            labelFontWeight: "bold",
            labelFontColor: "black"

        },
        toolTip: {
            shared: true
        },
        legend: {
            verticalAlign: "top",
            horizontalAlign: "right",
            dockInsidePlotArea: true
        },
        data: [{

            type: "splineArea",
            showInLegend: true,
            color: "rgba(0,75,141,0.7)",
            markerSize: 5,
            name: "Humo",
            indexLabelFontColor: "dark",
            indexLabelFontSize: 16,
            xValueFormatString: "MMMM",
            yValueFormatString: "$#,##0.##",
            dataPoints: listFewMonthHumo
        },
            {
                type: "splineArea",
                showInLegend: true,
                color: "rgba(40,175,101,0.6)",
                markerSize: 5,
                name: "UzCard",
                xValueFormatString: "MMMM",
                yValueFormatString: "$#,##0.##",
                indexLabelFontColor: "blue",
                indexLabelFontSize: 16,
                dataPoints: listFewMonthUzCard
            }
        ]
    });
    chartMonthly.render();
}