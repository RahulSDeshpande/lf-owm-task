package in.jigyasacodes.leftshiftowmtask;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

public class LfOWMMainAct extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dynamic_listview);

		Date d = new Date(1428649200);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 21);
		c.setTimeInMillis(1428649200L * 1000);
		// c.getDisplayName(Calendar.MONTH,Calendar.SHORT, Locale.US);

		Toast.makeText(this,
				c.get(Calendar.DATE) + "\n" + (c.get(Calendar.MONTH) + 1),
				Toast.LENGTH_LONG).show();

		// SimpleDateFormat sdf =new SimpleDateFormat();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}