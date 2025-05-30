document.addEventListener("DOMContentLoaded", () => {
  const signupForm = document.querySelector('#signupForm');
  const userIdInput = document.querySelector('#userId');
  const validElement = document.querySelector('#idCheckMsg');

  userIdInput.addEventListener('input', (e) => {
    e.target.value = e.target.value.replace(/[^a-zA-Z0-9]/g, '');
  });
  const API_PATH ='/api/user';

  userIdInput.addEventListener('focusout', async (e) => {
    const id = e.target.value;
    if (!id) return;
    // TODO: ID 중복 체크 API 필요
    // try {
    //
    //   const response = await fetch(`${API_PATH}/exists/` + id);
    //   const data = await response.json();
    //
    //   validElement.style.display = 'block';
    //   validElement.textContent = data.data
    //       ? '사용이 불가능한 아이디 입니다.'
    //       : '사용 가능한 아이디 입니다.';
    // } catch (error) {
    //   console.error('ID 중복 체크 오류:', error);
    // }
  });

  signupForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const id = userIdInput.value;
    if (!id) return;
    const formData = {
      userId: document.querySelector('#userId').value,
      password: document.querySelector('#password').value,
      name: document.querySelector('#name').value,
      address: document.querySelector('#address').value,
      email: document.querySelector('#email').value,
    };

    try {
      const response = await fetch('/api/signup', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
      });

      if (response.ok) {
        alert('회원가입에 성공했습니다.')
        window.location.href = '/';
      } else {
        alert('회원가입에 실패했습니다.')
      }
    } catch (error) {
      console.error('회원가입 중 오류:', error);
    }

    // e.target.submit();
    // TODO: ID 중복 체크 API 필요
    // try {
    //   const response = await fetch(`${API_PATH}/exists/` + id);
    //   const data = await response.json();
    //
    //   if (data.data) {
    //     userIdInput.focus();
    //     validElement.textContent = '사용이 불가능한 아이디 입니다.';
    //     return;
    //   }
    //
    //   e.target.submit();
    // } catch (error) {
    //   console.error('회원가입 제출 중 오류:', error);
    // }
  });
});
