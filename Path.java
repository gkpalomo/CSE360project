package Elin620patch1;

import java.util.Comparator;

public class Path {

	private String pathNodes;
	private int pathDuration;

	// Constructor
	public Path() {
		pathNodes = "";
		pathDuration = 0;
	}

	public String getPathNodes() {
		return pathNodes;
	}

	public int getPathDuration() {
		return pathDuration;
	}

	public void setPathNodes(String str) {
		pathNodes = str;
	}

	public void setPathDuration(int num) {
		pathDuration = num;
	}

	public static Comparator<Path> sortDur = new Comparator<Path>() {

		public int compare(Path p1, Path p2) {

			int dur1 = p1.getPathDuration();
			int dur2 = p2.getPathDuration();

			/* For descending order */
			return dur2 - dur1;
			/*
			 * For ascending order return dur1 - dur2;
			 */
		}
	};
}