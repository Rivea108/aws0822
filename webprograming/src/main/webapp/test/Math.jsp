<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>수학 객체</title>
</head>
<body>
	<script>
		//0에서 99까지의 랜덤 숫자를 출력하세요
		let m;
		let n;
		
		for(let i=0; i<10; i++) {
			m = Math.random()*100;  //0부터 99.99999사이의 값이 나온다
		    n =	Math.floor(m);  //소수점은 제거한 함수 
		}
		document.write("랜던값은? : " + n);
		
		setTimeout('location.reload()',3000);
		
		
		
		
		
	</script>
</body>
</html>