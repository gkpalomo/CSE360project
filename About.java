package brian_patch_2;

public abstract class About {
	
public static String output() {
	StringBuilder about = new StringBuilder();
	about.append("Activity Planner ");
	about.append("Gui version 2.2");
	about.append("\nVersion Date: 4 October 2017");
	return about.toString();
}

}
