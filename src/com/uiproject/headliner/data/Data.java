package com.uiproject.headliner.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.uiproject.headliner.R;

import android.content.Context;
import android.database.Cursor;

public class Data {

	public static final String TOPICS = "TOPICS";
	public static final String TAB_KEY = "com.uiproject.headliner.topic";
	public static final String CHECKED = "checked";
	public static String topics[] = { "Trending", "National", "International",
			"Sports", "Local" };
	
	public static String location = "Minneapolis";

	public static Boolean flag = true;

	public static ArrayList<HashMap<String, Object>> topicList;

	public static List<String> trendingTopics;
	public static List<List<HashMap<String, Object>>> trendingChildList;
	public static List<HashMap<String, Object>> trendingFavorList;
	public static List<HashMap<String, Object>> trendingSearchList;

	public static List<HashMap<String, Object>> nationalList;
	public static List<HashMap<String, Object>> nationalFavorlList;
	public static List<HashMap<String, Object>> nationalSearchlList;

	public static List<HashMap<String, Object>> internationalList;
	public static List<HashMap<String, Object>> internationalFavorList;
	public static List<HashMap<String, Object>> internationalSearchList;

	public static List<HashMap<String, Object>> sportList;
	public static List<HashMap<String, Object>> sportFavorList;
	public static List<HashMap<String, Object>> sportSearchList;

	public static List<HashMap<String, Object>> localList;
	public static List<HashMap<String, Object>> localFavorList;
	public static List<HashMap<String, Object>> localSearchList;

	private static void searchDBTopic(DBHelper dbHelper) {
		Cursor c = dbHelper.queryTopic();
		while (c.moveToNext()) {
			int checked = c.getInt(1);
			String topic = c.getString(2);
			HashMap<String, Object> map = new HashMap<String, Object>();
			if (checked == 1)
				map.put("checked", true);
			else
				map.put("checked", false);
			map.put(TOPICS, topic);
			System.out.println(topic);
			topicList.add(map);
		}
	}
	
