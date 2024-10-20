package mvc.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/FrontController")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uri = request.getRequestURI();  //전체주소 가져오기
		//        /member/memberJoinAction.aws
		String[]  entity = uri.split("/");    //split으로 잘라주기
		
		if (entity[1].equals("member")) {
			MemberController  mc = new MemberController(entity[2]);
			mc.doGet(request, response);			
			
		}else if (entity[1].equals("board")) {
			BoardController  bc = new BoardController(entity[2]);
			bc.doGet(request, response);			
		}
		//(entity[1].equals("board")보더라고해놓고 보더컨트롤러에서 멤버컨트롤러를 반환해서 
		//반환이 제대로 되지 아니하였다.
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
