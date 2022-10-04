package Q3;

import java.util.*;

public class Practice_3ab
{
	public static void main(String[] args) {
		HashSet<Character> visited=new HashSet<>();
		DFS(createTree(), 'A', visited);
		System.out.println("\n");
		BFS(createTree(), 'A');

	}
	public static HashMap<Character, String> createTree() {
		HashMap<Character, String> map = new HashMap<>();
		map.put('A', "BCD"); // A의 인접 노드
		map.put('B', "AEF");
		map.put('C', "AG");
		map.put('D', "AHI");
		map.put('E', "BJ");
		map.put('F', "B");
		map.put('G', "CK");
		map.put('H', "D");
		map.put('I', "D");
		map.put('J', "E");
		map.put('K', "L");
		map.put('L', "K");
		return map;

	}

	public static void DFS(HashMap<Character, String> tree, char nowNode, HashSet<Character> visited) {
		visited.add(nowNode);
		System.out.printf("%c ", nowNode);
		String childNodes = tree.get(nowNode);
		for (char childNode : childNodes.toCharArray())
			if (visited.contains(childNode) == false)
				DFS(tree, childNode, visited);
	}

	public static void BFS(HashMap<Character, String> tree, char rootNode) {
		HashSet<Character> visited = new HashSet<>();
		Queue<Character> nextNodes = new LinkedList<Character>();
		visited.add(rootNode);
		nextNodes.add(rootNode);
		while (nextNodes.isEmpty() == false) {
			char nowNode = nextNodes.remove();
			System.out.printf("%c ", nowNode);
			String childNodes = tree.get(nowNode);
			for (char childNode : childNodes.toCharArray())
				if (visited.contains(childNode) == false) {
					visited.add(childNode);
					nextNodes.add(childNode);
				}
		}
	}
}
