package in.jigyasacodes.leftshiftowmtask.commons.utils;

import in.jigyasacodes.leftshiftowmtask.step2.trial.Step2GPSLocationWeatherAct;
import in.jigyasacodes.leftshiftowmtask.step2.trial.Step2GetGPSLocation;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.view.View;

public class AlertDialogs {

	private Context ctx;

	public AlertDialogs(Context ctx) {

		this.setContext(ctx);
	}

	public void showInternetDisabledAD(String strTitle, String strMessage) {

		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

		Step2GPSLocationWeatherAct.setPBVisibility(View.GONE);

		builder.setMessage(strMessage)
				.setCancelable(false)
				.setPositiveButton("Turn ON",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int id) {

								Intent myIntent = new Intent(
										Settings.ACTION_DATA_ROAMING_SETTINGS);
								ctx.startActivity(myIntent);

								dialog.cancel();
							}
						})
				.setNegativeButton("Leave",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int id) {

								dialog.cancel();

							}
						}).create().show();

		// AlertDialog alert = builder.create();
		// alert.show();
	}

	public void showGPSRetryAD(
			final Step2GetGPSLocation step2GetGPSLocation,
			final String strTitle, final String strMessage) {

		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

		builder.setMessage(strMessage)
				.setCancelable(false)
				.setPositiveButton("RETRY",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int id) {

								// ////////////////////////////////
								step2GetGPSLocation.setupGPSVarsAndCall(ctx);
								// ////////////////////////////////
								dialog.cancel();
							}
						})
				.setNegativeButton("Leave",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int id) {

								dialog.cancel();
								Step2GPSLocationWeatherAct
										.setPBVisibility(View.GONE);

							}
						}).create().show();

		// AlertDialog alert = builder.create();
		// alert.show();
	}

	public Context getContext() {
		return ctx;
	}

	public void setContext(Context ctx) {
		this.ctx = ctx;
	}
}