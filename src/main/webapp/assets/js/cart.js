const LOCALSTORAGE_KEY = 'cart';

function getCart() {
  const savedCart = localStorage.getItem(LOCALSTORAGE_KEY);
  return savedCart ? JSON.parse(savedCart) : {};
}

function updateCart(cart) {
  localStorage.setItem(LOCALSTORAGE_KEY, JSON.stringify(cart));
}

function clearCart() {
  localStorage.removeItem(LOCALSTORAGE_KEY);
}

function addToCart(productId, productName, productPrice, productCount) {
  const cart = getCart();
  const count = parseInt(productCount, 10);

  if (!cart[productId]) {
    cart[productId] = {
      productId,
      productName,
      productPrice,
      productCount: count,
    };
  } else {
    cart[productId].productCount += count;
  }

  updateCart(cart);
  console.log('현재 장바구니:', cart);
}

function removeToCart(productId, productCount) {
  const cart = getCart();
  if (!cart[productId]) {
    return;
  }

  const count = parseInt(productCount, 10);

  cart[productId].productCount -= count;

  if (cart[productId].productCount <= 0) {
    delete cart[productId];
  }

  updateCart(cart);
  console.log('현재 장바구니:', cart);
}