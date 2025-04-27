// assets/js/header.js
document.addEventListener('DOMContentLoaded', async () => {
  const navMobile = document.querySelector('#nav-mobile');
  if (!navMobile) return;

  try {
    const res = await fetch('/api/session-info', {
      method: 'GET',
      credentials: 'same-origin'
    });
    console.log('/api/session-info →', res.status);

    if (res.ok) {
      const { userId, role } = await res.json();
      console.log('asdasdasd', res);
      console.log('세션 정보:', { userId, role });

      if (role === 'ROLE_ADMIN') {
        // 관리자 메뉴
        navMobile.innerHTML = `
          <li><span>관리자님 환영합니다</span></li>
          <li><a href="/admin/dashboard" class="grey-text">관리자 페이지</a></li>
          <li><a href="/logout" class="grey-text">로그아웃</a></li>
        `;
      }
      else if (role === 'ROLE_GUEST' || !role ) {
        // 비회원 메뉴
        navMobile.innerHTML = `
          <li><span>비회원님 환영합니다</span></li>
          <li><a href="/orders/guest" class="grey-text">비회원 주문 확인</a></li>
          <li><a href="/logout" class="grey-text">로그아웃</a></li>
                          <li>
                    <a href="/cart">
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
                             viewBox="0 0 24 24"
                             fill="none" stroke="black" stroke-width="2" stroke-linecap="round"
                             stroke-linejoin="round" class="feather feather-shopping-cart">
                            <circle cx="9" cy="21" r="1"/>
                            <circle cx="20" cy="21" r="1"/>
                            <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"/>
                        </svg>
                    </a>
                </li>
        `;
      }
      else {
        // 일반 회원 메뉴 (ROLE_CUSTOMER)
        navMobile.innerHTML = `
          <li><span>환영합니다, ${userId}님</span></li>
          <li><a href="/my-page/${userId}" class="grey-text">마이페이지</a></li>
          <li><a href="/cart" class="grey-text">장바구니</a></li>
          <li><a href="/logout" class="grey-text">로그아웃</a></li>
                          <li>
                    <a href="/cart">
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
                             viewBox="0 0 24 24"
                             fill="none" stroke="black" stroke-width="2" stroke-linecap="round"
                             stroke-linejoin="round" class="feather feather-shopping-cart">
                            <circle cx="9" cy="21" r="1"/>
                            <circle cx="20" cy="21" r="1"/>
                            <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"/>
                        </svg>
                    </a>
                </li>
        `;
      }
    } else {
      // 로그인 안 된 상태
      console.log('비로그인 →', res.status);
      navMobile.innerHTML = `
        <li><a href="/signin" class="grey-text">sign in</a></li>
        <li><a href="/signup" class="grey-text">sign up</a></li>
                        <li>
                    <a href="/cart">
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
                             viewBox="0 0 24 24"
                             fill="none" stroke="black" stroke-width="2" stroke-linecap="round"
                             stroke-linejoin="round" class="feather feather-shopping-cart">
                            <circle cx="9" cy="21" r="1"/>
                            <circle cx="20" cy="21" r="1"/>
                            <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"/>
                        </svg>
                    </a>
                </li>
      `;
    }
  } catch (err) {
    console.error('세션 조회 중 오류:', err);
    // 네트워크 오류 시에도 비회원 UI
    navMobile.innerHTML = `
      <li><a href="/signin" class="grey-text">sign in</a></li>
      <li><a href="/signup" class="grey-text">sign up</a></li>
                      <li>
                    <a href="/cart">
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
                             viewBox="0 0 24 24"
                             fill="none" stroke="black" stroke-width="2" stroke-linecap="round"
                             stroke-linejoin="round" class="feather feather-shopping-cart">
                            <circle cx="9" cy="21" r="1"/>
                            <circle cx="20" cy="21" r="1"/>
                            <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"/>
                        </svg>
                    </a>
                </li>
    `;
  }
});
