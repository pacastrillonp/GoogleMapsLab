package co.test.googlemapslab;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class MapsActivity extends FragmentActivity {

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1;

    int REQUEST_PLACE_PICKER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (Build.VERSION.SDK_INT >= 23) {
            CheckPermissions checkPermissions = new CheckPermissions(this, this);
            checkPermissions.verifyPermission();
        }

        if (checkPlayServices()) {
            checkOut();
        } else {
            Intent startMainActivityIntent = new Intent(this, MapsActivity.class);
            startActivity(startMainActivityIntent);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    private boolean checkPlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            return false;
        }
        return true;
    }


    public void checkOut() {
        try {
            PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
            Intent intent = intentBuilder.build(this);
            startActivityForResult(intent, REQUEST_PLACE_PICKER);
        } catch (GooglePlayServicesRepairableException e) {
            GoogleApiAvailability.getInstance().getErrorDialog(this, e.getConnectionStatusCode(),
                    REQUEST_PLACE_PICKER);
        } catch (GooglePlayServicesNotAvailableException e) {
            Toast.makeText(this, "Please install Google Play Services!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_PLACE_PICKER) {
            if (resultCode == Activity.RESULT_OK) {
                assert data != null;
                Place place = PlacePicker.getPlace(this, data);
                LocationDto locationDto = new LocationDto();
                locationDto.setLatitude(String.valueOf(place.getLatLng().latitude));
                locationDto.setLongitude(String.valueOf(place.getLatLng().longitude));
                LocationChangeModel.getInstance().changeLocation(locationDto);
                this.finish();
            } else if (resultCode == PlacePicker.RESULT_ERROR) {
                Toast.makeText(this, "Places API failure! Check that the API is enabled for your key",
                        Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
