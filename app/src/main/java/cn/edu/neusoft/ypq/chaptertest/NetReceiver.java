package cn.edu.neusoft.ypq.chaptertest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 作者:颜培琦
 * 时间:2022/4/26
 * 功能:NetReceiver
 */
public class NetReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            // 获取连接管理者
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // 获取网络状态信息
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            // 调用接口，在InfoActivity实现该接口
            netWorkChangeListener.changeState(networkInfo);
        }

    }

    private OnNetWorkChangeListener netWorkChangeListener;
    // 网络变化的接口
    public interface OnNetWorkChangeListener{
        void changeState(NetworkInfo info);
    }

    public void changeState(OnNetWorkChangeListener netWorkChangeListener){
        this.netWorkChangeListener = netWorkChangeListener;
    }
}
