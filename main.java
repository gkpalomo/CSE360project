//package master_101018a;

import javax.swing.*;

//import java.awt.Container;
import java.util.*;

//@SuppressWarnings("deprecation")
public class main extends JApplet
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int APPLET_WIDTH = 800, APPLET_HEIGHT = 600;
    private ProjectPanel projPanel;
    private Vector <Project> projectList;

    //The method init initializes the Applet
    public void init()
    {
    	projectList = new Vector<Project>();
        projPanel = new ProjectPanel(projectList);
        getContentPane().add(projPanel);
        setSize (APPLET_WIDTH, APPLET_HEIGHT); //set Applet size
    }
    
   
}  