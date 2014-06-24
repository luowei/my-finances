<%--
  Created by IntelliJ IDEA.
  User: luowei
  Date: 14-4-6
  Time: 上午5:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url var="first" value="1"/>
<c:url var="last" value="${totalPages}"/>
<c:url var="prev" value="${pageNumber - 1}"/>
<c:url var="next" value="${pageNumber + 1}"/>

<script type="text/javascript">
    function subform(pageNumber){
//        $('form:eq(0)').attr("action",url).submit();
        $('input[name=pageNumber]').val(pageNumber);
        $('form[name=searchForm]').submit();
    }

</script>

<div class="wrapper">
    <ul class="pagination" style="margin-bottom: 10px;margin-top: 5px;">
        <c:choose>
            <c:when test="${pageNumber == 1}">
                <li class="disabled"><a href="javascript:void(0)">&lt;&lt;</a></li>
                <li class="disabled"><a href="javascript:void(0)">&lt;</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="javascript:void(0)" onclick="subform('${first}')">&lt;&lt;</a></li>
                <li><a href="javascript:void(0)" onclick="subform('${prev}')">&lt;</a></li>
            </c:otherwise>
        </c:choose>
        <c:forEach var="i" begin="${beginIndex}" end="${endIndex}">
            <c:url var="pageUrl" value="${contextUrl}?pageNumber=${i}"/>
            <c:choose>
                <c:when test="${i == pageNumber}">
                    <li class="active"><a href="javascript:void(0)"><c:out value="${i}"/></a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="javascript:void(0)" onclick="subform('${i}')"><c:out value="${i}"/></a></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:choose>
            <c:when test="${pageNumber == totalPages}">
                <li class="disabled"><a href="javascript:void(0)">&gt;</a></li>
                <li class="disabled"><a href="javascript:void(0)">&gt;&gt;</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="javascript:void(0)" onclick="subform('${next}')">&gt;</a></li>
                <li><a href="javascript:void(0)" onclick="subform('${last}')">&gt;&gt;</a></li>
            </c:otherwise>
        </c:choose>
    </ul>
    <ul style="margin-bottom: 10px;">
        当前页:${pageNumber}&nbsp;&nbsp;总页数:${totalPages}&nbsp;&nbsp;总记录数:${totalElements}
    </ul>
</div>
