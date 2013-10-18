(function ($, exports) {
    $(function () {

        var org_school = $("#org_school").val();
        $("#schoolName").val(org_school);

        var org_date = $("#org_date").val();
        $("#date").val(org_date);

        var org_time = $("#org_time").val();
        $("#time").val(org_time);

        var org_place = $("#org_place").val();
        $("#place").val(org_place);

        var org_url = $("#org_url").val();
        $("#url").val(org_url);

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

        $("#careerForm").submit(function(){
            var $careerForm = $("#careerForm");
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
            $.post("careertalk.json",{ignore:ignore,sourceId:sourceId,db:db},function(){
                window.location.reload();
            })
        })

        $("#org_batch_click").click(function(){
            var org_name = $("#org_name").val();
            $("#companyName").val(org_name);

            var org_school = $("#org_school").val();
            $("#schoolName").val(org_school);

            var org_date = $("#org_date").val();
            $("#date").val(org_date);

            var org_time = $("#org_time").val();
            $("#time").val(org_time);

            var org_place = $("#org_place").val();
            $("#place").val(org_place);

            var org_url = $("#org_url").val();
            $("#url").val(org_url);
        })

        var key = $("#org_name").val();
        $.post("search/find.json",{key:key},function(data){
            data = data.companies;
            var json = eval(data);
            $("#companyList").html("");
            for(var i=0; i<json.length; i++){
                $("#companyList").append('<li>'+json[i].name+'<span class="btn" id="'+json[i].id+'">复制</span></li>');
            }
        });

        $("#companyList li span").live("click",function(){
            var id = $(this).attr("id");
            $.post("mobile/company.json",{companyID:id},function(data){
                data = data.campusRecruit;
                $("#companyName").val(data.companyName);
            })
        })

        $("#search").click(function(){
            var key = $("#org_name").val();
//            $.post("mobile/search.json",{key:key},function(data){
            $.post("search/find.json",{key:key},function(data){
                data = data.companies;
                var json = eval(data);
                $("#companyList").html("");
                for(var i=0; i<json.length; i++){
                    $("#companyList").append('<div>'+json[i].name+'</div>');
                }
//                var companyTmpl=$.template("companyTmpl", "<div>${name}</div>");
//                $("#companyList").html($.tmpl("companyTmpl", data, {substring:function (str, s, e) {
//                    return str.substring(s, e)
//                }}));
            });
        })

        $("#companyList li span").live("click",function(){
            var id = $(this).attr("id");
            $.post("mobile/company.json",{companyID:id},function(data){
                data = data.campusRecruit;
                $("#companyName").val(data.companyName);
                $("#type").val(data.type);

//                $("#province").val(data.place);
//                if(org_province == ""){
//                    $("#place").val(data.place);
//                }
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

                $("#famous").val(data.famous);
                $("#info").val(data.introduction);
                $("#state").val(data.company_state);
                $("#statetime").val(data.statetime);
            })
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
