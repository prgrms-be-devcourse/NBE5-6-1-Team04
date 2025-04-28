<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/view/include/page.jsp" %>
<html>
<head>
    <title>Coffee-shop</title>
    <%@include file="/WEB-INF/view/include/static.jsp" %>
</head>
<body>
<%@include file="/WEB-INF/view/include/header.jsp" %>
<%@include file="/WEB-INF/view/include/sidenav.jsp" %>
<main class="container">
    <div class="section">
        <c:if test="${not empty param.error}">
            <div class="card-panel red lighten-2 text-white">이메일을 입력해주세요</div>
        </c:if>

        <form id="guestSigninForm" class="col s12">
            <div class="row">
                <div class="input-field col s7">
                    <i class="material-icons prefix">account_circle</i>
                    <input id="email" name="email" type="email" placeholder="이메일 입력" class="validate" required/>
                </div>
            </div>
            <button class="btn waves-effect waves-light offset-s1" type="submit" name="action">
                로그인
                <i class="material-icons right">send</i>
            </button>
        </form>
    </div>
</main>
<%@include file="/WEB-INF/view/include/footer.jsp" %>
<script src="${context}/assets/js/guest-signin.js"></script>

</body>
</html>