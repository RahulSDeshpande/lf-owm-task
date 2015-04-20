package in.jigyasacodes.leftshiftowmtask.step2.trial.frag;

import in.jigyasacodes.leftshiftowmtask.R;
import in.jigyasacodes.leftshiftowmtask.step2.trial.data.DayForecast;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Francesco
 * 
 */
public class DailyForecastFragment extends Fragment {

	TextView tvTempDay, tvTempMin, tvTempMax, tvTempMorn, tvTempEven,
			tvTempNight, tvHumidity, tvWindSpeed, tvCloudAll, tvRain3hh,
			tvSnow3h;

	private DayForecast dayForecast;

	// private ImageView iconWeather;

	public DailyForecastFragment() {
	}

	public void setForecast(DayForecast dayForecast) {

		this.dayForecast = dayForecast;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.daily_forecast_fragment,
				container, false);

		setupUIMapping(view);

		/*
		 * TextView tempView = (TextView) view.findViewById(R.id.tempForecast);
		 * TextView descView = (TextView)
		 * view.findViewById(R.id.skydescForecast); tempView.setText((int)
		 * (dayForecast.forecastTemp.min - 275.15) + "-" + (int)
		 * (dayForecast.forecastTemp.max - 275.15));
		 * descView.setText(dayForecast.weather.currentCondition.getDescr());
		 */

		// iconWeather = (ImageView) view.findViewById(R.id.forCondIcon);
		// Now we retrieve the weather icon
		// JSONIconWeatherTask task = new JSONIconWeatherTask();
		// task.execute(new String[] { dayForecast.weather.currentCondition
		// .getIcon() });

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

		tvTempDay.append((int) (dayForecast.forecastTemp.day - 275.15) + "");

		tvTempMin.append((int) (dayForecast.forecastTemp.min - 275.15) + "");
		tvTempMax.append((int) (dayForecast.forecastTemp.max - 275.15) + "");

		tvTempMorn.append((int) (dayForecast.forecastTemp.morning - 275.15)
				+ "");
		tvTempEven.append((int) (dayForecast.forecastTemp.eve - 275.15) + "");
		tvTempNight
				.append((int) (dayForecast.forecastTemp.night - 275.15) + "");

	}
}