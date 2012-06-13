package it.pdm.progetto.AndroDogs.BluetoothChat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
public class ChatBluetoothDAO {
	
	SQLiteDatabase db;
	DBHelper helper;
	String currentChat;
	
	public ChatBluetoothDAO(Context context) 
	{
		helper = new DBHelper(context);
	}
	
	public void open() 
	{
		db=helper.getWritableDatabase();
	}	
	
	public void close() 
	{
		db.close();
	}
	/**
	 * crea una nuova tabella se questa non esiste con il nome = mac passato e aggiorna la main table
	 * @param name nome del device collegato
	 * @param mac mac del device collegato (sarà anche il nome della tabella)
	 */
	public void getChatTable(String name, String mac) 
	{	
		Cursor verifica = db.query(DBHelper.mainTable, DBHelper.cln_name_for_main_table,DBHelper.cln_name_for_main_table[2]+"=\""+mac+"\"" , null, null, null, null);
		if(verifica.getCount()==0)
		{
			//aggiungi tupla alla mainTable e crea tabella per quella chat
			ContentValues values = new ContentValues();
			values.put(DBHelper.cln_name_for_main_table[1], name);
			values.put(DBHelper.cln_name_for_main_table[2], mac);
			long insertId =db.insert(DBHelper.mainTable, null, values);
			db.execSQL(helper.createSQLChatTable(mac));
		}
		currentChat=mac;
		
//		Cursor cursor = db.query(ToDoHelper.TABLE,
//				ToDoHelper.cln_name, ToDoHelper.cln_name[0] + " = " + insertId, null,
//				null, null, null);
//		cursor.moveToFirst();	
//		cursor.close();		
	}
	
	public void insertMessageInChatTable(String msg, int from)
	{
		ContentValues values = new ContentValues();
		values.put(DBHelper.cln_name_for_chat_table[1], from);
		values.put(DBHelper.cln_name_for_chat_table[2], msg);
		long insertId =db.insert(currentChat, null, values);
	}

	
	public void deleteChatTable()
	{
		db.execSQL("drop table if exists "+currentChat);
		db.execSQL(helper.createSQLChatTable(currentChat));
		
	}
	
	public Cursor getRecordsFromMainTable()
	{
		return db.query(helper.mainTable, helper.cln_name_for_main_table, null, null, null, null, null);
	}

}
