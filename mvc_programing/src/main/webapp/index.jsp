<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- index와 컨트롤러 연결에서 에러난 것들 주석 달아놓기
1. 서버에서 모듈 거기서 서버 /webprograming으로 되어있는 것을 /로 줄이지 않았다.
2. 나는 member파일에 있는 모든 파일을 소문자로 해놓고 대문자로 적었다
3. 그리고 배열을 나타내는 기호 []가 있으면 0,1,2,3로 시작해야한다. -->
    
    <%
 /*System.out.println("안녕하세요");
 out.println("웹페이지에서 안녕하세요");*/  
 String msg = "";
 if(session.getAttribute("msg") != null) {
    msg = (String)session.getAttribute("msg");   
 }

 session.setAttribute("msg","");
 
 int midx =0;
 String memberId="";
 String memberName="";
 String alt = "";
 String logMsg="";
 if (session.getAttribute("midx") != null) { //로그인이 되었으면 
	 midx = (int) session.getAttribute("midx");//midx를 가져와야 하는데 mid를 입력했음
 	memberId =  (String)session.getAttribute("mid");
 	memberName = (String)session.getAttribute("MemberName");	//MemberName을 가져와야하는데 memberName을가져옴
	
 	alt = memberName+"님 로그인 되셨습니다.";
 	logMsg = "<a href='"+request.getContextPath()+"/member/memberLogout.aws'>로그아웃</a>";
 }else {
 alt = "로그인하세요";
 logMsg = "로그인";
 }
    
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="./css/style.css" type="text/css" rel="stylesheet">
<script>
<%if (!msg.equals("")) {%>
      alert('<%=msg%>');
<% }%>
</script>
</head>
<body>

<%=alt %>
<%=logMsg %>
<hr>

<div class="main">환영합니다.메인페이지입니다.</div>

<div>
<a href="<%=request.getContextPath() %>/member/memberJoin.aws">
회원가입 페이지 가기
</a>
</div>


<div>
<a href="<%=request.getContextPath() %>/member/memberLogin.aws">
회원 로그인 하기
</a>
</div>


<div>
<a href="<%=request.getContextPath() %>/member/memberList.aws">
회원목록 가기
</a>
</div>


<div>
<a href="<%=request.getContextPath() %>/board/boardList.aws">
게시판 목록 가기
</a>
</div>


</body>
</html>