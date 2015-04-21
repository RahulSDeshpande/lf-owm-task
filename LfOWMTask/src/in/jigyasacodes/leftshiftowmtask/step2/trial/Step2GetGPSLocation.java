package in.jigyasacodes.leftshiftowmtask.step2.trial;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class Step2GetGPSLocation implements LocationListener {

	private Context ctx = null, baseCtx = null;

	private LocationManager locationManager = null;
	private LocationListener locationListener = null;

	private final long GPS_SEARCH_MIN_TIME = 100000;
	private final float GPS_SEARCH_MIN_DISTANCE = 0f;

	OnGPSLatLngCompleteListener onGPSLatLngCompleteListener;
	OnGPSLocationCompleteListener onGPSLocationCompleteListener;

	public Step2GetGPSLocation(OnGPSLatLngCompleteListener this1,
			OnGPSLocationCompleteListener this2) {

		this.onGPSLatLngCompleteListener = this1;
		this.onGPSLocationCompleteListener = this2;
	}

	public void setupGPSVarsAndCall(Context ctx, Context baseCtx) {

		this.ctx = ctx;
		locationManager = (LocationManager) ctx
				.getSystemService(Context.LOCATION_SERVICE);
		// //Toast.makeText(ctx, locationManager.toString(),
		// Toast.LENGTH_LONG).show();

		if (getGPSStatus()) {

			Toast.makeText(ctx,
					"Please move your device to let GPS locate your device..",
					Toast.LENGTH_LONG).show();

			// locationListener = new MyLocationListener();

			locationListener = new Step2GetGPSLocation(onGPSLatLngCompleteListener,
					onGPSLocationCompleteListener);
			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, GPS_SEARCH_MIN_TIME,
					GPS_SEARCH_MIN_DISTANCE, locationListener);
			// locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER,
			// locationListener, null);

		} else {

			showGPSDisabledAlertDialog("GPS Status",
					"GPS is disabled on your device !!");
		}

	}

	protected void showGPSDisabledAlertDialog(String strTitle, String strMessage) {

		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

		builder.setMessage(strMessage)
				.setCancelable(false)
				.setPositiveButton("Turn ON",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int id) {

								Intent myIntent = new Intent(
										Settings.ACTION_LOCATION_SOURCE_SETTINGS);
								ctx.startActivity(myIntent);

								dialog.cancel();
							}
						})
				.setNegativeButton("Leave",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int id) {

								dialog.cancel();
								Step2GPSLocationWeatherAct
										.setPBVisibility(View.GONE);

							}
						}).create().show();

		// AlertDialog alert = builder.create();
		// alert.show();
	}

	// Check GPS' STATUS - Disabled/Enabled ?
	private Boolean getGPSStatus() {

		// Not necessary --
		// ContentResolver contentResolver = ctx.getContentResolver();

		boolean boolGPSStatus = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);

		if (boolGPSStatus) {

			return true;

		} else {

			return false;
		}
	}

	public void stopGPSLocationListener() {

		if (locationManager != null) {
			locationManager.removeUpdates(Step2GetGPSLocation.this);
			Log.e("GPS", "GPS stopped");
		}
	}

	@Override
	public void onLocationChanged(Location location) {

		// //onGPSLatLngCompleteListener.onGPSLatLngComplete(location,locationListener,onGPSLatLngCompleteListener);

		// //locationManager.removeGpsStatusListener((Listener)
		// locationListener);

		Log.e("onLocationChanged",
				location.getLatitude() + " & " + location.getLongitude()
						+ "--------------------------------");

		onGPSLatLngCompleteListener.onGPSLatLngComplete(location);
		// new GPSLocationWeather().requestGPSStop();

		// Shooting fkn NULL_POINTER_EXCEPTION
		// locationManager.removeUpdates(GetGPSLocation.this);

		/**
		 * final long[] lngLat = { (long) location.getLongitude(), (long)
		 * location.getLatitude() };
		 * 
		 * //Toast.makeText(ctx, "lat:", Toast.LENGTH_LONG).show();
		 * 
		 * // lngLat[0] = (long) location.getLongitude(); // lngLat[1] = (long)
		 * location.getLatitude();
		 * 
		 * // Reverse Geocoding // GPSLocationWeather.this.getBaseContext();
		 * Geocoder geocoder = new Geocoder(baseCtx, Locale.getDefault());
		 * List<Address> addresses = null;
		 * 
		 * try {
		 * 
		 * addresses = geocoder.getFromLocation(location.getLatitude(),
		 * location.getLongitude(), 4);
		 * 
		 * Toast.makeText(ctx, addresses.get(0).toString(), Toast.LENGTH_LONG)
		 * .show();
		 * 
		 * if (addresses.size() > 0) {
		 * 
		 * System.out.println(addresses.get(0).getLocality()); // cityName =
		 * addresses.get(0).getLocality();
		 * 
		 * } else {
		 * 
		 * Toast.makeText(ctx,
		 * "Current City location name could not be fetched..",
		 * Toast.LENGTH_LONG).show(); }
		 * 
		 * } catch (IOException e) {
		 * 
		 * e.printStackTrace(); }
		 * 
		 * locationManager.removeGpsStatusListener((Listener) locationListener);
		 * onGPSLocationCompleteListener.onGPSLocationComplete(lngLat,
		 * (String[]) addresses.toArray());
		 **/

	}

	@Override
	public void onProviderDisabled(String provider) {

		Log.e("GetLocation", "GPS is Disabled -> " + provider);
	}

	@Override
	public void onProviderEnabled(String provider) {

		Log.e("GetLocation", "GPS is Enabled -> " + provider);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

		Log.e("GetLocation", "GPS' Status -> " + status);
	}

	public interface OnGPSLatLngCompleteListener {

		public void onGPSLatLngComplete(Location loc);
	}

	/*
	 * public interface OnGPSLatLngCompleteListener {
	 * 
	 * public void onGPSLatLngComplete(Location loc, LocationListener
	 * locationListener, OnGPSLatLngCompleteListener
	 * onGPSLatLngCompleteListener); }
	 */

	public interface OnGPSLocationCompleteListener {

		public void onGPSLocationComplete(final double lngLat[],
				final List<Address> listAddrCityNames);

	}

}