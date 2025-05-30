<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/view/include/page.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Coffee-shop</title>
    <%@include file="/WEB-INF/view/include/static.jsp" %>
</head>
<body>
<%@include file="/WEB-INF/view/include/header.jsp" %>
<%@include file="/WEB-INF/view/include/sidenav.jsp" %>
<main class="container">
    <c:if test="${not empty param.error}">
        <div class="card-panel red lighten-2 text-white">${param.error}</div>
    </c:if>
    <%@include file="/WEB-INF/view/product/product-list.jsp" %>
</main>
<%@include file="/WEB-INF/view/include/footer.jsp" %>
<script src="${context}/assets/js/cart.js"></script>
</body>
</html>