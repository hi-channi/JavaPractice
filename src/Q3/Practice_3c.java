package Q3;

import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;

import java.util.*;

public class Practice_3c
{
	public static HashMap<Character, String> createTree()
	{
		HashMap<Character, String> map = new HashMap<>();
		map.put('A', "B, C, D");
		map.put('B', "E, F");
		map.put('C', "G");
		map.put('D', "H, I");
		map.put('E', "J");
		map.put('F', "");
		map.put('G', "K");
		map.put('H', "");
		map.put('I', "");
		map.put('J', "");
		map.put('K', "L");
		map.put('L', "");
		return map;
	}

	public static void findRootPath(Map<Character, String> tree, char startNode)	// 탐색할 트리, 시작할 노드
	{
		for (Map.Entry<Character, String> node : tree.entrySet())
			if (node.getValue().contains(String.valueOf(startNode)))
				findRootPath(tree, node.getKey());

		System.out.printf("/%s", startNode);
	}

	public static void main(String[] args)
	{
		findRootPath(createTree(), 'L');
	}
}
