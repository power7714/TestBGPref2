package com.example.testbgpref2;

import rajawali.wallpaper.Wallpaper;
import android.content.Context;
import android.content.SharedPreferences;

public class Service extends Wallpaper {
	private Renderer mRenderer;
	static SharedPreferences prefs;

	public Engine onCreateEngine() {
		mRenderer = new Renderer(this);
		return new WallpaperEngine(this.getSharedPreferences(SHARED_PREFS_NAME,
				Context.MODE_PRIVATE), getBaseContext(), mRenderer, false);
	}
}
