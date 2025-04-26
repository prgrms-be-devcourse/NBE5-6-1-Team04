<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h3>주문 목록</h3>

<table class="striped">
    <thead>
    <tr>
        <th>주문번호</th>
        <th>총 금액</th>
        <th>배송 상태</th>
        <th>배송지</th>
<%--     필요하면 적용   <th>상세 페이지</th>--%>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>${order.orderId}</td>
            <td><fmt:formatNumber value="${order.totalPrice}" type="number" groupingUsed="true"/>원</td>
            <td>${order.orderStatus}</td>
            <td>${order.orderAddress}</td>
<%--            <td><a href="/orders/${order.orderId}">보기</a></td>--%>
        </tr>
    </c:forEach>
    </tbody>
</table>
