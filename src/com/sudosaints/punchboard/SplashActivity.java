package com.sudosaints.punchboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.widget.ProgressBar;

public class SplashActivity extends Activity {

	ProgressBar progressBar;
	Handler handler = new Handler();
	int progressStatus = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					while(progressStatus < 100) {
						try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						progressStatus = progressStatus + 1;
						handler.post(new Runnable() {
							
							@Override
							public void run() {
								progressBar.setProgress(progressStatus);
							}
						});
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					startActivity(new Intent(SplashActivity.this, HomeActivity.class));
					finish();
				}
			}
		}).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

}
