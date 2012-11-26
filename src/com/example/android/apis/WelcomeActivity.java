package com.example.android.apis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.gameplay.PlayActivity;
import com.example.android.service.FacebookLogin;


public class WelcomeActivity extends BaseActivity implements View.OnClickListener{
	
	private Button mButtonPlay;
	private Button mButtonSetting;
	private Button mButtonHelp;
	private Button mButtonInfo;
	private Button mButtonQuit;
//	private Button mButtonlevel1;
	
	   /** Called when the activity is first created. */
 @Override
 public void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     setContentView(R.layout.welcome);
     mButtonPlay = (Button)findViewById(R.id.buttonplay);
     mButtonPlay.setOnClickListener(this);
     mButtonSetting = (Button)findViewById(R.id.buttonsetting);
     mButtonSetting.setOnClickListener(this); 
     mButtonQuit = (Button)findViewById(R.id.buttonquit);
     mButtonQuit.setOnClickListener(this); 
 }

	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		  if (mButtonPlay.equals(v)) {
				Intent i = new Intent(this, PlayActivity.class);
				startActivity(i);
	         }
		  if (mButtonSetting.equals(v)) {
			//	Intent i = new Intent(this, SettingActivity.class);
			  Intent i = new Intent(this, FacebookLogin.class);
				startActivity(i);
	         }	
		  if (mButtonQuit.equals(v)) {
				this.finish();
	         }	
	}
 
 
}
