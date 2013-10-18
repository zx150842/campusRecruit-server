<%--
  Created by IntelliJ IDEA.
  User: zhang
  Date: 13-9-12
  Time: 上午9:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <%@include file="../snippet/meta.jsp"%>
</head>
<body>
    <form action="mobile/user/portrait/update.json" method="POST" enctype="multipart/form-data">
        username: <input type="text" name="userID" value="080b900d-b6e7-4e68-a6cf-315ce0a9f3c2"/><br/>
        yourfile: <input type="file" name="portrait"/><br/>
        <input type="submit" value="上传"/>
    </form>
</body>
</html>