<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, java.text.DecimalFormat" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/product.css" />

<html>
<head>
    <title>${product.productName} 상세</title>

</head>
<body>
<div class="product-detail">

    <h2>${product.productName}</h2>
    <c:choose>
        <c:when test="${not empty product.imageBase64}">
            <img src="data:image/jpg;base64,${product.imageBase64}" class="product-img" alt="${product.productName}" />
        </c:when>

    </c:choose>




    <p class="product-price">
        <fmt:formatNumber value="${product.price}" type="number" groupingUsed="true"/>원
    </p>
    <p class="product-description">${product.description}</p>
    <p class="product-stock">재고: ${product.stock}개</p>

    <div class="product-buttons">
        <button class="buy-now">구매하기</button>
        <button class="add-to-cart-icon">🛒</button>
    </div>
</div>
</body>
</html>
