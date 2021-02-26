package com.activeitzone.activeecommercecms.Presentation.ui.activities.impl.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.activeitzone.activeecommercecms.Models.ShippingAddress;
import com.activeitzone.activeecommercecms.Models.User;
import com.activeitzone.activeecommercecms.Network.response.AuthResponse;
import com.activeitzone.activeecommercecms.Network.response.ProfileInfoUpdateResponse;
import com.activeitzone.activeecommercecms.Network.response.ShippingInfoResponse;
import com.activeitzone.activeecommercecms.Presentation.presenters.AccountInfoPresenter;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.AccountInfoView;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.impl.AccountInfoActivity;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.impl.ShippingActivity;
import com.activeitzone.activeecommercecms.R;
import com.activeitzone.activeecommercecms.Threading.MainThreadImpl;
import com.activeitzone.activeecommercecms.Utils.ConstantMap;
import com.activeitzone.activeecommercecms.Utils.FetchAddressIntentService;
import com.activeitzone.activeecommercecms.Utils.Location;
import com.activeitzone.activeecommercecms.Utils.LocationListener;
import com.activeitzone.activeecommercecms.Utils.UserPrefs;
import com.activeitzone.activeecommercecms.domain.executor.impl.ThreadExecutor;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Objects;

public class AddresssPicupActivity extends AppCompatActivity implements OnMapReadyCallback, AccountInfoView {

