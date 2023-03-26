window.onload = function () {
    const valueFewDays = document.getElementById("fewDays").value;
    const valueFewDays2 = document.getElementById("fewDays2").value;
    var fewDays = JSON.parse(valueFewDays);
    var fewDays2 = JSON.parse(valueFewDays2);
    var listFewdats = [];
    var listFewdats2 = [];
    let list = fewDays.list;
    let list2 = fewDays2.list;
    list.forEach(element =>{
        let obj= { x: new Date(element.localDate[0]-1,element.localDate[1]-1 ,element.localDate[2]-1), y: element.amount ,indexLabel:element.indexLabel,markerColor:element.markerColor}
        listFewdats.push(obj)
    })
    list2.forEach(element =>{
        let obj= { x: new Date(element.localDate[0]-1,element.localDate[1]-1 ,element.localDate[2]-1), y: element.amount ,indexLabel:element.indexLabel,markerColor:element.markerColor}
        listFewdats2.push(obj)
    })
    // 2
    const valueFewMonth = document.getElementById("fewMonth").value;
    var fewMonth= JSON.parse(valueFewMonth);
    var listFewMonth = [];
    fewMonth.forEach(element =>{
        let obj= { y: element.amount, label: element.label ,indexLabel:element.indexLabel}
        listFewMonth.push(obj)
    })

    /// 3

    const valueFewYear = document.getElementById("fewYear").value;
    var fewYear= JSON.parse(valueFewYear);
    var listFewYear = [];
    fewYear.forEach(element =>{
        let obj= { x: new Date(element.localDate[0],element.localDate[1] ,element.localDate[2]), y: element.amount ,indexLabel:element.indexLabel,markerColor:element.markerColor}
        listFewYear.push(obj)
    })

    // var chart = new CanvasJS.Chart("chartContainer", {
    //
    //     exportEnabled: true,
    //     animationEnabled: true,
    //     title: {
    //         text: "Kunlar kesimida"
    //     },
    //     axisX: {
    //         minimum: new Date(fewDays.minimum[0]-1, fewDays.minimum[1]-1, fewDays.minimum[2]-1),
    //         maximum: new Date(fewDays.maximum[0]-1, fewDays.maximum[1]-1, fewDays.maximum[2]-1),
    //         valueFormatString: "DD MMM",
    //
    //     },
    //     axisY: {
    //         title: "Umumiy miqdor",
    //         titleFontColor: "#4F81BC",
    //         includeZero: true,
    //         suffix: "mn"
    //     },
    //     data: [{
    //         indexLabelFontColor: "dark",
    //         indexLabelFontSize: 18,
    //         name: "views",
    //         type: "area",
    //         yValueFormatString: "#,##0.0mn",
    //         markerSize: 8,
    //         dataPoints:listFewdats,
    //
    //     }]
    // });
    // chart.render();

    var chart = new CanvasJS.Chart("chartContainer", {
        animationEnabled: true,
        title: {
            text: "Daily Email Analysis"
        },
        axisX: {
            minimum: new Date(fewDays.minimum[0]-1, fewDays.minimum[1]-1, fewDays.minimum[2]-1),
            maximum: new Date(fewDays.maximum[0]-1, fewDays.maximum[1]-1, fewDays.maximum[2]-1),
            valueFormatString: "DD MMM",
        },
        axisY: {
            title: "Number of Messages"
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
            type: "area",
            color: "rgba(40,175,101,0.6)",
            markerSize: 0,
            dataPoints: listFewdats
        },
            {
                name: "HUMO",
                showInLegend: true,
                legendMarkerType: "square",
                type: "area",
                color: "rgba(0,75,141,0.7)",
                markerSize: 0,
                dataPoints: listFewdats2
            }]
    });
    chart.render();

    // chart 2

    var chart2 = new CanvasJS.Chart("chartContainer2", {
        animationEnabled: true,
        exportEnabled: true,
        theme: "light2", // "light1", "light2", "dark1", "dark2"
        title:{
            text: "Oylar kesimida"
        },
        axisY: {
            title: "Umumiy miqdor"
        },
        data: [{
            type: "column",
            showInLegend: true,
            indexLabelFontSize: 14,
            legendMarkerColor: "grey",
            indexLabelFontColor: "dark",
            legendText: "Qandeydur yozuv",
            dataPoints: listFewMonth
        }]
    });
    chart2.render();


    /// 3

    var chart3 = new CanvasJS.Chart("chartContainer3", {
        exportEnabled: true,
        animationEnabled: true,
        title:{
            text: "Yillar kesimida"
        },
        axisX: {
            valueFormatString: "YYYY",

        },
        axisY: {
            title: "Revenue in USD",
            valueFormatString: "#,###,###,###",
            suffix: "mn",
            prefix: "$"
        },
        data: [{
            type: "splineArea",
            color: "rgba(54,158,173,.7)",
            markerSize: 10,
            indexLabelFontSize: 20,
            xValueFormatString: "YYYY",
            yValueFormatString: "$#,##0.##",
            dataPoints: listFewYear
        }]
    });
    chart3.render();
}