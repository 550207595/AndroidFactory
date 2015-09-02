package com.jeiel.databasetest;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private MyDatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper=new MyDatabaseHelper(this,"BookStore.db",null,3);
        Button createBtn=(Button)findViewById(R.id.create_database);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db=dbHelper.getWritableDatabase();
            }
        });

        Button addBtn=(Button)findViewById(R.id.add_data);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                /*ContentValues values=new ContentValues();
                values.put("name","The Da Vinci Code");
                values.put("author","Dan Brown");
                values.put("pages",454);
                values.put("price", 16.96);
                db.insert("Book", null, values);
                values.clear();
                values.put("name","The Lost Symbol");
                values.put("author","Dan Brown");
                values.put("pages",510);
                values.put("price",19.95);
                db.insert("Book",null,values);*/
                db.execSQL("insert into Book(name,author,pages,price) values(?,?,?,?)",
                        new String[]{"The Da Vinci Code","Dan Brown","454","16.96"});
                db.execSQL("insert into Book(name,author,pages,price) values(?,?,?,?)",
                        new String[]{"The Lost Symbol","Dan Brown","510","19.95"});
            }
        });

        Button deleteBtn=(Button)findViewById(R.id.delete_data);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                /*db.delete("Book", "pages > ?", new String[]{"500"});*/
                db.execSQL("delete from Book where pages >?",
                        new String[]{"500"});
            }
        });

        Button updateBtn=(Button)findViewById(R.id.update_data);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                /*ContentValues values=new ContentValues();
                values.put("price",10.99);
                db.update("Book",values,"name = ?",new String[]{"The Da Vinci Code"});*/
                db.execSQL("update Book set price = ? where name =?",
                        new String[]{"10.99","TheDa Vinci Code"});
            }
        });

        Button queryBtn=(Button)findViewById(R.id.query_data);
        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                /*Cursor cursor=db.query("Book", null, null, null, null, null, null);*/
                Cursor cursor=db.rawQuery("select * from Book",null);
                if(cursor.moveToFirst()){
                    do{
                        String name=cursor.getString(cursor.getColumnIndex("name"));
                        String author=cursor.getString(cursor.getColumnIndex("author"));
                        int pages=cursor.getInt(cursor.getColumnIndex("pages"));
                        double price=cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("MainActivity","book name is "+name);
                        Log.d("MainActivity","book author is "+author);
                        Log.d("MainActivity","book pages is "+pages);
                        Log.d("MainActivity","book price is "+price);
                    }while(cursor.moveToNext());
                }
            }
        });

        Button replaceBtn=(Button)findViewById(R.id.replace_data);
        replaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                db.beginTransaction();
                try{
                    db.delete("Book",null,null);
                    /*if(true){
                        throw new NullPointerException("Terminate transaction");
                    }*/
                    db.execSQL("insert into Book(name,author,pages,price) values(?,?,?,?)",
                            new  String[]{"Game of Thrones","George Martin","720","20.85"});
                    db.setTransactionSuccessful();
                }catch(NullPointerException e){
                    e.printStackTrace();
                }finally{
                    db.endTransaction();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
