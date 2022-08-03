package io.paywell.official_theme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.paywell.official_theme.adapter.MainAdapterLinear;
import io.paywell.official_theme.extras.AppObject;
import io.paywell.official_theme.extras.TinyDB;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private RecyclerView app_recyclerView;
    private List<AppObject> appList;
    private ImageView settings, background_image, main_logo;
    private TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        getPermissions();
        appList = getInstalledAppList();

        recylerViewMethod();


        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
            }
        });


    }


    private void recylerViewMethod() {
        int number = tinyDB.getInt("Layout");
        if (number == 1) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            app_recyclerView.setLayoutManager(layoutManager);
            MainAdapterLinear adapter = new MainAdapterLinear(context, appList);
            adapter.notifyDataSetChanged();
            app_recyclerView.setAdapter(adapter);

        } else {
            GridLayoutManager manager = new GridLayoutManager(this, 2);
            app_recyclerView.setLayoutManager(manager);
            MainAdapterLinear adapter = new MainAdapterLinear(context, appList);
            adapter.notifyDataSetChanged();
            app_recyclerView.setAdapter(adapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        recylerViewMethod();
    }

    private void showPopUpMenu() {
        PopupMenu popupMenu = new PopupMenu(context, settings);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_settings:
                        showCustomDialog();
                        return true;
                    case R.id.menu_exit:
                        finish();
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();

    }

    void showCustomDialog() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_password_dialog);

        CardView btn_enter = dialog.findViewById(R.id.dialog_enter_btn);
        EditText pass = dialog.findViewById(R.id.dialog_editText);

        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = tinyDB.getString("Password");
                String pass_text = pass.getText().toString();
                if (TextUtils.isEmpty(pass_text)) {
                    Toast.makeText(context, "Please Enter Password", Toast.LENGTH_SHORT).show();
                } else {
                    if (pass_text.equals(password)) {
                        dialog.dismiss();
                        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(context, "Wrong Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        dialog.show();
    }

    public void init() {
        context = getApplicationContext();
        app_recyclerView = findViewById(R.id.app_recyclerView);
        settings = findViewById(R.id.main_settings);
        background_image = findViewById(R.id.main_background_image);
        main_logo = findViewById(R.id.main_background_logo);
        tinyDB = new TinyDB(MainActivity.this);
    }

    private List<AppObject> getInstalledAppList() {
        List<AppObject> list = new ArrayList<>();
        ArrayList<String> showing_apps = tinyDB.getListString("ShowingApps");
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> untreatedAppList = getApplicationContext().getPackageManager().queryIntentActivities(intent, 0);

        for (ResolveInfo appInfo : untreatedAppList) {
            String appName = appInfo.activityInfo.loadLabel(getPackageManager()).toString();
            String appPackageName = appInfo.activityInfo.packageName;
            Drawable appImage = appInfo.activityInfo.loadIcon(getPackageManager());

            AppObject object = new AppObject(appName, appPackageName, appImage);
            if (!list.contains(object)) {

                if (showing_apps.contains(object.getAppName())) {
                    list.add(object);
                }

            }
        }
        return list;
    }

    private void getPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
    }


    @Override
    protected void onStart() {
        super.onStart();
        String password = tinyDB.getString("Password");
        if (TextUtils.isEmpty(password)) {
            tinyDB.putString("Password", "0000");
        }

    }

    @Override
    public void onBackPressed() {
        return;
    }
}