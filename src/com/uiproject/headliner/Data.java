package com.uiproject.headliner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
		
		for(int i = 0; i < 10; i++) {
			HashMap<String, Object> trending_map = new HashMap<String, Object>();
			trending_map.put("starBox", false);
			trending_map.put("title", "trending news " + i);
			trending_map.put("abstract",
					"trending trending trending trending trending trending");
			trending_map.put("date", "11/4/2012");
			trendingList.add(trending_map);
			
			HashMap<String, Object> national_map = new HashMap<String, Object>();
			national_map.put("starBox", false);
			national_map.put("title", "national news " + i);
			national_map.put("abstract",
					"national national national national national national");
			national_map.put("date", "11/4/2012");
			nationalList.add(national_map);
			
			HashMap<String, Object> international_map = new HashMap<String, Object>();
			international_map.put("starBox", false);
			international_map.put("title", "international news " + i);
			international_map.put("abstract",
					"international international international international international international");
			international_map.put("date", "11/4/2012");
			internationalList.add(international_map);
			
			HashMap<String, Object> sport_map = new HashMap<String, Object>();
			sport_map.put("starBox", false);
			sport_map.put("title", "sport news " + i);
			sport_map.put("abstract",
					"sport sport sport sport sport sport");
			sport_map.put("date", "11/4/2012");
			sportList.add(sport_map);
			
			HashMap<String, Object> local_map = new HashMap<String, Object>();
			local_map.put("starBox", false);
			local_map.put("title", "local news " + i);
			local_map.put("abstract",
					"local local local local local local");
			local_map.put("date", "11/4/2012");
			localList.add(local_map);			
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
