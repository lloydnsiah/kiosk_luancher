package io.paywell.official_theme.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.paywell.official_theme.R;
import io.paywell.official_theme.extras.AppObject;

public class MainAdapterLinear extends RecyclerView.Adapter<MainAdapterLinear.ViewHolder> {

    private Context context;
    private List<AppObject> list;

    public MainAdapterLinear(Context context, List<AppObject> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_app_layout_linear,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppObject object = list.get(position);
        holder.name.setText(object.getAppName());
        String packagename = object.getPackageName();
        holder.image.setImageDrawable(object.getAppImage());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchAppIntent = context.getPackageManager().getLaunchIntentForPackage(packagename);
                if (launchAppIntent != null){
                    context.startActivity(launchAppIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView name;
        private ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.main_horizontal_name);
            image = itemView.findViewById(R.id.main_horizontal_image);
            cardView = itemView.findViewById(R.id.settings_layout_horizontal);
        }
    }
}
