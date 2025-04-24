<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, java.util.HashMap" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    // 상품 목록 생성 (DB 데이터 연결 전 목업용)
    Random rand = new Random();
    List<Map<String, Object>> productList = new ArrayList<>();
    for (int i = 1; i <= 50; i++) {
        Map<String, Object> product = new HashMap<>();
        product.put("id", i); // DB 테이블과 매칭
        product.put("name", "커피 상품 " + i);
        product.put("price", 10000 + i * 100);
        product.put("description", "이 커피는 매우 특별합니다 #" + i);
        product.put("stock", 100);
        product.put("image", String.format("coffee_%02d.jpg", rand.nextInt(10) + 1));
        productList.add(product);
    }
    request.setAttribute("productList", productList);

    int productsPerPage = 12;
    int pageNum =
            request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page"))
                    : 1;
    int startIndex = (pageNum - 1) * productsPerPage;
    int endIndex = Math.min(startIndex + productsPerPage, productList.size());

    request.setAttribute("startIndex", startIndex);
    request.setAttribute("endIndex", endIndex);
    request.setAttribute("productsPerPage", productsPerPage);
%>

<div class="product-list">
    <c:forEach var="product" items="${productList}" varStatus="status">
        <c:if test="${status.index >= startIndex && status.index < endIndex}">
            <div class="product-item">
                    <%--                <img src="${pageContext.request.contextPath}${imagePath}/${product.image}"--%>
                    <%--                     alt="${product.name}" class="product-img" />--%>
                <a href="${pageContext.request.contextPath}/product/detail?id=${product.id}">
                    <img src="${pageContext.request.contextPath}/uploads/${product.image}"
                         alt="${product.name}" class="product-img"/>
                </a>
                <div class="product-info">
                    <h5 class="product-name">${product.name}</h5>
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
        <a href="?page=${i}" class="pagination-btn ${i == pageNum ? 'selected' : ''}">${i}</a>
    </c:forEach>
</div>
