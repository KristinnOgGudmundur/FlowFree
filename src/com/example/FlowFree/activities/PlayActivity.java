package com.example.FlowFree.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.FlowFree.*;
import com.example.FlowFree.database.FlowAdapter;
import com.example.FlowFree.objects.Board;
import com.example.FlowFree.objects.Coordinate;
import com.example.FlowFree.objects.Line;
import com.example.FlowFree.objects.Puzzle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gvendur Stefáns on 8.9.2014.
 */
public class PlayActivity extends Activity {

    private Board theBoard;
    private FlowAdapter mSA = new FlowAdapter( this );


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play);

        Puzzle myPuzzle = new Puzzle();
        List<Line> myLines = new ArrayList<Line>();

        Bundle extras = getIntent().getExtras();
        int index = extras.getInt("index");

        //get our cursor and move it to the point of the index
        Cursor cursor = mSA.queryFlows();
        cursor.moveToPosition(index-1);

        myPuzzle.setFid(cursor.getInt(1));
        myPuzzle.setGridSize(cursor.getInt(2));

        char[] coors;
        //Gather our flows from the database, some parsing is needed
        for(int i = 3; i < 8; i++ )
        {
            coors = cursor.getString(i).toCharArray();

            if(coors.toString() == null){ break; }

            Coordinate start = new Coordinate(Character.getNumericValue(coors[1]),
                                              Character.getNumericValue(coors[3]));
            Coordinate end   = new Coordinate(Character.getNumericValue(coors[5]),
                                              Character.getNumericValue(coors[7]));

            myLines.add(new Line(start, end, i , 0));
        }
        myPuzzle.setLines(myLines);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        boolean sound = settings.getBoolean("sounds", false);
        boolean vibrations = settings.getBoolean("vibrations", false);
		boolean colorblind = settings.getBoolean("ColorBlindMode", false);

        //set the board up with the given level from database
        theBoard = (Board)findViewById(R.id.board);

		TextView flows = (TextView)findViewById(R.id.flowsComplete);
		TextView fillPercentage = (TextView)findViewById(R.id.fillPercentage);

        theBoard.setupBoard(myPuzzle, sound, vibrations, colorblind);
		theBoard.setTextViews(flows, fillPercentage);
	}

	public void resetLevel(View view){
		Button theButton = (Button)view;

		int id = theButton.getId();
		if(id == R.id.button_reset){
			theBoard.reset();
		}
	}

	public void previousPuzzle(View view){

	}

	public void nextPuzzle(View view){

	}
}