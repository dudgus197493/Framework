<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<footer>
    <p>
        Copyright &copy; KH Information Educational Institute A-Class
    </p>

    <article>
        <a href="#">프로젝트 소개</a>
        <span>|</span>
        <a href="#">이용약관</a>
        <span>|</span>
        <a href="#">개인정보처리방침</a>
        <span>|</span>
        <a href="#">고객센터</a>
    </article>
</footer>


<%-- session scope에 message 속성이 존재하는 경우
     alert창을 이용해서 내용 출력
     
 --%>
<c:if test="${not empty message}">
        <script>
            alert("${message}");
        </script>

        <%-- message 1회 출력 후 삭제 --%>

        <%-- 
            RedirectAttributes -> request
            HttpSession        -> session
         --%>
        <c:remove var="message" />
    </c:if>
    <%-- alert메세지를 footer에 작성하는 이유
        - header or footer는 모든 페이지에 include 될것이다.
        - header는 너무 복잡하다.
        - 페이지 윗부분에서 alert가 실행되면 뒷부분의 로딩이 멈춘다.
    --%>
