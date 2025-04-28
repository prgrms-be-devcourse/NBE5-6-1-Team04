function addCartClick(button) {
  const isConfirm = confirm("장바구니에 상품을 담으시겠습니까?")
  if (!isConfirm) {
    return;
  }
  const {productId, productName, productPrice, productCount} = button.dataset;
  addToCart(productId, productName, productPrice, productCount);
}

async function directOrder(button) {
  const isConfirm = confirm("구매 하시겠습니까?");
  if (!isConfirm) {
    return;
  }
  try {
    const res = await fetch('/api/session-info', {
      method: 'GET',
      credentials: 'same-origin'
    });
    if (res.ok) {
      const {userId, role} = await res.json();
      console.log('세션 정보:', {userId, role});
      const {
        productId,
        productName,
        productPrice,
        productCount
      } = button.dataset;

      addToCart(productId, productName, productPrice, productCount);
      window.location.href = '/orders';
    } else {
      alert('로그인 해주세요')
      window.location.href = '/signin';
    }
  } catch (_) {
    alert('로그인 정보가 없습니다.')
    window.location.href = '/signin';
  }

}