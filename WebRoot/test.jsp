<%--
  Created by IntelliJ IDEA.
  User: luowei
  Date: 14-3-29
  Time: 上午5:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Test</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/bootstrap3/css/bootstrap.min.css' />"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/bootstrap3/css/bootstrap-theme.min.css' />"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/docs.css' />"/>

    <script type="text/javascript" src="<c:url value='/resources/js/jquery/jquery-2.1.0.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/resources/bootstrap3/js/bootstrap.min.js'/>"></script>
</head>
<body>

<header class="navbar navbar-inverse navbar-fixed-top bs-docs-nav" role="banner">
    <div class="container">
        <div class="navbar-header">
            <button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".bs-navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a href="#" class="navbar-brand">唯为社区</a>
        </div>
        <nav class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
            <ul class="nav navbar-nav">
                <li><a href="#">起步</a></li>
                <li><a href="#">组件</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="../about">关于</a></li>
            </ul>
        </nav>
    </div>
</header>

<div class="bs-header" id="content">
    <div class="container">
        <h1>JavaScript插件</h1>

        <p>jQuery插件为Bootstrap的组件赋予了“生命”。可以简单地一次性引入所有插件，或者单个引入到你的页面。</p>

    </div>
</div>

<%--------------------------------------------%>

<div class="container bs-docs-container">
    <div class="row">
        <div class="col-md-3">
            <div class="bs-sidebar hidden-print affix-top" role="complementary">
                <ul class="nav bs-sidenav">
                    <%-------------------------%>
                        <li class="">
                            <a href="#overview">概览</a>
                            <ul class="nav">
                                <li class=""><a href="#overview-doctype">HTML5文档类型</a></li>
                                <li class=""><a href="#overview-mobile">移动设备优先</a></li>
                                <li class=""><a href="#overview-responsive-images">响应式图片</a></li>
                                <li class=""><a href="#overview-type-links">排版和链接</a></li>
                                <li class=""><a href="#overview-normalize">Normalize</a></li>
                                <li class=""><a href="#overview-container">Containers</a></li>
                            </ul>
                        </li>

                        <li class="active">
                            <a href="#type">排版</a>
                            <ul class="nav">
                                <li class=""><a href="#type-headings">标题</a></li>
                                <li><a href="#type-body-copy">页面主体</a></li>
                                <li><a href="#type-emphasis">强调</a></li>
                                <li><a href="#type-abbreviations">缩略语</a></li>
                                <li><a href="#type-addresses">地址</a></li>
                                <li><a href="#type-blockquotes">引用</a></li>
                                <li><a href="#type-lists">列表</a></li>
                            </ul>
                        </li>

                    <%-------------------------%>
                </ul>
            </div>
        </div>

        <div class="col-md-9" role="main">
            <%-------  .........  -------%>

            <div class="bs-docs-section">
                <div class="page-header">
                    <h1 id="grid">栅格系统</h1>
                </div>
                <p class="lead">
                    Bootstrap内置了一套响应式、移动设备优先的流式栅格系统，随着屏幕设备或视口（viewport）尺寸的增加，
                    系统会自动分为最多12列。它包含了易于使用的
                    <a href="#grid-example-basic">预定义classe</a>，还有强大的<a href="#grid-less">mixin用于生成更具语义的布局</a>。
                </p>

                <h3 id="grid-intro">简介</h3>
                <p>
                    栅格系统用于通过一系列的行（row）与列（column）的组合创建页面布局，你的内容就可以放入创建好的布局中。
                    下面就介绍以下Bootstrap栅格系统的工作原理：
                </p>
                <ul>
                    <li>“行（row）”必须包含在<code>.container</code>中，以便为其赋予合适的排列（aligment）和内补（padding）。</li>
                    <li>使用“行（row）”在水平方向创建一组“列（column）”。</li>
                    <li>你的内容应当放置于“列（column）”内，而且，只有“列（column）”可以作为行（row）”的直接子元素。</li>
                    <li>类似Predefined grid classes like <code>.row</code> and <code>.col-xs-4</code>
                        这些预定义的栅格class可以用来快速创建栅格布局。Bootstrap源码中定义的mixin也可以用来创建语义化的布局。
                    </li>
                    <li>通过设置<code>padding</code>从而创建“列（column）”之间的间隔（gutter）。然后通过为第一和最后一样设置
                        负值的<code>margin</code>从而抵消掉padding的影响。
                    </li>
                    <li>栅格系统中的列是通过指定1到12的值来表示其跨越的范围。例如，三个等宽的列可以使用三个<code>.col-xs-4</code>来创建。</li>
                </ul>
                <p>通过研究案例，可以将这些原理应用到你的代码中。</p>

                <div class="bs-callout bs-callout-info">
                    <h4>栅格与全宽布局Grids and full-width layouts</h4>
                    <p>
                        对于需要占据整个浏览器视口（viewport）的页面，需要将内容区域包裹在一个容器元素内，
                        并且赋予<code>padding: 015px;</code>，为的是抵消掉为<code>.row</code>所设置的
                        <code>margin: 0 -15px;</code>（如果不这样的话，你的页面会左右超出视口15px，页面底部出现横向滚动条）。
                    </p>
                </div>

                <h3 id="grid-media-queries">媒体查询</h3>
                <p>在栅格系统中，我们在LESS文件中使用以下媒体查询（media query）来创建关键的分界点阈值。</p>

                <div class="highlight">
                <pre><code class="language-css">
