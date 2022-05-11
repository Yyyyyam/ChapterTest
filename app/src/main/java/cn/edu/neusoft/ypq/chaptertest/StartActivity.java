package cn.edu.neusoft.ypq.chaptertest;

import android.content.Context;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.os.SystemProperties;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 作者:颜培琦
 * 时间:2022/5/10
 * 功能:StartActivity
 */
public class StartActivity extends AppCompatActivity {

    // 预设值
    private int pre_system_light = 51;
    private int pre_voice_call = 6;
    private int pre_stream_ring = 1;
    private int pre_stream_music = 13;
    private AudioManager audioManager;

    private Button btMem;
    private Button btSetting;
    private TextView tvMemStart;
    private TextView tvMemNow;
    private TextView tvMemTotal;
    private TextView tvPreSystemLight;
    private TextView tvPreVoiceCall;
    private TextView tvPreStreamRing;
    private TextView tvPreStreamMusic;
    private TextView tvSystemLight;
    private TextView tvVoiceCall;
    private TextView tvStreamRing;
    private TextView tvStreamMusic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btMem = findViewById(R.id.bt_refresh_mem);
        btSetting = findViewById(R.id.bt_refresh_setting);
        tvMemStart = findViewById(R.id.tv_mem_start);
        tvMemNow = findViewById(R.id.tv_mem_now);
        tvMemTotal = findViewById(R.id.tv_mem_total);
        tvPreSystemLight = findViewById(R.id.tv_pre_system_light);
        tvPreVoiceCall = findViewById(R.id.tv_pre_voice_call);
        tvPreStreamRing = findViewById(R.id.tv_pre_stream_ring);
        tvPreStreamMusic = findViewById(R.id.tv_pre_stream_music);
        tvSystemLight = findViewById(R.id.tv_system_light);
        tvVoiceCall = findViewById(R.id.tv_voice_call);
        tvStreamRing = findViewById(R.id.tv_stream_ring);
        tvStreamMusic = findViewById(R.id.tv_stream_music);

        tvMemStart.setText("开机可用内存:" + ListActivity.startService.availMemory);
        tvMemNow.setText("当前可用内存:" + ListActivity.startService.getAvailMem());
        tvMemTotal.setText("总可用内存:" + ListActivity.startService.totalMemory);

        tvPreSystemLight.setText("预设亮度:"+pre_system_light);
        tvPreVoiceCall.setText("预设通话音量:"+pre_voice_call);
        tvPreStreamRing.setText("预设铃声音量:"+pre_stream_ring);
        tvPreStreamMusic.setText("预设媒体音量:"+pre_stream_music);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        initSetting();

        btSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSetting();
                Toast.makeText(StartActivity.this, "已更新", Toast.LENGTH_SHORT).show();
            }
        });
        btMem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvMemNow.setText("当前可用内存:" + ListActivity.startService.getAvailMem());
                Toast.makeText(StartActivity.this, "已更新", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSetting() {
        try {
            tvSystemLight.setText("当前亮度:"
                    +Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS));
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        tvVoiceCall.setText("当前通话音量:" + audioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL));
        tvStreamRing.setText("当前铃声音量:" + audioManager.getStreamVolume(AudioManager.STREAM_RING));
        tvStreamMusic.setText("当前媒体音量:" + audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
    }
}
