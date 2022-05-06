package cn.edu.neusoft.ypq.chaptertest;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 作者:颜培琦
 * 时间:2022/5/6
 * 功能:ThreadActivity
 */
public class ThreadActivity extends AppCompatActivity {
    private int playTimes = 0;
    private boolean isPlaying = false;
    private MediaPlayer mediaPlayer;
    private Thread threadDownload;
    private EditText etUrl;
    private Button btDownload;
    private Button btPlay;
    private Button btStop;
    private TextView tvStatusDownload;
    private TextView tvIsPlaying;
    private TextView tvStatusPlayTimes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        etUrl = findViewById(R.id.et_url);
        btDownload = findViewById(R.id.bt_start);
        btPlay = findViewById(R.id.bt_play);
        btStop = findViewById(R.id.bt_stop);
        tvStatusDownload = findViewById(R.id.tv_status_download);
        tvIsPlaying = findViewById(R.id.tv_status_is_playing);
        tvStatusPlayTimes = findViewById(R.id.tv_status_playtimes);


        btDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvStatusPlayTimes.setText("已播放0次");
                playTimes = 0;
                String url = etUrl.getText().toString();
                if (url.isEmpty()) {
                    Toast.makeText(ThreadActivity.this, "请输入地址", Toast.LENGTH_SHORT).show();
                } else {
                    startMediaThread(url);
                }
            }
        });

        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                    btPlay.setText("播放");
                    tvIsPlaying.setText("已停止");
                }
            }
        });

        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer == null) {
                    Toast.makeText(ThreadActivity.this, "未播放音频", Toast.LENGTH_SHORT).show();
                } else if (isPlaying){
                    isPlaying = false;
                    btPlay.setText("播放");
                    tvIsPlaying.setText("已暂停");
                    mediaPlayer.pause();
                } else {
                    isPlaying = true;
                    btPlay.setText("暂停");
                    tvIsPlaying.setText("正在播放");
                    mediaPlayer.start();
                }
            }
        });
    }

    private void startMediaThread(String url) {
        threadDownload = new Thread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null ) {
                    mediaPlayer.stop();
                }
                tvStatusDownload.post(new Runnable() {
                    @Override
                    public void run() {
                        tvStatusDownload.setText("下载中");
                    }
                });
                mediaPlayer = MediaPlayer.create(ThreadActivity.this, Uri.parse(url));
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        isPlaying = true;
                        tvStatusDownload.post(new Runnable() {
                            @Override
                            public void run() {
                                tvStatusDownload.setText("下载完成");
                                btPlay.setText("暂停");
                                tvIsPlaying.setText("正在播放");
                            }
                        });
                    }
                });
//                    mediaPlayer.prepare();
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.start();
                        playTimes+=1;
                        tvStatusPlayTimes.setText("已播放"+playTimes+"次");
                    }
                });
            }
        });
        threadDownload.start();
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (threadDownload != null) {
            threadDownload = null;
        }
        super.onDestroy();
    }
}