    private GoogleMap mMap;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private AddressResultReceiver addressResultResultReceiver;
    Marker marker;
    Location location;
    public TextView addrTitle, saveAddressBtn, saveAddress;
    TextInputEditText addInputEditText, userNameEditText, flatNoEditText, phoneEditText;
    LinearLayout saveAddressLayout;
    ImageView gpsPinpoint;
    private AuthResponse authResponse;
    JsonObject jsonObject = new JsonObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sliding_up_panel_map);

        Places.initialize(this, getResources().getString(R.string.google_maps_key));
        Places.createClient(this);

        addrTitle = findViewById(R.id.address_places);
        addInputEditText = findViewById(R.id.addEditText);
        userNameEditText = findViewById(R.id.usernameEditText);
        flatNoEditText = findViewById(R.id.flatEditText);
        saveAddressBtn = findViewById(R.id.save_add_txt);
        gpsPinpoint = findViewById(R.id.gps_pinpoint);
        saveAddressLayout = findViewById(R.id.save_address);
        phoneEditText = findViewById(R.id.phoneEditText);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        

        setTextChangeListener(userNameEditText);
        setTextChangeListener(flatNoEditText);
        setTextChangeListener(addInputEditText);
        setTextChangeListener(phoneEditText);

        authResponse = new UserPrefs(this).getAuthPreferenceObjectJson("auth_response");


        location = new  Location(AddresssPicupActivity.this, new LocationListener() {
            @Override
            public void locationResponse(LocationResult locationResult) {
                mMap.clear();
                LatLng currLoc = new LatLng(locationResult.getLastLocation().getLatitude(),
                        locationResult.getLastLocation().getLongitude());

                marker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.
                        fromBitmap(bitmapScaledDescriptorFromVector())).position(currLoc));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currLoc, 14f));

                startIntentService(currLoc);
                location.stopUpdateLocation();
            }
        });

        saveAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder showConfirmationDialog = new AlertDialog.Builder(AddresssPicupActivity.this);
                showConfirmationDialog.setTitle("Confirmation Message");
                showConfirmationDialog.setMessage("Do you want confirm save the address ?");
                showConfirmationDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Objects.requireNonNull(addInputEditText.getText()).length() > 0 &&
                                Objects.requireNonNull(userNameEditText.getText()).length() > 0 &&
                                Objects.requireNonNull(flatNoEditText.getText()).length() > 0) {
                            jsonObject.addProperty("phone", Objects.requireNonNull(phoneEditText.getText()).toString());
                            if(authResponse != null && authResponse.getUser() != null){

                                new AccountInfoPresenter(ThreadExecutor.getInstance(),
                                        MainThreadImpl.getInstance(),
                                        AddresssPicupActivity.this).addNewShippingRequest(jsonObject, authResponse.getAccessToken());
                            }
                        }
                    }
                });

                showConfirmationDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                showConfirmationDialog.show();

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        enableLocation();
        mMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int i) {
                mMap.clear();
                gpsPinpoint.setVisibility(View.VISIBLE);
            }
        });

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                LatLng midLatLng = mMap.getCameraPosition().target;
                if (marker != null){
                    marker.setPosition(midLatLng);
                    marker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.
                            fromBitmap(bitmapScaledDescriptorFromVector())).position(midLatLng));

                    startIntentService(midLatLng);

                }
                location.stopUpdateLocation();
                gpsPinpoint.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onProfileInfoUpdated(ProfileInfoUpdateResponse profileInfoUpdateResponse) {

    }

    @Override
    public void setShippingAddresses(List<ShippingAddress> shippingAddresses) {

    }

    @Override
    public void onShippingInfoCreated(ShippingInfoResponse shippingInfoResponse) {
        Intent intent= new Intent();
        intent.putExtra("result", "true");
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void onShippingInfoDeleted(ShippingInfoResponse shippingInfoResponse) {

    }

    @Override
    public void setUpdatedUserInfo(User user) {

    }

    class AddressResultReceiver extends ResultReceiver{

        /**
         * Create a new ResultReceive to receive results.  Your
         * {@link #onReceiveResult} method will be called from the thread running
         * <var>handler</var> if given, or from an arbitrary thread if null.
         *
         * @param handler
         */
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            addrTitle.setText(resultData.getString(ConstantMap.RESULT_DATA_KEY));
            addInputEditText.setText(resultData.getString(ConstantMap.RESULT_DATA_KEY));
            jsonObject.addProperty("address", resultData.getString(ConstantMap.RESULT_DATA_KEY));
            jsonObject.addProperty("city", resultData.getString("city"));
            jsonObject.addProperty("country", resultData.getString("country"));
            jsonObject.addProperty("postal_code", resultData.getString("postal_code"));
            jsonObject.addProperty("user_id", authResponse.getUser().getId());
        }
    }

    private Bitmap bitmapScaledDescriptorFromVector() {
        int height = 80;
        int width = 80;
        Bitmap imageBitmap = BitmapFactory.decodeResource(
                getResources(), getResources().getIdentifier(
                        "custommarker",
                        "drawable",
                        getPackageName()
                )
        );
        return Bitmap.createScaledBitmap(imageBitmap, width, height, false);
    }

    private void startIntentService(LatLng currLoc) {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        addressResultResultReceiver = new AddressResultReceiver(new Handler());
        intent.putExtra(ConstantMap.RECEIVER, addressResultResultReceiver);
        intent.putExtra(ConstantMap.LOCATION_DATA_EXTRA, currLoc);

        startService(intent);
    }


    public void enableLocation(){
        if (location.validatePermissionLocation()){
            mMap.isMyLocationEnabled();
            location = new Location(this, new LocationListener(){

                @Override
                public void locationResponse(LocationResult locationResult) {
                    LatLng currLoc = new LatLng(locationResult.getLastLocation().getLatitude(),
                            locationResult.getLastLocation().getLongitude());

                    marker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.
                            fromBitmap(bitmapScaledDescriptorFromVector())).position(currLoc));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currLoc, 14f));

                    startIntentService(currLoc);
                    location.stopUpdateLocation();
                }
            });
        }else {
            location.requestPermissions();
        }
    }

    public void setTextChangeListener(TextInputEditText idText){
        idText.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0 &&
                        Objects.requireNonNull(userNameEditText.getText()).length() > 0 &&
                        Objects.requireNonNull(flatNoEditText.getText()).length() > 0 &&
                        Objects.requireNonNull(phoneEditText.getText()).length() > 0){
                    saveAddressLayout.setBackgroundColor(ContextCompat.getColor(AddresssPicupActivity.this, R.color.common_color));
                    saveAddressBtn.setTextColor(Color.BLACK);
                }else {
                    saveAddressLayout.setBackgroundColor(Color.parseColor("#7F807F"));
                    saveAddressBtn.setTextColor(Color.parseColor("#40000000"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}