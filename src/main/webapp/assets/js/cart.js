const LOCALSTORAGE_KEY = 'cart';

function getCart() {
  const savedCart = localStorage.getItem(LOCALSTORAGE_KEY);
  // TODO: 사용자가 로그인 했다면 GET /api/cart 필요
  return savedCart ? JSON.parse(savedCart) : {};
}

function updateCart(cart) {
  localStorage.setItem(LOCALSTORAGE_KEY, JSON.stringify(cart));
  // TODO: 사용자가 로그인 했다면 POST /api/cart 필요
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

function removeCartItem(productId, productCount) {
  const cart = getCart();
  if (!cart[productId]) {
    return;
  }

  cart[productId].productCount -= 1;

  if (cart[productId].productCount <= 0) {
    delete cart[productId];
  }

  updateCart(cart);
  console.log('현재 장바구니:', cart);
}

async function goToOrderPage() {
  const isConfirmed = confirm("결제 하시겠습니까?");
  if (!isConfirmed) {
    return;
  }
  window.location.href = '/orders';
}