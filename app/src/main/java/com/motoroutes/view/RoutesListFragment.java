package com.motoroutes.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.motoroutes.R;
import com.motoroutes.model.Route;
import com.motoroutes.model.RoutesAdapter;
import com.motoroutes.viewmodel.RoutesListViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RoutesListFragment extends Fragment {

    private RoutesListViewModel routesListViewModel;
    private int toolBarItemState;
    private CardView popupCardView;
    private TextView tv_pop_name;
    private TextView tv_pop_area;
    private TextView tv_pop_diff;
    private TextView tv_pop_desc;
    private ImageView imv_pop_image;

    RecyclerView recyclerView;
    ArrayList<Route> routesList;
    RoutesAdapter routesAdapter;

    Observer<String> toolBarState = new Observer<String>() {
        @Override
        public void onChanged(String s) {
            toolBarItemState = Integer.parseInt(s);
            switch (toolBarItemState){
                case R.id.item_routes:
                    //Navigation.findNavController(getActivity(),R.id.activity_main_navHostFragment).navigate(R.id.action_loggedInFragmemt_to_routesListFragment);
                    break;
                case R.id.item_addRoute:
                    Navigation.findNavController(getActivity(),R.id.activity_main_navHostFragment).navigate(R.id.action_routesListFragment_to_loggedInFragmemt);
                    break;
                case R.id.item_emergency:

                    break;
                case R.id.item_logout:
                    routesListViewModel.logOut();
                    Navigation.findNavController(getActivity(),R.id.activity_main_navHostFragment).navigate(R.id.action_routesListFragment_to_loginFragment);
                    break;
                case R.id.item_map:
                    Navigation.findNavController(getActivity(),R.id.activity_main_navHostFragment).navigate(R.id.action_routesListFragment_to_loggedInFragmemt);


                    break;
            }
        }
    };

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

        popupCardView = view.findViewById(R.id.route_popup_card);
        tv_pop_name = view.findViewById(R.id.route_popup_name);
        tv_pop_area = view.findViewById(R.id.route_popup_area);
        tv_pop_diff = view.findViewById(R.id.route_popup_difficulty);
        tv_pop_desc = view.findViewById(R.id.route_popup_description);
        imv_pop_image = view.findViewById(R.id.route_popup_image);

        routesList = routesListViewModel.getRoutes();

        /*Thread t = new Thread(new Runnable() {
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
*/

        routesAdapter = new RoutesAdapter(this.getContext(), routesList);
        routesAdapter.setListener(new RoutesAdapter.MyRouteListener() {
            @Override
            public void onRouteClicked(int position, View view) {

                popupCardView.setVisibility(View.VISIBLE);

                tv_pop_name.setText(routesList.get(position).getName());
                tv_pop_area.setText(routesList.get(position).getArea());
                tv_pop_diff.setText(routesList.get(position).getDifficulty());
                tv_pop_desc.setText(routesList.get(position).getDescription());

                Glide.with(getContext()).load(routesList.get(position).getImageUrl()).into(imv_pop_image);

            }
        });

        recyclerView.setAdapter(routesAdapter);

        routesAdapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        routesListViewModel.getToolBarItemStateMutableLiveData().observe(getViewLifecycleOwner(),toolBarState);

    }

}
