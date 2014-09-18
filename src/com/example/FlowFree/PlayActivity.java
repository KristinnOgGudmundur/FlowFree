package com.example.FlowFree;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Gvendur Stefáns on 8.9.2014.
 */
public class PlayActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play);

        Bundle b = getIntent().getExtras();
        String tag = b.getString("tag");

		Board board = (Board)findViewById(R.id.board);
        board.setNUM_CELLS(tag);
	}
}