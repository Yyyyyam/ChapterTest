package cn.edu.neusoft.ypq.chaptertest;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;

import cn.edu.neusoft.ypq.chaptertest.R;
import cn.edu.neusoft.ypq.chaptertest.bean.Account;

/**
 * 作者:颜培琦
 * 时间:2022/5/5
 * 功能:ContentActivity
 */
public class ContentActivity extends AppCompatActivity {

    private static final String AUTHORITIES = "cn.edu.neusoft.accountprovider";
    private String uri = "content://" + AUTHORITIES + "/account";
    private ContentResolver resolver;
    private Button btInsert;
    private Button btSearch;
    private EditText etName;
    private EditText etMoney;
    private EditText etSearch;
    private ListView listView;
    private List<Account> accountList;
    private BaseAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        btInsert = findViewById(R.id.bt_insert);
        btSearch = findViewById(R.id.bt_search);
        etName = findViewById(R.id.et_item_name);
        etMoney = findViewById(R.id.et_item_money);
        etSearch = findViewById(R.id.et_account_search);
        listView = findViewById(R.id.list_account);
        accountList = new ArrayList<>();
        resolver = getContentResolver();

        adapter = new BaseAdapter() {
            Account account;

            @Override
            public int getCount() {
                return accountList.size()+1;
            }

            @Override
            public Object getItem(int position) {
                if (account != null) {
                    return account;
                } else {
                    return null;
                }
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(ContentActivity.this);
                convertView = (ConstraintLayout) inflater.inflate(R.layout.item_account, null);

                TextView tvID = convertView.findViewById(R.id.item_account_tv_id);
                TextView tvName = convertView.findViewById(R.id.item_account_tv_name);
                TextView tvMoney = convertView.findViewById(R.id.item_account_tv_money);
                if (position == 0) {
                    tvID.setText("ID");
                    tvName.setText("记账项");
                    tvMoney.setText("金额");
                } else {
                    Account account = accountList.get(position - 1);
                    tvID.setText(String.valueOf(account.getAid()));
                    tvName.setText(account.getName());
                    tvMoney.setText("￥ "+account.getMoney());
                }

                convertView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                    @Override
                    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                        account = accountList.get(position - 1);
                        MenuInflater inflater = new MenuInflater(ContentActivity.this);
                        inflater.inflate(R.menu.account_menu, menu);
                    }
                });
                return convertView;
            }
        };
        listView.setAdapter(adapter);

        setData(null, null);
        btInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etName.getText().toString().isEmpty() || etMoney.getText().toString().isEmpty()) {
                    Toast.makeText(ContentActivity.this,
                            "请填写完整数据", Toast.LENGTH_SHORT).show();
                } else {
                    String name = etName.getText().toString();
                    Double money = Double.valueOf(etMoney.getText().toString());
                    ContentValues values = new ContentValues();
                    values.put("name", name);
                    values.put("money", money);
                    resolver.insert(Uri.parse(uri), values);
                    setData(null, null);
                }
            }
        });
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etSearch.getText().toString().isEmpty()) {
                    Toast.makeText(ContentActivity.this,
                            "请填写完整数据", Toast.LENGTH_SHORT).show();
                } else {
                    String detail = etSearch.getText().toString();
                    setData("aid=? or name=? or money=?", new String[]{detail, detail, detail});
                }
            }
        });
    }

    private void setData(String selection, String[] selectionArgs) {
        accountList.clear();
        Cursor cursor = resolver.query(Uri.parse(uri), null, selection, selectionArgs, null);
        while (cursor.moveToNext()) {
            Account account = new Account();
            account.setAid(cursor.getInt(cursor.getColumnIndex("aid")));
            account.setName(cursor.getString(cursor.getColumnIndex("name")));
            account.setMoney(cursor.getDouble(cursor.getColumnIndex("money")));
            accountList.add(account);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_account_delete){
            Account account = (Account) adapter.getItem(0);
            resolver.delete(Uri.parse(uri), "aid=?", new String[]{String.valueOf(account.getAid())});
            setData(null, null);
        }
        return super.onContextItemSelected(item);
    }
}
