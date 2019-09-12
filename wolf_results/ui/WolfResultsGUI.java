package edu.ncsu.csc216.wolf_results.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import edu.ncsu.csc216.wolf_results.manager.WolfResultsManager;

/**
 * Wolf Results GUI.
 * 
 * @author Sammy Penninger, Griffin Brookshire
 */
public class WolfResultsGUI extends JFrame implements ActionListener, Observer {

	/** ID number used for object serialization. */
	private static final long serialVersionUID = 1L;
	/** Title for top of GUI. */
	private static final String APP_TITLE = "Wolf Results";
	/** Text for the File Menu. */
	private static final String FILE_MENU_TITLE = "File";
	/** Text for the New Issue XML menu item. */
	private static final String NEW_FILE_TITLE = "New";
	/** Text for the Load Issue XML menu item. */
	private static final String LOAD_FILE_TITLE = "Load";
	/** Text for the Save menu item. */
	private static final String SAVE_FILE_TITLE = "Save";
	/** Text for the Quit menu item. */
	private static final String QUIT_TITLE = "Quit";
	/** Menu bar for the GUI that contains Menus. */
	private JMenuBar menuBar;
	/** Menu for the GUI. */
	private JMenu menu;
	/** Menu item for creating a new list of Races. */
	private JMenuItem itemNewFile;
	/** Menu item for loading a file. */
	private JMenuItem itemLoadFile;
	/** Menu item for saving the list to a file. */
	private JMenuItem itemSaveFile;
	/** Menu item for quitting the program. */
	private JMenuItem itemQuit;
	
	//private WolfResultsManager manager = WolfResultsManager.getInstance();

