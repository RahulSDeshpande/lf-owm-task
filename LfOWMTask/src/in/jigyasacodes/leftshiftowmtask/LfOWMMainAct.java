package in.jigyasacodes.leftshiftowmtask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import in.jigyasacodes.leftshiftowmtask.step1.Step1CityNamesDynamicListViewAct;
import in.jigyasacodes.leftshiftowmtask.step2.trial.Step2GPSLocationWeatherAct;

public class LfOWMMainAct extends Activity {

    Button btnStep1, btnStep2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.dynamic_listview);

        btnStep1 = (Button) findViewById(R.id.btnStep1);
        btnStep2 = (Button) findViewById(R.id.btnStep2);

		/*
         * Date d = new Date(1428649200); Calendar c = Calendar.getInstance();
		 * c.add(Calendar.DATE, 21); c.setTimeInMillis(1428649200L * 1000); //
		 * c.getDisplayName(Calendar.MONTH,Calendar.SHORT, Locale.US);
		 * 
		 * Toast.makeText(this, c.get(Calendar.DATE) + "\n" +
		 * (c.get(Calendar.MONTH) + 1), Toast.LENGTH_LONG).show();
		 * 
		 * // SimpleDateFormat sdf =new SimpleDateFormat();
		 */
    }

    public void onStep1Click(View view) {

        startActivity(new Intent(LfOWMMainAct.this,
                Step1CityNamesDynamicListViewAct.class));

    }


    public void onStep2Click(View view) {

        startActivity(new Intent(LfOWMMainAct.this,
                Step2GPSLocationWeatherAct.class));
    }
}