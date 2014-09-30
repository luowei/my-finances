<%--
  Created by IntelliJ IDEA.
  User: luowei
  Date: 14-3-31
  Time: 下午12:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <title>常用帐号</title>
    <style type="text/css">
        .modal-dialog {
            position: relative;
            overflow: auto;
        }

        @media (max-width: 768px) {
            .modal-dialog {
                position: relative;
                overflow: auto;
            }
        }
    </style>
</head>
<body>

<div class=" container">
    <form:form name="searchForm" modelAttribute="account" role="form-horizontal" action="${ctx}/account/list" method="post">
        <input type="hidden" name="pageNumber" value="${pageNumber}">
        <input type="hidden" name="pageSize" value="${pageSize}">
        <input type="hidden" name="order" value="${order}">
        <div class="from-group " style="margin-bottom: 10px">
            <div class="row">
                <div class="col-md-5 ">
                    <div class="input-group">
                        <span class="input-group-addon">站点名称:</span>
                        <form:input type="text" path="siteName" class="form-control"/>
                        <span class="input-group-addon">帐号名称:</span>
                        <form:input type="text" path="keyName" class="form-control"/>
                    <span class="input-group-btn">
                        <button class="btn btn-primary" type="submit">查询</button>
                    </span>
                    </div>
                </div>
                <div class="col-md-1">
                    <div class="input-group">
                        <button class="btn btn-success" type="button" onclick="add()">添加</button>
                    </div>
                </div>
                <div class="col-md-1">
                    <div class="input-group">
                        <button class="btn btn-warning" type="button" onclick="reloadAccount()">从文件重新加载</button>
                    </div>
                </div>
            </div>
        </div>
    </form:form>

    <div class="text-center">
        <jsp:include page="../common/pagination.jsp"/>
    </div>

    <div class="table-responsive">
        <table border="0" class="table table-hover table-bordered">
            <tr>
                <th>Id</th>
                <th>所属网站
                    &nbsp;&nbsp;<span onclick="orderQuery('siteName asc')" style="cursor: pointer">↑</span>
                    &nbsp;&nbsp;<span onclick="orderQuery('siteName desc')" style="cursor: pointer">↓</span>
                </th>
                <th>帐号名称</th>
                <th>密码</th>
                <th>操作</th>
            </tr>
            <c:forEach var="account" items="${content}">
                <tr class="active">
                    <td>${account.id}</td>
                    <td>${account.siteName}</td>
                    <td>${account.keyName}</td>
                    <td><code id="keySecret" class="text-muted" title="load ..."
                              onmousemove="showTip(this,'${account.keySecret}')">${account.keySecret}</code></td>
                    <td>
                        <security:authorize access="hasRole('ROLE_USER')">
                        <button class="btn btn-sm btn-info" onclick="update('${account.id}')">修改</button>
                        <button class="btn  btn-sm btn-waring" onclick="del('${ctx}/account/del?id=${account.id}')">删除</button>
                        </security:authorize>
                    </td>
                </tr>
            </c:forEach>

        </table>
    </div>

    <div class="text-center">
        <jsp:include page="../common/pagination.jsp"/>
    </div>

</div>

<%--model dialog--%>
<div class="modal-dialog fade" id="addModalWin" aria-hidden="true" tabindex="-1">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="addModalLabel">添加</h4>
        </div>
        <div class="modal-body model-open">
            <form id="addForm" class="form" action="${ctx}/account/add" method="post">
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon">所属网站:</span>
                            <input placeholder="名称" type="text" name="siteName" class="form-control"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon">帐号名称:</span>
                            <input placeholder="描述" type="text" name="keyName" class="form-control"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon">密码:</span>
                            <input placeholder="密码" type="text" name="keySecret" class="form-control"/>
                        </div>
                    </div>
            </form>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" onclick="submitForm('addForm','addModalWin')">保存
            </button>
        </div>
    </div>
    <!-- /.modal-content -->
</div>

<div class="modal-dialog fade" id="updateModalWin" aria-hidden="true" tabindex="-1">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="updateModalLabel">修改</h4>
        </div>
        <div class="modal-body model-open">
            <form id="updateForm" class="form" action="${ctx}/account/update" method="post">
                <input type="hidden" name="id" value="">
                <div class="form-group">
                    <div class="input-group">
                        <span class="input-group-addon">所属网站:</span>
                        <input placeholder="名称" type="text" name="siteName" class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <span class="input-group-addon">帐号名称:</span>
                        <input placeholder="描述" type="text" name="keyName" class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <span class="input-group-addon">密码:</span>
                        <input placeholder="密码" type="text" name="keySecret" class="form-control"/>
                    </div>
                </div>
            </form>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" onclick="submitForm('updateForm','updateModalWin')">保存
            </button>
        </div>
    </div>
    <!-- /.modal-content -->
</div>

<script src="<c:url value='/resources/js/mycrypt.min.js'/>"></script>
<script src="<c:url value='/resources/js/jquery-ui/1.10.4/ui/jquery-ui.min.js'/>"></script>

<script type="text/javascript">
    var reloadAccount = function(){
        if(!confirm('确定要重新加载数据')){ return; }
        var reloadUrl = '${ctx}/account/reloadAccounts';
        $.ajax({
            url:reloadUrl,
            dataType:"json",
            type:"post",
            cache:false,
            success:function(data){
               showAjaxMsg(data);
            }
        });
    }
    var add = function () {
        $('#addModalWin').draggable().modal({show: true});
    }
    var update = function (id) {
        $('#updateModalWin').draggable().modal({show: true})

        var bean = getBean('${ctx}/account/getBean?id=' + id)
        bean.regex = jiemi(bean.keySecret);
        populate($('#updateForm'), bean);
    }
    var del = function (url) {
        if (!confirm('你确定要删除？')) { return; }
        return $.ajax({
            url: url,
            dataType: "json",
            type: "POST",
            data: null,
            cache: false,
            success: function (data) {
                showAjaxMsg(data);
            }
        })
    }

    var getBean = function (url) {
        var data = $.ajax({
            url: url,
            dataType: "json",
            type: "POST",
            data: null,
            async: false,
            cache: true,
            success: function () {
            }
        }).responseText;
        if (typeof data == 'string' || data instanceof String) {
            return eval('(' + data + ')');
        } else {
            return data;
        }
    }
    var submitForm = function (formName, modelId) {
        var data = $("#" + formName).serializeJson()
        if (typeof data.keySecret != "undefined") {
            data.keySecret = jiami(data.keySecret);
        }
        $.ajax({
            url: $('#' + formName).attr("action"),
            dataType: "json",
            type: "POST",
            data: data,
            cache: false,
            success: function (data) {
                if (typeof data == 'string' || data instanceof String) {
                    data = eval('(' + data + ')');
                    ;
                }
                if (data.code == 1) {
                    $('#' + modelId).modal('hide');
                    location.reload();
                } else {
                    //失败
                    alert(data.msg)
                }
            }
        })
    }
    var showTip = function (obj, tip) {
        $(obj).attr({"data-original-title": '明码', "data-content": jiemi(tip)});
    }
    $(function () {
        $('code').popover();
        $('.model-dialog').draggable().modal({keyboard: true, backdrop: true})
    });

</script>
</body>
</body>
</html>