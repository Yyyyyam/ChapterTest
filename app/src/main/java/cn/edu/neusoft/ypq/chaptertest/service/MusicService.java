package cn.edu.neusoft.ypq.chaptertest.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

/**
 * 作者:颜培琦
 * 时间:2022/5/9
 * 功能:MusicService
 */
public class MusicService extends Service {
    public static int MUSIC_STOP_STATE = 0;
    public static int MUSIC_PLAYING_STATE = 1;
    public static int MUSIC_PAUSE_STATE = 2;

    private final IBinder binder = new MusicBinder();
    private static String playingUri = "";
    private static MediaPlayer player;
    private static int times = 0;
    private static int state = 0;
    private static String name;

    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void play(Uri uri, String name) {
        if (playingUri.equals(uri.toString())){
            Toast.makeText(this, "选择歌曲即为当前播放歌曲", Toast.LENGTH_SHORT).show();
            return;
        } else if (player != null){
            player.stop();
        }
        playingUri = uri.toString();
        this.name = name;
        times = 0;
        player = MediaPlayer.create(MusicService.this, uri);
        player.start();
        state = 1;
        initState();

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
                times += 1;
                initState();
            }
        });
    }

    public void setPlayState(boolean isPlaying) {
        if (player != null) {
            if (isPlaying) {
                player.pause();
                state = MUSIC_PAUSE_STATE;
            } else {
                player.start();
                state = 1;
            }
            initState();
        } else {
            Toast.makeText(this, "请选择歌曲", Toast.LENGTH_SHORT).show();
        }
    }

    public void stop() {
        if (player != null) {
            state = 0;
            player.stop();
            player.release();
            player = null;
            initState();
            playingUri = "";
        }
    }

    public interface OnPlayFinishedListener {
        void finished(Bundle bundle);
    }

    private OnPlayFinishedListener playFinishedListener;

    public void finished(OnPlayFinishedListener playFinishedListener) {
        this.playFinishedListener = playFinishedListener;
    }

    public void initState() {
        if (!playingUri.isEmpty()) {
            Bundle bundle = new Bundle();
            bundle.putInt("times", times);
            bundle.putInt("state", state);
            bundle.putString("name", name);
            if (playFinishedListener != null) {
                playFinishedListener.finished(bundle);
            }
        }
    }

}
