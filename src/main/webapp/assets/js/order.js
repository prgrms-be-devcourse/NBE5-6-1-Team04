document.addEventListener('DOMContentLoaded', function () {
  const cart = getCart();
  const orderBody = document.getElementById('order-body');
  const totalDiv = document.getElementById('order-total-price');

  orderBody.innerHTML = '';
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
    td4.textContent = subtotal.toLocaleString() + '원';

    row.appendChild(td1);
    row.appendChild(td2);
    row.appendChild(td3);
    row.appendChild(td4);

    orderBody.appendChild(row);

    totalPrice += subtotal;
  }

  totalDiv.textContent = '총 금액: ' + totalPrice.toLocaleString() + '원';
});

async function submitOrder() {
  const receiverName = document.getElementById('receiver-name').value.trim();
  const receiverAddress = document.getElementById(
      'receiver-address').value.trim();
  const cart = getCart();
  console.log("결제 상품들", cart);
  console.log("결제 정보", receiverName, receiverAddress);

  if (!receiverName || !receiverAddress) {
    alert('받는 사람 이름과 배송지를 입력해주세요.');
    return;
  }

  const orderItems = [];
  let totalPrice = 0;

  for (const id in cart) {
    const item = cart[id];
    orderItems.push({
      orderItemId: null,
      orderId: null,
      productId: Number(item.productId),
      orderCount: Number(item.productCount),
      productName: item.productName,
      productPrice: Number(item.productPrice)
    });
    totalPrice += Number(item.productPrice) * Number(item.productCount);
  }

  const res = await fetch('/api/session-info', {
    method: 'GET',
    credentials: 'same-origin'
  });
  if (res.ok) {
    const {userId, role} = await res.json();
    let formData = {}
    if (role === 'ROLE_GUEST' || !role) {
      formData = {
        userId: null,
        email: userId,
        orderItems: orderItems,
        totalPrice: totalPrice,
        orderAddress: receiverAddress
      };
    } else if (role === 'ROLE_CUSTOMER') {
      formData = {
        userId: userId,
        email: null,
        orderItems: orderItems,
        totalPrice: totalPrice,
        orderAddress: receiverAddress
      };
    }


    try {
      const response = await fetch('/api/orders', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData),
        credentials: "include"
      });

      if (response.ok) {
        alert('주문이 완료되었습니다.');
        clearCart();
        window.location.href = '/';
      } else {
        alert('주문 실패: ' + await response.text());
      }
    } catch (error) {
      console.error('주문 에러', error);
      alert('주문 중 오류가 발생했습니다.');
    }

  }

}