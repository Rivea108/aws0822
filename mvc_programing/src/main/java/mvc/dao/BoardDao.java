package mvc.dao;//새로생성한 주석이나 그런 것만 적음 이전 것은 멤버다오 가서보셈

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mvc.Vo.BoardVo;
import mvc.Vo.Criteria;
import mvc.Vo.MemberVo;
import mvc.dbcon.Dbconn;

public class BoardDao { 
	
	private Connection conn; //전역적으로 쓴다 연결객체를
	
	private PreparedStatement pstmt;//쿼리 실행을 위한 구문객체
	
	public BoardDao() { //1. 생성자 생성 db콘과 연결시키기 위해 Dbconn객체 생성 생성해야지만 mysql에 접속할수있다
	Dbconn db = new Dbconn(); //객체 생성
	this.conn =  db.getConnection(); //멤버??쪽으로 꺼내야한다고 하심
	}
	public ArrayList<BoardVo> boardSelectAll(Criteria cri) { //형식먼저
		int page = cri.getPage(); //페이지번호
		int perPageNum = cri.getPerPageNum(); //화면노출 개수
		
		ArrayList<BoardVo> alist = new ArrayList<BoardVo>(); //메소드 생성 영역 시작===================================================
		//ArrayList 컬렉션 객체에 BoardVo를 담겠다 BoardVo는 컬럼값을 담겠다.
	
		String sql = "select * from board order by originbidx desc, depth asc limit ?,?";
		ResultSet rs = null;
		
	try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, (page-1)*perPageNum);
		pstmt.setInt(2, perPageNum);
		rs = pstmt.executeQuery();
		
