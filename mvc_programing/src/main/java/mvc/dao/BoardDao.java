package mvc.dao;//새로생성한 주석이나 그런 것만 적음 이전 것은 멤버다오 가서보셈

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mvc.Vo.BoardVo;
import mvc.Vo.MemberVo;
import mvc.dbcon.Dbconn;

public class BoardDao { 
	
	private Connection conn; //전역적으로 쓴다 연결객체를
	
	private PreparedStatement pstmt;
	
	public BoardDao() { //1. 생성자 생성 db콘과 연결시키기 위해 Dbconn객체 생성 생성해야지만 mysql에 접속할수있다
	Dbconn db = new Dbconn(); //객체 생성
	this.conn =  db.getConnection(); //멤버??쪽으로 꺼내야한다고 하심
	}
	public ArrayList<BoardVo> boardSelectAll() { //형식먼저
		System.out.println("eeeee");
		ArrayList<BoardVo> alist = new ArrayList<BoardVo>(); //메소드 생성 영역 시작===================================================
		//ArrayList 컬렉션 객체에 BoardVo를 담겠다 BoardVo는 컬럼값을 담겠다.
	
		String sql = "select * from board where delyn='N' order by originbidx desc, depth asc";
		ResultSet rs = null;
		
	try {
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		
		while (rs.next()) {
			int bidx = rs.getInt("bidx");
			String subject = rs.getString("subject");
			String contents = rs.getString("contents");
			String writer =  rs.getString("writer");
			int viewcnt = rs.getInt("viewcnt");
			String writeday = rs.getString("writeday");
			
		 	BoardVo bv = new BoardVo(); //멤버는 memberId에서 member를 포함한 id 그러니까 memberId풀네임(컬럼명)이라 쓴것
		 	//보드는 그냥 테이블명을 안붙인  bidx, subject, contents등등이다 
			bv.setBidx(bidx);
			bv.setSubject(subject);
			bv.setContents(contents);
			bv.setWriter(writer);
			bv.setViewcnt(viewcnt);
			bv.setWriteday(writeday);
			
			alist.add(bv); 
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
	}
