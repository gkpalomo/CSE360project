//package master_101018a;

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
		frame.setSize(800, 600);
		frame.setTitle("Path Finder");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
