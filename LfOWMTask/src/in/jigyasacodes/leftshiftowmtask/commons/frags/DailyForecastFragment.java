package in.jigyasacodes.leftshiftowmtask.commons.frags;

import in.jigyasacodes.leftshiftowmtask.R;
import in.jigyasacodes.leftshiftowmtask.commons.data.ForecastAllDays;
import in.jigyasacodes.leftshiftowmtask.commons.data.ForecastAllDays.ForecastSingleDay;
import in.jigyasacodes.leftshiftowmtask.commons.utils.Constants;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DailyForecastFragment extends Fragment {

	TextView tvTempDay, tvTempMin, tvTempMax, tvTempMorn, tvTempEven,
			tvTempNight, tvHumidity, tvWindSpeed, tvCloudAll, tvRain3hh,
			tvSnow3h;

	private ForecastAllDays forecastAllDays;

	public DailyForecastFragment() {
	}

	public void setForecast(ForecastAllDays dayForecast) {

		this.forecastAllDays = dayForecast;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.daily_forecast_fragment,
				container, false);

		setupUIMapping(view);

		setWeatherForecastDataToUI();

		return view;
	}

	private void setupUIMapping(View view) {

		tvTempDay = (TextView) view.findViewById(R.id.tvTempDay);
		tvTempMin = (TextView) view.findViewById(R.id.tvTempMin);
		tvTempMax = (TextView) view.findViewById(R.id.tvTempMax);
		tvTempMorn = (TextView) view.findViewById(R.id.tvTempMorn);
		tvTempEven = (TextView) view.findViewById(R.id.tvTempEven);
		tvTempNight = (TextView) view.findViewById(R.id.tvTempNight);
		tvHumidity = (TextView) view.findViewById(R.id.tvHumidity);
		tvWindSpeed = (TextView) view.findViewById(R.id.tvWindSpeed);
		tvCloudAll = (TextView) view.findViewById(R.id.tvCloudAll);
		tvRain3hh = (TextView) view.findViewById(R.id.tvRain3h);
		tvSnow3h = (TextView) view.findViewById(R.id.tvSnow3h);
	}

	private void setWeatherForecastDataToUI() {

		tvTempDay.append((int) (forecastAllDays.forecastSingleDay.day - 275.15)
				+ Constants.TEMP_DEGREE_CELCIUS);

		tvTempMin.append((int) (forecastAllDays.forecastSingleDay.min - 275.15)
				+ Constants.TEMP_DEGREE_CELCIUS);
		tvTempMax.append((int) (forecastAllDays.forecastSingleDay.max - 275.15)
				+ Constants.TEMP_DEGREE_CELCIUS);

		tvTempMorn
				.append((int) (forecastAllDays.forecastSingleDay.morning - 275.15)
						+ Constants.TEMP_DEGREE_CELCIUS);
		tvTempEven
				.append((int) (forecastAllDays.forecastSingleDay.eve - 275.15)
						+ Constants.TEMP_DEGREE_CELCIUS);
		tvTempNight
				.append((int) (forecastAllDays.forecastSingleDay.night - 275.15)
						+ Constants.TEMP_DEGREE_CELCIUS);

	}
}