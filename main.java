import javax.swing.*;
import java.util.*;

public class main extends JApplet
 {
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