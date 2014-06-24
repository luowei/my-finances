<%--
  Created by IntelliJ IDEA.
  User: luowei
  Date: 14-4-1
  Time: 下午6:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>常用工具</title>
    <style type="text/css">
        .fix400x300 {
            max-width: 400px;
            max-height: 300px;
            min-width: 400px;
            min-height: 300px;
        }
    </style>
    <script type="text/javascript">
        var clearCache = function () {
            $.ajax({
                url: '${ctx}/cache/clear',
                dataType: "json",
                type: "POST",
                data: null,
                cache: false,
                success: function (data) {
                    showAjaxMsg(data)
                }
            })
        }
        var removeLineNumber = function () {
            var data = $("#removeLineNumber").serialize()
            $.ajax({
                url: '${ctx}/other/removeLineNumber',
                dataType: "json",
                type: "POST",
                data: data,
                cache: false,
                success: function (data) {
                    $('removeLineNumber-result').text(data)
                }
            })
        }
        var removeEmptyLine = function () {
            var data = $("#removeEmptyLine").serialize()
            $.ajax({
                url: '${ctx}/other/removeEmptyLine',
                dataType: "json",
                type: "POST",
                data: data,
                cache: false,
                success: function (data) {
                    $('#removeEmptyLine-result').text(data)
                }
            })
        }
    </script>
</head>
<body>

<div class="container">

    <div class="bs-example">

        <div class="highlight">
            <div class="row">
                <div class="col-md-1">
                    <button type="button" class="btn btn-primary" onclick="clearCache()">清除服务器缓存</button>
                </div>
            </div>
        </div>

        <div class="highlight">
            <label for="removeLineNumber">去掉行首行号</label>

            <form id="removeLineNumber" action="">
                <div class="row">
                    <div class="form-group col-md-10">匹配正则：<input name="regex" value=""></div>
                    <div class="form-group col-md-5">
                        源文本：<br/>
                        <textarea name="source" class="fix400x300"></textarea>
                        </span>
                    </div>
                    <div class="form-group col-md-5">
                        结果：<br/>
                        <textarea id="removeLineNumber-result" class="fix400x300" disabled></textarea>
                    </div>
                    <div class="form-group col-md-1 col-md-offset-4">
                        <button type="button" class="btn btn-primary" onclick="removeLineNumber()">去掉行号</button>
                    </div>
                </div>
            </form>
        </div>

        <div class="highlight">
            <label for="removeEmptyLine">去掉文本中的空行</label>

            <form id="removeEmptyLine" action="">
                <div class="row">
                    <div class="form-group col-md-10">匹配正则：<input name="regex" value="" disabled></div>
                    <div class="form-group col-md-5">
                        源文本：<br/>
                        <textarea name="source" class="fix400x300"></textarea>
                        </span>
                    </div>
                    <div class="form-group col-md-5">
                        结果：<br/>
                        <textarea id="removeEmptyLine-result" class="fix400x300" disabled></textarea>
                    </div>
                    <div class="form-group col-md-1 col-md-offset-4">
                        <button type="button" class="btn btn-primary" onclick="removeEmptyLine()">去掉空行</button>
                    </div>
                </div>
            </form>
        </div>


    </div>

</div>
</body>
</html>