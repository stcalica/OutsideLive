package org.teaminfamous.outsidelive;

import android.content.Context;
import android.content.Entity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.teaminfamous.outsidelive.R;



public class StageAcitivity extends ActionBarActivity {
    private MediaPlayer mPlayer;
    private VisualizerView mVisualizerView;
    public  String Weatherjson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_acitivity);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "My Tag");
        wl.acquire();

        //messenger webview
        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.setWebViewClient(new WebViewClient());
        //enable javascript
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        Intent intent = getIntent();
        String stage = intent.getStringExtra(AreaSelection.STAGE_TITLE);
        setTitle(stage);

        int stg_num = intent.getIntExtra("STAGE_NUMBER", 0);

        if (stg_num == 1) {
            mPlayer = MediaPlayer.create(this, Uri.parse(getResources().getString(R.string.stage_1_URL)));
            mPlayer.start();
            myWebView.loadUrl(getResources().getString(R.string.chat_URL));
        }//if stage 1
        else if (stg_num == 2) {
            mPlayer = MediaPlayer.create(this, Uri.parse(getResources().getString(R.string.stage_2_URL)));
            mPlayer.start();
            myWebView.loadUrl(getResources().getString(R.string.chat_URL));
        } else if (stg_num == 3) {
            mPlayer = MediaPlayer.create(this, R.raw.mozart);
            mPlayer.start();
            myWebView.loadUrl(getResources().getString(R.string.chat2_URL));
        }

        updateData();
    }

    protected void onResume() {
        super.onResume();
        init();
    }

    protected void onPause() {
        cleanUp();
        super.onPause();
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        if (wl.isHeld())
            wl.release();
    }

    @Override
    protected void onDestroy() {
        cleanUp();
        super.onDestroy();
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        if (wl.isHeld())
            wl.release();

    }

    private void init() {
        mVisualizerView = (VisualizerView) findViewById(R.id.visualizerView);
        mVisualizerView.link(mPlayer);

        //addBarGraphRenderers();
        addLineRenderer();
    }

    private void cleanUp() {
        if (mPlayer != null) {
            mVisualizerView.release();
            mPlayer.release();
            mPlayer = null;
        }
    }


    private void addLineRenderer() {

        Paint linePaint = new Paint();
        linePaint.setStrokeWidth(5f);
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.argb(150, 255, 60, 60));

        Paint lineFlashPaint = new Paint();
        lineFlashPaint.setStrokeWidth(10f);
        lineFlashPaint.setAntiAlias(true);
        lineFlashPaint.setColor(Color.argb(255, 60, 255, 255));
        LineRenderer lineRenderer = new LineRenderer(linePaint, lineFlashPaint, true);
        mVisualizerView.addRenderer(lineRenderer);

    }


    private void addBarGraphRenderers() {
        Paint paint2 = new Paint();
        paint2.setStrokeWidth(12f);
        paint2.setAntiAlias(true);
        paint2.setColor(Color.argb(200, 200, 0, 0));
        BarGraphRenderer barGraphRendererTop = new BarGraphRenderer(4, paint2, true);
        mVisualizerView.addRenderer(barGraphRendererTop);
    }


    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stage_acitivity, menu);
        return true;
    }*/

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

    private void updateData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("UpdateData", "Here");
                    int resp = 666;
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpGet httpPost = new HttpGet("http://api.wunderground.com/api/013ca7a73d07e736/geolookup/conditions/q/CA/San_Francisco.json");
                    try {
                        HttpResponse httpResponse = httpclient.execute(httpPost);
                        HttpEntity entity = httpResponse.getEntity();
                        BufferedReader rd = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
                        String body = "";
                        while ((body = rd.readLine()) != null)
                        {
                            Log.e("UpdateData", body);
                        }
                        Weatherjson = body;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();

    }//update listview

    class WeatherData extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            return "try";
        }
    }
}






