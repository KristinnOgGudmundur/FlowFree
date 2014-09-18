package com.example.FlowFree;

import android.graphics.Paint;

/**
 * Created by Gvendur Stefáns on 11.9.2014.
 */
public class Line {
	private Coordinate start;
	private Coordinate end;
	private Cellpath path;
    private Paint paint;
	private int colorBlindNumber;

    public Line(Coordinate start, Coordinate end, int paintColor, int colorBlindNumber){
		this.start = start;
		this.end = end;
		path = new Cellpath();
        paint = new Paint();
		this.colorBlindNumber = colorBlindNumber;

        paint.setStyle( Paint.Style.STROKE );
        paint.setColor(paintColor);
        paint.setStrokeWidth(32);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin( Paint.Join.ROUND );
        paint.setAntiAlias( true );
	}

    public Paint getPaint()
    {
        return paint;
    }

    public void setPaint(Paint paint)
    {
        this.paint = paint;
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

	public String getColorBlindString(){
		return colorBlindNumber + "";
	}
}