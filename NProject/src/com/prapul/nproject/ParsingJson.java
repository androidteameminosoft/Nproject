package com.prapul.nproject;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.util.Log;

public class ParsingJson {

	public static List<BreakingNews> parseNews(JSONArray json) {

		List<BreakingNews> playnewsLists = new ArrayList<BreakingNews>();
		try {

			for (int i = 0; i < json.length(); i++) {

				BreakingNews breakNews = new BreakingNews();

				JSONObject jObj = (JSONObject) json.get(i);
				breakNews.setTitle(jObj.getString("title"));
				breakNews.setDescription(jObj.getString("desc"));
				breakNews.setImagePath(jObj.getString("imgpath"));
				breakNews.setId(Integer.parseInt(jObj.getString("id")));
				breakNews.setAddedDate(jObj.getString("updatedon"));

				playnewsLists.add(breakNews);

			}

		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}
		return playnewsLists;

	}
}
