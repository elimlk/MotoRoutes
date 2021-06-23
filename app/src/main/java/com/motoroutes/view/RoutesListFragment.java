package com.motoroutes.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.motoroutes.R;
import com.motoroutes.model.Route;
import com.motoroutes.model.RoutesAdapter;
import com.motoroutes.viewmodel.RoutesListViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RoutesListFragment extends Fragment {

    private RoutesListViewModel routesListViewModel;

    RecyclerView recyclerView;
    ArrayList<Route> routesList;
    RoutesAdapter routesAdapter;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        routesListViewModel = new ViewModelProvider(this).get(RoutesListViewModel.class);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_routes_list, container,false);
        recyclerView = view.findViewById(R.id.recycler_view_routes_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        routesList = routesListViewModel.getRoutes();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                routesList = routesListViewModel.getRoutes();
            }});

        t.start(); // spawn thread

        try {
            t.join();  // wait for thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        routesAdapter = new RoutesAdapter(this.getContext(), routesList);
        recyclerView.setAdapter(routesAdapter);

        routesAdapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}
