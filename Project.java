
public class Project {
	private String projTitle;
	private int projDuration;
	private String projDependencies;
	private String[] dependencies; //for later use with parser and error check
	
	// Constructor to initialize all member variables
	public Project() {
		projTitle = "?";
		projDuration = 0;
		projDependencies = "?";
		dependencies = null;
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
		String result = "\nActivity Title:\t\t" + projTitle + ",\nActivity Duration:\t" + projDuration
				+ ",\nActivity Dependencies:\t" + projDependencies + "\n\n";
		return result;
	}
}
