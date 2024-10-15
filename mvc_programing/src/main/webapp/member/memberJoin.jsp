 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<HTML>
<HEAD>
<TITLE>회원가입 페이지</TITLE>
<link href="../css/style.css" type="text/css" rel="stylesheet">
<!--  스타일이 없더라도 바로위 줄때문에 스타일링크를 지정해 스타일을 가져온다 -->
<script>
const email = /[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]$/i;
//alert(email.test("hello5@email.com"));
//alert("123456789");//이것이 먼저 실행되고 화면은 이창을 끄면 나온다

//버튼을 눌렀을때 check 함수 작동

//Document document; 이 프로그램에 참조되어 내장되어 있기에 아래 출력문에 사용해도 문제가 생기지 않는다.

function check() {

	var fm = document.frm; //var 통합타입(숫자, 문자 등등 받을수있다.
	
	/* var memberid = document.frm.memberid;
	var memberpwd = document.frm.memberpwd; */

	if (fm.memberid.value == "") {
		alert("아이디를 입력해주세요");
		fm.memberid.focus();
		return;
    }else if (fm.memberpwd.value == "") {
	alert("비밀번호를 입력해주세요");
	fm.memberpwd.focus();
	    return;
}else if (fm.memberpwd2.value == "") {
	alert("비밀번호2를 입력해주세요");
	fm.memberpwd2.focus();
    return;
}else if (fm.memberpwd.value != fm.memberpwd2.value) {
	alert("비밀번호가 일치하지 않습니다.");
	fm.memberpwd,memberpwd2.focus();
	return;
}else if (fm.membername.value == "") {
	alert("이름를 입력해주세요");
	fm.membername.focus();
	return;
    }else if (fm.memberemail.value == "") {
	alert("이메일를 입력해주세요");
	fm.memberemail.focus();      
	return;
    } else if(email.test(fm.memberemail.value) == false) {
    	alert("이메일 형식이 올바르지 않습니다.")
    	fm.memberemail.value="";
    	fm.memberemail.focus();      
    	return;
    }else if (fm.memberphone.value == "") {
	alert("연락처를 입력해주세요");
	fm.memberphone.focus();
	return;
    }else if (fm.memberbirth.value == "") {
	alert("생년월일를 입력해주세요");
	fm.memberbirth.focus();
    return;
    }else if (hobbyCheck() == false) {
		alert("취미를 한개 이상 선택해주세요.");
		return;
	}
	
	var ans = confirm("저장하시겠습니까?");
	
	
	if (ans == true){
	//alert("이동할 정보등록할 차례입니다.");
	//action="memberjoinaction.jsp" method="post" html홈태그 기능을 자바스크립트로 제어
	
	
	//가상경로를 사용해서 쓸 예정                   가짜 경로의 형식은 /기능/세부기능.aws 
	fm.action="<%=request.getContextPath()%>/member/MemberJoinAction.aws";
	//MemberJoinAction.aws 가상경로
	fm.method="post";
	fm.submit();
	}
		return;	//리턴에 값을 안쓰면 그냥 멈춤 종료	
}

function hobbyCheck(){
	
	var arr = document.frm.memberhobby; //문서객체안에 폼객체 안에 input객체 선언
	var flag = false;                                                //체크유무 초기값 false 선언
	
	for(var i=0; i<arr.length; i++) {                     //선택한 여러값을 반복해서 출력
	if(arr[i].checked == true){                                 //하나라도 선택했다면 true 리턴
		flag = true;                                                    
		break;                                                            
		}	
	}
	
	/* if (flag==false){
		alert("취미를 한개 이상 선택해주세요.")
		return false;
	}	 */
	return flag;
}

</script>

</HEAD>

<BODY>
	<header><a href ="./memberjoin.jsp" style="text-decoration:none">회원가입 페이지</a></header>

	<nav><a href ="./memberlogin.jsp" style="text-decoration:none">회원로그인 가기</a></nav>

	<section>
	<div>
		<article>
		<form name="frm">
		  	<table  class="jaeseong">
				
				<tr>
					<th class="idcolor">아이디</th>
					<td><input type="text" name="memberid" maxlength="30"
						style="width: 200px;" value="" placeholder="아이디를 입력하세요"></td>
				</tr>
				<tr>
					<th class ="idcolor">비밀번호</th>
					<td><input type="password" name="memberpwd" maxlength="30"
						style="width: 100px;"></td>
				</tr>
				<tr>
					<th>비밀번호재확인</th>
					<td><input type="password" name="memberpwd2" maxlength="30"
						style="width: 100px;"></td>
				</tr>
				<tr>
					<th id="name">이름</th>
					<td><input type="text" name="membername" maxlength="30"
						style="width: 200px;"></td>
				</tr>
			    <tr>
					<th>이메일</th>
					<td><input type="email" name="memberemail" maxlength="30"
						style="width: 200px;"></td>
				</tr>
				<tr>
					<th>연락처</th>
					<td><input type="number" name="memberphone" maxlength="30"
						style="width: 300px;"></td>
				</tr>
				
				<tr>
					<th>주소</th>
					<td><select name="memberaddr" style="width: 100px;">
							<option value="">선택하시오</option> 
							<option value="서울">서울</option>
							<option value="대전" selected>대전</option>
							<option value="대구">대구</option>
							<option value="부산">부산</option>
							</select></td>
				</tr>
				
<!-- 
****실행을 memberjoinaction말고 제발 memberjoin에서 실행할것 ****
<option value="선택하시오">선택하시오</option> 에서 "선택하시오"는 빼야한다 왜냐하면 선택하시오가 아무것도 선택하지 않았을때 선택하시오가 출력되기 때문이다
<option value="대전" >대전</option> 그리고 이거에서는 기본값을 대전으로 선택하려면 "대전" ***selected***을 붙여야한다. 
 -->
 
				<tr>
					<th>성별</th>

					    <td>
			    <input type="radio" name="membergender" id="select1" value="M"><label for="select1">남성</label>
                <input type="radio" name="membergender" id="select2"  value="F" checked><label for="select2">여성</label>
						</td>
				</tr>
				<tr>
					<th>생년월일</th>
					<td><input type="number" name="memberbirth" maxlength="8"
						style="width: 100px;"> 예)20240101</td>
				</tr>
				<tr>
					<th>취미</th>

				<td>
				<input type="checkbox" name="memberhobby"id="check1" value = "야구"><label for="check1">야구</label>
				<input type="checkbox" name="memberhobby" id="check2"value = "축구"><label for="check2">축구</label>
			   <input type="checkbox" name="memberhobby" id="check3"value = "농구"><label for="check3">농구</label>
					    </td>
				</tr>
 
	            <tr>
					<td colspan=2 style="text-align: center;">
					<button type="button"onclick="check()">
				 	<!--  	<img src="https://t1.daumcdn.net/daumtop_deco/images/pctop/2023/logo_daum.png" width="20px" height="10px">  -->
					저장하기
					</button>
					
				<!--   	<input type="submit" name="btn" value="회원정보 저장하기">
					<input type="reset" name="btn" value="초기화"></td> -->
				</tr> 

		</table>
</form>
</article>
</div>
</section>

<aside></aside>

<footer> made by hji. </footer>

</body>
</html>
