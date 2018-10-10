//package master_101018a;

public abstract class HelpFile {
	
	public static String help() {
		String output;
		
		output = "This is the Activity Planning utility.\n"
				+ "This utility is intended to find the paths in a given project activity list.\n\n"
				+ "Entering actities:\n\n"
				+ "Activities are entered by Name, Duration, and any Dependencies.  Activity names can"
				+ " be named as the user sees fit and are entered in the \n\"Activity Title\" text field."
				+ "Activity Durations must be entered as positive integers only in the \"Activity Duratoin,\""
				+ " text field. Activity durations can be \nany user defined duration, but must all be the same period."
				+ "  If activities are in days, then all entries are assumed the same scale.  \n"
				+ "Dependencies are entered in the \"Activity Dependencies\" text field.  "
				+ "Because of internal checking, activity dependency names must match \nactivity names exactly."
				+ "  If there are multiple dependencies for a given activity, the dependency "
				+ "names must separated with a comma and no \nspace after the comma."
				+ "The \"Add Activity,\" button is left clicked to enter the activity once entry is complete.  "
				+ "The entered activity information will \nthen be displayed in the upper right text field.\n"
				+ "Example: Activity A with a duration of 4 days will display as:\n"
				+ "Activity Title:             A\n" 
				+ "Activity Duration:     4\n" 
				+ "Activity Dependencies:	\n\n"
				+ "Removing activities:\n\n"
				+ "Activities can be removed by entering the exact activity name in the \"Activity Title\" text field.  "
				+ "The \"Remove Activity,\" button must be left-clicked.  \nThe activity name "
				+ "must exactly match a previously entered activity, or an error will be displayed above the text entry fields in red."
				+ " \"Activity \nRemoved\" will be displayed above the text entry boxes in red and the activity will be"
				+ "  removed from the list at the right.\n\n"
				+ "Finding Paths: \n\n"
				+ "Once at least one activity with a correct duration has been entered into the activity list, the \"Find Paths!,\""
				+ " button can now be used.  This button \nwill find all activity paths and sort them by total duration.  \nExample: \n"
				+ "If activitiess A, B, and C, have been entered where A is the start, B depends on A, but C depends on A and B and is the final activity, "
				+ "both \npaths to C will be displayed in the text field bewith the coresponding durations:\n"
				+ "Path:  A,B,C, Duration:  9\n" 
				+ "Path:  A,C, Duration:  5 \n\n"
				+ "Clearing All Input:\n\n"
				+ "The \"Clear all,\" button will clear all input previously entered.  Use caution in using this button as all entries will be lost. "
				+ " A message will be \ndisplayed in red above the text entry boxes indicating this has been done and both output fields will be"
				+ " cleared.\n\n"
				+ "Problems:\n\n"
				+ "If data is entered incorrectly, the user will recieve an error message indicating the problem.  There are several"
				+ " types of errors which will be \nreported:\n\n"
				+ "Invalid input:  If the user does not input an actity name, or if the activity duration is not a positive integer, the user"
				+ "will recieve an error message \nabove the entry fields and the activity will not be added.\n\n"
				+ "No starting point:  The project must contain an activity with no dependency.  This will be considered the project starting"
				+ " point.\n\n"
				+ "Multiple staring points: If one activity is entered without a dependancy, it is considered the starting point."
				+ "  If the user enters another activity \nwithout a dependancy, the activity will not be added to the list."
				+ "  An error message will be displayed above in red.\n\n"
				+ "Loops:  Loops are cycles where an activity dependency is a previous activity, but that previous activity also depends"
				+ "on a downstream activty.\nThis creates a loop and the activty list must be re-checked.  The problem activty(s) must"
				+ " be removed and re-added appropriately.";
		
		
		
		return output;
	}

}
