package in.jigyasacodes.leftshiftowmtask.step1;

import in.jigyasacodes.leftshiftowmtask.R;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DynamicListView extends Activity {

	private Button btnAddCity;
	private EditText etCityName;

	List<String> l;
	// ArrayList<String> arrLCityNames;
	private ListView lv;
	// private String[] strArrCityNames;
	private ArrayAdapter<String> arrAdapStr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.dynamic_listview);

		l = new ArrayList<String>();
		// arrLCityNames = new ArrayList<String>();
		arrAdapStr = new ArrayAdapter<String>(this, R.layout.listview_item_tv,
				l);

		btnAddCity = (Button) findViewById(R.id.btnAddCity);
		etCityName = (EditText) findViewById(R.id.etCityName);

		lv = (ListView) findViewById(R.id.lvMain);

		btnAddCity.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				addCityToListView(etCityName.getText().toString().trim());

			}
		});

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Toast.makeText(
						getApplicationContext(),
						"Position: " + position + "\nID: " + id + "\nText: "
								+ ((TextView) view).getText().toString(),
						Toast.LENGTH_SHORT).show();

				Intent i = new Intent(DynamicListView.this,
						CityWeatherForecast.class);
				i.putExtra("city_name", arrAdapStr.getItem(position));
				startActivity(i);

			}
		});
	}

	protected void addCityToListView(final String strCityName) {

		// arrLCityNames.add(strCityName);
		// l.add(strCityName);
		// arrAdapStr = new ArrayAdapter<String>(this,
		// R.layout.listview_item_tv, l);
		arrAdapStr.add(strCityName);

		lv.setAdapter(arrAdapStr);
	}
}