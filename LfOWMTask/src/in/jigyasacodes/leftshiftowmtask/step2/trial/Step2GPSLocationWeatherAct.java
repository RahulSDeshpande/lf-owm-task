package in.jigyasacodes.leftshiftowmtask.step2.trial;

import in.jigyasacodes.leftshiftowmtask.R;
import in.jigyasacodes.leftshiftowmtask.commons.adapter.DailyForecastPageAdapter;
import in.jigyasacodes.leftshiftowmtask.commons.adapter.WeatherForecastAdapHelper;
import in.jigyasacodes.leftshiftowmtask.commons.util.CityWeatherForecastAsync;
import in.jigyasacodes.leftshiftowmtask.commons.util.CityWeatherForecastAsync.OnCityWeatherForecastRESTCompleteListener;
import in.jigyasacodes.leftshiftowmtask.commons.util.Constants;
import in.jigyasacodes.leftshiftowmtask.commons.util.JSONWeatherParser;
import in.jigyasacodes.leftshiftowmtask.step2.trial.Step2GetGPSLocation.OnGPSLatLngCompleteListener;
import in.jigyasacodes.leftshiftowmtask.step2.trial.Step2GetGPSLocation.OnGPSLocationCompleteListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Step2GPSLocationWeatherAct extends FragmentActivity implements
		OnGPSLocationCompleteListener, OnGPSLatLngCompleteListener,
		OnCityWeatherForecastRESTCompleteListener {

	TextView tvCityName, tvWeatherResponse;
	Button btnGetGPSLocation;

	static ProgressBar pbloading;

	Step2GetGPSLocation getGPSLocation = null;
	CityWeatherForecastAsync cityWeatherForecastAsync;

	// List<Address> listAddresses = null;

	private ViewPager viewPager;

	private static int OWM_WEATHER_FORECAST_CNT_cnt = 14;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.gps_location_weather_common_layout);

		/*
		 * Geocoder geocoder = new Geocoder(getApplicationContext(),
		 * Locale.getDefault()); Toast.makeText(this, geocoder.isPresent() + "",
		 * Toast.LENGTH_LONG) .show(); try { geocoder.getFromLocation(2.0022,
		 * 2.323232, 2); } catch (IOException e) { // TODO Auto-generated catch
		 * block e.printStackTrace();
		 * 
		 * }
		 */

		tvCityName = (TextView) findViewById(R.id.tvCityName);
		tvWeatherResponse = (TextView) findViewById(R.id.tvWeatherResponse);

		btnGetGPSLocation = (Button) findViewById(R.id.btnGetGPSLocation);

		pbloading = (ProgressBar) findViewById(R.id.pbLoading);

		viewPager = (ViewPager) findViewById(R.id.viewPager);

		btnGetGPSLocation.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				pbloading.setVisibility(View.VISIBLE);

				getGPSLocation = new Step2GetGPSLocation(
						Step2GPSLocationWeatherAct.this,
						Step2GPSLocationWeatherAct.this);

				// ///////////////////////////////////////////////////////////
				getGPSLocation.setupGPSVarsAndCall(
						Step2GPSLocationWeatherAct.this, getBaseContext());
				// ///////////////////////////////////////////////////////////

				/*
				 * cityWeatherForecastAsync = new CityWeatherForecastAsync(
				 * GPSLocationWeatherAct.this); fetchWeatherForecast("Pune");
				 */
			}
		});
	}

	public static void setPBVisibility(final int VISIBILITY) {

		pbloading.setVisibility(VISIBILITY);
	}

	public void requestGPSStop() {

		getGPSLocation.stopGPSLocationListener();
	}

	@Override
	public void onGPSLatLngComplete(Location location) {

		getGPSLocation.stopGPSLocationListener();

		if (location == null) {

			showGPSRetryAlertDialog(
					"GPS Response",
					"GPS could not fetch your current location co-ordinates - Lat-Lnt !!\n\nDo you want to RETRY ??");

		} else {

			Log.e("onLocationChanged 1", location.getLatitude() + " & "
					+ location.getLongitude()
					+ "--------------------------------");

			// /////////////////////////////////////////
			new ReverseGeocodeAsync().execute(location);
			// /////////////////////////////////////////
		}
	}

	protected void showGPSRetryAlertDialog(final String strTitle,
			final String strMessage) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setMessage(strMessage)
				.setCancelable(false)
				.setPositiveButton("RETRY",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int id) {

								// ////////////////////////////////
								getGPSLocation.setupGPSVarsAndCall(
										Step2GPSLocationWeatherAct.this,
										getBaseContext());
								// ////////////////////////////////
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

	private class ReverseGeocodeAsync extends
			AsyncTask<Location, Void, List<Address>> {

		double[] lngLat;
		Geocoder geocoder = null;

		private static final int GEOCODER_MAX_RESULTS = 4;

		@Override
		protected void onPreExecute() {

			Log.e("ReverseGeocodeAsync",
					"onPreExecute()--------------------------------");

			super.onPreExecute();

			// Toast.makeText(ctx, "lat:", Toast.LENGTH_LONG).show();

			// lngLat[0] = (long) location.getLongitude(); // lngLat[1] = (long)
			// location.getLatitude();

			// Reverse Geocoding // GPSLocationWeather.this.getBaseContext();
			geocoder = new Geocoder(getBaseContext(), Locale.getDefault());

		}

		@Override
		protected List<Address> doInBackground(Location... locations) {

			Log.e("ReverseGeocodeAsync",
					"doInBackground() 1 --------------------------------");

			// Looper.prepare();

			List<Address> listAddresses = null;

			final double[] lngLat = { locations[0].getLatitude(),
					locations[0].getLongitude() };
			this.lngLat = lngLat;

			Log.e("ReverseGeocodeAsync",
					"doInBackground() 2 --------------------------------");

			try {

				Log.e("ReverseGeocodeAsync",
						"doInBackground() 2.1 --------" + lngLat[0] + " & "
								+ lngLat[1] + "------" + Geocoder.isPresent());

				listAddresses = geocoder.getFromLocation(lngLat[0], lngLat[1],
						GEOCODER_MAX_RESULTS);

				Log.e("ReverseGeocodeAsync",
						"doInBackground() 3 --------------------------------");

				Toast.makeText(
						Step2GPSLocationWeatherAct.this,
						"Curent City Locality:\n\n"
								+ listAddresses.get(0).toString(),
						Toast.LENGTH_SHORT).show();

				if (listAddresses.size() > 0) {

					Log.e("ReverseGeocodeAsync",
							"doInBackground() 4 --------------------------------");

					// /System.out.println(listAddresses.get(0).getLocality());
					Log.e("CITY LOCALITY--------", listAddresses.get(0)
							.getLocality() + "-----------------------");

				} else {

					Log.e("ReverseGeocodeAsync",
							"doInBackground() 5 --------------------------------");

					Toast.makeText(
							Step2GPSLocationWeatherAct.this,
							"Current City location name could not be fetched..",
							Toast.LENGTH_LONG).show();
				}

			} catch (IOException e) {

				Log.e("ReverseGeocodeAsync",
						"doInBackground() 6 --------------------------------");

				e.printStackTrace();
			}

			Log.e("ReverseGeocodeAsync",
					"doInBackground() 7 --------------------------------");
			return listAddresses;
		}

		@Override
		protected void onPostExecute(List<Address> listAddresses) {

			Log.e("ReverseGeocodeAsync",
					"onPostExecute() 1 --------------------------------");

			super.onPostExecute(listAddresses);

			cityWeatherForecastAsync = new CityWeatherForecastAsync(
					Step2GPSLocationWeatherAct.this);

			if (listAddresses != null) {

				Log.e("ReverseGeocodeAsync",
						"onPostExecute() 2 --------------------------------");

				// // Impossible to cast shit..
				// //final String[] strCityNames = (String[])
				// addresses.toArray();
				Toast.makeText(
						Step2GPSLocationWeatherAct.this,
						"City name: "
								+ listAddresses.get(0).getLocality().toString(),
						Toast.LENGTH_LONG).show();
				fetchWeatherForecast(listAddresses);

				Log.e("ReverseGeocodeAsync",
						"onPostExecute() 3 --------------------------------");

			} else {

				Log.e("ReverseGeocodeAsync",
						"onPostExecute() 4 --------------------------------");

				Toast.makeText(
						Step2GPSLocationWeatherAct.this,
						"Seemsssssss, GPS could not get current City location..",
						Toast.LENGTH_LONG).show();

				Toast.makeText(Step2GPSLocationWeatherAct.this,
						"So fetching Weather Forecast by Lng-Lat",
						Toast.LENGTH_LONG).show();

				Toast.makeText(Step2GPSLocationWeatherAct.this,
						"Lng: " + lngLat[0] + "\nLat: " + lngLat[1],
						Toast.LENGTH_LONG).show();

				fetchWeatherForecast(lngLat);

				Log.e("ReverseGeocodeAsync",
						"onPostExecute() 5 --------------------------------");
			}
			// //Looper.myLooper().quit();

			Log.e("ReverseGeocodeAsync",
					"onPostExecute() 6 --------------------------------");
		}
	}

	@Override
	public void onGPSLocationComplete(final double[] lngLat,
			final List<Address> listAddrCityNames) {

		// cityWeatherForecastAsync = new CityWeatherForecastAsync(this);
		// /cityWeatherForecastAsync = new CityWeatherForecastAsync(this);

		// for (int i = 0; i < strCityNames.length; ++i) {

		// if (strCityNames[i] == null) {
		// }
		// }

		if (listAddrCityNames != null) {

			fetchWeatherForecast(listAddrCityNames);

		} else {

			fetchWeatherForecast(lngLat);

			Toast.makeText(this,
					"Seem, GPS could not get current City location..",
					Toast.LENGTH_LONG).show();

			Toast.makeText(this, "So fetching Weather Forecast by Lng-Lat",
					Toast.LENGTH_LONG).show();

			Toast.makeText(this, "Lng: " + lngLat[0] + "\nLat: " + lngLat[1],
					Toast.LENGTH_LONG).show();
		}
	}

	// Just in case if we need to call it directly by CITY NAME (String)
	private void fetchWeatherForecast(final String strCityName) {

		// //////////////////// IMP //////////////////////////////
		// For the time being, even if we get multiple addresses,
		// I am going to use the very first one -> listAddrCityNames.get(0)

		tvCityName.setText(strCityName);

		final String OWM_FORECAST_REST_URL = Constants.OWM_BASE_URL
				+ Constants.OWM_FORECAST_URL + "/"
				+ Constants.OWM_FORECAST_DAILY_daily_URL
				+ Constants.OWM_CITY_q_URL + strCityName + "&"
				+ Constants.OWM_DAYS_cnt_URL + OWM_WEATHER_FORECAST_CNT_cnt
				+ Constants.OWM_RESPONSE_MODE_mode_URL + "json"
				+ Constants.OWM_RSD_APPID_URL;

		Toast.makeText(this, OWM_FORECAST_REST_URL, Toast.LENGTH_LONG).show();

		// ///////////////////////////////////////////////////////////
		cityWeatherForecastAsync.execute(OWM_FORECAST_REST_URL);
		// ///////////////////////////////////////////////////////////
	}

	private void fetchWeatherForecast(final List<Address> listAddrCityNames) {

		// //////////////////// IMP //////////////////////////////
		// For the time being, even if we get multiple addresses,
		// I am going to use the very first one -> listAddrCityNames.get(0)

		tvCityName.setText(listAddrCityNames.get(0).getLocality().toString());

		final String OWM_FORECAST_REST_URL = Constants.OWM_BASE_URL
				+ Constants.OWM_FORECAST_URL + "/"
				+ Constants.OWM_FORECAST_DAILY_daily_URL
				+ Constants.OWM_CITY_q_URL
				+ listAddrCityNames.get(0).getLocality() + "&"
				+ Constants.OWM_DAYS_cnt_URL + OWM_WEATHER_FORECAST_CNT_cnt
				+ Constants.OWM_RESPONSE_MODE_mode_URL + "json"
				+ Constants.OWM_RSD_APPID_URL;

		Toast.makeText(this, OWM_FORECAST_REST_URL, Toast.LENGTH_LONG).show();

		// ///////////////////////////////////////////////////////////
		cityWeatherForecastAsync.execute(OWM_FORECAST_REST_URL);
		// ///////////////////////////////////////////////////////////
	}

	private void fetchWeatherForecast(double[] lngLat) {

		tvCityName.setTextSize(16);
		tvCityName.setHint("City name could not be fetched..");

		final String OWM_FORECAST_REST_URL = Constants.OWM_BASE_URL
				+ Constants.OWM_FORECAST_URL + "/"
				+ Constants.OWM_FORECAST_DAILY_daily_URL
				+ Constants.OWM_LATITUDE_lat_URL + lngLat[0]
				+ Constants.OWM_LATITUDE_lat_URL + lngLat[1] + "&"
				+ Constants.OWM_DAYS_cnt_URL + OWM_WEATHER_FORECAST_CNT_cnt
				+ Constants.OWM_RESPONSE_MODE_mode_URL + "json"
				+ Constants.OWM_RSD_APPID_URL;

		Toast.makeText(this, OWM_FORECAST_REST_URL, Toast.LENGTH_LONG).show();

		// ///////////////////////////////////////////////////////////
		cityWeatherForecastAsync.execute(OWM_FORECAST_REST_URL);
		// ///////////////////////////////////////////////////////////

	}

	@Override
	public void onCityWeatherForecastRESTComplete(final JSONObject jsonObj)
			throws JSONException {

		// //////////////////////////////////
		setupAndSetViewPagerAdapter(jsonObj);
		// //////////////////////////////////
	}

	private void setupAndSetViewPagerAdapter(JSONObject jsonObj) {

		WeatherForecastAdapHelper weatherForecastAdapHelper = new WeatherForecastAdapHelper();

		try {

			weatherForecastAdapHelper = JSONWeatherParser
					.getWeatherForecast(jsonObj);
			System.out.println("Weather [" + weatherForecastAdapHelper + "]"); // ////////

		} catch (JSONException e) {

			e.printStackTrace();
		}

		// Toast.makeText(this, jsonObj.toString(), Toast.LENGTH_LONG).show();

		pbloading.setVisibility(View.GONE);

		// tvWeatherResponse.setVisibility(View.VISIBLE);
		// tvWeatherResponse.setText(jsonObj.toString(4));

		DailyForecastPageAdapter adapter = new DailyForecastPageAdapter(
				OWM_WEATHER_FORECAST_CNT_cnt, getSupportFragmentManager(),
				weatherForecastAdapHelper);

		viewPager.setAdapter(adapter);

	}

}