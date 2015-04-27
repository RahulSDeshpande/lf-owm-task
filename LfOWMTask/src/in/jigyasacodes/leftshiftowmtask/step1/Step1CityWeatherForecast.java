package in.jigyasacodes.leftshiftowmtask.step1;

import in.jigyasacodes.leftshiftowmtask.R;
import in.jigyasacodes.leftshiftowmtask.commons.adapter.WeatherForecastAdapter;
import in.jigyasacodes.leftshiftowmtask.commons.adapter.WeatherForecastAdapHelper;
import in.jigyasacodes.leftshiftowmtask.commons.utils.AlertDialogs;
import in.jigyasacodes.leftshiftowmtask.commons.utils.CheckGPSAndNet;
import in.jigyasacodes.leftshiftowmtask.commons.utils.CityWeatherForecastAsync;
import in.jigyasacodes.leftshiftowmtask.commons.utils.Constants;
import in.jigyasacodes.leftshiftowmtask.commons.utils.JSONWeatherForecastParser;
import in.jigyasacodes.leftshiftowmtask.commons.utils.CityWeatherForecastAsync.OnCityWeatherForecastRESTCompleteListener;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Step1CityWeatherForecast extends FragmentActivity implements
		OnCityWeatherForecastRESTCompleteListener {

	private String strCityName = null;

	private TextView tvCityName;

	private AlertDialogs alertDialogs;
	private CheckGPSAndNet checkGPSAndNet;

	CityWeatherForecastAsync cityWeatherForecastAsync;

	static ProgressBar pbloading;

	private static int OWM_WEATHER_FORECAST_CNT_cnt = 14;
	private ViewPager viewPager;

	public Step1CityWeatherForecast() {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.gps_location_weather_common_layout);

		// /////////////////////////////////////////
		alertDialogs = new AlertDialogs(this);
		checkGPSAndNet = new CheckGPSAndNet(this);
		// /////////////////////////////////////////

		tvCityName = (TextView) findViewById(R.id.tvCityName);

		pbloading = (ProgressBar) findViewById(R.id.pbLoading);
		pbloading.setVisibility(View.VISIBLE);

		// ////////////////////////////////////////////////////////////////////
		Intent i = getIntent();
		tvCityName.setText(strCityName = i.getExtras().getString("city_name"));
		// ////////////////////////////////////////////////////////////////////

		// //Toast.makeText(getApplicationContext(), strCityName,
		// Toast.LENGTH_SHORT).show();

		viewPager = (ViewPager) findViewById(R.id.viewPager);

		// //////////////////
		setupOWMURLAndCall();
		// //////////////////

	}

	public static void setPBVisibility(final int VISIBILITY) {

		pbloading.setVisibility(VISIBILITY);
	}

	private void setupOWMURLAndCall() {

		final String OWM_FORECAST_REST_URL = Constants.OWM_BASE_URL
				+ Constants.OWM_FORECAST_URL + "/"
				+ Constants.OWM_FORECAST_DAILY_daily_URL
				+ Constants.OWM_CITY_q_URL + strCityName + "&"
				+ Constants.OWM_DAYS_cnt_URL + OWM_WEATHER_FORECAST_CNT_cnt
				+ Constants.OWM_RSD_APPID_URL;

		Toast.makeText(this, "REST URL Query:\n\n" + OWM_FORECAST_REST_URL,
				Toast.LENGTH_LONG).show();

		// ///////////////////////////////////////////////////////////
		cityWeatherForecastAsync = new CityWeatherForecastAsync(this);
		cityWeatherForecastAsync.execute(OWM_FORECAST_REST_URL);
		// ///////////////////////////////////////////////////////////
	}

	@Override
	public void onCityWeatherForecastRESTComplete(
			final boolean isOWMResponseSuccessful, final JSONObject jsonObj)
			throws JSONException {

		if (isOWMResponseSuccessful) {

			pbloading.setVisibility(View.GONE);

			alertDialogs
					.showWeatherDataNotFoundAD(
							this,
							strCityName,
							"No Data Found",
							"NO Weather Forecast data found for the city '"
									+ strCityName
									+ "'\n\nPlease verify the locality name & TRY AGAIN..");

		} else {

			// //////////////////////////////////
			setupAndSetViewPagerAdapter(jsonObj);
			// //////////////////////////////////
		}
	}

	private void setupAndSetViewPagerAdapter(final JSONObject jsonObj) {
		// //tvCityWeatherResponse.setText(jsonObj.toString(4));

		WeatherForecastAdapHelper weatherForecastAdapHelper = new WeatherForecastAdapHelper();

		try {

			weatherForecastAdapHelper = JSONWeatherForecastParser
					.getWeatherForecast(jsonObj);

		} catch (JSONException e) {

			e.printStackTrace();
		}

		// Toast.makeText(this, jsonObj.toString(), Toast.LENGTH_LONG).show();

		WeatherForecastAdapter adapter = new WeatherForecastAdapter(
				OWM_WEATHER_FORECAST_CNT_cnt, getSupportFragmentManager(),
				weatherForecastAdapHelper);

		pbloading.setVisibility(View.GONE);

		viewPager.setAdapter(adapter);
		
		viewPager.setVisibility(View.VISIBLE);
	}
}