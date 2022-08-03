package io.paywell.official_theme.adapter;

import static io.paywell.official_theme.R.drawable.*;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.paywell.official_theme.R;
import io.paywell.official_theme.extras.AppObject;
import io.paywell.official_theme.extras.TinyDB;

public class EditAppAdapter extends RecyclerView.Adapter<EditAppAdapter.ViewHolder> {
    private Activity activity;
    private List<AppObject> list;

    public EditAppAdapter(Activity activity, List<AppObject> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_app_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppObject object = list.get(position);
        String app_name = object.getAppName();
        holder.image.setImageDrawable(object.getAppImage());
        holder.name.setText(app_name);
        TinyDB tinyDB = new TinyDB(activity.getApplicationContext());
        ArrayList<String> apps = tinyDB.getListString("ShowingApps");
        if (apps.contains(app_name)){
            holder.cancel.setImageResource(ic_cancel);
        }else{
            holder.cancel.setImageResource(ic_done);
        }

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> app_list = tinyDB.getListString("ShowingApps");
                if (!app_list.contains(app_name)){
                    app_list.add(app_name);
                    Toast.makeText(activity, "App Added", Toast.LENGTH_SHORT).show();
                }else{
                    app_list.remove(app_name);
                    Toast.makeText(activity, "App Removed", Toast.LENGTH_SHORT).show();
                }
                tinyDB.putListString("ShowingApps",app_list);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView cancel,image;
        private TextView name;
        private CardView layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cancel = itemView.findViewById(R.id.edit_app_select);
            image = itemView.findViewById(R.id.edit_app_image);
            name = itemView.findViewById(R.id.edit_app_name);
            layout = itemView.findViewById(R.id.all_apps_layout);
        }
    }
}
