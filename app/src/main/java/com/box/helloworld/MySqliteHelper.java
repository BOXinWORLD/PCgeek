package com.box.helloworld;
/**
 * Created by BOXinWORLD on 2018/4
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqliteHelper extends SQLiteOpenHelper{
    public MySqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public MySqliteHelper(Context context) {
        super(context, "SC.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table if not exists News(nurl varchar(256) primary key,ntitle varchar(64)" +
                ",nfrom varchar(32),ndesc varchar(128),ntime varchar(32))";
        db.execSQL(sql);
//        Log.e("====", "Create");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        Log.e("====", "Upgrade");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
//        Log.e("====", "open");
        super.onOpen(db);
    }
}
