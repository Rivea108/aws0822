package mvc.controller;
//zz
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
