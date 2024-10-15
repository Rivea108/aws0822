<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>체크박스 연습하기</title>
</head>
<body>
<script>
let sum = 0; //누적변수 담는 전역변수 
function calc(obj) { //인자값을 받을 매개변수를 만든다
	
	
	if(obj.checked ){ //결과값이 true(참)이면  
		sum = sum + parseInt(obj.value); //숫자형으로 바꾼다 sum += parseInt(obj.value);
	} else {
		sum = sum - parseInt(obj.value); //                                  sum -= parseInt(obj.value);
	}
	document.getElementById("sumtext").value = sum;
	
return;

}
</script>
<form>
<input type="checkbox" name = "cap" value = "10000" onclick="calc(this)"> 모자 1만원
<input type="checkbox" name = "shose" value = "30000" onclick="calc(this)"> 구두 3만원
<input type="checkbox" name = "bag" value = "80000" onclick="calc(this)"> 명품가방 8만원
</form>

<input type = "text" id ="sumtext" value = "0">


</body>
</html>