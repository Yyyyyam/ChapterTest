package cn.edu.neusoft.ypq.chaptertest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者:颜培琦
 * 时间:2022/4/24
 * 功能:SettingDetailFragment
 */
public class SettingDetailFragment extends Fragment {

    private String mTitle;
    private ListView mListView;

    public SettingDetailFragment(String title) {
        mTitle = title;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_setting_detail, null);
        TextView tvTitle = view.findViewById(R.id.setting_detail_title);
        tvTitle.setText(mTitle);
        mListView = view.findViewById(R.id.list_detail);
        if ("亮度".equals(mTitle)) {
            loadData();
        }
        return view;
    }

    private void loadData() {
        String[] items = new String[]{"系统亮度" , "休眠时间" , "夜间模式" , "阅读模式" ,
                "屏幕颜色" , "显示增强" , "区域显示" , "显示方式"};
        int[] pid = new int[]{R.drawable.transportation_and_vehicle_01 , R.drawable.transportation_and_vehicle_02 ,
                R.drawable.transportation_and_vehicle_03 , R.drawable.transportation_and_vehicle_04 ,
                R.drawable.transportation_and_vehicle_05 , R.drawable.transportation_and_vehicle_06 ,
                R.drawable.transportation_and_vehicle_07 , R.drawable.transportation_and_vehicle_08 };

        List<Map<String, Object>> listItems = new ArrayList<>();
        for (int i=0; i<items.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", items[i]);
            map.put("pid", pid[i]);
            listItems.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(getContext() , listItems , R.layout.item_setting ,
                new String[]{"name" , "pid"} , new int[]{R.id.tv_setting_name , R.id.item_setting_icon});
        mListView.setAdapter(adapter);
    }
}
