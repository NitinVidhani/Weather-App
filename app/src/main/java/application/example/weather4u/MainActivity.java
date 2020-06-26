package application.example.weather4u;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText editTextCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextCity = findViewById(R.id.editText);

    }

    public void showWeather(View view) {
        String city = editTextCity.getText().toString();
        startActivity(new Intent(this, WeatherActivity.class).putExtra("city",city));
    }

}
