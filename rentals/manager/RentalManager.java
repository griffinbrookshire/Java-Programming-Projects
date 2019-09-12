package edu.ncsu.csc316.rentals.manager;

import java.io.FileNotFoundException;
import edu.ncsu.csc316.dsa.graph.Graph;
import edu.ncsu.csc316.dsa.graph.GraphTraversalUtil;
import edu.ncsu.csc316.dsa.graph.Graph.Edge;
import edu.ncsu.csc316.dsa.graph.Graph.Vertex;
import edu.ncsu.csc316.dsa.graph.ShortestPathUtil;
import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.dsa.map.Map.Entry;
import edu.ncsu.csc316.dsa.sorter.Sorter;
import edu.ncsu.csc316.rentals.data.Rental;
import edu.ncsu.csc316.rentals.factory.DSAFactory;
import edu.ncsu.csc316.rentals.io.RentalReaderIO;

/**
 * Manages rental information
 * @author griffin
 *
 */
public class RentalManager {

	/** First possible rental day */
	private int lowestDay = Integer.MAX_VALUE;

	/** Last possible rental day */
	private int highestDay = Integer.MIN_VALUE;
	
	/** List of rentals */
	private List<Rental> rentals;

	/** Graph of rentals */
	private Graph<Integer, Rental> graph;

	/**
	 * Constructs a new Rental manager with the given input file
	 * 
	 * @throws FileNotFoundException if file does not exist
	 * @param pathToFile the path to the input CSV file
	 */
	public RentalManager(String pathToFile) throws FileNotFoundException {
		rentals = RentalReaderIO.readFile(pathToFile);
		graph = buildGraph(rentals);
	}

	/**
	 * Returns the String representation of the rentals that
	 * minimize the total cost from the start day to the end day
	 * (or for as many days from the start day while rentals are possible).
	 * NOTE: remember to check for valid start day, valid end day, and
	 *       whether the graph is connected
	 * 
	 * @param start - the start day as an integer
	 * @param end - the end day as an integer
	 * @return the String representation of the rentals that minimize cost
	 */
	public String getRentals(int start, int end) {
		if (!checkStartDay(start)) {
			return "The specified start day (" + start + ") is smaller than the minimum day in the input data (" + lowestDay + ").";
		}
		if (start > highestDay) {
			return "The specified start day (" + start + ") is larger than the maximum day in the input data (" + highestDay + ").";
		}
		if (!checkEndDay(end)) {
			return "The specified end day (" + end + ") is larger than the maximum day in the input data (" + highestDay + ").";
		}
		if (end < lowestDay) {
			return "The specified end day (" + end + ") is smaller than the minimum day in the input data (" + lowestDay + ").";
		}
		if (start == end) {
			return "There are no rentals available on day " + start + ".";
		}
		int check = checkConnectedGraph(start, end);
		if (check != -1 && check != end) {
			return "There are no rentals available on day " + check + ".";
		}
		Vertex<Integer> v = getVertex(graph.vertices(), start);
		Map<Vertex<Integer>, Integer> d = ShortestPathUtil.dijkstra(graph, v);
		Entry<Vertex<Integer>, Integer> w = getEntry(d.entrySet(), end);
		int total = w.getValue();
		if (total <= 0) {
			return "There are no rentals available on day " + start + ".";
		}
		Map<Vertex<Integer>, Edge<Rental>> m = ShortestPathUtil.shortestPathTree(graph, getVertex(graph.vertices(), start), d);
		StringBuilder s = new StringBuilder("Rental Total is $" + total + ".00 [\n");
		List<Rental>  list = DSAFactory.getIndexedList();
		for (Entry<Vertex<Integer>, Edge<Rental>> e : m.entrySet()) {
			list.addFirst(e.getValue().getElement());
		}
		Graph<Integer, Rental> g = buildGraph(list);
		g = removeVertices(g, end);
		Vertex<Integer> temp = getVertex(g.vertices(), start);
		while (temp != getVertex(g.vertices(), end)) {
			Edge<Rental> e = g.outgoingEdges(temp).iterator().next();
			s.append("   " + e.getElement().toString() + "\n");
			temp = g.opposite(temp, e);
		}
		return s.toString() + "]";
	}    

