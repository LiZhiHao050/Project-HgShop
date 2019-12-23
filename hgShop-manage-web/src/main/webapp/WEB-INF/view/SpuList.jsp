<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <base href="${pageContext.request.contextPath }/">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>hgshop后台管理系统</title>

        <!-- Bootstrap core CSS -->
        <link href="/resources/css/bootstrap.css" rel="stylesheet"/>
        <link rel="stylesheet" href="/resources/css/bootstrap-treeview.css" />
        <script type="text/javascript" src="/resources/jquery/jquery-3.4.1.js"></script>
        <script type="text/javascript" src="/resources/bootstrap/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="/resources/bootstrap/js/bootstrap-treeview.js" ></script>

        <script>
            $(function(){
                $("#categoryName").click(function() {
                    $.post('category/getAllCategoriesTree', {}, function(data) {
                        var options = {
                            levels : 2,
                            data : data,
                            onNodeSelected : function(event, data) {
                                $("#categoryId").val(data.id);
                                $("#categoryName").val(data.text);
                                $("#tree").hide();//选中树节点后隐藏树
                            }
                        };
                        $('#tree').treeview(options);
                        $('#tree').show();
                    });
                });
                $("#categoryName2").click(function() {
                    $.post('category/getAllCategoriesTree', {}, function(data) {
                        var options = {
                            levels : 2,
                            data : data,
                            onNodeSelected : function(event, data) {
                                $("#categoryId2").val(data.id);
                                $("#categoryName2").val(data.text);
                                $("#tree2").hide();//选中树节点后隐藏树
                            }
                        };
                        $('#tree2').treeview(options);
                        $('#tree2').show();
                    });
                });
            });
            //【新增商品】按钮或【修改】按钮点击时调用
            function preAddSpu(flag){
                var obj;
                if(flag==1){
                    //【新增商品】按钮
                    $('#tree').hide();
                    $('#categoryId').val('');
                    $('#categoryName').val('');

                    obj=$("#brandId");
                    obj.html('<option value="">请选择品牌</option>');
                    $("#smallPic12").hide();
                }else{
                    //【修改】按钮
                    obj=$("#brandId2");
                    obj.html('<option value="">请选择品牌</option>');
                    $("#smallPic22").hide();
                }
                //获取品牌列表
                $.post("brand/getAllBrands", {},function(data) {
                    //遍历数据
                    for(var i in data){
                        obj.append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
                    }
                },"json");
            }
            function addSpu(flag){
                var obj,modal;
                if(flag==1){
                    obj=$('#modalForm')[0];
                    modal=$('#myModal');
                }else{
                    obj=$('#modalForm2')[0];
                    modal=$('#myModal2');
                }
                var formData = new FormData(obj);
                $.ajax({
                    type:'post',
                    data:formData,
                    url:'spu/spuAdd',
                    processData : false, // 告诉jQuery不要去处理发送的数据
                    contentType : false, // 告诉jQuery不要去设置Content-Type请求头
                    dataType:'json',
                    success:function(data){
                        if(data){
                            //关闭模态框
                            modal.modal('hide');
                            window.location.reload();
                        }else{
                            alert("商品操作失败");
                        }
                    }
                });
            }
            //在线预览图片
            function show(obj,flag){
                var obj1;
                if(flag==1){
                    //新增
                    obj1=$("#smallPic12");
                }else{
                    //修改
                    obj1=$("#smallPic22");
                }
                var rd = new FileReader();//创建文件读取对象
                var files = obj.files[0];//获取file组件中的文件
                rd.readAsDataURL(files);//文件读取装换为base64类型
                rd.onloadend = function(e) {
                    //加载完毕之后获取结果赋值给img
                    obj1.prop('src', this.result);
                    obj1.show();
                }
            }
            function getSpuById(id,flag){
                if(flag==2){
                    preAddSpu(flag);
                }
                $.post('spu/getSpuById',{id:id},function(data){
                    if(flag==2){  //修改
                        $("#smallPic22").prop("src", "pic/" + data.smallPic);
                        $("#smallPic22").show();
                        $('#id2').val(data.id);
                        $('#smallPic20').val(data.smallPic);
                        $('#goodsName2').val(data.goodsName);
                        $('#caption2').val(data.caption);
                        $('#isMarketable2').val(data.isMarketable);
                        $('#brandId2').val(data.brandId);
                        $('#brandId2 option[value=' + data.brandId + ']').prop('selected',true);
                        $('#categoryId2').val(data.categoryId);
                        $('#categoryName2').val(data.cName);
                    }else{  //查看
                        $("#smallPic32").prop("src", "pic/" + data.smallPic);
                        $("#smallPic32").show();
                        $('#goodsName3').text(data.goodsName);
                        $('#caption3').text(data.caption);
                        $('#isMarketable3').text(data.isMarketable==0 ? '上架' : '下架');
                        $('#brandName3').text(data.bName);
                        $('#categoryName3').text(data.cName);
                    }
                },'json');
            }
            $(function(){
                $('#cbk').on('click',function(){
                    $('.ck').prop('checked', this.checked);
                });
            })
            function deleteSpu(ids){
                if(ids==undefined){
                    ids = $('.ck:checked').map(function(){
                        return this.value;
                    }).get().join(',');
                }
                if(ids!=''){
                    if(confirm('确定要删除选中的数据吗?')){
                        $.post('spu/spuDelete',{ids:ids},function(data){
                            if(data){
                                window.location.reload();
                            }else{
                                alert('删除spu失败');
                            }
                        },'json');
                    }
                }else{
                    alert('请选中要删除的数据');
                }
            }

        </script>
    </head>

