package in.jigyasacodes.leftshiftowmtask.step2.trial;

import in.jigyasacodes.leftshiftowmtask.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LocationClass extends Activity implements OnClickListener {

	OnGPSLocationCompleteListener1111 onGPSLocationCompleteListener;

	private LocationManager locationMangaer = null;
	private LocationListener locationListener = null;

	private Button btnGetLocation = null;
	private EditText editLocation = null;
	private ProgressBar pb = null;

	private static final String TAG = "Lf Task Step II - LocationClass";
	private Boolean gpsFlag = false;

	public LocationClass(OnGPSLocationCompleteListener1111 thiss) {

		this.onGPSLocationCompleteListener = thiss;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location_layout);

		// if you want to lock screen for always Portrait mode
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		pb = (ProgressBar) findViewById(R.id.progressBar1);
		pb.setVisibility(View.INVISIBLE);

		editLocation = (EditText) findViewById(R.id.editTextLocation);

		btnGetLocation = (Button) findViewById(R.id.btnLocation);
		btnGetLocation.setOnClickListener(this);

		locationMangaer = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

	}

	@Override
	public void onClick(View v) {
		gpsFlag = displayGpsStatus();
		if (gpsFlag) {

			Log.v(TAG, "onClick");

			editLocation.setText("Please!! move your device to"
					+ " see the changes in coordinates." + "\nWait..");

			pb.setVisibility(View.VISIBLE);
			locationListener = new MyLocationListener();

			locationMangaer.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0,
					locationListener);

		} else {
			alertbox("GPS Status", "GPS is disabled on your device !!");
		}

	}

	/*----Method to Check GPS is enable or disable ----- */
	private Boolean displayGpsStatus() {
		ContentResolver contentResolver = getBaseContext().getContentResolver();
		boolean gpsStatus = Settings.Secure.isLocationProviderEnabled(contentResolver,
				LocationManager.GPS_PROVIDER);
		if (gpsStatus) {
			return true;

		} else {
			return false;
		}
	}

	/*----------Method to create an AlertBox ------------- */
	protected void alertbox(String strTitle, String strMessage) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setMessage(strMessage).setCancelable(false)
				.setPositiveButton("Turn ON", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int id) {

						Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						startActivity(myIntent);

						dialog.cancel();
					}
				}).setNegativeButton("Leave", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int id) {

						dialog.cancel();
					}
				}).create().show();
		;
		// AlertDialog alert = builder.create();
		// alert.show();
	}

	/*----------Listener class to get coordinates ------------- */
	private class MyLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location loc) {

			editLocation.setText("");
			pb.setVisibility(View.INVISIBLE);
			Toast.makeText(getBaseContext(),
					"Location changed : Lat: " + loc.getLatitude() + " Lng: " + loc.getLongitude(),
					Toast.LENGTH_SHORT).show();
			String longitude = "Longitude: " + loc.getLongitude();
			Log.v(TAG, longitude);
			String latitude = "Latitude: " + loc.getLatitude();
			Log.v(TAG, latitude);

			/*----------to get City-Name from coordinates ------------- */
			String cityName = null;
			Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
			List<Address> addresses;
			try {
				addresses = gcd.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
				if (addresses.size() > 0)
					System.out.println(addresses.get(0).getLocality());
				cityName = addresses.get(0).getLocality();
			} catch (IOException e) {
				e.printStackTrace();
			}

			String s = longitude + "\n" + latitude + "\n\nMy Currrent City is: " + cityName;
			editLocation.setText(s);
		}

		@Override
		public void onProviderDisabled(String provider) {
			Log.d("Latitude", "disable");
		}

		@Override
		public void onProviderEnabled(String provider) {
			Log.d("Latitude", "enable");
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			Log.d("Latitude", "status");
		}
	}

	public interface OnGPSLocationCompleteListener1111 {

		public void onGPSLocationComplete(final long lngLat[], final String strCityName);

	}
}