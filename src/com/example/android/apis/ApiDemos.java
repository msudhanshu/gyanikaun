package com.example.android.apis;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

public class ApiDemos extends Activity implements View.OnClickListener{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.main);
		Intent i = new Intent(this, WelcomeActivity.class);
		startActivity(i);
		//startActivityForResult(i, 0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
       
       switch (keyCode) {
       case KeyEvent.KEYCODE_DPAD_UP:
    	   break;
       default:
    	   
          return super.onKeyDown(keyCode, event);
       }
   //    mp.seekTo(0); 
     //  mp.start();
       return true;
    }

	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i = new Intent(this, BaseActivity.class);
		startActivity(i);
		//startActivityForResult(i, 0);
	}
       
	
}