<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    <form:form modelAttribute="signupForm" class="col s12" action="/api/signup" method="post"
               id="signupForm">
        <div class="row">
            <!-- userId -->
            <div class="input-field col s7">
                <i class="material-icons prefix">account_circle</i>
                <form:input path="userId" id="userId" name="userId" type="text" placeholder="userId"
                            class="validate" required="required"/>
                <form:errors path="userId" cssClass="helper-text"/>
                <span class="helper-text" id="idCheckMsg" style="display: none"></span>
            </div>

            <!-- password -->
            <div class="input-field col s7">
                <i class="material-icons prefix">lock</i>
                <form:input path="password" id="password" name="password" type="password"
                            placeholder="password" class="validate" required="required"/>
                <form:errors path="password" cssClass="helper-text"/>
            </div>

            <!-- name -->
            <div class="input-field col s7">
                <i class="material-icons prefix">person</i>
                <form:input path="name" id="name" name="name" type="text" placeholder="name"
                            class="validate"/>
                <form:errors path="name" cssClass="helper-text"/>
            </div>

            <!-- address -->
            <div class="input-field col s7">
                <i class="material-icons prefix">home</i>
                <form:input path="address" id="address" name="address" type="text"
                            placeholder="address" class="validate"/>
                <form:errors path="address" cssClass="helper-text"/>
            </div>

            <!-- email -->
            <div class="input-field col s7">
                <i class="material-icons prefix">email</i>
                <form:input path="email" id="email" name="email" type="email" placeholder="email"
                            class="validate"/>
                <form:errors path="email" cssClass="helper-text"/>
            </div>
        </div>

        <button class="btn waves-effect waves-light offset-s1" type="submit" name="action">
            회원가입
            <i class="material-icons right">send</i>
        </button>
    </form:form>
</main>

<%@include file="/WEB-INF/view/include/footer.jsp" %>

<script src="${context}/assets/js/signup.js"></script>

</body>
</html>