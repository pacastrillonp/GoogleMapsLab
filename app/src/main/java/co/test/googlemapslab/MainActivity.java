package co.test.googlemapslab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LocationChangeModel.OnLocationChangeListener {
    private TextView longitude;
    private TextView latitude;

    private Intent startMapsActivityIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        longitude = findViewById(R.id.tv_longitude);
        latitude = findViewById(R.id.tv_latitude);
        Button getLocation = findViewById(R.id.bt_get_location);
        LocationChangeModel.getInstance().setListener(this);
        startMapsActivityIntent = new Intent(this, MapsActivity.class);
        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(startMapsActivityIntent);
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void locationChanged() {
        LocationDto locationDto = LocationChangeModel.getInstance().getState();
        longitude.setText(locationDto.getLongitude());
        latitude.setText(locationDto.getLatitude());
    }
}
