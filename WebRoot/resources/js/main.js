/**
 * Created with inputtelliJ IDEA.
 * User: luowei
 * Date: 14-3-31
 * Time: 下午2:08
 * To change this template use File | Settinputgs | File Templates.
 */

(function ($) {
    $.fn.serializeJson = function () {
        var serializeObj = {};
        var array = this.serializeArray();
        var str = this.serialize();
        $(array).each(function () {
            if (serializeObj[this.name]) {
                if ($.isArray(serializeObj[this.name])) {
                    serializeObj[this.name].push(this.value);
                } else {
                    serializeObj[this.name] = [serializeObj[this.name], this.value];
                }
            } else {
                serializeObj[this.name] = this.value;
            }
        });
        return serializeObj;
    };
})(jQuery);

var populate = function (form, data) {
    $.each(data, function (key, value) {
        var $ctrl = $('[name=' + key + ']', form);
        switch ($ctrl.attr("type")) {
            case "text" :
            case "hidden":
                $ctrl.val(value);
                break;
            case "radio" :
            case "checkbox":
                $ctrl.each(function () {
                    if ($(this).attr('value') == value) {
                        $(this).attr("checked", value);
                    }
                });
                break;
            default:
                $ctrl.val(value);
        }
    });
}

var getTitle = function (id) {
    var result = $.ajax({
        url: '${ctx}/account/getKeyReal?id=' + id,
        dataType: "json",
        async: false,
        data: null,
        cache: true,
        success: function () {
        }
    }).responseText.content;
    return result.content;
}

var showAjaxMsg = function (data) {
    if (typeof data == 'string' || data instanceof String) {
        data = eval('(' + data + ')')
    }
    if (data.code == 1) {
        if ($('form[name=searchForm]').length) {
            $('form[name=searchForm]').submit()
        } else {
            location.reload();
        }
    } else {
        //失败
        alert(data.msg)
    }
}

var orderQuery = function(oderStr){
    $('input[name=order').val(oderStr)
    $('form[name=searchForm]').submit();
}

