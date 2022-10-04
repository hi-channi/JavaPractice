package Q4;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class XmlToCsvDomConverter
{
	Element rootTag;
	String rootChildTagName;
	String[] attrNames;
	List<String> attrValues = new ArrayList<>();
	List<List<String>> allAttrValues = new ArrayList<>();

	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException
	{
		XmlToCsvDomConverter converter = new XmlToCsvDomConverter();
		converter.xmlFileRead("input/musicinfo.xml");
		converter.csvFileWrite("D:\\Project\\JavaPractice\\output\\", converter.rootTag.getNodeName());
	}

	public void xmlFileRead(String xmlFilePath) throws IOException, SAXException, ParserConfigurationException
	{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		;
		Document doc = docBuilder.parse(xmlFilePath);

		// root 요소(최상위 태그) 가져오기
		rootTag = doc.getDocumentElement();
		// 최상위 태그의 첫번째 노드는 #Text
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
			{
				NamedNodeMap map = nodeList.item(i).getAttributes();
				attrValues.add(map.getNamedItem(attrNames[j]).getNodeValue());
			}
			allAttrValues.add(attrValues);
			attrValues = new ArrayList<>();
		}
		System.out.println(allAttrValues);
	}

	public void csvFileWrite(String savePath, String csvFileName) throws IOException
	{
		savePath += csvFileName + ".csv";
		// 최상위 노드(태그)가 fileName
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(savePath)))
		{
			String attrHeader = "";
			String convertData = "";

			// 포함된 태그 가져오기
			bw.write(csvFileName + "," + rootChildTagName + "\n");

			for (String attrName : attrNames)
				attrHeader += attrName + ",";
			attrHeader = attrHeader.substring(0, attrHeader.length() - 1);
			bw.write(attrHeader);
			bw.write("\n");

			for (List<String> allAttrValue : allAttrValues)
			{
				for (int j = 0; j < attrNames.length; j++)
					convertData += allAttrValue.get(j) + ",";
				// 한 row 출력이 끝나면 마지막에 추가된 콤마를 삭제
				convertData = convertData.substring(0, convertData.length() - 1);
				// 다음 row에 추가하기 위한 줄바꿈
				convertData += "\n";
			}
			bw.write(convertData);
			System.out.println("CSV 파일 생성 완료!");
		}

	}
}