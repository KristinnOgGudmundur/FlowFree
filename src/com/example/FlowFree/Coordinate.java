package com.example.FlowFree;

/**
 * Created by yngvi on 5.9.2014.
 */
public class Coordinate {

    private int m_col;
    private int m_row;

    Coordinate( int col, int row ) {
        m_col = col;
        m_row = row;
    }

    public int getCol() {
        return m_col;
    }

    public int getRow() {
        return m_row;
    }

    @Override
    public boolean equals( Object other ) {
        if ( !(other instanceof Coordinate) ) {
            return false;
        }
        Coordinate otherCo = (Coordinate) other;
        return otherCo.getCol() == this.getCol()&& otherCo.getRow() == this.getRow();
    }

	public static boolean areNeighbours(int c1, int r1, int c2, int r2){
		return Math.abs(c1-c2) + Math.abs(r1-r2) == 1;
	}

	public static boolean areNeighbours(Coordinate c1, Coordinate c2){
		return Math.abs(c1.getCol()-c2.getCol()) + Math.abs(c1.getRow()-c2.getRow()) == 1;
	}

	public boolean areNeighbours(Coordinate other){
		return Math.abs(this.getCol()-other.getCol()) + Math.abs(this.getRow()-other.getRow()) == 1;
	}
}
