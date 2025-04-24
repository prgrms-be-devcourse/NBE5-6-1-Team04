<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, java.util.HashMap" %>
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
                        <img src="${pageContext.request.contextPath}/uploads/${product.image}"
                             alt="${product.name}" class="product-img" />
                <div class="product-info">
                    <h5 class="product-name">${product.name}</h5>
                    <p class="product-price">${product.price}원</p>
                    <button class="add-to-cart">장바구니 담기</button>
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
