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
    <c:if test="${not empty param.error}">
        <div class="card-panel red lighten-2 text-white">아이디나 비밀번호를 확인하세요</div>
    </c:if>
    <%--    <form:form modelAttribute="signinForm" class="col s12" action="/api/signin" method="post">--%>
    <form id="signinForm" class="col s12">
        <div class="row">
            <div class="input-field col s7 ">
                <i class="material-icons prefix">account_circle</i>
                <input id="userId" name="userId" type="text" placeholder="아이디 입력" class="validate" required/>
<%--                <form:input path="userId" id="userId" name="userId" type="text" placeholder="userId"--%>
<%--                            class="validate"/>--%>
<%--                <form:errors path="userId" cssClass="helper-text"/>--%>
            </div>
            <div class="input-field col s7 ">
                <i class="material-icons prefix">account_circle</i>
                <input id="password" name="password" type="password" placeholder="패스워드 입력" class="validate" required/>
<%--                <form:input path="password" id="password" name="password" type="password"--%>
<%--                            placeholder="password"--%>
<%--                            class="validate"/>--%>
<%--                <form:errors path="password" cssClass="helper-text"/>--%>
            </div>
        </div>
        <button class="btn waves-effect waves-light offset-s1" type="submit" name="action">
            로그인
            <i class="material-icons right">send</i>
        </button>
        <a class="btn waves-effect waves-light offset-s1" href="/guest-signin"
           style="margin-left: 10px;">
            비회원 로그인
            <i class="material-icons right">send</i>
        </a>
    </form>
    <%--    </form:form>--%>

</main>
<%@include file="/WEB-INF/view/include/footer.jsp" %>
<script src="${context}/assets/js/signin.js"></script>

</body>
</html>