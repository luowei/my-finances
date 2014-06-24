$(function () {
    $("#loginForm").validate({
        errorElement:"span",
        rules:{
            username:{
                required:true
            },
            password:{
                required:true
            }
        },
        messages:{
            username:"用户名必须填写",
            password:"密码必须填写"
        }
    });
});