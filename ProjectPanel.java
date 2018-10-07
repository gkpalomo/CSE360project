package brian_patch_2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;

public class ProjectPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vector<Project> projectList;// vector shared between the two panel classes
	private JButton addActButton, clearAllButton, helpButton, remActButton, findPathButton, aboutButton;
	private JLabel titleLabel, actDurLabel, actDepLabel, errorLabel;
	private JTextField actField, durField, depField;
	private JTextArea actArea, pathArea;
	private JPanel inputPanel, topButtonPanel, allButtonPanel, inputAndButtonsPanel, inputAndScroll, botButtonPanel;// ,panel7;
	private JScrollPane scroll, scroll2;// ,scroll3;
	// private JList projList,list1,list2;
	// private Project selectedProj1,selectedProj2;
	private DirectedGraph dGraph;

	// constructor for project panel to be used in applet
	public ProjectPanel(Vector<Project> projectList) {

		dGraph = new DirectedGraph();
		this.projectList = projectList;
		actArea = new JTextArea("No Activities");
		pathArea = new JTextArea("No Paths to display");
		titleLabel = new JLabel("Activity Title");
		actDurLabel = new JLabel("Activity Duration");
		actDepLabel = new JLabel("Activity Dependencies");
		errorLabel = new JLabel("");
		actField = new JTextField();
		durField = new JTextField();
		depField = new JTextField();
		addActButton = new JButton("Add activity");
		clearAllButton = new JButton("Clear all");
		helpButton = new JButton("Help");
		remActButton = new JButton("Remove activity");
		findPathButton = new JButton("Find Path!");
		aboutButton = new JButton("About");
		// projList = new JList(projectList);
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
		topButtonPanel.add(findPathButton);
		// bottom button group
		botButtonPanel = new JPanel();
		botButtonPanel.setLayout(new FlowLayout());
		botButtonPanel.add(clearAllButton);
		botButtonPanel.add(helpButton);
		botButtonPanel.add(aboutButton);
		// all buttons combined
		allButtonPanel = new JPanel();
		allButtonPanel.setLayout(new GridLayout(3, 1));
		allButtonPanel.add(topButtonPanel);
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

		addActButton.addActionListener(new ButtonListener());
		clearAllButton.addActionListener(new ButtonListener());
		remActButton.addActionListener(new ButtonListener());
		findPathButton.addActionListener(new ButtonListener());
		aboutButton.addActionListener(new ButtonListener());

	}

	// ButtonListener is a listener class that listens to
	// see if the button "Create a project" is pushed.
	// When the event occurs, it add the project information
	// in the text fields to the text area
	// and the list of project information,
	// and it also does error checking.

	private class ButtonListener implements ActionListener {

		private boolean hasError;
		private int eCode; // error code to be used later

		private String projName, projDepends, projDurStr;
		private int projDuration;

		public void actionPerformed(ActionEvent event) {

			// booleans for use with the error handling of adding a project
			hasError = false;
			eCode = 0;
			/*
			 * Error Codes: 1 = no input in text fields 2 = no input in the activity name
			 * field 3 = no duration entered 4 = duration is not an integer 5 = duration is
			 * negative 6 = duplicate activity names 7 = duplicate starting points
			 * 
			 */
			// noDurError = false;
			// error2 = false;
			// error3 = false;
			// duplicateActError = false;

			Object source = event.getSource();
			// proj must have all fields filled
			if (source == addActButton) {

				projName = (actField.getText());
				projDepends = depField.getText();
				projDurStr = durField.getText();

				checkInputErrors(projName, projDurStr, projDepends);

				if (!hasError)// if all criteria are met for it to be a new
								// project,
				// creates and adds to vector
				{
					Project proj = new Project();
					proj.setProjDependencies(projDepends);
					proj.setProjDuration(projDuration);
					proj.setProjTitle(projName);

					projectList.add(proj);
					if (projectList.size() == 1) {
						actArea.setText("");
					}
					actArea.append(proj.toString());

					errorLabel.setText("Activity added");
					clearInputs();
				}
			}

			/*
			 * else if (source == addActButton && (actField.getText().length() == 0 ||
			 * durField.getText().length() == 0)) {
			 * errorLabel.setText("Please enter required fields"); clearInputs(); }
			 */

			if (source == clearAllButton) // removes all activities in projectList and resets the appropriate textfields
			{
				projectList.removeAllElements();

				actArea.setText("No activities");
				pathArea.setText("No path to display");
				clearInputs();
				errorLabel.setText("All activities removed");
			}

			if (source == remActButton && actField.getText().length() > 0) // removes the user-specified activity
			{
				String projToBeRemoved = actField.getText();
				boolean removed = false;

				for (int i = 0; i < projectList.size(); i++) {
					if (projectList.get(i).getProjTitle().equals(projToBeRemoved)) {
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

			if (source == aboutButton) {
				clearInputs();
				pathArea.setText(About.output());

			}
			if (source == findPathButton) {
				clearInputs();
				if (projectList.size() == 0) {
					errorLabel.setText("Please enter activities to find path");
				} else {
					dGraph.setStart(0);
					for (Project proj : projectList) {
						for (String dependency : proj.getProjDependencies().split(",")) {
							if (dependency.equals("")) {
								dGraph.setStart(dGraph.getEdgeListSize());
							}
							dGraph.addEdge(dependency, proj.getProjTitle(), proj.getProjDuration());

						}
					}
					if (dGraph.hasCycle(dGraph.getStart(), 0, "")) {
						errorLabel.setText("Loop Detected");
						return;
					} else {
						dGraph.pathFinder(dGraph.getStart(), 0, "");
						pathArea.setText(dGraph.printPath());
					}
				}

			}

			// if there is no error, add a project to project list
			// otherwise, show an error message

		} // end of actionPerformed method

		private void clearInputs() {
			actField.setText("");
			durField.setText("");
			depField.setText("");
		}

		private void clearOutputs() {
			actArea.setText("");
			pathArea.setText("");
		}

		private void checkInputErrors(String activity, String duration, String dependency) {

			if (activity.length() == 0 && duration.length() == 0 && dependency.length() == 0) {
				eCode = 1;
				printErrors();
				return;
			}
			if (activity == "" || activity.length() == 0) {
				eCode = 2;
				printErrors();
				return;
			}
			if (duration == "" || duration.length() == 0) {
				eCode = 3;
				printErrors();
				return;
			}
			try {
				projDuration = Integer.parseInt(durField.getText());
			}

			catch (java.lang.NumberFormatException e1) {
				eCode = 4;
				printErrors();
				return;
			}
			if (projDuration < 0) {
				eCode = 5;
				printErrors();
				return;
			}

			for (int i = 0; i < projectList.size(); i++)// for loop to cycle through vector
			{
				if (projectList.get(i).getProjTitle().equals(activity))// searches for duplicate project numbers and
				{
					eCode = 6;
					printErrors();
					return;
				}
			}
			for (int i = 0; i < projectList.size(); i++) {
				if (projectList.get(i).getProjDependencies().equals(dependency)) {
					eCode = 7;
					printErrors();
					return;
				}
			}
		}

		private void printErrors() {
			hasError = true;
			switch (eCode) {
			case 1:
				errorLabel.setText("Please enter required fields");
				break;
			case 2:
				errorLabel.setText("Please enter and activity name.");
				break;
			case 3:
				errorLabel.setText("Please enter an activity duration.");
				break;
			case 4:
				errorLabel.setText("Duration must be an integer.");
				break;

			case 5:
				errorLabel.setText("Duration must be a positive integer.");
				break;
			case 6:
				errorLabel.setText("Duplicate activities not allowed");
				break;
			case 7:
				errorLabel.setText("This node must have a dependency, starting point already entered");
				break;
			}
			clearInputs();
		}
	} // end of ButtonListener class

}