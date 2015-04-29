package in.jigyasacodes.leftshiftowmtask.commons.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class CityWeatherForecastAsync extends
		AsyncTask<String, Void, JSONObject> {

	OnCityWeatherForecastRESTCompleteListener onCityWeatherForecastRESTCompleteListener;

	public CityWeatherForecastAsync(
			OnCityWeatherForecastRESTCompleteListener thiss) {

		onCityWeatherForecastRESTCompleteListener = thiss;
	}

	public CityWeatherForecastAsync() {

		// onEBookSearchRESTCompleteListener = thiss;
	}

	@Override
	protected JSONObject doInBackground(String... urls) {

		// //Looper.prepare();

		// This fkn line wasted my 3 days !!
		// onRESTCompleteListener = new MainActivity();
		return get(urls[0]);
	}

	@Override
	protected void onPostExecute(JSONObject jsonObjectResponse) {

		// super.onPostExecute(jsonResp);

		// //showJSONRespose(jsonResp);
		try {
			if (isOWMResponseSuccessful(jsonObjectResponse)) {

				onCityWeatherForecastRESTCompleteListener
				.onCityWeatherForecastRESTComplete(false,jsonObjectResponse);
				
			} else {

				onCityWeatherForecastRESTCompleteListener
						.onCityWeatherForecastRESTComplete(true,jsonObjectResponse);

			}
		} catch (JSONException e) {

			e.printStackTrace();
		}

	}

	private boolean isOWMResponseSuccessful(final JSONObject jsonObjectResponse)
			throws JSONException {

		if (jsonObjectResponse.getInt(Constants.OWM_RESPONSE_CODE_cod_URL) == Constants.OWM_RESPONSE_CODE_404_ERROR) {

			return false;
		}

		return true;
	}

	private JSONObject get(String url) {

		// USING FOLLOWING CODE CONCEPT, SAVES MEMORY & TIME - RSD

		// InputStream isResponse = null;
		String strResp = null;
		// defaultHttpClient
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();

			// HttpPost httpPost = new HttpPost(URL);
			HttpGet httpget = new HttpGet(url);

			HttpResponse httpResponse = httpClient.execute(httpget);

			strResp = EntityUtils.toString(httpResponse.getEntity()).trim();

		} catch (UnsupportedEncodingException e) {

			Log.e(getClass().getSimpleName(), e.toString());

		} catch (ClientProtocolException e) {

			Log.e(getClass().getSimpleName(), e.toString());

		} catch (IOException e) {

			Log.e(getClass().getSimpleName(), e.toString());
		}

		// Toast.makeText(this, strResp, Toast.LENGTH_LONG).show();

		// //////params/////////////////
		JSONObject jsonObj = null;

		// Parse the string to a JSON object
		try {

			jsonObj = new JSONObject(strResp);

		} catch (JSONException jsonE) {

			Log.e("JSON Parser", "Error parsing JSON data.." + jsonE.toString());
		}
		// return JSON String
		return jsonObj;
	}

	public interface OnCityWeatherForecastRESTCompleteListener {

		public void onCityWeatherForecastRESTComplete(
				final boolean isOWMResponseSuccessful, final JSONObject jsonObj)
				throws JSONException;

	}
}