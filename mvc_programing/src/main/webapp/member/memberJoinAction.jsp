<%@page import="java.sql.DriverManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <jsp:useBean id="mv" class = "mvc.Vo.MemberVo" scope="page" />
    <%@ page import = "mvc.dao.MemberDao" %>
    <jsp:setProperty name ="mv" property="*" /> 
    <%
   
    
    
    //PreparedStatement 클래스는 메소드화 시켜서 사용함
    String [] memberhobby = request.getParameterValues("memberhobby");
    String memberInHobby="";
    for(int i = 0; i<memberhobby.length; i++) {
    	memberInHobby = memberInHobby  +memberhobby[i] + ",";
    	System.out.println("memberhobby?" + memberhobby[i]);
    }
    
    MemberDao md = new MemberDao(); 
    int value = md.memberInsert(mv.getMemberid(),                        //객체안에 생성해놓은 멤버 메소드를 호출해서 값을 꺼낸다.
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
	   pageUrl=request.getContextPath() + "/"; // request.getContextPath() :프로젝트이동시키는 메소드
	  response.sendRedirect(pageUrl); // 전송방식 : sendRedirect는 요청받으면 다시 그쪽으로 가라고 지시하는 방법 , 포워드방식도 있다고 하심 
   }else {
	   msg="회원가입 오류가 발생하였습니다";
	   pageUrl=request.getContextPath()+"/member/memberjoin.jsp";
	   response.sendRedirect(pageUrl);
   }
   
    %>
    
   <script>
   alert('<%=msg%>');
   //자바스크립트로 페이지 이동시킨다 document객체안에 location객체안에 주소속성을 담아서
   document.location.href="<%=pageUrl%>";
   </script>