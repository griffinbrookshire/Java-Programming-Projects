package edu.ncsu.csc316.dsa.graph;

import edu.ncsu.csc316.dsa.Position;
import edu.ncsu.csc316.dsa.Weighted;
import edu.ncsu.csc316.dsa.disjoint_set.DisjointSet;
import edu.ncsu.csc316.dsa.disjoint_set.UpTreeDisjointSetForest;
import edu.ncsu.csc316.dsa.graph.Graph.Edge;
import edu.ncsu.csc316.dsa.graph.Graph.Vertex;
import edu.ncsu.csc316.dsa.list.positional.PositionalLinkedList;
import edu.ncsu.csc316.dsa.list.positional.PositionalList;
import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.dsa.map.hashing.LinearProbingHashMap;
import edu.ncsu.csc316.dsa.priority_queue.AdaptablePriorityQueue;
import edu.ncsu.csc316.dsa.priority_queue.HeapAdaptablePriorityQueue;
import edu.ncsu.csc316.dsa.priority_queue.PriorityQueue.Entry;
import edu.ncsu.csc316.dsa.set.HashSet;
import edu.ncsu.csc316.dsa.set.Set;

/**
 * Min span tree util
 * @author griffin
 *
 */
public class MinimumSpanningTreeUtil {

	/**
	 * Creates a min span tree
	 */
	public MinimumSpanningTreeUtil() {
		/**
		 * All static methods
		 */
	}

	/**
	 * Traversal alg
	 * @param g Graph
	 * @param <V> Vertex
	 * @param <E> Edge
	 * @return Map of graph
	 */
	public static <V, E extends Weighted> PositionalList<Edge<E>> primJarnik(Graph<V, E> g) {

		// Adaptable Priority Queue to order vertices by weight
		AdaptablePriorityQueue<Integer, Vertex<V>> q = new HeapAdaptablePriorityQueue<>();

		// Keep track of the current shortest path weights
		Map<Vertex<V>, Integer> weights = new LinearProbingHashMap<>();

		// Keep track of the vertices that have been found
		Set<Vertex<V>> known = new HashSet<>();

		// To be able to use the replaceKey() behavior in the adaptable priority queue,
		//   we must keep track of the PQ entries so that we can pass a reference
		//   to the entry as a parameter when calling replaceKey()
		Map<Vertex<V>, Entry<Integer, Vertex<V>>> pqEntries = new LinearProbingHashMap<>();

		// Instead of keeping track of the parents, we need to keep track
		//   of the edges that connect the previous vertex to the current vertex
		//   to know which edges should be added to the MST
		Map<Vertex<V>, Edge<E>> connectingEdges = new LinearProbingHashMap<>();

		// We need to keep track of the edges in the MST
		PositionalList<Edge<E>> tree = new PositionalLinkedList<>();

		// Select an arbitrary vertex to use as the starting vertex.
		// In Dijkstra's algorithm, this starting vertex was an input
		//   into the algorithm. For Prim-Jarnik's algorithm, it does not
		//   matter which vertex we pick to start
		Vertex<V> src = g.vertices().iterator().next();

		// Loop to initialize all the information about each vertex
		for(Vertex<V> v : g.vertices()) {
			if(v == src) {
				weights.put(v, 0);
			} else {
				weights.put(v, Integer.MAX_VALUE);
			}
			// Insert the vertex into the adaptable PQ
			//   and save a reference to the PQ entry that was created for v
			pqEntries.put(v, q.insert(weights.get(v), v));
		}

		// MAIN LOOP for Prim-Jarnik's algorithm
		while(!q.isEmpty()) {
			Entry<Integer, Vertex<V>> entry = q.deleteMin();
			Vertex<V> u = entry.getValue();
			// The edge we used to reach vertex u
			//   should now be added to the MST
			if(connectingEdges.get(u) != null) {
				tree.addLast(connectingEdges.get(u));
			}
			known.add(u);
			for(Edge<E> e : g.outgoingEdges(u)) {
				Vertex<V> z = g.opposite(u, e);
				// Edge relaxation
				int r = e.getElement().getWeight();
				if(!known.contains(z) && r < weights.get(z)) {
					weights.put(z, r);
					// Save that we reached vertex z by following edge e
					connectingEdges.put(z, e);
					q.replaceKey(pqEntries.get(z), r);
				}
			}
		}
		return tree;
	}


	//	Algorithm kruskal(G)
	//	Input a graph G
	//	Output a collection of the edges in the minimum spanning tree
	//	// Use a list to store the edges in the MST
	//	T <-- new empty list
	//	// Use a priority queue to order the edges
	//	Q <-- new empty priority queue
	//	// Use a disjoint set to help with cycle detection
	//	D <-- new empty forest of disjoint sets
	//
	//	// insert the edges into the priority queue, partially ordered according to edge weight
	//	for each Edge e in G.edges() do
	//	   Q.insert(e)
	//	   
	//	// Initialize each vertex into its own disjoint set
	//	for each Vertex v in G.vertices() do
	//	   D.makeSet(v)
	//
	//	components <-- G.numVertices()
	//
	//	// Main loop   
	//	while components > 1 do // process edges in order of increasing weight
	//	   Edge e <-- Q.deleteMin()
	//	   Vertex u <-- D.find(G.endVertices(e)[0]);
	//	   Vertex v <-- D.find(G.endVertices(e)[1]);
	//	   if u ≠ v then
	//	      D.union(u,v)
	//	      T.addLast(e)
	//	      components <-- components–1
	//	return T             

	/**
	 * Traversal alg
	 * @param g Graph
	 * @param <V> Vertex
	 * @param <E> Edge
	 * @return Map of graph
	 */
	public static <V, E extends Weighted> PositionalList<Edge<E>> kruskal(Graph<V, E> g) {
		PositionalList<Edge<E>> t = new PositionalLinkedList<Edge<E>>();
		AdaptablePriorityQueue<Integer, Edge<E>> q = new HeapAdaptablePriorityQueue<>();
		DisjointSet<Vertex<V>> d = new UpTreeDisjointSetForest<Vertex<V>>();
		for (Edge<E> e : g.edges()) {
			q.insert(e.getElement().getWeight(), e);
		}
		for (Vertex<V> v : g.vertices()) {
			d.makeSet(v);
		}
		int components = g.numVertices();
		while (components > 1) {
			Edge<E> e = q.deleteMin().getValue();
			Position<Vertex<V>> u = d.find(g.endVertices(e)[0]);
			Position<Vertex<V>> v = d.find(g.endVertices(e)[1]);
			if (!u.equals(v) ) {
				d.union(u, v);
				t.addLast(e);
				components--;
			}
		}
		return t;
	}

}
