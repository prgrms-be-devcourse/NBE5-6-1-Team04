<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, java.util.HashMap" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/product.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/pagenation.css" />

<div class="product-list">
    <c:forEach var="product" items="${requestScope.productList}" varStatus="status">
        <c:if test="${status.index >= startIndex && status.index < endIndex}">
            <div class="product-item">
                    <%--                <img src="${pageContext.request.contextPath}${imagePath}/${product.image}"--%>
                    <%--                     alt="${product.name}" class="product-img" />--%>
                        <a href="${pageContext.request.contextPath}/api/products/${product.productId}">
                            <c:choose>
                                <c:when test="${not empty product.imageBase64}">
                                    <img src="data:image/jpg;base64,${product.imageBase64}" class="product-img" alt="${product.productName}"/>
                                </c:when>
                                <c:otherwise>
                                    <img src="${pageContext.request.contextPath}/assets/img/default.jpg" class="product-img"/>
                                </c:otherwise>
                            </c:choose>


                        </a>
                <div class="product-info">
                    <h5 class="product-name">${product.productName}</h5>
                    <p class="product-price"><fmt:formatNumber value="${product.price}"
                                                               type="number"
                                                               groupingUsed="true"/>원</p>
                    <div class="product-buttons">
                        <button class="buy-now">구매하기</button>
                        <button class="add-to-cart-icon">
                            <!-- 장바구니 SVG 아이콘 -->
                            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
                                 viewBox="0 0 24 24"
                                 fill="none" stroke="white" stroke-width="2" stroke-linecap="round"
                                 stroke-linejoin="round" class="feather feather-shopping-cart">
                                <circle cx="9" cy="21" r="1"/>
                                <circle cx="20" cy="21" r="1"/>
                                <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"/>
                            </svg>
                        </button>
                    </div>
                </div>

            </div>
        </c:if>
    </c:forEach>
</div>
<div class="pagination-container center-align">
    <c:forEach begin="1"
               end="${(productList.size() / productsPerPage) + (productList.size() % productsPerPage == 0 ? 0 : 1)}"
               var="i">
        <a href="?page=${i}" class="pagination-btn ${i == pageNum ? 'selected' : ''}">
                ${i}
        </a>
    </c:forEach>
</div>

