<%@page import="java.sql.DriverManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%@ page import ="java.sql.*" %> 
    <%@include file = "/common/dbconn.jsp" %>
    <%@include file = "/common/function.jsp" %>
    <%-- <%@ page import ="java.sql.Connection" %> --%>
    <%-- <%@ page import ="java.sql.Drivermanager" %> --%>
    
    <jsp:useBean id="mv" class = "Vo.MemberVo" scope="page" />
    <!-- scope 범위는 4가지가 있다 page(페이지 내에서만), requst(전송하는 범위까지)
    ,session(서버에서 끝날때까지(로그아웃)),application(프로그램이 살아있을 때까지) -->
    
    <jsp:setProperty name ="mv" property="*" /> <바인딩 기술>
    
    
    
    <%
    /* String memberId = request.getParameter("memberId");
    System.out.println("memberId 값은? = " + memberId);
        
    String memberPwd = request.getParameter("memberPwd");
    System.out.println("memberPwd 값은? = " + memberPwd);
        
    String memberPwd2 = request.getParameter("memberPwd2");
    System.out.println("memberPwd2 값은? = " + memberPwd2);
        
    String memberName = request.getParameter("memberName");
    System.out.println("memberName 값은? = " + memberName);
        
    String memberEmail = request.getParameter("memberEmail");
    System.out.println("memberEmail 값은? = " + memberEmail);
        
    String memberPhone = request.getParameter("memberPhone");
    System.out.println("memberPhone 값은? = " + memberPhone);
       
    String memberAddr = request.getParameter("memberAddr");
    System.out.println("memberAddr 값은? = " + memberAddr);
    
    String memberGender = request.getParameter("memberGender");
    System.out.println("memberGender 값은? = " + memberGender);
    
    String memberBirth = request.getParameter("memberBirth");
    System.out.println("memberBirth 값은? = " + memberBirth);
    
    
   String [] memberHobby = request.getParameterValues("memberHobby");
   String memberInHobby="";
   for(int i = 0; i<memberHobby.length; i++) {
   	memberInHobby = memberInHobby  +memberHobby[i] + ",";
   	
   	System.out.println("memberHobby?" + memberHobby[i]);
   }
	System.out.println("memberInHobby?" + memberInHobby); */
	
   //1. jsp 프로그래밍(날코딩 날코딩방법부터 -> 함수화 -> 객체화 방식)
   //2. java/jsp 프로그래밍 (model1,model2 MVC 방식으로 진화되는 방법)
   //3. spring 프레임워크로 프로그래밍 하는 방법 
    
   
   
   //conn 객체안에는 많은 메소드가 있는데, 일단 createStatement 메소드를 사용해서 쿼리 작성
   
   /* String sql = "insert into member(memberid,memberpwd,membername,"
		   + "membergender,memberbirth,memberaddr,memberphone,memberemail,memberhobby)"
           + "values('"
		   +memberId+"','"
           +memberPwd+"','"
		   +memberName+"','"
           +memberGender+"','"
		   +memberBirth+"','"
		   +memberAddr+"','"
		   +memberPhone+"','"
		   +memberEmail+"','"
		   +memberInHobby+"')";
        		   
   Statement stmt = conn.createStatement(); //쿼리구문을 동작시키는 클래스(Statement)   
   int value = stmt.executeUpdate(sql); */
   //value가 0이면 미입력 1이면 입력
   
   //PreparedStatement 클래스는 메소드화 시켜서 사용함
   String [] memberhobby = request.getParameterValues("memberhobby");
   String memberInHobby="";
   for(int i = 0; i<memberhobby.length; i++) {
   	memberInHobby = memberInHobby  +memberhobby[i] + ",";
   	System.out.println("memberhobby?" + memberhobby[i]);
   }
   
   //매개변수에 인자값 대입해서 함수호출
   int value = memberInsert(
		   conn,
		   mv.getMemberid(),                        //객체안에 생성해놓은 멤버 메소드를 호출해서 값을 꺼낸다.
		   mv.getMemberpwd(),                   
		   mv.getMembername(),
		   mv.getMembergender(),
		   mv.getMemberbirth(),
		   mv.getMemberaddr(),
		   mv.getMemberphone(),
		   mv.getMemberemail(),
		   memberInHobby);
   
   //value값이 1이면 입력성공 0이면 입력실패
   //1이면 성공했기 때문에 다른페이지로 이동시키고 0이면 다시 회원가입입력페이지로 이동
   
   String pageUrl ="";
   String msg ="";
   if(value == 1){      //-> index.jsp는 웹 설정파일에 기본등록되어있어서 생략가능
	   msg="회원가입되었습니다";
	   pageUrl=request.getContextPath()+"/index.jsp"; // request.getContextPath() :프로젝트이동시키는 메소드
	   //response.sendRedirect(pageUrl); // 전송방식 : sendRedirect는 요청받으면 다시 그쪽으로 가라고 지시하는 방법 , 포워드방식도 있다고 하심 
   }else {
	   msg="회원가입 오류가 발생하였습니다";
	   pageUrl=request.getContextPath()+"/member/memberjoin.jsp";
	   //response.sendRedirect(pageUrl);
   }
   
    %>
    
   <script>
   alert('<%=msg%>');
   //자바스크립트로 페이지 이동시킨다 document객체안에 location객체안에 주소속성을 담아서
   document.location.href="<%=pageUrl%>";
   </script>