package model;

import java.util.ArrayList;

public class SubGraph extends Graph{

	//label is the string that describes what this graph stands for, and will replace in the final solution.
	private String label = new String();
	// start_loop isnt needed because it'll always be the start node of the subgraph.
	// end_loop is the node right after the end of the loop. in the case of a forloop, its where you end up if the for-loop never executes.
	private String end_loop   = new String();
	// this arraylist consists of the edges that were removed from the graph.. the ones that connect the start point/start_loop and the end_loop.
	ArrayList<Pair> removedbegs = new ArrayList<Pair>();
	ArrayList<Pair> removedends = new ArrayList<Pair>();


	public SubGraph(String label){
		this.label = label;
	}


	/**
	 * this method removes the nodes connecting the start_loop(aka start) and the end_loop node(NOT THE FINISH NODE) then solves the graph like normal.
	 * this allows this normal solve method to check for further inner loops, and then solve them.
	 */
	public void solveSubgraph(Integer iterations){
		System.out.println("IN SOLVE SUBGRAPH");
		// TODO remove loop connectors!!!!!!
		//<<remove the nodes that connect the start node, and the end_loop node. (end loop is a construct that only exists within a subgraph).
		ArrayList<String> remark = new ArrayList<String>();
		
		for(int i = 0; i < this.getBegin().size(); i++){
			if(this.getBegin().get(i).getY().equalsIgnoreCase(this.getEnd_loop())){
				System.out.println("yes to 1");
				if(this.getEnd().get(indfromX(this.getEnd(), this.getBegin().get(i).getX())).getY().equalsIgnoreCase(this.getStartPt())){
					System.out.println("yes to 2, now actually removing " + this.getBegin().get(i).getX());
					remark.add(this.getBegin().get(i).getX());
					removedbegs.add(this.getBegin().get(i));
					removedends.add(this.getEnd().get(this.indfromX(this.getEnd(), this.getBegin().get(i).getX())));
					this.getEnd().remove(this.indfromX(this.getEnd(), this.getBegin().get(i).getX()));
					this.getBegin().remove(i);
					i--;
				}
			}
		}
		
		// remove all the edges that were removed, remark is our array for later replacing them.
		this.getEdge().removeAll(remark);
		
		
		
		
		

		// this block removes edges that go between start_loop and end_loop

		System.out.println("we have finished removing edges.... here is the graph...");
		this.printMe();
		System.out.println("finished printing graph... calling createSubGraphs()")	;

		


		// at this point all edges connecting the begin_loop and end_loop nodes has been successfully removed.
		// now IF theres any loops remaining we consolidate them into a subgraph.
		this.createSubGraphs();
		// now the graphs have been removed, we REPLACE those edges that we removed, and solve the graph.

		this.solveAllSubgraphs(iterations);
		
		this.getEdge().addAll(remark);
		
		for(int i = 0; i < removedbegs.size(); i++){
			this.getBegin().add(removedbegs.get(i));
			this.getEnd().add(removedends.get(i));
		}
		
		System.out.println("We've finished readding the edges, here is the graph.");
		this.printMe();
		// now removed edges have been re-added but they are out of order in the arrayList, but it shouldn't matter.
		// then just solve like normal.
		// we call solvePathwLoop because this subgraph should now contain exactly ONE loop. the primary outer loop, and possibly an array of subgraphs
		System.out.println("Calling solvepathwloop");
		this.solvePathwLoop(iterations);
		System.out.println("finished with solvepathwloop...");
	}


	// this function does exactly what Graph.solvepath() does except it only increments until the point where the start node is encountered a second time in the path.
	// this should ONLY be called on a graph that contains ONE loop... ie a properly treated subgraph.
	// finds all paths up until the point where the startnode is encountered again.  Then uses this.subgraphs to replace the variables created.

	private void solvePathwLoop(Integer iterations){
		this.setPath(PathFinderwLoop.find_loop_path(this, iterations));
		if(this.getPath() == null){
			System.out.println("No path found, check your input");
		}
		else{
			System.out.println("pathfound in solvepathwloop");
		System.out.println("path found == " + this.getPath());
		}
		
		for(int i = 0; i < this.getSubs().size(); i++){
			this.setPath( this.getPath().replaceAll(this.getSubs().get(i).getLabel(), this.getSubs().get(i).getPath()) );
			}


	}








	public String getLabel() {
		return label;
	}








	public void setLabel(String label) {
		this.label = label;
	}








	public String getEnd_loop() {
		return end_loop;
	}








	public void setEnd_loop(String end_loop) {
		this.end_loop = end_loop;
	}
















}
