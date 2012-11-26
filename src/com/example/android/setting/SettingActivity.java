package com.example.android.setting;

import android.os.Bundle;

import com.example.android.apis.BaseActivity;
import com.example.android.apis.R;
import com.example.android.server.JSONParsing;
import com.example.android.service.FacebookLogin;

public class SettingActivity extends BaseActivity {
	   /** Called when the activity is first created. */
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.main);
//FacebookLogin.getInstance();//login(currentActivity, iFacebookLogin);;


JSONParsing mJSJsonParsing = new JSONParsing(this);

mJSJsonParsing.getQuestionData();

}


}
