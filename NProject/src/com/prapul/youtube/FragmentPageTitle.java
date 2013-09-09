package com.prapul.youtube;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.prapul.nproject.NTVMainActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class FragmentPageTitle extends FragmentPagerAdapter {

	Context mContext;

	String[] urls;

	String titles[];
	int totalCount;
	Fragment frags[];

	public FragmentPageTitle(FragmentManager fm, Context mContext) {
		super(fm);
		this.mContext = mContext;

		getTitlesAndUrls();

	}

	private void getTitlesAndUrls() {

		JsonObjectRequest jsObjRequest = new JsonObjectRequest(
				Request.Method.GET,
				"http://gdata.youtube.com/feeds/api/users/NTVNewsChannel/playlists?v=2&alt=jsonc&max-results=50&start-index=1",
				null, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {

						FillUrlsAsync async = new FillUrlsAsync(response);
						async.execute();

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
					}

				});

		NTVMainActivity.quee.add(jsObjRequest);

	}

	@Override
	public Fragment getItem(int arg0) {
		Fragment fragment = frags[arg0];
		return fragment;
	}

	@Override
	public int getCount() {
		return totalCount;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return titles[position];
	}

	public class FillUrlsAsync extends AsyncTask<String, String, String>

	{

		JSONObject responceObject;

		public FillUrlsAsync(JSONObject response) {
			responceObject = response;
		}

		@Override
		protected String doInBackground(String... params) {

			try {
				JSONObject data = responceObject.getJSONObject("data");

				int totalvideos = data.getInt("totalItems");
				urls = new String[totalvideos];
				titles = new String[totalvideos];
				JSONArray items = data.getJSONArray("items");

				for (int i = 0; i < items.length(); i++) {

					JSONObject jObj = (JSONObject) items.get(i);
					// jObj.get("id");
					urls[i] = "https://gdata.youtube.com/feeds/api/playlists/"
							+ jObj.get("id") + "?v=2&alt=jsonc&max-results=50";
					titles[i] = jObj.getString("title");

				}

			} catch (JSONException e) {
				Log.e("log_tag", "Error parsing data " + e.toString());
			}
			return null;

		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			totalCount = titles.length;
			frags = new Fragment[totalCount];
			for (int i = 0; i < totalCount; i++) {
				Fragment fragment = new FragmentList();
				Bundle bundle = new Bundle();
				bundle.putString("url", urls[i]);
				bundle.putString("title", titles[i]);
				fragment.setArguments(bundle);

				frags[i] = fragment;
			}
			notifyDataSetChanged();

		}
	}

}
