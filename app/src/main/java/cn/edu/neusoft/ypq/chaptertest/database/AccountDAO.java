package cn.edu.neusoft.ypq.chaptertest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

/**
 * 作者:颜培琦
 * 时间:2022/5/5
 * 功能:AccountDAO
 */
public class AccountDAO {
    DBOpenHelper openHelper;
    private static AccountDAO instance;
    private Context context;

    public AccountDAO(Context context){
        this.context = context;
        openHelper = new DBOpenHelper(context);
    }

    public static AccountDAO getInstance(Context context){
        if(instance == null){
            instance = new AccountDAO(context);
        }
        return instance;
    }

    public long insert(ContentValues values) {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        long i = db.insert(DBOpenHelper.ACCOUNT_TABLE,null,values);
        db.close();
        return i;
    }

    public Cursor query(String selection, String[] selectionArgs) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db.query(DBOpenHelper.ACCOUNT_TABLE, null, selection,
                selectionArgs,null,null,null);
    }

    public int delete(String selection, String[] selectionArgs) {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        int delete = db.delete(DBOpenHelper.ACCOUNT_TABLE, selection, selectionArgs);
        db.close();
        return delete;
    }
}