<body>

<div class="container-fluid">
    <div class="row">
        <!-- 加入了列 填充整行 -->
        <form class="col-sm-12" action="spu/spuList" method="post">
            <div class="form-group">
                <label>商品名称</label>
                <input type="text" name="goodsName" class="form-control" placeholder="请输入品牌名称" value="${spu.goodsName}">
            </div>

            <div class="form-group">
                <label>商品副标题</label>
                <input type="text" name="caption" class="form-control" placeholder="请输入商品副标题" value="${spu.caption}">
            </div>
            <!-- 隐藏分类的id值 -->
            <input type="hidden" id="cid" name="categoryId" value="${spu.categoryId}"/>
            <button class="btn btn-success" type="submit">搜索</button>
        </form>
    </div>

    <!-- 外边距(下方) 10像素的大小 -->
    <div class="row" style="margin-bottom: 10px;">
        <!-- 右端对齐 -->
        <div class="col-sm-12" align="right">
            <input type="button" class="btn btn-danger"
                   onclick="deleteSpu()" value="批量删除" />
            <button class="btn btn-primary btn-sm" onclick="preAddSpu(1)"
                    data-toggle="modal" data-target="#myModal">添加商品</button>
        </div>
    </div>


    <div class="row">
        <table class="table table-striped">
            <thead>
            <tr>
                <th scope="col"><input type="checkbox" id="cbk" />全选</th>
                <th scope="col">序号</th>
                <th scope="col">图标</th>
                <th scope="col">商品名称</th>
                <th scope="col">商品状态</th>
                <th scope="col">商品品牌</th>
                <th scope="col">商品分类</th>
                <th scope="col">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${pageInfo.list}" var="spu" varStatus="index">
                <tr>
                    <td scope="row">
                        <input type="checkbox" class="ck" value="${spu.id}"/>
                    </td>
                    <td>${index.count}</td>
                    <td class="col-sm-2">
                        <img class="img-responsive" src="pic/${spu.smallPic}"/>
                    </td>
                    <td class="col-sm-2">${spu.goodsName}</td>
                    <td>${spu.isMarketable==0 ? '上架' : '下架'}</td>
                    <td>${spu.bName}</td>
                    <td>${spu.cName}</td>
                    <td>
                        <a href="javascript:void(0)" onclick="deleteSpu(${spu.id})" class="btn btn-info">删除</a>
                        <button class="btn btn-info btn-sm" data-toggle="modal"
                                data-target="#myModal2"
                                onclick="getSpuById(${spu.id},2)">修改</button>
                        <button class="btn btn-primary btn-sm" data-toggle="modal"
                                data-target="#myModal3"
                                onclick="getSpuById(${spu.id},3)">详情</button></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <nav>
            <ul class="pagination">
                <c:if test="${pageInfo.hasPreviousPage}">
                    <li>
                        <a href="spu/spuList?pageNum=${pageInfo.prePage}&goodsName=${spu.goodsName}&caption=${spu.caption}">
                            <span aria-hidden="true">上一页</span>
                        </a>
                    </li>
                </c:if>
                <c:forEach items="${pageInfo.navigatepageNums}" var="pageNum">
                    <c:if test="${pageInfo.pageNum==pageNum}">
                        <li class="active"><a href="spu/spuList?pageNum=${pageNum}&goodsName=${spu.goodsName}&caption=${spu.caption}">${pageNum}</a></li>
                    </c:if>
                    <c:if test="${pageInfo.pageNum!=pageNum}">
                        <li><a href="spu/spuList?pageNum=${pageNum}&goodsName=${spu.goodsName}&caption=${spu.caption}">${pageNum}</a></li>
                    </c:if>
                </c:forEach>
                <c:if test="${pageInfo.hasNextPage}">
                    <li>
                        <a href="spu/spuList?pageNum=${pageInfo.nextPage}&goodsName=${spu.goodsName}&caption=${spu.caption}" aria-label="Next">
                            <span aria-hidden="true">下一页</span>
                        </a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>

