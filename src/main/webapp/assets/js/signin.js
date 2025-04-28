document.addEventListener('DOMContentLoaded', function () {
  const form = document.getElementById('signinForm');

  form.addEventListener('submit', function (event) {
    event.preventDefault();

    const userId = document.getElementById('userId').value.trim();
    const password = document.getElementById('password').value.trim();

    if (!userId) {
      alert('아이디를 입력해주세요.');
      return;
    }
    console.log('로그인 정보', userId, password);

    fetch('/api/signin', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({userId, password})
    })
    .then(response => {
      if (!response.ok) {
        throw new Error('로그인 실패');
      }
      return response.json();
    })
    .then(data => {
      console.log('로그인 성공:', data);
      console.log('로그인 성공 roles:', data?.roles);
      alert('로그인 성공');
      if (data?.roles[0] === "ROLE_ADMIN") {
        window.location.href = '/admin';
      } else {
        window.location.href = '/';
      }

    })
    .catch(error => {
      console.error('에러 발생:', error);
      alert('로그인 실패');
    });
  });
});
