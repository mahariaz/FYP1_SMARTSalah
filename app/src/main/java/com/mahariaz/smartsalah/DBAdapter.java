package com.mahariaz.smartsalah;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
    //columns
    static final String ROWID="id";
    static  final String USERNAME="username";
    static  final String NAMAZ_NAME="namaz_name";
    static  final String RAKAH_ENTERED="rakah_entered";
    static  final String RAKAH_COMPLETED="rakah_completed";
    static  final String QAYAM="qayam";
    static  final String RUKU="ruku";
    static  final String QOUMA="qouma";
    static  final String SAJDA="sajda";
    static  final String JALSA="jalsa";
    static  final String TASHAHUD="tashahud";
    static final String TAG="DBSpinner";

    //db properties
    static  final String DBNAME="s_DB";
    static final String TBNAME="s_TB";
    static final int DBVERSION='1';

    //create tb
    static final String CREATE_TB="CREATE TABLE s_TB(id INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "user_name TEXT,namaz_name TEXT ,rakah_entered TEXT," +
            "rakah_completed TEXT,qayam TEXT,ruku TEXT,qouma TEXT,sajda TEXT,jalsa TEXT,tashahud TEXT);";
    final Context c;
    SQLiteDatabase db,dbr;
    DBHelper helper;

    public DBAdapter(Context ctx) {
        this.c = ctx;
        helper=new DBHelper(c);
    }
    // inner helper db class
    private static class DBHelper extends SQLiteOpenHelper{
        public DBHelper(Context context){
            super(context,DBNAME,null,DBVERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try{
                db.execSQL(CREATE_TB);
            }catch (SQLException e){
                e.printStackTrace();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG,"Upgrading DB");
            db.execSQL("DROP TABLE IF EXISTS s_TB");
            onCreate(db);

        }
    }
    //open the db
    public DBAdapter openDB(){
        try{
            db=helper.getWritableDatabase();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return this;
    }
    //close the db
    public void close (){
        helper.close();
    }
    // insert into table
    public long add(String username,String namaz_name,String rakah_entered, String rakah_completed,
                    String qayam,String ruku,String qouma,String sajda,String jalsa,String tashahud){
        try{
            ContentValues cv=new ContentValues();
            cv.put(USERNAME,username);
            cv.put(NAMAZ_NAME,namaz_name);
            cv.put(RAKAH_ENTERED,rakah_entered);
            cv.put(RAKAH_COMPLETED,rakah_completed);
            cv.put(QAYAM,qayam);
            cv.put(RUKU,ruku);
            cv.put(QOUMA,qouma);
            cv.put(SAJDA,sajda);
            cv.put(JALSA,jalsa);
            cv.put(TASHAHUD,tashahud);
            return db.insert(TBNAME,ROWID,cv);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
    //get all values
    public Cursor getAllValues(){
        String[] columns={ROWID,USERNAME,NAMAZ_NAME,RAKAH_ENTERED,RAKAH_COMPLETED,QAYAM,RUKU,QOUMA,SAJDA,JALSA,TASHAHUD};
        return db.query(TBNAME,columns,null,null,null,null,null);

    }

    public void delete_table(){
        db.execSQL("DROP TABLE IF EXISTS s_TB");

    }

}
