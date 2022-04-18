package com.eservia.booking.ui.business_page.map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.eservia.booking.R;
import com.eservia.booking.common.view.BaseActivity;
import com.eservia.booking.util.MessageUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import moxy.presenter.InjectPresenter;

public class BusinessMapActivity extends BaseActivity implements BusinessMapView,
        OnMapReadyCallback {

    private static final String KEY_TITLE = "title";
    private static final String KEY_LAT = "lat";
    private static final String KEY_LNG = "lng";

    private static final float MIN_ZOOM_PREFERENCE = 4.0f;

    private static final int MARKER_CAMERA_ZOOM = 16;

    RelativeLayout rlContainer;
    Button btnRoute;
    ImageView ivBack;

    @InjectPresenter
    BusinessMapPresenter mPresenter;

    private GoogleMap mMap;

    public static void start(Context context, String title, double lat, double lng) {
        Intent intent = new Intent(context, BusinessMapActivity.class);
        intent.putExtra(KEY_TITLE, title);
        intent.putExtra(KEY_LAT, lat);
        intent.putExtra(KEY_LNG, lng);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_map);
        WindowUtils.setBlackStatusBar(this);
        setUnbinder(ButterKnife.bind(this));
        rlContainer = findViewById(R.id.rlContainer);
        btnRoute = findViewById(R.id.btnRoute);
        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(v -> onBackClicked());
        btnRoute.setOnClickListener(v -> onRouteClick());
        initViews();
    }

    public void onBackClicked() {
        mPresenter.onBackClick();
    }
    public void onRouteClick() {
        mPresenter.onRouteClick();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(mPresenter);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.setMinZoomPreference(MIN_ZOOM_PREFERENCE);
        mPresenter.onMapReady();
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void setMarker(LatLng destination, String title) {
        mMap.addMarker(new MarkerOptions()
                .position(destination)
                .title(title)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_map_marker_green)))
                .showInfoWindow();
    }

    @Override
    public void animateCamera(LatLng destination) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(destination)
                .zoom(MARKER_CAMERA_ZOOM)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void moveCamera(LatLng destination) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(destination)
                .zoom(MARKER_CAMERA_ZOOM)
                .build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void setRoutButtonVisible(boolean visible) {
        btnRoute.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void startRouteIntent(Intent intent) {
        try {
            startActivity(intent);
        } catch (Exception e) {
            MessageUtil.showSnackbar(rlContainer, R.string.route_loading_failed);
        }
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void setMyLocationEnabled(boolean enabled) {
        mMap.setMyLocationEnabled(enabled);
    }

    @Override
    public void initExtra() {
        String title = getIntent().getStringExtra(KEY_TITLE);
        double lat = getIntent().getDoubleExtra(KEY_LAT, 0.0);
        double lng = getIntent().getDoubleExtra(KEY_LNG, 0.0);
        mPresenter.onExtra(title, lat, lng);
    }

    private void initViews() {
        MapFragment mMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);
    }
}
