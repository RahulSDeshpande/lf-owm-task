package in.jigyasacodes.leftshiftowmtask.commons.adapter;

import in.jigyasacodes.leftshiftowmtask.commons.data.ForecastAllDays;
import in.jigyasacodes.leftshiftowmtask.commons.frags.DailyForecastFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class WeatherForecastAdapter extends FragmentPagerAdapter {

	private int numDays;
	private FragmentManager fm;
	private WeatherForecastAdapHelper forecast;
	private final static SimpleDateFormat sdf = new SimpleDateFormat("E, dd-MM");

	public WeatherForecastAdapter(int numDays, FragmentManager fm,
			WeatherForecastAdapHelper forecast) {

		super(fm);

		this.numDays = numDays;
		this.fm = fm;
		this.forecast = forecast;

	}

	@Override
	public CharSequence getPageTitle(int position) {

		Date d = new Date();
		Calendar gc = new GregorianCalendar();
		gc.setTime(d);
		gc.add(GregorianCalendar.DAY_OF_MONTH, position);

		return sdf.format(gc.getTime());
	}

	@Override
	public Fragment getItem(int num) {

		ForecastAllDays forecastAllDays = (ForecastAllDays) forecast
				.getdForecast(num);
		DailyForecastFragment f = new DailyForecastFragment();
		f.setForecast(forecastAllDays);

		return f;
	}

	@Override
	public int getCount() {

		return numDays;
	}

	public WeatherForecastAdapHelper getForecast() {

		return forecast;
	}
}