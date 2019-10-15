function submitForm(){
    $("form").submit(function(event)
    {
//      get data from form`s fields
        var data = {}
        data["windSpeed"] = $("#wind-speed").val("go");
        data["wind-direction"] = $("#wind-direction").children("option:selected").val();
//      disable submit form button while client waiting server response
        $("#wind-submit-button").prop("disabled, true");
//      send form`s data to server as JSON
        $.ajax({
            type: "POST",
            accept: "application/json",
            contentType: "application/json",
            url: "/weather-form",
            data: JSON.stringify(data),
            dataType: 'json',
            timeout: 600000,
            success: function (data) {
                $("#btn-update").prop("disabled", false);
            },
            error: function (event) {

            }
        });
    });
}