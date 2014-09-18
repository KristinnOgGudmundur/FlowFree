package com.example.FlowFree;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;

/**
 * Created by Kristinn on 17.9.2014.
 */
public class GameChooserActivity extends ListActivity {

    private FlowAdapter mSA = new FlowAdapter( this );
    private SimpleCursorAdapter mCA;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Cursor cursor = mSA.queryFlows();
        String cols[] = DBHelper.TableFlowsCols;
        String from[] = { cols[1], cols[2]};
        int to[] = { R.id.s_fid , R.id.size};
        startManagingCursor( cursor );
        mCA = new SimpleCursorAdapter(this, R.layout.gamechooser, cursor, from, to );

        setListAdapter(mCA);
    }

    public void levelChosen(View view){
        Button button = (Button) view;

        int index = Character.getNumericValue(button.getText().charAt(0));

        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra("index", index);
        startActivity(intent);
        finish();

    }
}
