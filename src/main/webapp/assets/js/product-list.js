document.addEventListener("DOMContentLoaded", function () {
  fetchProducts(1);
});

function fetchProducts(pageNum) {
  fetch('/api/products')
  .then(response => response.json())
  .then(data => {
    renderProducts(data, pageNum);
    renderPagination(data.length, pageNum);
  })
  .catch(error => console.error('상품 불러오기 실패:', error));
}

function renderProducts(productList, pageNum) {
  const productListDiv = document.getElementById('product-list');
  productListDiv.innerHTML = '';

  const productsPerPage = 12;
  const startIndex = (pageNum - 1) * productsPerPage;
  const endIndex = Math.min(startIndex + productsPerPage, productList.length);

  for (let i = startIndex; i < endIndex; i++) {
    const product = productList[i];

    const itemHtml = `
      <div class="product-item">
        <a href="/product/detail?id=${product.productId}">
            <img src="data:image/jpg;base64,${product.imageBase64}" class="product-img" alt="${product.productName}" />
        </a>
        <div class="product-info">
          <h5 class="product-name">${product.productName}</h5>
          <p class="product-price">${formatPrice(product.price)}원</p>
          <div class="product-buttons">
            <button class="buy-now"
              data-product-id="${product.productId}"
              data-product-name="${product.productName}"
              data-product-price="${product.price}"
              data-product-count="1"
              onclick="directOrder(this)">
              구매하기
            </button>
            <button class="add-to-cart-icon"
              data-product-id="${product.productId}"
              data-product-name="${product.productName}"
              data-product-price="${product.price}"
              data-product-count="1"
              onclick="addCartClick(this)">
              <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
                viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2"
                stroke-linecap="round" stroke-linejoin="round"
                class="feather feather-shopping-cart">
                <circle cx="9" cy="21" r="1"/>
                <circle cx="20" cy="21" r="1"/>
                <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"/>
              </svg>
            </button>
          </div>
        </div>
      </div>
    `;

    productListDiv.insertAdjacentHTML('beforeend', itemHtml);
  }
}

function renderPagination(totalProducts, currentPage) {
  const paginationDiv = document.getElementById('pagination');
  paginationDiv.innerHTML = '';

  const productsPerPage = 12;
  const totalPages = Math.ceil(totalProducts / productsPerPage);

  for (let i = 1; i <= totalPages; i++) {
    const pageButton = document.createElement('a');
    pageButton.href = '#';
    pageButton.className = `pagination-btn ${i === currentPage ? 'selected' : ''}`;
    pageButton.textContent = i;
    pageButton.addEventListener('click', function (e) {
      e.preventDefault();
      fetchProducts(i);
    });
    paginationDiv.appendChild(pageButton);
  }
}

function formatPrice(price) {
  return price.toLocaleString();
}
