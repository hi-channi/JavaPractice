package Q5;

import java.io.*;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class HTMLWriter
{
	public static void main(String[] args) throws SQLException, IOException
	{
		writeHTML("D:\\Project\\JavaPractice\\output\\", "tablespec");
	}

	public static void writeHTML(String savePath, String htmlFileName) throws SQLException, IOException
	{
		StringBuilder sb = new StringBuilder();
		File file = new File(savePath + htmlFileName + ".html");
		GetDBMetadata getData = new GetDBMetadata();
		List<Table> tables = getData.getAllTables("SCOTT");

		try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file))))
		{
			sb.append("<html><head>")
					.append("<meta charset='utf-8'>")
					.append("<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css'>")
					.append("<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js'></script>")
					.append("</head>")
					.append("<body>")
					.append("<h2>테이블 명 목록</h2>");
			for (Table table : tables)
				sb.append("<span>" + table.getName() + "</span>" + "<br>");
			sb.append("<hr style='border: 1px solid;'>");
			for (Table table : tables)
			{
				Collections.sort(table.getColumns());
				table.writeHTML(sb);
				for (TableColumn column : table.getColumns())
					column.writeHTML(sb);
				if (table.getIndexes().size() != 0)
					for (TableIndex index : table.getIndexes())
					{
						sb.append("<tr style='background : lightgray !important;'>");
						sb.append("<td>인덱스명</td>");
						sb.append("<td colspan='5'>컬럼</td>");
						sb.append("</tr>");
						sb.append("<tr>");
						index.writeHTML(sb, table);
					}
				else
					sb.append("<tr><td colspan='6'>테이블에 생성된 인덱스가 존재하지 않습니다.</td></tr>");
				sb.append("</table>");
			}
			sb.append("</body>");
			sb.append("</html>");
			writer.println(sb);
		}
	}
}
