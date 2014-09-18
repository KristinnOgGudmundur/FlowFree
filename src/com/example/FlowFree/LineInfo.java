package com.example.FlowFree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gvendur Stefáns on 11.9.2014.
 */
public class LineInfo {
	private ArrayList<Line> allLines;
	private final int NUM_CELLS;

	public LineInfo(ArrayList<Line> lines, int NUM_CELLS){
		allLines = lines;
		this.NUM_CELLS = NUM_CELLS;
	}

    //get start coordinates
    public Coordinate getStart(int index)
    {
        return allLines.get(index).getStart();
    }

    //get end coordinates
    public Coordinate getEnd(int index)
    {
        return allLines.get(index).getEnd();
    }

    //get all lines
    public ArrayList<Line> getAllLines()
    {
        return allLines;
    }

	public boolean allComplete(){
		for(Line l : allLines){
			if(!l.complete()){
				return false;
			}
		}
		return true;
	}

	public Cellpath getCellPath(Coordinate c){
		for(Line l : allLines){
			if(l.contains(c)){
				return l.getPath();
			}
		}
		return null;
	}

	//Returns a cellpath that has c as a start point or end point
	//or null, if no such path exists
	public Cellpath getCellPathByStartPoint(Coordinate c){
		for(Line l : allLines){
			if(l.isStartingPoint(c)){
			//if(l.contains(c)){
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
		if(last.areNeighbours(c) && !isStartOfOtherLine(p, c)){
			if(!theLine.complete()) {
				Cellpath otherCellpath = getCellPath(c);
				if(otherCellpath != null){
					if(otherCellpath != p) {
						//We are tracing into another line
						getCellPath(c).setEndAt(c);
						getCellPath(c).removeLast();
					}
				}
				p.append(c);
			}
			else if(p.contains(c)){
				p.append(c);
			}
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

	public Line getLine(Cellpath p){
		for(Line l : allLines){
			if(l.getPath().equals(p)){
				return l;
			}
		}
		return null;
	}

	public Line getLine(Coordinate c){
		for(Line l : allLines){
			if(l.contains(c)){
				return l;
			}
		}
		return null;
	}

	private boolean isStartOfOtherLine(Cellpath p, Coordinate c){
		for(Line l : allLines){
			if(!l.getPath().equals(p)){
				if(l.isStartingPoint(c)){
					return true;
				}
			}
		}
		return false;
	}
}
