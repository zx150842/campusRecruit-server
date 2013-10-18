<%--
  Created by IntelliJ IDEA.
  User: zhang
  Date: 13-8-29
  Time: 上午10:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
    <%@include file="../snippet/meta.jsp"%>
</head>
<body>

    <div class="tabbable">
        <ul class="nav nav-tabs">
            <li class="active"><a href="/careertalk.html" data-toggle="tab">宣讲会</a></li>
            <li><a href="/campusrecruit.html" data-toggle="tab">校园招聘</a></li>
            <li class="btn btn-primary" style="float: right;" id="search_index">初始化搜索索引</li>
        </ul>
        <div id="rebuild_ok" style="display: none;" class="alert">
            <button id="close_alert" type="button" class="close" data-dismiss="alert">&times;</button>
            <strong>注意：</strong> 搜索索引已经重建完毕！
        </div>
        <div class="tab-content">
            <div class="tab-pane active" id="tab1">
                <div class="row-fluid">
                    <div class="span12">
                        <div class="row-fluid">
                            <div class="span6">
                                <form class="form-horizontal" id="sourceForm">
                                    <div class="control-group">
                                        <label class="control-label" for="org_name">公司名称</label>
                                        <div class="controls">
                                            <input type="text" id="org_name" name="org_name" value="${source.title}">
                                            <div id="search" class="btn">Search</div>
                                            <div id="companyList"></div>
                                        </div>
                                    </div>
                                    <%--<div class="control-group">--%>
                                        <%--<label class="control-label" for="org_name">检查公司是否存在</label>--%>
                                        <%--<div class="controls form-search">--%>
                                                <%--<input type="text" value="${source.title}" id="search_key" class="input-medium search-query" >--%>
                                                <%--<div id="search" class="btn">Search</div>--%>
                                                <%--<div id="companyList"></div>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>

                                    <div class="control-group">
                                        <label class="control-label" for="org_school">学校</label>
                                        <div class="controls">
                                            <input type="text" id="org_school" name="org_school" value="${source.school}">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label" for="org_date">日期</label>
                                        <div class="controls">
                                            <input type="text" id="org_date" name="org_date" value="${source.date}">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label" for="org_time">时间</label>
                                        <div class="controls">
                                            <input type="text" id="org_time" name="org_time" value="${source.time}">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label" for="org_place">地点</label>
                                        <div class="controls">
                                            <input type="text" id="org_place" name="org_place" value="${source.local}">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label" for="org_url">原站网址</label>
                                        <div class="controls">
                                            <input type="text" id="org_url" name="org_url" value="${source.url}">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label" for="org_place"></label>
                                        <div class="controls">
                                            <%--<div id="org_batch_click" class="btn">一键复制</div>--%>
                                            <div id="ignore" class="btn btn-danger">跳过</div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="span6">
                                <form class="form-horizontal" id="companyForm" action="mobile/inputCompany.json" target="hidFrame" method="post">
                                    <div class="control-group">
                                        <label class="control-label" for="companyName">公司名称</label>
                                        <div class="controls">
                                            <input type="text" id="companyName" name="companyName" placeholder="公司名称">
                                        </div>
                                    </div>

                                    <div class="control-group">
                                        <label class="control-label" for="type">公司性质</label>
                                        <div class="controls">
                                            <select id="type" name="type">
                                                <option value="1">国企</option>
                                                <option value="2" selected="selected">私企</option>
                                                <option value="3">外企</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">公司所属行业</label>
                                        <div class="controls">
                                            <label class="checkbox">
                                                <input type="checkbox" value="1" id="computer" name="computer">计算机
                                            </label>
                                            <label class="checkbox">
                                                <input type="checkbox" value="2" id="electron" name="electron">电子
                                            </label>
                                            <label class="checkbox">
                                                <input type="checkbox" value="3" id="communication" name="communication">通信
                                            </label>
                                            <label class="checkbox">
                                                <input type="checkbox" value="4" id="financial" name="financial">金融
                                            </label>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label" for="famous">是否为名企</label>
                                        <div class="controls">
                                            <input type="text" id="famous" name="famous" placeholder="是否为名企">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label" for="info">公司介绍</label>
                                        <div class="controls">
                                            <textarea id="info" name="info" rows="5"></textarea>
                                        </div>
                                    </div>

                                    <div class="control-group" style="display: none;">
                                        <label class="control-label" for="pageType"></label>
                                        <div class="controls">
                                            <input type="text" id="pageType" name="pageType" value="1">
                                        </div>
                                    </div>

                                    <div class="control-group">
                                        <div class="controls">
                                            <button type="submit" class="btn">录入</button>
                                            <div id="name"></div>
                                        </div>
                                    </div>
                                </form>

                                <form id="careerForm" action="mobile/careerTalk/setCareerTalk.json" target="hidFrame" method="post" class="form-horizontal">
                                    <%--<div class="control-group">--%>
                                        <%--<label class="control-label" for="schoolName">学校名称</label>--%>
                                        <%--<div class="controls">--%>
                                            <%--<select id="schoolName" name="schoolName">--%>
                                                <%--<option value="1">北京邮电大学</option>--%>
                                                <%--<option value="2">清华大学</option>--%>
                                                <%--<option value="3">北京大学</option>--%>
                                                <%--<option value="4">中国人民大学</option>--%>
                                                <%--<option value="5">北京理工大学</option>--%>
                                                <%--<option value="6">北京航空航天大学</option>--%>
                                                <%--<option value="7">北京科技大学</option>--%>
                                                <%--<option value="8">北京交通大学</option>--%>
                                                <%--<option value="9">北京师范大学</option>--%>
                                                <%--<option value="10">中央财经大学</option>--%>
                                                <%--<option value="11">中国矿业大学</option>--%>
                                                <%--<option value="12">华北电力大学</option>--%>
                                                <%--<option value="13">中国农业大学</option>--%>
                                                <%--<option value="14">中国石油大学</option>--%>
                                                <%--<option value="15">中国民族大学</option>--%>
                                                <%--<option value="16">中国地质大学</option>--%>
                                            <%--</select>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                    <div class="control-group">
                                        <label class="control-label" for="schoolName">学校名称</label>
                                        <div class="controls">
                                            <input type="text" id="schoolName" name="schoolName" placeholder="学校名称">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label" for="place">宣讲会地点</label>
                                        <div class="controls">
                                            <input type="text" id="place" name="place" placeholder="宣讲会地点">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label" for="date">日期</label>
                                        <div class="controls">
                                            <input type="text" name="date" id="date" placeholder="日期">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label" for="time">时间</label>
                                        <div class="controls">
                                            <input type="text" name="time" id="time" placeholder="时间">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label" for="url">原站网址</label>
                                        <div class="controls">
                                            <input type="text" id="url" name="url" placeholder="原站网址">
                                        </div>
                                    </div>
                                    <div class="control-group" style="display: none;">
                                        <label class="control-label" for="companyId"></label>
                                        <div class="controls">
                                            <input type="text" id="companyId" name="companyId">
                                        </div>
                                    </div>
                                    <div class="control-group" style ="display: none;">
                                        <label class="control-label" for="sourceId"></label>
                                        <div class="controls">
                                            <input type="text" id="sourceId" name="sourceId" value="${source.id}">
                                        </div>
                                    </div>
                                    <div class="control-group" style="display: none;">
                                        <label class="control-label" for="db"></label>
                                        <div class="controls">
                                            <input type="text" id="db" name="db" value="${source.db}">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <div class="controls">
                                            <button type="submit" class="btn">提交</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <iframe name="hidFrame" style="display:none"></iframe>

</body>
<script type="text/javascript">
    $L.wait().script("js/career.js");
</script>
</html>