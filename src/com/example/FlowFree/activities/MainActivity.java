package com.example.FlowFree.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.example.FlowFree.R;
import com.example.FlowFree.database.FlowAdapter;
import com.example.FlowFree.objects.parser.Flow;
import com.example.FlowFree.objects.parser.XmlParser;

import java.util.List;

public class MainActivity extends Activity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        getActionBar().setTitle("Menu");

        SharedPreferences settings = getSharedPreferences("MyPrefs", 0);

        //if the app is running on your phone for the first time, then we will write to the database
        if (settings.getBoolean("is_first_time", true)) {

            FlowAdapter fa = new FlowAdapter( this );
            XmlParser myParser = new XmlParser();
            List<Flow> insertFlows;
            insertFlows = myParser.parseXML();

            int i = 1;
            for(Flow f : insertFlows)
            {
                fa.insertFlows(i,f.getSize(),0 ,f.getFlow1(),f.getFlow2(),f.getFlow3(),
                                             f.getFlow4(),f.getFlow5(),f.getFlow6());
                i++;
            }
            settings.edit().putBoolean("is_first_time", false).commit();
        }

		setImageSize();
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

	public void setImageSize(){
		ImageView theImage = (ImageView)findViewById(R.id.titleImage);
		Display theDisplay = getWindowManager().getDefaultDisplay();
		Point dimensions = new Point();
		theDisplay.getSize(dimensions);
		System.out.println("Width: " + dimensions.x + ", Height: " + dimensions.y);

		int sideMargins = (int)(dimensions.x * 0.2);
		int imageSize = (int)(dimensions.y * 0.75);

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, imageSize);
		lp.setMargins(sideMargins,-sideMargins,sideMargins,-sideMargins);
		theImage.setLayoutParams(lp);
	}
}
