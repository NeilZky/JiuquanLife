package com.jiuquanlife.module.company.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class SharePreferenceHelp {
	public static SharePreferenceHelp INSTANCE;
	public static final String COMPANY_SEARCH_HISTORY = "COMPANY_SEARCH_HISTORY";
	public static final String PUBLISH_SEARCH_HISTORY = "PUBLISH_SEARCH_HISTORY";

	private SharedPreferences preferences;

	private SharePreferenceHelp() {
	}

	public static synchronized SharePreferenceHelp getInstance(Context context) {
		if (INSTANCE == null) {
			INSTANCE = new SharePreferenceHelp();
			INSTANCE.preferences = PreferenceManager
					.getDefaultSharedPreferences(context);
		}
		return INSTANCE;

	}

	public void saveSearchHistory(String text, String type) {
		Set<String> history = preferences.getStringSet(type,
				new HashSet<String>());
		if (history.size() >= 6) {
			for (Iterator it = history.iterator(); it.hasNext();) {
				String value = (String) it.next();
				it.remove();
				break;
			}
		}
		history.add(text);
		Editor editor = preferences.edit();
		editor.putStringSet(type, history);
		editor.commit();
	}

	public void clearSearchHiostory(String type) {
		Editor editor = preferences.edit();
		editor.putStringSet(type, new HashSet<String>());
		editor.commit();
	}

	public Set<String> getSearchHistory(String type) {
		return preferences.getStringSet(type, new HashSet<String>());
	}
}
