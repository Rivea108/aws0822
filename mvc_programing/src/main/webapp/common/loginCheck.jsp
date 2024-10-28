<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    //파일을 새로 생성했더니 작동했다.
    //세션정보를 꺼내서 담겨있지 않으면 로그인 화면으로 넘긴다
if (session.getAttribute("midx") == null) { //로그인이 되어있지 않다면 로그인을 하라고 로그인 페이지로 밀어냄
out.println("<script>alert('로그인을 해주세요');location.href='"+request.getContextPath()+"/member/memberLogin.aws';</script>");
	}
	 %>