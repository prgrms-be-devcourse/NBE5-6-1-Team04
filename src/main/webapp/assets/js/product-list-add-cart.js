function addCartClick(button) {
  const isConfirm = confirm("장바구니에 상품을 담으시겠습니까?")
  if (!isConfirm) {
    return;
  }
  const {productId, productName, productPrice, productCount} = button.dataset;
  addToCart(productId, productName, productPrice, productCount);
}

function directOrder(button) {
  const isConfirm = confirm("구매 하시겠습니까?");
  if (!isConfirm) {
    return;
  }
  const {productId, productName, productPrice, productCount} = button.dataset;

  addToCart(productId, productName, productPrice, productCount);
  window.location.href = '/orders';
}