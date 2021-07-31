<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>员工列表</title>

    <%
        pageContext.setAttribute("APP_PATH", request.getContextPath());
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
<!-- BootStrip搭建页面 -->
<div class="container">
    <!-- 标题 -->
    <%--    <div class="row">--%>
    <%--        <div class="col-md-12">--%>
    <%--            <h1>SSM-CRUD</h1>--%>
    <%--        </div>--%>
    <%--    </div>--%>
    <header>
        <div class="row" style="background-color:#9acfea; height:60px;">
            <div class="col-md-4">
                <h1>SSM-CRUD</h1>
            </div>
<%--            <div class="col-md-6 col-md-offset-6">--%>
<%--                <a class="text-muted" href="#">Home</a>--%>
<%--                <a class="text-muted" href="#">About</a>--%>
<%--                <a class="text-muted" href="#">Contact</a>--%>
<%--            </div>--%>
        </div>
        <!-- 按钮 -->
<%--            <div class="row">--%>
<%--                <div class="col-md-4 col-md-offset-8">--%>
<%--                    <button class="btn btn-primary">新增</button>--%>
<%--                    <button class="btn btn-danger">删除</button>--%>
<%--                </div>--%>
<%--            </div>--%>
        <div class="row" style="background-color:#adadad; height:60px;">
            <div class="col-md-3 col-md-offset-7">
                <form class="form-inline">
                    <input type="text" class="form-control" name="search">
                    <button type="submit" class="btn btn-default">查找</button>
                </form>
            </div>
            <div class="col-md-1">
                <button class="btn btn-primary">新增</button>
            </div>
        </div>
    </header>

    <div class="row">
        <div class="col-md-12" style="background-color:#9acfea;">
            <table class="table table-hover" >
                <tr>
                    <th>#</th>
                    <th>empName</th>
                    <th>gender</th>
                    <th>email</th>
                    <th>deptName</th>
                    <th>操作</th>
                </tr>
                <c:forEach items="${pageInfo.list}" var="emp">
                    <tr>
                        <th>${emp.empId}</th>
                        <th>${emp.empName}</th>
                        <th>${emp.gender=="M"?"男":"女"}</th>
                        <th>${emp.email}</th>
                        <th>${emp.department.deptName}</th>
                        <th>
                            <button class="btn btn-primary btn-sm">
                                <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                编辑
                            </button>
                            <button class="btn btn-danger btn-sm">
                                <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                                删除
                            </button>
                        </th>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
    <!-- 显示分页信息 -->
    <div class="row">
        <!-- 分页文字信息 -->
        <div class="col-md-6">
            当前第${pageInfo.pageNum}页，总${pageInfo.pages}页，总${pageInfo.total}条记录
        </div>
        <!-- 分页条信息 -->
        <div class="col-md-6">
            <nav aria-label="Page navigation">
                <ul class="pagination">

                    <li><a href="${APP_PATH}/emps?pn=1">首页</a></li>

                    <c:if test="${pageInfo.hasPreviousPage}">
                        <li>
                            <a href="${APP_PATH}/emps?pn=${pageInfo.prePage}" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                    </c:if>


                    <c:forEach items="${pageInfo.navigatepageNums}" var="page_Num">
                        <c:if test="${page_Num == pageInfo.pageNum}">
                            <li class="active"><a href="#">${page_Num}</a></li>
                        </c:if>
                        <c:if test="${page_Num != pageInfo.pageNum}">
                            <li><a href="${APP_PATH}/emps?pn=${page_Num}">${page_Num}</a></li>
                        </c:if>
                    </c:forEach>

                    <c:if test="${pageInfo.hasNextPage}">
                        <li>
                            <a href="${APP_PATH}/emps?pn=${pageInfo.nextPage}" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </c:if>
                    <li><a href="${APP_PATH}/emps?pn=${pageInfo.pages}">末页</a></li>
                </ul>
            </nav>
        </div>
    </div>
</div>
<footer class="foot-wrap container-fluid">
    <div class="container">
        <div class="row">
            <div class="col-4">
                <div class="widget">
                    <h4 class="title"><i class="fa fa-home"></i> About</h4>
                    <ul>
                        <li><a href="#">ustc</a></li>
                    </ul>
                </div>
            </div>
            <div class="col-4">
                <div class="widget">
                    <h4 class="title"><i class="fa fa-desktop"></i> Friendly links</h4>
                    <ul>
                        <li><a href="#">ustc</a></li>
                        <li><a href="#">ustc</a></li>
                    </ul>
                </div>
            </div>
            <div class="col-4">
                <div class="widget">
                    <h4 class="title"><i class="fa fa-envelope"></i> Contact me</h4>
                    <ul>
                        <li><a href="#">mobile:xxxxxx</a></li>
                        <li><a href="#">email:xxxxx@</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</footer>
<div class="copyright">
    <div class="container">
        <div class="row">
            <div class="col-12">
                <span>Copyright@</span>
            </div>
        </div>
    </div>
</div>
</body>
</html>
