package in.jigyasacodes.leftshiftowmtask.commons.adapter;

import in.jigyasacodes.leftshiftowmtask.commons.data.DayForecast;

import java.util.ArrayList;
import java.util.List;

public class WeatherForecastAdapHelper {

	private List<DayForecast> daysForecast = new ArrayList<DayForecast>();

	public void addForecast(DayForecast forecast) {
		daysForecast.add(forecast);
		System.out.println("Add forecast [" + forecast + "]");
	}

	public DayForecast getdForecast(int dayNum) {
		return daysForecast.get(dayNum);
	}
}
