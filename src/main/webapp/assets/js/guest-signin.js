document.addEventListener('DOMContentLoaded', function () {
  const form = document.getElementById('guestSigninForm');

  form.addEventListener('submit', function (event) {
    event.preventDefault();

    const email = document.getElementById('email').value.trim();

    if (!email) {
      alert('이메일을 입력해주세요.');
      return;
    }

    fetch('/api/guest-signin', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ email: email })
    })
    .then(response => {
      if (!response.ok) {
        throw new Error('로그인 실패');
      }
      return response.json();
    })
    .then(data => {
      console.log('로그인 성공:', data);
      alert('로그인 성공');
      window.location.href = '/';
    })
    .catch(error => {
      console.error('에러 발생:', error);
      alert('로그인 실패');
    });
  });
});
