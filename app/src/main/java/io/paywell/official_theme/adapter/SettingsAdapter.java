package io.paywell.official_theme.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import io.paywell.official_theme.ChangeImageActivity;
import io.paywell.official_theme.EditAppsActivity;
import io.paywell.official_theme.R;
import io.paywell.official_theme.extras.TinyDB;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder> {

    private Activity activity;
    private List<String> list;

    public SettingsAdapter(Activity activity, List<String> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = list.get(position);
        holder.text.setText(name);
        TinyDB tinyDB = new TinyDB(activity);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (name){
                    case "Change Images":
                        Intent intent_1 = new Intent(activity.getApplicationContext(), ChangeImageActivity.class);
                        intent_1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(intent_1);
                        break;
                    case "Reset Passwords":
                        showCustomDialog();
                        break;
                    case "Show All Apps":
                        Intent intent = new Intent(activity.getApplicationContext(), EditAppsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(intent);
                        break;
                    case "Default Settings":
                        Snackbar.make(v, "Are You sure you want to return to default?", Snackbar.LENGTH_LONG)
                                .setAction("Yes", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        tinyDB.remove("Layout");
                                        tinyDB.remove("Background_Image");
                                        tinyDB.remove("Logo_Image");
                                        tinyDB.remove("ShowingApps");
                                        Toast.makeText(activity, "Done", Toast.LENGTH_SHORT).show();
                                    }
                                }).show();
                        break;
                    case "Change Apps Layout":
                        int number = tinyDB.getInt("Layout");
                        if (number ==1){
                            tinyDB.putInt("Layout",2);
                        }else if (number ==2){
                            tinyDB.putInt("Layout",1);
                        }
                        Toast.makeText(activity, "Layout Changed", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        return;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView layout;
        private TextView text;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.settings_layout);
            text = itemView.findViewById(R.id.settings_text);
        }
    }

    void showCustomDialog() {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_password_dialog);

        CardView btn_enter = dialog.findViewById(R.id.dialog_enter_btn);
        EditText pass = dialog.findViewById(R.id.dialog_editText);

        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TinyDB tinyDB = new TinyDB(activity);
                String pass_text = pass.getText().toString();
                if (TextUtils.isEmpty(pass_text)){
                    Toast.makeText(activity.getApplicationContext(), "Please Enter Password", Toast.LENGTH_SHORT).show();
                }else{
                    tinyDB.putString("Password",pass_text);
                    dialog.dismiss();
                    Toast.makeText(activity, "Password change successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }
}
