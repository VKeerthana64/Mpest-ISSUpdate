package com.amana.pentapest;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.amana.pentapest.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mGoogleMap;
    private double mlatitude, mlongitude;
    private String mLocation = "", mLatlog = "";
    Context mContext;
    public LatLng toLatLng = null;
    @BindView(R.id.btn_back)
    Button back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mContext = this;
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mLocation = extras.getString("Location");
            mLatlog = extras.getString("LatLong");

            String[] latLong = mLatlog.split(",");
            mlongitude = Double.parseDouble(latLong[0].toString().trim());
            mlatitude = Double.parseDouble(latLong[1].toString().trim());
            toLatLng = new LatLng(mlatitude, mlongitude);

        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng toLatLng = new LatLng(mlatitude, mlongitude);

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(toLatLng, 15));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomIn());

        // Add a marker in Sydney and move the camera
        mGoogleMap.addMarker(new MarkerOptions().position(toLatLng).title(mLocation));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(toLatLng));
    }
}
