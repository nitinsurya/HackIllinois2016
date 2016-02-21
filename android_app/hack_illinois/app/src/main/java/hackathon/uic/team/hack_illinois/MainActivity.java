package hackathon.uic.team.hack_illinois;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


import android.preference.PreferenceManager;


import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends Activity {
    GraphView graph;

    BroadcastReceiver mRegistrationBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive (Context context, Intent intent){

            SharedPreferences sharedPreferences =
                    PreferenceManager.getDefaultSharedPreferences(context);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       graph = (GraphView) findViewById(R.id.graph);



        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
        fetchData();

    }


   void fetchData(){
       int l=0;
       int pi=0;

       Object response="";
       String string="";
       DataPoint p[]=new DataPoint[50];

/*
       try {
            response = HttpRequest.get("http://40.114.2.108/send_update.php?deviceid=1");
           JSONObject responseObject1 = new JSONObject(response.toString());
           JSONArray arraySet1[]=new JSONArray[5];
       }
       */





      //     int k=1;


    //for (int i = 0; i < 2; i++) {
        //arraySet1[i] = responseObject1.getJSONArray(i + 1 + "");

        //l = arraySet1[i].length();

        //float a, b;

      //  for (int j = 0; j < l; j++) {

          //  a = (float) arraySet1[i].getJSONArray(j).get(0);
           // b = (float) arraySet1[i].getJSONArray(j).get(1);
            double arr[][]=new double[][]{{1,2},{2,2},{1,3},{2,2},{1,2},{0.1,0.5},{0.2,-0.3},{0.3,2.5},{0.4,-1.5},{0.5,0.3}};
            for(int ii=0;ii<10;ii++ )
                for(int jj=0;jj<2;jj++ ) {
                    p[pi]=new DataPoint(arr[ii][jj],arr[ii][++jj]);
                    pi++;

                }
           // p[pi] = new DataPoint(a, b);
            //

       // }
        //System.out.println("a: "+a);








       LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(p);
       graph.addSeries(series);

       graph.getViewport().setScalable(true);
       graph.getViewport().setScrollable(true);
       //    set manual X bounds
       graph.getViewport().setXAxisBoundsManual(true);
       graph.getViewport().setMinX(0);
       graph.getViewport().setMaxX(10);

// set manual Y bounds
       graph.getViewport().setYAxisBoundsManual(true);
       graph.getViewport().setMinY(-1.5);
       graph.getViewport().setMaxY(2.5);

   }
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                Log.i("GCM", "Resolvable Error ");
            } else {
                Log.i("GCM", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }



}
