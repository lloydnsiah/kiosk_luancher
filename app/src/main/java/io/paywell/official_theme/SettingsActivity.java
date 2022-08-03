package io.paywell.official_theme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.paywell.official_theme.adapter.SettingsAdapter;

public class SettingsActivity extends AppCompatActivity {

    private Context context;
    private RecyclerView listView;
    private List<String> settings_List;
    private ImageView btn_back;
    private LinearLayout exit_app;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        init();
        settings_List = getList();

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(layoutManager);
        SettingsAdapter adapter = new SettingsAdapter(SettingsActivity.this,settings_List);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        exit_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });

    }

    private List<String> getList() {
        List<String> list = new ArrayList<>();
        list.add("Change Images");
        list.add("Reset Passwords");
        list.add("Show All Apps");
        list.add("Change Apps Layout");
        list.add("Default Settings");

        return list;
    }

    public void init(){
        context = getApplicationContext();
        listView = findViewById(R.id.settings_recyclerview);
        btn_back = findViewById(R.id.settings_back);
        exit_app = findViewById(R.id.linearLayout_exit_app);
    }

    @Override
    public void onBackPressed() {
       return;
    }
}