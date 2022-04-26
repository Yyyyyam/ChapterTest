package cn.edu.neusoft.ypq.chaptertest;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import cn.edu.neusoft.ypq.chaptertest.bean.ItemSelected;

/**
 * 作者:颜培琦
 * 时间:2022/4/26
 * 功能:InfoActivity
 */
public class InfoActivity extends AppCompatActivity {
    private NetReceiver mNetReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        init();
    }

    private void init(){
        Intent intent = getIntent();
        int days = intent.getIntExtra(ListActivity.KEY_INT , 0);
        byte b = intent.getByteExtra(ListActivity.KEY_BYTE , (byte) 0);
        ItemSelected item = (ItemSelected) intent.getSerializableExtra(ListActivity.KEY_ITEM);
        TextView tvInt = findViewById(R.id.info_tv_int);
        tvInt.setText("int:" + days);
        TextView tvByte = findViewById(R.id.info_tv_byte);
        tvByte.setText("byte:" + b);
        TextView tvSerial = findViewById(R.id.info_tv_serial);
        tvSerial.setText("Serializable:姓名-" + item.getName() + ",编号-" + item.getPosition());
    }

    @Override
    protected void onStart() {
        super.onStart();
        TextView tvNet = findViewById(R.id.info_tv_net);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mNetReceiver = new NetReceiver();
        // 实现广播的接口功能
        mNetReceiver.changeState(new NetReceiver.OnNetWorkChangeListener() {
            @Override
            public void changeState(NetworkInfo info) {
                if (null != info && info.isConnected()) {
                    tvNet.setText("当前网络状态:" + info.getTypeName());
                } else {
                    tvNet.setText("当前网络状态:无网络");
                }
            }
        });
        // 注册广播接收器
        registerReceiver(mNetReceiver , intentFilter);
        Toast.makeText(this, "广播接收器已注册", Toast.LENGTH_SHORT);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 注销广播接收器
        unregisterReceiver(mNetReceiver);
        Toast.makeText(this, "广播接收器已注销", Toast.LENGTH_SHORT);
    }
}
