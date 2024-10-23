package mvc.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mvc.Vo.BoardVo;
import mvc.Vo.Criteria;
import mvc.Vo.PageMaker;
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
		
		if (location.equals("boardList.aws") ) {//가상경로
			
			String page = request.getParameter("page");
			if(page == null) page = "1";
			//if 문을 만들때 실행문이 한개일때 {}생략가능
			int pageInt = Integer.parseInt(page); //문자를 숫자로 변경

			System.out.println(pageInt);
			Criteria cri = new Criteria();
			cri.setPage(pageInt);
			

			PageMaker pm = new PageMaker();
			pm.setCrl(cri);                          //<------ PageMaker에 Criteria 담아서 가지고다닌다
			
			BoardDao bd = new BoardDao(); //객체생성
			//페이징 처리하기 위한 전체 데이터 갯수 가져오기
			int boardCnt = bd.boardTotalCount();
			//System.out.println("게시물 수는? : " +  boardCnt );
			pm.setTotalCount(boardCnt);                          //<------ PageMaker에 전체 게시물수를 담아서 페이지계산 
			
			ArrayList<BoardVo> alist = bd.boardSelectAll(cri); 
		
			
			request.setAttribute("alist", alist); //화면까지 가지고 가기위해 request객체에 담는다
			request.setAttribute("pm", pm);    //forward 방식으로 넘기기 때문에 공유가 가능하다.
		
			paramMethod="F";
			url=request.getContextPath() + "/board/boardList.jsp"; //실제 내부경로
		
			
		}else if (location.equals("boardWrite.aws")) { 
		System.out.println("boardWrite");
		
		paramMethod="F";  //포워드 방식은 내부에서 공유하는 것이기 때문ㅇ ㅔ내부에서 활동한다
		url = "/board/boardWrite.jsp"; //실제 내부경로
		}	else if(location.equals("boardWriteAction.aws")) {
			System.out.println("boardWriteAction.aws");
		
			//저장될 위치
			/*
			 * String savePath=
			 * "C:\\Users\\admin\\git\\aws0822\\mvc_programing\\src\\main\\webapp\\images";
			 * int sizeLimit = 15 * 1024 * 1024; //15메가 String dateType = "UTF-8";
			 * DefaultFileRenamePolicy policy = new DefaultFileRenamePolicy();
			 * MultipartRequest multi = new MultipartRequest(request,savePath, sizeLimit,
			 * dateType, policy);
			 */
			
			
			
			
			
			//1.파라미터 값을 넘겨받는다.
		String subject = request.getParameter("subject");
		String contents = request.getParameter("contents");
		System.out.println(contents);
		String writer = request.getParameter("writer");
		String password = request.getParameter("password");
		
		HttpSession session = request.getSession(); //세션 객체 불러와서 
		int midx = Integer.parseInt(session.getAttribute("midx").toString()); //로그인 할 때 담았던  세션변수 midx값을 꺼낸다
		
		BoardVo bv = new BoardVo();
		bv.setSubject(subject);
		bv.setContents(contents);
		bv.setWriter(writer);
		bv.setPassword(password);
		bv.setMidx(midx);
		
		//2. DB처리한다..
		BoardDao bd = new BoardDao();
		int value = bd.boardInsert(bv);
		
		if(value == 2 ) { //입력성공
			paramMethod="S";
			url = request.getContextPath() + "/board/boardList.aws";
		}else { //입력실패
			paramMethod="S";
			url = request.getContextPath() + "/board/boardWriter.aws";
		}
		
		//3. 처리후 이동한다 sendRedirect
		/*
		 * paramMethod="S"; url = request.getContextPath() + "/board/boardList.aws";
		 */
		
		}else if (location.equals("boardContents.aws")) {
			System.out.println("boardContents.aws");
			
			//이것이 컨트롤러의 역할이다
			
			//1.  넘어온 값 받기
			String bidx = request.getParameter("bidx");
			System.out.println("bidx-->"+bidx);
			int bidxInt = Integer.parseInt(bidx); //bidx라는 문자를 숫자로 변경
			
			//2. 처리하기
			BoardDao bd = new BoardDao(); //객체생성하고
			
			bd.boardViewCntUpdate(bidxInt);
			BoardVo bv = bd.boardSelectOne(bidxInt);  //생성한 메소드 호출 (해당되는 bidxdl게시물 데이터 가져옴)
			request.setAttribute("bv", bv); //포워드방식이라 같은 영역안에 있어서 공유해서 jsp페이지에서 꺼내쓸수 있다.
			
			//3. 이동해서 화면 보여주기
			paramMethod="F"; // 화면을 보여주기 위해서 같은 영역 내부 안에 jsp페이지를 보여준다.
			url = "/board/boardContents.jsp";
		}else if (location.equals("boardModify.aws")) {
			System.out.println("boardModify.aws");
			
			String bidx = request.getParameter("bidx");
		
			//2. 처리하기(120줄부터  126줄을 복사붙여넣기 한다.
			int bidxInt = Integer.parseInt(bidx);  //120줄
			BoardDao bd = new BoardDao();      //123줄
			BoardVo bv = bd.boardSelectOne(bidxInt); //124줄
			
			request.setAttribute("bv", bv); //126줄
			
			//=============================모디파이 생성
			
			paramMethod="F"; 			
			url= "/board/boardModify.jsp";
			
			}else if(location.equals("/board/boardModifyAction.aws")) {
			System.out.println("/board/boardModifyAction.aws");
			
			String subject = request.getParameter("subject");
			String contents = request.getParameter("contents");
			String writer = request.getParameter("writer");
			String password = request.getParameter("password");
			String bidx = request.getParameter("bidx");
			
			int bidxInt = Integer.parseInt(bidx);  //120줄
			
			BoardDao bd = new BoardDao();      
			BoardVo bv = bd.boardSelectOne(bidxInt); 
			paramMethod="S"; 			
			
			//비밀번호 체크
			if(password.equals(bv.getPassword())) {
				//같으면 
				BoardDao bd2 = new  BoardDao();
				BoardVo bv2 = new BoardVo();
				bv.setSubject(subject);
				bv.setContents(contents);
				bv.setWriter(writer);
				bv.setPassword(password);
				bv.setBidx(bidxInt);
				int value = bd2.boardUpdate(bv);
				
				if(value == 1) {
					url= request.getContextPath()+"/board/boardContents.aws?bidx="+bidx;
				}else {
					url= request.getContextPath()+"/board/boardModify.aws?bidx="+bidx;
				}
			}else {
				//비밀번호가 다르면
				url= request.getContextPath()+"/board/boardModify.aws?bidx=";
			}			
		}else if(location.equals("boardRecom.aws")) {
			
			String bidx = request.getParameter("bidx");
			int bidxInt = Integer.parseInt(bidx); 
			
			BoardDao bd = new BoardDao();
			int recom = bd.boardRecomUpdate(bidxInt);
			
			PrintWriter out = response.getWriter();
			out.println("{\"recom\":\""+recom+"\"}");
			
			
			
			//paramMethod="S";
			//url="/board/boardComtents.aws?bidx=" + bidx;
		}
		
		if (paramMethod.equals("F")) {		
			RequestDispatcher rd  =request.getRequestDispatcher(url);  
			rd.forward(request, response); 				
		}else if (paramMethod.equals("S")) {
			response.sendRedirect(request.getContextPath() + url);
		}
		
		  //=====================================모디파이 생성 종료
		                     

		}
			protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				doGet(request, response);
			}
		}
		  