package mvc.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/MemberController") //서블릿 : 자바로 만든 (동적)웹페이지 (접속주소 /MemberController)
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
//    public MemberController() {
//        super(); //상위를 가르킴 -> HttpServlet
//    } 없어도 문제없다
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//response.getWriter().append("Served at: ").append(request.getContextPath()); //겟은 기본
	    //넘어온 모든 값은 여기에서 처리해서 분기한다. - 이게 contoller의 역할이다 
	
		System.out.println("값이 넘어오나요? : ");
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response); //이전사이트에서 포스트를 사용했다면 포스트
	}

}
