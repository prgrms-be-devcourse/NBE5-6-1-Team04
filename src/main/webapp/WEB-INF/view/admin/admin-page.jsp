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
    <h4>관리자 페이지</h4>

    <div class="section">
        <p>아래에서 전체 주문 내역을 확인하실 수 있습니다.</p>
    </div>

    <div class="section">
        <%@include file="/WEB-INF/view/admin/order-list.jsp" %>
    </div>
</main>

<%@include file="/WEB-INF/view/include/footer.jsp" %>
</body>
</html>
