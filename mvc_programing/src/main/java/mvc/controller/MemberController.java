package mvc.controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mvc.dao.MemberDao;

// index와 컨트롤러 연결에서 에러난 것들 주석 달아놓기
// 1. 서버에서 모듈 거기서 서버 /webprograming으로 되어있는 것을 /로 줄이지 않았다.
// 2. 나는 member파일에 있는 모든 파일을 소문자로 해놓고 대문자로 적었다
// 3. 그리고 배열을 나타내는 기호 []가 있으면 0,1,2,3로 시작해야한다.

@WebServlet("/MemberController")       //서블릿 : 자바로 만든 웹페이지 (접속주소: /MemberController)
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
         
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		// 넘어온 모든 값은 여기에서 처리해서 분기한다  - controller 역할
		
	//	System.out.println("값이 넘어오나요?");
		//전체주소를 추출
		String uri = request.getRequestURI();
		System.out.println("uri"+uri);   
              //          /member/memberJoinAction.aws
	
       		String[] location = uri.split("/");
		if (location[2].equals("memberJoinAction.aws")){    
		//4번째방의 값이 memberJoinAction.aws이면
			
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
		
		    String pageUrl="";
		    String msg ="";
		    HttpSession session = request.getSession();
		    
		    if (value==1){                                  // -> index.jsp파일은 web.xml웹설정파일에 기본등록되어있어서 생략가능

				
				msg="회원 가입되었습니다";
				session.setAttribute("msg", msg);
				pageUrl=request.getContextPath()+"/";  //request.getContextPath() : 프로젝트이름
		        response.sendRedirect(pageUrl);    	  //전송방식 sendRedirect는 요청받으면 다시 그쪽으로 가라고 지시하는 방법
		    }else{   
				
		    	msg="회원 가입 오류발생하였습니다";
		    	session.setAttribute("msg", msg);
		    	pageUrl=request.getContextPath()+"/member/memberJoin.jsp";
		        response.sendRedirect(pageUrl);  	//sendRediect방식은 새롭게 다른쪽으로 가라고 지시
		    
		    } 
		    
		}else if  (location[2].equals("memberJoin.aws")){       			
			
				String uri2="/member/memberJoin.jsp";
				RequestDispatcher rd = request.getRequestDispatcher(uri2);
				rd.forward(request, response); //포워드 방식 : 내부에서 넘겨서 토스하겠다는 뜻
				
		}else if  (location[2].equals("memberLogin.aws")){
			System.out.println("들어왔나?");
			
			String uri2 = "/member/memberLogin.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(uri2);
			System.out.println(uri2);
			rd.forward(request, response); 
		}
		
	}	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
