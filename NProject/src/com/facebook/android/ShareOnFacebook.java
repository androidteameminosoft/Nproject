package com.facebook.android;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.android.Facebook.DialogListener;
import com.prapul.nproject.NtvFullNews;
import com.prapul.nproject.R;

public class ShareOnFacebook {

	public static Context ctx;
	private static final String APP_ID = "144735949069200";
	private static final String[] PERMISSIONS = new String[] {
			"publish_stream", "read_stream", "offline_access" };

	private static final String TOKEN = "access_token";
	private static final String EXPIRES = "expires_in";
	private static final String KEY = "facebook-credentials";

	private Facebook facebook;
	private String messageToPost;

	Button ok, cancel;
	Context context;
	Activity activity;

	public boolean saveCredentials(Facebook facebook) {
		Editor editor = context.getApplicationContext()
				.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putString(TOKEN, facebook.getAccessToken());
		editor.putLong(EXPIRES, facebook.getAccessExpires());
		return editor.commit();
	}

	public boolean restoreCredentials(Facebook facebook) {
		SharedPreferences sharedPreferences = context.getApplicationContext()
				.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		facebook.setAccessToken(sharedPreferences.getString(TOKEN, null));
		facebook.setAccessExpires(sharedPreferences.getLong(EXPIRES, 0));
		return facebook.isSessionValid();
	}

	// String fbmsgs = " Hi welcome to Gypsy Application." + " "
	// + "https://twitter.com/GypsyApp" + " " + ","
	// + "https://plus.google.com/u/2/116301554077879756993/posts";

	String fbmsgs = " (Link to app) ";

	public void callToshare(Context context, String facebookmessage,
			Activity act) {

		ShareOnFacebook.ctx = context;
		this.context = context;

		activity = act;

		facebook = new Facebook(APP_ID);
		restoreCredentials(facebook);

		// Uri video =
		// Uri.parse("android.resource://com.cpt.sample/raw/kalimba");
		// String uri = "android.resource://" + getPackageName() + "/"
		// + R.raw.matakandani;
		//
		// byte[] image = getImageData();
		Bundle b = new Bundle();

		// b.putByteArray("picture", image);
		// b.putString("message", fbmsgs);

		String facebookMessage = facebookmessage;
		if (facebookMessage == null) {
			// facebookMessage = fbmsgs;
			AsyncFacebookRunner mAsyncRunner = new AsyncFacebookRunner(facebook);
			mAsyncRunner.request("me/photos", b, "POST",
					new SampleUploadListener(), null);

		}

		messageToPost = facebookMessage;
		callButtons();
	}

	public void callButtons() {

		// ok.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {

		if (!facebook.isSessionValid()) {

			loginAndPostToWall();

		} else {

			postToWall(messageToPost);
		}
		// }
		// });

	}

	// public void DoNotShare(View button) {
	//
	// finish();
	// }
	//
	// public void Share(View button) {
	//
	// if (!facebook.isSessionValid()) {
	// loginAndPostToWall();
	// } else {
	//
	// postToWall(messageToPost);
	// }
	// }

	public void loginAndPostToWall() {
		facebook.authorize(activity, PERMISSIONS, Facebook.FORCE_DIALOG_AUTH,
				new LoginDialogListener());
	}

	public void postToWall(final String message) {

		new Thread(new Runnable() {
			public void run() {
				// your logic

				Bundle parameters = new Bundle();
				parameters.putString("message", message);
				parameters.putString("description", "topic share");
				try {
					facebook.request("me");
					String response = facebook.request("me/feed", parameters,
							"POST");
					Log.d("Tests", "got response: " + response);
					if (response == null || response.equals("")
							|| response.equals("false")) {
						showToast("Blank response.");
					} else {
						showToast("Message posted to your facebook wall!");
					}
					// finish();
				} catch (Exception e) {
					showToast("Failed to post to wall!");
					e.printStackTrace();
					// finish();
				}
			}
		}).start();

	}

	// public void postToWall(String msg) {
	// Log.d("Tests", "Testing graph API wall post");
	// try {
	// String response = facebook.request("me");
	// Bundle parameters = new Bundle();
	// parameters.putString("message", msg);
	// parameters.putString("description", "test test test");
	// response = facebook.request("me/feed", parameters,
	// "POST");
	// Log.d("Tests", "got response: " + response);
	// if (response == null || response.equals("") ||
	// response.equals("false")) {
	// Log.v("Error", "Blank response");
	// }
	// } catch(Exception e) {
	// e.printStackTrace();
	// }
	// }

	class LoginDialogListener implements DialogListener {
		public void onComplete(Bundle values) {
			saveCredentials(facebook);
			if (messageToPost != null) {
				postToWall(messageToPost);
			}
		}

		public void onFacebookError(FacebookError error) {
			showToast("Authentication with Facebook failed!");
			// finish();
		}

		public void onError(DialogError error) {
			showToast("Code Error :Authentication with Facebook failed!");
			// finish();
		}

		public void onCancel() {
			showToast("Authentication with Facebook cancelled!");
			// finish();
		}
	}

	private void showToast(final String message) {
		((Activity) context).runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			}
		});

	}

	// public static byte[] getImageData() {
	//
	// Bitmap bitmap = BitmapFactory.decodeResource(ctx.getResources(),
	// R.drawable.ic_launcher);
	// ByteArrayOutputStream bao = new ByteArrayOutputStream();
	// bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
	// byte[] bitMapData = bao.toByteArray();
	// return bitMapData;
	// }
}
