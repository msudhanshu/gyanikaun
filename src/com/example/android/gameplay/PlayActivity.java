package com.example.android.gameplay;



import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewDebug.IntToString;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.apis.BaseActivity;
import com.example.android.apis.Config;
import com.example.android.apis.R;
import com.example.android.database.QuestionAnswerDbAdapter;
import com.example.android.database.Questiondata;
import com.example.android.server.HttpRetriever;
import com.example.android.server.JSONParsing;
import com.example.android.service.FacebookLogin;

public class PlayActivity extends BaseActivity implements View.OnClickListener{

	public QuestionAnswerDbAdapter dbHelper;
	private Cursor cursor;
	Questiondata qdata = null;
	private Button questionButton;
	private Button optionAButton;
	private Button optionBButton;
	private Button optionCButton;
	private Button optionDButton;
	private TextView resultviewed;
	private Button buttonResultviewed;
	public JSONParsing mJSJsonParsing;
	public int questionnumber = 1;
	public boolean SERVER_FLAG = true;
	Bundle fbParams = null;
	public static MediaPlayer mp,a,up,correct,levelfinished,startsound,gamefinished;
	   /** Called when the activity is first created. */
@Override
public void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  up = MediaPlayer.create(this, R.raw.up);
  a = MediaPlayer.create(this, R.raw.a);
  startsound = MediaPlayer.create(this, R.raw.kbc_start); 
  correct = MediaPlayer.create(this, R.raw.clap2);
  levelfinished = MediaPlayer.create(this, R.raw.clap3);
  gamefinished = MediaPlayer.create(this, R.raw.clap4);
  
  
  setContentView(R.layout.startgame);
  startsound.start();
  
	dbHelper = new QuestionAnswerDbAdapter(this);
	dbHelper.open();


 mJSJsonParsing = new JSONParsing(this);
//filldata();


//in future it is one time job , if local database down is supported
//it get question data from json file and populate local database and cursor
mJSJsonParsing.getQuestionData();

	//getNextQuestion();

//Document document = Jsoup.connect("http://stackoverflow.com/questions/2971155").get();
//String question = document.select("#question .post-text p").first().text();
//System.out.println(question);
	
    cursor = dbHelper.fetchAllQuestions();
	questionButton = (Button) findViewById(R.id.buttonQuestion);
	optionAButton = (Button) findViewById(R.id.buttonOptionA);
	optionBButton = (Button) findViewById(R.id.buttonOptionB);
	optionCButton = (Button) findViewById(R.id.buttonOptionC);
	optionDButton = (Button) findViewById(R.id.buttonOptionD);
	
	questionButton.setOnClickListener(this);
	optionAButton.setOnClickListener(this);
	optionBButton.setOnClickListener(this);
	optionCButton.setOnClickListener(this);
	optionDButton.setOnClickListener(this);

	getNextQuestion(SERVER_FLAG); //false in case of question from local databases
}


public void onClick(View v) {
	// TODO Auto-generated method stub
	  if (optionAButton.equals(v)) {
		  checksolution("A");
         }
	  if (optionBButton.equals(v)) {
		  checksolution("B");
         }	
	  if (optionCButton.equals(v)) {
		  checksolution("C");

         }	
	  if (optionDButton.equals(v)) {
		  checksolution("D");
		  
			//Intent i = new Intent(this, SettingActivity.class);
			//startActivity(i);
         }	
	  
}

