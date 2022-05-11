package cn.edu.neusoft.ypq.chaptertest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;

import cn.edu.neusoft.ypq.chaptertest.service.MusicService;

/**
 * 作者:颜培琦
 * 时间:2022/5/9
 * 功能:ServiceActivity
 */
public class ServiceActivity extends AppCompatActivity {
    public static int SELECT_MUSIC_CODE = 1;

    private int times = 0;
    private boolean isPlaying = false;
    private boolean bound = false;
    private MusicService musicService;

    private Button btSelect;
    private Button btPlay;
    private Button btStop;
    private TextView tvName;
    private TextView tvState;
    private TextView tvTimes;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
            musicService = binder.getService();
            bound = true;
            musicService.finished(new MusicService.OnPlayFinishedListener() {
                @Override
                public void finished(Bundle bundle) {
                    int times = bundle.getInt("times");
                    int state = bundle.getInt("state");
                    String name = bundle.getString("name");
                    if (state == 0) {
                        isPlaying = false;
                        tvState.setText("已停止");
                        btPlay.setText("播放");
                    } else if (state == 1) {
                        isPlaying = true;
                        tvState.setText("正在播放");
                        btPlay.setText("暂停");
                    } else if (state == 2) {
                        isPlaying = false;
                        tvState.setText("已暂停");
                        btPlay.setText("播放");
                    }
                    tvName.setText(name);
                    tvTimes.setText("已播放" + times + "次");
                }
            });
            musicService.initState();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        btSelect = findViewById(R.id.service_bt_select);
        btPlay = findViewById(R.id.service_bt_play);
        btStop = findViewById(R.id.service_bt_stop);
        tvName = findViewById(R.id.service_tv_name);
        tvState = findViewById(R.id.service_tv_state);
        tvTimes = findViewById(R.id.service_tv_times);

        btSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("audio/*");
                startActivityForResult(i,SELECT_MUSIC_CODE);
            }
        });

        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicService.setPlayState(isPlaying);
            }
        });

        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicService.stop();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_MUSIC_CODE && resultCode == RESULT_OK) {
            DocumentFile documentFile = DocumentFile.fromSingleUri(this, data.getData());
            if (documentFile != null) {
                String name = documentFile.getName();
                musicService.play(data.getData(), name);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(ServiceActivity.this, MusicService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bound) unbindService(serviceConnection);
    }
}
