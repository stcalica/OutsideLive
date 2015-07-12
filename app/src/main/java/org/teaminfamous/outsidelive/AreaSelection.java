package org.teaminfamous.outsidelive;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


public class AreaSelection extends ActionBarActivity {
    public final static String STAGE_TITLE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_selection);
        ImageButton stage1 = (ImageButton) findViewById(R.id.stage1);
        stage1.setImageResource(R.drawable.b1);
        ImageButton stage2 = (ImageButton) findViewById(R.id.stage2);
        stage2.setImageResource(R.drawable.b2);
        ImageButton stage3 = (ImageButton) findViewById(R.id.stage3);
        stage3.setImageResource(R.drawable.b3);
        startStage1Listener();
        startStage2Listener();
        startStage3Listener();

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#6DCAD6"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
    }

    @Override
    protected void onResume(){
        super.onResume();
        ImageButton stage1 = (ImageButton) findViewById(R.id.stage1);
        stage1.setImageResource(R.drawable.b1);

        ImageButton stage2 = (ImageButton) findViewById(R.id.stage2);
        stage2.setImageResource(R.drawable.b2);

        ImageButton stage3 = (ImageButton) findViewById(R.id.stage3);
        stage3.setImageResource(R.drawable.b3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_area_selection, menu);
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
            finish();
            System.exit(0);
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    public void showStage1(View v){
        Intent stage_activity = new Intent(this, StageAcitivity.class);

        startActivity(stage_activity);
    }//start new intent*/

    public void startStage1Listener() {

        final ImageButton stage1 = (ImageButton) findViewById(R.id.stage1);

        stage1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent stage_activity = new Intent(AreaSelection.this, StageAcitivity.class);
                stage_activity.putExtra(STAGE_TITLE, "Stage 1 - Twin Peaks");
                stage_activity.putExtra("STAGE_NUMBER", 1);
                startActivity(stage_activity);

                stage1.setImageResource(R.drawable.stage1_buttonb);
            }

        });

    }

    public void startStage2Listener() {

        final ImageButton stage2 = (ImageButton) findViewById(R.id.stage2);

        stage2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent stage_activity = new Intent(AreaSelection.this, StageAcitivity.class);
                stage_activity.putExtra(STAGE_TITLE, "Stage 2 - Land's End");
                stage_activity.putExtra("STAGE_NUMBER", 2);
                startActivity(stage_activity);

                stage2.setImageResource(R.drawable.stage1_buttonb);
            }

        });

    }

    public void startStage3Listener() {

        final ImageButton stage3 = (ImageButton) findViewById(R.id.stage3);

        stage3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent stage_activity = new Intent(AreaSelection.this, StageAcitivity.class);
                stage_activity.putExtra(STAGE_TITLE, "Beer Garden");
                stage_activity.putExtra("STAGE_NUMBER", 3);
                startActivity(stage_activity);

                stage3.setImageResource(R.drawable.stage1_buttonb);
            }

        });

    }
}
