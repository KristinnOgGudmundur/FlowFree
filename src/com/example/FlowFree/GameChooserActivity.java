package com.example.FlowFree;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Kristinn on 17.9.2014.
 */
public class GameChooserActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamechooser);
    }



    public void levelChosen(View view){
        Button button = (Button) view;

        String tag = (String)button.getTag();

        Intent intent = new Intent(this, PlayActivity.class);
        Bundle b = new Bundle();
        b.putString("tag", tag);
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
        finish();

    }
}
