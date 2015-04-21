package in.jigyasacodes.leftshiftowmtask.step1;

import in.jigyasacodes.leftshiftowmtask.R;
import in.jigyasacodes.leftshiftowmtask.commons.adapter.DailyForecastPageAdapter;
import in.jigyasacodes.leftshiftowmtask.commons.adapter.WeatherForecastAdapHelper;
import in.jigyasacodes.leftshiftowmtask.commons.util.CityWeatherForecastAsync;
import in.jigyasacodes.leftshiftowmtask.commons.util.JSONWeatherParser;
import in.jigyasacodes.leftshiftowmtask.commons.util.CityWeatherForecastAsync.OnCityWeatherForecastRESTCompleteListener;
import in.jigyasacodes.leftshiftowmtask.commons.util.Constants;

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
				+ Constants.OWM_FORECAST_URL
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
	public void onCityWeatherForecastRESTComplete(JSONObject jsonObj)
			throws JSONException {

		setupAndSetViewPagerAdapter(jsonObj);
	}

	private void setupAndSetViewPagerAdapter(final JSONObject jsonObj) {
		// //tvCityWeatherResponse.setText(jsonObj.toString(4));

		WeatherForecastAdapHelper weatherForecastAdapHelper = new WeatherForecastAdapHelper();

		try {

			weatherForecastAdapHelper = JSONWeatherParser
					.getWeatherForecast(jsonObj);
			System.out.println("Weather [" + weatherForecastAdapHelper + "]"); // ////////

		} catch (JSONException e) {

			e.printStackTrace();
		}

		// Toast.makeText(this, jsonObj.toString(), Toast.LENGTH_LONG).show();

		DailyForecastPageAdapter adapter = new DailyForecastPageAdapter(
				OWM_WEATHER_FORECAST_CNT_cnt, getSupportFragmentManager(),
				weatherForecastAdapHelper);

		pbloading.setVisibility(View.GONE);

		viewPager.setAdapter(adapter);
	}
}