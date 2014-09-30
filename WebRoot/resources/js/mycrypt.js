/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 14-4-5
 * Time: 下午12:53
 * To change this template use File | Settings | File Templates.
 */

/*对Date的扩展，将 Date 转化为指定格式的String
 * 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
 * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
 * 例子：
 * (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
 * (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
 */
Date.prototype.Format = function(fmt)
{ //author: meizz
    var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt))
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
        if(new RegExp("("+ k +")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
    return fmt;
}

var secretKey = null;

var myEncrypt = function (input,key) {
    if (input == null) {
        return null;
    }
    if(typeof(key)  === "undefined" || key ==null){
        key = secretKey==null?"luowei":secretKey;
    }

    var num = 1;
    if (key != null) {
        for (var i = key.length - 1; i > 0; i--) {
            num += key.charCodeAt(i);
        }
        num = num % (key.length - 1);
        num = num==0?1:num;
    }

    var output = "";
    for (var i = input.length - 1; i >= 0; i--) {
        if (i == num) {
            output += num;
        }
        if (input.charCodeAt(i) == 0) {
            output += String.fromCharCode(127);
        }else if (input.charCodeAt(i) == 65) {
            output += "Z";
        } else if (input.charCodeAt(i) == 97) {
            output += "z";
        } else if (input.charCodeAt(i) == 48) {
            output += "9";
        } else {
            output += String.fromCharCode(input.charCodeAt(i) - 1);
        }
    }
    return output;
}

var myDecrypt = function (output,key) {
    if (output == null) {
        return null;
    }

    if(typeof(key)  === "undefined" || key ==null){
        key = secretKey==null?"luowei":secretKey;
    }

    var num = 1;
    if (key != null) {
        for (var i = key.length - 1; i > 0; i--) {
            num += key.charCodeAt(i);
        }
        num = num % (key.length - 1);
        num = num==0?1:num;
    }

    var input = "";
    for (var i = output.length - 1; i >= 0; i--) {
        if(i==(output.length-1-1-num)){
            continue;
        }
        if (output.charCodeAt(i) == 127) {
            input += String.fromCharCode(0);
        }if (output.charCodeAt(i) == 57 ) {
            input += "0";
        } else if (output.charCodeAt(i) == 90 ) {
            input += "A";
        } else if (output.charCodeAt(i) == 122) {
            input += "a";
        } else {
            input += String.fromCharCode((output.charCodeAt(i) + 1));
        }

    }
    return input;
}

var jiami = function(input,key){
    if(input==null){
        return null;
    }
    return myEncrypt(input+(new Date()).Format("yyyyMMdd"),key)
}

var jiemi = function(output,key){
    if(output==null){
        return null;
    }
    return myDecrypt(output.substring(8),key)
}
