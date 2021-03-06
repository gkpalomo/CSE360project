package patch_v3_6a;

import java.util.Vector;

//import javax.swing.JApplet;
import javax.swing.JFrame;

public class PathFinder {

	private static ProjectPanel projPanel;
	private static Vector<Project> projectList;

	public static void main(String[] arg) {
		JFrame frame = new JFrame();
		projectList = new Vector<Project>();
        projPanel = new ProjectPanel(projectList);
		frame.getContentPane().add(projPanel);
		frame.pack();
		frame.setVisible(true);
		frame.setSize(800, 700);
		frame.setJMenuBar(projPanel.menuBar);
		frame.setTitle("Activity Planner v3.6");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
