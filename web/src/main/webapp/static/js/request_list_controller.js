//Animation to hide/display 'more_information' form
$("#more_information").click(function () {
    if ($("#more_information_form").is(":hidden")) {
        $("#more_information_form").slideDown("slow");
        $("#more_information").html("<span class='glyphicon glyphicon-remove' aria-hidden='true'></span> Additional information ...");
    } else {
        $("#more_information_form").slideUp("slow");
        $("#more_information").html("<span class='glyphicon glyphicon-plus' aria-hidden='true'></span> Additional information ...");
    }
});

$(".delete_request").click(function () {
    var request = $(this).closest(".request");
    var id = request.attr("data-id");
    var url = id + "/delete";
    var confirmation = confirm("Do you really want to erase this order ?");
    if(confirmation) {
        $.ajax({
            type: "GET",
            url: url,
            success: function (response) {
                if (response.status != "SUCCESS") {
                    alert("Error : The order couldn't be deleted.");
                }
                else {
                    request.remove();
                }
            },
            error: function () {
                alert("An unexpected error occurred. Please contact an administrator.");
            },
            dataType: 'json'
        });
    }
})

$(".refuse_request").click(function () {
    var request = $(this).closest(".request");
    var id = request.attr("data-id");
    var url = id + "/refuse";
    $.ajax({
        type: "GET",
        url: url,
        success: function (response) {
            if (response.status != "SUCCESS") {
                alert("Error : The order couldn't be aborted.");
            }
            else {
                request.remove();
            }
        },
        error: function () {
            alert("An unexpected error occurred. Please contact an administrator.");
        },
        dataType: 'json'
    });
})

$(".proceed_workflow").click(function () {
    var request = $(this).closest(".request");
    var id = request.attr("data-id");
    var url = id + "/proceed_workflow";
    $.ajax({
        type: "GET",
        url: url,
        success: function (response) {
            if (response.status != "SUCCESS") {
                alert("Error : Failed to update request status");
            }
            else {
                request.remove();
            }
        },
        error: function () {
            alert("An unexpected error occurred. Please contact an administrator.");
        },
        dataType: 'json'
    });
})

$(".revert_workflow").click(function () {
    var request = $(this).closest(".request");
    var id = request.attr("data-id");
    var url = id + "/revert_workflow";
    $.ajax({
        type: "GET",
        url: url,
        success: function (response) {
            if (response.status != "SUCCESS") {
                alert("Error : Failed to update request status");
            }
            else {
                request.remove();
            }
        },
        error: function () {
            alert("An unexpected error occurred. Please contact an administrator.");
        },
        dataType: 'json'
    });
})

$('form[id=sendRequest]').submit(function () {
    var form = $(this);
    var data = form.serialize();
    $.ajax({
        type: "POST",
        url: form.attr('action'),
        data: data,
        success: function (response) {
            console.log(response);
            if (response.status != "SUCCESS") {
                var errors = response.errorMessageList;
                for (var i = 0; i < errors.length; i++) {
                    var field = errors[i].field;
                    var element = $('input[name=' + field + ']');
                    var tooltipOptions = {
                        title: errors[i].defaultMessage,
                        delay: {"show": 500, "hide": 100}
                    };
                    element.tooltip(tooltipOptions);
                    element.tooltip('show');
                }
            }
            else {
                var content = "";
                content = content.concat("<tr class='hover_container'>");
                content = content.concat("<td>Just Now</td>");
                if ($('input[name=url]').val() != "") {
                    content = content.concat("<td><a href='" + $('input[name=url]').val() + "' target='_blank'>" + $('input[name=title]').val() + "</a> ");
                }
                else {
                    content = content.concat("<td>" + $('input[name=title]').val() + ' ');
                }
                if ($('#comments').val() != "" && $('#comments').val() != undefined) {
                    content = content.concat('<i class="glyphicon glyphicon-info-sign text-info" data-toggle="tooltip" data-placement="right" title="' + $('#comments').val() + '"></i>');
                }
                content = content.concat("</td>");
                content = content.concat("<td>" + $('input[name=firstName]').val() + "</td>");
                content = content.concat("<td class='hidden_element'>");
                content = content.concat("<span class='glyphicon glyphicon-ok text-muted' aria-hidden='true'></span> ");
                content = content.concat("<span class='glyphicon glyphicon-ban-circle text-muted' aria-hidden='true'></span> ");
                content = content.concat("<span class='glyphicon glyphicon-erase text-muted' aria-hidden='true'></span>");
                content = content.concat("</td>");
                content = content.concat("</tr>");
                $("#request_tab tbody").append(content);
                form.trigger("reset");
            }
        },
        error: function () {
            alert("An unexpected error occurred. Please contact an administrator.");
        },
        dataType: 'json'
    });
    return false; // prevent default action
});

//Tooltip init.
$(function () {
    $('[data-toggle="tooltip"]').tooltip();
    $('#deadlinepicker').datetimepicker({ format:"D/MM/YYYY" });
})