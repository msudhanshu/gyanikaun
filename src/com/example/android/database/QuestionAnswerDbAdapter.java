package com.example.android.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class QuestionAnswerDbAdapter {
	// Database fields
		public static final String KEY_ROWID = "_id";
		public static final String KEY_QUESTION = "questioncolumn";
		public static final String KEY_OPTIONA= "optionAcolumn";
		public static final String KEY_OPTIONB= "optionBcolumn";
		public static final String KEY_OPTIONC= "optionCcolumn";
		public static final String KEY_OPTIOND= "optionDcolumn";
		public static final String KEY_ANSWER= "answercolumn";
		public static final String KEY_RESOURCE = "resourcelinkcolumn";
		public static final String KEY_INFO = "infocolumn";
		public static final String KEY_LEVEL = "levelcolumn";
		public static final String KEY_FLAG = "flagcolumn";
		private static final String DATABASE_TABLE = "questiontable";
		private Context context;
		private SQLiteDatabase database;
		private QuestionAnswerDatabase mqdb;
		private SqLiteAssetCopy mdb;

		public QuestionAnswerDbAdapter(Context context) {
			this.context = context;
		}

		public QuestionAnswerDbAdapter open() throws SQLException {
		
			// $$ TODO :: TEMP .. DELETE IT 
						//database.execSQL("DROP TABLE IF EXISTS questiontable");
						
			mdb = new SqLiteAssetCopy(context);
			database = mdb.getWritableDatabase();
			mqdb = new QuestionAnswerDatabase(context);
			mqdb.onCreate(database);
	
			return this;
		}

		public void close() {
			mdb.close();
		}

		
	/** * Create a new Question If the Question is successfully created return the new * rowId for that note, otherwise return a -1 to indicate failure. */

		public long createQuestion(String question, String optionA, String optionB, String optionC, String optionD,String answer, String resolink, String info, String level, String flag) {
			ContentValues initialValues = createContentValues(question, optionA, optionB, optionC, optionD, answer, resolink, info, level, flag);
			return database.insert(DATABASE_TABLE, null, initialValues);
		}

		public long createQuestion(Questiondata tQd) {
			ContentValues initialValues = createContentValues(tQd.getQuestion(), tQd.getOptionA(), tQd.getOptionB(), tQd.getOptionC(), tQd.getOptionD(), tQd.getAnswer(), tQd.getResolink(), tQd.getInfo(), tQd.getLevel(), tQd.getFlag());
		 long temp=0;
			try {
				temp=database.insert(DATABASE_TABLE, null, initialValues);
			} catch (SQLiteException exception) {
			    Log.i("error la query child", "on the next line");
			    exception.printStackTrace();
			}
			
			return temp;
		}

	/** * Update the Question */

		public boolean updateQuestion(long rowId, String question, String optionA, String optionB, String optionC, String optionD, String answer, String resolink, String info, String level, String flag) {
			ContentValues updateValues = createContentValues(question, optionA, optionB, optionC, optionD, answer, resolink, info, level, flag);

			return database.update(DATABASE_TABLE, updateValues, KEY_ROWID + "="
					+ rowId, null) > 0;
		}

		public boolean updateQuestion(Questiondata tQd) {
			ContentValues updateValues = createContentValues(tQd.getQuestion(), tQd.getOptionA(), tQd.getOptionB(), tQd.getOptionC(), tQd.getOptionD(), tQd.getAnswer(), tQd.getResolink(), tQd.getInfo(), tQd.getLevel(), tQd.getFlag());
			return database.update(DATABASE_TABLE, updateValues, KEY_ROWID + "="
					+ tQd.getRowID(), null) > 0;
		}
		
		
	/** * Deletes Question */

		public boolean deleteQuestion(long rowId) {
			return database.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
		}

		
	/** * Return a Cursor over the list of all Question in the database * * @return Cursor over all notes */

		public Cursor fetchAllQuestions() throws SQLException{
			String a = "k";
			Cursor tc;
			
			try {
			tc = database.query(true, DATABASE_TABLE, new String[] {
					KEY_ROWID, KEY_QUESTION },
					KEY_OPTIONA + "=" + "A", null, null, null, null, null);
			return tc;
			} catch (SQLiteException exception) {
			    Log.i("error la query child", "on the next line");
			    exception.printStackTrace();
			    
			}
			
			try {
			tc =  database.query(DATABASE_TABLE, new String[] { KEY_ROWID, KEY_QUESTION, KEY_OPTIONA, KEY_OPTIONB, KEY_OPTIONC, KEY_OPTIOND,KEY_ANSWER, KEY_RESOURCE, KEY_INFO, KEY_LEVEL, KEY_FLAG}, null, null, null, null, null);
			return tc;
			} catch (SQLiteException exception) {
			    Log.i("error la query child", "on the next line");
			    
			    exception.printStackTrace();
			    
					a = "k";
				
			    
			}
	
			return null;
			
		}

		
	/** * Return a Cursor positioned at the defined Question */

		public Cursor fetchQuestion(long rowId) throws SQLException {
			Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] {
					KEY_ROWID, KEY_QUESTION },
					KEY_ROWID + "=" + rowId, null, null, null, null, null);
			if (mCursor != null) {
				mCursor.moveToFirst();
			}
			return mCursor;
		}

		private ContentValues createContentValues(String question, String optionA, String optionB, String optionC, String optionD, String answer, String resolink, String info, String level, String flag) {
			ContentValues values = new ContentValues();
			values.put(KEY_QUESTION, question);
			values.put(KEY_OPTIONA, optionA);
			values.put(KEY_OPTIONB, optionB);
			values.put(KEY_OPTIONC, optionC);
			values.put(KEY_OPTIOND, optionD);
			values.put(KEY_ANSWER, answer);
			values.put(KEY_RESOURCE, resolink);
			values.put(KEY_INFO, info);
			values.put(KEY_LEVEL, level);
			values.put(KEY_FLAG, flag);
			return values;
		}
}
