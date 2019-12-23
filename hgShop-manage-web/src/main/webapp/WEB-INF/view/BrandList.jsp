<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->

    <title>品牌管理</title>

    <!-- Bootstrap core CSS -->
    <link href="/resources/css/bootstrap.css" rel="stylesheet">

    <link rel="stylesheet" href="/resources/css/signin.css">
    <script type="text/javascript" src="/resources/jquery/jquery-3.4.1.js"></script>
    <script type="text/javascript" src="/resources/bootstrap/js/bootstrap.min.js"></script>

</head>
<body>
<div class="container-fluid">
    <div class="row">
        <form>
            <div class="form-group">
                <label for="name">品牌名称</label>
                <input type="text" class="form-control" id="name" name="name" placeholder="品牌名称" value="${brand.name}">
            </div>
            <div class="form-group">
                <label for="firstChar">品牌首字母</label>
                <input type="text" class="form-control" id="firstChar" name="firstChar" placeholder="品牌首字母" value="${brand.firstChar}">
            </div>
            <button class="btn btn-success" type="submit">搜索</button>
        </form>
    </div>

    <div class="row" style="float: right;">
        <button class="btn btn-danger" onclick="deleteBrand()">批量删除</button>
        <button type="button" class="btn btn-primary btn-md" data-toggle="modal" data-target="#myModal">
            新增品牌
        </button>
    </div>

    <div class="row">
        <table class="table table-striped">
            <thead>
            <tr>
                <th><input type="checkbox" id="cbk">全选</th>
                <th>编号</th>
                <th>品牌名称</th>
                <th>首字母</th>
                <th>状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${pageInfo.list}" var="brand" varStatus="index">
                <tr>
                    <td><input type="checkbox" class="ck" value="${brand.id}" /></td>
                    <td>${index.count}</td>
                    <td>${brand.name}</td>
                    <td>${brand.firstChar}</td>
                    <td>${brand.deletedFlag==0 ? '正常' : '删除'}</td>
                    <td>
                        <button class="btn btn-danger" onclick="deleteBrand(${brand.id})">删除</button>
                        <button type="button" class="btn btn-primary btn-md" data-toggle="modal" data-target="#myModal" onclick="getBrandById(${brand.id},1);">
                            修改
                        </button>
                        <button type="button" class="btn btn-info btn-md" data-toggle="modal" data-target="#myModal" onclick="getBrandById(${brand.id},2);">
                            查看
                        </button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <nav>
            <ul class="pagination">
                <c:if test="${pageInfo.hasPreviousPage}">
                    <li>
                        <a href="brandList?pageNum=${pageInfo.prePage}&name=${brand.name}&firstChar=${brand.firstChar}">
                            <span aria-hidden="true">上一页</span>
                        </a>
                    </li>
                </c:if>
                <c:forEach items="${pageInfo.navigatepageNums}" var="pageNum">
                    <c:if test="${pageInfo.pageNum==pageNum}">
                        <li class="active"><a href="brandList?pageNum=${pageNum}&name=${brand.name}&firstChar=${brand.firstChar}">${pageNum}</a></li>
                    </c:if>
                    <c:if test="${pageInfo.pageNum!=pageNum}">
                        <li><a href="brandList?pageNum=${pageNum}&name=${brand.name}&firstChar=${brand.firstChar}">${pageNum}</a></li>
                    </c:if>
                </c:forEach>
                <c:if test="${pageInfo.hasNextPage}">
                    <li>
                        <a href="brandList?pageNum=${pageInfo.nextPage}&name=${brand.name}&firstChar=${brand.firstChar}" aria-label="Next">
                            <span aria-hidden="true">下一页</span>
                        </a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
</div>


<!-- 模态框 -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">新增品牌</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="modalForm">
                    <input type="hidden" id="addId" name="id" />
                    <div class="form-group">
                        <label for="addName" class="col-sm-3 control-label">品牌名称</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="addName" name="name" placeholder="品牌名称">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="addFirstChar" class="col-sm-3 control-label">品牌首字母</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="addFirstChar" name="firstChar" placeholder="品牌首字母">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="addBrand()" id="addButton">添加</button>
            </div>
        </div>
    </div>
</div>

</body>

<script>
    function addBrand(){
        $.post('addBrand',$('#modalForm').serialize(),function(data){
            if(data){
                $("#myModal").modal('hide');
                window.location.reload();
            }else{
                alert('添加品牌失败');
            }
        },'json');
    }
    function getBrandById(id,flag){
        $.post('getBrandById',{id:id},function(data){
            if(flag == 1){
                $('#addButton').show();
                $('#myModalLabel').text('编辑品牌');
                $('#modalForm input').prop('readonly',false);
            }else{
                $('#myModalLabel').text('查看品牌');
                $('#addButton').hide();
                $('#modalForm input').prop('readonly',true);
            }
            $('#addId').val(data.id);
            $('#addName').val(data.name);
            $('#addFirstChar').val(data.firstChar);
        },'json');
    }
    $(function(){
        $('#cbk').on('click',function(){
            $('.ck').prop('checked', this.checked);
        });
    })
    // 删除数据(逻辑)
    function deleteBrand(ids){
        if(ids==undefined){
            //批量删除 [user1,user2,user3]  ----> [1,2,3]
            ids = $('.ck:checked').map(function(){
                return this.value;
            }).get().join(',');
        }
        if(ids!=''){
            if(confirm('确定要删除选中的数据吗?')){
                $.post('delBrand',{id:ids},function(data){
                    if(data){
                        window.location.reload();
                    }else{
                        alert('删除品牌失败');
                    }
                },'json');
            }
        }else{
            alert('请选中要删除的数据');
        }
    }
</script>

</html>
