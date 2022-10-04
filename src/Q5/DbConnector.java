package Q5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

// 여러 클래스에서 필요시마다 호출할 DB 클래스
public class DbConnector
{
	static final String ORACLE_LOCAL = "jdbc:oracle:thin:@localhost:1521:juliv";

	public Connection getConnection()
	{
		Connection conn = null;
		try
		{
		conn = DriverManager.getConnection(ORACLE_LOCAL, "SCOTT", "tiger");
		}
		catch (SQLException e)
		{
			System.out.println("==DBMS 연결 실패:" + e.getMessage());
		}
		return conn;
	}
	public void dbClose(ResultSet rs, Connection conn)
	{
		try
		{
			if (rs != null)
				rs.close();
			if (conn != null)
				conn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	public void dbClose(Connection conn)
	{
		try
		{
			if (conn != null)
				conn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}