package patch_v3_6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.io.BufferedWriter;
//import java.io.FileWriter;
import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
import java.util.Vector;

public class ProjectPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Vector<Project> projectList;// vector shared between the two panel classes
	private JButton addActButton, clearAllButton, remActButton, findPathsButton, durationButton, criticalButton; // fileButton,
	private JLabel titleLabel, actDurLabel, actDepLabel, errorLabel;
	private JTextField actField, durField, depField;
	private JTextArea actArea, pathArea;
	private JPanel inputPanel, topButtonPanel, allButtonPanel, inputAndButtonsPanel, inputAndScroll, botButtonPanel;
	private JScrollPane scroll, scroll2;
	public JMenuBar menuBar;
	private JMenu menu;
	JMenuItem fileMenuItem, helpMenuItem, aboutMenuItem;
	JRadioButtonMenuItem rbMenuItem;
	JCheckBoxMenuItem cbMenuItem;
	private String projName, projDepends;
	private int projNum;
	private boolean error1, error2;// dependencyFlag, loopFlag; // error4;
	private boolean startPointFlag = false, actFlag = false;

	// constructor for project panel to be used in applet
	public ProjectPanel(Vector<Project> projectList) {

		this.projectList = projectList;
		actArea = new JTextArea("No Activities");
		pathArea = new JTextArea("No paths to display");
		titleLabel = new JLabel("Activity Title");
		actDurLabel = new JLabel("Activity Duration");
		actDepLabel = new JLabel("Activity Dependencies");
		errorLabel = new JLabel("");
		actField = new JTextField();
		durField = new JTextField();
		depField = new JTextField();
		addActButton = new JButton("Add activity");
		clearAllButton = new JButton("Clear all");
		// helpButton = new JButton("Help");
		remActButton = new JButton("Remove activity");
		findPathsButton = new JButton("Find paths");
		// aboutButton = new JButton("About");
		durationButton = new JButton("Change duration");
//		fileButton = new JButton("Print to file");
		criticalButton = new JButton("Critical path");

		// Inputs bunched together into one panel
		inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(4, 2));
		inputPanel.add(titleLabel);
		inputPanel.add(actField);
		inputPanel.add(actDurLabel);
		inputPanel.add(durField);
		inputPanel.add(actDepLabel);
		inputPanel.add(depField);

		// top button group
		topButtonPanel = new JPanel();
		topButtonPanel.setLayout(new FlowLayout());
		topButtonPanel.add(addActButton);
		topButtonPanel.add(remActButton);
		topButtonPanel.add(durationButton);

		// bottom button group
		botButtonPanel = new JPanel();
		botButtonPanel.setLayout(new FlowLayout());
		botButtonPanel.add(clearAllButton);
		botButtonPanel.add(findPathsButton);
		botButtonPanel.add(criticalButton);
//		botButtonPanel.add(fileButton);

		// all buttons combined
		allButtonPanel = new JPanel();
		allButtonPanel.setLayout(new GridLayout(3, 1));
		allButtonPanel.add(topButtonPanel);
		// allButtonPanel.add(midButtonPanel);
		allButtonPanel.add(botButtonPanel);

		// input fields and buttons grouped
		inputAndButtonsPanel = new JPanel(); // left side panel
		inputAndButtonsPanel.setLayout(new GridLayout(3, 1));
		inputAndButtonsPanel.add(errorLabel);
		errorLabel.setForeground(Color.RED); // errors will be in red
		inputAndButtonsPanel.add(inputPanel);
		inputAndButtonsPanel.add(allButtonPanel);

		scroll = new JScrollPane(actArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);// adds the text area to a scroll pane

		scroll2 = new JScrollPane(pathArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		inputAndScroll = new JPanel();
		inputAndScroll.setLayout(new GridLayout(1, 2));
		inputAndScroll.add(inputAndButtonsPanel);
		inputAndScroll.add(scroll);

		setLayout(new GridLayout(2, 1));
		add(inputAndScroll);
		add(scroll2);

		// menu bar for additional options, as to not clutter UI with too many buttons
		menuBar = new JMenuBar();
		// Build the first menu.
		menu = new JMenu("Options");
		menuBar.add(menu);

		// a group of JMenuItems
		fileMenuItem = new JMenuItem("Print to file");
		menu.add(fileMenuItem);
		helpMenuItem = new JMenuItem("Help");
		menu.add(helpMenuItem);
		aboutMenuItem = new JMenuItem("About");
		menu.add(aboutMenuItem);

		addActButton.addActionListener(new ButtonListener());
		clearAllButton.addActionListener(new ButtonListener());
		remActButton.addActionListener(new ButtonListener());
		findPathsButton.addActionListener(new ButtonListener());
		criticalButton.addActionListener(new ButtonListener());
		durationButton.addActionListener(new ButtonListener());
		helpMenuItem.addActionListener(new ButtonListener());
		aboutMenuItem.addActionListener(new ButtonListener());
		fileMenuItem.addActionListener(new ButtonListener());
//		fileButton.addActionListener(new ButtonListener());

	}

