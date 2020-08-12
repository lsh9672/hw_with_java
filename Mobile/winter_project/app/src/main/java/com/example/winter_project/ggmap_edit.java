package com.example.winter_project;

import android.app.FragmentManager;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ggmap_edit extends AppCompatActivity implements OnMapReadyCallback {
    private FragmentManager fragmentManager;
    private MapFragment mapFragment;
    double latitude_select,longtitude_select;
    TextView ggmap_edit_text;
    Button ggmap_edit_ok,ggmap_edit_no;

    String address_location1,uni_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ggmap_editmode);

        Intent intent = getIntent();

        uni_edit = intent.getStringExtra("unique_name");

        ggmap_edit_text = (TextView)findViewById(R.id.ggmap_edit_text);

        fragmentManager = getFragmentManager();
        mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.google_edit_map);
        mapFragment.getMapAsync(this);


        ggmap_edit_ok=(Button)findViewById(R.id.ggmap_edit_ok);
        ggmap_edit_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ggmap_edit.this,picture_management.class);
                intent.putExtra("unique_name",uni_edit);
                intent.putExtra("select_la",Double.toString(latitude_select));
                intent.putExtra("select_lo",Double.toString(longtitude_select));
                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        ggmap_edit_no=(Button)findViewById(R.id.ggmap_edit_no);
        ggmap_edit_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ggmap_edit.this, picture_management.class);
                intent.putExtra("unique_name",uni_edit);
                startActivity(intent);

            }
        });

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        final MarkerOptions markerOptions = new MarkerOptions();
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                googleMap.clear();
                markerOptions.title("클릭한 위치");

               latitude_select = latLng.latitude;
               longtitude_select = latLng.longitude;


                markerOptions.position(new LatLng(latitude_select,longtitude_select));
                googleMap.addMarker(markerOptions);
                ggmap_edit_text.setText(getAddress(latitude_select,longtitude_select));

            }

        });

        LatLng startingPoint = new LatLng(36.3519012,127.3014307);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startingPoint, 16));//좀더 가까이서 보고싶으면 숫자를 올리고 넓게 보고싶으면 내림, 적정선은 16

    }
    public String getAddress(double a,double l){
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addressList;
        try{
            if(geocoder !=null) {
                addressList = geocoder.getFromLocation(a, l, 1);
                if(addressList !=null && addressList.size()>0){
                    String aaa1 = addressList.get(0).getAddressLine(0).toString();
                    address_location1= aaa1;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return address_location1;
    }
}
