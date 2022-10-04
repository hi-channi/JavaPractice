package Q6;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetColumnMode
{
	public String getColumnMode(String column, String tableName, Connection conn) throws SQLException
	{
		String query = String.format("select %s, count(%s) as cnt from %s group by %s order by cnt desc", column, column, tableName, column);
		try (PreparedStatement pstmt = conn.prepareStatement(query);
			 ResultSet rs = pstmt.executeQuery())
		{
			List<String> list = new ArrayList<>();
			while (rs.next() && list.size() < 6)
				list.add(list.size() < 5 ? rs.getString(column) + "(" + rs.getInt("cnt") + ")" : "...");
			String mode = String.join(", ", list);
			return mode;
		}
	}
}
