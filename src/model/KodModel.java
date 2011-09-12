package model;

public class KodModel extends AbstractModel {
	
	String Filename = new String();
	Graph modelgraph = new Graph();
	Double Systime = new Double (0.0);
	
	
	
	public Double getSystime() {
		return Systime;
	}

	public void setSystime(Double systime) {
		Systime = systime;
	}

	public void setFileName(String filename){
		System.currentTimeMillis();
		Filename = filename;
	}
	
	public String getPathString() {
		return modelgraph.getPath();
		
	}
	public String getFileString() {
		return Filename;
		
	}
	public void FindPathsFromFile() {
		long temp = System.currentTimeMillis();
		modelgraph.readFile(Filename);
		modelgraph.solveGraph(1);
		this.setSystime(    ((System.currentTimeMillis() - temp)  / 1000.0 ) );
		ModelEvent d = new ModelEvent(this, 1, "");
		notifyChanged(d);
		
		
		
	}

	public void reset() {
		modelgraph = new Graph();
	}
	

	

}
