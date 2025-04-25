<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>장바구니</title>
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
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${cart.items}" var="item">
                <tr>
                    <td>
                        <img src="${item.thumbnailUrl}" alt="${item.productName}" style="width: 50px; height: 50px; margin-right: 10px;"/>
                        ${item.productName}
                    </td>
                    <td>${item.productPrice}원</td>
                    <td>${item.productCount}개</td>
                    <td>${item.productPrice * item.productCount}원</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
    <div class="total">
        총 금액: ${cart.totalPrice}원
    </div>
</body>
</html> 