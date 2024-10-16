<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>윈도우 사이즈 크기 조절</title>
<script type="text/javascript">

function startScroll(){
	setInterval("autoScoll()",interval)
}

fumction autoScroll(){
	window.scrollBy(0,10); //10px씩 이동
}

</script>

</head>
<body onload="startScoll(1000);">
<h3>윈도우 위치와 크기조절</h3>

<button onclick = "window.moveBy(-1010);">left</button>
<button onclick = "window.moveBy(10,0);">right</button>
<button onclick = "self.moveBy(0,-10);">up</button>
<button onclick = "moveBy(0,10);">down</button>
<button onclick = "resizeBy(10,10);"> + </button>
<button onclick = "resizeBy(-10,-10);"> - </button>

<hr>
무<br>
궁<br>
화<br>
꽃<br>
이<br>
피<br>
었<br>
습<br>
니<br>
다<br>
무<br>
궁<br>
화<br>
꽃<br>
이<br>
피<br>
었<br>
습<br>
니<br>
다<br>
무<br>
궁<br>
화<br>
꽃<br>
이<br>
피<br>
었<br>
습<br>
니<br>
다<br>

<a href = "http://localhost/webprograming/test/javascript_history.jsp">이동</a>





</body>
</html>