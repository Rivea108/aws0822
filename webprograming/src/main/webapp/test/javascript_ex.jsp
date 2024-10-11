<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>예제문제</title>
</head>
<body>
1. 다음문장에서 사람 이름에 해당하는 문장은 글자크기를 30px로 작성하고 색상은 orange 색상으로 표현.

<p id = "firstP">
<span id = "fristP" style="color:orange; font-Size : 30px;">이순신장군은</span> 배 13척을 가지고 명량해전을 대승으로 이끌었다 . <br>
<span id = "fristP" style="color:orange; font-Size : 30px;">홍길동은</span>아버지를 아버지를 부르지 못하고 가출했다. <br>
<span id = "fristP" style="color:orange; font-Size : 30px;">강감찬은</span>귀주대첩의 유명한 장군이다. <br>
<span id = "fristP" style="color:orange; font-Size : 30px;">김한수는</span>내 학교 선배였다. <br></p>
<hr>
<p id2 = "firstP2" style="color:skyblue; background-color: #D09AFF;" onclick="this.style.color='red'" >
<span id2 = "fristP2" style="color:orange; font-Size : 30px;">이순신장군은</span> 배 13척을 가지고 명량해전을 대승으로 이끌었다 . <br>
<span id2 = "fristP2" style="color:orange; font-Size : 30px;">홍길동은</span>아버지를 아버지를 부르지 못하고 가출했다. <br>
<span id2 = "fristP2" style="color:orange; font-Size : 30px;">강감찬은</span>귀주대첩의 유명한 장군이다. <br>
<span id2 = "fristP2" style="color:orange; font-Size : 30px;">김한수는</span>내 학교 선배였다. <br></p>
<hr>
<p id3 = "firstP3" style="color:skyblue; background-color: #D09AFF;" onclick="this.style.color='red'" >
<span id3 = "fristP3" style="color:orange; font-Size : 30px;">이순신장군은</span> 배 13척을 가지고 명량해전을 대승으로 이끌었다 . <br>
<span id3 = "fristP3" style="color:orange; font-Size : 30px;">홍길동은</span>아버지를 아버지를 부르지 못하고 가출했다. <br>
<span id3 = "fristP3" style="color:orange; font-Size : 30px;">강감찬은</span>귀주대첩의 유명한 장군이다. <br>
<span id3 = "fristP3" style="color:orange; font-Size : 30px;">김한수는</span>내 학교 선배였다. <br>
<input type="button" value="스타일바꾸기" onclick = "change();"></p>

<hr>
이순신장군은 배 13척을 가지고 명량해전을 대승으로 이끌었다 . <br>
홍길동은 아버지를 아버지를 부르지 못하고 가출했다. <br>
강감찬은 귀주대첩의 유명한 장군이다. <br>
김한수는 내 학교 선배였다. <br>

<button>사람을 찾기</button>


</body>
</html>