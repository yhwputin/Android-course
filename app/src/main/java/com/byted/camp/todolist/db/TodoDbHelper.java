package com.byted.camp.todolist.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.byted.camp.todolist.db.TodoContract;

import java.sql.SQLException;

import static com.byted.camp.todolist.db.TodoContract.SQL_CREATE_ENTRIES;
import static com.byted.camp.todolist.db.TodoContract.SQL_CREATE_ENTRIES_NEW;

/**
 * Created on 2019/1/22.
 *
 * @author xuyingyi@bytedance.com (Yingyi Xu)
 */
public class TodoDbHelper extends SQLiteOpenHelper {

    public static int DB_VERSION = 1;
    public static final String DB_NAME = "todolist.db";

    public TodoDbHelper(Context context) {

        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        onUpgrade(db,1,2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for(int i = oldVersion;i<newVersion;i++){
            switch (i){
                case 1:
                    updatedatebase(db);
                    break;
                default:
                    break;
            }
        }
    }

    private void updatedatebase(SQLiteDatabase db){
        try{
            db.beginTransaction();
            db.setVersion(2);
            String tempTableName = TodoContract.FeedEntry.TABLE_NAME + "_temp";
            String sqlnew = "ALTER TABLE " + TodoContract.FeedEntry.TABLE_NAME + " RENAME TO " + tempTableName;
            db.execSQL(sqlnew);
            db.execSQL(SQL_CREATE_ENTRIES_NEW);
            String sqlinsert = "INSERT INTO " + TodoContract.FeedEntry.TABLE_NAME + " (" + "_id,date,state,content" + ") " + " SELECT " + "_id,date,state,content" + " FROM " + tempTableName;
            db.execSQL(sqlinsert);
            db.execSQL("DROP TABLE IF EXISTS " + tempTableName);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }
}
