package com.example.winter_project;

import androidx.appcompat.app.AppCompatActivity;


import android.app.FragmentManager;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ggmap extends AppCompatActivity implements OnMapReadyCallback {
    private FragmentManager fragmentManager;
    private MapFragment mapFragment;
    double atitude_value,longtitude_value;
    TextView addresstext;
    Button ggmap_ok;

    String address_location, uni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ggmap);

        ggmap_ok = (Button)findViewById(R.id.ggmap_ok);

        addresstext = (TextView)findViewById(R.id.addresstext);

        fragmentManager = getFragmentManager();
        mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();

        uni = intent.getStringExtra("unique_name");

        atitude_value=Double.parseDouble(intent.getStringExtra("at"));
        longtitude_value = Double.parseDouble(intent.getStringExtra("long"));

        addresstext.setText(getAddress(atitude_value,longtitude_value));

        ggmap_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ggmap.this, picture_management.class);
                intent.putExtra("unique_name",uni);
                intent.putExtra("select_la",Double.toString(atitude_value));
                intent.putExtra("select_lo",Double.toString(longtitude_value));
                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng loc = new LatLng(atitude_value,longtitude_value);//위도 경도를 의미
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title("촬영한 위치");
        markerOptions.position(loc);
        googleMap.addMarker(markerOptions);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 16));//좀더 가까이서 보고싶으면 숫자를 올리고 넓게 보고싶으면 내림, 적정선은 16
    }
    public String getAddress(double a,double l){
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addressList;
        try{
            if(geocoder !=null) {
                addressList = geocoder.getFromLocation(a, l, 1);
                if(addressList !=null && addressList.size()>0){
                    String aaa = addressList.get(0).getAddressLine(0).toString();
                    address_location = aaa;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return address_location;
    }
}
