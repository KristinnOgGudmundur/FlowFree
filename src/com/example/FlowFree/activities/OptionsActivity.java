package com.example.FlowFree.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import com.example.FlowFree.R;
import com.example.FlowFree.database.FlowAdapter;

/**
 * Created by Kristinn on 11.9.2014.
 */

public class OptionsActivity extends PreferenceActivity {

    private FlowAdapter mSA = new FlowAdapter( this );

    private AlertDialog.Builder confirmDialog;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);


        confirmDialog = new AlertDialog.Builder(this);
        confirmDialog.setMessage(R.string.confirm);
        confirmDialog.setCancelable(true);
        confirmDialog.setPositiveButton(R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Cursor cursor = mSA.queryFlows();
                        cursor.moveToFirst();

                        long count = mSA.count();
                        for(int i = 0; i <= count; i++)
                        {
                            mSA.updateFinished(i, false);
                        }
                        dialog.cancel();
                    }
                });
        confirmDialog.setNegativeButton(R.string.no,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        Preference myPref = (Preference) findPreference("reset");
        myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                confirmDialog.show();
                return true;
            }
        });

        getActionBar().setTitle("Options");
    }
}
