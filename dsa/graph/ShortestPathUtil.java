package edu.ncsu.csc316.dsa.graph;

import java.util.Iterator;

import edu.ncsu.csc316.dsa.Weighted;
import edu.ncsu.csc316.dsa.graph.Graph.Edge;
import edu.ncsu.csc316.dsa.graph.Graph.Vertex;
import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.dsa.map.hashing.LinearProbingHashMap;
import edu.ncsu.csc316.dsa.priority_queue.AdaptablePriorityQueue;
import edu.ncsu.csc316.dsa.priority_queue.HeapAdaptablePriorityQueue;
import edu.ncsu.csc316.dsa.priority_queue.PriorityQueue.Entry;
import edu.ncsu.csc316.dsa.set.HashSet;
import edu.ncsu.csc316.dsa.set.Set;

/**
 * Shortest Path util
 * @author griffin
 *
 */
public class ShortestPathUtil {

	/**
	 * Creates a shortest path util
	 */
	public ShortestPathUtil() {
		/**
		 * All static methods
		 */
	}

	//	Algorithm dijkstra(G, s)
	//	Input a graph G
	//	      a starting vertex s
	//	Output the shortest path costs from the starting vertex to every other vertex
	//	// Use a map to track the shortest path cost to each vertex
	//	M <-- new empty map
	//
	//	// Use an adaptable priority queue to order vertices by weight
	//	Q <-- new adaptable priority queue
	//
	//	// Initialize information for each vertex
	//	for each vertex v in G.vertices()
	//	   if v = s
	//	      setWeight(v, 0)
	//	   else
	//	      setWeight(v, ∞)
	//	   setFound(v, false)
	//	   Q.insert(weight(v), v)
	//	   
	//	// Main loop
	//	while not Q.isEmpty()
	//	   Vertex u <-- Q.deleteMin()
	//	   // At this point, we know the shortest path to vertex u
	//	   // so we can add this cost to our shortest path cost map
	//	   M.put(u, weight(u) )
	//	   setFound(u, true)
	//	   for all edges e in G.outgoingEdges(u)
	//	      Vertex z <-- G.opposite(u,e)
	//	      r <-- weight(e) + getWeight(u)
	//	      if found(z) is FALSE && r < getWeight(z)
	//	         setWeight(z,r)
	//	         Q.replaceKey(z,r)
	//	return M
	/**
	 * Dijkstra
	 * @param g Graph
	 * @param src Start vertex
	 * @param <V> Vertex
	 * @param <E> Edge
	 * @return Map of graph
	 */
	public static <V, E extends Weighted> Map<Vertex<V>, Integer> dijkstra(Graph<V, E> g, Vertex<V> src) {
		Map<Vertex<V>, Integer> m = new LinearProbingHashMap<>();
		AdaptablePriorityQueue<Integer, Vertex<V>> q = new HeapAdaptablePriorityQueue<>();
		Set<Vertex<V>> known = new HashSet<>();
		Map<Vertex<V>, Entry<Integer, Vertex<V>>> pqEntries = new LinearProbingHashMap<>();
		for (Vertex<V> v : g.vertices()) {
			if (v.equals(src)) {
				m.put(v, 0);
			} else {
				m.put(v, Integer.MAX_VALUE);
			}
			known.remove(v);
			q.insert(m.get(v), v);
			pqEntries.put(v, q.insert(m.get(v), v));
		}
		while (!q.isEmpty()) {
			Vertex<V> u = q.deleteMin().getValue();
			m.put(u, m.get(u));
			known.add(u);
			for (Edge<E> e : g.outgoingEdges(u)) {
				Vertex<V> z = g.opposite(u, e);
				int r = e.getElement().getWeight() + m.get(u);
				if (!known.contains(z) && r < m.get(z)) {
					m.put(z, r);
					q.replaceKey(pqEntries.get(z), r);
				}
			}
		}
		return m;
	}

	/**
	 * Shortest tree path
	 * @param g Graph
	 * @param s Start vertex
	 * @param distances Map of weights
	 * @param <V> Vertex
	 * @param <E> Edge
	 * @return Map of graph
	 */
	public static <V, E extends Weighted> Map<Vertex<V>, Edge<E>> shortestPathTree(Graph<V, E> g, Vertex<V> s, Map<Vertex<V>, Integer> distances) {
		Map<Vertex<V>, Edge<E>> m = new LinearProbingHashMap<>();
		Iterator<Vertex<V>> i = distances.iterator();
		while (i.hasNext()) {
			Vertex<V> v = i.next();
			if (!v.equals(s)) {
				for (Edge<E> e : g.incomingEdges(v)) {
					Vertex<V> u = g.opposite(v, e);
					if (distances.get(v).equals(distances.get(u) + e.getElement().getWeight())) {
						m.put(v, e);
					}
				}
			}
		}
		return m;
	}

}
