<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>제이쿼리 연습하기</title>
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
<script>

// 여기서도 오타 에러 많이나고 그 문장끝내는 }); 주의해야할듯 


$(document).ready(function(){
	//alert("hello world");
$("#btn2").click(function(){
	$("p").hide();
	});
	
	//태그를 부를때 태그셀렉터
$("#btn2").dblclick(function(){
	$("p").show();	
	});	
	
//아이디를 부를때는 #
	$("#btn").click(function(){
		$("#divid").html("");	
	});	
	
	/* 형식
	$("셀렉터").메소드(function(){
	alert("ddddd");
	); */
	
	$("#btn3").click(function(){
		//alert("버튼먹히나요?"); 디버깅 코드
	$("#div1").fadeIn(); 
	$("#div2").fadeIn("slow");
	$("#div3").fadeIn(3000);
			
	//$("#div1").fadeOut();                             사라지게 하는 것
	//$("#div2").fadeOut("slow");
	//$("#div3").fadeOut(3000);
	
	});
	
	$("#filep").click(function(){
		//alert("버튼먹히나요?");
		$("#panel").slideDown("slow");
	});	
	

});
</script>

<p>저는 홍길동입니다.</p>
<button id ="btn2">클릭하면 글이 사라집니다.</button>
<div id="divid">
안녕하세요
</div>
<button id="btn">클릭해보세요.</button>


<p>If you click on me, I will disappear.</p>
<p>Click me away!</p>
<p>Click me too!</p>



<br>
<button id="btn3">서서히 나타나다</button>

<div id="div1" style="width:80px; height:80px;background-color:red;display:none;"></div> <!-- 나타나는 것 -->
<br>
<div id="div2" style="width:80px; height:80px;background-color:green;display:none;"></div> 
<br>
<div id="div3" style="width:80px; height:80px;background-color:blue;display:none;"></div> <!-- display:none;을 지운다면 사라짐+ -->


<div id ="panel"style="padding:50px;background-color:#e5eecc;display:none;">안녕 반가워요</div>

</body>
</html> 