package pck;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Graph class which contains definition of a control flow graph and methods for reading-in a comma separated
 * control flow graph from a file
 * @author Cory M. Lee
 *
 */
public class Graph {


	private ArrayList<String> Nodes = new ArrayList<String>();
	private ArrayList<String> Edge = new ArrayList<String>();
	private ArrayList<Pair> begin = new ArrayList<Pair>();
	private ArrayList<Pair> end = new ArrayList<Pair>();
	private Integer numVisits = new Integer(0);
	String StartPt, endPt = new String();
	private ArrayList<SubGraph> subs = new ArrayList<SubGraph>();
	private String path = new String();

	public ArrayList<SubGraph> getSubs() {
		return subs;
	}

	public void setSubs(ArrayList<SubGraph> subs) {
		this.subs = subs;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getNumVisits() {
		return numVisits;
	}

	public void setNumVisits(Integer numVisits) {
		this.numVisits = numVisits;
	}


	/**
	 * blank constructor for graph class, just creates a blank graph.
	 */
	public Graph(){

		Nodes = new ArrayList<String>();
		Edge = new ArrayList<String>();
		begin = new ArrayList<Pair>();
		end = new ArrayList<Pair>();
		numVisits = new Integer(0);
		StartPt= endPt = new String();

	}

	/**
	 * Consructor for graph class.
	 * @param Nodes - ArrayList of Strings, these are the names of the nodes.
	 * @param Edge  - ArrayList of Edges, these are names of the Edges.
	 * @param begin - ArrayList of Pairs, these are the tuples of type Edge, Node. Pairs an edge with the node it begins on.
	 * @param end   - ArrayList of Pairs, these are the tuples of type Edge, Node. Pairs an edge with the node it ends on.
	 * @param numVisits - Number of steps(in edges) you want to complete pathing through this graph.
	 * @param StartPt   - the node where you want your path to start from. Must be contained in the ArrayList of Nodes or an exception will be thrown.
	 * @param endPt     - the node where you want your path to end. Must be contained in the ArrayList of Nodes or an exception will be thrown.
	 * @throws Exception - Exception is thrown when StartPt or Endpt are not contained within the ArrayList of Nodes.
	 */
	public Graph(ArrayList<String> Nodes, ArrayList<String> Edge, ArrayList<Pair> begin, ArrayList<Pair> end, Integer numVisits, String StartPt, String endPt) throws Exception{
		assert Nodes != null;
		assert Edge != null;
		assert numVisits > 0;
		assert numVisits <= 100;
		assert begin.size() == Nodes.size();
		assert end.size() ==   Nodes.size();

		if(! Nodes.contains(StartPt)){
			throw new Exception("Start Node is not in the set of nodes");
		}

		if(! Nodes.contains(endPt)){
			throw new Exception("End node is not in the set of nodes");
		}

		this.Nodes= Nodes;
		this.Edge = Edge;
		this.StartPt = StartPt;
		this.endPt = endPt;
		this.numVisits = numVisits;
		this.begin = begin;
		this.end   = end;
	}




	/**
	 * reads an input file and sets up the graph in question. format is as follows:
	 * Line# line-text. no trailing commas, no spaces.
	 * 
	 * 0 Node1,Node2,Node3,Node4  // comma separated list of node names.
	 * 1 Edge1,Edge2,Edge3        // comma separated list of edge names
	 * 2 Node1                    // name of start node.
	 * 3 Node4                    // name of end node.
	 * 4 3                        // number of steps you want to complete the path in.... in terms of edges.
	 * 5 Edge1,Node1/Edge2,Node2                         // begin tupples
	 * 6 Edge1,Node1/Edge2,Node2
	 * 
	 * 
	 * 
	 * @param inputFileName
	 */
	public void readFile(String inputFileName){
		try {
			Scanner scan = new Scanner(new File(inputFileName));
			// collect node names.
			String temp = scan.next();
			String[] nodeinp = temp.split(",");
			for(Integer i = 0; i < nodeinp.length; i++){
				Nodes.add(nodeinp[i]);
			}





			// collect edges
			temp = scan.next();
			String[] edgeinp = temp.split(",");
			for(Integer i = 0; i < edgeinp.length; i++){
				Edge.add(edgeinp[i]);
			}





			// collect start point.
			temp = scan.next();
			StartPt = temp;

			// collect end point.
			temp = scan.next();
			endPt = temp;

			// collect number of visits.
			temp = scan.next();
			numVisits = Integer.valueOf(temp);

			temp = scan.next();
			String[] compHolder = temp.split("/");

			for(Integer i = 0; i < compHolder.length; i++){
				//temp is now just a tuple of format "Edge1,Node1
				temp = compHolder[i];
				begin.add(this.CreatePair(temp.split(",")[0], temp.split(",")[1]) );
			}

			temp = scan.next();
			compHolder = temp.split("/");
			for(Integer i = 0; i < compHolder.length; i++){
				//temp is now just a tuple of format "Edge1,Node1
				temp = compHolder[i];
				end.add(this.CreatePair(temp.split(",")[0], temp.split(",")[1]) );
			}








		} catch (FileNotFoundException e) {
			System.out.println( e.getMessage() );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}




	}

	/**
	 * creates a pair for use as either a begin or end tuple.
	 * @param x - string. should refer to an existing edge.
	 * @param y - string. should refer to an existing node.
	 * @return -  returns pair composed of x and y.
	 * @throws Exception - throws an exception if X is not in the set of edges, or Y is not in the set of nodes.
	 */
	private Pair CreatePair(String x, String y) throws Exception{
		if(!Edge.contains(x)){
			throw new Exception("tried to connect an edge" +  x + " that doesn't exist");
		}
		if(!Nodes.contains(y)){
			throw new Exception("tryed to add a valid edge that connects to a node " +  y + " that doesn't exist.");
		}
		return new Pair(x,y);
	}














	/**
	 * returns a java.util.ArrayList of pairs in the format of (Edge,Node) such that Edge is an edge in the universe, and Node is the node that edge ends on.
	 * @return - ArrayList of pairs of format (Edge,Node) such that edge is an edge in the universe, and node is a node on which that edge ends upon.
	 */
	public ArrayList<Pair> getBegin() {
		return begin;
	}



	/**
	 * Sets the begin of all edges in the universe.
	 * @param begin - Accepts Pairs of type (Edge,Node) where an edge is the edge in question and the node is the node that edge begins on.
	 */
	public void setBegin(ArrayList<Pair> begin) {
		this.begin = begin;
	}



	/**
	 * returns a listing of all edge end points in the universe.
	 * @return - returns a pair in the format of (Edge,Node) where the edge is an edge that exists in the universe, and a node is the node where that edge ends.
	 */
	public ArrayList<Pair> getEnd() {
		return end;
	}



	/**
	 * Sets the end ofall edges in the universe.
	 * @param end - Accepts Pairs of type (Edge,Node) where an edge is the edge in question and the node is the node taht edge ends on.
	 */
	public void setEnd(ArrayList<Pair> end) {
		this.end = end;
	}



	public Integer getNumNodes(){
		return Nodes.size();
	}


	/**
	 * returns the nodes.
	 * @return returns ArrayList of node names in graph.
	 */
	public ArrayList<String> getNodes() {
		return Nodes;
	}

	/**
	 * takes in an ArrayList, and sets node content of the current graph to the content of that ArrayList.
	 * @param nodes
	 */
	public void setNodes(ArrayList<String> nodes) {
		Nodes = nodes;
	}

	/**
	 * Returns a list of all edges that currently exist in the universe.
	 * @return - returns java.util.ArrayList<String> containing all edges in the universe.
	 */
	public ArrayList<String> getEdge() {
		return Edge;
	}

	/**
	 * Sets names of all edges that exist in the universe.
	 * @param edge - takes java.util.ArrayList<String> which contains all edges that will later exist in the universe.
	 */
	public void setEdge(ArrayList<String> edge) {
		Edge = edge;
	}


	/**
	 * returns number of edges you want in your traversed path.
	 * @return - Integer.
	 */
	public Integer getnumVisits() {
		return numVisits;
	}

	/**
	 * sets the current number of steps you want to complete the path in. This is counted in terms of edges.
	 * @param numVisits - number of edges you want to traverse in your finished path.
	 */
	public void setnumVisits(Integer numVisits) {
		this.numVisits = numVisits;
	}

	/**
	 * retrieves current start of path
	 * @return  - String name of node.
	 */
	public String getStartPt() {
		return StartPt;
	}

	/**
	 * sets name of start node of path. must exist within the set of nodes.
	 * @param startPt - String name of start node of path, must exist within set of nodes.
	 */
	public void setStartPt(String startPt) {
		StartPt = startPt;
	}

	/**
	 * retrieves string name of endpoint
	 * @return - String, name of current end of path node.
	 */
	public String getEndPt() {
		return endPt;
	}




	/**
	 * sets internal endpoint to the endpoint specified, end point being the node at the end of the path.
	 * @param endPt - String name of node to be set. this must exist within the set of nodes or you'll run into problems later.
	 */
	public void setEndPt(String endPt) {
		this.endPt = endPt;
	}

	/**
	 * Outputs the content of this graph to console.
	 */
	public void printMe(){
		System.out.println("==================================================");
		System.out.println("Current Graph Contains:");
		System.out.println("");
		System.out.println("Nodes: ");
		System.out.println(Nodes.toString());
		System.out.println("");
		System.out.println("Edges: ");
		System.out.println(Edge.toString());

		System.out.println("");

		System.out.println("StartNode: " + StartPt.toString());
		System.out.println("");
		System.out.println("EndNode: " + endPt.toString());
		System.out.println("");

		System.out.println("Begin Pairs :" + begin.size());
		for(int i = 0; i < begin.size(); i++){
			System.out.println("( " + begin.get(i).getX() + ", " + begin.get(i).getY() + " )");
		}
		System.out.println("");
		System.out.println("End Pairs : ");
		for(int i = 0; i < end.size(); i++){
			System.out.println("( " + end.get(i).getX() + ", " + end.get(i).getY() + " )");
		}

		System.out.println();
		System.out.println("Num Visits == " + numVisits);

		System.out.println("");
		System.out.println("Proper Edges: ");
		for(int i = 0; i < begin.size(); i++){
			System.out.println("( " + begin.get(i).getY() + " , " + end.get(i).getY() + " )");
		}

		System.out.println();
		System.out.println("==================================================");

	}

	/**
	 * This private method solves all the subgraphs in this graph, by calling solveSubGraph on all of them.
	 */
	protected void solveAllSubgraphs(Integer iterations){
		System.out.println("SolveAllSubgraphs();");
		if(subs.size() == 0){
			System.out.println("zero subgraphs here, so we're returning");
			return;
		}
		for(int i = 0; i < subs.size(); i++){
			System.out.println("Attempting to solve subgraph " + i);
			subs.get(i).solveSubgraph(iterations);
		}
	}

	/**
	 * this method solves the current graph, and sets it's path string.
	 */
	public void solveGraph(Integer iterations){
		// TODO if loops exist....
		// TODO remove loops from graph.... and create subgraphs. then solve subgraphs. <-- magic function.

		System.out.println("solvegraph called");
		
		
		this.createSubGraphs();


System.out.println("CALLING DR. SOLVER");
		this.solveAllSubgraphs(iterations);
		// TODO then solve this graph <using normal methods in the pathfinder.. simply solve graph, and convert solutions into the pathstring>
		// TODO then replace subgraph labels in path string with subgraph solutions. <this one should be pretty easy>
		// finally set path string in THIS graph to final solution.

		this.solvePath();


	}

	protected void solvePath(){
		//TODO this runs the path solver on the current graph, does not change anything. simply runs it, interprets the output, and sets the path string.
		//uses path string from the subgraphs to generate the pathstring, so make sure you create and solve them first. 
		// this is intended to only be run on graphs that DO NOT CONTAIN LOOPS. Otherwise, it will take a long time to complete.
		this.setPath(PathFinder.find_path(this));
		for(int i = 0; i < subs.size(); i++){
		this.setPath( this.getPath().replaceAll(subs.get(i).getLabel(), subs.get(i).getPath()) );
		}
		
		
		
		if(this.getPath() == null){
			System.out.println("No path found, check your input");
		}
		else{
		System.out.println("path found == " + this.getPath());
		}
	}








	/**
	 * this private method finds all loops contained within the current graph, and eliminates them, generating a set of subgraphs now contained within this graph.
	 * These subgraphs can then be solved by the solveAllSubGraphs() method.
	 */
	protected void createSubGraphs() {
		// TODO this function removes all the loops from a graph and replaces them with singular nodes. named LOOP[INDEX] eg LOOP1
		// TODO all edges that point to the start node now point to the LOOP node.
		// TODO all edges that begin on the node AFTER the end node now begin with the LOOP node.
		System.out.println("in create subgraphs");
		boolean flag = false;
		Integer LoopCounter = 0;
		String tempx = new String();
		String nodeAfter = new String();
		String inp = LoopFinder.find_loops(this);
		String endloop = new String();
		
		
		
		//since these are saved locally it's okay to re-use the indexes for each graph and it's respective subgraphs.
		
		// if this statement evaluates to true we have a graph that starts on the start loop node, an edge must be added in order to find correct results.
		if(inp.contains("start_loop=[]") && inp.contains("end_loop=[]") && !inp.contains("loop_set=[]")){
			// add an edge and a node to the graph that comes before the start node (the graph found by this logic starts on the start-node
			flag = true;
			System.out.println("found an ugly graph.");
			this.getNodes().add("NodeX");
			this.getEdge().add("EdgeX");
			this.getBegin().add(new Pair("EdgeX" , "NodeX"));
			this.getEnd().add(new Pair("EdgeX", this.getStartPt()));
			System.out.println("GRAPH HAS BEEN MODIFIED // THIS IS THE RESULT --- ");
			this.printMe();
			
			
		}
		
		
		
		
		
		else if(inp.contains("start_loop=[]") && inp.contains("end_loop=[]") && inp.contains("loop_set=[]")){
			// we're done here, no loops are detected.
			System.out.println("no more loops detected, folding up.");
			return;
		}
		// now all graphs should give correct output if we find loops.
		// now refresh the string.
		System.out.println("All graphs have been normalized at this point... and we're going to search for loops.");
		inp = LoopFinder.find_loops(this);
		System.out.println("resultant string from loop finder looks like...");
		System.out.println(inp);
		if(flag == true){
			System.out.println("Flag evaluated to true, so we're removing the NodeX pairs, here is the graph before...");
			this.printMe();
			this.getNodes().remove(this.getNodes().size() - 1);
			this.getEdge().remove(this.getEdge().size() - 1);
			this.getBegin().remove(this.getBegin().size() - 1);
			this.getEnd().remove(this.getEnd().size() - 1);
			System.out.println("this is the graph after...");
			this.printMe();
		}

		// temp is a list of all the start nodes at this point.
		if(inp.contains("UNSATISFIABLE")){
			System.out.println("No more loops to be found... returning.");
		return;
		}
		String temp = inp.split("start_loop=")[1].split("], end_loop=")[0];
		//if there is only one start node.
		
		if(!temp.contains(",")){
			System.out.println("We've decided at this point there is only one loop in said graph.");
			String startNode = temp.substring(2, temp.length() - 1).trim();

			temp = inp.split("end_loop=")[1].split(", loop_set=")[0];
			String endNode = temp.substring(2, temp.length() - 2).trim();

			temp = inp.split("loop_set=")[1].split(", corresp=")[0];
			temp = temp.substring(1, temp.length() - 1);
			String[] temp2 = new String[20];
			temp2 = temp.split(", ");
			ArrayList<String> NodeList = new ArrayList<String>();
			for(int i= 0; i < temp2.length; i++){
				NodeList.add(temp2[i].substring(1, temp2[i].length() - 1));
				
			}
			
			ArrayList<String> CheckNodes = new ArrayList<String>();
			ArrayList<String> CheckEdges = new ArrayList<String>();
			for(int i = 0; i < this.getBegin().size(); i++){
				System.out.println("if "+ this.getBegin().get(i).getY()+ "equals " + startNode);
				if( this.getBegin().get(i).getY().trim().equalsIgnoreCase(startNode.trim() )){
					System.out.println("added one");
					CheckEdges.add( this.getBegin().get(i).getX());
					
				}
			}
			System.out.println("tempx = " + tempx);
			for(int i = 0; i < this.getEnd().size(); i++){
				if(CheckEdges.contains( this.getEnd().get(i).getX() ) && !(NodeList.contains(  this.getEnd().get(i).getY()))){
					nodeAfter = this.getEnd().get(i).getY();
				}
			}
			System.out.println("startnode = " + startNode);
			System.out.println("endnode = " + endNode);
			System.out.println("nodaf = " + nodeAfter);
			NodeList.add(nodeAfter);
			
			
			
			
			
			
			ArrayList<String> newnod = (ArrayList<String>) NodeList.clone();
			ArrayList<Pair> rembeg = new ArrayList<Pair>();
			ArrayList<Pair> remend = new ArrayList<Pair>();
			
			ArrayList<Pair> templist = this.getBegin();
			ArrayList<Pair> templist2 = this.getEnd();
			
			this.getNodes().removeAll(NodeList);
			
		
			
			// nodes that point to the start node, but who dont START from a node inside the loopset, now point to loop variable.
			
			for(int i = 0; i < templist2.size();i++){
				if(templist2.get(i).getY().equalsIgnoreCase(startNode)){
					if(!NodeList.contains( templist.get(Graph.indfromX(templist, templist2.get(i).getX())).getY())){
					templist2.get(i).setPair(templist2.get(i).getX(), "Loop1");
					}
				}
			}

			for(int i = 0; i < templist.size();i++){
				if(this.getBegin().get(i).getY().equalsIgnoreCase(nodeAfter)){
					templist.get(i).setPair(templist.get(i).getX(), "Loop1");
				}
			}
				
				
			
			
			
			
		/*	this.getNodes().add("Loop1");
			for(int i = 0; i < this.getBegin().size(); i++){
				if( templist.get(i).getY().equalsIgnoreCase(nodeAfter)){
					templist.get(i).setPair(templist.get(i).getX(), "Loop1");
				}
			}
			templist2 = this.getEnd();
			for(int i = 0; i < this.getEnd().size(); i++){
				if( templist2.get(i).getY().equalsIgnoreCase(startNode)){
					templist2.get(i).setPair(templist.get(i).getX(), "Loop1");
				}
			}
			*/
		/*	
			for(int i = 0; i < rembeg.size(); i++){
				System.out.println( rembeg.get(i).getX() + " , " + rembeg.get(i).getY());
			}
			for(int i = 0; i < remend.size(); i++){
				System.out.println( remend.get(i).getX() + " , " + remend.get(i).getY());
			}
			*/
	/*		
			System.out.println("NodeLIST:");
			for(int i = 0; i < NodeList.size(); i++){
				System.out.println(NodeList.get(i));
			}
		*/	
			
			
			
CheckEdges.clear();

			
			
			for(int i = 0; i < templist.size(); i ++){
				if(NodeList.contains( templist.get(i).getY() )){
					CheckEdges.add(templist.get(i).getX()	);
				
				}
			}
			for(int i = 0; i < templist2.size(); i++){
				if(NodeList.contains( templist2.get(i).getY())){
					if(!CheckEdges.contains(templist2.get(i).getX().trim())){
						CheckEdges.add(templist2.get(i).getX());
					}
				}
				
			}
			

			for(int i = 0; i < CheckEdges.size(); i++){
				int indexbeg = Graph.indfromX(this.getBegin(), CheckEdges.get(i));
				int indexend = Graph.indfromX(this.getEnd(), CheckEdges.get(i));
				
				rembeg.add(this.getBegin().get(indexbeg));
				remend.add(this.getEnd().get(indexend));
				this.getBegin().remove(indexbeg	);
				this.getEnd().remove(indexend);
			}
			
			
			
			for(int i = 0; i < rembeg.size(); i++){
				if(!CheckNodes.contains(rembeg.get(i).getY())){
					CheckNodes.add(rembeg.get(i).getY());
				}
			}
			for(int i = 0; i < remend.size(); i++){
				if(!CheckNodes.contains(remend.get(i).getY())){
					CheckNodes.add(remend.get(i).getY());
				}
			}
			
			System.out.println("begs:");
			for(int i = 0; i < begin.size(); i++){
			System.out.println(this.getBegin().get(i).getX() + " ," + this.getBegin().get(i).getY());
			}
			System.out.println("ends:");
			for(int i = 0; i < end.size(); i++){
			System.out.println(this.getEnd().get(i).getX() + " ," + this.getEnd().get(i).getY());
			}
			
			subs.add(new SubGraph("Loop1"));
			
			
			subs.get(0).setNodes(CheckNodes);
			this.getNodes().add("Loop1");
			subs.get(0).setEdge(CheckEdges);
			subs.get(0).setBegin(rembeg);
			subs.get(0).setEnd(remend);
			subs.get(0).setStartPt(startNode);
			subs.get(0).setEndPt(nodeAfter);
			subs.get(0).setEnd_loop(endNode);
			
			

			for(int i = 0; i < this.getEdge().size(); i++){
				if(CheckEdges.contains(this.getEdge().get(i).trim())){
					this.getEdge().remove(i);
					i--;
				}
			}
			
			
			
			System.out.println("at the end of createSubgraphs... Here's what we got.");
			System.out.println("graph:");
			this.printMe();
			for(int i  = 0; i < subs.size(); i++){
				System.out.println("Sub index " + i + "is below: ");
				subs.get(i).printMe();
			}
			
			
			
			
			
		}
		else{
			// here we deal with multiple outer loops in the same graph.
			System.out.println("We've decided at this point there is MORE THAN ONE loop in the graph.");
			
		}
		
		
		

		
		
		
	}
	
	
	
	
	public static int indfromX(ArrayList<Pair> inp, String X){
		for(int i = 0; i < inp.size();i++){
			if(inp.get(i).getX().equalsIgnoreCase(X)){
				return i;
			}
			
		}
		return -1;
		
		
	}

	/**
	 * short test for the graph.java class.
	 * @param argc - ignore.
	 */
	public static void main(String[] argc){
	    SubGraph test  = new SubGraph("Loop1");
	    
		//Graph test2 = new Graph();
	//	test2.readFile("src/graphs/parallelloops.txt");
		Graph test3 = new Graph();
		Graph test4 = new Graph();
		test.readFile("src/graphs/nestedloop.txt");
		System.out.println("Starting with the following graph...");
		test.printMe();
		test.solveGraph(1);
		System.out.println("finally done with solve graph... solution is = ");
		System.out.println( test.getPath()  );

	//	test2.printMe();
		//test3.readFile("src/graphs/linearinput.txt");
	//	test3.solveGraph(1);
		//test3.printMe();
	//	test4.readFile("src/graphs/complexgraph.txt");
	//	test4.solveGraph(1);
		System.out.println("test complete");
	}









}
