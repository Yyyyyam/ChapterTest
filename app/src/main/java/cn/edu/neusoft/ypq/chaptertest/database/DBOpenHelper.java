package cn.edu.neusoft.ypq.chaptertest.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * 作者:颜培琦
 * 时间:2022/5/5
 * 功能:DBOpenHelper
 */
public class DBOpenHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "test.db";
    public static final String ACCOUNT_TABLE = "account";

    private static final int DB_VERSION = 1;

    public DBOpenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists "+ACCOUNT_TABLE +
                "(aid integer primary key autoincrement, name vchar,  money double)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
