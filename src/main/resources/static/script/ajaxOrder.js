$(document).ready(function () {

    $("button[id^='buttonConfirmOrder']").on('click', function(event){
        var id = $(this).attr("name");
        ajaxPostOrder("/admin/carts/confirm",id);
    });
    $("button[id^='buttonDelOrder']").on('click', function(event){
        var id = $(this).attr("name");
        delOrder("/admin/carts/del/"+id,id);
    });
    $("button[id^='buttonCancelOrder']").on('click', function(event){
        var id = $(this).attr("name");
        ajaxPostOrder("/admin/carts/cancel",id);
    });

});

function ajaxPostOrder(url,id) {
    let search = {}
    search["id"] = id;
    console.log(JSON.stringify(search));

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        headers: {"X-CSRF-TOKEN": $("#csrf").val()},
        data: JSON.stringify(search),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function () {
            location.reload(true);

        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.warn(typeof jqXHR.responseText)
            console.log(textStatus, errorThrown);

        }
    });
}
function delOrder(url,id) {
    $.ajax({
        type: "DELETE",
        headers: {"X-CSRF-TOKEN": $("#csrf").val()},
        url: url,
        cache: false,
        success: function () {

            $("#orderBlock" + id).fadeOut('slow', function () {
                $(this).remove();
            });
            location.reload(true);
        },
        error: function (jqXHR) {
            console.warn(typeof jqXHR.responseText)

        }
    });
}