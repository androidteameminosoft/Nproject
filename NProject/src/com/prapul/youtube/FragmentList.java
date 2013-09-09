package com.prapul.youtube;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.prapul.nproject.NTVMainActivity;
import com.prapul.nproject.R;

public class FragmentList extends Fragment {

	private String url;

	int presentCount = 0;
	int strtingCount = 0;

	String TAG = "navuulaTV";
	List<Video> videosDetails = new ArrayList<Video>();

	private PullToRefreshListView list;
	ProgressBar progressbar;
	int totalVideos;
	// int color;
	private PullToRefreshGridView mPullRefreshGridView;
	String title;
	// List<VideoDetails> upDatedVideos = new ArrayList<VideoDetails>();
	// boolean reached = false;

	int screenorentation = 0;// 1 landscape

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		Bundle bundle = getArguments();
		url = bundle.getString("url");
		// color = bundle.getInt("color");
		title = bundle.getString("title");
		// MainActivity.actionbar.changeBackgroundColor(color);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup rootView = null;
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			screenorentation = 1;
			// if (CompatibilityUtil.getOrientation(getActivity()) == 1) {
			rootView = (ViewGroup) inflater.inflate(
					R.layout.youtuabe_activity_fragment_grid, null);

			// rootView.setBackgroundColor(color);

