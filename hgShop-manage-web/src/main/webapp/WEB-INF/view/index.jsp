<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>HgShop后台管理系统</title>

    <!-- Bootstrap core CSS -->
    <link href="/resources/css/bootstrap.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="/resources/css/dashboard.css" rel="stylesheet">
    <link rel="stylesheet" href="/resources/css/font-awesome.min.css" />
    <script type="text/javascript" src="/resources/jquery/jquery-3.4.1.js"></script>
    <script type="text/javascript" src="/resources/bootstrap/js/bootstrap.min.js"></script>

    <style>
        img{
            width:30px;
            height: 30px;
            border-radius: 100%;

        }
        .sidebar-open-button{
            font-size: 20px;
            padding-top: 10px;
            color: #76747A;
            text-align: center;
            float: left;
        }
        .nav-sidebar li a {
            color: black;
        }
        .nav-sidebar > .active > a,
        .nav-sidebar > .active > a:hover,
        .nav-sidebar > .active > a:focus {
            color: #fff;
            background-color: darkgray;
        }
    </style>

    <script>
        $(function(){
            $('.main').css('height',window.screen.height);
            var marginLeft = $('.main').css('margin-left');
            $('.sidebar-open-button').on('click',function(){
                //$('.sidebar').toggle();
                if($('.sidebar').hasClass('hidden')){
                    $('.sidebar').removeClass('hidden');
                    $('.main').css('margin-left', marginLeft);
                }else{
                    $('.sidebar').addClass('hidden');
                    $('.main').css('margin-left', 0);
                }
            });

            $('.nav-sidebar li').click(function(){
                $('.nav-sidebar li').removeClass('active');
                $(this).addClass('active');
            });
        })

    </script>

</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header col-md-2">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#" style="color: white;">HgShop后台管理系统</a>
        </div>
        <a href="#" class="sidebar-open-button"><i class="fa fa-bars"></i></a>

        <div class="dropdown navbar-right" style="margin-top:10px">
            <a id="dLabel" data-toggle="dropdown" style="color: white;">
                <img src="/resources/img/profileimg.png"/>
                <b style="color: white;">Admin</b>
                <span class="caret"></span>
            </a>
            <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
                <li><a href="#">个人操作</a></li>
                <li role="separator" class="divider"></li>
                <li><a href="#">注销</a></li>
            </ul>
        </div>

    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <ul class="nav nav-sidebar">
                <li class="active"><a href="welcome.html" target="mainFrame">首页</a></li>
                <li><a href="brand/brandList" target="mainFrame">品牌管理</a></li>
                <li><a href="category/categoryList" target="mainFrame">分类管理</a></li>
                <li><a href="spec/specList" target="mainFrame">规格参数管理</a></li>
                <li><a href="spu/showCategoryTree" target="mainFrame">SPU管理</a></li>
                <li><a href="sku/skuList" target="mainFrame">SKU管理</a></li>
            </ul>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <iframe name="mainFrame" src="welcome.html" width="100%" height="100%" frameborder="0"></iframe>
        </div>
    </div>
</div>

</body>
</html>