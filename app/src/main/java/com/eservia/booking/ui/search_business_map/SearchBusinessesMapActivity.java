package com.eservia.booking.ui.search_business_map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.eservia.booking.R;
import com.eservia.booking.common.view.BaseActivity;
import com.eservia.booking.ui.booking.beauty.BookingBeautyActivity;
import com.eservia.booking.ui.business_page.beauty.BusinessPageBeautyActivity;
import com.eservia.booking.util.BusinessUtil;
import com.eservia.booking.util.ImageUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.eservia.model.entity.Address;
import com.eservia.model.entity.Business;
import com.eservia.model.entity.BusinessSector;
import com.eservia.simpleratingbar.SimpleRatingBar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import moxy.presenter.InjectPresenter;

public class SearchBusinessesMapActivity extends BaseActivity implements SearchBusinessesMapView,
        OnMapReadyCallback {

    private static final LatLng KYIV = new LatLng(50.452565, 30.530632);

    private static final float MIN_ZOOM_PREFERENCE = 6.0f;

    private static final int KYIV_AREA_CAMERA_ZOOM = 10;

    private static final int ADDRESS_MARKER_CAMERA_ZOOM = 15;

    RelativeLayout rlBusiness;
    RelativeLayout rlCardHolder;
    CardView cvContainer;
    ImageView ivBusinessImage;
    TextView tvBusinessName;
    TextView tvAddress;
    TextView tvDistance;
    SimpleRatingBar rbRating;
    TextView tvRating;
    ImageView ivBook;
    ImageView ivIconNext;
    Button btnBooking;
    ImageView ivBack;

    @InjectPresenter
    SearchBusinessesMapPresenter mPresenter;

    private GoogleMap mMap;

    private final List<Marker> mAddressMarkers = new ArrayList<>();

    public static void start(Context context, SearchBusinessesMapExtra extra) {
        EventBus.getDefault().postSticky(extra);
        Intent intent = new Intent(context, SearchBusinessesMapActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_business_map);
        WindowUtils.setBlackStatusBar(this);
        setUnbinder(ButterKnife.bind(this));
        rlBusiness = findViewById(R.id.rlBusiness);
        rlCardHolder = findViewById(R.id.rlCardHolder);
        cvContainer = findViewById(R.id.cvContainer);
        ivBusinessImage = findViewById(R.id.ivBusinessImage);
        tvBusinessName = findViewById(R.id.tvBusinessName);
        tvAddress = findViewById(R.id.tvAddress);
        tvDistance = findViewById(R.id.tvDistance);
        rbRating = findViewById(R.id.rbRating);
        tvRating = findViewById(R.id.tvRating);
        ivBook = findViewById(R.id.ivBook);
        ivIconNext = findViewById(R.id.ivIconNext);
        btnBooking = findViewById(R.id.btnBooking);
        mPresenter = new SearchBusinessesMapPresenter();
        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(v -> onBackClicked());
        initViews();
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

    public void onBackClicked() {
        mPresenter.onBackClicked();
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void setKyivCameraPosition() {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(KYIV)
                .zoom(KYIV_AREA_CAMERA_ZOOM)
                .build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onNewMarkers(List<AddressMarker> addressMarkers) {
        for (AddressMarker addressMarker : addressMarkers) {
            if (mapAlreadyContainsMarker(addressMarker)) {
                continue;
            }
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(BusinessUtil.latLngFromAddress(addressMarker.getAddress()))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_map_marker_blue)));
            marker.setTag(addressMarker);
            mAddressMarkers.add(marker);
        }
    }

    @Override
    public void onNewMarkersLoadingFailed(Throwable throwable) {
    }

    @Override
    public void clearMarkers() {
    }

    @Override
    public void setSelectedAddressMarker(AddressMarker addressMarker) {
        for (Marker marker : mAddressMarkers) {
            AddressMarker aMarker = (AddressMarker) marker.getTag();
            if (aMarker.getAddress().getId().equals(addressMarker.getAddress().getId())) {
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.icon_map_marker_green));
                marker.setZIndex(1.0f);
            } else {
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.icon_map_marker_blue));
                marker.setZIndex(0.0f);
            }
        }
    }

    @Override
    public void moveCameraToMarker(AddressMarker addressMarker) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(BusinessUtil.latLngFromAddress(addressMarker.getAddress()))
                .zoom(ADDRESS_MARKER_CAMERA_ZOOM)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void showAddressInfo(AddressMarker addressMarker)  {
        rlBusiness.setVisibility(View.VISIBLE);

        Business business = addressMarker.getBusiness();
        Address address = addressMarker.getAddress();

        rlBusiness.setOnClickListener(view -> mPresenter.onBusinessInfoClick());

        BusinessSector sector = business.getSector();
        if (sector != null) {
            if (BusinessUtil.mayShowBookingButton(sector.getSector())) {
                showBookingButton(true);
                btnBooking.setOnClickListener(view -> mPresenter.onBusinessReserveClick());
            } else {
                hideBookingButton();
            }
        } else {
            hideBookingButton();
        }

        try {
            ImageUtil.displayBusinessImageTransform(this, ivBusinessImage, business.getLogo(),
                    R.drawable.icon_business_photo_placeholder_beauty, R.drawable.mask_business_image);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        tvBusinessName.setText(business.getName());

        rbRating.setRating(BusinessUtil.starsRating(business.getRating()));

        tvRating.setText(BusinessUtil.formatRating(business.getRating()));

        tvAddress.setText(BusinessUtil.getFormattedAddress(address));

        String distance = BusinessUtil.formatAddressDistance(this, address);
        if (distance.isEmpty()) {
            tvDistance.setVisibility(View.GONE);
        } else {
            tvDistance.setVisibility(View.VISIBLE);
            tvDistance.setText(distance);
        }
    }

    @Override
    public void hideAddressInfo() {
        rlBusiness.setVisibility(View.GONE);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void setMyLocationEnabled(boolean enabled) {
        mMap.setMyLocationEnabled(enabled);
    }

    @Override
    public void showBookingBeautyActivity(Business business, Address address) {
        BookingBeautyActivity.start(this, business, address, null);
    }

    @Override
    public void showBusinessBeautyActivity(Business business) {
        BusinessPageBeautyActivity.start(this, business);
    }

    private void showBookingButton(boolean show) {
        if (show) {
            showBookingButton();
        } else {
            hideBookingButton();
        }
    }

    private void showBookingButton() {
        ivBook.setVisibility(View.GONE);
        ivIconNext.setVisibility(View.GONE);
        btnBooking.setVisibility(View.VISIBLE);
    }

    private void hideBookingButton() {
        ivBook.setVisibility(View.GONE);
        ivIconNext.setVisibility(View.VISIBLE);
        btnBooking.setVisibility(View.GONE);
    }

    private boolean mapAlreadyContainsMarker(AddressMarker addressMarker) {
        for (Marker marker : mAddressMarkers) {
            AddressMarker aMarker = (AddressMarker) marker.getTag();
            if (aMarker.getAddress().getId().equals(addressMarker.getAddress().getId())) {
                return true;
            }
        }
        return false;
    }

    private void initViews() {
        MapFragment mMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);
        prepareBusinessInfoLayout();
    }

    private void prepareBusinessInfoLayout() {
        ViewUtil.setCardOutlineProvider(this, rlCardHolder, cvContainer);

        int marginPx = (int) getResources().getDimension(R.dimen.window_left_right_margins);
        ViewUtil.setMargins(rlCardHolder, marginPx, marginPx, marginPx, marginPx);

        tvDistance.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
    }
}
