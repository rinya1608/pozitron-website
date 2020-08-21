$(document).ready(function () {

    $("button[id^='submitAddProductToCart']").on('click', function(event){
        var id = $(this).attr("name");
        console.log(id);
        event.preventDefault();
        addProduct("/cart/add",id);

    });

    $("button[id^='submitDelProduct']").on('click', function(event){
        var id = $(this).attr("name");
        delProduct("/cart/del/" + id,id);
    });

    $("button[id^='button_plus']").on('click', function(event){
        let id = $(this).attr("name");
        let number = $('#input_number' + id);
        let numberMax = +number.attr('max');

        if (number.val() < numberMax){
            number.val(+number.val() + 1);
            changeQuantity("/cart/change",id);
        }
    });
    $("button[id^='button_minus']").on('click', function(event){
        let id = $(this).attr("name");
        let number = $('#input_number' + id);

        if (number.val() > 1){
            number.val(+number.val() - 1);
            changeQuantity("/cart/change",id);
        }
    });
    $("input[id^='input_number']").on('input', function(event){
        let id = $(this).attr("name");
        let numberMax = +this.attr('max');

        if (this.val() > numberMax){
            this.val(+numberMax);
        }
        else if (this.val() < 1 || isNaN(this.val())){
            this.val(1);
        }
        changeQuantity("/cart/change",id);
    });

});

function addProduct(url,id) {
    var search = {}
    search["id"] = id;
    search["quantity"] = $("#input_number" + id).val();
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


        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.warn(typeof jqXHR.responseText)
            console.log(textStatus, errorThrown);

        }
    });
}

function delProduct(url,id) {
    $.ajax({
        type: "DELETE",
        headers: {"X-CSRF-TOKEN": $("#csrf").val()},
        url: url,
        cache: false,
        success: function (data) {

            $("#costAllProducts").html(JSON.stringify(data));
            $("#productSection" + id).fadeOut('slow', function () {
                $(this).remove();
            });
            location.reload(true);
        },
        error: function (jqXHR) {
            console.warn(typeof jqXHR.responseText)

        }
    });
}

function changeQuantity(url,id) {
    var search = {}
    search["id"] = id;
    search["quantity"] = $("#input_number" + id).val();

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        headers: {"X-CSRF-TOKEN": $("#csrf").val()},
        data: JSON.stringify(search),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {

            $("#costAllProducts").html(JSON.stringify(data));

        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.warn(typeof jqXHR.responseText)

        }
    });
}