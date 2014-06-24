<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/bootstrap3/css/bootstrap.min.css' />"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/bootstrap3/css/bootstrap-theme.min.css' />"/>
    <script type="text/javascript" src="<c:url value="/resources/bootstrap3/js/bootstrap.min.js" />"></script>

    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/main.css' />"/>
</head>
<body>
<div class=" wrapper ">

    <a href="${ctx}/finance/list" target="_blank" class="btn btn-success">消费管理</a> &nbsp;&nbsp;
    <a href="${ctx}/finance/list?tableName=daytipDetail" target="_blank" class="btn btn-success">消费明细管理</a>

</div>
</body>
</html>
