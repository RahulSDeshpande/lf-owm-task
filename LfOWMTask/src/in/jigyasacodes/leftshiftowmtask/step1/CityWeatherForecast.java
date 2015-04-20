package in.jigyasacodes.leftshiftowmtask.step1;

import org.json.JSONException;
import org.json.JSONObject;

import in.jigyasacodes.leftshiftowmtask.R;
import in.jigyasacodes.leftshiftowmtask.step1.utils.CityWeatherForecastAsync;
import in.jigyasacodes.leftshiftowmtask.step1.utils.CityWeatherForecastAsync.OnCityWeatherForecastRESTCompleteListener;
import in.jigyasacodes.leftshiftowmtask.step1.utils.Constants;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class CityWeatherForecast extends Activity implements
		OnCityWeatherForecastRESTCompleteListener {

	private String strCityName = null;

	private TextView tvCityWeatherResponse;

	CityWeatherForecastAsync cityWeatherForecastAsync;

	public CityWeatherForecast() {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.city_weather);

		Intent i = getIntent();
		strCityName = i.getExtras().getString("city_name");
		// //Toast.makeText(getApplicationContext(), strCityName,
		// Toast.LENGTH_SHORT).show();

		tvCityWeatherResponse = (TextView) findViewById(R.id.tvCityWeatherResponse);

		final String OWM_FORECAST_REST_URL = Constants.OWM_BASE_URL
				+ Constants.OWM_FORECAST_URL
				+ Constants.OWM_FORECAST_DAILY_daily_URL
				+ Constants.OWM_CITY_q_URL + strCityName + "&"
				+ Constants.OWM_DAYS_cnt_URL + "14"
				+ Constants.OWM_RSD_APPID_URL;

		Toast.makeText(this, OWM_FORECAST_REST_URL, Toast.LENGTH_LONG).show();

		// ///////////////////////////////////////////////////////////
		cityWeatherForecastAsync = new CityWeatherForecastAsync(this);
		cityWeatherForecastAsync.execute(OWM_FORECAST_REST_URL);
		// ///////////////////////////////////////////////////////////

	}

	@Override
	public void onCityWeatherForecastRESTComplete(JSONObject jsonObj)
			throws JSONException {

		tvCityWeatherResponse.setText(jsonObj.toString(4));
	}
}