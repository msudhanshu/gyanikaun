package com.example.android.database;

import com.example.android.database.QuestionAnswerDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class QuestionAnswerDatabase extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "applicationdata";

	private static final int DATABASE_VERSION = 2;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table questiontable (_id integer primary key autoincrement, "
			+ "questioncolumn text not null, "
			+ "optionAcolumn text not null, "
		    + "optionBcolumn text not null, "
			+ "optionCcolumn text not null, "
			+ "optionDcolumn text not null, "
			+ "answercolumn text not null, "
			+ "resourcelinkcolumn text not null, "
			+ "infocolumn text not null, "
			+ "levelcolumn text not null, "
			+ "flagcolumn text not null);";

	public QuestionAnswerDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Method is called during creation of the database
	@Override
	public void onCreate(SQLiteDatabase database) {
		try {
			// $$ TODO :: TEMP .. DELETE IT 
			database.execSQL("DROP TABLE IF EXISTS questiontable");
		database.execSQL(DATABASE_CREATE);
		} catch (SQLiteException exception) {
		    Log.i("error la query child", "on the next line");
		    exception.printStackTrace();
		}
		
	}

	// Method is called during an upgrade of the database, e.g. if you increase
	// the database version
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(QuestionAnswerDatabase.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS questiontable");
		onCreate(database);
	}
}

