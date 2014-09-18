package com.example.FlowFree;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        SharedPreferences settings = getSharedPreferences("MyPrefs", 0);

        if (settings.getBoolean("is_first_time", true)) {

            FlowAdapter fa = new FlowAdapter( this );
            fa.insertFlows( 1, 5, "(2 2 3 1)", "(3 0 3 4)", "(2 0 0 4)", "(1 0 0 3)", "(2 4 3 3)", "" );
            fa.insertFlows( 2, 5, "(1 3 3 0)", "(2 4 4 3)", "(0 0 1 4)", "(2 2 3 1)", "(2 3 4 0)", "" );

            settings.edit().putBoolean("is_first_time", false).commit();
        }
    }


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
