function addCartClick(button) {
  const result = confirm("장바구니에 상품을 담으시겠습니까?")
  if (result) {
    const {productId, productCount} = button.dataset;
    addToCart(productId, productCount);
  }
}