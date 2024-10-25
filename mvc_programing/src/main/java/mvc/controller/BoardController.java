package mvc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.util.ArrayList;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import mvc.Vo.BoardVo;
import mvc.Vo.PageMaker;
import mvc.Vo.SearchCriteria;
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
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String paramMethod="";   //전송방식이 sendRedirect면 S   forward방식으면  F
		String url="";
		
		if (location.equals("boardList.aws") ) {//가상경로
			
			String page = request.getParameter("page");
			if(page == null) page = "1";
			//if 문을 만들때 실행문이 한개일때 {}생략가능
			int pageInt = Integer.parseInt(page); //문자를 숫자로 변경
			
			String searchType = request.getParameter("searchType");
			String keyword = request.getParameter("keyword");
			if(keyword == null) keyword = "";
			System.out.println(searchType);
			System.out.println(keyword);
			
			SearchCriteria scri = new SearchCriteria();
			scri.setPage(pageInt);
			scri.setSearchType(searchType);
			scri.setKeyword(keyword);
			
			PageMaker pm = new PageMaker();         
			pm.setScri(scri);              //<------ PageMaker에 SearchCriteria 담아서 가지고다닌다
			
			BoardDao bd = new BoardDao(); //객체생성
			//페이징 처리하기 위한 전체 데이터 갯수 가져오기
			int boardCnt = bd.boardTotalCount(scri);
			//System.out.println("게시물 수는? : " +  boardCnt );
			pm.setTotalCount(boardCnt);                          //<------ PageMaker에 전체 게시물수를 담아서 페이지계산 
			
			ArrayList<BoardVo> alist = bd.boardSelectAll(scri); 
		
			
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
		
			//저장되는 위치
			String savePath = "D:\\dev\\eclipse-workspace\\mvc_programing\\src\\main\\webapp\\images\\";
			System.out.println(savePath);
			
			//업로드 되는 파일사이즈
			int fsize = (int)request.getPart("filename").getSize();
			System.out.println("fsize:" + fsize);

			//원본 파일이름
			String originFileName="";
			if (fsize != 0) {
				Part filePart = (Part) request.getPart("filename");  //넘어온 멀티파트형식의 파일을 Part클래스로 담는다
				System.out.println("filePart==>" + filePart);
				
				originFileName = getFileName(filePart);   //파일이름 추출
				System.out.println("originFileName==>" + originFileName);

				System.out.println("저장되는 위치===>" + savePath + originFileName);

				File file = new File(savePath + originFileName);  //파일객체 생성
				InputStream is = filePart.getInputStream();   //파일 읽어들이는 스트림 생성
				FileOutputStream fos = null;

				fos = new FileOutputStream(file);   //파일 작성 및 완성하는 스트림생성

				int temp = -1;

				while ((temp = is.read()) != -1) {   //반복문을 돌려서 읽어드린 데이터를 output에 작성한다
					fos.write(temp);
				} 
				is.close();   //input 스트림 객체 소명
				fos.close(); //Output 스트림 객체소명
			}else {
				originFileName = "";			
			}
			 
			
			
			
			
			
			//1.파라미터 값을 넘겨받는다.
		String subject = request.getParameter("subject");
		String contents = request.getParameter("contents");
		System.out.println(contents);
		String writer = request.getParameter("writer");
		String password = request.getParameter("password");
		
		HttpSession session = request.getSession(); //세션 객체 불러와서 
		int midx = Integer.parseInt(session.getAttribute("midx").toString()); //로그인 할 때 담았던  세션변수 midx값을 꺼낸다
		
		String ip="";
		try {		  			
			  ip = getUserIp(request);						
		  } catch (Exception e) {			
			e.printStackTrace();
		 }
		
		
		
		BoardVo bv = new BoardVo();
		bv.setSubject(subject);
		bv.setContents(contents);
		bv.setWriter(writer);
		bv.setPassword(password);
		bv.setMidx(midx);
		bv.setFilename(originFileName);                                                  //여기서 파일네임 빼먹어서 사진 출력이 되지 않았음
		bv.setIp(ip);
		
		//2. DB처리한다..
		BoardDao bd = new BoardDao();
		int value = bd.boardInsert(bv);
		
		paramMethod="S";
		if(value == 2 ) { //입력성공
			url = request.getContextPath() + "/board/boardList.aws";
		}else { //입력실패
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
		}else if(location.equals("boardDelete.aws")) {
			String bidx = request.getParameter("bidx");
			
			request.setAttribute("bidx", bidx);
			
			paramMethod="F"; //포워드는 지역내에서 공유
			url="/board/boardDelete.jsp";//실제주소
		
		}else if (location.equals("boardDeleteAction.aws")) {
			
			String bidx = request.getParameter("bidx");
			String password = request.getParameter("password"); 
			System.out.println(1234);
		//처리하기
			BoardDao bd = new BoardDao();
			int value = bd.boardDelete(Integer.parseInt(bidx), password); //결과값이 0이면 실패 1이면 성공
			System.out.println("value" + value);
			paramMethod="S";
			if(value == 1) {
				url=request.getContextPath() + "/board/boardList.aws";
			}else {
				url=request.getContextPath() + "/board/boardDelete.aws?bidx=" + bidx;
			}
			
			paramMethod="S";
			url=request.getContextPath() + "/board/boardList.aws"; 
	
		}else if (location.equals("boardReply.aws")) {
			String bidx = request.getParameter("bidx");
			
			BoardDao bd = new BoardDao();
			BoardVo bv = bd.boardSelectOne(Integer.parseInt(bidx));
			int originbidx = bv.getOriginbidx();
			int depth = bv.getDepth();
			int level_ = bv.getLevel_();
			
			request.setAttribute("bidx", Integer.parseInt(bidx));
			request.setAttribute("originbidx", originbidx);
			request.setAttribute("depth", depth);
			request.setAttribute("level_", level_);
			
			
			paramMethod="F";
			url="/board/boardReply.jsp";
			
		}else if (location.equals("boardReplyAction.aws")) {
			System.out.println("boardReplyAction");
			
			//저장되는 위치
			String savePath = "D:\\dev\\eclipse-workspace\\mvc_programming\\src\\main\\webapp\\images\\";
			System.out.println(savePath);
			
			//업로드 되는 파일사이즈
			int fsize = (int) request.getPart("filename").getSize();
			System.out.println("fsize:" + fsize);

			//원본 파일이름
			String originFileName="";
			if (fsize != 0) {
				Part filePart = (Part) request.getPart("filename");  //넘어온 멀티파트형식의 파일을 Part클래스로 담는다
				System.out.println("filePart==>" + filePart);
				
				originFileName = getFileName(filePart);   //파일이름 추출
				System.out.println("originFileName==>" + originFileName);

				System.out.println("저장되는 위치===>" + savePath + originFileName);

				File file = new File(savePath + originFileName);  //파일객체 생성
				InputStream is = filePart.getInputStream();   //파일 읽어들이는 스트림 생성
				FileOutputStream fos = null;

				fos = new FileOutputStream(file);   //파일 작성 및 완성하는 스트림생성

				int temp = -1;

				while ((temp = is.read()) != -1) {   //반복문을 돌려서 읽어드린 데이터를 output에 작성한다
					fos.write(temp);
				} 
				is.close();   //input 스트림 객체 소멸
				fos.close(); //Output 스트림 객체소멸			
			}else {
				originFileName = "";
				
			}
			System.out.println(123);
			  //1. 파라미터값을 넘겨받는다. 
			  String subject = request.getParameter("subject"); 
			  String contents = request.getParameter("contents"); 
			  String writer =  request.getParameter("writer"); 
			  String password =  request.getParameter("password");
			  String bidx = request.getParameter("bidx");
			  String originbidx = request.getParameter("originbidx");
			  String depth = request.getParameter("depth");
			  String level_ = request.getParameter("level_");
			  
			  HttpSession session = request.getSession(); //세션 객체를 불러와서 
			  int midx = Integer.parseInt(session.getAttribute("midx").toString()); 
			  //로그인할때 담았던 세션변수 midx값을 꺼낸다
			  
			  //HttpServletRequest request2 =  ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
			  
			  String ip="";
			  try {
				  ip = getUserIp(request);
			  }catch (Exception e) {
				e.printStackTrace();
			}
			  System.out.println(ip);
			  
			  
			  
			  
			  BoardVo bv = new BoardVo(); 
			  bv.setSubject(subject); 
			  bv.setContents(contents);
			  bv.setWriter(writer); 
			  bv.setPassword(password); 
			  bv.setMidx(midx);
			  bv.setFilename(originFileName);   //파일 이름 DB컬럼 추가
			  bv.setBidx(Integer.parseInt(bidx));
			  bv.setOriginbidx(Integer.parseInt(originbidx));
			  bv.setDepth(Integer.parseInt(depth));
			  bv.setLevel_(Integer.parseInt(level_));
			  bv.setIp(ip); //ip추가
			 
			
			  BoardDao bd = new BoardDao();
			  int maxbidx = bd.boardReply(bv);    
			  
			  paramMethod="S";
			  if (maxbidx != 0) {
				  url=request.getContextPath()+"/board/boardContents.aws?bidx="+maxbidx;
			  }else {				  
				  url=request.getContextPath()+"/board/boardReply.aws?bidx="+bidx;
			  }
		} else if(location.equals("boardDownload.aws")) {
		System.out.println("boardDownload.aws");
		
		String filename = request.getParameter("filename");
		String savePath = "D:\\dev\\eclipse-workspace\\mvc_programing\\src\\main\\webapp\\images\\";
		
		ServletOutputStream sos =  response.getOutputStream();
		
		String downfile = savePath+filename;
		//System.out.println("realPath:" + downfile);
		
		File f = new File(downfile);
		
		String header = request.getHeader("User-Agent");
		
		String fileName="";
		response.setHeader("Cache-Contorl", "no-cache");
		if (header.contains("Chrome") || header.contains("Opear")) {
		
			fileName = new String(filename.getBytes("UTF-8"),"ISO-8859-1");	
			response.setHeader("Content-Disposition", "attachment;filename="+fileName);				
		
		}else if (header.contains("MSIE") || header.contains("Trident") || header.contains("Edge")) {
			
			fileName=URLEncoder.encode(filename, "UTF-8").replace("\\+", "%20");
			response.setHeader("Content-Disposition", "attachment;filename="+fileName);
		
		}else {
			response.setHeader("Content-disposition", "attachment;fileName="+filename);	
		}
		FileInputStream in = new FileInputStream(f);

		byte[] buffer = new byte[1024*8];
		
		while(true) {
			int count = in.read(buffer);
			if (count == -1) {
				break;
			}
			sos.write(buffer, 0, count);
		}
		
		in.close();
		sos.close();			
	} 	

		if (paramMethod.equals("F")) {
			RequestDispatcher rd = request.getRequestDispatcher(url);
			rd.forward(request, response);
		} else if (paramMethod.equals("S")) {
			response.sendRedirect(url);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public String getFileName(Part filePart) {

		for (String filePartData : filePart.getHeader("Content-Disposition").split(";")) {
			System.out.println(filePartData);

			if (filePartData.trim().startsWith("filename")) {
				return filePartData.substring(filePartData.indexOf("=") + 1).trim().replace("\"", "");
			}
		}
		return null;
	}
	
public String getUserIp(HttpServletRequest request) throws Exception {
		
        String ip = null;
      //  HttpServletRequest request =  ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();

        ip = request.getHeader("X-Forwarded-For");
        
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("Proxy-Client-IP"); 
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("WL-Proxy-Client-IP"); 
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("HTTP_CLIENT_IP"); 
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("X-Real-IP"); 
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("X-RealIP"); 
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("REMOTE_ADDR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getRemoteAddr(); 
        }        
        
        if (ip.equals("0:0:0:0:0:0:0:1") || ip.equals("127.0.0.1")) {
        	InetAddress address = InetAddress.getLocalHost();
        	ip = address.getHostAddress();
        }        
		
		return ip;
	}
}
