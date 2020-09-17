$(document).ready(function () {
    quantity();
});

function quantity() {
    let number = $("input[id^='input_number']");
    let plus = $('#button_plus');
    let minus = $('#button_minus');
    let numberMax = +number.attr('max');
    plus.on('click', function(event){
        if (number.val() < numberMax){
            number.val(+number.val() + 1);
        }

    });
    minus.on('click', function(event){
        if (number.val() > 1){
            number.val(+number.val() - 1);
        }
    });
    number.on('input',function(event){
        if (number.val() > numberMax){
            number.val(numberMax);
        }
        else if (number.val() < 1 || isNaN(number.val())){
            number.val(1);
        }
    });
}

