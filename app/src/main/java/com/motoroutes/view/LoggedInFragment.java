package com.motoroutes.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.motoroutes.R;
import com.motoroutes.model.Route;
import com.motoroutes.model.User;
import com.motoroutes.viewmodel.LoggedInViewModel;
import com.motoroutes.viewmodel.MainActivityViewModel;

import org.jetbrains.annotations.NotNull;

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
            route.addRoutePoint(new LatLng(32.015596, 34.77325));
            route.addRoutePoint(new LatLng(32.01505654218039, 34.761792183366666));
            route.addRoutePoint(new LatLng(32.036756180283724, 34.76017340135148));
            route.addRoutePoint(new LatLng(32.03760225918485, 34.761066422833));
            route.addRoutePoint(new LatLng(32.03608821773392, 34.773936438301966));
            route.addRoutePoint(new LatLng(32.03844833026754, 34.77850660706034));
            googleMap.addPolyline(route.getPolyRoutes());

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

/*        loggedInViewModel =  new ViewModelProvider(this).get(LoggedInViewModel.class);
        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        loggedInViewModel.getUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null){
                    String userID = firebaseUser.getUid();
                    DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference("Users");
                    firebaseDatabase.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            User userProfile = snapshot.getValue(User.class);
                            loggedInUserTextView.setText("Hello "+userProfile.fullname);
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });;

                }
            }
        });

        loggedInViewModel.getLoggedOutMutableLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loggedOut) {
                if(loggedOut){
                    Navigation.findNavController(getView()).navigate(R.id.action_loggedInFragmemt_to_loginFragment);
                }
            }
        });

    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_logged_in, container,false);
        loggedInUserTextView = view.findViewById(R.id.fragment_loggedin_loggedInUser);
        logOutButton = view.findViewById(R.id.fragment_loggedin_logOut);

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loggedInViewModel.logOut();
            }
        });

        MainActivity.changeToolbarVisibility(true);
        return view;
    }*/


    }
}
