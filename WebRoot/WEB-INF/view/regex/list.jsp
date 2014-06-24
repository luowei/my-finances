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
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <title>常用正则表达式</title>

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

    <s:form form="searchForm" cssClass="from" modelAttribute="regexTip" role="form-horizontal" action="${ctx}/regex/list"
            method="post">
        <div class="from-group" style="margin-bottom: 10px">
            <div class="row">
                <div class="col-md-3">
                    <div class="input-group">
                        <span class="input-group-addon">名称:</span>
                        <s:input placeholder="名称" type="text" path="name" class="form-control"/>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="input-group">
                        <span class="input-group-addon">描述:</span>
                        <s:input placeholder="描述" type="text" path="describe" class="form-control"/>
                    </div>
                </div>
                <div class="col-md-1">
                    <button class="btn btn-primary" type="submit">查询</button>
                </div>
                <div class="col-md-1">
                    <button class="btn btn-success" type="button" onclick="addRegex()">添加</button>
                </div>
                <div class="col-md-1">
                    <button class="btn btn-warning" type="button" onclick="reloadRegex()">从文件重新加载</button>
                </div>
            </div>

        </div>
    </s:form>

    <div class=""></div>

    <div class="table-responsive">
        <table border="0" class="table table-hover table-bordered">
            <tr>
                <th>Id</th>
                <th>名称</th>
                <th>描述</th>
                <th>正则内容</th>
                <th>操作</th>
            </tr>
            <c:forEach var="regex" items="${list}">
                <tr class="active">
                    <td>${regex.id}</td>
                    <td>${regex.name}</td>
                    <td>
                        <div title="${regex.describe}" style="width:200px;overflow: hidden;text-overflow: clip;">
                                ${regex.describe}</div>
                    </td>
                    <c:set var="regContent" value="${regex.regex eq null or regex.regex eq 'null'? '':regex.regex}"/>
                    <td>
                        <div style="width:250px;overflow: hidden;text-overflow: clip;">
                            <code id="keySecret" class="text-muted" data-container="body" data-placement="bottom"
                                  title="load ..."
                                  onmousemove="showTip(this,{title:'${regex.name}',content:'${regContent}'})">
                                    ${regContent}</code>
                        </div>
                    </td>
                    <td>
                        <button class="btn btn-sm btn-info" onclick="updateRegex('${regex.id}')">修改</button>
                        <button class="btn  btn-sm btn-waring" onclick="delRegex('${ctx}/regex/del?id=${regex.id}')">
                            删除
                        </button>
                    </td>

                </tr>
            </c:forEach>

        </table>
    </div>

</div>

<!-- Modal -->
<%--<div class="modal fade" id="updateModalWin" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">--%>
<div class="modal-dialog fade" id="addModalWin" aria-hidden="true" tabindex="-1">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="addModalLabel">添加</h4>
        </div>
        <div class="modal-body model-open">
            <form id="addRegexForm" class="form" action="${ctx}/regex/add" method="post">
                <div class="form-group">
                    <div class="row">
                        <div class=" col-md-6">
                            <div class="input-group">
                                <span class="input-group-addon">名称:</span>
                                <input placeholder="名称" type="text" name="name" class="form-control"/>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="input-group">
                                <span class="input-group-addon">描述:</span>
                                <input placeholder="描述" type="text" name="describe" class="form-control"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group col-md-12">
                        <span class="input-group-addon">表达式:</span>
                        <textarea name="regex" class="form-control" rows="4" placeholder="正则内容..."></textarea>
                    </div>
                </div>
            </form>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" onclick="submitRegex('addRegexForm','addModalWin')">保存
            </button>
        </div>
    </div>
    <!-- /.modal-content -->
</div>
<!-- /.modal-dialog -->
<%--</div><!-- /.modal -->--%>

<div class="modal-dialog fade" id="updateModalWin" aria-hidden="true" tabindex="-1">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="updateModalLabel">修改</h4>
        </div>
        <div class="modal-body">
            <form id="updateRegexForm" class="form" action="${ctx}/regex/update" method="post">
                <input type="hidden" name="id" value="">

                <div class="form-group">
                    <div class="row">
                        <div class=" col-md-6">
                            <div class="input-group">
                                <span class="input-group-addon">名称:</span>
                                <input placeholder="名称" type="text" name="name" class="form-control"/>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="input-group">
                                <span class="input-group-addon">描述:</span>
                                <input placeholder="描述" type="text" name="describe" class="form-control"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group col-md-12">
                        <span class="input-group-addon">表达式:</span>
                        <textarea name="regex" class="form-control" rows="4" placeholder="正则内容..."></textarea>
                    </div>
                </div>
            </form>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" onclick="submitRegex('updateRegexForm','updateModalWin')">保存
            </button>
        </div>
    </div>
    <!-- /.modal-content -->
</div>


<script src="<c:url value='/resources/js/base64.js'/>"></script>
<script src="<c:url value='/resources/js/jquery-ui/1.10.4/ui/jquery-ui.min.js'/>"></script>

<script type="text/javascript">
    var reloadRegex = function () {
        if(!confirm('确定要删除？')){
            return;
        }
        var reloadRegexUrl = '${ctx}/regex/reloadRegex'
        $.ajax({
            url: reloadRegexUrl,
            dataType: "json",
            type: "POST",
            cache: false,
            success: function (data) {
                showAjaxMsg(data);
            }
        });
    }
    var addRegex = function () {
        $('#addModalWin').draggable().modal({show: true});
    }
    var updateRegex = function (id) {
        $('#updateModalWin').draggable().modal({show: true})

        var regex = getRegex('${ctx}/regex/getRegex?id=' + id)
        regex.regex = utf8to16(base64decode(regex.regex));
        populate($('#updateRegexForm'), regex);
    }
    var delRegex = function (url) {
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
    var getRegex = function (url) {
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
    var submitRegex = function (formName, modelId) {
//        var data = $("#" + formName).serialize();
        var data = $("#" + formName).serializeJson()
        if (typeof data.regex != "undefined") {
            data.regex = base64encode(utf16to8(data.regex));
        }
        $.ajax({
            url: $('#' + formName).attr("action"),
            dataType: "json",
            type: "POST",
            data: data,
            cache: false,
            success: function (data) {
                showAjaxMsg(data);
            }
        })
    }
    var showTip = function (obj, regex) {
        $(obj).attr({"data-original-title": regex.title, "data-content": utf8to16(base64decode(regex.content))});
    }

    $(function () {
        $('code').popover();
        $('.model-dialog').draggable().modal({keyboard: true, backdrop: true})
    });

</script>
</body>
</body>
</html>