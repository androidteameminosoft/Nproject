package com.prapul.nproject;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.prapul.youtube.FullscreenDemoActivity;
import com.prapul.youtube.ParseDetailsForRecent;
import com.prapul.youtube.Video;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FragmentRecentVideos extends Fragment {
	private List<Video> recentVideoList;
	private LayoutInflater inflater;
	private String TAG = "ntv";
	private LinearLayout linerRecentvideos;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		ViewGroup group = (ViewGroup) inflater.inflate(
				R.layout.fragment_for_recent_videos, null);
		linerRecentvideos = (LinearLayout) group
				.findViewById(R.id.recentvideos);
		addRecentVideos();
		return group;
	}

	public void addRecentVideos() {

		getRecentVideosData("http://gdata.youtube.com/feeds/api/users/NTVNewsChannel/uploads?v=2&alt=jsonc&max-results=50&start-index=1");

	}

	private void getRecentVideosData(String url) {

		Log.d(TAG, url);
		JsonObjectRequest jsObjRequest = new JsonObjectRequest(
				Request.Method.GET, url, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						MyRecentAsync task = new MyRecentAsync(response);
						task.execute();

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
					}

				});

		NTVMainActivity.quee.add(jsObjRequest);

	}

	private class MyRecentAsync extends AsyncTask<String, String, String> {
		JSONObject recentobj;

		public MyRecentAsync(JSONObject response) {

			recentVideoList = new ArrayList<Video>();
			recentobj = response;

		}

		@Override
		protected String doInBackground(String... params) {

			recentVideoList.clear();
			recentVideoList.removeAll(recentVideoList);

			recentVideoList = ParseDetailsForRecent.playListVideos(recentobj);

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			inflater = (LayoutInflater) getActivity().getSystemService(
					getActivity().LAYOUT_INFLATER_SERVICE);

			for (int i = 0; i < recentVideoList.size(); i++) {

				linerRecentvideos.addView(insertintoResent(i));
			}

		}

	}

	public View insertintoResent(int position) {

		View view = (View) inflater.inflate(R.layout.braking_news_thumbnail,
				null);
		view.setId(position);
		NetworkImageView netImageView = (NetworkImageView) view
				.findViewById(R.id.brakingimageView);
		TextView tv = (TextView) view.findViewById(R.id.texttitleData);
		tv.setText(recentVideoList.get(position).getTitle());
		netImageView.setImageUrl(recentVideoList.get(position).getThumbnail(),
				NTVMainActivity.imageLoader);

		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent in = new Intent(getActivity(),
						FullscreenDemoActivity.class);
				in.putExtra("id", recentVideoList.get(v.getId()).getId());
				startActivity(in);

			}
		});

		return view;

	}

}
