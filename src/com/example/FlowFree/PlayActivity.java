package com.example.FlowFree;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Gvendur Stefáns on 8.9.2014.
 */
public class PlayActivity extends Activity {
	private Board theBoard;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play);

        Bundle b = getIntent().getExtras();
        String tag = b.getString("tag");

		theBoard = (Board)findViewById(R.id.board);
        theBoard.setNUM_CELLS(tag);
	}

	public void resetLevel(View view){
		Button theButton = (Button)view;

		int id = theButton.getId();
		if(id == R.id.button_reset){
			theBoard.reset();
		}
	}
}