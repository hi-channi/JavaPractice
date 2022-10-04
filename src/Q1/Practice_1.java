package Q1;

import java.util.*;

public class Practice_1
{
	public static void main(String args[])
	{
		//1-a
		System.out.println("단순 substring 사용: " + "aaa:bbb:ccc".substring(4, 7));
		System.out.println("split 사용: " + "aaa:bbb:ccc".split(":")[1]);

		String data1 = "aaa:bbb:ccc";
		int idx = data1.indexOf(':');
		System.out.println("indexOf와 substring 사용: " + data1.substring(idx + 1, data1.indexOf(':', idx + 1)));

		// 1-b, 1-c
		String[] data = {"aaa", "bbb", "_aaa", "bb_"};
		List<String> rawData = Arrays.asList(data);

		System.out.println("---반복문 사용 출력---");
		for (int i = 0; i < data.length; i++)
			if (data[i].startsWith("_"))
				System.out.println(data[i]);

		System.out.println("---스트림 사용 출력---");
		rawData.stream()    //스트림 생성
				.filter(r -> r.startsWith("_"))   //중간연산
				.forEach(r -> System.out.println(r)); //최종연산(출력)

		System.out.println("---반복문 사용 출력---");
		for (int i = 0; i < data.length; i++)
			if (data[i].endsWith("_"))
				System.out.println(data[i]);

		System.out.println("---스트림 사용 출력---");
		Arrays.asList("aaa", "bbb", "_aaa", "bb_").stream()
				.filter(r -> r.endsWith("_"))
				.forEach(r -> System.out.println(r));
	}
}