//***********************************************************************************************************
//	ButtonListener Class
//***********************************************************************************************************
	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			// booleans for use with the error handling of adding a project
			error1 = false;
			error2 = false;
			@SuppressWarnings("unused")
			boolean dependencyFlag; //guard values
			@SuppressWarnings("unused")
			boolean loopFlag;
			Object source = event.getSource();

//***********************************************************************************************************
//			Add and Activity
//***********************************************************************************************************
			if (source == addActButton && actField.getText().length() > 0 && durField.getText().length() > 0) {

				try {
					projNum = Integer.parseInt(durField.getText()); // tries to parse the value as an int, sends error
					// if not int
					if (projNum < 0) { // number must be positive - can be 0 for a place holder...
						errorLabel.setText("Durations must be positive integers.");
						error1 = true;
						clearInputs();
						return;

					}
					projName = (actField.getText());
					projDepends = depField.getText();

				} catch (java.lang.NumberFormatException e1) {
					errorLabel.setText("Please enter an integer for the activity duration");// error message to print
					error1 = true;
					clearInputs();
					return;
				}

				for (int i = 0; i < projectList.size(); i++)// for loop to cycle through vector
				{
					if (projectList.get(i).getProjTitle().equals(projName))// searches for duplicate project numbers and
					// duplicate project names
					{
						errorLabel.setText("Duplicate activities not allowed");
						error2 = true;
						clearInputs();
					}
				}

				if (projDepends.equals("") && startPointFlag) {
					errorLabel.setText("Multiple start points detected");
					clearInputs();
					return;
				}

				if (!(error1 || error2))// if all criteria are met for it to be a new project,
				// creates and adds to vector
				{
					Project proj = new Project();
					proj.setProjDependencies(projDepends);
					proj.setProjDuration(projNum);
					proj.setProjTitle(projName);

					if (projDepends.equals("") && !startPointFlag) {
						startPointFlag = true;
					}

					projectList.add(proj);
					if (projectList.size() == 1) {
						actArea.setText("");
					}
					actArea.append(proj.toString());

					errorLabel.setText("Activity added");
					clearInputs();
				}
			}


			else if (source == addActButton && (actField.getText().length() == 0 || durField.getText().length() == 0)) {
				errorLabel.setText("Please enter required fields");
				clearInputs();
			}
			
//***********************************************************************************************************
//			Clear All Button
//***********************************************************************************************************

			if (source == clearAllButton) // removes all activities in projectList and resets the appropriate textfields
			{
				projectList.removeAllElements();
				actArea.setText("No activities");
				pathArea.setText("No paths to display");
				clearInputs();
				startPointFlag = false;
				errorLabel.setText("All activities removed");
			}
			
//***********************************************************************************************************
//			Removed an Activity
//***********************************************************************************************************

			if (source == remActButton && actField.getText().length() > 0) // removes the user-specified activity
			{
				String projToBeRemoved = actField.getText();
				boolean removed = false;

				for (int i = 0; i < projectList.size(); i++) {
					if (projectList.get(i).getProjTitle().equals(projToBeRemoved)) {
						if (projectList.get(i).getProjDependencies().equals("")) {
							startPointFlag = false;
						}
						projectList.remove(i);
						removed = true;
					}

				}

				if (removed) {
					errorLabel.setText("The activity was removed");
					clearInputs();
					clearOutputs();

					for (int i = 0; i < projectList.size(); i++) {
						actArea.append(projectList.get(i).toString()); // reprints the current activities after removal
					}
				} else {
					errorLabel.setText("The activity was not found");
					clearInputs();

				}
			}

			else if (source == remActButton && actField.getText().length() == 0) {
				errorLabel.setText("Type the title of the activity to be removed");
				clearInputs();

			}

//***********************************************************************************************************
//			ChangeDuration
//***********************************************************************************************************

			if (source == durationButton && actField.getText().length() > 0 && durField.getText().length() > 0) {

				actFlag = false;

				try {
					projNum = Integer.parseInt(durField.getText()); // tries to parse the value as an int, sends error
					// if not int
					if (projNum < 0) { // number must be positive - can be 0 for a place holder...
						errorLabel.setText("Durations must be positive integers.");
						// error1 = true;
						clearInputs();
						return;

					}
					projName = (actField.getText());
					// projDepends = depField.getText();

				} catch (java.lang.NumberFormatException e1) {
					errorLabel.setText("Please enter an integer for the activity duration");// error message to print
					// error1 = true;
					clearInputs();
					return;
				}

				for (int i = 0; i < projectList.size(); i++)// for loop to cycle through vector
				{
					if (projectList.get(i).getProjTitle().equals(projName)) {
						actFlag = true;
						projDepends = projectList.get(i).getProjDependencies();
						projectList.remove(i);
					}
				}

				if (!actFlag) {
					errorLabel.setText("Non-existent activity");
					clearInputs();
					return;
				}

				clearOutputs();

				Project proj2 = new Project();
				proj2.setProjDependencies(projDepends);
				proj2.setProjDuration(projNum);
				proj2.setProjTitle(projName);

				projectList.add(proj2);

				for (int k = 0; k < projectList.size(); k++) {
					actArea.append(projectList.get(k).toString()); // reprints the current activities after removal
				}
				errorLabel.setText("Duration edited");
				clearInputs();
				return;
			}

			else if (source == durationButton
					&& (actField.getText().length() == 0 || durField.getText().length() == 0)) {
				errorLabel.setText("Please enter required fields");
				clearInputs();
			}

