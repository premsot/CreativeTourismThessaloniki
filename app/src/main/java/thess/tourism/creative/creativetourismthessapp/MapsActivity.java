package thess.tourism.creative.creativetourismthessapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import thess.tourism.creative.creativetourismthessapp.DbFiles.BikesParkingPois;
import thess.tourism.creative.creativetourismthessapp.DbFiles.Creator;
import thess.tourism.creative.creativetourismthessapp.DbFiles.CreatorDao;
import thess.tourism.creative.creativetourismthessapp.DbFiles.SqliteUtils;
import thess.tourism.creative.creativetourismthessapp.DbFiles.StartPoint;
import thess.tourism.creative.creativetourismthessapp.DbFiles.StartPointDao;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationChangeListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private ArrayList<Integer> selectedCategories;
    boolean showAll = false;
    int filter = -1;
    List<Creator> creatorsForMap;
    long startingPoint;
    int distance;
    StartPoint startPoint;
    float mapTopPadding, mapLeftRightPadding;
    HashMap<Long, Marker> markerHashMap = new HashMap<>();
    Drawable drawableToGetPadding = null;
    List<BikesParkingPois> bikesParkingPois;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        selectedCategories = getIntent().getIntegerArrayListExtra("sel_categories");
        showAll = getIntent().getBooleanExtra("showAll", false);
        if (!showAll) {
            filter = getIntent().getIntExtra("filter", -1);
        } else {
            creatorsForMap = SqliteUtils.getDaoSession().getCreatorDao().loadAll();
        }

        if (filter == 2) {
            startingPoint = getIntent().getLongExtra("startingPoint", -1);
            startPoint = SqliteUtils.getDaoSession().getStartPointDao().queryBuilder().where(StartPointDao.Properties.Id.eq(startingPoint)).unique();
            creatorsForMap = SqliteUtils.getDaoSession().getCreatorDao().queryBuilder().where(CreatorDao.Properties.StartingPointId.eq(startingPoint)).list();
        } else if (filter == 1) {
            distance = getIntent().getIntExtra("distance", 0);
            creatorsForMap = SqliteUtils.getDaoSession().getCreatorDao().loadAll();

        }
        bikesParkingPois = SqliteUtils.getDaoSession().getBikesParkingPoisDao().loadAll();

        mapFragment.getMapAsync(this);
        ImageButton backBtn = (ImageButton) findViewById(R.id.btn_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng center = new LatLng(40.635138, 22.941282);


        mMap.setMyLocationEnabled(true);
        mMap.setOnMarkerClickListener(this);

        for (BikesParkingPois aPoi : bikesParkingPois) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(aPoi.getLatitude(), aPoi.getLongitude())).title(aPoi.getName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.bike)));
        }

        if (showAll) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (Creator creator : creatorsForMap) {
                if (!selectedCategories.contains(creator.getCategoryId().intValue())) {
                    continue;
                }
                LatLng creatorPoi = new LatLng(creator.getLat(), creator.getLongitude());
                builder.include(creatorPoi);
                Marker marker = mMap.addMarker(new MarkerOptions().position(creatorPoi).icon(BitmapDescriptorFactory.fromResource(getCreatorImg(creator.getMap_image()))));
                markerHashMap.put(creator.getId(), marker);
            }
            LatLngBounds bounds = builder.build();
            mMap.setPadding((int) mapLeftRightPadding, (int) mapTopPadding, (int) mapLeftRightPadding, 0);

            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0));

        } else if (filter == 2) {
            boolean foundAtLeastOne = false;
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(new LatLng(startPoint.getLat(), startPoint.getLongitude()));

            mMap.addMarker(new MarkerOptions().position(new LatLng(startPoint.getLat(), startPoint.getLongitude())).icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_you)));
            for (Creator creator : creatorsForMap) {
                if (!selectedCategories.contains(creator.getCategoryId().intValue())) {
                    continue;
                }
                foundAtLeastOne = true;
                LatLng creatorPoi = new LatLng(creator.getLat(), creator.getLongitude());
                builder.include(creatorPoi);
                Marker marker = mMap.addMarker(new MarkerOptions().position(creatorPoi).icon(BitmapDescriptorFactory.fromResource(getCreatorImg(creator.getMap_image()))));
                markerHashMap.put(creator.getId(), marker);
            }

            if (!foundAtLeastOne) {
                onFailToFindCreators();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 14f));
                return;
            }
            LatLngBounds bounds = builder.build();
            mMap.setPadding((int) mapLeftRightPadding, (int) mapTopPadding, (int) mapLeftRightPadding, 0);
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0));

        } else if (filter == 1) {
            mMap.setOnMyLocationChangeListener(this);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 14f));
        }
    }

    private void onFailToFindCreators() {
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.fail_to_find_creators), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMyLocationChange(Location location) {

        if (location != null && filter == 1 && distance != 0) {
            mMap.setOnMyLocationChangeListener(null);
            boolean atLeastOneFound = false;
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (Creator creator : creatorsForMap) {
                if (!selectedCategories.contains(creator.getCategoryId().intValue())) {
                    continue;
                }
                if (getDistanceFromMe(location, new LatLng(creator.getLat(), creator.getLongitude())) > distance) {
                    continue;
                }
                atLeastOneFound = true;
                LatLng creatorPoi = new LatLng(creator.getLat(), creator.getLongitude());
                builder.include(creatorPoi);
                Marker marker = mMap.addMarker(new MarkerOptions().position(creatorPoi).icon(BitmapDescriptorFactory.fromResource(getCreatorImg(creator.getMap_image()))));
                markerHashMap.put(creator.getId(), marker);
            }
            if (!atLeastOneFound) {
                onFailToFindCloseCreators();
                return;
            }
            builder.include(new LatLng(location.getLatitude(), location.getLongitude()));
            LatLngBounds bounds = builder.build();
            mMap.setPadding((int) mapLeftRightPadding, (int) mapTopPadding, (int) mapLeftRightPadding, 0);
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0));
        }
    }

    private void onFailToFindCloseCreators() {
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.fail_to_find_close_creators), Toast.LENGTH_LONG).show();
    }

    private int getDistanceFromMe(Location location, LatLng latLng) {
        float[] results = new float[1];
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                latLng.latitude, latLng.longitude,
                results);
        return ((int) results[0]);
    }

    private int getCreatorImg(String image) {
        String uri = "drawable/" + image;
        // int imageResource = R.drawable.icon;
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        if (drawableToGetPadding == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawableToGetPadding = getResources().getDrawable(imageResource, getApplicationContext().getTheme());
            } else {
                drawableToGetPadding = getResources().getDrawable(imageResource);
            }

            mapTopPadding = drawableToGetPadding.getIntrinsicHeight();
            mapLeftRightPadding = drawableToGetPadding.getIntrinsicWidth() / 2;
        }
        return imageResource;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        for (Map.Entry<Long, Marker> entry : markerHashMap.entrySet()) {
            if (entry.getValue().equals(marker)) {
                Intent anIntent = new Intent(MapsActivity.this, DetailsActivity.class);
                anIntent.putExtra("creator", entry.getKey());
                startActivity(anIntent);
                return true;
            }
        }
        marker.showInfoWindow();
        return true;
    }
}
