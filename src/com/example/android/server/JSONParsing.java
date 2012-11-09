package com.example.android.server;

import java.io.OutputStreamWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.example.android.database.QuestionAnswerDbAdapter;
import com.example.android.database.Questiondata;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class JSONParsing {

	private JSONObject jsonObject;
	private Questiondata mQuestiondata=null;
	private QuestionAnswerDbAdapter dbHelper;
	String strParsedValue = null;
	private String fileJSONQuestBank = "myfile.quest";
//	String strParsedValue = null;
	private Context context;
	private String strJSONValue = "{\"FirstObject\":{\"attr1\":\"one value\" ,\"attr2\":\"two value\","
			+"\"sub\": { \"sub1\":[ {\"sub1_attr\":\"sub1_attr_value\" },{\"sub1_attr\":\"sub2_attr_value\" }]}}}";

	private String strJSONQuestBank = "[{\"Question\":\"Sex?\",\"OptionA\":\"Yes\",\"OptionB\":\"Yes\",\"OptionC\":\"Yes\",\"OptionD\":\"Yes\","
			+ "\"Answer\":\"A\",\"ResourceLink\":\"Yes\",\"Information\":\"Yes\",\"Level\":\"Yes\",\"Flag\":\"Yes\"},"
			+ "{\"Question\":\"Hate?\",\"OptionA\":\"Yes\",\"OptionB\":\"Yes\",\"OptionC\":\"Yes\",\"OptionD\":\"Yes\","
					+ "\"Answer\":\"B\",\"ResourceLink\":\"Yes\",\"Information\":\"Yes\",\"Level\":\"Yes\",\"Flag\":\"Yes\"}]";

	
	
	public JSONParsing(Context context) {
		this.context = context;
		dbHelper = new QuestionAnswerDbAdapter(context);
		
		//dbHelper = this.dbHelper;
		dbHelper.open();
	}



	public Questiondata getQuestionData()
	{
		 try {
				parseJSON();

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	
		populateDatabasefromJsonFile(fileJSONQuestBank); 
		//populateDatabasefromJsonStringArray(strJSONQuestBank);
		 
		 return mQuestiondata;
	}
	
	
	
	public boolean populateDatabasefromJsonFile(String filename) {
		// TODO Auto-generated method stub
		StringBuffer buffer = new StringBuffer();
		buffer.append(strJSONQuestBank);
	
	//	### Temp : file developement
		String tstrJSONQuestBank = "[{\"Question\":\"Temp Sex?\",\"OptionA\":\"Yes\",\"OptionB\":\"Yes\",\"OptionC\":\"Yes\",\"OptionD\":\"Yes\","
				+ "\"Answer\":\"A\",\"ResourceLink\":\"Yes\",\"Information\":\"Yes\",\"Level\":\"Yes\",\"Flag\":\"Yes\"},"
				+ "{\"Question\":\"Temp Hate?\",\"OptionA\":\"Yes\",\"OptionB\":\"Yes\",\"OptionC\":\"Yes\",\"OptionD\":\"Yes\","
						+ "\"Answer\":\"B\",\"ResourceLink\":\"Yes\",\"Information\":\"Yes\",\"Level\":\"Yes\",\"Flag\":\"Yes\"}]";
		 BufferedWriter writer = null;
			try {
			  writer = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(filename, context.MODE_PRIVATE)));
			  writer.write(tstrJSONQuestBank);
			} catch (Exception e) {
					e.printStackTrace();
			} finally {
			  if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			  }
			}
			
			
			
			
			
			BufferedReader input = null;
			try {
			  input = new BufferedReader(new InputStreamReader(context.openFileInput(filename)));
			  String line; 
			  buffer.delete(0, buffer.length());
			  while ((line = input.readLine()) != null) {
				buffer.append(line);
			  }
			} catch (Exception e) {
			 	e.printStackTrace();
			} finally {
			if (input != null) {
			  try {
				input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			  }
			}
			
			
//		// try to write the content
//		 try {
//		   // open myfilename.txt for writing
//		   OutputStreamWriter out = new OutputStreamWriter(openFileOutput("myfilename.txt",0));
//		   // write the contents on mySettings to the file
//		   out.write(mySettings);
//		   // close the file
//		   out.close();
//		 } catch (java.io.IOException e) {
//		   //do something if an IOException occurs.
//		 }
//		 
//		 
//		 
//		 try {
//			    // open the file for reading
//			    InputStream instream = openFileInput("myfilename.txt");
//			 
//			    // if file the available for reading
//			    if (instream) {
//			      // prepare the file for reading
//			      InputStreamReader inputreader = new InputStreamReader(instream);
//			      BufferedReader buffreader = new BufferedReader(inputreader);
//			 
//			      String line;
//			 
//			      // read every line of the file into the line-variable, on line at the time
//			      while (( line = buffreader.readLine())) {
//			        // do something with the settings from the file
//			      }
//			 
//			    }
//			 
//			    // close the file again
//			    instream.close();
//			  } catch (java.io.FileNotFoundException e) {
//			    // do something if the myfilename.txt does not exits
//			  }
		 
		 
		 
		
		return populateDatabasefromJsonStringArray(buffer.toString());
	}



	public void parseJSON() throws JSONException
    {
    	jsonObject = new JSONObject(strJSONValue);

    	//parsing Object value
    	JSONObject object = jsonObject.getJSONObject("FirstObject");
    	System.out.println(object);
    	String attr1 = object.getString("attr1");
    	String attr2 = object.getString("attr2");

    	strParsedValue="Attribute 1 value => "+attr1;
    	strParsedValue+="\n Attribute 2 value => "+attr2;

    	//parsing sub-object
    	JSONObject subObject = object.getJSONObject("sub");

    	// parsing array values
    	JSONArray subArray = subObject.getJSONArray("sub1");

    	strParsedValue+="\n Array Length => "+subArray.length();

    	for(int i=0; i<subArray.length(); i++)
    	{
    		strParsedValue+="\n"+subArray.getJSONObject(i).getString("sub1_attr").toString();
    	}

    }
	
	public boolean populateDatabasefromJsonStringArray (String str) {
		
		try {
			JSONArray jsonArray = new JSONArray(str);
			Questiondata tQuestiondata = new Questiondata();
			Log.i("no of questions :" , "Tot : " + jsonArray.length());
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Log.i("A question :", jsonObject.getString("Question"));
				Log.i("A question :", jsonObject.getString("OptionA"));
				tQuestiondata.setQuestion(jsonObject.getString("Question")) ;
				tQuestiondata.setOptionA(jsonObject.getString("OptionA")) ;
				tQuestiondata.setOptionB(jsonObject.getString("OptionB")) ;
				tQuestiondata.setOptionC(jsonObject.getString("OptionC")) ;
				tQuestiondata.setOptionD(jsonObject.getString("OptionD")) ;
				tQuestiondata.setAnswer(jsonObject.getString("Answer")) ;
				tQuestiondata.setResolink(jsonObject.getString("ResourceLink")) ;
				tQuestiondata.setInfo(jsonObject.getString("Information")) ;
				tQuestiondata.setLevel(jsonObject.getString("Level")) ;
				tQuestiondata.setFlag(jsonObject.getString("Flag")) ;
				
				dbHelper.createQuestion(tQuestiondata);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	public Questiondata getQuestiondatafromJsonString (String str) {
		
		try {
			JSONArray jsonArray = new JSONArray(str);
			Questiondata tQuestiondata = new Questiondata();
			Log.i("no of questions :" , "Tot : " + jsonArray.length());
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Log.i("A question :", jsonObject.getString("Question"));
				Log.i("A question :", jsonObject.getString("OptionA"));
				tQuestiondata.setQuestion(jsonObject.getString("Question")) ;
				tQuestiondata.setOptionA(jsonObject.getString("OptionA")) ;
				tQuestiondata.setOptionB(jsonObject.getString("OptionB")) ;
				tQuestiondata.setOptionC(jsonObject.getString("OptionC")) ;
				tQuestiondata.setOptionD(jsonObject.getString("OptionD")) ;
				tQuestiondata.setAnswer(jsonObject.getString("Answer")) ;
				try {
					tQuestiondata.setInfo(jsonObject.getString("Information")) ;
				tQuestiondata.setResolink(jsonObject.getString("ResourceLink")) ;
				tQuestiondata.setLevel(jsonObject.getString("Level")) ;
				tQuestiondata.setFlag(jsonObject.getString("Flag")) ;
				} catch (Exception e) {}
				
				return tQuestiondata;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public boolean populateDatabasefromJson () {
		
		try {
			JSONArray jsonArray = new JSONArray(strJSONQuestBank);
			Questiondata tQuestiondata = new Questiondata();
			Log.i("no of questions :" , "Tot : " + jsonArray.length());
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Log.i("A question :", jsonObject.getString("Question"));
				Log.i("A question :", jsonObject.getString("OptionA"));
				tQuestiondata.setQuestion(jsonObject.getString("Question")) ;
				tQuestiondata.setOptionA(jsonObject.getString("OptionA")) ;
				tQuestiondata.setOptionB(jsonObject.getString("OptionB")) ;
				tQuestiondata.setOptionC(jsonObject.getString("OptionC")) ;
				tQuestiondata.setOptionD(jsonObject.getString("OptionD")) ;
				tQuestiondata.setAnswer(jsonObject.getString("Answer")) ;
				tQuestiondata.setResolink(jsonObject.getString("ResourceLink")) ;
				tQuestiondata.setInfo(jsonObject.getString("Information")) ;
				tQuestiondata.setLevel(jsonObject.getString("Level")) ;
				tQuestiondata.setFlag(jsonObject.getString("Flag")) ;
				
				dbHelper.createQuestion(tQuestiondata);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
}
