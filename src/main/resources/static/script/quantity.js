
function Quantity(numberElement,plusElement,minusElement) {
    this.number = numberElement;
    this.plus = plusElement;
    this.minus = minusElement;
    this.initEvents();
}
Quantity.prototype = {
    initEvents : function() {
        var number = this.number;
        var plus = this.plus;
        var minus = this.minus;
        var numberMax = +number.attr('max');
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
}

$(function() {
    var number = new Quantity( $('#input_number'),$('#button_plus'), $('#button_minus'));
});