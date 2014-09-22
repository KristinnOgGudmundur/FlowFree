package com.example.FlowFree.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class PlayActivity extends Activity{
	private int levelIndex;
    private Board theBoard;
    private FlowAdapter mSA = new FlowAdapter( this );
    private static AlertDialog.Builder continueDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play);

        Puzzle myPuzzle = new Puzzle();
        List<Line> myLines = new ArrayList<Line>();

        Bundle extras = getIntent().getExtras();
        levelIndex = extras.getInt("index");

        //get our cursor and move it to the point of the index
        Cursor cursor = mSA.queryFlows();

        cursor.moveToPosition(levelIndex-1);

        myPuzzle.setFid(cursor.getInt(1));
        myPuzzle.setGridSize(cursor.getInt(2));

        getActionBar().setTitle("Level " + myPuzzle.getFid() + " - " +
                                           myPuzzle.getGridSize() + "x" +
                                           myPuzzle.getGridSize());

        String coor;
        char[] coors;
        //Gather our flows from the database, some parsing is needed
        for(int i = 4; i < 9; i++ )
        {
            coor = cursor.getString(i);

            if(!(coor.equals("")))
            {
                coors = coor.toCharArray();
                Coordinate start = new Coordinate(Character.getNumericValue(coors[1]),
                        Character.getNumericValue(coors[3]));
                Coordinate end   = new Coordinate(Character.getNumericValue(coors[5]),
                        Character.getNumericValue(coors[7]));

                myLines.add(new Line(start, end, i , 0));
            }
        }
        myPuzzle.setLines(myLines);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);

		String stringSound = settings.getString("sounds", "50");
        int sound = Integer.parseInt(stringSound);

		boolean vibrations = settings.getBoolean("vibrations", false);
		boolean colorblind = settings.getBoolean("colorBlindMode", false);


        cursor.close();

        //build our dialog for the winning state
        continueDialog = new AlertDialog.Builder(this);
        continueDialog.setMessage("Level completed !");
        continueDialog.setCancelable(true);
        continueDialog.setPositiveButton("next level",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finishedPuzzle();
                    }
                });
        continueDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        cancel();
                        dialog.cancel();
                    }
                });

        theBoard = (Board)findViewById(R.id.board);

        TextView flows = (TextView)findViewById(R.id.flowsComplete);
		TextView fillPercentage = (TextView)findViewById(R.id.fillPercentage);

        //set the board up with the given level from database
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
		Intent intent = new Intent(this, PlayActivity.class);
        if(levelIndex > 1)
        {
            intent.putExtra("index", levelIndex - 1);
            startActivity(intent);
            finish();
        }
	}

	public void finishedPuzzle(){
		Intent intent = new Intent(this, PlayActivity.class);
        long count = mSA.count();
        mSA.updateFinished(levelIndex, 1);
        if(count > levelIndex)
        {
            intent.putExtra("index", levelIndex + 1);
            startActivity(intent);
            finish();
        }
	}

    public void cancel(){
        mSA.updateFinished(levelIndex, 1);
    }

    public void nextPuzzle(View view){
        Intent intent = new Intent(this, PlayActivity.class);
        long count = mSA.count();
        if(count > levelIndex)
        {
            intent.putExtra("index", levelIndex + 1);
            startActivity(intent);
            finish();
        }
    }

    public static void show()
    {
        continueDialog.show();
    }
}