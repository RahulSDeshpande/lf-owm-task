package in.jigyasacodes.leftshiftowmtask.step2.trial;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import in.jigyasacodes.leftshiftowmtask.R;
import in.jigyasacodes.leftshiftowmtask.step1.utils.CityWeatherForecastAsync;
import in.jigyasacodes.leftshiftowmtask.step1.utils.CityWeatherForecastAsync.OnCityWeatherForecastRESTCompleteListener;
import in.jigyasacodes.leftshiftowmtask.step1.utils.Constants;
import in.jigyasacodes.leftshiftowmtask.step2.trial.GetGPSLocation.OnGPSLatLngCompleteListener;
import in.jigyasacodes.leftshiftowmtask.step2.trial.GetGPSLocation.OnGPSLocationCompleteListener;
import in.jigyasacodes.leftshiftowmtask.step2.trial.adapter.DailyForecastPageAdapter;
import in.jigyasacodes.leftshiftowmtask.step2.trial.adapter.WeatherForecastAdapHelper;
import in.jigyasacodes.leftshiftowmtask.step2.trial.util.JSONWeatherParser;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class GPSLocationWeatherAct extends FragmentActivity implements
		OnGPSLocationCompleteListener, OnGPSLatLngCompleteListener,
		OnCityWeatherForecastRESTCompleteListener {

	TextView tvCityName, tvWeatherResponse;
	Button btnGetGPSLocation;

	static ProgressBar pbloading;

	GetGPSLocation getGPSLocation = null;
	CityWeatherForecastAsync cityWeatherForecastAsync;

	List<Address> addresses = null;

	private ViewPager viewPager;

	private static int OWM_WEATHER_FORECAST_CNT_cnt = 14;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.gps_location_weather_layout);

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

				// //getGPSLocation = new
				// GetGPSLocation(GPSLocationWeatherAct.this,
				// //GPSLocationWeatherAct.this);

				// //getGPSLocation.setupGPSVars(GPSLocationWeatherAct.this,
				// //getBaseContext());

				cityWeatherForecastAsync = new CityWeatherForecastAsync(
						GPSLocationWeatherAct.this);
				fetchWeatherForecast("Pune");

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

		final long[] lngLat = { (long) location.getLongitude(),
				(long) location.getLatitude() };

		// Toast.makeText(ctx, "lat:", Toast.LENGTH_LONG).show();

		// lngLat[0] = (long) location.getLongitude(); // lngLat[1] = (long)
		location.getLatitude();

		// Reverse Geocoding // GPSLocationWeather.this.getBaseContext();
		Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
		List<Address> addresses = null;

		try {

			addresses = geocoder.getFromLocation(location.getLatitude(),
					location.getLongitude(), 4);

			Toast.makeText(this, addresses.get(0).toString(), Toast.LENGTH_LONG)
					.show();

			if (addresses.size() > 0) {

				System.out.println(addresses.get(0).getLocality()); // cityName
																	// =
				addresses.get(0).getLocality();

			} else {

				Toast.makeText(this,
						"Current City location name could not be fetched..",
						Toast.LENGTH_LONG).show();
			}

		} catch (IOException e) {

			e.printStackTrace();
		}

		// //locationMangaer.removeGpsStatusListener((Listener)
		// locationListener);
		// new GPSLocationWeather().onGPSLocationComplete(lngLat,
		// (String[]) addresses.toArray());

		// //final String[] strCityNames = (String[]) addresses.toArray();

		cityWeatherForecastAsync = new CityWeatherForecastAsync(this);

		if (addresses != null) {

			// // Impossible to cast shit..
			// //final String[] strCityNames = (String[]) addresses.toArray();
			Toast.makeText(this,
					"City name: " + addresses.get(0).getLocality().toString(),
					Toast.LENGTH_LONG).show();
			fetchWeatherForecast(addresses);

		} else {

			Toast.makeText(this,
					"Seem, GPS could not get current City location..",
					Toast.LENGTH_LONG).show();

			Toast.makeText(this, "So fetching Weather Forecast by Lng-Lat",
					Toast.LENGTH_LONG).show();

			Toast.makeText(this, "Lng: " + lngLat[0] + "\nLat: " + lngLat[1],
					Toast.LENGTH_LONG).show();

			fetchWeatherForecast(lngLat);

		}
	}

	@Override
	public void onGPSLocationComplete(final long[] lngLat,
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

	private void fetchWeatherForecast(final String strCityName) {

		// //////////////////// IMP //////////////////////////////
		// For the time being, even if we get multiple addresses,
		// I am going to use the first very one -> strCityNames[0]

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
		// I am going to use the first very one -> strCityNames[0]

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

	private void fetchWeatherForecast(long[] lngLat) {

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
	public void onCityWeatherForecastRESTComplete(JSONObject jsonObj)
			throws JSONException {

		WeatherForecastAdapHelper weatherForecastAdapHelper = new WeatherForecastAdapHelper();

		try {

			weatherForecastAdapHelper = JSONWeatherParser
					.getWeatherForecast(jsonObj);
			System.out.println("Weather [" + weatherForecastAdapHelper + "]"); // ////////

		} catch (JSONException e) {

			e.printStackTrace();
		}

		//Toast.makeText(this, jsonObj.toString(), Toast.LENGTH_LONG).show();

		pbloading.setVisibility(View.GONE);

		//tvWeatherResponse.setVisibility(View.VISIBLE);
		//tvWeatherResponse.setText(jsonObj.toString(4));

		DailyForecastPageAdapter adapter = new DailyForecastPageAdapter(
				OWM_WEATHER_FORECAST_CNT_cnt, getSupportFragmentManager(),
				weatherForecastAdapHelper);

		viewPager.setAdapter(adapter);
	}

}