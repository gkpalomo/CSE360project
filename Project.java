package brian_patch_2;

//import java.lang.reflect.Array;
//import java.util.*;  
public class Project {
    private String projTitle;
    private int projDuration;
    private String projDependencies;
    //private String[] dependencies; //for later use with parser and error check

    // Constructor to initialize all member variables
    public Project() {
        projTitle = "?";
        projDuration = 0;
        projDependencies = "?";
        //dependencies = null;
    }

    // Accessor methods
    public String getProjTitle() {
        return projTitle;
    }

    public int getProjDuration() {
        return projDuration;
    }

    public String getProjDependencies() {
        return projDependencies;
    }

    // Mutator methods
    public void setProjTitle(String name) {
        projTitle = name;
    }

    public void setProjDuration(int num) {
        projDuration = num;
    }

    public void setProjDependencies(String dependencies) {
        projDependencies = dependencies;
    }

    // toString() method returns a string containing its title, number, and location
    public String toString() {
    	StringBuilder output = new StringBuilder();
        output.append("Activity Title:\t\t");
        output.append(projTitle);
        output.append("\nActivity Duration:\t");
        output.append(projDuration);
        output.append("\nActivity Dependencies:\t");
        output.append(projDependencies);
        output.append("\n\n");
        return output.toString();
    }
    
    
    //get, set, parse dependencies
    /*public String getDependency(int index) {
    	return (String)Array.get(this.dependencies, index);
    }
    
    public int lengthDependencies() {
    	return this.dependencies.length;
    }
    
    public void parseDependencies(String projDependencies){
    	this.dependencies = projDependencies.split(",");
    	System.out.println(Arrays.toString(this.dependencies));
    }
    */
}