	private static void searchDBFavor(DBHelper dbHelper, 
			List<HashMap<String, Object>> list,
			String category) {
		Cursor c = dbHelper.queryNews(category);
		System.out.println("get favorite news");
		while(c.moveToNext()) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			System.out.println(c.getString(1));
			map.put("starBox", true);
			map.put("news", c.getString(1));
			map.put("url", c.getString(2));
			map.put("image", c.getInt(4));
			list.add(map);
		}
	}

	public static void init(Context context) {

		if (!flag)
			return;
		flag = false;

		DBHelper dbHelper = new DBHelper(context);

		topicList = new ArrayList<HashMap<String, Object>>();
		searchDBTopic(dbHelper);

		if (topicList.size() == 0) {
			for (int i = 0; i < topics.length; i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("checked", true);
				map.put(TOPICS, topics[i]);
				topicList.add(map);
			}
		}

		trendingTopics = new ArrayList<String>();
		for(int i = 0; i < News.Trending_News.trending_topics.length; i++) {
			trendingTopics.add(News.Trending_News.trending_topics[i]);
		}
		trendingChildList = new ArrayList<List<HashMap<String, Object>>>();
		
		ArrayList<HashMap<String, Object>> trendingChildItem = new ArrayList<HashMap<String, Object>>();
		insert(trendingChildItem, News.Trending_News.Fiscal_Cliff_news,
				News.Trending_News.Fiscal_Cliff_url, R.drawable.ic_launcher,
				trendingFavorList);
		trendingChildList.add(trendingChildItem);
		
		trendingChildItem = new ArrayList<HashMap<String, Object>>();
		insert(trendingChildItem, News.Trending_News.Kate_Middleton_news,
				News.Trending_News.Kate_Middleton_url, R.drawable.ic_launcher,
				trendingFavorList);
		trendingChildList.add(trendingChildItem);
		
		trendingChildItem = new ArrayList<HashMap<String, Object>>();
		insert(trendingChildItem, News.Trending_News.Mall_Shooting_news,
				News.Trending_News.Mall_Shooting_urls, R.drawable.ic_launcher,
				trendingFavorList);
		trendingChildList.add(trendingChildItem);
		
		trendingFavorList = new ArrayList<HashMap<String, Object>>();
		searchDBFavor(dbHelper, trendingFavorList, "Trending");
		
		nationalList = new ArrayList<HashMap<String, Object>>();
		nationalFavorlList = new ArrayList<HashMap<String, Object>>();
		searchDBFavor(dbHelper, nationalFavorlList, "National");

		internationalList = new ArrayList<HashMap<String, Object>>();
		internationalFavorList = new ArrayList<HashMap<String, Object>>();
		searchDBFavor(dbHelper, internationalFavorList, "International");

		sportList = new ArrayList<HashMap<String, Object>>();
		sportFavorList = new ArrayList<HashMap<String, Object>>();
		searchDBFavor(dbHelper, sportFavorList, "Sports");

		localList = new ArrayList<HashMap<String, Object>>();
		localFavorList = new ArrayList<HashMap<String, Object>>();
		searchDBFavor(dbHelper, localFavorList, "Local");

		// insert national news
		insert(nationalList, News.National_News.CNN_National_news,
				News.National_News.CNN_National_url, R.drawable.cnn_news,
				nationalFavorlList);
		insert(nationalList, News.National_News.FoxNews_National_news,
				News.National_News.FoxNews_National_url, R.drawable.fox_news,
				nationalFavorlList);
		insert(nationalList, News.National_News.newyorktimes_National_news,
				News.National_News.newyorktimes_National_url,
				R.drawable.thenewyorktimes_news,
				nationalFavorlList);
		insert(nationalList, News.National_News.WashingtonPost_National_news,
				News.National_News.WashingtonPost_National_url,
				R.drawable.wpt_news,
				nationalFavorlList);

		// insert local news
		insert(localList, News.Local_News.StarTribune_Local_news,
				News.Local_News.StarTribune_Local_url,
				R.drawable.startribune_news,
				localFavorList);

		// insert international news
		insert(internationalList, News.World_News.ABC_world_news,
				News.World_News.ABC_world_url, R.drawable.abc_world_news,
				internationalFavorList);
		insert(internationalList, News.World_News.BBC_world_news,
				News.World_News.BBC_world_url, R.drawable.bbc_world_news,
				internationalFavorList);
		insert(internationalList, News.World_News.Bloomberg_World_news,
				News.World_News.Bloomberg_World_url, R.drawable.bloomberg_news,
				internationalFavorList);
		insert(internationalList, News.World_News.CBSnews_world_news,
				News.World_News.CBSnews_world_url, R.drawable.cbs_news,
				internationalFavorList);
		insert(internationalList, News.World_News.CNN_world_news,
				News.World_News.CNN_world_url, R.drawable.cnn_world_news,
				internationalFavorList);
		insert(internationalList, News.World_News.Reuters_World_news,
				News.World_News.Reuters_World_url, R.drawable.reuters,
				internationalFavorList);
	}

	private static void insert(List<HashMap<String, Object>> list,
			String[] news, String[] url, int drawable, 
			List<HashMap<String, Object>> favorList) {
		for (int i = 0; i < news.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("starBox", false);		
			map.put("news", news[i]);
			map.put("url", url[i]);
			map.put("image", (Integer) drawable);
			list.add(map);
		}
	}

	public static void search(String key) {
		key = key.toLowerCase();
		
//		trendingSearchList = new ArrayList<HashMap<String, Object>>();
//		for (int i = 0; i < trendingList.size(); i++) {
//			HashMap<String, Object> map = trendingList.get(i);
//			String news = ((String) map.get("news")).toLowerCase();
//			if (news.indexOf(key) != -1) {
//				trendingSearchList.add(map);
//			}
//		}

		nationalSearchlList = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < nationalList.size(); i++) {
			HashMap<String, Object> map = nationalList.get(i);
			String news = ((String) map.get("news")).toLowerCase();
			if (news.indexOf(key) != -1) {
				nationalSearchlList.add(map);
			}
		}

		internationalSearchList = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < internationalList.size(); i++) {
			HashMap<String, Object> map = internationalList.get(i);
			String news = ((String) map.get("news")).toLowerCase();
			if (news.indexOf(key) != -1) {
				internationalSearchList.add(map);
			}
		}

		sportSearchList = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < sportList.size(); i++) {
			HashMap<String, Object> map = sportList.get(i);
			String news = ((String) map.get("news")).toLowerCase();
			if (news.indexOf(key) != -1) {
				sportSearchList.add(map);
			}
		}

		localSearchList = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < localList.size(); i++) {
			HashMap<String, Object> map = localList.get(i);
			String news = ((String) map.get("news")).toLowerCase();
			if (news.indexOf(key) != -1) {
				localSearchList.add(map);
			}
		}
	}
	
	public static void changeLocation(String _location) {
		location = _location;
		
		if(location.equals("Minneapolis")) {
			localList = new ArrayList<HashMap<String, Object>>();
			insert(localList, News.Local_News.StarTribune_Local_news,
					News.Local_News.StarTribune_Local_url,
					R.drawable.startribune_news,
					localFavorList);
		} else {
			localList = new ArrayList<HashMap<String, Object>>();
			insert(localList, News.Local_News.local_10_WPLG_News,
					News.Local_News.local_10_WPLG_url,
					R.drawable.local10_wplgnews,
					localFavorList);
		}
	}

}