		while (rs.next()) { //커서가 다음으로 이동해서 첫 글이 있느냐 물어보고 true면 진행
			int bidx = rs.getInt("bidx");
			String subject = rs.getString("subject");
			String contents = rs.getString("contents");
			String writer =  rs.getString("writer");
			int viewcnt = rs.getInt("viewcnt");
			int recom = rs.getInt("recom");
			String writeday = rs.getString("writeday");
			
		 	BoardVo bv = new BoardVo(); //멤버는 memberId에서 member를 포함한 id 그러니까 memberId풀네임(컬럼명)이라 쓴것
		 	//보드는 그냥 테이블명을 안붙인  bidx, subject, contents등등이다  // 첫행부터 bv에 옮겨담기
			bv.setBidx(bidx);
			bv.setSubject(subject);
			bv.setContents(contents);
			bv.setWriter(writer);
			bv.setViewcnt(viewcnt);
			bv.setRecom(recom);
			bv.setWriteday(writeday);
			
			alist.add(bv); // ArrayList객체에 하나씩 추가한다
		}
		
	} catch (SQLException e) {			
		e.printStackTrace();
	} finally {
		try {
			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {			
			e.printStackTrace();
		}					
	}		
	return alist;
}//메소드 생성 영역 종료 ========================================================================================================
	
	//20241021 메소드 짜기
	
	//게시판 전체 갯수 구하기
	public int boardTotalCount() {
		
		int value = 0;
		//1. 쿼리 만들기
		String sql = "select COUNT(*) AS cnt from board where delyn = 'N'";
		//2. conn 객체 안에 있는 구문 클래스 호출(생성)
		//3. DB 컬럼값을 받는 전용 클래스 ResultSet 호출(ResultSet 특징은 데이터를 그대로 복사하기 때문에 전달이 빠름)
		ResultSet rs = null;
		try { //각 객체부터 소멸시키다.DB연결 끊는다 
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) { //커서를 이동시켜서 첫줄로 옮긴다
				value = rs.getInt("cnt"); //지역변수 value 답아서 리턴해서 가져간다
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {     // 각 객체도 소멸시키고 DB연결 끊는다
				rs.close();
				pstmt.close();
			//	conn.close();
			} catch (SQLException e) {			
				e.printStackTrace();
			}			
		}	
		return value;
	}
	
	public int boardInsert(BoardVo bv) {
		int value=0;
		
		String subject = bv.getSubject();
		String contents = bv.getContents();
		String writer = bv .getWriter();
		String password = bv.getPassword();
		int midx =bv.getMidx();
		
		String sql="insert into board(originbidx, depth, level_, subject, contents, writer, password, midx)"
				+ "value(null, 0, 0, ?, ?, ?, ?, ?)";
		
		String sql2 = "update board set originbidx = (select A.maxbidx from (select max(bidx) as maxbidx from board)A) "
				+ "where bidx= (select A.maxbidx from (select max(bidx) as maxbidx from board)A)";
	
		try {
			conn.setAutoCommit(false); //수동커밋으로 전환
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, subject);
			pstmt.setString(2, contents);
			pstmt.setString(3, writer);
			pstmt.setString(4, password);
			pstmt.setInt(5, midx);
			int exec = pstmt.executeUpdate(); //실행되면 1 안되면 0
			
			pstmt = conn.prepareStatement(sql2);
			int exec2 = pstmt.executeUpdate(); //실행되면 1 안되면 0
		
			conn.commit(); //일괄처리 커밋
			
			value = exec+exec2; 
			
		} catch (SQLException e) {
			
			try {
				conn.rollback(); //실행중 오류발생시 롤백처리
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			try {     // 각 객체도 소멸시키고 DB연결 끊는다
				pstmt.close();
			    conn.close();
			} catch (SQLException e) {			
				e.printStackTrace();
			}			
		
		}	
		
		return value;
	}
	
	public BoardVo boardSelectOne(int bidx) {
		//1. 형식부터 만든다
		BoardVo bv = null;
		//2. 사용할 쿼리를 준비한다.
		String sql = "select * from board where delyn='N' AND bidx=?";
		
		ResultSet rs = null;
		try {
			//3. conn 연결객체에서 구문쿼리실행 구문클래스를 불러온다.
			pstmt = conn.prepareStatement(sql); //멤버번수(전역변수)로 선언한 PrepareStatement 객체로 담음
			pstmt.setInt(1, bidx); //첫번째 물음표에 매개변수 bidx값을 담아서 구문을 완성한다                                                            	     161줄로 대입됨 
			rs = pstmt.executeQuery(); //쿼리를 실행해서 결과값을 컬럼전용 클래스인 ResultSet 객체에 담는다(복사기능).               	 163줄로 대입됨
			
			if(rs.next() == true) { //rs.next()는 커서를 다음줄로 이동시킨다. 맨처름 커서는 상단에 위치되어있다.
				//값이 존재한다면 BoardVo 객체에 담는다.
				String subject = rs.getString("subject");
				String contents = rs.getString("contents");
				System.out.println("asdasda");
				String writer = rs.getString("writer");
				String writeday = rs.getString("writeday");
				int viewcnt = rs.getInt("viewcnt");
				int recom = rs.getInt("recom");
				String filename = rs.getString("filename");
				int rtnBidx = rs.getInt("bidx");
				int originbidx = rs.getInt("originbidx");
				int depth = rs.getInt("depth");
				int level_ = rs.getInt("level_");
				String password = rs.getString("password");
				
				bv = new BoardVo(); //객체생성해서 지역변수 bv로 담아서 리턴해서 가져간다
				bv.setSubject(subject);
				bv.setContents(contents);
				bv.setWriter(writer);
				bv.setWriteday(writeday);
				bv.setViewcnt(viewcnt);
				bv.setRecom(recom);
				bv.setFilename(filename);
				bv.setBidx(rtnBidx);
				bv.setOriginbidx(originbidx);
				bv.setDepth(depth);
				bv.setLevel_(level_);
				bv.setPassword(password);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {     // 각 객체도 소멸시키고 DB연결 끊는다
				rs.close();
				pstmt.close();
			    conn.close();
			} catch (SQLException e) {			
				e.printStackTrace();
			}			
		}
		System.out.println("subject" + bv.getSubject());
		return bv;
	}
	
	//게시물 수정하기
	public int boardUpdate(BoardVo bv) {
		
		int value = 0;
		String sql = "update board set subject='?',contents='?',writer='?',modifyday=now() where bidx=? and password=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bv.getSubject());
			pstmt.setString(2, bv.getContents());
			pstmt.setString(3, bv.getWriter());
			pstmt.setInt(4, bv.getBidx());
			pstmt.setString(5, bv.getPassword());
			value =  pstmt.executeUpdate();//실행하는 부분
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {     // 각 객체도 소멸시키고 DB연결 끊는다
				pstmt.close();
			    conn.close();
			} catch (SQLException e) {			
				e.printStackTrace();
			}			
		}
		return value;
	}
