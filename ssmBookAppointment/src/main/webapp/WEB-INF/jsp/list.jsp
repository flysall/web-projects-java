<%@page contentType="text/html; charset=UTF-8" language="java" %>
<%@include file="common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>图书列表</title>
    <%@include file="common/head.jsp" %>
</head>
<body>
    <div class="container">
        <div class="panel-heading text-center">
            <h2>图书列表</2>
        </div>
        <form name="firstForm" action="/ssm-BookAppointment/books/search" method="post">
            <div class="panel-heading">
                <table class="table table-bookName">
                    <thead>
                        <tr>
                            <th width="90" align="lift">图书名称:<th>
                            <th width="150" align="lift">
                                <input type="text" name="name" class="allInput" value="${name}" placeholder="输入检索书名"/>
                            </th>
                            <th>
                                <input type="submit" id="tabSub" value="检索"/>
                            </th>       
                        </tr>
                    </thead>
                </table>
            </div>
        </form>

        <div class="panel-body">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>图书ID</th>
                        <th>图书名称</th>
                        <th>馆藏数量</th>
                        <th>详细</th>

                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${list}" var="sk">
                        <tr>
                            <td>${sk.bookId}</td>
                            <td>${sk.name}</td>
                            <td>${sk.number}</td>
                            <td><a class="btn btn-info" href="/ssm-BookAppointment/books/${sk.bookId}/detail" target="_blank">详细</a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- jQuery文件，须在bootstrap.min.js前引入 -->
<script src="http://apps.bdimg.com/libs/jQuery/2.0.2/jquery.min.js"></script>
<script src="http://apps.bdimg.com/libs/bootstrap/3.0.3/js/bootstrap.min.js"></script>
</body>
</html>
