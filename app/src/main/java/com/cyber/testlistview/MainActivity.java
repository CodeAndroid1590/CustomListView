package com.cyber.testlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    String url = "https://jsonplaceholder.typicode.com/photos";
    ListView customListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customListView = (ListView)findViewById(R.id.cust_listview);
        new BackgroundWorker().execute();
    }

    public class BackgroundWorker extends AsyncTask<Void,Void,String>{
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MainActivity.this);
            pd.setTitle("Please wait");
            pd.setMessage("Downloading...");
            pd.show();
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL myUrl = new URL(url);
                HttpURLConnection connection= (HttpURLConnection)myUrl.openConnection();
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine())!= null){
                    builder.append(line);
                }
                Log.e("json",builder.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
        }
    }
}