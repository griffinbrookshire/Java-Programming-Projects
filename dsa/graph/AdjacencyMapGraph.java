package edu.ncsu.csc316.dsa.graph;

import edu.ncsu.csc316.dsa.Position;
import edu.ncsu.csc316.dsa.list.positional.PositionalLinkedList;
import edu.ncsu.csc316.dsa.list.positional.PositionalList;
import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.dsa.map.hashing.LinearProbingHashMap;

/**
 * An implementation of an AdjacencyMapGraph
 * @author griffin
 *
 * @param <V> Vertex
 * @param <E> Edge
 */
public class AdjacencyMapGraph<V, E> extends AbstractGraph<V, E> {

	/** List of vertices */
    private PositionalList<Vertex<V>> vertexList;
    
    /** List of edges */
    private PositionalList<Edge<E>> edgeList;
    
    /**
     * Creates a new undirected AdjacencyMapGraph
     */
    public AdjacencyMapGraph() {
        this(false);
    }
    
    /**
     * Creates a new AdjacencyMapGraph
     * @param directed True if directed, false otherwise
     */
    public AdjacencyMapGraph(boolean directed) {
        super(directed);
        vertexList = new PositionalLinkedList<Vertex<V>>();
        edgeList = new PositionalLinkedList<Edge<E>>();
    }
    
    @Override
    public int numVertices() {
        return vertexList.size();
    }

    @Override
    public Iterable<Vertex<V>> vertices() {
        return vertexList;
    }

    @Override
    public int numEdges() {
        return edgeList.size();
    }

    @Override
    public Iterable<Edge<E>> edges() {
        return edgeList;
    }

    @Override
    public Edge<E> getEdge(Vertex<V> vertex1, Vertex<V> vertex2) {
        return validate(vertex1).getOutgoing().get(vertex2);
    }

    @Override
    public int outDegree(Vertex<V> vertex) {
        return validate(vertex).getOutgoing().size();
    }

    @Override
    public int inDegree(Vertex<V> vertex) {
        return validate(vertex).getIncoming().size();
    }

    @Override
    public Iterable<Edge<E>> outgoingEdges(Vertex<V> vertex) {
        return validate(vertex).getOutgoing().values();
    }

    @Override
    public Iterable<Edge<E>> incomingEdges(Vertex<V> vertex) {
        return validate(vertex).getIncoming().values();
    }

    @Override
    public Vertex<V> insertVertex(V vertexData) {
        AMVertex vertex = new AMVertex(vertexData, isDirected());
        Position<Vertex<V>> pos = vertexList.addLast(vertex);
        vertex.setPosition(pos);
        return vertex;
    }

    /**
     * Got this code from textbook page 629
     */
    @Override
    public Edge<E> insertEdge(Vertex<V> v1, Vertex<V> v2, E edgeData) {
        GraphEdge edge = new GraphEdge(edgeData, v1, v2);
        Position<Edge<E>> pos = edgeList.addLast(edge);
        edge.setPosition(pos);
        AMVertex origin = validate(v1);
        AMVertex destination = validate(v2);
        origin.getOutgoing().put(v2, edge);
        destination.getIncoming().put(v1, edge);
        // Remember to add the edge to the maps for each endpoint vertex
		return edge;
    }

    /**
     * Got this code from textbook page 629
     */
    @Override
    public Vertex<V> removeVertex(Vertex<V> vertex) {
        AMVertex v = validate(vertex);
        for (Edge<E> e : v.getOutgoing().values())
        	if (e != null) {
        		removeEdge(e);
        	}
        for (Edge<E> e : v.getIncoming().values())
        	if (e != null) {
        		removeEdge(e);
        	}
        vertexList.remove(v.getPosition());
		return v;
    }

    @Override
    public Edge<E> removeEdge(Edge<E> edge) {
        GraphEdge e = validate(edge);
        Vertex<V>[] ends = e.getEndpoints();
        AMVertex origin = validate(ends[0]);
        AMVertex dest = validate(ends[1]);
        edgeList.remove(e.getPosition());
        origin.getOutgoing().remove(dest);
        dest.getIncoming().remove(origin);
		return e;
    }
    
    /**
     * A vertex for an AdjacencyMap
     * @author griffin
     *
     */
    private class AMVertex extends GraphVertex {
        
        private Map<Vertex<V>, Edge<E>> outgoing;
        private Map<Vertex<V>, Edge<E>> incoming;
        
        public AMVertex(V data, boolean isDirected) {
            super(data);
            outgoing = new LinearProbingHashMap<Vertex<V>, Edge<E>>();
            if(isDirected) {
                incoming = new LinearProbingHashMap<>();
            } else {
                incoming = outgoing;
            }
        }
        
        public Map<Vertex<V>, Edge<E>> getOutgoing() {
            return outgoing;
        }
        
        public Map<Vertex<V>, Edge<E>> getIncoming() {
            return incoming;
        }
    }
    
    private AMVertex validate(Vertex<V> v) {
        if(!(v instanceof AdjacencyMapGraph.AMVertex)) {
            throw new IllegalArgumentException("Vertex is not a valid adjacency map vertex.");
        }
        return (AMVertex)v;
    }
}