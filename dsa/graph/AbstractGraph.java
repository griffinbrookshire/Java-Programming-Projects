package edu.ncsu.csc316.dsa.graph;

import edu.ncsu.csc316.dsa.Position;

/**
 * Defines behavior and data for a graph
 * @author griffin
 *
 * @param <V> Vertex
 * @param <E> Edge
 */
public abstract class AbstractGraph<V, E> implements Graph<V, E> {
    
	/** Tells if the graph is directed or not */
    private boolean isDirected;
    
    /**
     * Creates a new AbstractGraph
     * @param directed True if directed, false otherwise
     */
    public AbstractGraph(boolean directed) {
        setDirected(directed);
    }
    
    /**
     * Sets if this graph is directed
     * @param directed True if directed, false otherwise
     */
    private void setDirected(boolean directed) {
        isDirected = directed;
    }
    
    /**
     * Gets if the graph is directed or not
     * @return True if directed, false otherwise
     */
    public boolean isDirected() {
        return isDirected;
    }
    
    @Override
    public Vertex<V>[] endVertices(Edge<E> edge) {
        return validate(edge).getEndpoints();
    }

    @Override
    public Vertex<V> opposite(Vertex<V> vertex, Edge<E> edge) {
        GraphEdge temp = validate(edge);
        Vertex<V>[] ends = temp.getEndpoints();
        if(ends[0] == vertex) {
            return ends[1];
        }
        if(ends[1] == vertex) {
            return ends[0];
        }
        throw new IllegalArgumentException("Vertex is not incident on this edge.");
    }
    
    /**
	 * Validates a edge
	 * @param e Edge to validate
	 * @return GraphEdge of the Edge
	 */
	protected GraphEdge validate(Edge<E> e) {
	    if (!(e instanceof AbstractGraph.GraphEdge)) {
	        throw new IllegalArgumentException("Edge is not a valid graph edge.");
	    }
	    return (GraphEdge) e;
	}

	/**
     * Defines behavior or data for a graph vertex
     * @author griffin
     *
     */
    protected class GraphVertex implements Vertex<V> {
    	
    	/** Element in the vertex */
        private V element;
        
        /** The position of the vertex */
        private Position<Vertex<V>> position;
        
        /**
         * Creates a new GraphVertex with an element
         * @param element Element to store
         */
        public GraphVertex(V element) {
            setElement(element);
        }
        
        /**
         * Sets the element in the vertex
         * @param element Element to set to
         */
        public void setElement(V element) {
            this.element = element;
        }
        
        /**
         * Gets the element of the vertex
         * @return Element in the vertex
         */
        public V getElement() {
            return element;
        }
        
        /**
         * Gets the position of the vertex
         * @return The position of the vertex
         */
        public Position<Vertex<V>> getPosition(){
            return position;
        }
        
        /**
         * Sets the position of the vertex
         * @param pos Postion to set to
         */
        public void setPosition(Position<Vertex<V>> pos) {
            position = pos;
        }
    }
    
    /**
     * Defines behavior and data for a graph edge
     * @author griffin
     *
     */
    protected class GraphEdge implements Edge<E> {
    	
    	/** Element in the edge */
        private E element;
        
        /** The endpoints of the edge */
        private Vertex<V>[] endpoints;
        
        /** The position of the edge */
        private Position<Edge<E>> position;
        
        /**
         * Creates a new GraphEdge with element and vertices
         * @param element Element to store
         * @param v1 Vertex 1
         * @param v2 Vertex 2
         */
        @SuppressWarnings("unchecked")
        public GraphEdge(E element, Vertex<V> v1, Vertex<V> v2) {
            setElement(element);
            endpoints = (Vertex<V>[])new Vertex[]{v1, v2};
        }
        
        /**
         * Sets the element of the edge
         * @param element Element to set to
         */
        public void setElement(E element) {
            this.element = element;
        }
        
        /**
         * Gets the element of the edge
         * @return Element of the edge
         */
        public E getElement() {
            return element;
        }
        
        /**
         * Gets the endpoints of the edge
         * @return Array of vertices
         */
        public Vertex<V>[] getEndpoints() {
            return endpoints;
        }
        
        /**
         * Gets the position of the edge
         * @return Position of the edge
         */
        public Position<Edge<E>> getPosition(){
            return position;
        }
        
        /**
         * Sets the position of the Edge
         * @param pos Position of the edge
         */
        public void setPosition(Position<Edge<E>> pos) {
            position = pos;
        }
        
        @Override
        public String toString() {
            return "Edge[element=" + element + "]";
        }
    }
}