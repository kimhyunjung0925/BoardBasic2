<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<link rel="stylesheet" href="/res/css/board/list.css?ver=6">
<div>
    <h1> ${requestScope.title}</h1>
    <div>
    <c:choose>
        <c:when test="${fn:length(requestScope.list) == 0}">
            랭킹이 없습니다.
        </c:when>
    <c:otherwise>
        <table id="boardTable">
            <colgroup>
                <col width="10%">
                <col width="35%">
                <col width="10%">
                <col width="15%">
                <col width="40%">
            </colgroup>
            <tr>
                <th>no</th>
                <th>title</th>
                <th>${param.type == 1 ? 'hit' : 'count'}</th>
                <th>writer</th>
                <th>reg-datetime</th>
            </tr>
            <c:forEach items="${requestScope.list}" var="item">
                <c:set var="eachTitle" value="${fn:replace(fn:replace(item.title, '>', '&gt;'), '<', '&lt;')}"/>
                <c:if test="${param.searchText != null && (param.searchType == 1 || param.searchType == 3 || param.searchType == 5)}">
                    <c:set var="eachTitle" value="${fn:replace(eachTitle, param.searchText, '<mark>' += param.searchText += '</mark>')}" />
                </c:if>
                <c:set var="eachWriterNm" value="${item.writerNm}" />
                <c:if test="${param.searchText != null && (param.searchType == 4 || param.searchType == 5)}">
                    <c:set var="eachWriterNm" value="${fn:replace(eachWriterNm, param.searchText, '<mark>' += param.searchText += '</mark>')}" />
                </c:if>

                <c:set var="pImg" value="defaultProfile.png"/>
                <c:if test="${item.profileImg != null}">
                    <c:set var="pImg" value="profile/${item.writer}/${item.profileImg}"/>
                </c:if>

                <tr class="record" onclick="moveToDetail(${item.iboard});">
                    <td>${item.iboard}</td>
                    <td> ${eachTitle}
                        <c:if test="${item.countCmt != 0}">
                            <input type="button" value="${item.countCmt}" class="cmtCountBtn">
                        </c:if>
                    </td>
                    <td>${item.cnt}</td>
                    <td><div class="circular--img circular--size40"><img src="/res/img/${pImg}"></div> ${eachWriterNm}</td>
                    <td>${item.rdt}</td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
    </c:choose>
    </div>
</div>
<script src="/res/js/board/list.js?ver=3"></script>