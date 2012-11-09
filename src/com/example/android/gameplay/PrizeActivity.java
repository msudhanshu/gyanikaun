package com.example.android.gameplay;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.apis.BaseActivity;
import com.example.android.apis.R;
import com.example.android.apis.WelcomeActivity;

public class PrizeActivity extends BaseActivity implements View.OnClickListener{
	private Button buttonOK;
	public static MediaPlayer correct,levelfinished,gamefinished;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.prize);
	  buttonOK = (Button) findViewById(R.id.bprize);
	  buttonOK.setOnClickListener(this);
	  gamefinished = MediaPlayer.create(this, R.raw.clap4);
	  gamefinished.start();
	}
	
	
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent ii = new Intent(this, WelcomeActivity.class);
		ii.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		gamefinished.start();
		startActivity(ii);
		
	}

}
