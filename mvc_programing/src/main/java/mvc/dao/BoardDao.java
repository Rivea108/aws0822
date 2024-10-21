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
			String writeday = rs.getString("writeday");
			
		 	BoardVo bv = new BoardVo(); //멤버는 memberId에서 member를 포함한 id 그러니까 memberId풀네임(컬럼명)이라 쓴것
		 	//보드는 그냥 테이블명을 안붙인  bidx, subject, contents등등이다  // 첫행부터 bv에 옮겨담기
			bv.setBidx(bidx);
			bv.setSubject(subject);
			bv.setContents(contents);
			bv.setWriter(writer);
			bv.setViewcnt(viewcnt);
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
	
	
		

	}
