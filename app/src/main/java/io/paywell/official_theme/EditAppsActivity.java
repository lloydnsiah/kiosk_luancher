package io.paywell.official_theme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import io.paywell.official_theme.adapter.EditAppAdapter;
import io.paywell.official_theme.extras.AppObject;

public class EditAppsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditAppAdapter adapter;
    private List<AppObject> appList;
    private ImageView btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_apps);

        recyclerView = findViewById(R.id.edit_apps_recyclerView);
        appList = getInstalledAppList();
        btn_back = findViewById(R.id.edit_activity_back);

        GridLayoutManager manager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(manager);
        adapter = new EditAppAdapter(this,appList);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditAppsActivity.this,SettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });



    }

    private List<AppObject> getInstalledAppList(){
        List<AppObject> list = new ArrayList<>();

        Intent intent = new Intent(Intent.ACTION_MAIN,null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> untreatedAppList = getApplicationContext().getPackageManager().queryIntentActivities(intent,0);

        for (ResolveInfo appInfo : untreatedAppList){
            String appName = appInfo.activityInfo.loadLabel(getPackageManager()).toString();
            String appPackageName = appInfo.activityInfo.packageName;
            Drawable appImage = appInfo.activityInfo.loadIcon(getPackageManager());

            AppObject object = new AppObject(appName,appPackageName,appImage);
            if (!list.contains(object)){
                list.add(object);
            }
        }
        return list;
    }

    @Override
    public void onBackPressed() {
        return;
    }
}