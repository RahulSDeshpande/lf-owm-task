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
	private Boolean getGPSStatus(LocationManager locationManager) {

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

	private boolean isNetworkConnectionAvailable() {

		boolean haveConnectedWifi = false;
		boolean haveConnectedMobile = false;

		ConnectivityManager cm = (ConnectivityManager) getContext()
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