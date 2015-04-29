package in.jigyasacodes.leftshiftowmtask.commons.utils;

public class Constants {

	public static final String OWM_RSD_APPID = "ca1a58a82ed2ecd9203969054b33c3ed";

	public static final String OWM_RSD_APPID_URL = "&appid=" + OWM_RSD_APPID;

	public static final String OWM_HOST_URL = "http://api.openweathermap.org/";
	public static final String OWM_PATH_URL = "data/2.5/";

	public static final String OWM_BASE_URL = OWM_HOST_URL + OWM_PATH_URL;

	public static final String OWM_FORECAST_URL = "forecast";

	public static final String OWM_FORECAST_DAILY_daily_URL = "daily?";

	public static final String OWM_CITY_q_URL = "q=";

	public static final String OWM_LATITUDE_lat_URL = "lat=";
	public static final String OWM_LONGITUDE_lon_URL = "lon=";

	public static final String OWM_DAYS_cnt_URL = "cnt=";
	public static int OWM_DAYS_cnt_VALUE_16_URL = 16;

	public static final String OWM_RESPONSE_MODE_mode_URL = "mode=";

	public static final String OWM_RESPONSE_CODE_cod_URL = "cod";

	public static final int OWM_RESPONSE_CODE_200_SUCCESS = 200;
	public static final int OWM_RESPONSE_CODE_404_ERROR = 404;
	public static final String OWM_RESPONSE_JSON_404_ERROR = "{\"message\":\"\",\"cod\":\"404\"}";

	// //////////////////////////////////////////////////////
	public static final String TEMP_DEGREE_CELCIUS = "°C";
	public static final String TEMP_DEGREE_FAHRENHEIT = "°F";
	// //////////////////////////////////////////////////////

}