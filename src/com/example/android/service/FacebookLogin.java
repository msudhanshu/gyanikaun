package com.example.android.service;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.apis.Config;
import com.example.android.apis.R;
import com.example.android.gameplay.PlayActivity;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.android.SessionEvents;
import com.facebook.android.SessionEvents.AuthListener;
import com.facebook.android.SessionEvents.LogoutListener;

public class FacebookLogin extends Activity implements View.OnClickListener{
	private Button mButtonFbpost;
	private static final String TOKEN = "access_token";
	private static final String EXPIRES = "expires_in";
	private static final String KEY = "facebook-session";
	private Context currentContext = null;
	private IFacebookLogin iFBLogin;
	private static Facebook facebook = null;
	private static FacebookLogin mInstance= null;
	private Handler mFBShareHandler = new Handler();
	private Bundle mFBMsgParams = null;
	
	
	  @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main);
	       
	        mButtonFbpost = (Button)findViewById(R.id.fbpost);
	        mButtonFbpost.setOnClickListener(this);
	        
	        facebook.authorize(this, new DialogListener() {
	            @Override
	            public void onComplete(Bundle values) {}

	            @Override
	            public void onFacebookError(FacebookError error) {}

	            @Override
	            public void onError(DialogError e) {}

	            @Override
	            public void onCancel() {}
	        });
	    }



		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
			facebook.authorizeCallback(requestCode, resultCode, data);
		}
		
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			  if (mButtonFbpost.equals(v)) {
				  fbShare();
		         }
			 
		}
		
		
	/**
	 * @Properties:
	 * Email,UserName,AccessToken,EmailId
	 **/
	private String emailid = "";
	private void setUserEmailId(String email){
		this.emailid = email;
	}
	public String getEmailId(){
		return emailid;
	}

	private String mUserName = "";
	private void setUserName(String userName){
		this.mUserName = userName;
	}
	public String getUsername(){
		return mUserName;
	}

	private String mAccessToken= "";
	private void setAccessToken(String userName){
		this.mAccessToken = userName;
	}
	public String getAccessToken(){
		return mAccessToken;
	}

	private String mUserId= "";
	private void setUserId(String userId){
		this.mUserId = userId;
	}
	public String getUserId(){
		return mUserId;
	}
	/**
	 * 
	 *
	 * Interface exposed for TODO after sucessful Authorization.
	 *
	 */
	public interface IFacebookLogin{
		//public void OnFBLoginBtnClick();
		public String OnAuthrizationSuccess();
		public void OnAuthorizationFailed();
	}
	/**
	 * To be executed on UI Thread.Posted by handler after wall post success.
	 */
	final Runnable mUpdateFBNotifiction = new Runnable() {

		public void run() {
			// TODO Auto-generated method stub
			try
			{
				Toast.makeText(currentContext, "Facebook updated !", Toast.LENGTH_LONG).show();
				if(threadFBShare != null){
					threadFBShare.interrupt();
				}
				
			}catch(Exception ex){}
		}
	};
	/**
	 * Default constructor.
	 */
	public FacebookLogin(){
		//clearSession();
		facebook = new Facebook(Config.GYANIKAUN_FB_APP_ID);
		 AsyncFacebookRunner mAsyncRunner = new AsyncFacebookRunner(facebook);
		//Will get session variables if present in shared prefrences
		SessionEvents.addAuthListener(new FbAPIsAuthListener());
		SessionEvents.addLogoutListener(new FbAPIsLogoutListener());
		//login(this);
	}
	public static FacebookLogin getInstance()
	{
		if(mInstance == null || !facebook.isSessionValid())
			mInstance = new FacebookLogin();
		return mInstance;
	}

	public void logout(){
		if(facebook.isSessionValid())
		{
			try {
				facebook.logout(currentContext);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	/**
	 * Will check for valid session.If user has logged in and his session is valid,interface method will be called.Otherwise will ask for loginid and password.
	 * @param currentActivity
	 * @param _iFacebookLogin Interface exposing method OnAuthorizationSuccess. 
	 */
	public void login(Activity currentActivity,IFacebookLogin iFacebookLogin){
		iFBLogin = iFacebookLogin;
		if(facebook.isSessionValid()){
			iFBLogin.OnAuthrizationSuccess();
		}
		else
		{
			currentContext = currentActivity.getApplicationContext();
			if(!facebook.isSessionValid()){
				// mPrefs = getPreferences(MODE_PRIVATE);
				facebook.authorize(currentActivity,new String[] { "email", "offline_access", "publish_checkins","publish_stream" }, new DialogListener() {
					public void onComplete(Bundle values) {
						//saveSession(facebook);//Not using now.
						requestUserData();
						iFBLogin.OnAuthrizationSuccess();
					}

					public void onFacebookError(FacebookError error) {}

					public void onError(DialogError e) {}

					public void onCancel() {}
				});
			}
		}
	}
	
	public void login(Activity currentActivity) {

		if(facebook.isSessionValid()){
		}
		else
		{
		//	currentContext = currentActivity.getApplicationContext();
			if(!facebook.isSessionValid()){
				// mPrefs = getPreferences(MODE_PRIVATE);
				facebook.authorize(this,new DialogListener() {
					public void onComplete(Bundle values) {
						//saveSession(facebook);//Not using now.
						requestUserData();
						iFBLogin.OnAuthrizationSuccess();
					}

					public void onFacebookError(FacebookError error) {}

					public void onError(DialogError e) {}

					public void onCancel() {}
				});
			}
		}
	}
	/**
	 * Will check for valid session.If user has logged in and his session is valid will start a new thread for making HTTP call to FBAPI.
	 * @param currentActivity
	 * @param pMsg Message to be posted on facebook wall.
	 */
	public void postOnWall(Bundle pFBMsgBundle){
		mFBMsgParams = pFBMsgBundle;
	//	if (facebook.isSessionValid()) {
			postMessageOnWall();
		//}
		/*else
		{
			facebook.authorize(currentActivity,new String[] { "email", "offline_access", "publish_checkins","publish_stream" }, new DialogListener() {
				public void onComplete(Bundle values) {
					//saveSession(facebook);//Not using now.
					postMessageOnWall();
				}

				public void onFacebookError(FacebookError error) {}

				public void onError(DialogError e) {}

				public void onCancel() {}
			});
		}*/
	}
	Thread threadFBShare;
	private void postMessageOnWall(){
		threadFBShare = new Thread(){
			public void run(){
				try
				{
					try {
						String response = facebook.request("me/feed", mFBMsgParams,"POST");
						mFBShareHandler.post(mUpdateFBNotifiction);
					} 
					catch (IOException e) {
						e.printStackTrace();
					}
				}
				catch(Exception ex){
					Log.e("FBShare", ex.toString());
				}
			}
		};
		threadFBShare.start();
	}
	private void requestUserData() {
		Bundle params = new Bundle();
		JSONObject jsonObject;
		params.putString("fields", "name, picture,email,id");
		try {
			String jsonResponse = facebook.request("me", params);
			try {
				jsonObject = new JSONObject(jsonResponse);
				this.setUserEmailId(jsonObject.getString("email"));
				this.setUserName(jsonObject.getString("name"));
				this.setUserId(jsonObject.getString("id"));
				if(facebook.getAccessToken() != null){
					this.setAccessToken(facebook.getAccessToken());
				}
				else
				{

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * The Callback for notifying the application when authorization
	 *  succeeds or fails.
	 */

	public class FbAPIsAuthListener implements AuthListener {

		public void onAuthSucceed() {
			//requestUserData();
			iFBLogin.OnAuthrizationSuccess();
		}

		public void onAuthFail(String error) {
			iFBLogin.OnAuthorizationFailed();
		}
	}

	/*
	 * The Callback for notifying the application when log out
	 *  starts and finishes.
	 */
	public class FbAPIsLogoutListener implements LogoutListener {
		public void onLogoutBegin() {
		}

		public void onLogoutFinish() {

		}
	}
	
	private void fbShare(){
		Bundle fbParams = new Bundle();
		//prepareMsg(fbParams);
		
			fbParams.putString("name","kkkk");
			fbParams.putString("link", "http://google.com");
			//If caption will not be there or caption will be "" FB will take default caption.So blank string
		//	fbParams.putString("caption", " ");
		//	fbParams.putString("description"," ");
			//String cuisineType = mAppState.getCuisineType();
		//	String picUrl = "http://www.punchh.com/images/cuisine_types/";
			
			//	picUrl +="DefaultBusinessLogo.png";
			
		//	fbParams.putString("picture",picUrl);
		//	fbParams.putString("message", "mShareThoughts");
			
		
			postOnWall(fbParams);
		//Check whether its current login is wid fb

		//FacebookLogin.getInstance().postOnWall(fbParams);
		
			
		//Intent intent = new Intent(Constants.INTENT_ACTION_LOCATION_LIST_VIEW);
		//startActivity(intent);
	}
	
}