private void checksolution(String sol) {
	
	//Intent i = new Intent(this, SettingActivity.class);
	//startActivity(i);
	boolean returnresult;
	  resultviewed = (TextView)findViewById(R.id.textresult);
	  buttonResultviewed = (Button)findViewById(R.id.buttonResultviewed);
	  
	if (sol.equals(qdata.getAnswer() )) {
		returnresult = true;
		resultviewed.setText("Congrats");
		buttonResultviewed.setText("Congrats");

	}
	else {
		returnresult = false;
		resultviewed.setText("Sorry");
		buttonResultviewed.setText("Sorry");
	}
	
	  AnimationSet set = new AnimationSet(true);
	  Animation animation = new AlphaAnimation(0.0f, 1.0f);
	  animation.setDuration(100);
	  set.addAnimation(animation);
	  animation = new TranslateAnimation(
	      Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
	      Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f
	  );
	  animation.setDuration(500);
	  set.addAnimation(animation);

	  // ........... for view group ......
	 // LayoutAnimationController controller = new LayoutAnimationController(set, 0.25f);
	  //OR
	  //LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(this,android.R.anim.fade_out);
	  //questionButton.setLayoutAnimation(controller);
	  
	  
	  // ... .. for View .....
	 // Animation vanimation = new Animation(set,0.25f);
	 // findViewById(R.id.showresult).setVisibility(View.VISIBLE);
	  Animation vanimation = AnimationUtils.loadAnimation(this,android.R.anim.fade_out);
	  

	  //  setContentView(R.layout.showresult);
	  
	// Demonstrate fading and adding an AnimationListener

//				float dest = 1;
//				if (aniView.getAlpha() > 0) {
//					dest = 0;
//				}
//				ObjectAnimator animation3 = ObjectAnimator.ofFloat(aniView,"alpha", dest);
//				animation3.setDuration(2000);
//				animation3.start();
				
   if(returnresult) {   
		  findViewById(R.id.showresult).setVisibility(View.VISIBLE);
			//play clap sound 
		   correct.start();
		  findViewById(R.id.showresult).startAnimation(vanimation);
		 // wait(2);
		  findViewById(R.id.showresult).setVisibility(View.INVISIBLE);
	   
	//   Intent i1 = new Intent(this, CorrectAnswerActivity.class);
	  // startActivity(i1);
		  questionnumber++;
	   if(!getNextQuestion(SERVER_FLAG)) 
	   {
		   
		   //TODO : show done congrats message
		   //Temp
		   questionnumber = 1;
			Intent ii = new Intent(this, PrizeActivity.class);
			ii.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			gamefinished.start();
			startActivity(ii);
	   }
   }
   else {
//		  findViewById(R.id.showresult).setVisibility(View.VISIBLE);
//			//play clap sound 
//		  // incorrect.start();
//		  findViewById(R.id.showresult).startAnimation(vanimation);
//		  //wait(2);
//		  findViewById(R.id.showresult).setVisibility(View.INVISIBLE);
	   
	   
//		  sorry screen first then final result
	     Intent ii = new Intent(this, PrizeActivity.class);
	     ii.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    	gamefinished.start();
		 startActivity(ii);
	   
   }
}

private boolean getNextQuestion(boolean fromServer) {
	//cursor = dbHelper.fetchAllQuestions();
	if (!fromServer)
	{
	cursor.moveToNext();
	if (cursor != null && !cursor.isAfterLast()) {
	//cursor.moveToFirst();
	//while (!cursor.isAfterLast()) {
		qdata = cursorTodata(cursor);
		//cursor.moveToNext();
	//}
	} else {
		
		return false;
	}
	
	}
	else 
	{
		String jsonquest = HttpRetriever.retrieveFeeds(Config.NEXT_QUESTION_URL+Integer.toString(questionnumber));
		//System.out.print(jsonquest);
		qdata=mJSJsonParsing.getQuestiondatafromJsonString(jsonquest);
		
	}
	displayQuestion();
	
	return true;
	// Make sure to close the cursor
	//cursor.close();
	
	
	//startManagingCursor(cursor);
	//String mcat = cursor.getString(cursor.getColumnIndexOrThrow(QuestionAnswerDbAdapter.KEY_QUESTION));
}

private void displayQuestion() {
	
	if (qdata!=null) {
	questionButton.setText("Q. " + qdata.getQuestion());
	optionAButton.setText("A. " + qdata.getOptionA());
	optionBButton.setText("B. " + qdata.getOptionB());
	optionCButton.setText("C. " + qdata.getOptionC());
	optionDButton.setText("D. " + qdata.getOptionD());
	
	}
	
}

