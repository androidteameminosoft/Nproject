package com.prapul.youtube;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.util.Log;

public class PlayListVideosUtil {

	public static List<Video> playListVideos(JSONObject json) {

		List<Video> playListVideosLists = new ArrayList<Video>();
		try {
			JSONObject data = json.getJSONObject("data");

			int totalvideos = data.getInt("totalItems");
			JSONArray items = data.getJSONArray("items");

			for (int i = 0; i < items.length(); i++) {

				JSONObject jObj = (JSONObject) items.get(i);
				int position = jObj.getInt("position");

				JSONObject videoObject = (JSONObject) jObj.get("video");
				String titles = videoObject.get("title").toString();
				String image;
				try {
					JSONObject thumbnail = (JSONObject) videoObject
							.get("thumbnail");
					image = thumbnail.get("hqDefault").toString();
				} catch (Exception e) {
					e.printStackTrace();

					JSONObject thumbnail12 = (JSONObject) data.get("thumbnail");
					image = thumbnail12.get("hqDefault").toString();
				} finally {

				}
				String id = videoObject.getString("id");

				Video bo = new Video();
				bo.setTitle(titles);
				bo.setId(id);
				bo.setThumbnail(image);
				bo.setPosition(position);
				bo.setTotalVideos(totalvideos);

				playListVideosLists.add(bo);

			}

		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}
		return playListVideosLists;

	}
}