public int boardViewCntUpdate(int bidx) { //스스로 완성하기
	
int value=0;
String sql = "update board SET viewcnt = viewcnt+1 where bidx=?";	 //DB
	
	try {
	pstmt = conn.prepareStatement(sql);	//sql쿼리 실행문 //pstmt = conn.prepareStatement(sql); 이걸 적어야지만 251줄에 SQLException e 나옴
	pstmt.setInt(1, bidx); //?부분만 하면 됨 만약 DB문에 ?가 5개이면 5개를 10개면 10개를 select * from이라면 전부다를
	value =  pstmt.executeUpdate();//실행하는 부분, 성공하면 1 실패하면 0
	}catch(SQLException e) {//캐치문 
		e.printStackTrace();// 개발자가 프로그램을 실행할 때 발생한 예외가 어떤 경로를 통해 발생했는지 추적하고 문제를 진단하기 위해 사용하는 디버깅 코드
	}finally {
		try {     // 각 객체도 소멸시키고 DB연결 끊는다
			
			//이 close문은 좀 물어봐야할 듯
			//rs.close(); 이거는 자세히 알아봐야함
			pstmt.close(); //이거는 자세히 알아봐야함
		    //conn.close(); //이거는 자세히 알아봐야함
		    
		} catch (SQLException e) {			
			e.printStackTrace();// 개발자가 프로그램을 실행할 때 발생한 예외가 어떤 경로를 통해 발생했는지 추적하고 문제를 진단하기 위해 사용하는 디버깅 코드
		}			
	}	

	return value; //실패,성공 결과값가져오기(실패해도 잘했다고하심, 결국 1이 성공 0이 실패이다보니 실패도 선택지에 있다)실행되면 문제 없 
}

public int boardRecomUpdate(int bidx) {
	
	int value = 0;
	int recom = 0;
	String sql =  "update board set recom = recom+1 where bidx=?";
	String sql2 = "select recom from board where bidx=?";
	ResultSet rs = null;
	
	try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, bidx);
		value = pstmt.executeUpdate();
		
		pstmt = conn.prepareStatement(sql2);
		pstmt.setInt(1, bidx);
		rs = pstmt.executeQuery();
		
		if(rs.next()) {
			recom = rs.getInt("recom");
		}
		
		
	} catch (SQLException e) {
		e.printStackTrace();
	}finally {
		try {     // 각 객체도 소멸시키고 DB연결 끊는다
			
			//이 close문은 좀 물어봐야할 듯
			//rs.close(); 이거는 자세히 알아봐야함
			pstmt.close(); //이거는 자세히 알아봐야함
		    conn.close(); //이거는 자세히 알아봐야함
		    
		} catch (SQLException e) {			
			e.printStackTrace();// 개발자가 프로그램을 실행할 때 발생한 예외가 어떤 경로를 통해 발생했는지 추적하고 문제를 진단하기 위해 사용하는 디버깅 코드
		}			
	}		
	return recom;
}


	}