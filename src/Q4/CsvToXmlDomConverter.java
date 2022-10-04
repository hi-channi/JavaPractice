package Q4;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvToXmlDomConverter
{
	static List<List<String>> metaDatas = new ArrayList<>();
	static List<String> header = new ArrayList<>();
	static List<String> attrValues = new ArrayList<>();
	static List<List<String>> allAttrValues = new ArrayList<>();

	public static void main(String[] args)
	{
		csvFileRead("D:\\Project\\JavaPractice\\input\\musicinfo.csv");
		xmlFileWrite("D:\\Project\\JavaPractice\\output\\", "musicinfo");
	}

	public static void csvFileRead(String csvFilePath)
	{
		BufferedReader br = null;

		try
		{
			br = Files.newBufferedReader(Paths.get(csvFilePath));
			String line = "";

			int cnt = 0;
			while ((line = br.readLine()) != null)
			{
				if (cnt < 2)
				{
					String info[] = line.split(",");
					header = Arrays.asList(info);
					metaDatas.add(header);
					System.out.println("header: " + header);
				}
				else
				{
					String attrValue[] = line.split(",");
					attrValues = Arrays.asList(attrValue);
					allAttrValues.add(attrValues);
					System.out.println(attrValues);
				}
				cnt++;
			}
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	public static void xmlFileWrite(String savePath, String xmlFileName)
	{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		Document doc;

		try
		{
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.newDocument();

			Element rootTag = doc.createElement(metaDatas.get(0).get(0));
			doc.appendChild(rootTag);

			for (int i = 0; i < allAttrValues.size(); i++)
			{
				Element rootChildTag = doc.createElement(metaDatas.get(0).get(1));
				rootTag.appendChild(rootChildTag);

				for (int j = 0; j < attrValues.size(); j++)
				{
					rootChildTag.setAttribute(metaDatas.get(1).get(j), allAttrValues.get(i).get(j));
				}
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();

			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new FileOutputStream(new File(savePath + xmlFileName + ".xml")));
			transformer.transform(source, result);

			System.out.println("XML 파일 생성 완료!");
		}
		catch (ParserConfigurationException | TransformerException | FileNotFoundException e)
		{
			throw new RuntimeException(e);
		}
	}
}