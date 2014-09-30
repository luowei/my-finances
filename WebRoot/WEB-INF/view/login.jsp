<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>登录</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/bootstrap3/css/bootstrap.min.css' />"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/bootstrap3/css/bootstrap-theme.min.css' />"/>

</head>
<body onload='document.loginForm.username.focus();'>

<div class="content">
    <form name="loginForm" action="<c:url value='/j_spring_security_check' />" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <div class="col-md-3 col-md-offset-4"><h3>用户登录</h3></div>
        <div class="col-md-3 col-md-offset-3">
            <div class="form-group">
                <div class="input-group">
                    <span class="input-group-addon">用户名:</span>
                    <input id="username" name="username" class="form-control" placeholder="admin" value="admin"/>
                </div>
            </div>
            <div class="form-group">
                <div class="input-group">
                    <span class="input-group-addon">密码:</span>
                    <input name="password" type="password" class="form-control" placeholder="abc123"/>
                </div>
            </div>

            <div class="form-group">
                <div class="checkbox">
                    <label>
                        <input type="checkbox"> Remember me
                    </label>
                </div>
            </div>
            <div class="form-group">
                <button class="btn btn-primary" onclick="login()">登录</button>
            </div>
        </div>
    </form>

</div>
<script type="text/javascript" src="<c:url value='/resources/js/jquery/jquery-2.1.0.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/bootstrap3/js/bootstrap.min.js'/>"></script>
<script src="<c:url value='/resources/js/mycrypt.min.js'/>"></script>
<script type="application/javascript">
    var login = function(){
        var username = $('input[name=username]').val();
         var password = $('input[name=password]').val();
        var key = getSecretKey(username);
        $('input[name=password]').val(jiami(password,key))
        $('form[name=loginForm]').submit();
    }

    var getSecretKey = function(username){
        var key = null;
        var data = $.ajax({
            url: "${ctx}/auth/getSecretKey?username="+username,
            dataType: "json",
            type: "GET",
            data: null,
            async: false,
            cache: true,
            success: function (data) {
                if (typeof data == 'string' || data instanceof String) {
                    var ret = eval('(' + data + ')');
                    if(ret.code ==1){
                        key = ret.key;
                    }else{
                        alert('获取密钥失败')
                        key = null;
                    }
                } else {
                    ret = data;
                    if(ret.code ==1){
                        key = ret.key;
                    }else{
                        alert('获取密钥失败')
                        key = null;
                    }
                }
            }
        });
        secretKey = key;
        return key;
    }
</script>

</body>
</html>
