package edu.ncsu.csc316.dsa.graph;

/**
 * Defines behavior of a graph
 * @author griffin
 *
 * @param <V> Vertex
 * @param <E> Edge
 */
public interface Graph<V, E> {
	
	/**
	 * Returns true if the graph is a directed graph, otherwise returns false
	 * @return True if directed, otherwise false
	 */
    boolean isDirected();
    
    /**
     * Returns the number of vertices in the graph
     * @return Number of vertices
     */
    int numVertices();
    
    /**
     * Returns an iteration of all vertices in the graph
     * @return Iteration of vertices
     */
    Iterable<Vertex<V>> vertices();
    
    /**
     * Returns the number of edges in the graph
     * @return Number of edges
     */
    int numEdges();
    
    /**
     * Returns an iteration of all edges in the graph
     * @return Iteration of edges
     */
    Iterable<Edge<E>> edges();
    
    /**
     * Returns the edge that connects vertex u and vertex v;
     * if no edge connects the two vertices, return null.
     * For undirected graphs, getEdge(u,v) = getEdge(v,u)
     * @param vertex1 Vertex 1
     * @param vertex2 Vertex 2
     * @return Edge between the vertices
     */
    Edge<E> getEdge(Vertex<V> vertex1, Vertex<V> vertex2);
    
    /**
     * Returns the two endpoint vertices of edge e.
     * For a directed graph, the first vertex is the source
     * vertex and the second is the destination vertex
     * @param edge Edge to get vertices
     * @return Array of vertices
     */
    Vertex<V>[] endVertices(Edge<E> edge);
    
    /**
     * Returns the other vertex of the edge, given an edge e incident to vertex v
     * @param vertex Vertex to check
     * @param edge Edge to check
     * @return Other vertex
     */
    Vertex<V> opposite(Vertex<V> vertex, Edge<E> edge);
    
    /**
     * Returns the number of outgoing edges from v
     * @param vertex Vertex to get edges
     * @return Number of edges
     */
    int outDegree(Vertex<V> vertex);
    
    /**
     * Returns the number of incoming edges to v. For undirected graphs, outDegree(v) = inDegree(v)
     * @param vertex Vertex to get edges
     * @return Number of edges
     */
    int inDegree(Vertex<V> vertex);
    
    /**
     * Returns an iteration of all outgoing edges from vertex v
     * @param vertex Vertex to get edges
     * @return Iteration of edges
     */
    Iterable<Edge<E>> outgoingEdges(Vertex<V> vertex);
    
    /**
     * Returns an iteration of all incoming edges to vertex v.
     * For undirected graphs, ougoingEdges(v) = incomingEdges(v)
     * @param vertex Vertex to get edges
     * @return Iteration of edges
     */
    Iterable<Edge<E>> incomingEdges(Vertex<V> vertex);
    
    /**
     * Creates and returns a new Vertex storing element x
     * @param vertexData Data to store in vertex
     * @return New vertex with element
     */
    Vertex<V> insertVertex(V vertexData);
    
    /**
     * Creates and returns a new Edge from vertex u to vertex v and stores element x
     * @param v1 Vector 1
     * @param v2 Vector 2
     * @param edgeData Data stored in the edge
     * @return New edge with data
     */
    Edge<E> insertEdge(Vertex<V> v1, Vertex<V> v2, E edgeData);
    
    /**
     * Removes vertex v and all its incident edges from the graph
     * @param vertex Vertex to remove
     * @return Removed vertex
     */
    Vertex<V> removeVertex(Vertex<V> vertex);
    
    /**
     * Removes edge e from the graph
     * @param edge Edge to remove
     * @return Removed edge
     */
    Edge<E> removeEdge(Edge<E> edge);
    
    /**
     * Defines behavior of an edge
     * @author griffin
     *
     * @param <E> Edge
     */
    interface Edge<E> {
    	
    	/**
    	 * Gets the element in the edge
    	 * @return Element in the edge
    	 */
        E getElement();
    }
    
    /**
     * Defines behavior of a vertex
     * @author griffin
     *
     * @param <V> Vertex
     */
    interface Vertex<V> {
    	
    	/**
    	 * Gets the element in the vertex
    	 * @return Element in the vertex
    	 */
        V getElement();
    }
}