</div>


<!-- ////////////////添加模态框 //////////////////////////////-->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">

    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <!-- 关闭的x效果 -->
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;</button>

                <!-- 模态框的标题 -->
                <h4 class="modal-title" id="spuAddModalLabel">添加商品操作</h4>
            </div>
            <div class="modal-body">
                <form id="modalForm" enctype="multipart/form-data" action="javascript:void(0)">
                    <div class="form-group row">
                        <label for="goodsName"
                               class="col-sm-3 col-form-label col-form-label-sm">商品名称</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control form-control-sm"
                                   id="goodsName" name="goodsName" placeholder="请输入商品名称">
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="caption" class="col-sm-3 col-form-label">商品副标题</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="caption"
                                   name="caption" placeholder="请输入商品的副标题">
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="isMarketable" class="col-sm-3 col-form-label">商品状态</label>
                        <div class="col-sm-9">
                            <select class="form-control" id="isMarketable" name="isMarketable">
                                <option value="">请选择</option>
                                <option value="0">上架</option>
                                <option value="1">下架</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="brandId" class="col-sm-3 col-form-label">商品品牌</label>
                        <div class="col-sm-9">
                            <select class="form-control" id="brandId" name="brandId">
                                <option value="">请选择品牌</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="categoryName" class="col-sm-3 col-form-label">商品分类</label>
                        <!-- 左边部分 -->
                        <div class="col-sm-9">
                            <input type="hidden" class="form-control" id="categoryId" name="categoryId">
                            <input type="text" class="form-control" id="categoryName" placeholder="选择商品分类">
                            <div id="tree" style="display: none; position:absolute; z-index:1010; background-color:white; "></div>
                        </div>
                    </div>


                    <div class="form-group row">
                        <label for="smallPic1" class="col-sm-3 col-form-label">商品图标</label>
                        <div class="col-sm-9">
                            <input type="file" class="form-control" id="smallPic11"
                                   name="file" onchange="show(this,1)"/>
                            <img id="smallPic12" alt="商品图标" style="display:none">
                        </div>
                    </div>


                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="addSpu(1)">添加</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>
