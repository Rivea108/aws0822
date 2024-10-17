package mvc.controller;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mvc.Vo.MemberVo;
import mvc.dao.MemberDao;


//index와 컨트롤러 연결에서 에러난 것들 주석 달아놓기
//1. 서버에서 모듈 거기서 서버 /webprograming으로 되어있는 것을 /로 줄이지 않았다.
//2. 나는 member파일에 있는 모든 파일을 소문자로 해놓고 대문자로 적었다
//3. 그리고 배열을 나타내는 기호 []가 있으면 0,1,2,3로 시작해야한다.


@WebServlet("/MemberController")       //서블릿 : 자바로 만든 웹페이지 (접속주소: /MemberController)
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
         
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		// 넘어온 모든 값은 여기에서 처리해서 분기한다  - controller 역할
		
	//	System.out.println("값이 넘어오나요?");
		//전체주소를 추출
		String uri = request.getRequestURI();
		//System.out.println("uri"+uri);   //   /mvc_programming/member/memberJoinAction.aws
		String[] location = uri.split("/");
		String paramMethod="";   //전송방식이 sendRedirect면 S   forward방식으면  F
		String url="";
		
		if (location[2].equals("memberJoinAction.aws")){    //4번째방의 값이 memberJoinAction.aws이면
			
			String memberId = request.getParameter("memberid");			
			String memberPwd = request.getParameter("memberpwd");			
			String memberPwd2 = request.getParameter("memberpwd2");
			String memberName = request.getParameter("membername");
			String memberEmail = request.getParameter("memberemail");
			String memberPhone = request.getParameter("memberphone");
			String memberAddr = request.getParameter("memberaddr");
			String memberGender = request.getParameter("membergender");
			String memberBirth = request.getParameter("memberbirth");	
			String[] memberHobby = request.getParameterValues("memberhobby");
			String memberInHobby="";
			for(int i=0;i<memberHobby.length;i++){
				memberInHobby = memberInHobby +memberHobby[i]+",";				
			}
			MemberDao md = new MemberDao();
			int value = md.memberInsert(
					         memberId,              //객체안에 생성해놓은 멤버 메소드를 호출해서 값을꺼낸다
							 memberPwd,
							 memberName,
							 memberGender,
							 memberBirth,
				    		 memberAddr,
				    		 memberPhone,
				    		 memberEmail,
				    		 memberInHobby);  
		   
		    String msg ="";		    
		    HttpSession session = request.getSession();   //세션객체 활용
		    
		    if (value==1){         
		    	msg="회원 가입되었습니다";
		    	session.setAttribute("msg", msg);		
		    	
		    	url=request.getContextPath()+"/";		    	
		    	
		    }else{    
		    	msg="회원 가입 오류발생하였습니다";
		    	session.setAttribute("msg", msg);		
		    	
		    	url=request.getContextPath()+"/member/memberJoin.jsp";	
		    }    	  		    
		    paramMethod="S";   //밑에서 sendRedirct방식으로 처리한다
		    
		}else if (location[2].equals("memberJoin.aws")) {
			//System.out.println("들어왔나?");
			
			url="/member/memberJoin.jsp";		 
			paramMethod = "F";   //하단에서 포워드로 처리합니다.
			
			
		}else if (location[2].equals("memberLogin.aws")) {
		//	System.out.println("들어왔나?");
			
			url="/member/memberLogin.jsp";
			paramMethod = "F";   //하단에서 포워드로 처리합니다.
			
		}else if (location[2].equals("memberLoginAction.aws")) {
			
		//	System.out.println("memberLoginAction 들어왔나?");
			
			String memberId = request.getParameter("memberid");
			String memberPwd = request.getParameter("memberpwd");
			
			MemberDao md = new MemberDao();
			MemberVo mv = md.memberLoginCheck(memberId, memberPwd);
		//	System.out.println("mv객체가 생겼나요?" + mv);
			
			if (mv ==null) {   
				url=request.getContextPath()+"/member/memberLogin.aws";
				paramMethod="S";
			}else {
				//해당되는 로그인 사용자가 있으면 세션에 회원정보 담아서 메인으로 가라
				
				String mid = mv.getMemberid();   //아이디 꺼내기
				int midx = mv.getMidx();      //회원번호 꺼내기
				String memberName = mv.getMembername();    //이름꺼내기
				
				HttpSession session = request.getSession();	
				session.setAttribute("mid", mid);
				session.setAttribute("midx", midx);
				session.setAttribute("memberName", memberName);
				
				url=request.getContextPath()+"/";
				paramMethod="S";			 			
			}			
		}else if(location[2].equals("memberLogout.aws")) {
			//	System.out.println("memberLogout");
				
				//세션 삭제
				HttpSession session = request.getSession();	
				session.removeAttribute("mid");
				session.removeAttribute("midx");
				session.removeAttribute("memberName");
				session.invalidate();				
				
				url=request.getContextPath()+"/";
				paramMethod="S";					
				
		}else if (location[2].equals("memberList.aws")) {
				System.out.println("memberList.aws");
			
				//1.메소드 불러서 처리하는 코드를 만들어야한다
				MemberDao md = new MemberDao(); //객체생성
				ArrayList<MemberVo> alist = md.memberSelectAll();
				
				request.setAttribute("alist", alist); 
				
				
			
				//2.보여줄 페이지를 forward방식으로 보여준다
				url= "/member/memberList.jsp";				
				paramMethod="F";			
		}				
		
		
		if (paramMethod.equals("F")) {		
			RequestDispatcher rd  =request.getRequestDispatcher(url);  
			rd.forward(request, response); 				
		}else {
			response.sendRedirect(url);
		}
		
		
	}	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
