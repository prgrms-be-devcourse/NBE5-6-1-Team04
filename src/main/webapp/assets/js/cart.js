const cart = {};

function addToCart(productId, productCount) {
  const count = parseInt(productCount, 10);

  if (!cart[productId]) {
    cart[productId] = {
      productId,
      quantity: count,
    };
  } else {
    cart[productId].quantity += count;
  }

  console.log('현재 장바구니:', cart);
}

function removeToCart(productId, productCount) {
  if (!cart[productId]) {
    return;
  }

  const count = parseInt(productCount, 10);

  cart[productId].quantity -= count;

  if (cart[productId].quantity <= 0) {
    delete cart[productId];
  }

  console.log('현재 장바구니:', cart);
}