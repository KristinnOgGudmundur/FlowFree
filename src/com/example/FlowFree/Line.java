package com.example.FlowFree;

/**
 * Created by Gvendur Stefáns on 11.9.2014.
 */
public class Line {
	private Coordinate start;
	private Coordinate end;
	private Cellpath path;

	public Line(Coordinate start, Coordinate end){
		this.start = start;
		this.end = end;
		path = new Cellpath();
	}
    public Coordinate getStart()
    {
        return start;
    }

    public Coordinate getEnd()
    {
        return end;
    }
	public boolean complete(){
		return path.contains(start, end);
	}

	public boolean isStartingPoint(Coordinate c){
		return (c.equals(start)) || (c.equals(end));
	}

	public Cellpath getPath(){
		return path;
	}

	public boolean contains(Coordinate theCoordinate){
		return start.equals(theCoordinate) || end.equals(theCoordinate) || path.contains(theCoordinate);
	}
}