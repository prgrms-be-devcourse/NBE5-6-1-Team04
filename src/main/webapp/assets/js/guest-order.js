document.addEventListener('DOMContentLoaded', function () {
  const form = document.getElementById('guestOrderForm');
  const orderSection = document.getElementById('orderSection'); // 주문 목록 들어갈 곳

  form.addEventListener('submit', function (event) {
    event.preventDefault();

    const email = document.getElementById('guestEmail').value.trim();

    if (!email) {
      alert('이메일을 입력해주세요.');
      return;
    }

    fetch(`/api/orders?email=${encodeURIComponent(email)}`)
        .then(response => {
          if (!response.ok) {
            throw new Error('주문 조회 실패');
          }
          return response.json();
        })
        .then(data => {
            console.log(data)
          if (data.code === '0000' && data.data.length > 0) {
            renderOrders(data.data);
          } else {
            orderSection.innerHTML = '<p>조회된 주문이 없습니다.</p>';
          }
        })
        .catch(error => {
          console.error('에러 발생:', error);
          orderSection.innerHTML = '<p>주문 조회 중 오류가 발생했습니다.</p>';
        });
  });

  function renderOrders(orders) {
    let html = `
            <h3>주문 목록</h3>
            <table class="striped">
                <thead>
                    <tr>
                        <th>주문번호</th>
                        <th>총 금액</th>
                        <th>배송 상태</th>
                        <th>배송지</th>
                    </tr>
                </thead>
                <tbody>
        `;

    orders.forEach(order => {
      html += `
                <tr>
                    <td>${order.orderId}</td>
                    <td>${Number(order.totalPrice).toLocaleString()}원</td>
                    <td>${order.orderStatus}</td>
                    <td>${order.orderAddress}</td>
                </tr>
            `;
    });

    html += `
                </tbody>
            </table>
        `;

    orderSection.innerHTML = html;
  }
});
