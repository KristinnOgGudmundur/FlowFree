package com.example.FlowFree.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import com.example.FlowFree.R;
import com.example.FlowFree.activities.PlayActivity;
import com.example.FlowFree.database.DBHelper;
import com.example.FlowFree.database.FlowAdapter;

/**
 * Created by Kristinn on 17.9.2014.
 */
public class GameChooserActivity extends ListActivity {

    private FlowAdapter mSA = new FlowAdapter( this );
    private SimpleCursorAdapter mCA;

    /**
     * runs when the Activity is created
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setTitle(R.string.gameChooserTitle);

        Cursor cursor = mSA.queryFlows();
        String cols[] = DBHelper.TableFlowsCols;
        String from[] = { cols[1], cols[2]};
        int to[] = { R.id.s_fid , R.id.size};
        startManagingCursor( cursor );
        mCA = new SimpleCursorAdapter(this, R.layout.gamechooser, cursor, from, to );


        mCA.setViewBinder( new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {

                if(view.getId() == R.id.s_fid)
                {
                    view.setTag(cursor.getInt(cursor.getColumnIndex("fid")));

                    if(cursor.getInt(cursor.getColumnIndex("finished")) == 1 )
                    {
                        ((Button)view).setText("x");
                        return true;
                    }
                }
                if(view.getId() == R.id.size)
                {
                    ((TextView)view).setText((cursor.getInt(cursor.getColumnIndex("size"))) + "x" + (cursor.getInt(cursor.getColumnIndex("size"))));
                    return true;
                }
                return false;
            }
        });

        setListAdapter(mCA);
    }

    /**
     * When a level button has been clicked
     * @param view
     */
    public void levelChosen(View view){

        Button button = (Button) view;

        int index;
        if(button.getTag().toString().length() == 2)
        {
            index = Character.getNumericValue(button.getTag().toString().charAt(1));
            index = index + 10;
        }
        else{
            index = Character.getNumericValue(button.getTag().toString().charAt(0));
        }

        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra("index", index);
        startActivity(intent);
    }
}
