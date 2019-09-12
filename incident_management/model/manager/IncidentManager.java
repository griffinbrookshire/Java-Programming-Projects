/**
 * 
 */
package edu.ncsu.csc216.incident_management.model.manager;

import java.util.List;

import edu.ncsu.csc216.incident.io.IncidentIOException;
import edu.ncsu.csc216.incident.io.IncidentReader;
import edu.ncsu.csc216.incident.io.IncidentWriter;
import edu.ncsu.csc216.incident.xml.Incident;
import edu.ncsu.csc216.incident_management.model.command.Command;
import edu.ncsu.csc216.incident_management.model.incident.ManagedIncident;
import edu.ncsu.csc216.incident_management.model.incident.ManagedIncident.Category;
import edu.ncsu.csc216.incident_management.model.incident.ManagedIncident.Priority;

/**
 * Holds and manages the collection of incidents
 * 
 * @author griffin
 */
public class IncidentManager {

	/** Holds the current instance of IncidentManager */
	private static IncidentManager instance;
	/** Holds the current incident list */
	private ManagedIncidentList incidents;

	/**
	 * Creates an incident manager
	 */
	private IncidentManager() {
		incidents = new ManagedIncidentList();
	}

	/**
	 * Gets the instance of the incident manager
	 * 
	 * @return instance of manager
	 */
	public static IncidentManager getInstance() {
		if (instance == null) {
			instance = new IncidentManager();
		}
		return instance;
	}

	/**
	 * Saves the current collection of incidents to an XML file
	 * 
	 * @param filename name of file to save to
	 */
	public void saveManagedIncidentsToFile(String filename) {
		IncidentWriter write;
		try {
			write = new IncidentWriter(filename);
			for (int i = 0; i < incidents.getManagedIncidents().size(); i++) {
				Incident inc = incidents.getManagedIncidents().get(i).getXMLIncident();
				write.addItem(inc);
			}
			write.marshal();
		} catch (IncidentIOException e) {
			throw new IllegalArgumentException("Unable to save file");
		}
	}

	/**
	 * Loads incidents from an XML file
	 * 
	 * @param filename name of file to load from
	 */
	public void loadManagedIncidentsFromFile(String filename) {
		IncidentReader read;
		try {
			read = new IncidentReader(filename);
		} catch (IncidentIOException e) {
			throw new IllegalArgumentException("Unable to load file");
		}
		incidents.addXMLIncidents(read.getIncidents());
	}

	/**
	 * Creates a new blank list of incidents
	 */
	public void createNewManagedIncidentList() {
		ManagedIncident.setCounter(0);
		incidents = new ManagedIncidentList();
	}

	/**
	 * Gets the current incidents in a 2-D string array
	 * 
	 * @return incidents array
	 */
	public String[][] getManagedIncidentsAsArray() {
		List<ManagedIncident> list = incidents.getManagedIncidents();
		String[][] array = new String[list.size()][5];
		for (int i = 0; i < list.size(); i++) {
			array[i][0] = Integer.toString(list.get(i).getIncidentId());
			array[i][1] = list.get(i).getCategoryString();
			array[i][2] = list.get(i).getStateName();
			array[i][3] = list.get(i).getPriorityString();
			array[i][4] = list.get(i).getName();
		}
		return array;
	}

	/**
	 * Gets the current incidents in a specified category in a 2-D string array
	 * 
	 * @param c category to search for
	 * @return incidents array
	 */
	public String[][] getManagedIncidentsAsArrayByCategory(Category c) {
		if (c == null) {
			throw new IllegalArgumentException();
		}
		List<ManagedIncident> list = incidents.getIncidentsByCategory(c);
		String[][] array = new String[list.size()][5];
		for (int i = 0; i < list.size(); i++) {
			array[i][0] = Integer.toString(list.get(i).getIncidentId());
			array[i][1] = list.get(i).getCategoryString();
			array[i][2] = list.get(i).getStateName();
			array[i][3] = list.get(i).getPriorityString();
			array[i][4] = list.get(i).getName();
		}
		return array;
	}

	/**
	 * Gets the incident with specified id
	 * 
	 * @param id id of incident
	 * @return incident
	 */
	public ManagedIncident getManagedIncidentById(int id) {
		return incidents.getIncidentById(id);
	}

	/**
	 * Executes specified command
	 * 
	 * @param i index of incident to execute command on
	 * @param c command to execute
	 */
	public void executeCommand(int i, Command c) {
		incidents.executeCommand(i, c);
	}

	/**
	 * Deletes the incident with the specified id
	 * 
	 * @param id id of incident to delete
	 */
	public void deleteManagedIncidentById(int id) {
		incidents.deleteIncidentById(id);
	}

	/**
	 * Adds an incident with the specified details to the list
	 * 
	 * @param caller Caller of the incident
	 * @param category Category of incident
	 * @param priority Priority of the incident
	 * @param name Incident’s name information from when the incident is created
	 * @param workNote Notes regarding the incident
	 */
	public void addManagedIncidentToList(String caller, Category category, Priority priority, String name, String workNote) {
		incidents.addIncident(caller, category, priority, name, workNote);
	}

}
