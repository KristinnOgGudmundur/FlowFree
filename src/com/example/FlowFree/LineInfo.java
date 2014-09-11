package com.example.FlowFree;

import java.util.ArrayList;

/**
 * Created by Gvendur Stefáns on 11.9.2014.
 */
public class LineInfo {
	public ArrayList<Line> allLines;

	public LineInfo(ArrayList<Line> lines){
		allLines = lines;
	}

	public boolean allComplete(){
		for(Line l : allLines){
			if(!l.complete()){
				return false;
			}
		}
		return true;
	}

	//Returns a cellpath that has c as a start point or end point
	//or null, if no such path exists
	public Cellpath getCellPath(Coordinate c){
		for(Line l : allLines){
			if(l.isStartingPoint(c)){
				return l.getPath();
			}
		}
		return null;
	}
}
