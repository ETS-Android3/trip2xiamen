package com.t2xm.application.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.t2xm.R;
import com.t2xm.utils.valuesConverter.ImageUtil;

public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {

    private double latitude;
    private double longitude;
    private String locationName;
    private String itemImageName;

    private ImageView iv_itemImage;
    private TextView tv_itemName;
    private Button btn_backToLocation;

    private LatLng latLng;
    private MarkerOptions markerOptions;

    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        latitude = getIntent().getDoubleExtra("latitude", -1000d);
        longitude = getIntent().getDoubleExtra("longitude", -1000d);
        locationName = getIntent().getStringExtra("locationName");
        itemImageName = getIntent().getStringExtra("image") ;
        latLng = new LatLng(latitude, longitude);
        markerOptions = new MarkerOptions().position(latLng).title(locationName);

        setupActivityComponents();
        setupActivityListeners();
        updateItemInformation();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        moveMapCamera();
        createMapMarker();
    }

    private void moveMapCamera() {
        map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
    }

    private void createMapMarker() {
        map.addMarker(markerOptions);
    }

    private void setupActivityComponents() {
        iv_itemImage = findViewById(R.id.iv_item_image);
        tv_itemName = findViewById(R.id.tv_item_name);
        btn_backToLocation = findViewById(R.id.btn_back_to_location);
    }

    private void setupActivityListeners() {
        btn_backToLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveMapCamera();
                createMapMarker();
            }
        });
    }

    private void updateItemInformation() {
        iv_itemImage.setImageDrawable(getDrawable(getResources().getIdentifier(itemImageName, "drawable", getPackageName())));
        tv_itemName.setText(locationName);
    }
}
