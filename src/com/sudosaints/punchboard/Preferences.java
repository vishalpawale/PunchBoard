package com.sudosaints.punchboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class Preferences {
	
	private static final String IS_DEMO_DONE = "IS_DEMO_DONE";
	
	private Context context;

	public Preferences(Context context) {
		super();
		this.context = context;
	}
		
	protected SharedPreferences getSharedPreferences(String key) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}

	private String getString(String key, String def) {
		SharedPreferences prefs = getSharedPreferences(key);
		String s = prefs.getString(key, def);
		return s;
	}

	private int getInt(String key, int def) {
		SharedPreferences prefs = getSharedPreferences(key);
		int i = Integer.parseInt(prefs.getString(key, Integer.toString(def)));
		return i;
	}

	private float getFloat(String key, float def) {
		SharedPreferences prefs = getSharedPreferences(key);
		float f = Float.parseFloat(prefs.getString(key, Float.toString(def)));
		return f;
	}

	private long getLong(String key, long def) {
		SharedPreferences prefs = getSharedPreferences(key);
		long l = Long.parseLong(prefs.getString(key, Long.toString(def)));
		return l;
	}

	private void setString(String key, String val) {
		SharedPreferences prefs = getSharedPreferences(key);
		Editor e = prefs.edit();
		e.putString(key, val);
		e.commit();
	}

	private void setBoolean(String key, boolean val) {
		SharedPreferences prefs = getSharedPreferences(key);
		Editor e = prefs.edit();
		e.putBoolean(key, val);
		e.commit();
	}

	private void setInt(String key, int val) {
		SharedPreferences prefs = getSharedPreferences(key);
		Editor e = prefs.edit();
		e.putString(key, Integer.toString(val));
		e.commit();
	}

	private void setLong(String key, long val) {
		SharedPreferences prefs = getSharedPreferences(key);
		Editor e = prefs.edit();
		e.putString(key, Long.toString(val));
		e.commit();
	}

	private boolean getBoolean(String key, boolean def) {
		SharedPreferences prefs = getSharedPreferences(key);
		boolean b = prefs.getBoolean(key, def);
		return b;
	}

	private int[] getIntArray(String key, String def) {
		String s = getString(key, def);
		int[] ia = new int[s.length()];
		for (int i = 0; i < s.length(); i++) {
			ia[i] = s.charAt(i) - '0';
		}
		return ia;
	}


	/*
	 * Public methods to get/set prefs
	 */
	
	public boolean getIsDemoDone() {
		return getBoolean(IS_DEMO_DONE, false);
	}
	
	public void setIsDemoDone(boolean isDemoDone) {
		setBoolean(IS_DEMO_DONE, isDemoDone);
	}
}
