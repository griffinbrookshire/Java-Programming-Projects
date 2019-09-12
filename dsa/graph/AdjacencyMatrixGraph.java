package edu.ncsu.csc316.dsa.graph;

import edu.ncsu.csc316.dsa.Position;
import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.list.SinglyLinkedList;
import edu.ncsu.csc316.dsa.list.positional.PositionalLinkedList;
import edu.ncsu.csc316.dsa.list.positional.PositionalList;
import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.dsa.map.hashing.LinearProbingHashMap;

/**
 * An implementation of an AdjacnectMatrixGraph
 * @author griffin
 *
 * @param <V> Vertex
 * @param <E> Edge
 */
public class AdjacencyMatrixGraph<V, E> extends AbstractGraph<V, E> {

	/** Matric of GraphEdges */
	private GraphEdge[][] matrix;

	/** List of vertices */
	private PositionalList<Vertex<V>> vertexList;

	/** List of edges */
	private PositionalList<Edge<E>> edgeList;

	/** Map of vertices */
	private Map<Vertex<V>, Integer> vertexMap;

	/**
	 * Creates a new undirected AdjacencyMatrixGraph
	 */
	public AdjacencyMatrixGraph() {
		this(false);
	}

	/**
	 * Creates a new AdjacencyMatrixGraph
	 * @param directed True if directed, false otherwise
	 */
	@SuppressWarnings("unchecked")
	public AdjacencyMatrixGraph(boolean directed) {
		super(directed);
		matrix = (GraphEdge[][]) (new AbstractGraph.GraphEdge[0][0]);
		vertexList = new PositionalLinkedList<Vertex<V>>();
		edgeList = new PositionalLinkedList<Edge<E>>();
		vertexMap = new LinearProbingHashMap<Vertex<V>, Integer>();
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
		// For a directed graph, we consistently use the 
		// "upper half" of the matrix since the matrix is not symmetric
		// like it is with an undirected graph
		if(!isDirected() && vertexMap.get(vertex2) < vertexMap.get(vertex1)) {
			return matrix[vertexMap.get(vertex2)][vertexMap.get(vertex1)];
		}
		return matrix[vertexMap.get(vertex1)][vertexMap.get(vertex2)];
	}

	@Override
	public Vertex<V>[] endVertices(Edge<E> edge) {
		GraphEdge e = validate(edge);
		return e.getEndpoints();
	}

	@Override
	public Vertex<V> opposite(Vertex<V> vertex, Edge<E> edge) {
		GraphEdge temp = validate(edge);
		Vertex<V>[] ends = temp.getEndpoints();
		if (ends[0] == vertex) {
			return ends[1];
		}
		if (ends[1] == vertex) {
			return ends[0];
		}
		throw new IllegalArgumentException("Vertex is not incident on this edge.");
	}

	@Override
	public int outDegree(Vertex<V> vertex) {
		return outgoingEdgeList(vertex).size();
	}

	private List<Edge<E>> outgoingEdgeList(Vertex<V> vertex) {
		List<Edge<E>> list = new SinglyLinkedList<Edge<E>>();
		for (GraphEdge e : matrix[vertexMap.get(vertex)]) {
			if (e != null) {
				list.addLast(e);
			}
		}
		if (!isDirected()) {
			for (int i = 0; i < matrix.length; i++) {
				GraphEdge e = matrix[i][vertexMap.get(vertex)];
				if (e != null) {
					list.addLast(e);
				}
			}
		}
		return list;
	}

	private List<Edge<E>> incomingEdgeList(Vertex<V> vertex) {
		List<Edge<E>> list = new SinglyLinkedList<Edge<E>>();
		for (int i = 0; i < matrix.length; i++) {
			GraphEdge e = matrix[i][vertexMap.get(vertex)];
			if (e != null) {
				list.addLast(e);
			}
		}
		if (!isDirected()) {
			for (GraphEdge e : matrix[vertexMap.get(vertex)]) {
				if (e != null) {
					list.addLast(e);
				}
			}
		}
		return list;
	}

	@Override
	public int inDegree(Vertex<V> vertex) {
		return incomingEdgeList(vertex).size();
	}

	@Override
	public Iterable<Edge<E>> outgoingEdges(Vertex<V> vertex) {
		return outgoingEdgeList(vertex);
	}

	@Override
	public Iterable<Edge<E>> incomingEdges(Vertex<V> vertex) {
		return incomingEdgeList(vertex);
	}

	@Override
	public Vertex<V> insertVertex(V vertexData) {
		GraphVertex v = new GraphVertex(vertexData);
		Position<Vertex<V>> pos = vertexList.addLast(v);
		vertexMap.put(v, vertexMap.size());
		v.setPosition(pos);
		growArray();
		return v;
	}

	@SuppressWarnings("unchecked")
	private void growArray() {
		// We are using a poor implementation here since we will
		// grow the matrix each time a new vertex is added to the graph
		GraphEdge[][] temp = new AbstractGraph.GraphEdge[matrix.length + 1][matrix.length + 1];
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix.length; j++)
			{
				temp[i][j] = matrix[i][j];
			}
		}
		matrix = temp;
	}

	@Override
	public Edge<E> insertEdge(Vertex<V> v1, Vertex<V> v2, E edgeData) {
		GraphEdge e = new GraphEdge(edgeData, v1, v2);
		Position<Edge<E>> pos = edgeList.addLast(e);
		e.setPosition(pos);
		if( !isDirected() && vertexMap.get(v2) < vertexMap.get(v1)) {
			matrix[vertexMap.get(v2)][vertexMap.get(v1)] = e;
		} else {
			matrix[vertexMap.get(v1)][vertexMap.get(v2)] = e;
		}
		return e;
	}

	@Override
	public Vertex<V> removeVertex(Vertex<V> vertex) {
		GraphVertex v = validate(vertex);
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
		int i = vertexMap.get(vertex);
		for (int j = 0; j < matrix.length; j++) {
			matrix[i][j] = null; //remove row
			matrix[j][i] = null; //remove column
		}
		return vertexList.remove(v.getPosition());
	}

	@Override
	public Edge<E> removeEdge(Edge<E> edge) {
		GraphEdge e = validate(edge);
		edgeList.remove(e.getPosition());
		Vertex<V>[] ends = e.getEndpoints();
		GraphVertex origin = validate(ends[0]);
		GraphVertex dest = validate(ends[1]);
		int o = vertexMap.get(origin);
		int d = vertexMap.get(dest);
		if( !isDirected() && d < o ) {
			matrix[d][o] = null;
		} else {
			matrix[o][d] = null;
		}
		return e;
	}

	private GraphVertex validate(Vertex<V> v) {
		if (!(v instanceof AbstractGraph.GraphVertex)) {
			throw new IllegalArgumentException("Vertex is not a valid graphvertex.");
		}
		return (GraphVertex) v;
	}
}