package com.box.helloworld;
/**
 * Created by BOXinWORLD on 2018/4
 */
import android.content.Context;

public class DBmanager {
    private static MySqliteHelper helper;
    public static MySqliteHelper getHelper(Context context){
        if(helper==null){
            helper=new MySqliteHelper(context);
        }
        return helper;
    }
}
