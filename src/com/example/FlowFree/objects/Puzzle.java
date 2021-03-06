package com.example.FlowFree.objects;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Kristinn on 18.9.2014.
 */

/**
 * A Pojo class for the puzzles
 */
public class Puzzle {

    private int fid;
    private int gridSize;
    private List<Line> lines;
    private int best;

    //region Getters and Setters
    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public int getBest() {
        return best;
    }

    public void setBest(int best) {
        this.best = best;
    }
    //endregion
}
