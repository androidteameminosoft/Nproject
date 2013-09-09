package com.prapul.nproject;

import java.util.List;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.facebook.android.ShareOnFacebook;
import com.google.ads.f;

public class NtvFullNews extends ActionBarActivity {
	TextView textTitle;
	TextView textStorey;
	TextView textTimestamp;
	NetworkImageView imageview;;
	Button butPreavious;
	Button butNext;
	int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ntv_news_full);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		Bundle bu = getIntent().getExtras();
		imageview = (NetworkImageView) findViewById(R.id.netImageView);

		textTitle = (TextView) findViewById(R.id.textTitle);
		textStorey = (TextView) findViewById(R.id.textFullDescription);
		textTimestamp = (TextView) findViewById(R.id.texttimestamp);
		butPreavious = (Button) findViewById(R.id.butPrevious);
		butNext = (Button) findViewById(R.id.butNext);
		position = bu.getInt("position");

		if (NtvBrakingNewsFrag.brackNewsList.size() > 0) {

			if (position == 0) {
				butPreavious.setVisibility(View.GONE);

			}

			int t = position + 1;
			if (!(t <= NtvBrakingNewsFrag.brackNewsList.size())) {
				butNext.setVisibility(View.GONE);
			}

			if (position == NtvBrakingNewsFrag.brackNewsList.size() - 1) {
				butNext.setVisibility(View.GONE);

			}

		}

		textTitle.setTypeface(NTVMainActivity.typeFaceTelugu);
		textStorey.setTypeface(NTVMainActivity.typeFaceTelugu);
		textTitle.setText(NtvBrakingNewsFrag.brackNewsList.get(position)
				.getTitle());

		textStorey.setText(Html.fromHtml(NtvBrakingNewsFrag.brackNewsList.get(
				position).getDescription()));
		imageview.setImageUrl("http://ntvapps.com/APHadmin/galleries/"
				+ NtvBrakingNewsFrag.brackNewsList.get(position)
						.getImagesPath(), NTVMainActivity.imageLoader);

		textTimestamp.setText(NtvBrakingNewsFrag.brackNewsList.get(position)
				.getAddedDate());

		butNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				position++;
				if (NtvBrakingNewsFrag.brackNewsList.size() > position) {
					changeNews(position);
					if (position == NtvBrakingNewsFrag.brackNewsList.size() - 1) {
						butNext.setVisibility(View.GONE);
					} else {
						butNext.setVisibility(View.VISIBLE);
						butPreavious.setVisibility(View.VISIBLE);
					}
				}

			}
		});

		butPreavious.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				position--;

				if (position >= 0) {
					changeNews(position);
					if (position == 0) {
						butPreavious.setVisibility(View.GONE);

					} else {
						butNext.setVisibility(View.VISIBLE);
						butPreavious.setVisibility(View.VISIBLE);
					}

				}

			}
		});

	}

	protected void changeNews(int currentposition) {
		textTitle.setText(NtvBrakingNewsFrag.brackNewsList.get(currentposition)
				.getTitle());

		textStorey.setText(Html.fromHtml(NtvBrakingNewsFrag.brackNewsList.get(
				currentposition).getDescription()));

		textTimestamp.setText(NtvBrakingNewsFrag.brackNewsList.get(
				currentposition).getAddedDate());

		imageview.setImageUrl("http://ntvapps.com/APHadmin/galleries/"
				+ NtvBrakingNewsFrag.brackNewsList.get(currentposition)
						.getImagesPath(), NTVMainActivity.imageLoader);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.fullnews, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == android.R.id.home) {

			finish();
		}

		else if (item.getItemId() == R.id.facebook) {
			// shareintent("face");

			ShareOnFacebook facebook = new ShareOnFacebook();
			facebook.callToshare(this, textStorey.getText().toString(), this);

		} else if (item.getItemId() == R.id.email) {
			Intent email = new Intent(Intent.ACTION_SEND);
			email.putExtra(Intent.EXTRA_SUBJECT, textTitle.getText().toString());
			email.putExtra(Intent.EXTRA_TEXT, textStorey.getText().toString());
			email.setType("message/rfc822");
			startActivity(Intent.createChooser(email,
					"Choose an Email client :"));

		}

		return super.onOptionsItemSelected(item);
	}
}
