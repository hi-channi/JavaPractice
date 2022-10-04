package Q4;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvToXmlStreamConverter
{
	List<String> headers = new ArrayList<>();
	List<List<String>> allAttrValues = new ArrayList<>();

	public static void main(String[] args) throws IOException, XMLStreamException
	{
		CsvToXmlStreamConverter converter = new CsvToXmlStreamConverter();
		converter.parseCsv("D:\\Project\\JavaPractice\\input\\musicinfo.csv");
		converter.createXml("musicinfo");
	}

	public void parseCsv(String filePath) throws IOException
	{
		try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath)))
		{
			int lineCnt = 0;
			for (String line = br.readLine(); line != null; line = br.readLine())
			{
				if (lineCnt++ == 0)
				{
					headers = Arrays.asList(line.split(","));
				}
				else
				{
					allAttrValues.add(Arrays.asList(line.split(",")));
				}
			}
		}
	}

	public void createXml(String fileName) throws XMLStreamException, IOException
	{
		try (PrintWriter pw = new PrintWriter(new FileWriter("D:\\Project\\JavaPractice\\output\\" + fileName + ".xml")))
		{
			XMLStreamWriter sw = XMLOutputFactory.newFactory().createXMLStreamWriter(pw);

			sw.writeStartDocument("UTF-8", "1.0");
			sw.writeStartElement(fileName);

			for (List<String> allAttrValue : allAttrValues)
			{
				sw.writeEmptyElement("song");
				for (int i = 0, iend = headers.size(); i < iend; i++)
					sw.writeAttribute(headers.get(i), allAttrValue.get(i));
				pw.println();
			}
			sw.writeEndElement();
			pw.println();
		}
	}
}