	/**
	 * Constructs the GUI.
	 */
	public WolfResultsGUI() {
		super();

		// Observe Manager
		WolfResultsManager.getInstance().addObserver(this);

		// Set up general GUI info
		setSize(1500, 500);
		setLocation(50, 50);
		setTitle(APP_TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUpMenuBar();

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				doExit();
			}

		});

		initializeGUI();

		// Set the GUI visible
		setVisible(true);
	}

	/**
	 * Initializes GUI
	 */
	private void initializeGUI() {
		// TODO initialize your GUI
		// We encourage creating inner classes for the major components of the GUI
		
		Container c = this.getContentPane(); //entire frame
		c.setLayout(new BorderLayout());
		
		// Set up left and right sides of the container
		JPanel left = new JPanel(); //left frame
		JPanel right = new JPanel(); // right frame
		left.setPreferredSize(new Dimension(720, 490));
		right.setPreferredSize(new Dimension(720, 490));
		Border border = BorderFactory.createTitledBorder("Left");
		//left.setBorder(border);
		border = BorderFactory.createTitledBorder("Race Results");
		right.setBorder(border);
		
		// Set up left side of GUI with races and filter options
		JPanel races = new JPanel(); // top left panel - display raceList and buttons
		JPanel filter = new JPanel(); // bottom left panel - display filter options
		border = BorderFactory.createTitledBorder("Races");
		races.setBorder(border);
		races.setPreferredSize(new Dimension(720, 220));
		border = BorderFactory.createTitledBorder("Filter Race Results");
		filter.setBorder(border);
		filter.setPreferredSize(new Dimension(720, 220));
		
		// Set up race list in races
		String[] test = new String[10];
		JList<String> raceList = new JList<String>(test);
		raceList.setSize(new Dimension(50, 50));
		races.add(raceList);
		raceList.setBackground(Color.WHITE);
		raceList.setBounds(10, 20, 330, 82);
		raceList.setVisible(true);
		
		// Add buttons to races
		races.setLayout(null);
		JButton addBut = new JButton("Add Race");
		addBut.setBounds(350, 20, 340, 20);		
		JButton removeBut = new JButton("Remove Race");
		removeBut.setBounds(350, 40, 340, 20);	
		JButton editBut = new JButton("Edit Race");
		editBut.setBounds(350, 60, 340, 20);
		JButton unselectBut = new JButton("Unselect Race");
		unselectBut.setBounds(350, 80, 340, 20);
		races.add(addBut);
		races.add(removeBut);
		races.add(editBut);
		races.add(unselectBut);
		
		// Set up race details panel
		JPanel raceDetails = new JPanel();
		raceDetails.setLayout(null);
		border = BorderFactory.createTitledBorder("Race Details");
		raceDetails.setBorder(border);
		raceDetails.setPreferredSize(new Dimension(710, 110));
		raceDetails.setBounds(6, 105, 700, 110);
		races.add(raceDetails, BorderLayout.SOUTH);
		
		// Add text labels to raceDetails
		JLabel nameLabel = new JLabel("Race Name");
		nameLabel.setBounds(8, 18, 100, 20);
		JLabel distLabel = new JLabel("Race Distance");
		distLabel.setBounds(8, 38, 100, 20);
		JLabel dateLabel = new JLabel("Race Date");
		dateLabel.setBounds(8, 58, 100, 20);
		JLabel locLabel = new JLabel("Race Location");
		locLabel.setBounds(8, 78, 100, 20);
		raceDetails.add(nameLabel, BorderLayout.WEST);
		raceDetails.add(distLabel, BorderLayout.WEST);
		raceDetails.add(dateLabel, BorderLayout.WEST);
		raceDetails.add(locLabel, BorderLayout.WEST);
		
		// Add text areas to raceDetails
		JTextArea name = new JTextArea("");
		name.setEditable(false);
		name.setBounds(340, 19, 350, 18);
		JTextArea dist = new JTextArea("");
		dist.setEditable(true);
		dist.setBounds(340, 38, 350, 19);
		JTextArea date = new JTextArea("");
		date.setEditable(false);
		date.setBounds(340, 58, 350, 19);
		JTextArea loc = new JTextArea("");
		loc.setEditable(false);
		loc.setBounds(340, 78, 350, 19);
		raceDetails.add(name);
		raceDetails.add(dist);
		raceDetails.add(date);
		raceDetails.add(loc);
		
		// Add text labels to filter
		filter.setLayout(null);
		JLabel ageMinLabel = new JLabel("Age Min");
		ageMinLabel.setBounds(6, 25, 100, 20);
		JLabel ageMaxLabel = new JLabel("Age Max");
		ageMaxLabel.setBounds(6, 60, 100, 20);
		JLabel paceMinLabel = new JLabel("Pace Min");
		paceMinLabel.setBounds(6, 95, 100, 20);
		JLabel paceMaxLabel = new JLabel("Pace Max");
		paceMaxLabel.setBounds(6, 130, 100, 20);
		filter.add(ageMinLabel);
		filter.add(ageMaxLabel);
		filter.add(paceMinLabel);
		filter.add(paceMaxLabel);
		
		// Add text areas to filter
		JTextArea ageMinText = new JTextArea("");
		ageMinText.setEditable(true);
		ageMinText.setBounds(350, 25, 350, 30);
		JTextArea ageMaxText = new JTextArea("");
		ageMaxText.setEditable(true);
		ageMaxText.setBounds(350, 60, 350, 30);
		JTextArea paceMinText = new JTextArea("");
		paceMinText.setEditable(true);
		paceMinText.setBounds(350, 95, 350, 30);
		JTextArea paceMaxText = new JTextArea("");
		paceMaxText.setEditable(true);
		paceMaxText.setBounds(350, 130, 350, 30);
		filter.add(ageMinText);
		filter.add(ageMaxText);
		filter.add(paceMinText);
		filter.add(paceMaxText);
		
		// Add the filter button to filter
		JButton filterButton = new JButton("Filter");
		filterButton.setBounds(6, 168, 340, 40);
		filter.add(filterButton);
		
		// Add text fields to right
		right.setLayout(null);
		JLabel runName = new JLabel("Runner Name");
		runName.setBounds(6, 300, 100, 20);
		JLabel runAge = new JLabel("Runner Age");
		runAge.setBounds(6, 340, 100, 20);
		JLabel runTime = new JLabel("Runner Time");
		runTime.setBounds(6, 380, 100, 20);
		right.add(runName);
		right.add(runAge);
		right.add(runTime);
		
		// Add text areas to right
		JTextArea runNam = new JTextArea();
		runNam.setBounds(400, 300, 300, 30);
		JTextArea runAg = new JTextArea();
		runAg.setBounds(400, 340, 300, 30);
		JTextArea runTim = new JTextArea();
		runTim.setBounds(400, 380, 300, 30);
		right.add(runNam);
		right.add(runAg);
		right.add(runTim);
		
		// Add the table to right panel
		String[] columns = {"Runner", "Age", "Time", "Pace"};
		DefaultTableModel tableModel = new DefaultTableModel(columns, 3);
		JTable results = new JTable(tableModel);
		results.setBounds(10, 20, 700, 130);
		right.add(results);
		
		// Add the 'add' button to the right table
		JButton add = new JButton("Add");
		add.setBounds(360, 155, 80, 30);
		right.add(add);
		
		left.add(races, BorderLayout.WEST);
		left.add(filter, BorderLayout.WEST);

		c.add(left, BorderLayout.WEST);
		c.add(right, BorderLayout.EAST);
		
		c.setVisible(true);
		
		
		// Set up the Race Results panel
		//JTable results = new JTable();
		
		
		
		
		
		
		
		
	}

	/**
	 * Makes the GUI Menu bar that contains options for loading a file
	 * containing issues or for quitting the application.
	 */
	private void setUpMenuBar() {
		// Construct Menu items
		menuBar = new JMenuBar();
		menu = new JMenu(FILE_MENU_TITLE);
		itemNewFile = new JMenuItem(NEW_FILE_TITLE);
		itemLoadFile = new JMenuItem(LOAD_FILE_TITLE);
		itemSaveFile = new JMenuItem(SAVE_FILE_TITLE);
		itemQuit = new JMenuItem(QUIT_TITLE);
		itemNewFile.addActionListener(this);
		itemLoadFile.addActionListener(this);
		itemSaveFile.addActionListener(this);
		itemQuit.addActionListener(this);

		// Start with save button disabled
		itemSaveFile.setEnabled(false);

		// Build Menu and add to GUI
		menu.add(itemNewFile);
		menu.add(itemLoadFile);
		menu.add(itemSaveFile);
		menu.add(itemQuit);
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
	}

	/**
	 * Exits the GUI
	 */
	private void doExit() {
		if (WolfResultsManager.getInstance().isChanged()) {
			doSaveFile();
		}

		if (!WolfResultsManager.getInstance().isChanged()) {
			System.exit(NORMAL);
		} else { // Did NOT save when prompted to save
			JOptionPane.showMessageDialog(this,
					"Race Results changes have not been saved. "
							+ "Your changes will not be saved.",
							"Saving Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Saves GUI to file
	 */
	private void doSaveFile() {
		try {
			WolfResultsManager instance = WolfResultsManager.getInstance();
			JFileChooser chooser = new JFileChooser("./");
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"Race Results files (md)", "md");
			chooser.setFileFilter(filter);
			chooser.setMultiSelectionEnabled(false);
			if (instance.getFilename() != null) {
				chooser.setSelectedFile(new File(instance.getFilename()));
			}
			int returnVal = chooser.showSaveDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String filename = chooser.getSelectedFile().getAbsolutePath();
				if (chooser.getSelectedFile().getName().trim().equals("")
						|| !chooser.getSelectedFile().getName()
						.endsWith(".md")) {
					throw new IllegalArgumentException();
				}
				instance.setFilename(filename);
				instance.saveFile(filename);
			}
			itemLoadFile.setEnabled(true);
			itemNewFile.setEnabled(true);
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this, "File not saved.",
					"Saving Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Loads race results from file
	 */
	private void doLoadFile() {
		try {
			WolfResultsManager instance = WolfResultsManager.getInstance();
			JFileChooser chooser = new JFileChooser("./");
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"Race Results files (md)", "md");
			chooser.setFileFilter(filter);
			chooser.setMultiSelectionEnabled(false);
			int returnVal = chooser.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				instance.loadFile(chooser.getSelectedFile().getAbsolutePath());
			}
			itemLoadFile.setEnabled(false);
			itemNewFile.setEnabled(false);
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this, "Error opening file.",
					"Opening Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof WolfResultsManager) {
			itemSaveFile.setEnabled(true);
			// TODO Call methods to update the contents of the GUI
			repaint();
			validate();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		WolfResultsManager instance = WolfResultsManager.getInstance();

		if (e.getSource() == itemNewFile) {
			doSaveFile();
			instance.newList();
		} else if (e.getSource() == itemLoadFile) {
			doLoadFile();
		} else if (e.getSource() == itemSaveFile) {
			doSaveFile();
		} else if (e.getSource() == itemQuit) {
			doExit();
		}
	}

	/**
	 * Starts the application
	 * 
	 * @param args command line args
	 */
	public static void main(String[] args) {
		new WolfResultsGUI();
	}

}