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
    private Integer position;
    private ListView mListView;

    private int[] arrayId = new int[] {R.array.wifi_array , R.array.bluetooth_array , R.array.app_array ,
            R.array.view_array , R.array.sound_array , R.array.key_array , R.array.battery_array ,
            R.array.storage_array , R.array.safe_array};
    int[] pid = new int[]{R.drawable.transportation_and_vehicle_01 , R.drawable.transportation_and_vehicle_02 ,
            R.drawable.transportation_and_vehicle_03 , R.drawable.transportation_and_vehicle_04 ,
            R.drawable.transportation_and_vehicle_05 , R.drawable.transportation_and_vehicle_06 ,
            R.drawable.transportation_and_vehicle_07 , R.drawable.transportation_and_vehicle_08 ,
            R.drawable.transportation_and_vehicle_09 , R.drawable.transportation_and_vehicle_10 ,
            R.drawable.transportation_and_vehicle_01 , R.drawable.transportation_and_vehicle_02 ,
            R.drawable.transportation_and_vehicle_03 , R.drawable.transportation_and_vehicle_04 };

    public SettingDetailFragment(String title, Integer position) {
        mTitle = title;
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_setting_detail, null);
        TextView tvTitle = view.findViewById(R.id.setting_detail_title);
        tvTitle.setText(mTitle);
        mListView = view.findViewById(R.id.list_detail);
        loadData();
        return view;
    }

    private void loadData() {
        String[] items = getResources().getStringArray(arrayId[position]);

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
