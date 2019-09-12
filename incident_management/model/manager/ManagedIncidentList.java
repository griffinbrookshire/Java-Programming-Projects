/**
 * 
 */
package edu.ncsu.csc216.incident_management.model.manager;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc216.incident.xml.Incident;
import edu.ncsu.csc216.incident_management.model.command.Command;
import edu.ncsu.csc216.incident_management.model.incident.ManagedIncident;
import edu.ncsu.csc216.incident_management.model.incident.ManagedIncident.Category;
import edu.ncsu.csc216.incident_management.model.incident.ManagedIncident.Priority;

/**
 * List of managed incidents
 * 
 * @author griffin
 *
 */
public class ManagedIncidentList {
	
	/** Collection of Incidents */
	private ArrayList<ManagedIncident> incidents;
	
	/**
	 * Creates a list of managed incidents
	 */
	public ManagedIncidentList() {
		incidents = new ArrayList<ManagedIncident>();
		ManagedIncident.setCounter(0);
	}
	
	/**
	 * Adds an incident with the specified details
	 * 
	 * @param caller Caller of the incident
	 * @param category Category of incident
	 * @param priority Priority of the incident
	 * @param name Incident’s name information from when the incident is created
	 * @param workNote Notes regarding the incident
	 * @return id IncidentId of the created incident
	 */
	public int addIncident(String caller, Category category, Priority priority, String name, String workNote) {
		ManagedIncident m = new ManagedIncident(caller, category, priority, name, workNote);
		incidents.add(m);
		return m.getIncidentId();
	}

	/**
	 * Adds incidents from an XML file to the list of incidents
	 * 
	 * @param l list of incidents to add from xml
	 */
	public void addXMLIncidents(List<Incident> l) {
		for (int i = 0; i < l.size(); i++) {
			Incident inc = l.get(i);
			ManagedIncident m = new ManagedIncident(inc);
			incidents.add(m);
		}
		if (l.size() > 0) {
			ManagedIncident.setCounter(getMaxId() + 1);
		}
	}
	
	/**
	 * Gets the list of managed incidents
	 * 
	 * @return list of managed incidents
	 */
	public List<ManagedIncident> getManagedIncidents() {
		return incidents;
	}
	
	/**
	 * Gets a list of managed incidents with specified category
	 * 
	 * @param c category
	 * @return list List of incidents with specified category
	 */
	public List<ManagedIncident> getIncidentsByCategory(Category c) {
		if (c == null) {
			throw new IllegalArgumentException();
		}
		List<ManagedIncident> list = new ArrayList<ManagedIncident>();
		for (int i = 0; i < incidents.size(); i++) {
			if (incidents.get(i).getCategory().equals(c)) {
				list.add(incidents.get(i));
			}
		}
		return list;
	}
	
	/**
	 * Gets the managed incident with the specified id
	 * 
	 * @param id id of incident
	 * @return incident with id
	 */
	public ManagedIncident getIncidentById(int id) {
		for (int i = 0; i < incidents.size(); i++) {
			if (incidents.get(i).getIncidentId() == id) {
				return incidents.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Executes specified command
	 * 
	 * @param id incidentId of incident to execute command on
	 * @param c command to execute
	 */
	public void executeCommand(int id, Command c) {
		for (int i = 0; i < incidents.size(); i++) {
			if (incidents.get(i).getIncidentId() == id) {
				incidents.get(i).update(c);
			}
		}
	}
	
	/**
	 * Deletes the incident with the specified id
	 * 
	 * @param id id of incident to delete
	 */
	public void deleteIncidentById(int id) {
		for (int i = 0; i < incidents.size(); i++) {
			if (incidents.get(i).getIncidentId() == id) {
				incidents.remove(i);
			}
		}
	}
	
	private int getMaxId() {
		int max = 0;
		for (int i = 0; i < incidents.size(); i++) {
			max = Math.max(max, incidents.get(i).getIncidentId());
		}
		return max;
	}
}
