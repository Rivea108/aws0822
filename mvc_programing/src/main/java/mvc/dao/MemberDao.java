package mvc.dao; //모델1

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mvc.Vo.MemberVo;
import mvc.dbcon.Dbconn;

public class MemberDao { //mvc 방식으로 가기전에 첫번째 model1 방식(객체들을 만들어 놓고 쓰는 방식)

	private Connection conn; // 전역변수로 사용 페이지 어느곳에서나 호출 사용 가능(전연변수 이기에)
	private PreparedStatement pstmt;
	// 생성자를 통해서 db연결해서 메소드 사용
	public MemberDao() {
		Dbconn dbcoon = new Dbconn(); // DB색체 생성
		conn = dbcoon.getConnection(); // 메소드, 호출해서 연결객체를 가져온다
	}

	// public : 어디서나 접근 가능, 리턴값 타입은 숫자(int)형 = 메소드타입과 같음, 각 매개변수(파라미터변수-전달변수)
	public int memberInsert(String memberId, String memberPwd, String memberName, String memberGender,
			String memberBirth, String memberAddr, String memberPhone, String memberEmail, String memberInHobby) {

		int value = 0; // 메소드 지역변수 결과값을 담는다
		String sql = "";
		//PreparedStatement pstmt = null;// 쿼리 구문클래스 선언
		try {

			sql = "insert into member(memberid,memberpwd,membername,"
					+ "membergender,memberbirth,memberaddr,memberphone,"
					+ "memberemail,memberhobby) values(?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId); // 문자형메소드 사용
			pstmt.setString(2, memberPwd); // 문자형메소드 사용
			pstmt.setString(3, memberName); // 문자형메소드 사용
			pstmt.setString(4, memberGender); // 문자형메소드 사용
			pstmt.setString(5, memberBirth); // 문자형메소드 사용
			pstmt.setString(6, memberAddr); // 문자형메소드 사용
			pstmt.setString(7, memberPhone); // 문자형메소드 사용
			pstmt.setString(8, memberEmail); // 문자형메소드 사용
			pstmt.setString(9, memberInHobby); // 문자형메소드 사용 //만약 문자형이 아닌 숫자형라면 pstmt.setInt
			value = pstmt.executeUpdate();// 실행에 성공하면 1 실패하면 0 리턴

		} catch (Exception e) {
			e.printStackTrace();
		} finally { // try를 했던 catch를 했던 꼭 실행해야하는 영역
			// 객체 사라지게하고
			// db연결 끊기
			try {
				pstmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return value;
	}
	
	//로그인을 통해서 회원정보를 담아오는 메소드이다.
	public MemberVo memberLoginCheck(String memberId,String memberPwd) {
		 MemberVo mv = null;
		String sql = "select * from member where memberid = ? and memberpwd = ?"; //?는 들어갈 변수 대신 나중에 사용할땐 db에 가서 쿼리가 맞는지 확인할것 그런 다음 붙여넣기할것
 		ResultSet rs = null; //db에서 결과데이터를 받아오는 전용 클래스
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPwd);
			rs = pstmt.executeQuery();
			
			if(rs.next() == true) { //커서가 이동해서 데이터 값이 있으면 if(rs.next()) 와 같은 표현
				String memberid = rs.getString("memberid");	     //결과 값에서 아이디값을 뽑는다
				int midx = rs.getInt("midx");                                   //결과 값에서 회원번호를 뽑는다.
				String membername =  rs.getString("membername");
				
			  mv = new MemberVo(); //화면에 가지고 갈 데이터를 담을 vo객체생성
			  mv.setMemberid(memberid); //옮겨담는다
			  mv.setMidx(midx); //
			  mv.setMembername(membername);
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return mv;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
