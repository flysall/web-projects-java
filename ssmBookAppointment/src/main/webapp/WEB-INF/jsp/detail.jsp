<%@page contentType="text/html; charset=UTF-8" language="java" %>
<%@include file="common/tag.jsp" %>
<html>
<head>
    <title>预约详情页</title>
    <%@include file="common/head.jsp" %>
</head>
<body>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h2>图书详情</h2>
        </div>
        <div class="panel-body">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>图书ID</th>
                        <th>图书名称</th>
                        <th>图书简介</th>
                        <th>剩余数量</th>
                        <th>预约数量</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>${book.bookId}</td>
                        <td>${book.name}</td>
                        <td>${book.intro}</td>
                        <td>${book.number}</td>
                        <td>1</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="panel-body text-center">
            <h2 class="text-danger">
                <span class="glyphicon" id="appoint-box"></span>
                <span class="glyphicon"><a class="btn btn-primary btn-lg" href="/books/appoint?studentId=${cookie['student'].value}" target="_blank">查看我预约的书籍</a></sapn>
            </h2>
        </div>
    </div>
</div>

<div id="varifyModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modl-content">
            <div class="modal-header">
                <h3 class="modal-title text-center">
                    <span class="glyphicon glyphicon-studentId"></span>请输入学号和密码:
                </h3>
            </div>

            <div class="modal-body">
                <div class="row">
                    <div class="col-xs8 col-xs-offset-2">
                        <input type="text" name="studentId" id="studentIdKey"
                            placeholder="填写学号" class="form-control">
                    </div>
                    <div class="col-xs-8 col-xs-offset-2">
                        <input type="password" id="passwordKey"
                            placeholder="输入密码" class="form-control">
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <span id="studentMessage" class="glyphicon"> </span>
                <button type="button" id="StudentBtn" class="btn btn-success">
                    <span class="glyphicon glypicon-student"></span>
                    Submit
                </buton>
            </div>
        </div>
    </div>
</div>
</body>

<script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>
<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<%--使用CDN 获取公共js http://www.bootcdn.cn/--%>
<%--jQuery Cookie操作插件--%>
<script src="http://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<%--jQuery countDown倒计时插件--%>
<script src="http://cdn.bootcss.com/jquery.countdown/2.1.0/jquery.countdown.min.js"></script>

<script src="/resources/script/bookappointment.js" type="text/javascript"></script>
<script type="text/javascript">
    ${function(){
        //EL表达式传入参数
        bookappointment.detail.init({
            bookId:${book.bookId}
        });
    })
</script>
</html>
