<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Bom객체로 타이머 사용하기</title>
</head>
<body>

<!-- 오타 진짜 너무 많이 나옴  주의해야함 -->


<img id = "img" src="../images/strawberry.png" 
onmouseover="startTimer(5000);"
onmouseout ="cancelTimer();">

<button onclick = "window.open('./javascript_size.jsp','test','width=300,height=300')">
버튼 크기 조절 버튼
</button> <!-- 자바스크립트_사이즈에서바로 실행시키면 화면만나올뿐 실행되지 않는다 그렇기에 타이머에 연결해서 실행하면 잘 실행된다.  -->


<script>
let timerID=null;

function startTimer(time){
	 timerID= setTimeout("load('http://naver.com')",time);
}

function load(url){
	window.document.location.href = url; //이동한다. 
}

function cancelTimer(){
	 clearTimeout(timerID); //타이머를 정리한다.
}
</script>



</body>
</html>