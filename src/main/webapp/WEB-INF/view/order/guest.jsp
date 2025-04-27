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
    <h4>비회원 주문 조회</h4>

    <div class="section">
        <p>아래에서 내 주문 내역을 확인하실 수 있습니다.</p>
    </div>
    <div class="section">
        <form id="guestOrderForm">
            <div class="input-field">
                <input type="email" id="guestEmail" name="email" required>
                <label for="guestEmail">이메일 주소</label>
            </div>
            <button type="submit" class="btn">조회하기</button>
        </form>
    </div>
    <div class="section" id="orderSection"></div>
</main>
<script src="${context}/assets/js/guest-order.js"></script>

<%@include file="/WEB-INF/view/include/footer.jsp" %>
</body>
</html>
