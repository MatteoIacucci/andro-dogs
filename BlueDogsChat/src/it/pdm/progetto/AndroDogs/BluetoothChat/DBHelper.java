package it.pdm.progetto.AndroDogs.BluetoothChat;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper
{
	private static final String DataBaseName = "chat.db";
	static int version = 1;
	public static final String mainTable = "chatPerson";
	static String[] cln_name_for_main_table = {"_id","person","mac"};
	static String[] cln_name_for_cursor_main_table = {"person","mac"};
	private static final String sqlCreateMainTable = "create table "+ mainTable+ "( " 
									+ cln_name_for_main_table[0]+ " integer primary key , " 
									+ cln_name_for_main_table[1]+ " text not null , "
									+ cln_name_for_main_table[2]+ " text not null)";
	
	static String[] cln_name_for_chat_table = {"_id","from","text"};

	
	Context context;
	
	public DBHelper(Context context)
	{
		super(context, DataBaseName, null, version);
		this.context=context;
	}
	public void onCreate(SQLiteDatabase db) 
	{
		db.execSQL(sqlCreateMainTable);
	}
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{		
	}	
	
	public String createSQLChatTable(String mac)
	{
		return "create table "+ mac+ "( " 
				+ cln_name_for_chat_table[0]+ " integer primary key , " 
				+ cln_name_for_chat_table[1]+ " integer not null , "
				+ cln_name_for_chat_table[2]+ " text not null)";
	}
	
	

}