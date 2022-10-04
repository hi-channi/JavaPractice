package Q5;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TableColumn implements Comparable<TableColumn>
{

	private String columnName;
	private String columnType;
	private int columnSize;
	private boolean isPrimaryKey;
	private boolean isNullable;
	private String mode;

	public TableColumn(String columnName, boolean isPrimaryKey, String columnType, int columnSize, boolean isNullable)
	{
		this.columnName = columnName;
		this.columnType = columnType;
		this.columnSize = columnSize;
		this.isPrimaryKey = isPrimaryKey;
		this.isNullable = isNullable;
	}

	public TableColumn(ResultSet rs_c, Set<String> pks) throws SQLException
	{
		this(rs_c.getString(4), pks.contains(rs_c.getString(4)), rs_c.getString(6), rs_c.getInt(7), rs_c.getString(18).equals("YES"));
	}

	public String getColumnName()
	{
		return columnName;
	}

	public void setColumnName(String columnName)
	{
		this.columnName = columnName;
	}

	public String getColumnType()
	{
		return columnType;
	}

	public void setColumnType(String columnType)
	{
		this.columnType = columnType;
	}

	public int getColumnSize()
	{
		return columnSize;
	}

	public void setColumnSize(int columnSize)
	{
		this.columnSize = columnSize;
	}

	public boolean isPrimaryKey()
	{
		return isPrimaryKey;
	}

	public void setPrimaryKey(boolean primaryKey)
	{
		isPrimaryKey = primaryKey;
	}

	public boolean isNullable()
	{
		return isNullable;
	}

	public void setNullable(boolean nullable)
	{
		isNullable = nullable;
	}

	public String getMode()
	{
		return mode;
	}

	public void setMode(String mode)
	{
		this.mode = mode;
	}

	@Override
	public String toString()
	{
		List<Object> table = new ArrayList<>();
		table.add(columnName);
		table.add(columnType);
		table.add(columnSize);
		table.add(isPrimaryKey);
		table.add(isNullable);
		return table.toString();
	}

	public void writeHTML(StringBuilder sb)
	{
		sb.append("<tr>");
		sb.append("<td>" + getColumnName() + "</td>");
		sb.append("<td>" + getColumnType() + "</td>");
		sb.append("<td>" + getColumnSize() + "</td>");
		sb.append("<td>" + (isPrimaryKey() ? "PK" : "") + "</td>");
		sb.append("<td>" + (isNullable() ? "" : "NN") + "</td>");
		sb.append("<td>");

		sb.append(mode);
		sb.append("</td>");
		sb.append("</tr>");
	}

	/* sorting 방법 1
	public int getSeq()
	{
		return isPrimaryKey ? 1 : isNullable ? 3 : 2;
	}
	@Override
	public int compareTo(TableColumn o)
	{
		int d = getSeq() - o.getSeq();
		return d == 0 ? columnName.compareTo(o.columnName) : d;
	*/

	// sorting 방법 2
	public String getOrder()
	{
		return isPrimaryKey ? "a" : isNullable ? "c" : "b";
	}

	@Override
	public int compareTo(TableColumn o)
	{
		return (getOrder() + columnName).compareTo(o.getOrder() + o.columnName);

	}

	/* sorting 방법 3
	@Override
	public int compareTo(TableColumn o)
	{
		int c1 = ((isPrimaryKey) ? 1 : 0) - ((o.isPrimaryKey) ? 1 : 0);
		int c2 = ((isNullable) ? 1 : 0) - ((o.isNullable) ? 1 : 0);
		int c3 = columnName.compareTo(o.columnName);

		if (c1 > 0)
			return -1;
		else if (c1 == 0)
		{
			if (c2 > 0)
				return 1;
			else if (c2 == 0)
				if (c3 > 0)
					return 1;
				else if (c3 == 0)
					return 0;
				else
					return -1;
			else
				return -1;
		}
		else
			return 1;
	}
	*/
}
