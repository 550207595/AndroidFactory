package com.jeiel.databasetest;

public class MyDatabaseHelper extends SQLiteOpenHelper{
	private static final String CREATE_BOOK="create table Book("+
				"id integer primary key autoincrement,"+
				"author text,"+
				"price real,"+
				"pages integer,"+
				"name text)";

}