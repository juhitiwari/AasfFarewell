package com.example.slytherin.aasffarewell;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    String value;
    CountDownTimer t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseMessaging.getInstance().subscribeToTopic("news");

Button button=findViewById(R.id.btn);
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

    }
});
        t = new CountDownTimer( Long.MAX_VALUE , 10000) {


            public void onTick(long millisUntilFinished) {
                Log.d("test","Timer tick");
                hit();
           }

            public void onFinish() {
                Log.d("test","Timer last tick");

            }
        }.start();



      //  Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
    }
    private void hit(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // All your networking logic
                // should be here
                // Create URL
                URL githubEndpoint = null;
                try {
                    githubEndpoint = new URL("http://aasf.in/farewell/testnotif.php/");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

// Create connection
                try {
                    HttpURLConnection myConnection =
                            (HttpURLConnection) githubEndpoint.openConnection();
                    //myConnection.setRequestProperty("User-Agent", "my-rest-app-v0.1");
                    //myConnection.setRequestProperty("Accept",
                    //      "application/vnd.github.v3+json");
                    //myConnection.setRequestProperty("Contact-Me",
                    //      "hathibelagal@example.com");
                    if (myConnection.getResponseCode() == 200) {
                        // Success
                        // Further processing here
                        InputStream responseBody = myConnection.getInputStream();
                        InputStreamReader responseBodyReader =
                                new InputStreamReader(responseBody, "UTF-8");
                        JsonReader jsonReader = new JsonReader(responseBodyReader);
                        jsonReader.beginObject(); // Start processing the JSON object
                        while (jsonReader.hasNext()) { // Loop through all keys
                            String key = jsonReader.nextName(); // Fetch the next key
                            if (key.equals("message")) { // Check if desired key
                                // Fetch the value as a String
                                value = jsonReader.nextString();



                                break; // Break out of the loop
                            } else {
                                jsonReader.skipValue(); // Skip values of other keys
                            }
                        }
                        jsonReader.close();
                        myConnection.disconnect();
                    } else {
                        // Error handling code goes here
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
