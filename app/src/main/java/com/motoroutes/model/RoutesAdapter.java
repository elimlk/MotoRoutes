package com.motoroutes.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.motoroutes.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RoutesAdapter extends RecyclerView.Adapter<RoutesAdapter.RoutesViewHolder> {

    Context context;
    ArrayList<Route> routesList;

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
        //TODO set image
        //holder.iv_route_pic

    }

    @Override
    public int getItemCount() {
        return routesList.size();
    }

    public static class RoutesViewHolder extends RecyclerView.ViewHolder{

        TextView tv_name, tv_description, tv_difficulty, tv_area;
        ImageView iv_route_pic;

        public RoutesViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.routes_list_cell_name);
            tv_description = itemView.findViewById(R.id.routes_list_cell_description);
            tv_difficulty = itemView.findViewById(R.id.routes_list_cell_difficulty);
            tv_area = itemView.findViewById(R.id.routes_list_cell_area);

        }
    }
}
