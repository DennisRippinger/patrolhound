var clickHandler = function (e) {
    e.preventDefault();
    $(this).tab('show');
};
$('#jobTab a[href="#new"]').click(clickHandler);
$('#jobTab a[href="#all"]').click(clickHandler);