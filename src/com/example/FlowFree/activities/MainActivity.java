package com.example.FlowFree.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.FlowFree.R;
import com.example.FlowFree.database.FlowAdapter;

public class MainActivity extends Activity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        SharedPreferences settings = getSharedPreferences("MyPrefs", 0);
        FlowAdapter fa = new FlowAdapter( this );

        //if the app is running on your phone for the first time, then we will write to the database
        if (settings.getBoolean("is_first_time", true)) {
            fa.insertFlows( 1, 5, "(2 2 3 1)", "(3 0 3 4)", "(2 0 0 4)", "(1 0 0 3)", "(2 4 3 3)", "" );
            fa.insertFlows( 2, 5, "(1 3 3 0)", "(2 4 4 3)", "(0 0 1 4)", "(2 2 3 1)", "(2 3 4 0)", "" );
            fa.insertFlows(3, 6, "(2 3 4 0)", "(0 0 0 4)", "(2 5 5 0)", "(0 5 1 0)", "(2 4 4 1)", "(2 0 2 2)");
            settings.edit().putBoolean("is_first_time", false).commit();
        }
    }

    /**
     * When the play or options button is pressed
     * @param view
     */
	public void buttonClick(View view){
		Button button = (Button) view;

		int id = button.getId();
		if(id == R.id.button_play){
			startActivity(new Intent(this, GameChooserActivity.class));
		}
        else{
            startActivity(new Intent(this, OptionsActivity.class));
        }
	}
}
