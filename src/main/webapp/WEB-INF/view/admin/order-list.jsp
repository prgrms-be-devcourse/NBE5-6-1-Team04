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
        <th>상태 변경</th>
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
            <td><fmt:formatNumber value="${order.totalPrice}" type="number" groupingUsed="true"/>원
            </td>
            <td>${order.orderStatus}</td>
            <td>
                <select name="orderStatus" class="browser-default">
                    <c:forEach var="status"
                               items="${['PENDING', 'PAID', 'PROCESSING', 'SHIPPED', 'DELIVERED', 'CANCELED']}">
                        <option value="${status}"
                                <c:if test="${status == order.orderStatus}">selected</c:if>>
                            <c:choose>
                                <c:when test="${status == 'PENDING'}">대기중</c:when>
                                <c:when test="${status == 'PAID'}">결제완료</c:when>
                                <c:when test="${status == 'PROCESSING'}">처리중</c:when>
                                <c:when test="${status == 'SHIPPED'}">배송중</c:when>
                                <c:when test="${status == 'DELIVERED'}">배송완료</c:when>
                                <c:when test="${status == 'CANCELED'}">취소됨</c:when>
                            </c:choose>
                        </option>
                    </c:forEach>
                </select>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