	/**
	 * Returns the String representation of all the rentals 
	 * that are available for the requested day.
	 * 
	 * @param day - the day for which to retrieve available rentals
	 * @return the String representation of the rentals
	 */
	public String getRentalsForDay(int day) {
		if (!checkStartDay(day)) {
			return "The specified start day (" + day + ") is smaller than the minimum day in the input data (" + lowestDay + ").";
		}
		if (!checkEndDay(day)) {
			return "The specified end day (" + day + ") is larger than the maximum day in the input data (" + highestDay + ").";
		}
		Rental[] list = listToArray(rentals);
		Sorter<Rental> m = DSAFactory.getComparisonSorter();
		m.sort(list);
		boolean wasChanged = false;
		StringBuilder s = new StringBuilder("Available rentals for day " + day + " [\n");
		for (Rental r : list) {
			if (r.getStartDay() == day) {
				s.append("   " + r.toString() + "\n");
				wasChanged = true;
			}
		}
		if (!wasChanged) {
			return "Available rentals for day " + day + " [\n   No rentals available.\n]";
		}
		s.append("]");
		return s.toString();
	}

	private Graph<Integer, Rental> buildGraph(List<Rental> list) {
		Graph<Integer, Rental> g = DSAFactory.getDirectedGraph();
		List<Integer> usedDays = DSAFactory.getIndexedList(); //vertices already created
		for (Rental r : list) {
			if (!containsDay(usedDays, r.getStartDay())) {
				g.insertVertex(r.getStartDay());
				usedDays.addFirst(r.getStartDay());
				if (r.getStartDay() < lowestDay) {
					lowestDay = r.getStartDay();
				}
			}
			if (!containsDay(usedDays, r.getEndDay())) {
				g.insertVertex(r.getEndDay());
				usedDays.addFirst(r.getEndDay());
				if (r.getEndDay() > highestDay) {
					highestDay = r.getEndDay();
				}
			} //at this point the two vertices of the current rental are guaranteed to be in the graph
			g.insertEdge(getVertex(g.vertices(), r.getStartDay()), getVertex(g.vertices(), r.getEndDay()), r);
		}
		return g;
	}

	private boolean checkEndDay(int endDay) {
		if (endDay > highestDay) {
			return false;
		}
		return true;
	}

//	private boolean checkDay(int day) {
//		if (checkStartDay(day) && checkEndDay(day)) {
//			return true;
//		}
//		return false;
//	}

	private boolean checkStartDay(int startDay) {
		if (startDay < lowestDay) {
			return false;
		}
		return true;
	}

	private int checkConnectedGraph(int start, int end) {
		Map<Vertex<Integer>, Edge<Rental>> m = GraphTraversalUtil.depthFirstSearch(graph, getVertex(graph.vertices(), start));
		int highest = 0;
		for (Entry<Vertex<Integer>, Edge<Rental>> v : m.entrySet()) {
			int x = v.getKey().getElement();
			if (x == end) { // then you can get from start to end
				return -1;
			}
			if (x > highest) {
				highest = x;
			}
		}
		return highest; // then you cannot reach the end, so give the farthest you can reach
	}
	
	private boolean containsDay(List<Integer> used, Integer test) {
		for (Integer i : used) {
			if (test.equals(i)) {
				return true;
			}
		}
		return false;
	}
	
	private Vertex<Integer> getVertex(Iterable<Vertex<Integer>> vertices, Integer search) {
		for (Vertex<Integer> v : vertices) {
			if (v.getElement().equals(search)) {
				return v;
			}
		}
		return null;
	}
	
	private Entry<Vertex<Integer>, Integer> getEntry(Iterable<Entry<Vertex<Integer>, Integer>> vertices, Integer search) {
		for (Entry<Vertex<Integer>, Integer> v : vertices) {
			if (v.getKey().getElement().equals(search)) {
				return v;
			}
		}
		return null;
	}
	
	private Rental[] listToArray(List<Rental> list) {
		Rental r[] = new Rental[list.size()];
		for (int i = 0; i < list.size(); i++) {
			r[i] = list.get(i);
		}
		return r;
	}
	
	private Graph<Integer, Rental> removeVertices(Graph<Integer, Rental> g, int end) {
		int v = getVertexToRemove(g, end);
		while (v != -1) {
			g.removeVertex(getVertex(g.vertices(), v));
			v = getVertexToRemove(g, end);
		}
		return g;
	}
	
	private int getVertexToRemove(Graph<Integer, Rental> g, int end) {
		for (Vertex<Integer> v : g.vertices()) {
			if (g.outDegree(v) == 0 && v != getVertex(g.vertices(), end)) {
				return v.getElement();
			}
		}
		return -1;
	}
}
