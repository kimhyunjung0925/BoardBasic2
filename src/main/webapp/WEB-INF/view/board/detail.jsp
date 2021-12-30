<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="/res/css/board/detail.css?ver=2">
<div>
    <c:if test="${sessionScope.loginUser.iuser == requestScope.data.writer}">
        <div>
            <a href="/board/del?iboard=${requestScope.data.iboard}">
                <button>삭제</button>
            </a>
            <a href="/board/regmod?iboard=${requestScope.data.iboard}">
                <button>수정</button>
            </a>
        </div>
    </c:if>


    <div>
        ${requestScope.data.heartCount}
        <c:choose>
            <c:when test="${requestScope.isHeart == 1}">
                <c:if test="${sessionScope.loginUser != null}">
                    <a href="/board/heart?proc=2&iboard=${requestScope.data.iboard}">
                </c:if>
                <i class="fas fa-thumbs-up"></i></a>
            </c:when>
            <c:otherwise>
                <c:if test="${sessionScope.loginUser != null && requestScope.isHate != 1}">
                    <a href="/board/heart?proc=1&iboard=${requestScope.data.iboard}">
                </c:if>
                <i class="far fa-thumbs-up"></i></a>
            </c:otherwise>
        </c:choose>

        ${requestScope.data.hateCount}
            <c:choose>
                <c:when test="${requestScope.isHate == 1}">
                    <c:if test="${sessionScope.loginUser != null}">
                        <a href="/board/hate?proc=2&iboard=${requestScope.data.iboard}">
                    </c:if>
                    <i class="fas fa-thumbs-down"></i></a>
                </c:when>
                <c:otherwise>
                    <c:if test="${sessionScope.loginUser != null && requestScope.isHeart != 1}">
                        <a href="/board/hate?proc=1&iboard=${requestScope.data.iboard}">
                    </c:if>
                    <i class="far fa-thumbs-down"></i></a>
                </c:otherwise>
            </c:choose>


    </div>


    <div>글번호: ${requestScope.data.iboard}</div>
    <div>조회수 : <c:out value="${requestScope.data.hit}"/></div>
    <div>작성자 : <c:out value="${requestScope.data.writerNm}"/></div>
    <div>등록일시 : <c:out value="${requestScope.data.rdt}"/></div>
    <div>수정일시 : <c:out value="${requestScope.data.mdt}"/> </div>
    <div>제목 : <c:out value="${requestScope.data.title}"/></div>
    <div><c:out value="${requestScope.data.ctnt}"/></div>

    <c:if test="${sessionScope.loginUser != null}">
        <div>
            <form id="cmtNewFrm">
                <input type="text" name="ctnt" placeholder="댓글 내용">
                <input type="submit" value="댓글달기">
            </form>
        </div>
    </c:if>

    <div id="cmtListContainer" data-iboard="${requestScope.data.iboard}"
         data-loginuserpk="${sessionScope.loginUser.iuser}"></div>

</div>

<div class="cmtModContainer">
    <div class="cmtModBody">
        <form id="cmtModFrm" onsubmit="return false;">
            <input type="hidden" name="icmt">
            <div><input type="text" name="ctnt" placeholder="댓글 내용"></div>
            <div>
                <input type="submit" value="수정">
                <input type="button" value="취소" id="btnCancel">
            </div>
        </form>
    </div>
</div>

<script src="/res/js/board/detail.js?ver=1"></script>
