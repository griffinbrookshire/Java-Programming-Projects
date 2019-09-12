package edu.ncsu.csc316.dsa.graph;

import edu.ncsu.csc316.dsa.graph.Graph.Edge;
import edu.ncsu.csc316.dsa.graph.Graph.Vertex;
import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.dsa.map.hashing.LinearProbingHashMap;
import edu.ncsu.csc316.dsa.queue.ArrayBasedQueue;
import edu.ncsu.csc316.dsa.queue.Queue;
import edu.ncsu.csc316.dsa.set.HashSet;
import edu.ncsu.csc316.dsa.set.Set;

/**
 * graph trav util
 * @author griffin
 *
 */
public class GraphTraversalUtil {

	/**
	 * Creates a traversal
	 */
	public GraphTraversalUtil() {
		/**
		 * All static methods
		 */
	}

	/**
	 * Depth First Search
	 * @param graph Graph
	 * @param start Start vertex
	 * @param <V> Vertex
	 * @param <E> Edge
	 * @return Map of traversal
	 */
	public static <V, E> Map<Vertex<V>, Edge<E>> depthFirstSearch(Graph<V, E> graph, Vertex<V> start) {
		Set<Vertex<V>> s = new HashSet<>();
		Map<Vertex<V>, Edge<E>> m = new LinearProbingHashMap<>();
		dfsHelper(graph, start, s, m);
		return m;
	}

	private static <V, E> void dfsHelper(Graph<V, E> graph, Vertex<V> u, Set<Vertex<V>> known, Map<Vertex<V>, Edge<E>> forest) {
		known.add(u);
		for (Edge<E> e : graph.outgoingEdges(u)) {
			Vertex<V> v = graph.opposite(u, e);
			if (!known.contains(v)) {
				forest.put(v, e);
				dfsHelper(graph, v, known, forest);
			}
		}
	}

	/**
	 * Breadth Frist Search
	 * @param graph Graph
	 * @param start Start vertex
	 * @param <V> Vertex
	 * @param <E> Edge
	 * @return Map of graph
	 */
	public static <V, E> Map<Vertex<V>, Edge<E>> breadthFirstSearch(Graph<V, E> graph, Vertex<V> start) {
		Set<Vertex<V>> s = new HashSet<>();
		Map<Vertex<V>, Edge<E>> m = new LinearProbingHashMap<>();
		Queue<Vertex<V>> q = new ArrayBasedQueue<>();
		s.add(start);
		q.enqueue(start);
		while (!q.isEmpty()) {
			Vertex<V> u = q.dequeue();
			for (Edge<E> e : graph.outgoingEdges(u)) {
				Vertex<V> w = graph.opposite(u, e);
				if (!s.contains(w)) {
					s.add(w);
					q.enqueue(w);
					m.put(w, e);
				}
			}
		}
		return m;
	}

}
