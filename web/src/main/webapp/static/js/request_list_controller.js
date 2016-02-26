function upperCaseFirstLetter(s) {
    return s.charAt(0).toUpperCase() + s.substring(1);
}

function getRequestObject(element) {
    return element.closest(".request");
}

function getElementId(element) {
    var request = element.closest(".request");
    return request.attr("data-id");
}

function deleteRequest(element) {
    var url = getElementId(element) + "/delete";
    var confirmation = confirm("Do you really want to erase this order ?");
    if (confirmation) {
        $.ajax({
            type: "GET",
            url: url,
            dataType: 'json',
            success: function (response) {
                if (response.status != "SUCCESS") {
                    alert("Error : The order couldn't be deleted.");
                }
                else {
                    getRequestObject(element).remove();
                }
            },
            error: function () {
                alert("An unexpected error occurred. Please contact an administrator.");
            }
        });
    }
}

function refuseRequest(element) {
    var url = getElementId(element) + "/refuse";
    var confirmation = confirm("Do you really want to refuse this order ?");
    if (confirmation) {
        $.ajax({
            type: "GET",
            url: url,
            dataType: 'json',
            success: function (response) {
                if (response.status != "SUCCESS") {
                    alert("Error : The order couldn't be aborted.");
                }
                else {
                    getRequestObject(element).remove();
                }
            },
            error: function () {
                alert("An unexpected error occurred. Please contact an administrator.");
            }
        });
    }
}

function proceedWorkflow(element) {
    var url = getElementId(element) + "/proceed_workflow";
    $.ajax({
        type: "GET",
        url: url,
        dataType: 'json',
        success: function (response) {
            if (response.status != "SUCCESS") {
                alert("Error : Failed to update request status");
            }
            else {
                getRequestObject(element).remove();
            }
        },
        error: function () {
            alert("An unexpected error occurred. Please contact an administrator.");
        }
    });
}

function revertWorkflow(element) {
    var url = getElementId(element) + "/revert_workflow";
    $.ajax({
        type: "GET",
        url: url,
        dataType: 'json',
        success: function (response) {
            if (response.status != "SUCCESS") {
                alert("Error : Failed to update request status");
            }
            else {
                getRequestObject(element).remove();
            }
        },
        error: function () {
            alert("An unexpected error occurred. Please contact an administrator.");
        }
    });
}

function refreshControls() {
    $(".table-request").on('click', ".delete_request", function(){deleteRequest($(this)); return false;});
    $(".table-request").on('click', ".refuse_request", function(){refuseRequest($(this)); return false;});
    $(".table-request").on('click', ".proceed_workflow", function(){proceedWorkflow($(this)); return false;});
    $(".table-request").on('click', ".revert_workflow", function(){revertWorkflow($(this)); return false;});
}

function displayErrors(errors) {
    for (var i = 0; i < errors.length; i++) { //List errors
        var field = errors[i].field;
        var element = $('input[name=' + field + ']');
        var tooltipOptions = {
            title: errors[i].defaultMessage,
            delay: {"show": 500, "hide": 100}
        };

        //Display the error tooltips
        element.tooltip(tooltipOptions);
        element.tooltip('show');

        //Add red border
        var formGroup = element.closest(".form-group");
        formGroup.addClass("has-error");
    }
}

function eraseErrors() {
    $(".has-error").removeClass("has-error");
    $("input").tooltip("destroy");
}

$('form[id=sendRequest]').submit(function () {
    var form = $(this);
    var data = form.serialize();
    $.ajax({
        type: "POST",
        url: form.attr('action'),
        data: data,
        success: function (response) {
            if (response.status != "SUCCESS") {
                displayErrors(response.errorMessageList);
            }
            else {
                var content = "";

                //Format deadline display
                var deadline = $('input[name=dateDeadline]').val();
                deadline = upperCaseFirstLetter(moment(deadline, "DD/MM/YYYY").format("MMM D, YYYY"));
                if (deadline == "Invalid date") {
                    deadline = "";
                }

                //Generate a new row
                content = content.concat("<tr class='request hover_container' data-id='" + response.body + "'>");

                //Current date
                content = content.concat("<td>" + upperCaseFirstLetter(moment().format("MMM D, YYYY")) + "</td>");

                //Deadline
                content = content.concat("<td>" + deadline + "</td>");

                //Product info
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

                //Quantity
                content = content.concat("<td>" + $('input[name=quantity]').val() + "</td>");

                //Author
                content = content.concat("<td>" + $('input[name=firstName]').val() + "</td>");

                //Disabled controls
                content = content.concat("<td>");
                content = content.concat("<span class='hidden_element'>");
                content = content.concat("<a href='#' class='proceed_workflow'><span class='glyphicon glyphicon-ok' aria-hidden='true'></span></a> ");
                content = content.concat("<a href='#' class='text-danger refuse_request'><span class='glyphicon glyphicon-ban-circle' aria-hidden='true'></span></a>");
                content = content.concat("</span>");
                content = content.concat("</td>");
                content = content.concat("</tr>");

                //Add the row to the DOM
                $("#request_tab tbody").append(content);

                //Reset form
                form.trigger("reset");

                //Erase Red color and tooltips on fields
                eraseErrors();

                //Refresh Tootips
                $('[data-toggle="tooltip"]').tooltip();
            }
        },
        error: function () {
            alert("An unexpected error occurred. Please contact an administrator.");
        },
        dataType: 'json'
    });
    return false; // prevent default form action
});

//Page init.
$(function () {
    moment.locale("en"); //Locale for date
    $('[data-toggle="tooltip"]').tooltip(); //Init. Tooltips
    $('#deadlinepicker').datetimepicker({format: "D/MM/YYYY", allowInputToggle: true}); //Init date format
    $(".date-now").html(upperCaseFirstLetter(moment().format("MMM D, YYYY"))); //Init "now" date
    refreshControls();
});