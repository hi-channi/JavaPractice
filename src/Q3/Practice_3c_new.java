package Q3;

import java.util.*;

public class Practice_3c_new
{
	public static void main(String[] args)
	{
		HashMap<String, List<String>> tree = makeTree();
		Map<String, String> parents = makeParents(tree);

		Set<String> nodes = new HashSet<>(tree.keySet());
		nodes.addAll(parents.keySet());

		getNodes(nodes);
	}

	private static Set<String> getNodes(Collection<String> col)
	{
		Map<String, List<String>> tree = makeTree();
		Map<String, String> parents = makeParents((HashMap<String, List<String>>) tree);

		Set<String> nodes = new HashSet<>(tree.keySet());
		nodes.addAll(parents.keySet());

		for (String node : parents.keySet())
			System.out.println(String.join("/", findPath(parents, node)));

		return nodes;
	}

	private static HashMap<String, List<String>> makeTree()
	{
		HashMap<String, List<String>> tree = new HashMap<>();
		tree.put("A", Arrays.asList("B", "C", "D")); // A 노드의 하위 노드
		tree.put("B", Arrays.asList("E", "F"));
		tree.put("C", Arrays.asList("G"));
		tree.put("D", Arrays.asList("H", "I"));
		tree.put("E", Arrays.asList("J"));
		tree.put("G", Arrays.asList("K"));
		tree.put("K", Arrays.asList("L"));
		return tree;
	}

	private static Map<String, String> makeParents(HashMap<String, List<String>> tree)
	{
		Map<String, String> parents = new HashMap<>();
		for (Map.Entry<String, List<String>> e : tree.entrySet())
			for (String child : e.getValue())
				parents.put(child, e.getKey());

		return parents;
	}

	public static List<String> findPath(Map<String, String> parents, String node)
	{
		List<String> path = new ArrayList<>();
		path.add(node);

		while ((node = parents.get(node)) != null)
		{
			//path.add(node);
			path.add(0, node);
		}
		return path;
	}
}
