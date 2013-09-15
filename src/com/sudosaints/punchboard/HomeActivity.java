package com.sudosaints.punchboard;

import java.util.ArrayList;
import java.util.List;

import com.github.espiandev.showcaseview.ShowcaseView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.ext.SatelliteMenu;
import android.view.ext.SatelliteMenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class HomeActivity extends Activity{

	SatelliteMenu satelliteMenu;
	WebView webView;
	boolean redirect, loadingFinished;
	ProgressDialog progressDialog;
	List<String> urls;
	Long lastTouchMillis = System.currentTimeMillis();
	Handler handler = new Handler();
	Preferences preferences;
	DisplayMetrics displayMetrics;
	
	/*Thread thread = new Thread(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				Log.i("PunchBoard", lastTouchMillis+"  " + System.currentTimeMillis());
				Log.i("PunchBoard", (System.currentTimeMillis() - lastTouchMillis >= 3000) + " " + satelliteMenu.isOpened());
				if(!satelliteMenu.isOpened()) {
					if(System.currentTimeMillis() - lastTouchMillis >= 3000) {
						handler.post(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								satelliteMenu.setVisibility(View.GONE);
							}
						});
					} else {
						handler.post(new Runnable() {
							
							@Override
							public void run() {
								satelliteMenu.setVisibility(View.VISIBLE);
							}
						});
					}
				} else {
					lastTouchMillis = System.currentTimeMillis();
					handler.post(new Runnable() {
						
						@Override
						public void run() {
							satelliteMenu.setVisibility(View.VISIBLE);
						}
					});
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	});*/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_layout);
		preferences = new Preferences(this);
		
		satelliteMenu = (SatelliteMenu) findViewById(R.id.menu);
		satelliteMenu.setMainImage(getResources().getDrawable(R.drawable.sat_main));
		List<SatelliteMenuItem> satelliteMenuItems = new ArrayList<SatelliteMenuItem>();
		satelliteMenuItems.add(new SatelliteMenuItem(0, getResources().getDrawable(R.drawable.satelite_menu_tc)));
		satelliteMenuItems.add(new SatelliteMenuItem(1, getResources().getDrawable(R.drawable.satelite_menu_gz)));
		satelliteMenuItems.add(new SatelliteMenuItem(2, getResources().getDrawable(R.drawable.satelite_menu_eng)));
		satelliteMenuItems.add(new SatelliteMenuItem(3, getResources().getDrawable(R.drawable.satelite_menu_tv)));
		satelliteMenuItems.add(new SatelliteMenuItem(4, getResources().getDrawable(R.drawable.satelite_menu_yahoo)));
		
		satelliteMenu.addItems(satelliteMenuItems);
		urls = new ArrayList<String>();
		urls.add("http://www.techcrunch.com");
		urls.add("http://www.gizmodo.com");
		urls.add("http://www.engadget.com");
		urls.add("http://www.theverge.com");
		urls.add("http://www.yahoo.com");
		
		
		satelliteMenu.setOnItemClickedListener(new SatelliteMenu.SateliteClickedListener() {
			
			@Override
			public void eventOccured(int id) {
				// TODO Auto-generated method stub
				webView.loadUrl(urls.get(id));
			}
		});
		

		webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new CustomWebViewClient());

        webView.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				lastTouchMillis = System.currentTimeMillis();
				return false;
			}
		});
        
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        
        satelliteMenu.setFocusableInTouchMode(false);
        satelliteMenu.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				webView.onTouchEvent(event);
				return true;
			}
		});
		if(!preferences.getIsDemoDone()) {
			
			satelliteMenu.setClickable(false);
			satelliteMenu.setSatelliteDistance(Math.round(20 * displayMetrics.density));
			ShowcaseView.ConfigOptions configOptions = new ShowcaseView.ConfigOptions();
			ShowcaseView showcaseView = ShowcaseView.insertShowcaseView(satelliteMenu, this, "Menu Button", "Tap on bottom left icon to see all the menus", configOptions);
			showcaseView.setOnShowcaseEventListener(new ShowcaseView.OnShowcaseEventListener() {
				
				@Override
				public void onShowcaseViewShow(ShowcaseView showcaseView) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onShowcaseViewHide(ShowcaseView showcaseView) {
					// TODO Auto-generated method stub
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							satelliteMenu.setClickable(true);
							satelliteMenu.setSatelliteDistance(Math.round(200 * displayMetrics.density));
							start();
						}
					});
				}
			});
			preferences.setIsDemoDone(true);
		} else {
			start();
		}
        
	}
	
	private void start() {
		progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        webView.loadUrl(urls.get(0));
        lastTouchMillis = System.currentTimeMillis();
        //thread.setPriority(Thread.MIN_PRIORITY);
        //thread.start();
	}
	
	private class CustomWebViewClient extends WebViewClient {
    	
    	@Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	    	if (!loadingFinished) {
	    		redirect = true;
	    	}
	    	loadingFinished = false;
	    	view.loadUrl(url);
	    	return true;
	    }
	    
	    @Override
	    public void onPageFinished(WebView view, String url) {
	    	if(!redirect){
	    		loadingFinished = true;
	    	}

	    	if(loadingFinished && !redirect){
	    		//HIDE LOADING IT HAS FINISHED
	    		if(progressDialog != null && progressDialog.isShowing()) {
	    			progressDialog.dismiss();
	    		}
	    	} else{
	    		redirect = false; 
	    	}
	    }
	    
	    @Override
	    public void onPageStarted(WebView view, String url, Bitmap favicon) {
	        loadingFinished = false;
	        if(progressDialog != null && !progressDialog.isShowing()) {
	        	progressDialog.show();
	        }
	    }
	    
    }
}
