package Q2;

import java.util.Map;
import java.util.TreeMap;

public class Practice_2
{
	public static void main(String[] args)
	{
		System.out.println(System.getProperties());
		// sorting
		System.out.println(new TreeMap(System.getProperties()));

		for(Map.Entry<Object, Object> e:new TreeMap<>(System.getProperties()).entrySet())
			System.out.println(e.getKey()+"="+e.getValue());

		for(Map.Entry<String, String> e:new TreeMap<>(System.getenv()).entrySet())
			System.out.println(e.getKey()+"="+e.getValue());
	}
}
