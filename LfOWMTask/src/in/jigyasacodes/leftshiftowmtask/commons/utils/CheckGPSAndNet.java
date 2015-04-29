package in.jigyasacodes.leftshiftowmtask.commons.utils;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckGPSAndNet {

	private Context ctx;

	public CheckGPSAndNet(Context ctx) {

		this.setContext(ctx);
	}

	// Check GPS' STATUS - Disabled/Enabled ?
	public Boolean getGPSStatus(Context ctxx, LocationManager locationManager) {

		// Not necessary --
		// ContentResolver contentResolver = ctx.getContentResolver();

		if (locationManager == null) {

			locationManager = (LocationManager) ctxx
					.getSystemService(Context.LOCATION_SERVICE);
		}

		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

		/*
		 * if (boolGPSStatus) {
		 * 
		 * return true;
		 * 
		 * } else {
		 * 
		 * return false; }
		 */
	}

	public boolean isNetworkConnectionAvailable(Context ctxx) {

		boolean haveConnectedWifi = false;
		boolean haveConnectedMobile = false;

		ConnectivityManager cm = (ConnectivityManager) ctxx
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo[] netInfo = cm.getAllNetworkInfo();

		for (NetworkInfo ni : netInfo) {

			if (ni.getTypeName().equalsIgnoreCase("WIFI"))

				if (ni.isConnected())

					haveConnectedWifi = true;

			if (ni.getTypeName().equalsIgnoreCase("MOBILE"))

				if (ni.isConnected())

					haveConnectedMobile = true;
		}

		return haveConnectedWifi || haveConnectedMobile;
	}

	public Context getContext() {
		return ctx;
	}

	public void setContext(Context ctx) {
		this.ctx = ctx;
	}
}