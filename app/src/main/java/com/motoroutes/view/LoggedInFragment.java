package com.motoroutes.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.motoroutes.R;
import com.motoroutes.model.Location;
import com.motoroutes.model.Route;
import com.motoroutes.viewmodel.LoggedInViewModel;
import com.motoroutes.viewmodel.MainActivityViewModel;

public class LoggedInFragment extends Fragment {

    private TextView loggedInUserTextView;
    private Button logOutButton;
    private LoggedInViewModel loggedInViewModel;
    private MainActivityViewModel mainActivityViewModel;

    //test Route Class
    private Route route;

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
            route = new Route("test","test description","center",4.5f,"hard");
            route.addRoutePoint(new Location(32.015596, 34.77325));
            route.addRoutePoint(new Location(32.01505654218039, 34.761792183366666));
            route.addRoutePoint(new Location(32.036756180283724, 34.76017340135148));
            route.addRoutePoint(new Location(32.03760225918485, 34.761066422833));
            route.addRoutePoint(new Location(32.03608821773392, 34.773936438301966));
            route.addRoutePoint(new Location(32.03844833026754, 34.77850660706034));
            PolylineOptions polyRoutes = new PolylineOptions();
            for (Location location : route.getPointsRoutes()){
                LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                polyRoutes.add(latLng);
            }
            polyRoutes.color( Color.parseColor( "#CC0000FF" ) );
            polyRoutes.width( 10 );
            polyRoutes.visible( true );
            googleMap.addPolyline(polyRoutes);
            //loggedInViewModel.addRoute(route);

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
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loggedInViewModel =  new ViewModelProvider(this).get(LoggedInViewModel.class);

    }


}