//***********************************************************************************************************
//			Find Paths
//***********************************************************************************************************

			if (source == findPathsButton) {
				
				if(!startPointFlag) {
					errorLabel.setText("No Starting point detected");
					return;
				}
				
				errorLabel.setText("");
				dependencyFlag = true;
				loopFlag = true;
				
				if (projectList.isEmpty()) {
					errorLabel.setText("Please enter activities to find path");
				} else {
					GetPathInfo path = new GetPathInfo(projectList);

					if (path.checkDependency()) {

						errorLabel.setText("Non-existent dependency detected");

					} else if (path.checkLoop()) {
						errorLabel.setText("Loop detected");
					} else {
						pathArea.setText(path.toString());
					}

				}
			}
//***********************************************************************************************************
//			Critical Paths button
//***********************************************************************************************************

			if (source == criticalButton && startPointFlag) {

				if(!startPointFlag) {
					errorLabel.setText("No Starting point detected");
					return;
				}
				errorLabel.setText("");
				dependencyFlag = false;
				loopFlag = false;
				
				if (projectList.isEmpty()) {
					errorLabel.setText("Please enter activities to find path");
				} else{
					GetPathInfo path = new GetPathInfo(projectList);

					if (path.checkDependency()) {

						errorLabel.setText("Non-existent dependency detected");

					} else if (path.checkLoop()) {
						errorLabel.setText("Loop detected");
					} else {
						pathArea.setText("Critical Path: \n" + path.getCritical());
					}
				}
			}

//***********************************************************************************************************
//			About
//***********************************************************************************************************			

			if (source == aboutMenuItem) {
				String about = "Activity Planner GUI version 3.0\n" + "Version Date:  3 November 2018\n"
						+ "ASU CSE360 Fall 2018\n\n" + "Monday 7:30 Group 5:\n" + "Brian Crethers\n"
						+ "Elin (Yi-Chuan) Lin\n" + "Giovanni Palomo\n" + "Stefan Savic\n";
				JOptionPane.showMessageDialog(null, about, "About", JOptionPane.PLAIN_MESSAGE);
			}
//***********************************************************************************************************
//			Write File
//***********************************************************************************************************			
			if (source == fileMenuItem) {
				// String inputError = "Please enter a name for the output file";
				// String success = "Successfully printed to file ";

				String fName = JOptionPane.showInputDialog("Please Enter File Name:");
				if (projectList.isEmpty()) {
					errorLabel.setText("Activity List is Empty! Please add activities.");

				} else if (fName.compareTo("") == 0)
					errorLabel.setText("No file name specified");

				else if (projectList.size() < 1) {
					errorLabel.setText("List is empty, plese add activities");
				}

				else {
					fName = fName + ".txt"; // append file e
					// System.out.println(projectList.get(0).getActnDur());
					GetPathInfo paths = new GetPathInfo(projectList);

					try {

						paths.writeFile(fName);

					} catch (IOException e) {
						System.out.println("Error");
					}

				}
			}
			

//***********************************************************************************************************
//			Help File Menu Item
//***********************************************************************************************************

			if (source == helpMenuItem) {
//				String help = HelpFile.help();
				String help = "<html><b>Add activity<br/>\nAdds an activity consisting of a "
						+ "string-based name, an int duration and any number\nof dependencies to a list of "
						+ "projects\n<html><b>Remove activity<br/>\nRemoves a user-specified activity from the current "
						+ "list of projects and clears it from\nthe activity view\n<html><b>Find paths!<br/>\nSorts the"
						+ " current list of projects in order of the activity name and displays the result"
						+ "\n<html><b>Clear all<br/>\nResets the current session by removing every entered activity "
						+ "from the project list";
				JOptionPane.showMessageDialog(null, help, "Help", JOptionPane.PLAIN_MESSAGE);
				//pathArea.setText(help);  //HTML tags won't work...

			}
		}
		
		
//***********************************************************************************************************
//		Clear Inputs
//***********************************************************************************************************

		private void clearInputs() {
			actField.setText("");
			durField.setText("");
			depField.setText("");
		}
		
		
//***********************************************************************************************************
//		Clear Outputs
//***********************************************************************************************************

		private void clearOutputs() {
			actArea.setText("");
			pathArea.setText("");
		}
	} // end of ButtonListener class


}
