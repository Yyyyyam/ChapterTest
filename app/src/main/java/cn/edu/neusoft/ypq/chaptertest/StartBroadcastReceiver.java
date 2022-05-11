package cn.edu.neusoft.ypq.chaptertest;

import static android.content.Context.ACTIVITY_SERVICE;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.format.Formatter;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 作者:颜培琦
 * 时间:2022/5/10
 * 功能:StartBroadcastReceiver
 */
public class StartBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent main = new Intent(context, MainActivity.class);
        main.setAction("android.intent.action.MAIN");
        main.addCategory("android.intent.category.LAUNCHER");
        main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(main);
    }
}
