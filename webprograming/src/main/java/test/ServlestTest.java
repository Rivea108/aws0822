package test;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/ServlestTest")
public class ServlestTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		request.setCharacterEncoding("UTF-8");//넘겨주는 쪽
		response.setContentType("text/html;charset=UTF-8");//응답하는 쪽
		
		System.out.println("서블릿 테스트입니다.");
		
	   PrintWriter out = response.getWriter();
	   out.println("Hello World.");
	   out.println("한글 테스트");
	   
	   out.println("<!cocttype html>"
	   		+ "<html>"
	   		+ "<head>"
	   		+ "<title>제목란</title>"
	   		+ "</head>"
	   		+ "<body><span style=\"color:red;font-size:20px;\">내용</span></body>"
	   		+ "</html>");
	   
	   
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
