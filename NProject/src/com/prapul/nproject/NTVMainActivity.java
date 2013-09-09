package com.prapul.nproject;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.ads.p;
import com.prapul.youtube.YouTubeMainActivity;

public class NTVMainActivity extends FragmentActivity {
	static Typeface typeFaceTelugu;
	public static ImageLoader imageLoader;
	public static RequestQueue quee;
	//
	Button butHome;
	Button butState;
	Button butNational;
	Button butInternational;
	Button butSports;
	Button butbussiness;
	Button butMovies;
	public static ProgressDialog pg;
	//

	Button butPlay;
	Button butyouTube;
	String TAG = "ntv";
	List<BreakingNews> brackNewsList = new ArrayList<BreakingNews>();

	TextView latestNews;

	String urls;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		latestNews = (TextView) findViewById(R.id.textLatesNews);

		initButtons();
		configureProgressDailog();
		typeFaceTelugu = Typeface.createFromAsset(getAssets(), "gautami.ttf");
		latestNews.setTypeface(typeFaceTelugu);

		quee = Volley.newRequestQueue(this);
		imageLoader = new ImageLoader(quee, new BitmapLruCache(
				BitmapLruCache.getDefaultLruCacheSize()));

		initFragments();

		butPlay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent in = new Intent(NTVMainActivity.this, LiveTV_Class.class);
				startActivity(in);

			}
		});
		butyouTube.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent in = new Intent(NTVMainActivity.this,
						YouTubeMainActivity.class);
				in.putExtra("position", 0);
				startActivity(in);

			}
		});

		getLatestNewsText();

	}

	private void configureProgressDailog() {

		pg = new ProgressDialog(NTVMainActivity.this);
		pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pg.setMessage("loading");
		pg.setCancelable(false);
		// pg.show();

	}

	private void initButtons() {

		butHome = (Button) findViewById(R.id.butHome);
		butState = (Button) findViewById(R.id.butState);
		butNational = (Button) findViewById(R.id.butNational);
		butInternational = (Button) findViewById(R.id.butInternational);
		butSports = (Button) findViewById(R.id.butSports);
		butbussiness = (Button) findViewById(R.id.butBussioness);
		butMovies = (Button) findViewById(R.id.butMovies);
		butPlay = (Button) findViewById(R.id.butLiveTv);
		butyouTube = (Button) findViewById(R.id.butYoutube);

		butHome.setOnClickListener(new MyButtonListener());
		butState.setOnClickListener(new MyButtonListener());
		butNational.setOnClickListener(new MyButtonListener());
		butInternational.setOnClickListener(new MyButtonListener());
		butSports.setOnClickListener(new MyButtonListener());
		butbussiness.setOnClickListener(new MyButtonListener());
		butMovies.setOnClickListener(new MyButtonListener());

	}

	private void initFragments() {
		// fragments adding
		FragmentManager fManger = getSupportFragmentManager();
		FragmentTransaction fTransaction = fManger.beginTransaction();
		Fragment breakNewsFg = new NtvBrakingNewsFrag();
		Bundle bu = new Bundle();
		bu.putString("url",
				"http://ntvapps.com/APHadmin/breakingnesservice.php");
		breakNewsFg.setArguments(bu);
		fTransaction.add(R.id.fragBrakingNews, breakNewsFg, null);
		fTransaction.commit();

		FragmentManager fragmentManger = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManger
				.beginTransaction();
		Fragment fragemntRecentVideos = new FragmentRecentVideos();
		fragmentTransaction.add(R.id.fragRecentVideos, fragemntRecentVideos,
				null);
		fragmentTransaction.commit();

		FragmentManager fragmentChanels = getSupportFragmentManager();
		FragmentTransaction fTrasaction1 = fragmentChanels.beginTransaction();
		Fragment fragemntChanels = new FragmentForYouTubeChanels();
		fTrasaction1.add(R.id.fragchanels, fragemntChanels, null);
		fTrasaction1.commit();

	}

	private void getLatestNewsText() {
		String url = "http://ntvapps.com/APHadmin/flashnewsservice.php";

		JsonArrayRequest request = new JsonArrayRequest(url,
				new Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray response) {

						if (response.length() > 0) {

							for (int i = 0; i < response.length(); i++) {
								try {

									JSONObject obj;
									obj = response.getJSONObject(i);
									latestNews.append("           "
											+ obj.getString("news"));

								} catch (JSONException e) {
									e.printStackTrace();
								}
							}

						}
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}
				});

		quee.add(request);

	}

	private class MyButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {

			butchangeStyle(v.getId());
			switch (v.getId()) {
			case R.id.butHome:

				urls = "http://ntvapps.com/APHadmin/breakingnesservice.php";
				changeFragmentAndUrl(urls);

				break;
			case R.id.butState:
				urls = "http://ntvapps.com/APHadmin/breakingnesservice.php?category=state";
				changeFragmentAndUrl(urls);

				break;

			case R.id.butNational:

				urls = "http://ntvapps.com/APHadmin/breakingnesservice.php?category=national";
				changeFragmentAndUrl(urls);

				break;

			case R.id.butInternational:
				urls = "http://ntvapps.com/APHadmin/breakingnesservice.php?category=international";
				changeFragmentAndUrl(urls);

				break;
			case R.id.butSports:
				urls = "http://ntvapps.com/APHadmin/breakingnesservice.php?category=sports";
				changeFragmentAndUrl(urls);

				break;

			case R.id.butBussioness:

				urls = "http://ntvapps.com/APHadmin/breakingnesservice.php?category=bussiness";
				changeFragmentAndUrl(urls);

				break;

			case R.id.butMovies:

				urls = "http://ntvapps.com/APHadmin/breakingnesservice.php?category=movies";
				changeFragmentAndUrl(urls);

				break;

			default:
				break;
			}

		}
	}

	private void butchangeStyle(int id) {

		switch (id) {
		case R.id.butHome:

			butHome.setBackgroundResource(R.drawable.button_background_sel);
			butState.setBackgroundResource(R.drawable.button_back_normal);
			butNational.setBackgroundResource(R.drawable.button_back_normal);
			butInternational
					.setBackgroundResource(R.drawable.button_back_normal);
			butSports.setBackgroundResource(R.drawable.button_back_normal);
			butbussiness.setBackgroundResource(R.drawable.button_back_normal);
			butMovies.setBackgroundResource(R.drawable.button_back_normal);

			break;

		case R.id.butState:

			butHome.setBackgroundResource(R.drawable.button_back_normal);
			butState.setBackgroundResource(R.drawable.button_background_sel);
			butNational.setBackgroundResource(R.drawable.button_back_normal);
			butInternational
					.setBackgroundResource(R.drawable.button_back_normal);
			butSports.setBackgroundResource(R.drawable.button_back_normal);
			butbussiness.setBackgroundResource(R.drawable.button_back_normal);
			butMovies.setBackgroundResource(R.drawable.button_back_normal);

			break;

		case R.id.butNational:

			butHome.setBackgroundResource(R.drawable.button_back_normal);
			butState.setBackgroundResource(R.drawable.button_back_normal);
			butNational.setBackgroundResource(R.drawable.button_background_sel);
			butInternational
					.setBackgroundResource(R.drawable.button_back_normal);
			butSports.setBackgroundResource(R.drawable.button_back_normal);
			butbussiness.setBackgroundResource(R.drawable.button_back_normal);
			butMovies.setBackgroundResource(R.drawable.button_back_normal);

			break;

		case R.id.butInternational:

			butHome.setBackgroundResource(R.drawable.button_back_normal);
			butState.setBackgroundResource(R.drawable.button_back_normal);
			butNational.setBackgroundResource(R.drawable.button_back_normal);
			butInternational
					.setBackgroundResource(R.drawable.button_background_sel);
			butSports.setBackgroundResource(R.drawable.button_back_normal);
			butbussiness.setBackgroundResource(R.drawable.button_back_normal);
			butMovies.setBackgroundResource(R.drawable.button_back_normal);

			break;
		case R.id.butSports:

			butHome.setBackgroundResource(R.drawable.button_back_normal);
			butState.setBackgroundResource(R.drawable.button_back_normal);
			butNational.setBackgroundResource(R.drawable.button_back_normal);
			butInternational
					.setBackgroundResource(R.drawable.button_back_normal);
			butSports.setBackgroundResource(R.drawable.button_background_sel);
			butbussiness.setBackgroundResource(R.drawable.button_back_normal);
			butMovies.setBackgroundResource(R.drawable.button_back_normal);
			break;

		case R.id.butBussioness:

			butHome.setBackgroundResource(R.drawable.button_back_normal);
			butState.setBackgroundResource(R.drawable.button_back_normal);
			butNational.setBackgroundResource(R.drawable.button_back_normal);
			butInternational
					.setBackgroundResource(R.drawable.button_back_normal);
			butSports.setBackgroundResource(R.drawable.button_back_normal);
			butbussiness
					.setBackgroundResource(R.drawable.button_background_sel);
			butMovies.setBackgroundResource(R.drawable.button_back_normal);
			break;

		case R.id.butMovies:

			butHome.setBackgroundResource(R.drawable.button_back_normal);
			butState.setBackgroundResource(R.drawable.button_back_normal);
			butNational.setBackgroundResource(R.drawable.button_back_normal);
			butInternational
					.setBackgroundResource(R.drawable.button_back_normal);
			butSports.setBackgroundResource(R.drawable.button_back_normal);
			butbussiness.setBackgroundResource(R.drawable.button_back_normal);
			butMovies.setBackgroundResource(R.drawable.button_background_sel);

			break;

		default:
			break;
		}

	}

	public void changeFragmentAndUrl(String url) {
		FragmentManager fManger = getSupportFragmentManager();
		FragmentTransaction fTransaction = fManger.beginTransaction();
		Fragment breakNewsFg = new NtvBrakingNewsFrag();
		Bundle bu = new Bundle();
		bu.putString("url", url);
		breakNewsFg.setArguments(bu);
		fTransaction.replace(R.id.fragBrakingNews, breakNewsFg, null);
		fTransaction.commit();

	}

}
