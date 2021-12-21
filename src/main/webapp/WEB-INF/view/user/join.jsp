<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="/res/css/user/join.css?ver">
<div class="join">
    <div>
    <form action="/user/join" method="post" id="frm" onsubmit="return joinChk();">
        <div>아이디*</div>
        <vr><input type="text" name="uid" placeholder="5~20자 사이로 작성해 주세요." required></vr>

        <div>비밀번호*<br><input type="password" name="upw" placeholder="비밀번호를 5자 이상 작성해 주세요." required></div>

        <div>비밀번호 확인*<br><input type="password" id="reupw" placeholder="password comfirm" required></div>

        <div>이름*<br><input type="text" name="nm" placeholder="이름을 입력해 주세요." required></div>

        <div>
            <label>female <input type="radio" value="2" name="gender" checked></label>
            <label>male <input type="radio" value="1" name="gender"></label>
        </div>
        <div>
            <input type="submit" value="회원가입">
            <input type="reset" value="초기화">
        </div>
    </form>
    </div>
</div>
<script src="/res/js/user/join.js"></script>