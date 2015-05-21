package com.example.gpsapp;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText txt1;
	LocationManager locationManager;
	LocationListener locationListener;
	private Button btn1, btn2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		txt1 = (EditText) findViewById(R.id.editText1);
		btn1 = (Button) findViewById(R.id.button1);
		btn2 = (Button) findViewById(R.id.button2);
		locationManager = (LocationManager) this
				.getSystemService(LOCATION_SERVICE);
		locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {

				Geocoder geocoder;
				List<Address> addresses = null;
				geocoder = new Geocoder(getBaseContext(), Locale.getDefault());

				try {
					addresses = geocoder.getFromLocation(
							location.getLatitude(), location.getLongitude(), 1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (addresses != null) {
					String address = addresses.get(0).getAddressLine(0);
					String city = addresses.get(0).getLocality();
					String state = addresses.get(0).getAdminArea();
					String country = addresses.get(0).getCountryName();
					String postalCode = addresses.get(0).getPostalCode();
					String knownName = addresses.get(0).getFeatureName();
					String result = address + ", " + ", " + city + ", " + state
							+ ", " + country + ", " + postalCode + ", "
							+ knownName;
					txt1.setText(result);
					Toast.makeText(MainActivity.this, "New Location detected --> " + result,
							Toast.LENGTH_SHORT).show();
				}
				else{
					Toast.makeText(MainActivity.this, "Something went fishy :(",
							Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this,
						"GPS/Use Wireless network is not enabled",
						Toast.LENGTH_SHORT).show();
			}
		};

		btn1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				locationManager.requestLocationUpdates(
						LocationManager.GPS_PROVIDER, 0, 0, locationListener);

			}
		});

		btn2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				locationManager.requestLocationUpdates(
						LocationManager.NETWORK_PROVIDER, 0, 0,
						locationListener);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
