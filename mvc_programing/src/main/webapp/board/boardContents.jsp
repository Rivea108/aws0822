<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@page import="mvc.Vo.BoardVo" %>   

 <%
 BoardVo bv = (BoardVo)request.getAttribute("bv");   //강제형변환  양쪽형을 맞춰준다 
 String memberName = "";
 if(session.getAttribute("memberName") !=null){
	 memberName = (String)session.getAttribute("memberName");
 }

 System.out.println("contextpath : " + request.getContextPath()); //디버깅코드 
 System.out.println("bidx: " + bv.getBidx()); //디버깅코드
 %>   
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글내용</title>
<link href="../css/style2.css" rel="stylesheet">
<!--jquery CDN주소 -->
<script src="https://code.jquery.com/jquery-latest.min.js"></script> 
<script> 

/* function check() {
	  
	  // 유효성 검사하기
	  let fm = document.frm;
	  if (fm.ccontent.value == "") {
		  alert("내용을 입력해주세요");
		  fm.ccontent.focus();
		  return;
	  }
	  
	  let ans = confirm("저장하시겠습니까?");
	  
	  if (ans == true) {
		  fm.action="./detail.html";
		  fm.method="post";
		  fm.submit();
	  }	  
	  
	  return;
} */

//jquery로 만드는 함수  ready밖에 생성
$.boardCommentList = function(){
	alert("ddddddd");
	$.ajax({
		type :  "get",    //전송방식
		url : "<%=request.getContextPath()%>/comment/commentList.aws?bidx=<%=bv.getBidx()%>",
		dataType : "json",       // json타입은 문서에서  {"키값" : "value값","키값2":"value값2"}
		success : function(result){   //결과가 넘어와서 성공했을 받는 영역
			alert("전송성공");			
						
		},
		error : function(){  //결과가 실패했을때 받는 영역						
			alert("전송실패");
		}			
	});	
}


$(document).ready(function(){

	//alert(12345679);
	$("#btn").click(function(){
		//alert("추천버튼 클릭");	
	
	
		 $.ajax({
			type :  "get",    //전송방식
			url : "<%=request.getContextPath()%>/board/boardRecom.aws?bidx=<%=bv.getBidx()%>",
			dataType : "json",       // json타입은 문서에서  {"키값" : "value값","키값2":"value값2"}
			success : function(result){   //결과가 넘어와서 성공했을 받는 영역
				//alert("전송성공 테스트");
			
				//alert("str"+ str);//결과값 확인하는 디버깅코드
				//$("#btn").val(str);
				var str ="추천("+result.recom+")";			
				$("#btn").val(str);			
			},
			
			error : function(){  //결과가 실패했을때 받는 영역
				//alert("전송실패 테스트");
			}			
		});			 
	});	
	
	$("#cmtBtn").click(function(){ //제이쿼리로만드는 유효성검사
		//alert("ddd");
		let loginCheck = "<%=session.getAttribute("midx")%>";
		//alert(loginCheck)
		if (loginCheck == "" || loginCheck == "null" || loginCheck == null){
			alert("로그인 해주세요");
			return;
		}
			
		
		let cwriter = $("#cwriter").val();
		let ccontents = $("#ccontents").val();
		
		if(cwriter ==  ""){
			alert("작성자를 입력해주세요");
			$("#cwriter").focus();
			return;
			
		}else if (ccontents == ""){
			alert("내용을 입력해주세요");
			$("#ccontents").focus();
			return;
		}
		
		$.ajax({
			type :  "post",    //내용을 옮겨야하기에 포스트전송방식
			url : "<%=request.getContextPath()%>/comment/commentWriteAction.aws",
			data : {"cwriter" : cwriter,
						 " ccontents": ccontents,
						 "bidx" : "<%=bv.getBidx()%>",
						 "midx" : "<%=session.getAttribute("midx")%>" 
						 },
			dataType : "json",       // json타입은 문서에서  {"키값" : "value값","키값2":"value값2"}
			
			success : function(result){   //결과가 넘어와서 성공했을 받는 영역
				alert("전송테스트 성공");
			
			var str ="추천("+result.recom+")";
			//alert("str"+ str);//결과값 확인하는 디버깅코드
				$("#btn").val(str);
			},
			error : function(){  //결과가 실패했을때 받는 영역
						
				alert("전송테스트 실패");
			}			
		});			
		
		
	}); 
});


</script>
</head>
<body>
<header>
	<h2 class="mainTitle">글내용</h2>
</header>

<article class="detailContents">
	<h2 class="contentTitle"><%=bv.getSubject() %> (조회수:<%=bv.getViewcnt() %>)
	<input type="button" id="btn" value="추천(<%=bv.getRecom() %>)">
	</h2>
	
	<p class="write"><%=bv.getWriter() %> (<%=bv.getWriteday() %>)</p>
	
	<div class="content">
		<%=bv.getContents() %>	
		
	</div>
	<% if (bv.getFilename() == null || bv.getFilename().equals("") ) {}else{ %>
	<img src="<%=request.getContextPath() %>/images/<%=bv.getFilename() %>">
	<p>
	<a href="<%=request.getContextPath() %>/board/boardDownload.aws?filename=<%=bv.getFilename() %>" class="fileDown">	
	첨부파일 다운로드</a>
	</p>
	<%} %>
	
	
</article>
	
<div class="btnBox">
	<a class="btn aBtn" href="<%=request.getContextPath() %>/board/boardModify.aws?bidx=<%=bv.getBidx()%>">수정</a>
	<a class="btn aBtn" href="<%=request.getContextPath() %>/board/boardDelete.aws?bidx=<%=bv.getBidx()%>">삭제</a>
	<a class="btn aBtn" href="<%=request.getContextPath() %>/board/boardReply.aws?bidx=<%=bv.getBidx()%>">답변</a>
	<a class="btn aBtn" href="<%=request.getContextPath() %>/board/boardList.aws">목록</a>
</div>

<article class="commentContents">
	<form name="frm">
		<p class="commentWriter">
		<input type="text" id="cwriter" name="cwriter" value="<%=memberName%>" readonly="readonly" style="width:100px;">
		</p>	
		<input type="text" id="ccontents" name="ccontents">
		<button type="button" id="cmtBtn" class="replyBtn">댓글쓰기</button>
	</form>
	
	<table class="replyTable">
		<tr>
			<th>번호</th>
			<th>작성자</th>
			<th>내용</th>
			<th>날짜</th>
			<th>DEL</th>
		</tr>
		<tr>
			<td>1</td>
			<td>홍길동</td>
			<td class="content">댓글입니다</td>
			<td>2024-10-18</td>
			<td>sss</td>
		</tr>
	</table>
</article>

</body>
</html>