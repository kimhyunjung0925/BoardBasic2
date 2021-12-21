<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="wrap">
    <div>
        <form action="/user/login" method="post" id="frm">
            <div><input type="text" name="uid" placeholder="id" value="microform"></div>
            <div><input type="password" name="upw" placeholder="password" value="121212"></div>
            <div><input type="button" value="비밀번호 보이기" id="btnShowPw"></div>
            <div><input type="submit" value="login"></div>
        </form>
        <div>
            <a class="a" href="/user/join">회원가입 하러가기</a>
        </div>
    </div>
</div>
<script src="/res/js/user/login.js?ver=3"></script>