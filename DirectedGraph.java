package brian_patch_2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

class DirectedGraph {

	private int nVertices;
	private int nEdges;
	private boolean hasHead;
	private int nonDependentV; // number of non-dependent vertices: ideally 1 if all verticies are connected
	private int startNode;

	private ArrayList<Edge> edgeList;
	private ArrayList<Path> pathList;
	private StringBuilder outString; // StringBuilder object to manage output

	public DirectedGraph() {

		nVertices = 0;
		nEdges = 0;
		hasHead = false;
		nonDependentV = 0;
		startNode = 0;
		edgeList = new ArrayList<Edge>();
		pathList = new ArrayList<Path>();

	}
	
	public int getNEdges() {
		return nEdges;
	}
	
	public int getNVertices() {
		return nVertices;
	}

	public void setStart(int n) {
		startNode = n;
	}

	public int getStart() {
		return this.startNode;
	}

	public int getEdgeListSize() {
		return this.edgeList.size();
	}

	public void addEdge(String head, String tail, int dur) {
		Edge edge = new Edge();
		edge.setEdge(head, tail, dur);
		edgeList.add(edge);
		nEdges++;
		if (head == "") {this.nonDependentV ++; hasHead = true;} // If a vertex has do dependency, it increments the non-dependent vertex count
	}
	
	public int getNonDependentV() {
		return nonDependentV;
	}

	public void setNonDependentV(int nonDependentV) {
		this.nonDependentV = nonDependentV;
	}

	public boolean checkHasHead() {
		return hasHead;
	}

	public String printPath() {
		outString = new StringBuilder();
		Collections.sort(pathList, Path.sortDur);
		for (Path p : pathList) {

			outString.append(" Path:  ");
			outString.append(p.getPathNodes());
			outString.append(" Duration:  ");
			outString.append(p.getPathDuration() + "\n");

		}
		return outString.toString();
	}
	
	public boolean hasCycle(int cur, int sum, String tmpPath) {
		if(Arrays.asList(tmpPath.split(",")).contains(edgeList.get(cur).getEdgeTail())) {
			return true;
		}else {
		return false;
		}
	}

	public void pathFinder(int cur, int sum, String tmpPath) {
		int flag = 0;
		int tmp;
		//if (Arrays.asList(tmpPath.split(",")).contains(edgeList.get(cur).getEdgeTail())) {
			// errorLabel.setText("Loop Detected");
			//return;
		//}
		tmpPath = tmpPath + edgeList.get(cur).getEdgeTail();
		tmpPath = tmpPath + " > ";
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



}