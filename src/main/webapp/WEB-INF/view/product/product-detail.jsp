<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, java.text.DecimalFormat" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    // DB에서 데이터를 조회해야합니다.
    Map<String, Object> product = new HashMap<>();
    product.put("id", 1);
    product.put("name", "에티오피아 예가체프");
    product.put("price", 12000);
    product.put("description", "과일향과 산미가 풍부한 커피입니다.");
    product.put("stock", 120);
    product.put("image", "coffee_01.jpg");
    request.setAttribute("product", product);
%>

<html>
<head>
    <title>${product.name} 상세</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/product.css">
</head>
<body>
<div class="product-detail">
    <h2>${product.name}</h2>
    <img src="${pageContext.request.contextPath}/uploads/${product.image}" alt="${product.name}"
         class="product-img"/>
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
