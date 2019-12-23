<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->

        <title>HgShop登录</title>

        <!-- Bootstrap core CSS -->
        <link href="/resources/css/bootstrap.css" rel="stylesheet">

        <link rel="stylesheet" href="/resources/css/signin.css">
        <script type="text/javascript" src="/resources/jquery/jquery-3.4.1.js"></script>
        <script type="text/javascript" src="/resources/bootstrap/js/bootstrap.min.js"></script>

    </head>

    <body>

    <div class="container">

        <form class="form-signin" id="loginForm" action="javascript:void(0)" method="post">
            <h2 class="form-signin-heading" style="text-align: center;">HgShop</h2>

            <div class="form-group">
                <label for="username">用户名:</label>
                <input type="text" id="username" class="form-control" placeholder="请输入用户名" required autofocus>
            </div>

            <div class="form-group">
                <label for="password">密码:</label>
                <input type="password" id="password" class="form-control" placeholder="Password" required>
            </div>

            <div class="checkbox">
                <label>
                    <input type="checkbox" value="remember-me"> 记住我
                </label>
            </div>
            <button class="btn btn-lg btn-primary btn-block" type="submit" onclick="login()">登录</button>
        </form>

    </div>

</body>
<script type="text/javascript">

    function login() {
        $.post("login", $("#loginForm").serialize(), function (res) {
            alert(res);
        }, "json");

    }


</script>
</html>