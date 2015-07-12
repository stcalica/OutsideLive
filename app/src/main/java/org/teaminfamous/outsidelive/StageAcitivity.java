package org.teaminfamous.outsidelive;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class StageAcitivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_acitivity);
        updateData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stage_acitivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateData()
    {
        //read comments
        ListView comments = (ListView) findViewById(R.id.comments);
        try {

            //parse data, comma delimited
            List<String> comments_ar = Arrays.asList("SUP", "dis wack", "lol",
                    "This piece of music expresses the nuances of life involving the lights of the petal flower.",
                    "kappa kappa", "FREEDOM!", "kappa", "heyoo", "great set", "where dey at doe?", "TEST");
            //create adapter
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, comments_ar);
            comments.setAdapter(adapter);
        } catch (Exception e){
        }
    }//update listview
}
