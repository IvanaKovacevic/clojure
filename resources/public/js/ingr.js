function searchIngr(text) {
    $.ajax({
        type: "GET",
        url: "/ingredients",
        data: {
            text: text
        },
        dataType: 'json',
        success: function(data) {
            var tableData = "";
            if (data != null) {
                for (var i = 0; i < data.length; i++) {
                    tableData += "<tr>";
                    tableData += "<tr id='" + data[i].id + "' class='clickable-row'>";
                    tableData += "<td>" + data[i].id + "</td>";
                    tableData += "<td>" + data[i].name + "</td>";
                    tableData += "<tr>";
                }
            }
            $("#table-ingr tbody").html(tableData);
        },
        error: function(xhr, status, error) {
            showErrorMessage(xhr.responseText);
        }
    });
}