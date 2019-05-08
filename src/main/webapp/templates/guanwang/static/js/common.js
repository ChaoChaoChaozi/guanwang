//客服
$(function () {
    $(".qqkefu .top").click(function () {
        $("html,body").animate({ "scrollTop": 0 }, 1000);
    })


    //QQ
    $(".qq_czaa").hover(function () {
        var idw = this.id;
        if (navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.split(";")[1].replace(/[ ]/g, "") == "MSIE7.0") {
            $(this).stop(true, false).animate({ width: idw, "left": "-85px" }, 300);
        } else {
            $(this).stop(true, false).animate({ "width": "160px" }, 300);
        }
    }, function () {
        if (navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.split(";")[1].replace(/[ ]/g, "") == "MSIE7.0") {
            $(this).stop(true, false).animate({ "width": "45px", "left": "0" }, 300);
        } else {
            $(this).stop(true, false).animate({ "width": "45px" }, 300);
        }
    })

    $(".qq_czb").hover(function () {
        $(".erweima").show(50)
    }, function () {
        $(".erweima").hide(50)
    })

});
