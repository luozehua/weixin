$(function () {

    $("#btn").click(function () {
        $.get("/num/2",function(data,status){
            alert("Data: " + data + "nStatus: " + status);
        });
    });
});