			mPullRefreshGridView = (PullToRefreshGridView) rootView
					.findViewById(R.id.pull_refresh_grid);
			mPullRefreshGridView.setMode(Mode.PULL_FROM_END);
			progressbar = (ProgressBar) rootView.findViewById(R.id.progressBar);
			getJData(url);

		} else {
			screenorentation = 0;
			// if (CompatibilityUtil.getOrientation(getActivity()) == 1) {
			rootView = (ViewGroup) inflater.inflate(
					R.layout.youtube_activity_fragment_list, null);

			// rootView.setBackgroundColor(color);

			list = (PullToRefreshListView) rootView
					.findViewById(R.id.pull_refresh_list);
			list.setMode(Mode.PULL_FROM_END);
			progressbar = (ProgressBar) rootView.findViewById(R.id.progressBar);
			getJData(url);
		}

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (screenorentation == 0) {
			list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {

					Intent in = new Intent(getActivity(),
							FullscreenDemoActivity.class);

					in.putExtra("id", videosDetails.get(arg2 - 1).getId());
					startActivity(in);

				}
			});

			list.setOnRefreshListener(new OnRefreshListener<ListView>() {
				@Override
				public void onRefresh(PullToRefreshBase<ListView> refreshView) {
					String label = DateUtils.formatDateTime(getActivity(),
							System.currentTimeMillis(),
							DateUtils.FORMAT_SHOW_TIME
									| DateUtils.FORMAT_SHOW_DATE
									| DateUtils.FORMAT_ABBREV_ALL);

					// Update the LastUpdatedLabel
					// refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

					// Do work to refresh the list here.

					try {
						totalVideos = videosDetails.get(0).getToatalVideos();
					} catch (ArrayIndexOutOfBoundsException ae) {

					}

					if (totalVideos > 50) {
						if (videosDetails.get(videosDetails.size() - 1)
								.getPosition() < totalVideos) {

							progressbar.setVisibility(View.VISIBLE);
							String urlupdate = url.substring(0,
									url.indexOf("?"));

							if (title.equals("Popular uploads")) {

								urlupdate = urlupdate
										+ "?v=2&max-results=50&start-index="
										+ (videosDetails.get(
												videosDetails.size() - 1)
												.getPosition() + 1)
										+ "&alt=jsonc&orderby=viewCount";

							} else {

								urlupdate = urlupdate
										+ "?v=2&max-results=50&start-index="
										+ (videosDetails.get(
												videosDetails.size() - 1)
												.getPosition() + 1)
										+ "&alt=jsonc";

							}

							getJData(urlupdate);

						} else {
							videosDetails.clear();
							videosDetails.removeAll(videosDetails);
							progressbar.setVisibility(View.VISIBLE);
							String urlupdate = url.substring(0,
									url.indexOf("?"));
							urlupdate = urlupdate
									+ "?v=2&max-results=50&start-index=" + 1
									+ "&alt=jsonc";

							getJData(urlupdate);

						}

					} else {
						list.onRefreshComplete();
					}

				}
			});

		} else if (screenorentation == 1) {

			mPullRefreshGridView
					.setOnRefreshListener(new OnRefreshListener2<GridView>() {

						@Override
						public void onPullDownToRefresh(
								PullToRefreshBase<GridView> refreshView) {

						}

						@Override
						public void onPullUpToRefresh(
								PullToRefreshBase<GridView> refreshView) {

							try {
								totalVideos = videosDetails.get(0)
										.getToatalVideos();
							} catch (ArrayIndexOutOfBoundsException ae) {

							}

							if (totalVideos > 50) {
								if (videosDetails.get(videosDetails.size() - 1)
										.getPosition() < totalVideos) {

									progressbar.setVisibility(View.VISIBLE);
									String urlupdate = url.substring(0,
											url.indexOf("?"));
									urlupdate = urlupdate
											+ "?v=2&max-results=50&start-index="
											+ (videosDetails.get(
													videosDetails.size() - 1)
													.getPosition() + 1)
											+ "&alt=jsonc";

									getJData(urlupdate);

								} else {
									videosDetails.clear();
									videosDetails.removeAll(videosDetails);
									progressbar.setVisibility(View.VISIBLE);
									String urlupdate = url.substring(0,
											url.indexOf("?"));
									urlupdate = urlupdate
											+ "?v=2&max-results=50&start-index="
											+ 1 + "&alt=jsonc";

									getJData(urlupdate);

								}

							} else {
								mPullRefreshGridView.onRefreshComplete();
							}

						}

					});
			mPullRefreshGridView
					.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {

							Intent in = new Intent(getActivity(),
									FullscreenDemoActivity.class);

							in.putExtra("id", videosDetails.get(arg2).getId());
							startActivity(in);

						}
					});

		}

	}

	/*
	 * asyntask for parsing and loading the images
	 */
	public class MyDataFetchTask extends AsyncTask<String, String, String> {

		JSONObject jsonObject;

		public MyDataFetchTask(JSONObject response) {
			jsonObject = response;
		}

		@Override
		protected String doInBackground(String... params) {
			if (jsonObject != null) {
				videosDetails.clear();
				videosDetails.removeAll(videosDetails);

				if (title.equals("Recent uploads")
						|| title.equals("Popular uploads")) {

					videosDetails = ParseDetailsForRecent
							.playListVideos(jsonObject);

				} else {

					videosDetails = PlayListVideosUtil
							.playListVideos(jsonObject);
				}

			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (screenorentation == 0) {
				progressbar.setVisibility(View.GONE);
				list.setAdapter(new MyListAdapter(getActivity()));
				try {
					// list.getLoadingLayoutProxy().setLastUpdatedLabel(label);
					list.getLoadingLayoutProxy().setLastUpdatedLabel(
							videosDetails.get(0).getToatalVideos()
									+ " videos u have"
									+ videosDetails.get(0).getPosition()
									+ "to"
									+ videosDetails.get(
											videosDetails.size() - 1)
											.getPosition());
				} catch (Exception e) {
					e.printStackTrace();
				}

				list.onRefreshComplete();
			} else if (screenorentation == 1) {

				progressbar.setVisibility(View.GONE);
				mPullRefreshGridView
						.setAdapter(new MyListAdapter(getActivity()));
				try {
					// list.getLoadingLayoutProxy().setLastUpdatedLabel(label);
					mPullRefreshGridView.getLoadingLayoutProxy()
							.setLastUpdatedLabel(
									videosDetails.get(0).getToatalVideos()
											+ " videos u have"
											+ videosDetails.get(0)
													.getPosition()
											+ "to"
											+ videosDetails.get(
													videosDetails.size() - 1)
													.getPosition());
				} catch (Exception e) {
					e.printStackTrace();
				}

				mPullRefreshGridView.onRefreshComplete();

			}

		}
	}

	public void getJData(String url) {

		Log.d(TAG, url);
		JsonObjectRequest jsObjRequest = new JsonObjectRequest(
				Request.Method.GET, url, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						MyDataFetchTask task = new MyDataFetchTask(response);
						task.execute();

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
					}

				});

		NTVMainActivity.quee.add(jsObjRequest);

	}

	// adapter for listview;

	public class MyListAdapter extends BaseAdapter {

		LayoutInflater inflater;

		public MyListAdapter(Context mContext) {
			inflater = (LayoutInflater) getActivity().getSystemService(
					getActivity().LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			return videosDetails.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = inflater.inflate(R.layout.youtube_thumbnailtitle, null);
			TextView tvTitle = (TextView) view.findViewById(R.id.textTitle);
			TextView tvNumber = (TextView) view.findViewById(R.id.textNumber);

			tvTitle.setText(videosDetails.get(position).getTitle());
			tvNumber.setText("" + videosDetails.get(position).getPosition());
			NetworkImageView networkImageView = (NetworkImageView) view
					.findViewById(R.id.networkImageView);

			networkImageView.setImageUrl(videosDetails.get(position)
					.getThumbnail(), NTVMainActivity.imageLoader);

			return view;
		}
	}

	@Override
	public void onResume() {
		super.onResume();

	}

}