private Questiondata cursorTodata(Cursor cursor) {
	Questiondata tdata = new Questiondata();
	//qdata.setQuestion(question)(cursor.getLong(0));
	tdata.setQuestion( cursor.getString(cursor.getColumnIndexOrThrow(QuestionAnswerDbAdapter.KEY_QUESTION)) );
	tdata.setOptionA(cursor.getString(2));
	tdata.setOptionB(cursor.getString(3));
	tdata.setOptionC(cursor.getString(4));
	tdata.setOptionD(cursor.getString(5));
	tdata.setAnswer(cursor.getString(cursor.getColumnIndexOrThrow(QuestionAnswerDbAdapter.KEY_ANSWER)));
	
//	qdata.setResolink(curs)
	return tdata;
}

private void fbShare(){
	fbParams = new Bundle();
	//prepareMsg(fbParams);
	
		fbParams.putString("name","kkkk");
		fbParams.putString("link", "http://punchh.com");
		//If caption will not be there or caption will be "" FB will take default caption.So blank string
		fbParams.putString("caption", " ");
		fbParams.putString("description"," ");
		//String cuisineType = mAppState.getCuisineType();
		String picUrl = "http://www.punchh.com/images/cuisine_types/";
		
			picUrl +="DefaultBusinessLogo.png";
		
		fbParams.putString("picture",picUrl);
		fbParams.putString("message", "mShareThoughts");
		
	
	
	//Check whether its current login is wid fb

		FacebookLogin.getInstance().postOnWall(fbParams);
	
		
	//Intent intent = new Intent(Constants.INTENT_ACTION_LOCATION_LIST_VIEW);
	//startActivity(intent);
}
//private void LoginAndPost(){
//	
//	final FacebookLogin fbLogin = FacebookLogin.getInstance();
//	//fbLogin.LoginWithFacebook(Login.this);
//	fbLogin.login(FBShare.this,new IFacebookLogin() {
//		public String OnAuthrizationSuccess() {
//			// TODO Auto-generated method stub
//			//Get user details
//			String username = fbLogin.getUsername();
//			String[] arrSplitedNames = username.split("\\s+");
//			final String firstName = arrSplitedNames[0];
//			final String lastName = arrSplitedNames[arrSplitedNames.length - 1];
//			final String password = fbLogin.getUserId().substring(0,6);
//
//			//Call API
//			AsyncHandler handler = new AsyncHandler(FBShare.this);
//			handler.SetBackgroundTaskListener(new iBackgroundTask() {
//
//				public void OnBackgroundTaskCompleted() {
//					// TODO Auto-generated method stub
//					runOnUiThread(new Runnable() {
//
//						public void run() {
//							if(mFBUserId != 0){
//								// TODO Auto-generated method stub
//								mCurrentUser.setUserId(mFBUserId);
//								mCurrentUser.setSignInMode("fb");
//								mCurrentUser.setemail(fbLogin.getEmailId());
//								mCurrentUser.setFirstName(firstName);
//								mCurrentUser.setLastName(lastName);
//								mCurrentUser.setPassWord(password);
//								updatePreferences(mCurrentUser);
//							}
//							fbLogin.postOnWall(fbParams);
//						//	Intent intent = new Intent(Constants.INTENT_ACTION_LOCATION_LIST_VIEW);
//							//startActivity(intent);
//						}
//					});
//				}
//
//				public void DoBackgroundTask(String[] url) {
//					// TODO Auto-generated method stub
//				//	mFBUserId =  feedManager.createUserWithFacebook(firstName,lastName,fbLogin.getEmailId(),password,fbLogin.getUserId(),fbLogin.getAccessToken(),ConstantsFunc.getDeviceId(FBShare.this));
//				}
//			});
//			handler.execute("");
//			return null;
//		}
//		public void OnAuthorizationFailed() {
//			// TODO Auto-generated method stub
//			//Toast.makeText(this.c, "Autorization failed.UserId or password is invalid.", Toast.LENGTH_SHORT).show();
//			return;
//		}
//	});
//}

void filldata() {
	
	Questiondata tq = new Questiondata("When is the birthday of BhanuD", "13March", "13May", "23March", "23May", "A", "NO", "NO", "1", "0");
	dbHelper.createQuestion(tq);
}

}
