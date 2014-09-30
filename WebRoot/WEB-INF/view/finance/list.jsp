<%--
  Created by IntelliJ IDEA.
  User: luowei
  Date: 14-3-29
  Time: 上午2:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>消费<c:if test="${tableName eq 'daytip_detail'}">明细</c:if>管理</title>
</head>
<body>
<div class=" container ">
    <form:form name="searchForm" modelAttribute="daytip" role="form-horizontal" action="${ctx}/finance/list" method="post">
        <input type="hidden" name="pageNumber" value="${pageNumber}">
        <input type="hidden" name="pageSize" value="${pageSize}">
        <input type="hidden" name="order" value="${order}">
        <div class="from-group " style="margin-bottom: 10px">
            <div class="row">
                <div class="col-md-9 ">
                    <div class="input-group">
                        <span class="input-group-addon">描述:</span>
                        <form:input type="text" path="desc" class="form-control" cssStyle="max-width: 120px"/>
                        <span class="input-group-addon">开始日期:</span>
                        <form:input type="text" path="startDate" class="form-control" cssStyle="max-width: 100px"/>
                        <span class="input-group-addon">结束日期:</span>
                        <form:input type="text" path="endDate" class="form-control" cssStyle="max-width: 100px"/>
                        <span class="input-group-addon">类型:</span>
                        <form:input type="text" path="type" class="form-control" cssStyle="max-width: 60px"/>
                        <span class="input-group-addon"></span>
                        <select id="tableName" name="tableName" class="form-control"  cssStyle="max-width: 60px">
                              <option value="daytip" <c:if test="${tableName ne 'daytipDetail'}">selected </c:if> >简要数据</option>
                            <option value="daytipDetail" <c:if test="${tableName eq 'daytipDetail'}">selected </c:if>>明细数据</option>
                        </select>
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
                        <button class="btn btn-warning" type="button" onclick="reloadDaytip()">从文件重新加载</button>
                    </div>
                </div>
            </div>
        </div>
    </form:form>

    <div class="text-center">
        <jsp:include page="../common/pagination.jsp"/>
    </div>

    <table border="0" class="table table-bordered">
        <tr>
            <th>Id</th>
            <th>日期
                &nbsp;&nbsp;<span onclick="orderQuery('tipDate asc')" style="cursor: pointer">↑</span>
                &nbsp;&nbsp;<span onclick="orderQuery('tipDate desc')" style="cursor: pointer">↓</span>
            </th>
            <th>星期</th>
            <th>消费(￥)</th>
            <th>描述</th>
            <th>类型</th>
        </tr>
        <c:forEach var="tip" items="${content}">
            <tr>
                <td>${tip.id}</td>
                <td><fmt:formatDate value="${tip.tipDate}" pattern="yyyy-MM-dd"/></td>
                <td>${tip.week}</td>
                <td>${tip.money}</td>
                <td>${tip.desc}</td>
                <td>${tip.type}</td>
            </tr>
        </c:forEach>
        <tr><td colspan="5">价格总计:${moneySum}</td></tr>
    </table>
</div>

<script src="<c:url value='/resources/js/mycrypt.min.js'/>"></script>
<script src="<c:url value='/resources/js/jquery-ui/1.10.4/ui/jquery-ui.min.js'/>"></script>

<script type="text/javascript">
    var reloadDaytip = function(){
        if(!confirm('确定要重新加载数据')){ return; }
        var tableName = $('#tableName').val()
        var reloadUrl = '${ctx}/finance/reloadDaytip?tableName='+tableName;
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

</script>
</body>
</html>