package com.prapul.nproject;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.prapul.youtube.ParseDetailsForRecent;
import com.prapul.youtube.Video;
import com.prapul.youtube.YouTubeMainActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FragmentForYouTubeChanels extends Fragment {
	LayoutInflater inflater;
	List<Video> chanelsList;
	LinearLayout linerPrograms;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		ViewGroup group = (ViewGroup) inflater.inflate(
				R.layout.fragment_for_youtube_chaneels, null);

		linerPrograms = (LinearLayout) group.findViewById(R.id.linerPrograms);
		getyoutubeChanlesData("http://gdata.youtube.com/feeds/api/users/NTVNewsChannel/playlists?v=2&alt=jsonc&max-results=50&start-index=1");
		return group;
	}

	private void getyoutubeChanlesData(String url) {

		JsonObjectRequest jsObjRequest = new JsonObjectRequest(
				Request.Method.GET, url, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						MyRecentChanels Mytask = new MyRecentChanels(response);
						Mytask.execute();

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
					}

				});

		NTVMainActivity.quee.add(jsObjRequest);

	}

	public class MyRecentChanels extends AsyncTask<String, String, String> {
		JSONObject chanelsrecentobj;

		public MyRecentChanels(JSONObject response) {

			chanelsList = new ArrayList<Video>();
			chanelsrecentobj = response;

		}

		@Override
		protected String doInBackground(String... params) {

			chanelsList.clear();
			chanelsList.removeAll(chanelsList);

			chanelsList = ParseDetailsForRecent
					.playListVideos(chanelsrecentobj);

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			inflater = (LayoutInflater) getActivity().getSystemService(
					getActivity().LAYOUT_INFLATER_SERVICE);

			for (int i = 0; i < chanelsList.size(); i++) {

				linerPrograms.addView(insertintoChanels(i));
			}

		}

	}

	public View insertintoChanels(int i) {
		View view = inflater.inflate(R.layout.braking_news_thumbnail, null);
		view.setId(i);
		TextView tv = (TextView) view.findViewById(R.id.texttitleData);
		NetworkImageView imageView = (NetworkImageView) view
				.findViewById(R.id.brakingimageView);
		tv.setText(chanelsList.get(i).getTitle());
		tv.setTypeface(NTVMainActivity.typeFaceTelugu);
		imageView.setImageUrl(chanelsList.get(i).getThumbnail(),
				NTVMainActivity.imageLoader);
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent in = new Intent(getActivity(), YouTubeMainActivity.class);
				in.putExtra("position", v.getId());
				startActivity(in);
			}
		});
		return view;
	}

}
