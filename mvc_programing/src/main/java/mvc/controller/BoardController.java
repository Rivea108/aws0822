package mvc.controller;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mvc.Vo.BoardVo;
import mvc.dao.BoardDao;


@WebServlet("/BoardController")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private String location; //멤버변수(전역) 초기화 => 이동할 페이지
	
	public BoardController(String location) { //생성자를 생성했지만 멤버여서 안되었다
		//애초에 메소드는 클래스이름이랑 같아야하는데 클래스이름은 보더컨트롤러로하고 매소드는 
		//멤버컨트롤러로 했다
		this.location = location;
	}
	
    public BoardController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String paramMethod="";   //전송방식이 sendRedirect면 S   forward방식으면  F
		String url="";
		
		//1. 가상경로(boardList) 생성 보드컨트롤러에 연결(맞는지 아닌지도 모르겠음)
		if (location.equals("boardList.aws") ) {//가상경로
			
			BoardDao bd = new BoardDao(); //객체생성
			ArrayList<BoardVo> alist = bd.boardSelectAll(); 
			System.out.println("alist ==>" + alist); //객체주소가 나오면 객체가 생성된 것을 짐작할수 있다.
			
			request.setAttribute("alist", alist);//통신객체?
			
			paramMethod="F";
			url=request.getContextPath() + "/board/boardList.jsp"; //실제 내부경로
			
		}
		if (paramMethod.equals("F")) {		
			RequestDispatcher rd  =request.getRequestDispatcher(url);  
			rd.forward(request, response); 				
		}else if (paramMethod.equals("S")) {
			response.sendRedirect(url);
		}//가상경로 보드컨트롤러에 연결하는거 종료(맞는지 아닌지도 모르겠음)
		
		
		
		  //2. 가상경로(boardWrite) 생성 보드컨트롤러에 연결(이게맞냐?)
		if (location.equals("boardWrite.aws")) {
		  
		  BoardDao wr = new BoardDao(); 
		  ArrayList<BoardVo> alist = wr.boardSelectAll();
		  System.out.println("alist ==>" + alist);
		  
		  request.setAttribute("alist", alist);
		  
		  paramMethod="F";
		  url=request.getContextPath() + "/board/boardWrite.jsp"; //실제내부경로 
		  }
		if (paramMethod.equals("F")) { 
			RequestDispatcher rd=request.getRequestDispatcher(url);
			rd.forward(request, response); 
			}else if (paramMethod.equals("S")) { response.sendRedirect(url); }
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
