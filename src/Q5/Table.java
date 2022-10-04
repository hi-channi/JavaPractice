package Q5;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class Table
{
	private String name;
	private List<TableColumn> columns;
	private Collection<TableIndex> indexes;

	public String getName()
	{
		return name;
	}
	public Table(String name)
	{
		this.name = name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<TableColumn> getColumns()
	{
		return columns;
	}

	public void setColumns(List<TableColumn> columns)
	{
		this.columns = columns;
	}

	public Collection<TableIndex> getIndexes()
	{
		return indexes;
	}

	public void setIndexes(Collection<TableIndex> indexes)
	{
		this.indexes = indexes;
	}


	@Override
	public String toString()
	{
		return name;
	}

	public void writeHTML(StringBuilder sb) {

		this.getColumns().sort(Comparator.comparing(TableColumn::isPrimaryKey).reversed()
				.thenComparing(TableColumn::isNullable)
				.thenComparing(TableColumn::getColumnName));
		sb.append("<a>" + getName() + "</a>");
		sb.append("<table class='table table-border' style='width: 1000px'>");
		sb.append("<tr style='background : lightgray'>");
		sb.append("<td>컬럼명</td>");
		sb.append("<td>데이터 타입</td>");
		sb.append("<td>길이</td>");
		sb.append("<td>PK</td>");
		sb.append("<td>Null</td>");
		sb.append("<td>컬럼 데이터(최빈값 순)</td>");
		sb.append("</tr>");
	}
}