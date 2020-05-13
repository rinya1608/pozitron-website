function DropDown(el) {
    this.dd = el;
    this.initEvents();
}
DropDown.prototype = {
    initEvents : function() {
        var obj = this;

        obj.dd.on('click', function(event){
            $(this).toggleClass('active');
            event.stopPropagation();
        });
    }
}

$(function() {

    var dd = new DropDown( $('#profile') );

    $(document).click(function() {
        // all dropdowns
        $('.section-header-top-wrapper-right-profile').removeClass('active');
    });

});
$(function() {

    var dd = new DropDown( $('#admin') );

    $(document).click(function() {
        // all dropdowns
        $('#admin').removeClass('active');
    });

});

