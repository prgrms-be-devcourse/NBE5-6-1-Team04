<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h3>주문 목록</h3>

<table class="striped">
    <thead>
    <tr>
        <th>주문번호</th>
        <th>회원ID/이메일</th>
        <th>총 금액</th>
        <th>상태</th>
        <th>상세 내용</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>${order.orderId}</td>
            <td>
                <c:choose>
                    <c:when test="${not empty order.userId}">${order.userId}</c:when>
                    <c:otherwise>${order.email}</c:otherwise>
                </c:choose>
            </td>
            <td><fmt:formatNumber value="${order.totalPrice}" type="number" groupingUsed="true"/>원</td>
            <td>${order.orderStatus}</td>
            <td><a href="/orders/${order.orderId}">보기</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
