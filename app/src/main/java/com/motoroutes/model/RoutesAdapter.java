package com.motoroutes.model;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.motoroutes.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RoutesAdapter extends RecyclerView.Adapter<RoutesAdapter.RoutesViewHolder> {

    Context context;
    ArrayList<Route> routesList;
    private myRouteListener listener;

    public interface myRouteListener{
        void onRouteClicked(int postion ,View view);
    }

    public RoutesAdapter(Context context, ArrayList<Route> routesList) {
        this.context = context;
        this.routesList = routesList;
    }

    @NonNull
    @NotNull
    @Override
    public RoutesViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.route_cell,parent,false);
        return new RoutesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RoutesViewHolder holder, int position) {
        Route route = routesList.get(position);
        holder.tv_name.setText(route.getName());
        holder.tv_area.setText(route.getArea());
        holder.tv_description.setText(route.getDescription());
        holder.tv_difficulty.setText(route.getDifficulty());
        String imageUrl = route.getImageUrl();
        if(imageUrl!=null) {
            Glide.with(context).load(imageUrl).into(holder.iv_route_pic);
            holder.iv_route_pic.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        else
            Glide.with(context).load(getURLForResource(R.drawable.default_route_image))
                    .into(holder.iv_route_pic);


    }

    @Override
    public int getItemCount() {
        return routesList.size();
    }

    public class RoutesViewHolder extends RecyclerView.ViewHolder{

        TextView tv_name, tv_description, tv_difficulty, tv_area;
        ImageView iv_route_pic;

        public RoutesViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.routes_list_cell_name);
            tv_description = itemView.findViewById(R.id.routes_list_cell_description);
            tv_difficulty = itemView.findViewById(R.id.routes_list_cell_difficulty);
            tv_area = itemView.findViewById(R.id.routes_list_cell_area);
            iv_route_pic = itemView.findViewById(R.id.routes_list_cell_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        listener.onRouteClicked(getAdapterPosition(),v);
                    }
                }
            });

        }
    }


    public String getURLForResource (int resourceId) {
        //use BuildConfig.APPLICATION_ID instead of R.class.getPackage().getName() if both are not same
        return Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +resourceId).toString();
    }
}
