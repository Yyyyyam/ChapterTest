package cn.edu.neusoft.ypq.chaptertest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

/**
 * 作者:颜培琦
 * 时间:2022/4/24
 * 功能:SettingActivity
 */
public class SettingActivity extends AppCompatActivity {
    private int mCurrentSelect = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        if ( bundle != null) {
            String text = bundle.getString("text");
            Toast.makeText(this , "上一个界面传递的数据是:" + text , Toast.LENGTH_SHORT).show();
        }

        ListView listView = findViewById(R.id.list_head);
        int[] pid = new int[]{R.drawable.transportation_and_vehicle_01 , R.drawable.transportation_and_vehicle_02 ,
                R.drawable.transportation_and_vehicle_03 , R.drawable.transportation_and_vehicle_04 ,
                R.drawable.transportation_and_vehicle_05 , R.drawable.transportation_and_vehicle_06 ,
                R.drawable.transportation_and_vehicle_07 , R.drawable.transportation_and_vehicle_08 ,
                R.drawable.transportation_and_vehicle_09 };
        String[] settingHead = getResources().getStringArray(R.array.item_array);

        BaseAdapter adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return pid.length;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(SettingActivity.this);
                LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.item_setting, null);

                TextView tvSetting = layout.findViewById(R.id.tv_setting_name);
                tvSetting.setText(settingHead[position]);
                tvSetting.setTextSize(18);
                if (mCurrentSelect == position) {
                    tvSetting.setTextColor(Color.YELLOW);
                } else {
                    tvSetting.setTextColor(Color.BLACK);
                }
                ImageView img = layout.findViewById(R.id.item_setting_icon);
                img.setImageResource(pid[position]);
                return layout;
            }
        };

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCurrentSelect = position;
                adapter.notifyDataSetChanged();
                TextView tvSetting = view.findViewById(R.id.tv_setting_name);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.detail, new SettingDetailFragment(tvSetting.getText().toString(), position)).commit();
            }
        });


        getSupportFragmentManager().beginTransaction().add(R.id.detail, new SettingDetailFragment("WiFi" , 0)).commit();
    }
}
