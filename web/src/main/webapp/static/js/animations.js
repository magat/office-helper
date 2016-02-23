//Animation to hide/display 'more_information' form
$("#more_information").click(function () {
    if ($("#more_information_form").is(":hidden")) {
        $("#more_information_form").slideDown("slow");
    } else {
        $("#more_information_form").slideUp("slow");
    }
});