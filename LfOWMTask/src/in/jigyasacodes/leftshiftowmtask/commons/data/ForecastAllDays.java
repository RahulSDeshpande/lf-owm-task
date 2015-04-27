package in.jigyasacodes.leftshiftowmtask.commons.data;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ForecastAllDays {

	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	public MetaWeather weather = new MetaWeather();
	public ForecastSingleDay forecastSingleDay = new ForecastSingleDay();
	public long timestamp;

	public class ForecastSingleDay {
		public float day;
		public float min;
		public float max;
		public float night;
		public float eve;
		public float morning;
	}

	public String getStringDate() {

		return sdf.format(new Date(timestamp));
	}
}