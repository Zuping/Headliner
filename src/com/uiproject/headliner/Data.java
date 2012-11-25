package com.uiproject.headliner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.graphics.drawable.Drawable;

public class Data {
	
	public static final String TOPICS = "TOPICS";
	public static final String TAB_KEY = "com.uiproject.headliner.topic";
	public static final String CHECKED = "checked";
	public static String topics[] = { "Trending", "National", "International",
		"Sports", "Local"};
	
	public static Boolean flag = true;
	
	public static List<HashMap<String, Object>> topicList;
	
	public static List<HashMap<String, Object>> trendingList;
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
	
	
	public static void init() {
		
		if(!flag) return;
		flag = false;
		
		topicList = new ArrayList<HashMap<String, Object>>();
		for(int i = 0; i < topics.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("checked", true);
			map.put(TOPICS, topics[i]);
			topicList.add(map);
		}
		
		trendingList = new ArrayList<HashMap<String, Object>>();
		trendingFavorList = new ArrayList<HashMap<String, Object>>();
		
		nationalList = new ArrayList<HashMap<String, Object>>();
		nationalFavorlList = new ArrayList<HashMap<String, Object>>();
		
		internationalList = new ArrayList<HashMap<String, Object>>();
		internationalFavorList = new ArrayList<HashMap<String, Object>>();
		
		sportList = new ArrayList<HashMap<String, Object>>();
		sportFavorList = new ArrayList<HashMap<String, Object>>();
		
		localList = new ArrayList<HashMap<String, Object>>();
		localFavorList = new ArrayList<HashMap<String, Object>>();
		
		// insert national news
		insert(nationalList, News.National_News.CNN_National_news, News.National_News.CNN_National_url, R.drawable.cnn_news);
		insert(nationalList, News.National_News.FoxNews_National_news, News.National_News.FoxNews_National_url, R.drawable.fox_news);
		insert(nationalList, News.National_News.newyorktimes_National_news, News.National_News.newyorktimes_National_url, R.drawable.thenewyorktimes_news);
		insert(nationalList, News.National_News.WashingtonPost_National_news, News.National_News.WashingtonPost_National_url, R.drawable.wpt_news);
		
		// insert local news
		insert(localList, News.Local_News.StarTribune_Local_news, News.Local_News.StarTribune_Local_url, R.drawable.startribune_news);
		
		// insert international news
		insert(internationalList, News.World_News.ABC_world_news, News.World_News.ABC_world_url, R.drawable.abc_world_news);
		insert(internationalList, News.World_News.BBC_world_news, News.World_News.BBC_world_url, R.drawable.bbc_world_news);
		insert(internationalList, News.World_News.Bloomberg_World_news, News.World_News.Bloomberg_World_url, R.drawable.bloomberg_news);
		insert(internationalList, News.World_News.CBSnews_world_news, News.World_News.CBSnews_world_url, R.drawable.cbs_news);
		insert(internationalList, News.World_News.CNN_world_news, News.World_News.CNN_world_url, R.drawable.cnn_world_news);
		insert(internationalList, News.World_News.Reuters_World_news, News.World_News.Reuters_World_url, R.drawable.reuters);
	}
	
	private static void insert(List<HashMap<String, Object>> list, String[] news, String[] url, int drawable) {
		for(int i = 0; i < news.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("starBox", false);
			map.put("news", news[i]);
			map.put("url", url[i]);
			map.put("image", (Integer) drawable);
			list.add(map);
		}
	}
	
	public static void search(String key) {
		trendingSearchList = new ArrayList<HashMap<String, Object>>();
		for(int i = 0; i < trendingList.size(); i++) {
			HashMap<String, Object> map = trendingList.get(i);
			String title = (String) map.get("title");
			String _abstract = (String) map.get("abstract");
			if(title.indexOf(key) != -1 || _abstract.indexOf(key) != -1) {
				trendingSearchList.add(map);
			}
		}
		
		nationalSearchlList = new ArrayList<HashMap<String, Object>>();
		for(int i = 0; i < nationalList.size(); i++) {
			HashMap<String, Object> map = nationalList.get(i);
			String title = (String) map.get("title");
			String _abstract = (String) map.get("abstract");
			if(title.indexOf(key) != -1 || _abstract.indexOf(key) != -1) {
				nationalSearchlList.add(map);
			}
		}
		
		internationalSearchList = new ArrayList<HashMap<String, Object>>();
		for(int i = 0; i < internationalList.size(); i++) {
			HashMap<String, Object> map = internationalList.get(i);
			String title = (String) map.get("title");
			String _abstract = (String) map.get("abstract");
			if(title.indexOf(key) != -1 || _abstract.indexOf(key) != -1) {
				internationalSearchList.add(map);
			}
		}
		
		sportSearchList = new ArrayList<HashMap<String, Object>>();
		for(int i = 0; i < sportList.size(); i++) {
			HashMap<String, Object> map = sportList.get(i);
			String title = (String) map.get("title");
			String _abstract = (String) map.get("abstract");
			if(title.indexOf(key) != -1 || _abstract.indexOf(key) != -1) {
				sportSearchList.add(map);
			}
		}
		
		localSearchList = new ArrayList<HashMap<String, Object>>();
		for(int i = 0; i < localList.size(); i++) {
			HashMap<String, Object> map = localList.get(i);
			String title = (String) map.get("title");
			String _abstract = (String) map.get("abstract");
			if(title.indexOf(key) != -1 || _abstract.indexOf(key) != -1) {
				localSearchList.add(map);
			}
		}
	}
	
}
