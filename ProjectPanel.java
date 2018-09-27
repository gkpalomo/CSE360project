import java.awt.*; 
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;


import java.util.*;

public class ProjectPanel extends JPanel {
	
	private Vector <Project> projectList;//vector shared between the two panel classes 
	   private JButton button1,button2,button3,button4,button5;
	   private JLabel label1, label2, label3, label4;
	   private JTextField field1,field2,field3;
	   private JTextArea area1;
	   private JPanel panel1,panel2,panel3,panel4;
	   private JScrollPane scroll;
	   private JList projList;
	   
	   private String projName, projLocation;
	   private int projNum;
	   private boolean error1,error2,error3,error4;
	   
	   //constructor for project panel to be used in applet
	   public ProjectPanel(Vector <Project> projectList) {
		   
		   this.projectList = projectList;
		   area1 = new JTextArea("No Activities");
		    label1 = new JLabel("Activity Title");
		    label2 = new JLabel("Activity Duration");
		    label3 = new JLabel("Activity Dependencies");
		    label4 = new JLabel("");
		    field1 = new JTextField();
		    field2 = new JTextField();
		    field3 = new JTextField();
		    button1 = new JButton("Add activity");
		    button2 = new JButton("Remove activity");
		    button3 = new JButton("Find Paths!");
		    projList = new JList(projectList);
		    
		    panel1 = new JPanel();
		    panel1.setLayout(new GridLayout(4,2));
		    panel1.add(label1);
		    panel1.add(field1);
		    panel1.add(label2);
		    panel1.add(field2);
		    panel1.add(label3);
		    panel1.add(field3);
		    
		    panel2 = new JPanel();
		    panel2.setLayout(new FlowLayout());
		    panel2.add(button1);
		    panel2.add(button2);
		    panel2.add(button3);
		   
		    panel3 = new JPanel();
		    panel3.setLayout(new GridLayout(3,1));
		    panel3.add(panel2);
		    
		    panel4 = new JPanel(); //left side panel
		    panel4.setLayout(new GridLayout(3,1));
		    panel4.add(label4);
		    label4.setForeground(Color.RED);
		    panel4.add(panel1);
		    panel4.add(panel3); 
		    
		    
		    scroll = new JScrollPane(area1, 
				    ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);//adds the text area to a scroll pane
		      
		      setLayout(new GridLayout(1,2));
		      add(panel4);
		      add(scroll);
		    
		      button1.addActionListener(new ButtonListener());
		    
	   }

	   
	   
	   //ButtonListener is a listener class that listens to
	   //see if the buttont "Create a project" is pushed.
	   //When the event occurs, it add the project information
	   //in the text fields to the text area
	   //and the list of project information,
	   //and it also does error checking.
	   private class ButtonListener implements ActionListener
	    {
	     public void actionPerformed(ActionEvent event)
	      {
	     	
	     	//booleans for use with the error handling of adding a project
	 		error1=false;
	 		error2=false;
	 		error3=false;
	 		error4=false;
	 		Object source = event.getSource();
	 		
	 		
	 		
	 		if(source == button1 && field1.getText().length() > 0 && field2.getText().length()>0) //proj must have all fields filled 
	 		{
	 			
	 			try
	 			{
	 				projNum = Integer.parseInt(field2.getText());	//tries to parse the value as an int, sends error if not int
	 				projName = (field1.getText());
	 				projLocation = field3.getText();
	 							
	 			}
	 			catch (java.lang.NumberFormatException e1)
	 			{
	 				label4.setText("Please enter an integer for the activity duration");//error message to print
	 				error1 = true;
	 				field1.setText("");//resets the fields so loop doesnt get stuck 
	 				field2.setText("");
	 				field3.setText("");
	 				return;
	 			}
	 			
	 			
	 			for(int i=0;i<projectList.size();i++)//for loop to cycle through vector
	 			{
	 				
	 				
	 				if(projectList.get(i).getProjTitle().equals(projName))//searches for duplicate project numbers and duplicate project names
	 				{
	 					label4.setText("Duplicate activities not allowed");
	 					error4 = true;
	 					field1.setText("");//resets fields
	 					field2.setText("");
	 					field3.setText("");
	 				}
	 			}
	 			
	 		
	 				
	 		
	 			
	 			if(!(error1||error2||error3||error4))//if all criteria are met for it to be a new project, creates and adds to vector
	 			{
	 				
	 				Project proj = new Project();
	 				proj.setProjDependencies(projLocation);
	 				proj.setProjDuration(projNum);
	 				proj.setProjTitle(projName);
	 				
	 				projectList.add(proj);
	 				if(projectList.size() == 1)
	 				{
	 					area1.setText("");
	 				}
	 				area1.append(proj.toString());
	 				field1.setText("");
	 				field2.setText("");
	 				field3.setText("");
	 		 		label4.setText("Activity added.");
	 			}
	 			
	 			
	 		}
	 		
	 		else if(source == button1 && (field1.getText().length() == 0 || field2.getText().length() == 0))
	 		{
	 			label4.setText("Please enter required fields");
	 			field1.setText("");
	 			field2.setText("");
	 			field3.setText("");
	 		}
	 		
	 	
	 	

	 		
	 			
	 		
	          // if there is no error, add a project to project list
	          // otherwise, show an error message

	      } //end of actionPerformed method
	   } //end of ButtonListener class
	   

	
}
