$("#doc").click(function () {
    $.ajax({
        url: window.location.origin + "?sort=Doc",
    }).done(function(data) {
        buildPage(data);
    });
});

$("#name").click(function () {
    $.ajax({
        url: window.location.origin + "?sort=Name",
    }).done(function(data) {
        buildPage(data);
    });
});

$(".del").click(function () {
    $.ajax({
        url: window.location.origin + "/delete/" + parseInt($(this).val()),
        type: 'DELETE',
        success: function(result) {
            $("#id").text(result);
            $(this).remove();
            $(".toast").toast('show');
            $(".toast").delay(4000).toast('hide');
        }
    });
});

$('#search').submit(function(event) {
    event.preventDefault();

    let search = document.getElementById("search-box").value;

    if (search === "") {
        $.ajax({
            url: window.location.origin + "?sort=Doc",
        }).done(function(data) {
            buildPage(data);
        });
    } else {
        $.ajax({
            url: window.location.origin + "?surname=" + search,
        }).done(function(data) {
            buildPage(data);
        });
    }
});



function buildPage(data) {
    let tbody = $("#table-info").children("tbody");
    $("#warning").remove();
    tbody.empty();

    if (data.length === 0) {
        $("main").append('<h2 id="warning" class="text-center">Вибачте, нічого не знайдено</h2>');
    } else {
        let str = '';

        for (let i = 0; i < data.length; i++) {
            str += "<tr>\n" +
                "<th scope='row'>" + data[i].document + "</th>\n" +
                "<td>" + data[i].codeId + "</td>\n" +
                "<td>" + data[i].name + "</td>\n" +
                "<td>" + data[i].work + "</td>\n"+
                "<td>\n" +
                    "<button class='btn btn-dark del' value='"+ data[i].document +"' type='button'>Видалити</button>\n" +
                "</td>\n"+
            "</tr>"
        }
        tbody.append(str);
    }
}