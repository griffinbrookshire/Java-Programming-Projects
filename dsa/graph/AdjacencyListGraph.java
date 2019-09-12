package edu.ncsu.csc316.dsa.graph;

import java.util.Iterator;

import edu.ncsu.csc316.dsa.Position;
import edu.ncsu.csc316.dsa.list.positional.PositionalLinkedList;
import edu.ncsu.csc316.dsa.list.positional.PositionalList;

/**
 * An implementation of an AdjacencyListGraph
 * @author griffin
 *
 * @param <V> Vertex
 * @param <E> Edge
 */
public class AdjacencyListGraph<V, E> extends AbstractGraph<V, E> {

	/** List of vertices */
	private PositionalList<Vertex<V>> vertexList;

	/** List of edges */
	private PositionalList<Edge<E>> edgeList;

	/**
	 * Creates a new undirected AdjacenctListGraph
	 */
	public AdjacencyListGraph() {
		this(false);
	}

	/**
	 * Creates a new AdjacencyListGraph
	 * @param directed True if directed, false otherwise
	 */
	public AdjacencyListGraph(boolean directed) {
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
	public Iterable<Edge<E>> outgoingEdges(Vertex<V> vertex) {
		return validate(vertex).getOutgoing();
	}

	@Override
	public Iterable<Edge<E>> incomingEdges(Vertex<V> vertex) {
		return validate(vertex).getIncoming();
	}

	@Override
	public Edge<E> getEdge(Vertex<V> vertex1, Vertex<V> vertex2) {
		Iterator<Edge<E>> it = edgeList.iterator();
		while (it.hasNext()) {
			GraphEdge current = validate(it.next());
			Vertex<V>[] ends = current.getEndpoints();
			if(!isDirected() && ends[1] == vertex1 && ends[0] == vertex2) {
				return current;
			}
			if (ends[0] == vertex1 && ends[1] == vertex2) {
				return current;
			}
		}
		return null;
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
	public Vertex<V> insertVertex(V vertexData) {
		ALVertex vertex = new ALVertex(vertexData, isDirected());
		Position<Vertex<V>> pos = vertexList.addLast(vertex);
		vertex.setPosition(pos);
		return vertex;
	}

	/**
	 * Got this code from textbook page 629
	 */
	@Override
	public Edge<E> insertEdge(Vertex<V> v1, Vertex<V> v2, E edgeData) {
		ALEdge edge = new ALEdge(edgeData, v1, v2);
		Position<Edge<E>> pos = edgeList.addLast(edge);
		edge.setPosition(pos);
		ALVertex origin = validate(v1);
		ALVertex dest = validate(v2);
		edge.setOutgoingListPosition(origin.getOutgoing().addLast(edge));
		edge.setIncomingListPosition(dest.getIncoming().addLast(edge));
		// Remember to set the edge's positions in the outgoingEdges 
		//    and incomingEdges lists for the appropriate vertices
		return edge;
	}

	/**
	 * Got this code from textbook page 629
	 */
	@Override
	public Vertex<V> removeVertex(Vertex<V> vertex) {
		ALVertex v = validate(vertex);
		for(Edge<E> e: incomingEdges(v)) {
			if (e != null) {
				removeEdge(e);
			}
		}
		if (this.isDirected()) {
			for(Edge<E> e: outgoingEdges(v)) {
				if (e != null) {
					removeEdge(e);
				}
			}
		}
		return vertexList.remove(v.getPosition());
	}

	@Override
	public Edge<E> removeEdge(Edge<E> edge) {
		ALEdge e = validate(edge);
		Vertex<V>[] ends = e.getEndpoints();
		ALVertex origin = validate(ends[0]);
		ALVertex dest = validate(ends[1]);

		if (origin != null && dest != null) {
			origin.getOutgoing().remove(e.getOutgoingListPosition());
			if (!this.isDirected()) {
				dest.getIncoming().remove(e.getIncomingListPosition());
			}
		}
		
//		origin.getOutgoing().remove(e.getPosition());
//		if (!this.isDirected()) {
//			dest.getIncoming().remove(e.getPosition());
//		}

		
		

		return edgeList.remove(e.getPosition());
	}

	/**
	 * A vertex for an AdjacencyList
	 * @author griffin
	 *
	 */
	private class ALVertex extends GraphVertex {

		private PositionalList<Edge<E>> outgoing;
		private PositionalList<Edge<E>> incoming;

		public ALVertex(V data, boolean isDirected) {
			super(data);
			outgoing = new PositionalLinkedList<Edge<E>>();
			if(isDirected) {
				incoming = new PositionalLinkedList<Edge<E>>();
			} else {
				incoming = outgoing;
			}
		}

		public PositionalList<Edge<E>> getOutgoing() {
			return outgoing;
		}

		public PositionalList<Edge<E>> getIncoming() {
			return incoming;
		}
	}

	/**
	 * An edge for an AdjacencyList
	 * @author griffin
	 *
	 */
	private class ALEdge extends GraphEdge {    
		private Position<Edge<E>> outgoingListPosition;
		private Position<Edge<E>> incomingListPosition;

		public ALEdge(E element, Vertex<V> v1,
				Vertex<V> v2) {
			super(element, v1, v2);
		}

		public Position<Edge<E>> getOutgoingListPosition() {
			return outgoingListPosition;
		}
		public void setOutgoingListPosition(Position<Edge<E>> outgoingListPosition) {
			this.outgoingListPosition = outgoingListPosition;
		}
		public Position<Edge<E>> getIncomingListPosition() {
			return incomingListPosition;
		}
		public void setIncomingListPosition(Position<Edge<E>> incomingListPosition) {
			this.incomingListPosition = incomingListPosition;
		}
	}

	private ALVertex validate(Vertex<V> v) {
		if(!(v instanceof AdjacencyListGraph.ALVertex)) {
			throw new IllegalArgumentException("Vertex is not a valid adjacency list vertex.");
		}
		return (ALVertex)v;
	}

	/**
	 * Validates an edge is a ALEdge
	 * @param e The edge to check
	 * @return an ALEdge of the edge
	 */
	protected ALEdge validate(Edge<E> e) {
		if(!(e instanceof AdjacencyListGraph.ALEdge)) {
			throw new IllegalArgumentException("Edge is not a valid adjacency list edge.");
		}
		return (ALEdge)e;
	}
}