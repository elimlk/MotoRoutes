package com.motoroutes.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.motoroutes.R;
import com.motoroutes.model.FileUtils;
import com.motoroutes.model.Route;
import com.motoroutes.model.RouteBuilder;
import com.motoroutes.viewmodel.LoggedInViewModel;

import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;

public class LoggedInFragment extends Fragment {

    private LoggedInViewModel loggedInViewModel;
    private int toolBarItemState;
    private String route_difficulty;
    private String route_area;
    private Route route;
    private String gpxPath;
    private String imagePath;
    private CardView cardView_add_route;
    private RouteBuilder routeBuilder = new RouteBuilder();
    private static final int MY_REQUEST_CODE_PERMISSION = 1000;
    private static final int MY_RESULT_CODE_FILECHOOSER = 2000;
    private static final int MY_RESULT_CODE_IMAGECHOOSER = 3000;
    private static final String LOG_TAG = "LoggedInFragment";

    private Button buttonRouteBrowse;
    private Button buttonImageBrowse;


    Observer<String> toolBarState = new Observer<String>() {
        @Override
        public void onChanged(String s) {
            toolBarItemState = Integer.parseInt(s);
            switch (toolBarItemState){
                case R.id.item_routes:
                    Navigation.findNavController(getActivity(),R.id.activity_main_navHostFragment).navigate(R.id.action_loggedInFragmemt_to_routesListFragment);

                    break;
                case R.id.item_addRoute:
                    CardView cardView = getView().findViewById(R.id.card_addRoute);
                    if (cardView.getVisibility()==View.VISIBLE)
                        cardView.setVisibility(View.GONE);
                    else
                        cardView.setVisibility(View.VISIBLE);
                    break;
                case R.id.item_emergency:

                    break;
                case R.id.item_logout:

                    break;
            }
        }
    };

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            if (route != null)
                googleMap.addPolyline(routeBuilder.createPolygon(route.getPointsRoutes()));
            LatLng hit_collage = new LatLng(32.015596, 34.77325);
            googleMap.addMarker(new MarkerOptions().position(hit_collage).title("Marker in Sydney"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(hit_collage));
        }

    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logged_in, container,false);
        MainActivity.changeToolbarVisibility(true);
        buttonRouteBrowse = view.findViewById(R.id.btn_add_route_file_picker);
        buttonRouteBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askPermissionAndBrowseFile(MY_RESULT_CODE_FILECHOOSER);
            }
        });

        buttonImageBrowse = view.findViewById(R.id.btn_add_route_image_picker);
        buttonImageBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askPermissionAndBrowseFile(MY_RESULT_CODE_IMAGECHOOSER);
            }
        });
        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        super.onViewCreated(view, savedInstanceState);
        cardView_add_route = view.findViewById(R.id.card_addRoute);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
        loggedInViewModel.getToolBarItemStateMutableLiveData().observe(getViewLifecycleOwner(),toolBarState);

        //define spinners for add_route cardView
        Spinner spinnerDifficulty = view.findViewById(R.id.sp_addRoute_difficulty);
        Spinner spinnerArea = view.findViewById(R.id.sp_addRoute_area);
        Button btn_add_route_cancel = view.findViewById(R.id.btn_add_route_cancel);
        Button btn_add_route_add = view.findViewById(R.id.btn_add_route_add);
        EditText et_routeName = view.findViewById(R.id.et_addRoute_name);
        EditText et_description = view.findViewById(R.id.et_addRoute_description);

        btn_add_route_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardView_add_route.setVisibility(View.GONE);
            }
        });
        spinnerDifficulty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                route_difficulty = String.valueOf(parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                route_area = String.valueOf(parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_add_route_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String routeName = et_routeName.getText().toString();
                String routeDescription = et_description.getText().toString();
                route = new Route(routeName,routeDescription,route_area,0f,route_difficulty);
                try {
                    route.setMyLocations(routeBuilder.parseGpxToArray(getPath()));
                    loggedInViewModel.addRoute(route);
                    SupportMapFragment mapFragment =
                            (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                    mapFragment.getMapAsync(callback);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                et_routeName.getText().clear();
                et_description.getText().clear();
            }
        });
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loggedInViewModel =  new ViewModelProvider(this).get(LoggedInViewModel.class);


    }

    private void askPermissionAndBrowseFile(int requestCode) {
        // With Android Level >= 23, you have to ask the user
        // for permission to access External Storage.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) { // Level 23

            // Check if we have Call permission
            int permisson = ActivityCompat.checkSelfPermission(this.getContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE);

            if (permisson != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                this.requestPermissions(
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, requestCode);
                return;
            }
        }
        this.doBrowseFile();
    }

    private void doBrowseFile() {
        Intent chooseFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFileIntent.setType("*/*");
        // Only return URIs that can be opened with ContentResolver
        chooseFileIntent.addCategory(Intent.CATEGORY_OPENABLE);

        chooseFileIntent = Intent.createChooser(chooseFileIntent, "Choose a file");
        startActivityForResult(chooseFileIntent, MY_RESULT_CODE_FILECHOOSER);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_REQUEST_CODE_PERMISSION: {

                // Note: If request is cancelled, the result arrays are empty.
                // Permissions granted (CALL_PHONE).
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.i( LOG_TAG,"Permission granted!");
                    Toast.makeText(this.getContext(), "Permission granted!", Toast.LENGTH_SHORT).show();

                    this.doBrowseFile();
                }
                // Cancelled or denied.
                else {
                    Log.i(LOG_TAG,"Permission denied!");
                    Toast.makeText(this.getContext(), "Permission denied!", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        switch (requestCode) {
            case MY_RESULT_CODE_FILECHOOSER:
                if (resultCode == Activity.RESULT_OK ) {
                    if(data != null)  {
                        Uri fileUri = data.getData();
                        Log.i(LOG_TAG, "Uri: " + fileUri);

                        String filePath = null;
                        try {
                            filePath = FileUtils.getPath(this.getContext(),fileUri);
                        } catch (Exception e) {
                            Log.e(LOG_TAG,"Error: " + e);
                            Toast.makeText(this.getContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
                        }
                        gpxPath = (filePath);
                    }
                }
                break;
            case MY_RESULT_CODE_IMAGECHOOSER:
                if (resultCode == Activity.RESULT_OK ) {
                    if(data != null)  {
                        Uri fileUri = data.getData();
                        Log.i(LOG_TAG, "Uri: " + fileUri);

                        String filePath = null;
                        try {
                            filePath = FileUtils.getPath(this.getContext(),fileUri);
                        } catch (Exception e) {
                            Log.e(LOG_TAG,"Error: " + e);
                            Toast.makeText(this.getContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
                        }
                        imagePath = (filePath);
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getPath()  {
        return gpxPath;
    }

    public String getImagePath() { return imagePath; }
}
