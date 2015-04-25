package in.jigyasacodes.leftshiftowmtask.commons.adapter;

import in.jigyasacodes.leftshiftowmtask.commons.data.DayForecast;
import in.jigyasacodes.leftshiftowmtask.commons.frags.DailyForecastFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class DailyForecastPageAdapter extends FragmentPagerAdapter {

	private int numDays;
	private FragmentManager fm;
	private WeatherForecastAdapHelper forecast;
	private final static SimpleDateFormat sdf = new SimpleDateFormat("E, dd-MM");

	public DailyForecastPageAdapter(int numDays, FragmentManager fm,
			WeatherForecastAdapHelper forecast) {
		super(fm);
		this.numDays = numDays;
		this.fm = fm;
		this.forecast = forecast;

	}

	// Page title
	@Override
	public CharSequence getPageTitle(int position) {
		// We calculate the next days adding position to the current date
		Date d = new Date();
		Calendar gc = new GregorianCalendar();
		gc.setTime(d);
		gc.add(GregorianCalendar.DAY_OF_MONTH, position);

		return sdf.format(gc.getTime());

	}

	@Override
	public Fragment getItem(int num) {
		DayForecast dayForecast = (DayForecast) forecast.getdForecast(num);
		DailyForecastFragment f = new DailyForecastFragment();
		f.setForecast(dayForecast);
		return f;
	}

	/*
	 * Number of the days we have the forecast
	 */
	@Override
	public int getCount() {

		return numDays;
	}

}