<!-- ///////////////////添加模态框结束//////////////////////////// -->

<!-- ////////////////修改模态框 //////////////////////////////-->
<div class="modal fade" id="myModal2" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">

    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <!-- 关闭的x效果 -->
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;</button>

                <!-- 模态框的标题 -->
                <h4 class="modal-title">修改商品</h4>
            </div>
            <div class="modal-body">
                <form id="modalForm2" enctype="multipart/form-data" action="javascript:void(0)">
                    <input type="hidden" name="id" id="id2"/>
                    <input type="hidden" name="smallPic" id="smallPic20"/>
                    <div class="form-group row">
                        <label for="goodsName2"
                               class="col-sm-3 col-form-label col-form-label-sm">商品名称</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control form-control-sm"
                                   id="goodsName2" name="goodsName" placeholder="请输入商品名称">
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="caption2" class="col-sm-3 col-form-label">商品副标题</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="caption2"
                                   name="caption" placeholder="请输入商品的副标题">
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="isMarketable2" class="col-sm-3 col-form-label">商品状态</label>
                        <div class="col-sm-9">
                            <select class="form-control" id="isMarketable2" name="isMarketable">
                                <option value="">请选择</option>
                                <option value="0">上架</option>
                                <option value="1">下架</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="brandId2" class="col-sm-3 col-form-label">商品品牌</label>
                        <div class="col-sm-9">
                            <select class="form-control" id="brandId2" name="brandId">
                                <option value="">请选择品牌</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="categoryName2" class="col-sm-3 col-form-label">商品分类</label>
                        <!-- 左边部分 -->
                        <div class="col-sm-9">
                            <input type="hidden" class="form-control" id="categoryId2" name="categoryId">
                            <input type="text" class="form-control" id="categoryName2" placeholder="选择商品分类">
                            <div id="tree2" style="display: none; position:absolute; z-index:1010; background-color:white; "></div>
                        </div>
                    </div>


                    <div class="form-group row">
                        <label for="smallPic21" class="col-sm-3 col-form-label">商品图标</label>
                        <div class="col-sm-9">
                            <input type="file" class="form-control" id="smallPic21"
                                   name="file" onchange="show(this,2)"/>
                            <img id="smallPic22" alt="商品图标" style="display:none">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="addSpu(2)">编辑</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>
<!-- ///////////////////修改模态框结束//////////////////////////// -->

<!-- ////////////////查看模态框 //////////////////////////////-->
<div class="modal fade" id="myModal3" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">

    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <!-- 关闭的x效果 -->
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;</button>

                <!-- 模态框的标题 -->
                <h4 class="modal-title">查看商品</h4>
            </div>
            <div class="modal-body">
                <form id="modalForm3" action="javascript:void(0)">
                    <div class="form-group row">
                        <label for="goodsName3"
                               class="col-sm-3 col-form-label col-form-label-sm">商品名称</label>
                        <div class="col-sm-9">
                            <span id="goodsName3"></span>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="caption3" class="col-sm-3 col-form-label">商品副标题</label>
                        <div class="col-sm-9">
                            <span id="caption3"></span>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="isMarketable3" class="col-sm-3 col-form-label">商品状态</label>
                        <div class="col-sm-9">
                            <span id="isMarketable3"></span>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="brandName3" class="col-sm-3 col-form-label">商品品牌</label>
                        <div class="col-sm-9">
                            <span id="brandName3"></span>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="categoryName3" class="col-sm-3 col-form-label">商品分类</label>
                        <!-- 左边部分 -->
                        <div class="col-sm-9">
                            <span id="categoryName3"></span>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="smallPic32" class="col-sm-3 col-form-label">商品图标</label>
                        <div class="col-sm-9">
                            <img id="smallPic32" alt="商品图标">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>
<!-- ///////////////////查看模态框结束//////////////////////////// -->

</body>
</html>