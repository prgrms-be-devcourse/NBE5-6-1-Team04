<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page language="java" %>
<header class="header">
    <nav class="navbar white">

        <div class="nav-wrapper ">
            <a href="/" class="brand-logo grey-text">Grepp</a>
            <ul id="nav-mobile" class="right hide-on-med-and-down grey-text">
                <li><a href="/signin" class="grey-text">sign in</a></li>
                <li><a href="/signup" class="grey-text">sign up</a></li>
                <li><a href="/orders/guest" class="grey-text">비회원 주문 확인</a></li>
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
                <li>
                    <a href="mobile.html">
                        <i class="material-icons grey-text sidenav-trigger"
                           data-target="slide-out">more_vert</i>
                    </a>
                </li>
            </ul>
        </div>
    </nav>
</header>
<script src="${context}/assets/js/header.js"></script>