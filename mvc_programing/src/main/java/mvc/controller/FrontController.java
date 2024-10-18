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
		
		String uri = request.getRequestURI();  //1. 전체주소 뽑아서 
		//        /member/memberJoinAction.aws
		String[]  entity = uri.split("/");    //split으로 잘라주기
		
		if (entity[1].equals("member")) { //2. 멤버는 멤버컨트롤러를 부르고
			MemberController  mc = new MemberController(entity[2]);
			mc.doGet(request, response);			
			
		}else if (entity[1].equals("board")) { //3. 보드는 보드컨트롤러를 부르고
			BoardController  bc = new BoardController(entity[2]); //생성자 entity[2]
			bc.doGet(request, response);			
		}
		//(entity[1].equals("board")보더라고해놓고 보더컨트롤러에서 멤버컨트롤러를 반환해서 
		//반환이 제대로 되지 아니하였다.
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
