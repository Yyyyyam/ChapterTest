package cn.edu.neusoft.ypq.chaptertest.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 作者:颜培琦
 * 时间:2022/5/10
 * 功能:StartService
 */
public class StartService extends Service {
    public static String totalMemory;
    public static String availMemory;
    private final IBinder binder = new StartBinder();

    public class StartBinder extends Binder {
        public StartService getService() {
            return StartService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        totalMemory = Formatter.formatFileSize(this, mi.totalMem);
        availMemory = Formatter.formatFileSize(this, mi.availMem);
        Log.d("service", "totalMemory: "+totalMemory);
        Log.d("service", "availMemory: "+availMemory);
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "StartService Start", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "StartService Done", Toast.LENGTH_SHORT).show();
    }

    public String getAvailMem() {
        ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return Formatter.formatFileSize(this, mi.availMem);
    }
}
