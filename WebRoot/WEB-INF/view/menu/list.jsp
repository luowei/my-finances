<%--
  Created by IntelliJ IDEA.
  User: luowei
  Date: 14-3-31
  Time: 下午12:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <title>菜单列表</title>
</head>
<body>

<div class=" container">
    <form:form name="searchForm" modelAttribute="entity" role="form-horizontal" action="${ctx}/menu/list" method="post">
        <input type="hidden" name="pageNumber" value="${pageNumber}">
        <input type="hidden" name="pageSize" value="${pageSize}">
        <input type="hidden" name="order" value="${order}">
        <div class="from-group " style="margin-bottom: 10px">
            <div class="row">
                <div class="col-md-5 ">
                    <div class="input-group">
                        <span class="input-group-addon">名称:</span>
                        <form:input type="text" path="menuName" class="form-control"/>
                        <span class="input-group-addon">描述:</span>
                        <form:input type="text" path="describtion" class="form-control"/>
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
                        <button class="btn btn-warning" type="button" onclick="reloadBeans()">从文件重新加载</button>
                    </div>
                </div>
            </div>
        </div>
    </form:form>

    <div class="text-center">
        <jsp:include page="../common/pagination.jsp"/>
    </div>

    <div class="table-responsive">
        <table border="0" class="table table-hover table-condensed table-bordered">
            <tr>
                <th>Id</th>
                <th>名称</th>
                <th>链接</th>
                <th>描述</th>
                <th>父菜单</th>
                <th>排序</th>
                <th>操作</th>
            </tr>
            <c:forEach var="bean" items="${content}">
                <tr >
                    <td>${bean.id}</td>
                    <td>${bean.menuName}</td>
                    <td>${bean.menuUrl}</td>
                    <td>${bean.describtion}</td>
                    <td>${bean.parent}</td>
                    <td>${bean.sort}</td>
                    <td>
                        <button class="btn btn-xs btn-info" onclick="update('${bean.id}')">修改</button>
                        <button class="btn  btn-xs btn-danger" onclick="del('${ctx}/menu/del?id=${bean.id}')">删除
                        </button>
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
            <form id="addForm" class="form" action="${ctx}/menu/add" method="post">
                <div class="form-group">
                    <div class="input-group">
                        <span class="input-group-addon">名称:</span>
                        <input placeholder="名称" type="text" name="menuName" class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <span class="input-group-addon">链接:</span>
                        <input placeholder="链接" type="text" name="menuUrl" class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <span class="input-group-addon">描述:</span>
                        <input placeholder="描述" type="text" name="describtion" class="form-control"/>
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
            <form id="updateForm" class="form" action="${ctx}/menu/update" method="post">
                <input type="hidden" name="id" value="">

                <div class="form-group">
                    <div class="input-group">
                        <span class="input-group-addon">名称:</span>
                        <input placeholder="名称" type="text" name="menuName" class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <span class="input-group-addon">链接:</span>
                        <input placeholder="链接" type="text" name="menuUrl" class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <span class="input-group-addon">描述:</span>
                        <input placeholder="描述" type="text" name="describtion" class="form-control"/>
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
    var reloadBeans = function () {
        if (!confirm('确定要重新加载数据')) {
            return;
        }
        var reloadUrl = '${ctx}/menu/reloadBeans';
        $.ajax({
            url: reloadUrl,
            dataType: "json",
            type: "post",
            cache: false,
            success: function (data) {
                showAjaxMsg(data);
            }
        });
    }
    var add = function () {
        $('#addModalWin').draggable().modal({show: true});
    }
    var update = function (id) {
        $('#updateModalWin').draggable().modal({show: true})

        var bean = getBean('${ctx}/menu/getMenu?id=' + id)
        populate($('#updateForm'), bean);
    }
    var del = function (url) {
        if (!confirm('你确定要删除？')) {
            return;
        }
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
        var ret = null;
        $.ajax({
            url: url,
            dataType: "json",
            type: "POST",
            data: null,
            async: false,
            cache: true,
            success: function (data) {
                if (typeof data == 'string' || data instanceof String) {
                    data = eval('(' + data + ')')
                }
                if (data.code == 1) {
                    ret = data.data;
                } else {
                    //失败
                    alert(data.msg)
                }
            }
        });
        return ret;
    }
    var submitForm = function (formName, modelId) {
        var data = $("#" + formName).serializeJson()
        $.ajax({
            url: $('#' + formName).attr("action"),
            dataType: "json",
            type: "POST",
            data: data,
            cache: false,
            success: function (data) {
                showAjaxMsg(data)
            }
        })
    }
    $(function () {
        $('.model-dialog').draggable().modal({keyboard: true, backdrop: true})
    });

</script>
</body>
</body>
</html>