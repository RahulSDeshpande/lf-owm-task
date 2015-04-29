package in.jigyasacodes.leftshiftowmtask.commons.adapter;

import in.jigyasacodes.leftshiftowmtask.commons.data.ForecastAllDays;

import java.util.ArrayList;
import java.util.List;

public class WeatherForecastAdapHelper {

	private List<ForecastAllDays> daysForecast = new ArrayList<ForecastAllDays>();

	public void addForecast(ForecastAllDays forecast) {
		
		daysForecast.add(forecast);
	}

	public ForecastAllDays getdForecast(int dayNum) {
		return daysForecast.get(dayNum);
	}
}
