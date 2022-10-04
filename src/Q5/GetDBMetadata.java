package Q5;

import Q6.GetColumnMode;

import java.sql.*;
import java.util.*;

public class GetDBMetadata
{
	DbConnector db = new DbConnector();
	Connection conn = db.getConnection();
	GetColumnMode mode = new GetColumnMode();

	public static void main(String[] args) throws SQLException
	{
		GetDBMetadata getData = new GetDBMetadata();
		List<Table> tables = getData.getAllTables("SCOTT");    // DBConnector의 user와 일치시킬 것

		for (Table table : tables)
		{
			table.getColumns().sort(Comparator.comparing(TableColumn::isPrimaryKey).reversed().thenComparing(TableColumn::isNullable).thenComparing(TableColumn::getColumnName));
			System.out.println(table.getName());
			System.out.println("컬럼명\t데이터타입\t크기\tPK\tNullable");
			for (TableColumn column : table.getColumns())
			{
				System.out.println(column);
			}
			System.out.println(table.getIndexes().toString());
		}
	}

	public List<Table> getAllTables(String username) throws SQLException
	{
		DatabaseMetaData dbm = conn.getMetaData();
		try
		{
			List<Table> tables = getTables(username, dbm);

			for (Table table : tables)
			{
				Set<String> pks = getPks(dbm, table);
				table.setColumns(getColumns(dbm, table, pks));
				table.setIndexes(getIndexMap(dbm, table).values());
				for (TableColumn column : table.getColumns())
					column.setMode(mode.getColumnMode(column.getColumnName(), table.getName(), conn));
			}
			return tables;
		}
		finally
		{
			db.dbClose(conn);
		}
	}

	private static List<Table> getTables(String username, DatabaseMetaData dbm) throws SQLException
	{
		List<Table> tables = new ArrayList<>();
		try (ResultSet rs = dbm.getTables(null, username, null, new String[]{"TABLE"}))
		{
			while (rs.next())
			{
				Table table = new Table(rs.getString("TABLE_NAME"));
				tables.add(table);
			}
		}
		return tables;
	}

	private static Map<String, TableIndex> getIndexMap(DatabaseMetaData dbm, Table table) throws SQLException
	{
		Map<String, TableIndex> map = new HashMap<>();

		try (ResultSet rs_i = dbm.getIndexInfo(null, null, table.getName(), true, false))
		{
			while (rs_i.next())
			{
				String idxName = rs_i.getString("INDEX_NAME");
				String columnName = rs_i.getString("COLUMN_NAME");

				if (idxName != null)
					map.computeIfAbsent(idxName, v -> new TableIndex(v)).addColumn(columnName);
			}
		}
		return map;
	}

	private static List<TableColumn> getColumns(DatabaseMetaData dbm, Table table, Set<String> pks) throws SQLException
	{
		List<TableColumn> columns = new ArrayList<>();

		try (ResultSet rs_c = dbm.getColumns(null, null, table.getName(), null))
		{

			while (rs_c.next())
			{
				columns.add(new TableColumn(rs_c, pks));
			}
		}
		return columns;
	}

	private static Set<String> getPks(DatabaseMetaData dbm, Table table) throws SQLException
	{
		Set<String> pks = new HashSet<>();
		try (ResultSet rs_pk = dbm.getPrimaryKeys(null, null, table.getName()))
		{
			while (rs_pk.next())
				pks.add(rs_pk.getString("COLUMN_NAME"));
		}
		return pks;
	}
}
