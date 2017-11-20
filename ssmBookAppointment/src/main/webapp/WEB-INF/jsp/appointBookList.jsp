<%@page contentType="text/htm; charset=UTF-8" language="java" %>
<%@include file="common/tag.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>预约图书列表</title>
    <%@include file="common/head.jsp" %>
</head>
<body>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h2>您已预约图书列表</h2>
        </div>
        <div class="panel-body">
            <table class="table table-hover">
                <thead>
                     <tr>
                        <th>预定学号</th>
                        <th>预约时间</th>
                        <th>图书ID</th>
                        <th>图书名称</th>
                        <th>图书简介</th>
                        <th>预约数量</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${appointList}" var="sk">
                        <tr>
                            <td>${sk.studnentId}</td>
                            <td>${sk.appointTime}</td>
                            <td>${sk.bookId}</td>
                            <td>${sk.book.getName()}</td>
                            <td>${sk.boook.getIntrod()}</td>
                            <td>1</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

        </div>
    </div>
</div>
<script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>

<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>
</body>
</html>
