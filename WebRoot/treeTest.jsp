<%--
  Created by IntelliJ IDEA.
  User: luowei
  Date: 14-4-4
  Time: 下午4:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title></title>
    <link href="./resources/js/ztree/css/zTreeStyle.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="./resources/js/jquery/jquery-2.1.0.min.js"></script>
    <script type="text/javascript" src="./resources/js/ztree/js/jquery.ztree.core-3.5.min.js"></script>
    <script type="text/javascript" src="./resources/js/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
    <script type="text/javascript">
        var setting = {
            check: {
                enable: true,
                chkboxType: {"Y":"", "N":""}
            },
            view: {
                dblClickExpand: false
            },
            async: {
                enable: true,
                url:"${ctx}/common/getGroups.lz",
                autoParam:["id", "name=n", "level=lv"],
                otherParam:{"otherParam":"zTreeAsyncTest"},
                dataFilter: filter
            },
            callback: {
                beforeClick: beforeClick,
                onCheck: onCheck
            }
        };

        function filter(treeId, parentNode, childNodes) {
            if (!childNodes) return null;
            for (var i=0, l=childNodes.length; i<l; i++) {
                childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
            }
            return childNodes;
        }

        function beforeClick(treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            zTree.checkNode(treeNode, !treeNode.checked, null, true);
            return false;
        }

        function onCheck(e, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
                    nodes = zTree.getCheckedNodes(true),
                    v = "";
            for (var i=0, l=nodes.length; i<l; i++) {
                v += nodes[i].name + ",";
            }
            if (v.length > 0 ) v = v.substring(0, v.length-1);
            var cityObj = $("#citySel");
            cityObj.attr("value", v);
        }

        function showMenu() {
            var cityObj = $("#citySel");
            var cityOffset = $("#citySel").offset();
            $("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");

            $("body").bind("mousedown", onBodyDown);
        }
        function hideMenu() {
            $("#menuContent").fadeOut("fast");
            $("body").unbind("mousedown", onBodyDown);
        }
        function onBodyDown(event) {
            if (!(event.target.id == "menuBtn" || event.target.id == "citySel" || event.target.id == "menuContent"
                    || $(event.target).parents("#menuContent").length>0)) {
                hideMenu();
            }
        }

        $(document).ready(function(){
            $.fn.zTree.init($("#treeDemo"), setting);
        });

    </script>

</head>
<body>

<h1>带 checkbox 的多选下拉菜单  -- zTree</h1>
<input id="citySel" type="text" readonly value="" style="width:120px;" onclick="showMenu();" />
&nbsp;<a id="menuBtn" href="#" onclick="showMenu(); return false;">select</a>

<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>

<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
    <ul id="treeDemo" class="ztree" style="margin-top:0; width:180px; height: 300px;"></ul>
</div>

</body>
</html>