package in.jigyasacodes.leftshiftowmtask.commons.utils;

import in.jigyasacodes.leftshiftowmtask.commons.adapter.WeatherForecastAdapHelper;
import in.jigyasacodes.leftshiftowmtask.commons.data.ForecastAllDays;
import in.jigyasacodes.leftshiftowmtask.commons.data.MetaWeather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONWeatherForecastParser {

	public static WeatherForecastAdapHelper getWeatherForecast(JSONObject jObj)
			throws JSONException {

		WeatherForecastAdapHelper weatherForecastAdapHelper = new WeatherForecastAdapHelper();

		// We create out JSONObject from the data
		// //JSONObject jObj = new JSONObject(data);

		JSONArray jArr = jObj.getJSONArray("list");

		// We traverse all the array and parse the data
		for (int i = 0; i < jArr.length(); i++) {

			JSONObject jDayForecast = jArr.getJSONObject(i);

			// Now we have the json object so we can extract the data
			ForecastAllDays forecastAllDays = new ForecastAllDays();

			// We retrieve the timestamp (dt)
			forecastAllDays.timestamp = jDayForecast.getLong("dt");

			//
			//
			//
			//
			//

			// Temp is an object
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
			// Pressure & Humidity
			forecastAllDays.weather.currentCondition
					.setPressure((float) jDayForecast.getDouble("pressure"));
			forecastAllDays.weather.currentCondition
					.setHumidity((float) jDayForecast.getDouble("humidity"));

			// ...and now the weather
			JSONArray jWeatherArr = jDayForecast.getJSONArray("weather");
			JSONObject jWeatherObj = jWeatherArr.getJSONObject(0);
			forecastAllDays.weather.currentCondition.setWeatherId(getInt("id",
					jWeatherObj));
			forecastAllDays.weather.currentCondition.setDescr(getString(
					"description", jWeatherObj));
			forecastAllDays.weather.currentCondition.setCondition(getString(
					"main", jWeatherObj));
			forecastAllDays.weather.currentCondition.setIcon(getString("icon",
					jWeatherObj));

			weatherForecastAdapHelper.addForecast(forecastAllDays);
		}

		return weatherForecastAdapHelper;
	}

	private static JSONObject getJSONObject(String tagName, JSONObject jObj)
			throws JSONException {

		return jObj.getJSONObject(tagName);
	}

	private static String getString(String tagName, JSONObject jObj)
			throws JSONException {

		return jObj.getString(tagName);
	}

	private static float getFloat(String tagName, JSONObject jObj)
			throws JSONException {

		return (float) jObj.getDouble(tagName);
	}

	private static int getInt(String tagName, JSONObject jObj)
			throws JSONException {

		return jObj.getInt(tagName);
	}
}