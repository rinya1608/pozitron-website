$(document).ready(function () {

    $("#formAddProductToCartList").submit(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        fire_ajax_submit("/products/"+$("#spanProductCategoryId").text());

    });
    $("#formAddProductToCartPage").submit(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        fire_ajax_submit("/product/"+$("#spanProductCategoryId").text()+"/"+ $("#spanProductId").text());

    });

});

function fire_ajax_submit(url) {

    var search = {}
    search["name"] = $("#productName").text();
    search["quantity"] = $("#input_number").val();
    console.log(JSON.stringify(search));
    //search["_csrf_param_name"] = $("#csrf").val();

    $("#submitAddProductToCart").prop("disabled", true);

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

            var json = "<h4>Ajax Response</h4>&lt;pre&gt;"
                + JSON.stringify(data, null, 4) + "&lt;/pre&gt;";
            $('#result').html(json);

            console.log("SUCCESS : ", data);
            $("#submitAddProductToCart").prop("disabled", false);

        },
        error: function (e) {

            var json = "<h4>Ajax Response</h4>"
                + e.responseText;
            $('#result').html(json);

            console.log("ERROR : ", e);
            $("#submitAddProductToCart").prop("disabled", false);

        }
    });

}