package patch_v3_6a;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

public class GetPathInfo {

	private Vector<Project> projectList;
	private ArrayList<Edge> edgeList;
	private ArrayList<Path> pathList;
	// private StringBuilder outString; // String builder object to manage output
	private boolean dependencyFlag;
	private boolean loopFlag;

	GetPathInfo(Vector<Project> inputList) {
		projectList = inputList;
		sortList(this.projectList);
		edgeList = new ArrayList<Edge>();
		pathList = new ArrayList<Path>();
		dependencyFlag = true;
		loopFlag = false;
		buildList();

	}

	private void sortList(Vector<Project> pList) {
		int n = pList.size();
		for (int i = 1; i < n; ++i) {
			String key = pList.get(i).getProjTitle();
			int keyDur = pList.get(i).getProjDuration();
			String keyDep = pList.get(i).getProjDependencies();
			int j = i - 1;

			/*
			 * Move elements of arr[0..i-1], that are greater than key, to one position
			 * ahead of their current position
			 */
			while (j >= 0 && pList.get(j).getProjTitle().compareToIgnoreCase(key) > 0) {
				pList.get(j + 1).setProjTitle(pList.get(j).getProjTitle());
				pList.get(j + 1).setProjDuration(pList.get(j).getProjDuration());
				pList.get(j + 1).setProjDependencies(pList.get(j).getProjDependencies());
				j = j - 1;
			}
			pList.get(j + 1).setProjTitle(key);
			pList.get(j + 1).setProjDuration(keyDur);
			pList.get(j + 1).setProjDependencies(keyDep);
		}
	}

	public boolean checkLoop() {
		if (loopFlag) {
			return true;
		} else {
			return false;
		}

	}

	public boolean checkDependency() {
		if (!dependencyFlag) {
			return true;
		} else {
			return false;
		}
	}

	private void buildList() {
		int startNode = 0;
		for (Project proj : projectList) {
			for (String dependency : proj.getProjDependencies().split(",")) {
				dependencyFlag = false;
				if (dependency.equals("")) {
					startNode = edgeList.size();
					dependencyFlag = true;
				}
				for (int j = 0; j < projectList.size(); j++) {
					if (dependency.equals(projectList.get(j).getProjTitle())) {
						dependencyFlag = true;
						for (String newDependency : projectList.get(j).getProjDependencies().split(",")) {
							if (newDependency.equals(proj.getProjTitle()))
								loopFlag = true;
						}
					}
				}
				if (!dependencyFlag) {
					// errorLabel.setText("Non-existent dependency detected");
					return;
				}
				if (loopFlag) {
					// errorLabel.setText("Loop detected");
					return;
				}

				Edge edge = new Edge();
				edge.setEdge(dependency, proj.getProjTitle(), proj.getProjDuration());
				edgeList.add(edge);

			}
		}

		pathFinder(startNode, 0, "");
		Collections.sort(pathList, Path.sortDur);
	}

	private void pathFinder(int cur, int sum, String tmpPath) {
		int flag = 0;
		int tmp;
		if (Arrays.asList(tmpPath.split(",")).contains(edgeList.get(cur).getEdgeTail())) {
			// errorLabel.setText("Loop Detected");
			return;
		}
		tmpPath = tmpPath + edgeList.get(cur).getEdgeTail();
		tmpPath = tmpPath + ",";
		for (int i = 0; i < edgeList.size(); i++) {
			if (edgeList.get(i).getEdgeHead().equals(edgeList.get(cur).getEdgeTail())) {
				sum += edgeList.get(cur).getTailDuration();
				tmp = cur;
				cur = i;
				flag = 1;

				pathFinder(cur, sum, tmpPath);
				cur = tmp;
				sum -= edgeList.get(cur).getTailDuration();
			}
		}

		if (flag == 0) {
			sum += edgeList.get(cur).getTailDuration();
			Path path = new Path();
			path.setPathNodes(tmpPath);
			path.setPathDuration(sum);
			pathList.add(path);
			sum -= edgeList.get(cur).getTailDuration();
		}
		tmpPath = String.join(",", Arrays.copyOfRange(tmpPath.split(","), 0, tmpPath.split(",").length - 1));
	}

	public String getCritical() {

		Path tempP = new Path();
		ArrayList<Path> toRemove = new ArrayList<>();

		for (Path p : pathList) {
			if (p.getPathDuration() >= tempP.getPathDuration())
				tempP.setPathDuration(p.getPathDuration());
			else
				toRemove.add(p);
		}

		pathList.removeAll(toRemove);
		StringBuilder outString;
		outString = new StringBuilder();

		//outString.append("Critical Path:\n");

		for (Path p : pathList) {
			// add everything up together through each iteration...
			outString.append("Path:  ");
			outString.append(p.getPathNodes());
			outString.append(" Duration:  ");
			outString.append(p.getPathDuration() + "\n");

		}

		return outString.toString();
	}

	public void writeFile(String fileName) throws IOException {

		FileWriter fw = new FileWriter(fileName);
		PrintWriter pw = new PrintWriter(fw);
		
		pw.println("Activity Planner ==> " + fileName);
		
		pw.println("");

		pw.println(getDate());
		pw.println(getTime());
		
		pw.println("");
		pw.println("");
		
		pw.println("Activities:");
		pw.println("");

		for (int i = 0; i < this.projectList.size(); i++) {

			// output.append(this.projectList.get(i).getProjTitle() + ", ");
			pw.println(this.projectList.get(i).getActnDur());
		}
		pw.println("");
		pw.println("");
		
		pw.println("Paths: ");
		pw.println("");
		for (Path p : pathList) {
			// add everything up together through each iteration...
			pw.println("Path:  " + p.getPathNodes() + " Duration: " + p.getPathDuration());

		}
		pw.println("");
		pw.println("");
		pw.println("Critical Path: ");
		pw.println("");
		pw.println(getCritical());
		

		pw.close();
		fw.close();

	}

	@Override
	public String toString() {

		StringBuilder outString;
		outString = new StringBuilder();

		outString.append("Paths:\n");

		for (Path p : pathList) {
			// add everything up together through each iteration...
			outString.append("Path:  ");
			outString.append(p.getPathNodes());
			outString.append(" Duration:  ");
			outString.append(p.getPathDuration() + "\n");

		}

		return outString.toString();
	}

	public String getTime() {
		DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalDateTime currentTime = LocalDateTime.now();

		return "Time: " + time.format(currentTime);

	}

	public String getDate() {
		DateTimeFormatter date = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDateTime currentDate = LocalDateTime.now();

		return "Date: " + date.format(currentDate);

	}

}