<span class="comment"><span class="comment">/* Small devices (tablets, 768px and up) */</span></span><span class="at_rule">
<span class="at_rule">@<span class="keyword"><span class="keyword">media</span></span> (min-width: @screen-sm-min)</span></span>{ ... }
                </code></pre>
                </div>

            </div>

            <%-------  .........  -------%>
        </div>

    </div>
</div>

<%--------------------------------------------%>

<footer class="bs-footer" role="contentinfo">
    <div class="container">


        <p>Designed and built with all the love in the world by <a href="http://twitter.com/mdo" target="_blank">@mdo</a> and <a href="http://twitter.com/fat" target="_blank">@fat</a>.</p>
        <p>Code licensed under <a href="http://www.apache.org/licenses/LICENSE-2.0" target="_blank">Apache License v2.0</a>, documentation under <a href="http://creativecommons.org/licenses/by/3.0/">CC BY 3.0</a>.</p>
        <p>Bootstrap中文文档版权归<a href="http://www.bootcss.com/">Bootstrap中文网</a>及<a href="mailto:wangsai@bootcss.com">译者</a>所有。</p><p>
    </p><ul class="footer-links">
        <li>当前版本： v3.0.3</li>
        <li class="muted">·</li>
        <li><a href="http://v2.bootcss.com/">Bootstrap 2.3.2 中文文档</a></li>
        <li class="muted">·</li>
        <li><a href="http://www.bootcss.com">Bootstrap中文网</a></li>
        <li class="muted">·</li>
        <li><a href="http://blog.getbootstrap.com">官方博客</a></li>
        <li class="muted">·</li>
        <li><a href="https://github.com/twbs/bootstrap/issues?state=open">Issues</a></li>
        <li class="muted">·</li>
        <li><a href="https://github.com/twbs/bootstrap/releases">Releases</a></li>
    </ul>
    </div>
</footer>


<%--=============================================================================================--%>

<!-- JS and analytics only. -->
<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="http://cdn.bootcss.com/jquery/1.10.2/jquery.min.js"></script>

<!-- Bootstrap core JS file
  注意：此文件跟随官网最新版本更新，随时会有改变。建议使用下面v3.0.3版本的CDN链接！
 -->
<script src="../dist/js/bootstrap.js"></script>

<!-- Hi，如果你要在自己的网站上引入bootstrap JS文件的话，请使用当前最新版本v3.0.3的CDN链接，页面加载速度会更快！
<script src="http://cdn.bootcss.com/twitter-bootstrap/3.0.3/js/bootstrap.min.js"></script>
-->

<script src="http://cdn.bootcss.com/holder/2.0/holder.min.js"></script>
<script src="http://cdn.bootcss.com/highlight.js/7.3/highlight.min.js"></script>
<script >hljs.initHighlightingOnLoad();</script>

<script src="../docs-assets/js/application.js"></script>



<!-- Analytics
================================================== -->
<script type="text/javascript">
    var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
    document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F3d8e7fc0de8a2a75f2ca3bfe128e6391' type='text/javascript'%3E%3C/script%3E"));
</script>

<%--=============================================================================================--%>
</body>
</html>