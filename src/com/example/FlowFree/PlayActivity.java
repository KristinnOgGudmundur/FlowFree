package com.example.FlowFree;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gvendur Stefáns on 8.9.2014.
 */
public class PlayActivity extends Activity {
	private Board theBoard;
    public static Puzzle myPuzzle;
    private FlowAdapter mSA = new FlowAdapter( this );

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play);

        Bundle extras = getIntent().getExtras();
        int index = extras.getInt("index");

        Cursor cursor = mSA.queryFlows();
        cursor.moveToPosition(index-1);

        Puzzle myPuzzle = new Puzzle();
        myPuzzle.setFid(cursor.getInt(1));
        myPuzzle.setGridSize(cursor.getInt(2));
        List<Line> myLines = new ArrayList<Line>();
        for(int i = 3; i < 8; i++ )
        {
            char[] coors = cursor.getString(i).toCharArray();

            if(coors.equals(""))
            {
                break;
            }
            Coordinate start = new Coordinate(Character.getNumericValue(coors[1]), Character.getNumericValue(coors[3]));
            Coordinate end = new Coordinate(Character.getNumericValue(coors[5]), Character.getNumericValue(coors[7]));
            Line myline = new Line(start, end, i , 0);
            myLines.add(myline);

        }
        myPuzzle.setLines(myLines);

        Board board = (Board)findViewById(R.id.board);
        board.setupBoard(myPuzzle);
	}

	public void resetLevel(View view){
		Button theButton = (Button)view;

		int id = theButton.getId();
		if(id == R.id.button_reset){
			theBoard.reset();
		}
	}
}