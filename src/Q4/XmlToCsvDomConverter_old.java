package Q4;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class XmlToCsvDomConverter_old
{
	static Element rootTag;
	static String rootChildTagName;
	static String[] attrNames;
	static List<String> attrValues = new ArrayList<>();
	static List<List<String>> allAttrValues = new ArrayList<>();

	public static void main(String[] args)
	{
		xmlFileRead("input/musicinfo.xml");
		csvFileWrite("D:\\Project\\JavaPractice\\output\\", rootTag.getNodeName());
	}

	public static void xmlFileRead(String xmlFilePath)
	{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		Document doc;
		{
			try
			{
				docBuilder = docFactory.newDocumentBuilder();
				doc = docBuilder.parse(xmlFilePath);
			}
			catch (SAXException | IOException | ParserConfigurationException e)
			{
				throw new RuntimeException(e);
			}
		}

		// root 요소(최상위 태그) 가져오기
		rootTag = doc.getDocumentElement();
		// 최상위 태그 이후 indent를 node로 인식함: #Text
		Node defaultNode = rootTag.getFirstChild();
		Node rootChildTag = defaultNode.getNextSibling();
		rootChildTagName = rootChildTag.getNodeName();

		NamedNodeMap attrs = rootChildTag.getAttributes();
		int attrLength = attrs.getLength();
		attrNames = new String[attrLength];
		for (int i = 0; i < attrLength; i++)
		{
			Attr attr = (Attr) attrs.item(i);
			attrNames[i] = attr.getName();
		}
		System.out.println(Arrays.asList(attrNames));

		NodeList nodeList = doc.getElementsByTagName(rootChildTagName);
		for (int i = 0; i < nodeList.getLength(); i++)
		{
			for (int j = 0; j < attrLength; j++)
				attrValues.add(nodeList.item(i).getAttributes().getNamedItem(attrNames[j]).getNodeValue());
			allAttrValues.add(attrValues);
			attrValues = new ArrayList<>();
		}
		System.out.println(allAttrValues);
	}

	public static void csvFileWrite(String savePath, String csvFileName)
	{
		FileWriter fw = null;

		savePath += csvFileName + ".csv";
		// 최상위 노드(태그)가 fileName
		try
		{
			fw = new FileWriter(savePath);
			String attrHeader = "";
			String convertData = "";

			fw.write(csvFileName + "," + rootChildTagName + "\n");

			for (int i = 0; i < attrNames.length; i++)
			{
				attrHeader += attrNames[i] + ",";
			}
			attrHeader = attrHeader.substring(0, attrHeader.length() - 1);
			fw.write(attrHeader);
			fw.write("\n");

			for (int i = 0; i < allAttrValues.size(); i++)
			{
				for (int j = 0; j < attrNames.length; j++)
				{
					convertData += allAttrValues.get(i).get(j) + ",";
				}
				convertData = convertData.substring(0, convertData.length() - 1);
				convertData += "\n";
			}
			fw.write(convertData);
			System.out.println("CSV 파일 생성 완료!");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				fw.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}