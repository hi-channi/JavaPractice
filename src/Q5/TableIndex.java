package Q5;

import java.util.ArrayList;
import java.util.List;

public class TableIndex
{
	private String indexName;
	private List<String> indexColumns;

	public TableIndex(String indexName)
	{
		this.indexName = indexName;
		this.indexColumns = new ArrayList<>();
	}

	public String getIndexName()
	{
		return indexName;
	}

	public void setIndexName(String indexName)
	{
		this.indexName = indexName;
	}

	public List<String> getIndexColumns()
	{
		return indexColumns;
	}

	public void setIndexColumns(List<String> indexColumns)
	{
		this.indexColumns = indexColumns;
	}

	public void addColumn(String column)
	{
		indexColumns.add(column);
	}

	@Override
	public String toString()
	{
		List<String> indexes = new ArrayList<>();
		indexes.add(indexName);
		indexes.add(indexColumns.toString());
		return indexes.toString();
	}

	public void writeHTML(StringBuilder sb, Table table)
	{
		sb.append("<td>" + getIndexName() + "</td>");
		sb.append("<td>" + String.join(", ", getIndexColumns() + "</td>"));
	}
}
