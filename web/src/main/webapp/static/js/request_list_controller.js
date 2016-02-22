//Animation to hide/display 'more_information' form
$("#more_information").click(function () {
    if ($("#more_information_form").is(":hidden")) {
        $("#more_information_form").slideDown("slow");
        $("#more_information").html("<span class='glyphicon glyphicon-remove' aria-hidden='true'></span> Add more information to your order...");
    } else {
        $("#more_information_form").slideUp("slow");
        $("#more_information").html("<span class='glyphicon glyphicon-plus' aria-hidden='true'></span> Add more information to your order...");
    }
});

$(".request_eraser").click(function () {
    var request = $(this).closest(".request");
    var id = request.attr("data-id");
    var url = "request/" + id + "/delete";
    $.get(url, function () {
            request.remove();
        })
        .fail(function () {
            alert("An unexpected error occurred");
        });
})

$('form[id=send_request]').submit(function () {
    // TODO : LOADING INDICATOR
    var form = $(this);
    var data = form.serialize();
    $.post(form.attr('action'), data, function (response) {
        console.log(response);
        var content = "";
        content = content.concat("<tr>");
        content = content.concat("<td>Just Now</td>");
        if ($('input[name=url]').val() != "") {
            content = content.concat("<td><a href='" + $('input[name=url]').val() + "' target='_blank'>" + $('input[name=title]').val() + "</a></td>");
        }
        else {
            content = content.concat("<td>" + $('input[name=title]').val() + "</td>");
        }
        content = content.concat("<td>" + $('input[name=first_name]').val() + "</td>");
        content = content.concat("<td></td>");
        content = content.concat("</tr>");
        $("#request_tab tbody").append(content);
        form.trigger("reset");
    });
    return false; // prevent default action
});

//Tooltip init.
$(function () {
    $('[data-toggle="tooltip"]').tooltip()
})