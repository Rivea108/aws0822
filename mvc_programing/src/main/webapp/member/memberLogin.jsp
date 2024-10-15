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
    </HEAD>

 <BODY>
  <header style="text-align:center;">mamber-login</header>

   <section>
  <div>
  <article>
    <form  name = "frm" action="memberJoin.html">
    <table border = "1" width="700px" height="200px" class="jaeseong"> 
    
      <tr><td colspan = "2" style="text-align:center;">로그인 페이지</td></tr>
    <tr><th>아이디</th><td><input type="text" name="memberId" maxlength="30" style="width:200px;" ></td></tr>
  <tr><th>비밀번호</th><td><input type="password" name="memberPwd" maxlength="30" style="width:200px;" ></td></tr>
      
      
<tr>
<td colspan="7" style="text-align:center;">
<input type="submit" name="btn" value="로그인하기">
</td>
</tr>
      
     <!--  <tr>
      <td colspan=2 style="text-align:center;">
        <input type= "submit" name="btn" value="아이디 찾기"> / <input type= "submit" name="btn" value="비밀번호 찾기">
        </td>
      </tr> -->
        
      </table>
      </form>
    </article>
    </div>
    </section>
    </BODY>
</HTML>