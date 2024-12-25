package remotesoft.mymoney.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import remotesoft.mymoney.domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;


public class JdbcMemberRepository implements MemberRepository {
	
	/*---CONST---*/
	public enum QUERY {
	// java에서 enum은 클래스이고, 선언된 상수각각은 enum타입의 맴버객체이다.
	// 초기화 인자에 의해 타입이 추론되며 (여기서는 String"", 선언할 타입이 일반UINT라도 명시해야한다.)
	// javac실행시 선언된 맴버변수들을 매개변수로하는 생성자를 암묵적으로 호출하여, 선언된 맴버변수이름의 객체를 생성
		/* Implicit static block (conceptual) static { 
				public static final QUERY INSERT_NAME 
					= new QUERY("INSERT INTO member(name) VALUES(?)");
				//-- This block is not actually present in your code. 
				//-- The enum constants are initialized at class load time. 
		  } */
		/*class Node { //cpp에서도 자신이 속한 클래스타입의 맴버변수를 가질수 있다.
		  public:
		    int data;
		    Node* next;  // Pointer to another Node
			Node(int _data) : data(_data), next(nullptr) {}
		  }; */
		
		INSERT_NAME("INSERT INTO member(name) values(?)"),
		SELECT_ID("SELECT * FROM member WHERE id = ?"),
		SELECT_NAME("SELECT * FROM member WHERE name = ?"),
		SELECT_ALL("SELECT * FROM member");
		
		//DELETE
		//EDIT
		//...
		
		// in java: enum은 생성자에 의해 초기화되는 객체이고 객체의 맴버변수에대한 선언,초기화,getter가 필요
		private final String m_query;
		QUERY(String declaredConst_fromJava) { this.m_query = declaredConst_fromJava; }
		public String str() { return m_query; }
	}
	// in use: String query = QUERY.INSERT_NAME.str();
	// in java: ENUM.CONST -> 이넘 객체가 해당const로 초기화 됨
	
	/*---Variables---*/
	private final DataSource m_dataSource;
	
	public JdbcMemberRepository(DataSource _fromSpring) {
		this.m_dataSource = _fromSpring;
	}
	
	/*---Implements---*/
	private Connection getConnectionDB() {
		return DataSourceUtils.getConnection(m_dataSource);
		/*DataSource에서 바로 getConnection()할수도 있지만, 그렇게 하면 매번 새로운 Connection 반환됨
		 *DataSourceUtils를 통하면, 기존 커넥션 상태를 유지시켜줌(트렌젝션등), 해제(close)시도 마찬가지 */
	}
	private void closeDB(Connection connDB, PreparedStatement pstmtQuery, ResultSet rtQuery) {
		try	{
			if (rtQuery != null ) {
				rtQuery.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (pstmtQuery != null) {
				pstmtQuery.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (connDB != null) {
				closeDB(connDB);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private void closeDB(Connection connDB) throws SQLException {
		DataSourceUtils.releaseConnection(connDB, m_dataSource);
	}
	
	@Override
	public Member save(Member member) {
		String strInsertMember = QUERY.INSERT_NAME.str();
		Connection connDB = null; // DB연결 객체
		PreparedStatement pstmtQuery = null; // 쿼리를 미리컴파일해서 실행준비,저장
		ResultSet rtQuery = null; // 쿼리결과를 저장, 중복없는 컨테이너 Set을 의미하지않음, 중복허용
		
		try	{
			connDB = getConnectionDB();
			pstmtQuery = connDB.prepareStatement(strInsertMember, Statement.RETURN_GENERATED_KEYS);
			pstmtQuery.setString(1, member.getName()); // strInserMember의 1번 param, 즉 첫번쨰 ? 와 매칭됨
			pstmtQuery.executeUpdate();
			rtQuery = pstmtQuery.getGeneratedKeys(); // Statement.RETURN_GENERATED_KEYS 매칭
			
			if (rtQuery.next()) {
				member.setId(rtQuery.getLong(1));
			}
			else {
				throw new SQLException("id 조회실패?");
			}
			return member;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			closeDB(connDB, pstmtQuery, rtQuery); // 중요 
		}
	}

	@Override
	public Optional<Member> findById(Long id) {

		String strSelectMember = QUERY.SELECT_ID.str();
		Connection connDB = null; // DB연결 객체
		PreparedStatement pstmtQuery = null; // 쿼리를 미리컴파일해서 실행준비,저장
		ResultSet rtQuery = null; // 쿼리결과를 저장, 중복없는 컨테이너 Set을 의미하지않음, 중복허용
		
		try	{
			connDB = getConnectionDB();
			pstmtQuery = connDB.prepareStatement(strSelectMember);
			pstmtQuery.setLong(1, id);
			rtQuery = pstmtQuery.executeQuery();
			
			if (rtQuery.next()) {
				Member member = new Member();
				member.setId(rtQuery.getLong("id"));
				member.setName(rtQuery.getString("name"));
				return Optional.of(member);
			} else {
				return Optional.empty();
			}			
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			closeDB(connDB, pstmtQuery, rtQuery); // 중요 
		}
	}

	@Override
	public Optional<Member> findByName(String name) {

		String strSelectMember = QUERY.SELECT_NAME.str();
		Connection connDB = null; // DB연결 객체
		PreparedStatement pstmtQuery = null; // 쿼리를 미리컴파일해서 실행준비,저장
		ResultSet rtQuery = null; // 쿼리결과를 저장, 중복없는 컨테이너 Set을 의미하지않음, 중복허용
		
		try	{
			connDB = getConnectionDB();
			pstmtQuery = connDB.prepareStatement(strSelectMember);
			pstmtQuery.setString(1, name);
			rtQuery = pstmtQuery.executeQuery();
			
			if (rtQuery.next()) {
				Member member = new Member();
				member.setId(rtQuery.getLong("id"));
				member.setName(rtQuery.getString("name"));
				return Optional.of(member);
			} 
			return Optional.empty();			
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			closeDB(connDB, pstmtQuery, rtQuery); // 중요 
		}
	}

	@Override
	public List<Member> findAll() {
		
		String strSelectMember = QUERY.SELECT_ALL.str();
		Connection connDB = null; // DB연결 객체
		PreparedStatement pstmtQuery = null; // 쿼리를 미리컴파일해서 실행준비,저장
		ResultSet rtQuery = null; // 쿼리결과를 저장, 중복없는 컨테이너 Set을 의미하지않음, 중복허용
		
		try	{
			connDB = getConnectionDB();
			pstmtQuery = connDB.prepareStatement(strSelectMember);

			rtQuery = pstmtQuery.executeQuery();
			
			List<Member> members = new ArrayList<>();
			while(rtQuery.next()) {
				Member member = new Member();
				member.setId(rtQuery.getLong("id"));
				member.setName(rtQuery.getString("name"));
				members.add(member);
			}
			return members;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			closeDB(connDB, pstmtQuery, rtQuery); // 중요 
		}
	}

}
