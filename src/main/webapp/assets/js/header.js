// assets/js/header.js
document.addEventListener('DOMContentLoaded', async () => {
  const navMobile = document.querySelector('#nav-mobile');
  if (!navMobile) {
    return;
  }

  try {
    const res = await fetch('/api/session-info', {
      method: 'GET',
      credentials: 'same-origin'
    });
    console.log('[header.js] ←  /api/session-info 응답 상태:', res.status);

    if (res.ok) {
      const data = await res.json();
      console.log('✅ 세션 정보:', data);
      const { userId, roles } = data;

      navMobile.innerHTML = `
        <li><span>환영합니다, ${userId}님</span></li>
        <li><a href="/logout" class="grey-text">로그아웃</a></li>
        <li><a href="/my-page/${userId}" class="grey-text">마이페이지</a></li>
        <li><a href="/cart" class="grey-text">
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24"
               fill="none" stroke="black" stroke-width="2" stroke-linecap="round"
               stroke-linejoin="round" class="feather feather-shopping-cart">
            <circle cx="9" cy="21" r="1"/>
            <circle cx="20" cy="21" r="1"/>
            <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72
                     a2 2 0 0 0 2-1.61L23 6H6"/>
          </svg>
        </a></li>
      `;
    } else {
      console.log('로그인되지 않은 상태이거나 세션 만료:', res.status);
      navMobile.innerHTML = `
        <li><a href="/signin" class="grey-text">sign in</a></li>
        <li><a href="/signup" class="grey-text">sign up</a></li>
        <li><a href="/orders/guest" class="grey-text">비회원 주문 확인</a></li>
      `;
    }
  } catch (err) {
    console.error('세션 정보 조회 중 오류 발생:', err);
    // 네트워크 오류 시에도 비로그인 메뉴로
    navMobile.innerHTML = `
      <li><a href="/signin" class="grey-text">sign in</a></li>
      <li><a href="/signup" class="grey-text">sign up</a></li>
      <li><a href="/orders/guest" class="grey-text">비회원 주문 확인</a></li>
    `;
  }
});
