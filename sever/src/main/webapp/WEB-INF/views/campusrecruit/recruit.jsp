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
        <li><a href="/careertalk.html" data-toggle="tab">宣讲会</a></li>
        <li class="active"><a href="/campusrecruit.html" data-toggle="tab">校园招聘</a></li>
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
                            <form id="sourceForm" class="form-horizontal">
                                <div class="control-group">
                                    <label class="control-label">标题</label>
                                    <div class="controls">
                                        <input type="text" id="org_title" value="${source.title}">
                                        <div id="search" class="btn">Search</div>
                                        <div id="ignore" style="float: right;" class="btn btn-danger">跳过</div>
                                        <ul id="companyList">
                                            <%--<li id="companyList"></li>--%>
                                        </ul>

                                        <%--<div id="ignore" class="btn btn-danger">跳过</div>--%>
                                    </div>
                                </div>
                                <%--<div class="control-group">--%>
                                    <%--<label class="control-label">检查公司是否存在</label>--%>
                                    <%--<div class="controls form-search">--%>
                                        <%--<input type="text" value="${source.title}" id="search_key" class="input-medium search-query">--%>
                                        <%--<div id="search" class="btn">Search</div>--%>
                                        <%--<div id="companyList"></div>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                                <div class="control-group">
                                    <label class="control-label">公司性质</label>
                                    <div class="controls">
                                        <input type="text" value="${source.type}">
                                    </div>
                                </div>

                                <c:if test="${source.db==1}">
                                    <div class="control-group">
                                </c:if>
                                <c:if test="${source.db==2}">
                                    <div class="control-group" style="display: none;">
                                </c:if>
                                <c:if test="${source.db==3}">
                                    <div class="control-group" style="display: none;">
                                </c:if>
                                        <label class="control-label">公司所在地</label>
                                        <div class="controls">
                                            <input type="text" id="org_province"  value="${source.city}">
                                                <%--<div id="org_province_click" class="btn">复制</div>--%>
                                        </div>
                                    </div>

                                <div class="control-group">
                                    <label class="control-label">公司所属行业</label>
                                    <div class="controls">
                                        <input type="text" id="org_industry" value="${source.industry}">
                                    </div>
                                </div>

                                <c:if test="${source.db==1}">
                                    <div class="control-group">
                                </c:if>
                                <c:if test="${source.db==2}">
                                    <div class="control-group" style="display: none;">
                                </c:if>
                                <c:if test="${source.db==3}">
                                    <div class="control-group" style="display: none;">
                                </c:if>
                                        <label class="control-label">是否为名企</label>
                                        <div class="controls">
                                            <input type="text" id="org_famous" value="${source.isfame}">
                                        </div>
                                    </div>

                                <c:if test="${source.db==1}">
                                    <div class="control-group">
                                </c:if>
                                <c:if test="${source.db==2}">
                                    <div class="control-group" style="display: none;">
                                </c:if>
                                <c:if test="${source.db==3}">
                                    <div class="control-group" style="display: none;">
                                </c:if>
                                        <label class="control-label">发布日期</label>
                                        <div class="controls">
                                            <input type="text" id="org_pubDate" value="${source.releaseDate}">

                                        </div>
                                    </div>

                                <c:if test="${source.db==1}">
                                    <div class="control-group">
                                </c:if>
                                <c:if test="${source.db==2}">
                                    <div class="control-group" style="display: none;">
                                </c:if>
                                <c:if test="${source.db==3}">
                                    <div class="control-group" style="display: none;">
                                </c:if>
                                        <label class="control-label" for="org_url">原站网址</label>
                                        <div class="controls">
                                            <input type="text" id="org_url" name="org_url" value="${source.url}">
                                        </div>
                                    </div>



                                <c:if test="${source.db==1}">
                                    <div class="control-group">
                                </c:if>
                                <c:if test="${source.db==2}">
                                    <div class="control-group" style="display: none;">
                                </c:if>
                                <c:if test="${source.db==3}">
                                    <div class="control-group" style="display: none;">
                                </c:if>
                                        <label class="control-label">表格</label>
                                        <div class="controls">
                                                ${source.form}
                                        </div>
                                    </div>

                                <c:if test="${source.db==1}">
                                    <div class="control-group">
                                </c:if>
                                <c:if test="${source.db==2}">
                                    <div class="control-group" style="display: none;">
                                </c:if>
                                <c:if test="${source.db==3}">
                                    <div class="control-group" style="display: none;">
                                </c:if>
                                        <label class="control-label">图片</label>
                                        <div class="controls">
                                            <input type="text" id="org_img" value="${source.img}">
                                        </div>
                                    </div>

                                <c:if test="${source.db==1}">
                                    <div class="control-group">
                                </c:if>
                                <c:if test="${source.db==2}">
                                    <div class="control-group" style="display: none;">
                                </c:if>
                                <c:if test="${source.db==3}">
                                    <div class="control-group" style="display: none;">
                                </c:if>
                                        <label class="control-label">招聘进度</label>
                                        <div class="controls">
                                            <input type="text" id="org_state" value="${source.state}">
                                        </div>
                                    </div>

                                <c:if test="${source.db==1}">
                                    <div class="control-group">
                                </c:if>
                                <c:if test="${source.db==2}">
                                    <div class="control-group" style="display: none;">
                                </c:if>
                                <c:if test="${source.db==3}">
                                    <div class="control-group" style="display: none;">
                                </c:if>
                                        <label class="control-label"></label>
                                        <div class="controls">
                                            <input type="text" id="org_statetime" value="${source.statetime}">
                                                <%--<div id="org_batch_click" class="btn">一键复制</div>--%>

                                        </div>
                                    </div>

                                <div class="control-group" style="display: none;">
                                    <label class="control-label" for="source_industry"></label>
                                    <div class="controls">
                                        <input type="text" id="source_industry" name="source_industry" value="${source.industry}">
                                    </div>
                                </div>
                                <div class="control-group" style="display: none;">
                                    <label class="control-label" for="source_province"></label>
                                    <div class="controls">
                                        <input type="text" id="source_province" name="source_province" value="${source.province}">
                                    </div>
                                </div>
                                <div class="control-group" style="display: none;">
                                    <label class="control-label" for="source_type"></label>
                                    <div class="controls">
                                        <input type="text" id="source_type" name="source_type" value="${source.type}">
                                    </div>
                                </div>
                                <div style="text-align: center" class="control-group">
                                    <%--<label class="control-label">招聘详细信息</label>--%>
                                    <%--<div class="controls">--%>
                                        <textarea id="org_content" rows="50" style="width: 80%">${source.jobdetails}</textarea>
                                    <%--</div>--%>
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
                                    <label class="control-label" for="province">公司所在地</label>
                                    <div class="controls">
                                        <input type="text" id="province" name="province" placeholder="公司所在地">
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
                                <c:if test="${source.db==1}">
                                    <div class="control-group">
                                        <label class="control-label" for="state">招聘进度</label>
                                        <div class="controls">
                                            <input type="text" id="state" name="state" placeholder="状态">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label" for="statetime"></label>
                                        <div class="controls">
                                            <input type="text" id="statetime" name="statetime" placeholder="时间">
                                        </div>
                                    </div>
                                </c:if>

                                <div class="control-group">
                                    <div class="controls">
                                        <button type="submit" class="btn">录入</button>
                                        <div id="name"></div>
                                    </div>
                                </div>
                            </form>

                            <form class="form-horizontal" id="recruitForm" action="mobile/recruit/inputRecruit.json" target="hidFrame" method="post">
                                <div class="control-group">
                                    <label class="control-label" for="position">职位名称</label>
                                    <div class="controls">
                                        <input type="text" id="position" name="position" placeholder="职位名称">
                                    </div>
                                </div>

                                <c:if test="${source.db==1}">
                                    <div class="control-group">
                                </c:if>
                                <c:if test="${source.db==2}">
                                    <div class="control-group" style="display: none;">
                                </c:if>
                                <c:if test="${source.db==3}">
                                    <div class="control-group" style="display: none;">
                                </c:if>
                                        <label class="control-label" for="description">职位描述</label>
                                        <div class="controls">
                                            <textarea id="description" name="description" rows="5"></textarea>
                                        </div>
                                    </div>

                                <c:if test="${source.db==1}">
                                    <div class="control-group">
                                </c:if>
                                <c:if test="${source.db==2}">
                                    <div class="control-group" style="display: none;">
                                </c:if>
                                <c:if test="${source.db==3}">
                                    <div class="control-group" style="display: none;">
                                </c:if>
                                        <label class="control-label" for="contact">联系方式</label>
                                        <div class="controls">
                                            <textarea id="contact" name="contact" rows="5"></textarea>
                                        </div>
                                    </div>


                                <div class="control-group">
                                    <label class="control-label" for="place">工作地点</label>
                                    <div class="controls">
                                        <input type="text" id="place" name="place" placeholder="公司所在地">
                                    </div>
                                </div>

                                <c:if test="${source.db==1}">
                                    <div class="control-group">
                                </c:if>
                                <c:if test="${source.db==2}">
                                    <div class="control-group" style="display: none;">
                                </c:if>
                                <c:if test="${source.db==3}">
                                    <div class="control-group" style="display: none;">
                                </c:if>
                                        <label class="control-label" for="createdTime">发布时间</label>
                                        <div class="controls">
                                            <input type="text" id="createdTime" name="createdTime" placeholder="发布时间">
                                        </div>
                                    </div>


                                <div class="control-group">
                                    <label class="control-label" for="url">原站网址</label>
                                    <div class="controls">
                                        <input type="text" id="url" name="url" placeholder="原站网址">
                                    </div>
                                </div>

                                <c:if test="${source.db==1}">
                                    <div class="control-group">
                                </c:if>
                                <c:if test="${source.db==2}">
                                    <div class="control-group" style="display: none;">
                                </c:if>
                                <c:if test="${source.db==3}">
                                    <div class="control-group" style="display: none;">
                                </c:if>
                                        <label class="control-label">表格</label>
                                        <div class="controls">
                                            <input type="hidden" id="form" name="form" value="${source.have_form}">
                                        </div>
                                    </div>

                                <c:if test="${source.db==1}">
                                    <div class="control-group">
                                </c:if>
                                <c:if test="${source.db==2}">
                                    <div class="control-group" style="display: none;">
                                </c:if>
                                <c:if test="${source.db==3}">
                                    <div class="control-group" style="display: none;">
                                </c:if>
                                        <label class="control-label">图片</label>
                                        <div class="controls">
                                            <input type="text" id="img" name="img" placeholder="图片">
                                        </div>
                                    </div>

                                <div class="control-group" style="display: none;">
                                    <label class="control-label" for="companyId"></label>
                                    <div class="controls">
                                        <input type="text" id="companyId" name="companyId">
                                    </div>
                                </div>
                                <div class="control-group" style="display: none;">
                                    <label class="control-label" for="sourceId"></label>
                                    <div class="controls">
                                        <input type="text" id="sourceId" name="sourceId" value="${source.pid}">
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
    $L.wait().script("js/recruit.js");
</script>
</html>