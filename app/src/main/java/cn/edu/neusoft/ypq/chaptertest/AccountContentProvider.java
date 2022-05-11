package cn.edu.neusoft.ypq.chaptertest;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cn.edu.neusoft.ypq.chaptertest.database.AccountDAO;
import cn.edu.neusoft.ypq.chaptertest.database.DBOpenHelper;


/**
 * 作者:颜培琦
 * 时间:2022/5/5
 * 功能:AccountContentProvider
 */
public class AccountContentProvider extends ContentProvider {

    private static UriMatcher uriMatcher;
    private static final String AUTHORITY = "cn.edu.neusoft.accountprovider";
    public static final int CODE_ACCOUNT = 0;
    private AccountDAO accountDAO;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "account", CODE_ACCOUNT);
    }

    public String getTableName(Uri uri) {
        if (uriMatcher.match(uri) == CODE_ACCOUNT) {
            return DBOpenHelper.ACCOUNT_TABLE;
        }
        return null;
    }

    @Override
    public boolean onCreate() {
        accountDAO = AccountDAO.getInstance(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        if (uriMatcher.match(uri) == CODE_ACCOUNT) {
            return accountDAO.query(selection , selectionArgs);
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if (getTableName(uri) == null) {
            throw new NullPointerException("未找到表数据");
        }
        accountDAO.insert(values);
        getContext().getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        if (getTableName(uri) == null) {
            throw new NullPointerException("未找到表数据");
        }
        // selection = "aid=?"
        int rows = accountDAO.delete(selection, selectionArgs);
        return rows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}
