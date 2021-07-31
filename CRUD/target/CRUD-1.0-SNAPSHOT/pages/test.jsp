
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>success</title>
    <%
        pageContext.setAttribute("APP_PATH",request.getContextPath());
    %>
    <!-- 不以/开始的相对路径，找资源，以当前资源的路径为准，经常出问题
        以/开始的相对路径，找资源，以服务器的路径为准(http://localhost:8080/crud)；需要加项目名
        http://localhost:8080/crud
     -->
    <!--引入Jquery-->
    <script type="text/javascript" src="${APP_PATH}/static/js/jquery-3.5.1.min.js"></script>
    <!--引入BootStrap-->
    <link href="${APP_PATH}/static/bootstrap-3.4.1-dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="${APP_PATH}/static/bootstrap-3.4.1-dist/js/bootstrap.min.js"></script>
</head>
<body>
<form id="form">
    <input type="text" name="name" id="input">
</form>
<button id="btn">提交</button>
</body>
<script type="text/javascript">
    <%--$("#btn").click(function (){--%>
    <%--    $.ajax({--%>
    <%--        url:"${APP_PATH}/test",--%>
    <%--        type: "GET",--%>
    <%--        data: $("#form").serialize()--%>
    <%--        // data: $("#input").val()--%>
    <%--    });--%>
    <%--});--%>
    $("#input").blur(function (){
        alert("111");
    });
</script>
</html>
