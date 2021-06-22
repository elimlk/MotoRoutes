package com.motoroutes.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
    ArrayList<Route> routsList;
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

        routsList = new ArrayList<Route>();

        routesAdapter = new RoutesAdapter(this.getContext(),routsList);
        recyclerView.setAdapter(routesAdapter);


        routsList.add(new Route("test1","test desc",
                "test area",0f,"test diff"));
        routsList.add(new Route("te21","t2t desc",
                "te2area",1f,"te2 diff"));
        routesAdapter.notifyDataSetChanged();




        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
