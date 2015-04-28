package in.jigyasacodes.leftshiftowmtask.commons.utils;

import in.jigyasacodes.leftshiftowmtask.commons.adapter.WeatherForecastAdapHelper;
import in.jigyasacodes.leftshiftowmtask.commons.data.ForecastAllDays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONWeatherForecastParser {

	public static WeatherForecastAdapHelper getWeatherForecast(JSONObject jObj)
			throws JSONException {

		WeatherForecastAdapHelper weatherForecastAdapHelper = new WeatherForecastAdapHelper();

		JSONArray jArr = jObj.getJSONArray("list");

		for (int i = 0; i < jArr.length(); i++) {

			JSONObject jDayForecast = jArr.getJSONObject(i);

			ForecastAllDays forecastAllDays = new ForecastAllDays();

			forecastAllDays.timestamp = jDayForecast.getLong("dt");

			JSONObject jTempObj = jDayForecast.getJSONObject("temp");

			{
				forecastAllDays.forecastSingleDay.day = (float) jTempObj
						.getDouble("day");
				forecastAllDays.forecastSingleDay.min = (float) jTempObj
						.getDouble("min");
				forecastAllDays.forecastSingleDay.max = (float) jTempObj
						.getDouble("max");
				forecastAllDays.forecastSingleDay.night = (float) jTempObj
						.getDouble("night");
				forecastAllDays.forecastSingleDay.eve = (float) jTempObj
						.getDouble("eve");
				forecastAllDays.forecastSingleDay.morning = (float) jTempObj
						.getDouble("morn");
			}

			weatherForecastAdapHelper.addForecast(forecastAllDays);
		}

		return weatherForecastAdapHelper;
	}
}