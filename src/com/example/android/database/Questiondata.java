package com.example.android.database;

public class Questiondata {

	private long rowID = -1;
	private String question;
	private String optionA;
	private String optionB;
	private String optionC;
	private String optionD;
	private String answer;
	private String resolink;
	private String info;
	private String level;
	private String flag;
	
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public long getRowID() {
		return rowID;
	}

	public void setRowID(long rowID) {
		this.rowID = rowID;
	}
	
	public Questiondata() {
		// TODO Auto-generated constructor stub
	}
	
	public Questiondata(String question, String optionA, String optionB, String optionC, String optionD, String answer, String resolink, String info, String level, String flag) {
		this.question = question;
		this.optionA = optionA;
		this.optionB = optionB;
		this.optionC = optionC;
		this.optionD = optionD;
		this.answer = answer;
		this.resolink = resolink;
		this.info = info;
		this.level = level;
		this.flag = flag;
		
	}
	
	
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getOptionA() {
		return optionA;
	}
	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}
	public String getOptionB() {
		return optionB;
	}
	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}
	public String getOptionC() {
		return optionC;
	}
	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}
	public String getOptionD() {
		return optionD;
	}
	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}
	public String getResolink() {
		return resolink;
	}
	public void setResolink(String resolink) {
		this.resolink = resolink;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
		
		
	
}
