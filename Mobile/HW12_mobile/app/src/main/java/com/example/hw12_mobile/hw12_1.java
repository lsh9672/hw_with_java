package com.example.hw12_mobile;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class hw12_1 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    GroundOverlayOptions videoMark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hw12_1);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng DaeJeon_CityHall = new LatLng(36.350474, 127.384821);//좌표(위도,경도)
        mMap.addMarker(new MarkerOptions().position(DaeJeon_CityHall).title("Marker in DaeJeon_CityHall"));//marker에 title달기
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(DaeJeon_CityHall,15));//zoom 정도 조정
        mMap.getUiSettings().setZoomControlsEnabled(true);//zoom 컨트롤 가능하게 하기
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);//지도 보여지는 타입 설정

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                videoMark = new GroundOverlayOptions().image(BitmapDescriptorFactory.fromResource(R.drawable.anafi_4k))//원하는 이미지 비트맵으로 가져옴
                        .position(latLng,50f,50f);//위치
                mMap.addGroundOverlay(videoMark);//Map위에 그림
            }
        });
    }
}
