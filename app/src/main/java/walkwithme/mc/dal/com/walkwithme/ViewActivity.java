package walkwithme.mc.dal.com.walkwithme;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import walkwithme.mc.dal.com.walkwithme.ActivityJsonObj.ViewActivityJsonObj;

public class ViewActivity extends AppCompatActivity implements OnMapReadyCallback {

    //Map Variable
    private GoogleMap mMap;

    Double eventCoordinateLang = 44.637386;
    Double eventCoordinateLong = -63.587347;

    LatLng eventLoc = null;

    //Initializing views
    CarouselView carouselView;
    TextView location;
    TextView date, temperature;
    TextView description;
    TextView eventNameView;
    ImageView icon;

    // Variables for weather
    private static final String URL_START = "https://api.openweathermap.org/data/2.5/forecast?lat=";
    private static final String URL_MID = "&lon=";
    private static final String URL_END = "&appid=c7efb41660082306a3b0fe9ee27f770d";
    String latitude;
    String longitude;
    String weatherDate;

    //Fetching Images from the Drawable directory
    int[] sampleImages = {R.drawable.walk_2, R.drawable.walk_1, R.drawable.walk_3, R.drawable.walk_4};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        // Code Snippet From GOOGLE MAP API Doc
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Intent object passed from Home Activity
        Intent intent = getIntent();

        //Fetching the resources passed in the  Intent from Home Activity
        //event = viewActivityJsonObj.saveData(intent.getStringExtra("event"));
        //pull data from the intent/bundle
        Bundle dataBundle = getIntent().getBundleExtra("bundle");

        //pull data from the bundle which allows for default values.
        final Integer eventId = dataBundle.getInt("eventID",-1);
        String eventName = dataBundle.getString("eventName", "N/A");
        String eventDatetime = dataBundle.getString("eventDatetime", "N/A");
        String eventLocation = dataBundle.getString("eventLocation", "N/A");
        String eventImageURL = dataBundle.getString("eventImageURL", "N/A");
        Double eventCoordinateLang = dataBundle.getDouble("eventCoordinateLang", 0.0);
        Double eventCoordinateLong = dataBundle.getDouble("eventCoordinateLong", 0.0);
        String eventDescription = dataBundle.getString("eventDescription", "N/A");
        String eventWeather = dataBundle.getString("eventWeather", "N/A");

        //Fetching required data from JSON Object
        location = (TextView) findViewById(R.id.location);
        date = (TextView) findViewById(R.id.date);
        description = (TextView) findViewById(R.id.description);
        eventNameView = (TextView) findViewById(R.id.eventNameView);
        temperature = (TextView) findViewById(R.id.temperature);
        icon = (ImageView) findViewById(R.id.icon);
        // Setting the JSON data in the Activity
        location.setText(eventLocation);
        date.setText(eventDatetime);
        description.setText(eventDescription);
        eventNameView.setText(eventName);

        //Creating the carousel View for the event images
        carouselView = (CarouselView) findViewById(R.id.carousel_view);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

        // Setting the location coordinates in google Map
        eventLoc = new LatLng(eventCoordinateLang, eventCoordinateLong);
        String strDate = eventDatetime.substring(0,11);
        Log.i("Deep",strDate);
        DateFormat format = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        DateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
        String extractedDate = "";
        try {
            Date date = format.parse(strDate);
            extractedDate = newFormat.format(date);
        } catch (ParseException e) {
            Log.i("Deep", "exception");
            e.printStackTrace();
        }

        Log.i("Deep", extractedDate);

        //weatherDate = strDate.substring(0,10);

        // latitude = String.valueOf(eventCoordinateLang);
        // longitude = String.valueOf(eventCoordinateLong);
        // weatherDate = extractedDate;

        latitude = String.valueOf(19.0760);
        longitude = String.valueOf(-72.8777);
        weatherDate = "2019-03-26";

        String requestURL = URL_START + latitude + URL_MID + longitude + URL_END;
        new getWeather().execute(requestURL);
    }

    // Code to swipe the images in the carousel
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // Add a marker at the Event Location


        // Setting the location coordinates in google Map
        eventLoc = new LatLng(eventCoordinateLang, eventCoordinateLong);

        // Adding Marker text in Google Map
        mMap.addMarker(new MarkerOptions().position(eventLoc).title("Your Meeting LOcation"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(eventLoc));

    }
    public class getWeather extends AsyncTask<String, String, String> {
        public static final String TAG = "Deep";

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder builder = new StringBuilder();

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();

                urlConnect.setRequestMethod("GET");

                InputStreamReader streamReader = new InputStreamReader(urlConnect.getInputStream());

                BufferedReader bufferedReader = new BufferedReader(streamReader);

                String inputStr = "";


                while ((inputStr = bufferedReader.readLine()) != null) {

                    builder.append(inputStr);
                }
                urlConnect.disconnect();
                streamReader.close();

            } catch (Exception e) {

                e.printStackTrace();
            }
            String builderStr = builder.toString();
            return builderStr;
        }
        protected void onPostExecute(String str) {

            super.onPostExecute(str);

            try {

                double k = 273.15;


                JSONObject Obj = new JSONObject(str);

                Log.w(TAG, Obj.toString());

                JSONArray one = Obj.getJSONArray("list");


                for (int i = 0; i < one.length(); i++) {

                    Log.i(TAG, String.valueOf(i));

                    JSONObject c = one.getJSONObject(i);
                    JSONObject main = c.getJSONObject("main");
                    JSONArray weather = c.getJSONArray("weather");
                    JSONObject weatherzero = weather.getJSONObject(0);

                    String description = weatherzero.getString("main");

                    Log.i(TAG, description);

                    String sysJson = c.getString("dt_txt");
                    if (sysJson.substring(0, 10).equals(weatherDate)) {
                        temperature.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 50);
                        double temp = main.getDouble("temp");
                        int t = (int) (temp - k);
                        temperature.setText(String.valueOf(t) + " " + "°C");

                        switch (description) {
                            case "Rain":
                                icon.setBackgroundResource(R.drawable.rain);
                                break;
                            case "Clouds":
                                icon.setBackgroundResource(R.drawable.cloud);
                                break;
                            case "Snow":
                                icon.setBackgroundResource(R.drawable.snow);
                                break;
                            default:
                                icon.setBackgroundResource(R.drawable.clear);
                                break;
                        }

                        break;
                    }


                }

                //JSONObject zero = one.getJSONObject(0);


            } catch (JSONException e) {

                e.printStackTrace();
            }

        }

    }


}

