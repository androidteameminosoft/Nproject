package com.prapul.nproject;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;

public class NtvBrakingNewsFrag extends Fragment {

	String TAG = "NTV FRagment";
	// ListView lvNTv;
	// ProgressBar progressBar;

	static List<BreakingNews> brackNewsList = new ArrayList<BreakingNews>();
	LayoutInflater inflater;
	LinearLayout layoutBraking;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup viewgroup = (ViewGroup) inflater.inflate(
				R.layout.ntv_braking_news_main_frag, null);

		Bundle bundle = getArguments();

		String url = bundle.getString("url");

		layoutBraking = (LinearLayout) viewgroup.findViewById(R.id.brakingnews);

		getJData(url);

		return viewgroup;
	}

	public void getJData(String url) {

		NTVMainActivity.pg.show();

		Log.d(TAG, url);

		JsonArrayRequest request = new JsonArrayRequest(url,
				new Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray response) {

						if (response.length() > 0) {

							MyDataFetchTask async = new MyDataFetchTask(
									response);
							async.execute();

						} else {
							NTVMainActivity.pg.cancel();
						}
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}
				});

		NTVMainActivity.quee.add(request);

	}

	public class MyDataFetchTask extends AsyncTask<String, String, String> {

		JSONArray responcejsonArray;

		public MyDataFetchTask(JSONArray response) {
			responcejsonArray = response;
		}

		@Override
		protected String doInBackground(String... params) {
			if (responcejsonArray != null) {
				brackNewsList.clear();
				brackNewsList.removeAll(brackNewsList);

				brackNewsList = ParsingJson.parseNews(responcejsonArray);

			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			// progressBar.setVisibility(View.GONE);
			// lvNTv.setAdapter(new MyListAdapter(getActivity()));

			inflater = (LayoutInflater) getActivity().getSystemService(
					getActivity().LAYOUT_INFLATER_SERVICE);

			for (int i = 0; i < brackNewsList.size(); i++) {

				layoutBraking.addView(insertNews(i));
			}
			NTVMainActivity.pg.cancel();

		}

	}

	public View insertNews(int i) {

		View view = inflater.inflate(R.layout.braking_news_thumbnail, null);
		view.setId(i);
		TextView tv = (TextView) view.findViewById(R.id.texttitleData);
		NetworkImageView imageView = (NetworkImageView) view
				.findViewById(R.id.brakingimageView);
		tv.setText(brackNewsList.get(i).getTitle());
		tv.setTypeface(NTVMainActivity.typeFaceTelugu);
		imageView.setImageUrl("http://ntvapps.com/APHadmin/galleries/"
				+ brackNewsList.get(i).imagePath, NTVMainActivity.imageLoader);

		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent in = new Intent(getActivity(), NtvFullNews.class);
				in.putExtra("position", v.getId());
				startActivity(in);
			}
		});
		return view;

	}
	// public class MyListAdapter extends BaseAdapter {
	//
	// LayoutInflater inflater;
	//
	// public MyListAdapter(Context mContext) {
	// inflater = (LayoutInflater) getActivity().getSystemService(
	// getActivity().LAYOUT_INFLATER_SERVICE);
	// }
	//
	// @Override
	// public int getCount() {
	// return brackNewsList.size();
	// }
	//
	// @Override
	// public Object getItem(int position) {
	// return position;
	// }
	//
	// @Override
	// public long getItemId(int position) {
	// return position;
	// }
	//
	// @Override
	// public View getView(int position, View convertView, ViewGroup parent) {
	// View view = inflater.inflate(R.layout.listitemback, null);
	// TextView tvTitle = (TextView) view.findViewById(R.id.newstitle);
	// TextView tvDescription = (TextView) view
	// .findViewById(R.id.newDescr);
	// TextView tvtimeStamp = (TextView) view
	// .findViewById(R.id.texttimestamp);
	// tvDescription.setTypeface(NTVMainActivity.typeFaceTelugu);
	// tvTitle.setTypeface(NTVMainActivity.typeFaceTelugu);
	//
	// tvTitle.setText(brackNewsList.get(position).getTitle());
	// tvDescription.setText(""
	// + brackNewsList.get(position).getDescription());
	//
	// tvtimeStamp.setText(brackNewsList.get(position).getAddedDate());
	// NetworkImageView networkImageView = (NetworkImageView) view
	// .findViewById(R.id.imageThumb);
	//
	// // networkImageView.setImageUrl(brackNewsList.get(position)
	// // .getImagesPath(), NTVMainActivity.imageLoader);
	//
	// networkImageView
	// .setImageUrl(
	// "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg",
	// NTVMainActivity.imageLoader);
	//
	// return view;
	// }
	// }

}
