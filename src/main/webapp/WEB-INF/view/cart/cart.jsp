<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/WEB-INF/view/include/page.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>장바구니</title>
    <%@include file="/WEB-INF/view/include/static.jsp" %>
    <style>
      table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 20px;
      }

      th, td {
        padding: 10px;
        border: 1px solid #ddd;
        text-align: left;
      }

      th {
        background-color: #f5f5f5;
      }

      .total {
        margin-top: 20px;
        text-align: right;
        font-weight: bold;
      }

      .buy-btn {
        margin-top: 20px;
        padding-left: 25vw;
        padding-right: 25vw;
        align-items: center;
        justify-content: center;
        display: flex;
      }
    </style>
</head>
<body>
<h1>장바구니</h1>

<table>
    <thead>
    <tr>
        <th>상품명</th>
        <th>가격</th>
        <th>수량</th>
        <th>소계</th>
        <th></th>
    </tr>
    </thead>
    <tbody id="cart-body">
    </tbody>
</table>


<div class="buy-btn">
    <button class="buy-now" onclick="goToOrderPage()">구매하기</button>
</div>

<div class="total" id="total-price">
    총 금액: 0원
</div>


<script src="${pageContext.request.contextPath}/assets/js/cart.js"></script>
<script>
  document.addEventListener('DOMContentLoaded', function () {
    const cart = getCart();
    const cartBody = document.getElementById('cart-body');
    const totalDiv = document.getElementById('total-price');

    cartBody.innerHTML = '';
    let totalPrice = 0;

    for (const id in cart) {
      const item = cart[id];
      const productName = item.productName;
      const price = parseInt(item.productPrice, 10);
      const count = parseInt(item.productCount, 10);
      const subtotal = price * count;

      const row = document.createElement('tr');

      const td1 = document.createElement('td');
      td1.textContent = productName;

      const td2 = document.createElement('td');
      td2.textContent = price.toLocaleString() + '원';

      const td3 = document.createElement('td');
      td3.textContent = count.toLocaleString() + '개';

      const td4 = document.createElement('td');
      td4.textContent = subtotal.toLocaleString();

      const td5 = document.createElement('td');
      const deleteButton = document.createElement('button');
      deleteButton.textContent = '삭제';
      deleteButton.className = 'delete-btn';
      deleteButton.onclick = function() {
        removeCartItem(item.productId);
        location.reload();
      };
      td5.appendChild(deleteButton);

      row.appendChild(td1);
      row.appendChild(td2);
      row.appendChild(td3);
      row.appendChild(td4);
      row.appendChild(td5);

      cartBody.appendChild(row);

      totalPrice += subtotal;
    }

    totalDiv.textContent = '총 금액: ' + totalPrice.toLocaleString() + '원';
  });
</script>
</body>
</html>