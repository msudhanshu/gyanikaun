package com.example.android.gameplay;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.apis.BaseActivity;
import com.example.android.apis.R;


public  class CorrectAnswerActivity extends BaseActivity implements View.OnClickListener{

private TextView resultviewed;
private Button buttonResult;
public static MediaPlayer correct,levelfinished,gamefinished;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  
	  correct = MediaPlayer.create(this, R.raw.clap2);
	  levelfinished = MediaPlayer.create(this, R.raw.clap3);
	  gamefinished = MediaPlayer.create(this, R.raw.clap4);
	  buttonResult = (Button) findViewById(R.id.bresult);
  
	  setContentView(R.layout.correctanswer);
	  buttonResult.setOnClickListener(this);
	  correct.start();
	}
	
	
	public void  onClick(View v) {
		// TODO Auto-generated method stub
		  if (buttonResult.equals(v)) {
			  finish();
	         }
		
	    
	}

}
