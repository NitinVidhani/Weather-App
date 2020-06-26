package application.example.weather4u;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherActivity extends AppCompatActivity {

    private static final String baseURL = "https://api.openweathermap.org/data/2.5/weather?q=";
    private static final String appid = "ab23f60b475fc9fcebeedb5e86e07805";
    private static final String imageURL = "https://openweathermap.org/img/wn/";
    private String cityName;

    private ConstraintLayout weatherBackground;
    private TextView textViewCity;
    private TextView textViewTemp;
    private TextView textViewHumidity, textViewWindSpeed, textViewMain;

    private ImageView imageViewIcon1, imageViewIcon2;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        Intent intent = getIntent();
        cityName = intent.getStringExtra("city");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        weatherBackground = findViewById(R.id.weather_background);
        textViewCity = findViewById(R.id.text_view_city);
        textViewTemp = findViewById(R.id.text_view_temp);
        textViewHumidity = findViewById(R.id.text_view_humidity);
        textViewWindSpeed = findViewById(R.id.text_view_windspeed);
        textViewMain = findViewById(R.id.text_view_main);
        imageViewIcon1 = findViewById(R.id.imageView1);
        imageViewIcon2 = findViewById(R.id.imageView2);

        Log.e("TAG", "onCreate: " + cityName);

        String myURL = baseURL + cityName + "&appid=" + appid;

        Log.e("TAG", "onCreate: " + myURL);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, myURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.e("TAG", "onResponse: " + response.getString("cod"));
                    
                    double lat = response.getJSONObject("coord").getDouble("lat");
                    double lon = response.getJSONObject("coord").getDouble("lon");

                    JSONObject obj = (JSONObject) response.getJSONArray("weather").get(0);
                    String main = obj.getString("main");
                    textViewMain.setText(main);

                    String icon = obj.getString("icon");
                    if (icon.contains("d")) {
                        weatherBackground.setBackgroundResource(R.drawable.day);
                    } else {
                        textViewCity.setTextColor(Color.WHITE);
                        weatherBackground.setBackgroundResource(R.drawable.night);
                    }

                    String iconURL = imageURL + icon + "@4x.png";
                    Picasso.get().load(iconURL).fit().into(imageViewIcon1);
                    Picasso.get().load(iconURL).fit().into(imageViewIcon2);
                    progressDialog.dismiss();

                    String humidity = response.getJSONObject("main").getString("humidity");
                    textViewHumidity.setText("Humidity: " + humidity);
                    Log.e("TAG", "onResponse: " + humidity);

                    String city = response.getString("name");
                    textViewCity.setText(city);

                    double temp = response.getJSONObject("main").getDouble("temp");
                    double temperature = temp - 273.15;
                    textViewTemp.setText(Math.round(temperature) + "");

                    double windSpeed = response.getJSONObject("wind").getDouble("speed");
                    double speed = windSpeed * 3.6;
                    textViewWindSpeed.setText("Wind Speed: " + Math.round(speed) + " km/h");

                } catch (JSONException e) {
                    Log.e("TAG", "onResponse: " + e.getMessage());
                    Toast.makeText(WeatherActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                finish();
                progressDialog.dismiss();
                Toast.makeText(WeatherActivity.this, "City not found", Toast.LENGTH_SHORT).show();
            }
        });

        Log.e("TAG", "onCreate: before adding");

        MySingleton.getInstance(this).addToQueue(jsonObjectRequest);

        Log.e("TAG", "onCreate: after adding");

    }
}
