package com.example.FlowFree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gvendur Stefáns on 11.9.2014.
 */
public class LineInfo {
	public ArrayList<Line> allLines;
	private final int NUM_CELLS;

	public LineInfo(ArrayList<Line> lines, int NUM_CELLS){
		allLines = lines;
		this.NUM_CELLS = NUM_CELLS;
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

	public void addToCellPath(Cellpath p, Coordinate c){
		//TODO: Implement
		//The logic of legal additions should be called in this function
		List<Coordinate> coordinateList = p.getCoordinates();
		Coordinate last = coordinateList.get(coordinateList.size()-1);
		Line theLine = getLine(p);
		if(theLine == null){
			throw new NullPointerException("There is no line with that cellpath. WAT!");
		}
		if(last.areNeighbours(c) && (!cellIsFull(c) || theLine.isStartingPoint(c))){
			p.append(c);
		}
	}

	private boolean cellIsFull(Coordinate theCoordinate){
		for(Line l : allLines){
			if(l.contains(theCoordinate)){
				return true;
			}
		}
		return false;
	}

	private Line getLine(Cellpath p){
		for(Line l : allLines){
			if(l.getPath().equals(p)){
				return l;
			}
		}
		return null;
	}
}
