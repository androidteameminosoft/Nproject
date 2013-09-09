package com.prapul.nproject;

import static com.Ntv.pushNotifications.CommonUtilities.EXTRA_MESSAGE;
import static com.Ntv.pushNotifications.CommonUtilities.SENDER_ID;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.Ntv.pushNotifications.ConnectionDetector;
import com.Ntv.pushNotifications.ServerUtilities;
import com.Ntv.pushNotifications.WakeLocker;
import com.google.android.gcm.GCMRegistrar;

public class SplashActivity extends Activity {

	AsyncTask<Void, Void, Void> mRegisterTask;
	public static String name = "prudhvi";
	public static String email = "prudhvi@gmail.com";
	boolean backpressed = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		ConnectionDetector connectionDetector = new ConnectionDetector(
				SplashActivity.this);

		if (!connectionDetector.isConnectingToInternet()) {

			AlertDialog dilog = new AlertDialog.Builder(SplashActivity.this)
					.create();
			dilog.setTitle("Check yor net connection");
			dilog.setCancelable(false);
			dilog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
					new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							SplashActivity.this.finish();

						}
					});
			dilog.show();

		} else {

			registerGsm();

			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {

					if (backpressed == false) {

						Intent in = new Intent(SplashActivity.this,
								NTVMainActivity.class);
						startActivity(in);
						finish();
					}

				}
			}, 3000);

		}
	}

	public void registerGsm() {
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		final String regId = GCMRegistrar.getRegistrationId(this);
		if (regId.equals("")) {
			// Registration is not present, register now with GCM
			GCMRegistrar.register(this, SENDER_ID);
		}
		if (GCMRegistrar.isRegisteredOnServer(this)) {
			// Skips registration.
			Toast.makeText(getApplicationContext(),
					"Already registered with GCM", Toast.LENGTH_LONG).show();
		} else {
			final Context context = this;
			mRegisterTask = new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... params) {
					// Register on our server
					// On server creates a new user
					ServerUtilities.register(context, name, email, regId);
					return null;
				}

				@Override
				protected void onPostExecute(Void result) {
					mRegisterTask = null;
				}

			};
			mRegisterTask.execute(null, null, null);
		}

	}

	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
			// Waking up mobile if it is sleeping
			WakeLocker.acquire(getApplicationContext());

			/**
			 * Take appropriate action on this message depending upon your app
			 * requirement For now i am just displaying it on the screen
			 * */

			// Showing received message
			// lblMessage.append(newMessage + "\n");
			Toast.makeText(getApplicationContext(),
					"New Message: " + newMessage, Toast.LENGTH_LONG).show();

			// Releasing wake lock
			WakeLocker.release();
		}
	};

	@Override
	protected void onDestroy() {
		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
		try {
			unregisterReceiver(mHandleMessageReceiver);
			GCMRegistrar.onDestroy(this);
		} catch (Exception e) {
			Log.e("UnRegister Receiver Error", "> " + e.getMessage());
		}
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		backpressed = true;
		super.onBackPressed();
	}

}
