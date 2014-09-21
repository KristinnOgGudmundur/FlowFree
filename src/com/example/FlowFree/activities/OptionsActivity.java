package com.example.FlowFree.activities;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import com.example.FlowFree.R;

/**
 * Created by Kristinn on 11.9.2014.
 */
public class OptionsActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);

        getActionBar().setTitle("options");
    }

}
