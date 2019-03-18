package com.example.androidlabs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class WeatherForecast extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "Weather Forecast";
    private EditText messageBox, currentTemperature,minWeather, maxWeather, uvRating;
    private ImageView weatherImage;
    private ProgressBar progressBar;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        ForecastQuery networkThread = new ForecastQuery();
        networkThread.execute( "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric" ); //this starts doInBackground on other thread

        messageBox = (EditText)findViewById(R.id.messageBox);
        currentTemperature =  (EditText)findViewById(R.id.currentTemperature);
        minWeather =  (EditText)findViewById(R.id.minWeather);
        maxWeather =  (EditText)findViewById(R.id.maxWeather);
        uvRating =  (EditText)findViewById(R.id.uvRating);
        weatherImage = (ImageView)findViewById(R.id.weatherImage);

        progressBar= (ProgressBar) findViewById(R.id.progress);
        textView= (TextView) findViewById(R.id.tvProgress);
        new Thread(){
            @Override
            public void run() {
                int i = 0;
                while (i < 100) {
                    i++;
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    final int j = i;
                    progressBar.setProgress(i);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(j + "%");
                        }
                    });
                }

            }
        }.start();
        progressBar.setVisibility(View.VISIBLE);  //show the progress bar
    }

    // a subclass of AsyncTask                  Type1    Type2    Type3
    private class ForecastQuery extends AsyncTask<String, Integer, String>{

        private String  windSpeed, minTemerature, maxTemerature, currentTemerature, uvRatingText, iconName;
        private Bitmap currentWeatherIcon = null;
        @Override
        protected String doInBackground(String ... params) {
            try {
                //get the string url:
                String myUrl = params[0];

                //create the network connection:
                URL url = new URL(myUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inStream = urlConnection.getInputStream();

                InputStream stream = downloadUrl(myUrl);

                //create a pull parser:
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser parseXpp = factory.newPullParser();
                parseXpp.setInput( inStream  , "UTF-8");  //inStream comes from line 46


//                XmlPullParser parseXpp = this.parse(stream);

                //now loop over the XML:
                while(parseXpp.getEventType() != XmlPullParser.END_DOCUMENT)
                {
                    if(parseXpp.getEventType() == XmlPullParser.START_TAG)
                    {
                        String tagName = parseXpp.getName(); //get the name of the starting tag: <tagName>
                        if(tagName.equals("city"))
                        {
                            String parameter = parseXpp.getAttributeValue(null, "city");
                            Log.e("AsyncTask", "Found city temperature: "+ parameter);
                            publishProgress(1); //tell android to call onProgressUpdate with 1 as parameter
                        }


                        else if(tagName.equals("temperature"))
                        {
                            currentTemerature = parseXpp.getAttributeValue(null, "value");
                            Log.e("AsyncTask", "Found currentTemerature: "+ currentTemerature);
                            publishProgress(25);

                            minTemerature = parseXpp.getAttributeValue(null, "min");
                            Log.e("AsyncTask", "Found minTemerature: "+ minTemerature);
                            publishProgress(50);

                            maxTemerature = parseXpp.getAttributeValue(null, "max");
                            Log.e("AsyncTask", "Found maxTemerature: "+ maxTemerature);
                            publishProgress(75); //tell android to call onProgressUpdate with 2 as parameter
                        }

                        if(tagName.equals("weather"))
                        {
                            iconName = parseXpp.getAttributeValue(null, "icon");
                            Log.e("AsyncTask", "Found iconName: "+ iconName);
                            publishProgress(1); //tell android to call onProgressUpdate with 1 as parameter
                        }

                        else if(tagName.equals("Temperature")) {
                            parseXpp.next(); //move to the text between opening and closing tags:
                            String temp = parseXpp.getText();
                            publishProgress(3); //tell android to call onProgressUpdate with 3 as parameter
                        }
                    }

                    parseXpp.next(); //advance to next XML event
                }

                //End of XML reading

                //Start of JSON reading of UV factor:

                //create the network connection:
                URL UVurl = new URL("http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389");
                HttpURLConnection UVConnection = (HttpURLConnection) UVurl.openConnection();
                inStream = UVConnection.getInputStream();

                //create a JSON object from the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                String result = sb.toString();

                //now a JSON table:
                JSONObject jObject = new JSONObject(result);
                double aDouble = jObject.getDouble("value");
                uvRatingText = aDouble+"";
                Log.i("UV is:", ""+ aDouble);
                Log.e("UV is:", ""+ uvRatingText);
                //END of UV rating

                //connecting or searching through file to get weather image
                if(fileExistance(iconName + ".png")){
                    Log.i(ACTIVITY_NAME, "Looking for file"+iconName + ".png");
                    Log.i(ACTIVITY_NAME, "Weather image exists, found locally");
                    File file = getBaseContext().getFileStreamPath(iconName + ".png");
                    FileInputStream fis = null;
                    // FileInputStream fis = new FileInputStream(file);
                    try {    fis = openFileInput(iconName+".png");   }
                    catch (FileNotFoundException e) {    e.printStackTrace();  }
                    currentWeatherIcon = BitmapFactory.decodeStream(fis);
                }else {
                    Log.i(ACTIVITY_NAME, "Looking for file"+iconName + ".png");
                    Log.i(ACTIVITY_NAME, "Weather image does not exist, need to download");

                    URL imageUrl = new URL("http://openweathermap.org/img/w/" + iconName + ".png");
                    HttpURLConnection  connection = (HttpURLConnection) imageUrl.openConnection();
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        currentWeatherIcon = BitmapFactory.decodeStream(connection.getInputStream());
                    }
                    FileOutputStream imageOutput = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                    currentWeatherIcon.compress(Bitmap.CompressFormat.PNG, 80, imageOutput);
                    imageOutput.flush();
                    imageOutput.close();
                    connection.disconnect();
                }
                publishProgress(100);
                Thread.sleep(2000); //pause for 2000 milliseconds to watch the progress bar spin
            }catch (Exception ex)
            {
                Log.e("Crash!!", ex.toString() );
            }
            //return type 3, which is String:
            return "Finished task";
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i("AsyncTaskExample", "update:" + values[0]);
            messageBox.setText("At step:" + values[0]);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            //the parameter String s will be "Finished task" from line 27
            messageBox.setText("Finished all tasks");
            currentTemperature.setText(currentTemerature + "℃");
            minWeather.setText(minTemerature + "℃");
            maxWeather.setText(maxTemerature + "℃");
            uvRating.setText(uvRatingText);
            weatherImage.setImageBitmap(currentWeatherIcon);
            progressBar.setVisibility(View.INVISIBLE);
            textView.setText("");
        }

        public XmlPullParser parse(InputStream in) throws XmlPullParserException, IOException {
            try {
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, "UTF-8");
                parser.nextTag();
                //return readFeed(parser);
                return parser;
            } finally {
                //in.close();
            }
        }
    }

    // Given a string representation of a URL, sets up a connection and gets
// an input stream.
    private InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        return conn.getInputStream();
    }
    public boolean fileExistance(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();   }
}
