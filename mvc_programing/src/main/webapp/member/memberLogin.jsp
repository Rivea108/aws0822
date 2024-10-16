<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
 <HEAD>
  <TITLE> member-login </TITLE>
   <style>
  header {
  width: 100%;
  height: 100px;
  text-align: center; 
  }
  nav {
  width: 15%;
  height: 400px;
  float: left; 
  }
  section {
  width: 70%;
  height: 400px;
  float: left;
  }
  aside {
  width: 15%;
  height: 400px;
  float: left;
   border: 1px  solid red;
  }
  footer { 
  width: 100%;
  height: 150px;
  text-align: center; 
  }
  .jaeseong {
  margin-left: auto;
  margin-right: auto;
  }
 
  body{
 /* background-color:#d3d3d3;*/
  }
 
  td, th{
  border: none; /* 내부 셀 테두리 제거 */
    padding: 10px; /* 셀 내부 여백 */
  }
 table {
	border: 15px outset gray; /* 외곽 테두리 설정 */
    width: 700px; /* 너비 설정 */
    height: 200px;
    margin:0 auto;
}
input[type=text] {
color : red;
}
input[type=text]:focus, 
input[type=email]:focus, 
input[type=password]:focus {
background: aliceblue; 
    font-size: 120%; 
}
input[type=text]:focus, input[type=email]:focus { 
background : aliceblue;
}
input[type=text]:focus, input[type=email]:focus { 
font-size : 120%; 
}
label {
	display : block;
	padding : 10px;
}
label span {
	display : inline-block;
	width : 90px;
	text-align : right;
	padding : 10px;
}

   </style>
  
  <script>
  //아이디 비밀번호 유효성 검사
  function check() {
	  //이름으로 객체찾기
	  let memberid = document.getElementsByName("memberid");
	  let memberpwd = document.getElementsByName("memberpwd");
/* 	  alert(memberid[0].value);
	  alert(memberpwd[0].value); */
	  
	  if (memberid[0].value == ""){
	  alert("아이디를 입력해주세요");
	  memberid[0].focus();
	  return;
  }else if(memberpwd[0].value == "") {
	  alert("비밀번호를 입력해주세요");
	  memberpwd[0].focus();
	  return;
  }
	  var fm = document.frm;
	  fm.action = "<%=request.getContextPath()%>/member/memberLoginAction.aws";   //가상경로 지정 action은 처리하는 의미
	  fm.method = "post";
	  fm.submit();
	  
	  return;
  }
  </script>
    </HEAD>

<BODY>

 <header>회원로그인 페이지</header>
 <nav></nav>
 <section>
   <article>
   <div id="parent">
   <div id ="child">
<form name="frm">

   <table border=1 style="length:300px;">
   <tr> 
   <th>아이디</th>
         <td>
            <input type="text" name="memberid" style="width:150px;" maxlength="30">
         </td>
   </tr>
   <tr> 
   <th>비밀번호</th>
         <td>
            <input type="password" name="memberpwd" style="width:150px;" maxlength="30">
         </td>
   </tr>
   
   <tr>
      <td colspan=2 style="text-align:center;">
         <input type="button" name="btn" value="로그인" onclick="check();">
      </td>
   </tr>

   <tr>
      <td>
         <input type="button" name="btn" value="아이디 찾기">
      </td>
      <td>
         <input type="button" name="btn" value="비밀번호 찾기">
      </td>

   </tr>


   </table>
</form>      