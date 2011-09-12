package controller;

import java.io.IOException;

import view.JFrameView;
import view.KodView;

import model.KodModel;


public class KodController extends AbstractController{

	
	
	public KodController(){
		setModel(new KodModel());
		setView(new KodView((KodModel)getModel(), this));
		((JFrameView)getView()).setVisible(true);
	}

	
	public String getCurPath(){
		
		String temp = ((KodModel)getModel()).getPathString();
		((KodModel)getModel()).reset();
		return temp;
	}


	public String getCurFile() {
		return((KodModel)getModel()).getFileString();
	}


	public void FindPathsFromFile(String curFile) {
		((KodModel)getModel()).FindPathsFromFile();
		
	}


	public void setFile(String fn) {
		((KodModel)getModel()).setFileName(fn);
		
	}


	public Double getCurTime() {
		return ((KodModel)getModel()).getSystime();

	}
	

	
}
