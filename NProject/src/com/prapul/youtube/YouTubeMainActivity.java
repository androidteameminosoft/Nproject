package com.prapul.youtube;

import com.prapul.nproject.R;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;

public class YouTubeMainActivity extends ActionBarActivity {

	FragmentPageTitle mDemoCollectionPagerAdapter;
	ViewPager mViewPager;

	PagerTabStrip strap;
	int position;
	ActionBar actionBar;
	LayoutInflater inflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.youtube_activity_collection);
		Bundle bu = getIntent().getExtras();
		int position = bu.getInt("position");
		Log.d("Ntv", "" + position);
		inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

		strap = (PagerTabStrip) findViewById(R.id.pager_title_strip);

		actionBar = getSupportActionBar();
		actionBar.setDisplayShowHomeEnabled(true);
		mDemoCollectionPagerAdapter = new FragmentPageTitle(
				getSupportFragmentManager(), this);

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mDemoCollectionPagerAdapter);
		mViewPager.setCurrentItem(4, true);
//		mViewPager.invalidate();
		// try {
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		System.out.println(newConfig.orientation);
	}

}
