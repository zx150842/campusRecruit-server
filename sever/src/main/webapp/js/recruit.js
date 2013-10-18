(function ($, exports) {
    $(function () {

        var org_pubDate = $("#org_pubDate").val();
        var org_state = $("#org_state").val();
        var org_statetime = $("#org_statetime").val();
        $("#createdTime").val(org_pubDate);

        var org_title = $("#org_title").val();
        $("#position").val(org_title);

        var org_province = $("#org_province").val();
        $("#province").val(org_province);
        $("#place").val(org_province);

        var org_state = $("#org_state").val();
        $("#state").val(org_state);

        var org_statetime = $("#org_statetime").val();
        $("#statetime").val(org_statetime);

        var org_industry = $("#org_industry").val();
        $("#industry").val(org_industry);

        var org_famous = $("#org_famous").val();
        $("#famous").val(org_famous);

        var org_url = $("#org_url").val();
        $("#url").val(org_url);

        var org_img = $("#org_img").val();
        $("#img").val(org_img);



        var db = $("#db").val();
        if((db == 2) || (db == 3)) {
            var org_content = $("#org_content").val();
            $("#description").val(org_content);

            var source_type = $("#source_type").val();
            if(source_type == "国企")
                $("#type").val(1);
            else if(source_type == "私企")
                $("#type").val(2);
            else if(source_type == "外企")
                $("#type").val(3);

            var source_province = $("#source_province").val();
            $("#province").val(source_province);

            $("#place").val(source_province);

            var indus = new Array();
            indus = $("#source_industry").val().split(",");
            for (i=0;i<indus.length ;i++ ){
                if(indus[i] != ""){
                    if(indus[i] == "通信")
                        $("#computer").attr("checked",true);
                    if(indus[i] == "电子")
                        $("#electron").attr("checked",true);
                    if(indus[i] == "计算机")
                        $("#communication").attr("checked",true);
                    if(indus[i] == "金融")
                        $("#financial").attr("checked",true);
                }
            }
        }

        var key = $("#org_title").val();
        $.post("search/find.json",{key:key},function(data){
            data = data.companies;
            var json = eval(data);
            $("#companyList").html("");
            for(var i=0; i<json.length; i++){
                $("#companyList").append('<li>'+json[i].name+'<span class="btn" id="'+json[i].id+'">复制</span></li>');
            }
        });

        $("#companyList li span").live("click",function(){
            var id = $(this).attr("id");
            $.post("mobile/company.json",{companyID:id},function(data){
                data = data.campusRecruit;
                $("#companyName").val(data.companyName);
                $("#type").val(data.type);
                if(data.db == 1){
//                    $("#type").val(data.type);
                    $("#province").val(data.place);
                    if(org_province == ""){
                        $("#place").val(data.place);
                    }
                    var indus = new Array();
                    indus = data.industry.split(",");
                    for (i=0;i<indus.length ;i++ ){
                        if(indus[i] != ""){
                            if(indus[i] == "计算机")
                                $("#computer").attr("checked",true);
                            if(indus[i] == "电子")
                                $("#electron").attr("checked",true);
                            if(indus[i] == "计算机")
                                $("#communication").attr("checked",true);
                            if(indus[i] == "金融")
                                $("#financial").attr("checked",true);
                        }
                    }
                }
                $("#famous").val(data.famous);
                $("#info").val(data.introduction);
                $("#state").val(data.company_state);
                $("#statetime").val(data.statetime);
            })
        })

        $("#showCompanyList").click(function(){
            $.post("companyList.json", function(data){
                var list = data.list;
                company= $.template("company","<li>${companyName}</li>");
                $("#companyList").html($.tmpl(company,list));
            })
        })

        $("#companyForm").submit(function(){
            var $companyForm = $("#companyForm");
            var $Url = $companyForm.attr("action");
            $.post($Url,$companyForm.serializeArray(),function(data){
                var id = data.id;
                var name = data.name;
                $("#companyId").val(id);
                $("#name").html("<div id='name'>"+name+"</div>")
            });
            return false;
        })

        $("#recruitForm").submit(function(){
            var $careerForm = $("#recruitForm");
            var $Url = $careerForm.attr("action");
            $.post($Url,$careerForm.serializeArray(),function(data){
                window.location.reload();
            });
            return false;
        })

        $("#ignore").click(function(){
            var ignore = 1;
            var sourceId = $("#sourceId").val();
            var db = $("#db").val();
            $.post("campusrecruit.json",{ignore:ignore,sourceId:sourceId,db:db},function(){
                window.location.reload();
            })
        })

//        $("#org_title_click").click(function(){
//            var org_title = $("#org_title").val();
//            $("#position").val(org_title);
//        })
//
//        $("#org_province_click").click(function(){
//            var org_province = $("#org_province").val();
//            $("#province").val(org_province);
//        })

        $("#org_batch_click").click(function(){
            var org_pubDate = $("#org_pubDate").val();
            var org_state = $("#org_state").val();
            var org_statetime = $("#org_statetime").val();
            $("#createdTime").val(org_pubDate);

            var org_title = $("#org_title").val();
            $("#position").val(org_title);

            var org_province = $("#org_province").val();
            $("#province").val(org_province);
            $("#place").val(org_province);

            var org_state = $("#org_state").val();
            $("#state").val(org_state);

            var org_statetime = $("#org_statetime").val();
            $("#statetime").val(org_statetime);

            var org_industry = $("#org_industry").val();
            $("#industry").val(org_industry);

            var org_famous = $("#org_famous").val();
            $("#famous").val(org_famous);

            var org_url = $("#org_url").val();
            $("#url").val(org_url);

            var org_img = $("#org_img").val();
            $("#img").val(org_img);
        })

        $("#search").click(function(){
            var key = $("#org_title").val();
//            $.post("mobile/search.json",{key:key},function(data){
            $.post("search/find.json",{key:key,type:1},function(data){
                data = data.companies;
                var json = eval(data);
                $("#companyList").html("");
                for(var i=0; i<json.length; i++){
                    $("#companyList").append('<div>'+json[i].name+'</div>');
                }
//                var companyTmpl=$.template("companyTmpl", "<div>${name}</div>");
//                $("#companyList").html($.tmpl("companyTmpl", data, {substring:function (str, s, e) {
//                    return str.substring(s, e)
//                }}));
            });
        })

        $("#search_index").click(function(){
            $.post("search/rebuild.json",function(){
                $("#rebuild_ok").show();
            });
        })

        $("#close_alert").click(function(){
            $("#rebuild_ok").hide();

        })

    })
})(jQuery,window);
