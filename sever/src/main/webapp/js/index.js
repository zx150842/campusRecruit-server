(function ($, exports) {
    $(function () {
        $("#switchPage :button").click(function(){
            var page = $(this).attr("id");
            if(page == "careerTalk"){
                $.post("inputTalk.html");
            }
            else if(page == "recruit"){
                $.post("inputRecruit.html");
            }
        })
    })
})(jQuery,window);

