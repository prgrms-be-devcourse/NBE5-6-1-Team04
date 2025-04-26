<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/view/include/page.jsp" %>
<html>
<head>
    <title>주문서 작성</title>
    <%@include file="/WEB-INF/view/include/static.jsp" %>
</head>
<body>

<h1>주문서 작성</h1>

<div class="order-container">
    <!-- 좌측 : 상품 목록 -->
    <div class="order-items">
        <table style="width:100%; border-collapse: collapse;">
            <thead>
            <tr>
                <th style="border: 1px solid #ddd; padding: 8px;">상품명</th>
                <th style="border: 1px solid #ddd; padding: 8px;">가격</th>
                <th style="border: 1px solid #ddd; padding: 8px;">수량</th>
                <th style="border: 1px solid #ddd; padding: 8px;">소계</th>
            </tr>
            </thead>
            <tbody id="order-body">
            <!-- JS로 상품 목록 세팅 -->
            </tbody>
        </table>
    </div>

    <!-- 우측 : 주문 정보 입력 -->
    <div class="order-summary">
        <label>받는 사람 이름</label>
        <input type="text" id="receiver-name" placeholder="이름 입력">

        <label>배송지</label>
        <input type="text" id="receiver-address" placeholder="주소 입력">

        <div class="total" id="order-total-price">
            총 금액: 0원
        </div>

        <button class="buy-now" onclick="submitOrder()">결제하기</button>
    </div>
</div>

<script src="${context}/assets/js/order.js"></script>
<script src="${context}/assets/js/cart.js"></script>

</body>
</html>
