import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ProjectPanel extends JPanel {

    private Vector <Project> projectList;//vector shared between the two panel classes
    private JButton button1,button2,button3,button4,button5;
    private JLabel label1, label2, label3, label4;
    private JTextField field1,field2,field3;
    private JTextArea area1,area2;
    private JPanel panel1,panel2,panel3,panel4,panel5,panel6,panel7;
    private JScrollPane scroll,scroll2,scroll3;
    private JList projList,list1,list2;
    private Project selectedProj1,selectedProj2;

    private String projName, projLocation;
    private int projNum;
    private boolean error1,error2,error3,error4;

    //constructor for project panel to be used in applet
    public ProjectPanel(Vector <Project> projectList) {

        this.projectList = projectList;
        area1 = new JTextArea("No Activities");
        area2 = new JTextArea("No paths to display");
        label1 = new JLabel("Activity Title");
        label2 = new JLabel("Activity Duration");
        label3 = new JLabel("Activity Dependencies");
        label4 = new JLabel("");
        field1 = new JTextField();
        field2 = new JTextField();
        field3 = new JTextField();
        button1 = new JButton("Add activity");
        button2 = new JButton("Clear all");
        button3 = new JButton("Help");
        button4 = new JButton("Remove activity");
        button5 = new JButton("Find paths!");
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
        panel2.add(button4);
        panel2.add(button5);

        panel6 = new JPanel();
        panel6.setLayout(new FlowLayout());
        panel6.add(button2);
        panel6.add(button3);

        panel3 = new JPanel();
        panel3.setLayout(new GridLayout(3,1));
        panel3.add(panel2);
        panel3.add(panel6);

        panel4 = new JPanel(); //left side panel
        panel4.setLayout(new GridLayout(3,1));
        panel4.add(label4);
        label4.setForeground(Color.RED);
        panel4.add(panel1);
        panel4.add(panel3);


        scroll = new JScrollPane(area1,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);//adds the text area to a scroll pane


        scroll2 = new JScrollPane(area2,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);


        panel5 = new JPanel();
        panel5.setLayout(new GridLayout(1,2));
        panel5.add(panel4);
        panel5.add(scroll);



        setLayout(new GridLayout(2,1));
        add(panel5);
        add(scroll2);


        button1.addActionListener(new ButtonListener());
        button2.addActionListener(new ButtonListener());
        button4.addActionListener(new ButtonListener());
        button5.addActionListener(new FindPath());

    }



    //ButtonListener is a listener class that listens to
    //see if the button "Create a project" is pushed.
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
                    label4.setText("Activity added");
                }
            }

            else if(source == button1 && (field1.getText().length() == 0 || field2.getText().length() == 0))
            {
                label4.setText("Please enter required fields");
                field1.setText("");
                field2.setText("");
                field3.setText("");
            }

            if(source == button2) // removes all activities in projectList and resets the appropriate textfields
            {
                projectList.removeAllElements();

                area1.setText("No activities");
                area2.setText("No paths to display");
                field1.setText("");
                field2.setText("");
                field3.setText("");
                label4.setText("All activities removed");
            }

            if(source == button4 && field1.getText().length() > 0) // removes the user-specified activity
            {
                String projToBeRemoved = field1.getText();
                boolean removed = false;

                for(int i = 0; i < projectList.size(); i++)
                {
                    if(projectList.get(i).getProjTitle().equals(projToBeRemoved))
                    {
                        projectList.remove(i);
                        removed = true;
                    }
                }

                if(removed)
                {
                    label4.setText("The activity was removed");
                    field1.setText("");
                    field2.setText("");
                    field3.setText("");

                    area1.setText("");
                    area2.setText("");

                    for(int i = 0; i < projectList.size(); i++)
                    {
                        area1.append(projectList.get(i).toString()); // reprints the current activities after removal
                    }
                }
                else
                {
                    label4.setText("The activity was not found");
                    field1.setText("");
                    field2.setText("");
                    field3.setText("");
                }
            }

            else if(source == button4 && field1.getText().length() == 0)
            {
                label4.setText("Type the title of the activity to be removed");
                field1.setText("");
                field2.setText("");
                field3.setText("");
            }

            // if there is no error, add a project to project list
            // otherwise, show an error message

        } //end of actionPerformed method
    } //end of ButtonListener class
    
    
    private class FindPath implements ActionListener
    {
    	private ArrayList <Edge> edgeList = new ArrayList <Edge>();
    	private ArrayList <Path> pathList = new ArrayList <Path>();
    	
        public void actionPerformed(ActionEvent event)
        {
        	Object source = event.getSource();
        	if(source == button5) 
        	{
        		if(projectList.size()==0)
        		{
        			label4.setText("Please enter activities to find path");
        		}
        		else 
        		{
                	int startNode = 0;
        			for (Project proj:projectList) 
        			{
        				for(String dependency:proj.getProjDependencies().split(",")) 
        				{
        					if(dependency.equals("S")) 
        					{
        						startNode = edgeList.size();
        					}
        					Edge edge = new Edge();
        					edge.setEdge(dependency, proj.getProjTitle(), proj.getProjDuration());
        					edgeList.add(edge);
        					
        				}
        			}
        			
        			pathFinder(startNode,0,"");
        			
        			Collections.sort(pathList, Path.sortDur);
        			
        			//print all paths sorted by duration
        			for (Path p:pathList) 
        			{
        				System.out.println(p.getPathDuration());
        				System.out.println(p.getPathNodes());
        			}
        			
        		}
        	}
        }
        
       
        public void pathFinder(int cur,int sum, String tmpPath)
        {
        	int flag = 0;
        	int tmp;
        	if(Arrays.asList(tmpPath.split(",")).contains(edgeList.get(cur).getEdgeTail()))
        	{
        		label4.setText("Loop Detected");
        		return;
        	}
        	tmpPath = tmpPath + edgeList.get(cur).getEdgeTail();
        	tmpPath = tmpPath + ",";
        	for (int i =0; i<edgeList.size();i++) 
        	{
        		if (edgeList.get(i).getEdgeHead().equals(edgeList.get(cur).getEdgeTail()))
        		{
        			sum+=edgeList.get(cur).getTailDuration();
        			tmp=cur;
        			cur=i;
        			flag=1;

        			pathFinder(cur,sum,tmpPath);
        			cur =tmp;
        			sum-=edgeList.get(cur).getTailDuration();
        		}
        	}
        	
        	if(flag==0) 
        	{
        		sum+=edgeList.get(cur).getTailDuration();
				Path path = new Path();
				path.setPathNodes(tmpPath);
				path.setPathDuration(sum);
				pathList.add(path);
        		sum-=edgeList.get(cur).getTailDuration();
        	}
			tmpPath = String.join(",", Arrays.copyOfRange(tmpPath.split(","),0,tmpPath.split(",").length - 1));

        }
        


        
    
    }
    
    

}