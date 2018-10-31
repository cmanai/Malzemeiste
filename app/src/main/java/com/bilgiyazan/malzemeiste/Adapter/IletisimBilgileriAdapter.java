package com.bilgiyazan.malzemeiste.Adapter;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.os.ResultReceiver;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bilgiyazan.malzemeiste.R;
import com.bilgiyazan.malzemeiste.Utils.GeocodeAddressIntentService;

import java.util.List;

import permissions.dispatcher.NeedsPermission;


/**
 * Created by florentchampigny on 24/04/15.
 */
public class IletisimBilgileriAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    protected MapView mMapView;
    List<Object> contents;
    Bundle bundle;
    Activity activity;
    double longitude;
    double lattitude;
    String name_Company;
    AddressResultReceiver mResultReceiver;
    ImageView CallAction_Phone;
    ImageView email_send;
    RelativeLayout CallLayout_Phone;
    RelativeLayout mail_bilgi;
    ImageView facebook;
    ImageView twitter;
    ImageView g_plus;
    ImageView linked_in;
    private GoogleMap map;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 60000;  /* 60 secs */
    private long FASTEST_INTERVAL = 5000; /* 5 secs */

    public IletisimBilgileriAdapter(List<Object> contents, Bundle bundle, Activity activity) {
        this.contents = contents;
        this.bundle = bundle;
        this.activity = activity;
    }

    public static Intent getOpenFacebookIntent(Context context) {

        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/154857711247547"));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/154857711247547"));
        }
    }

    @Override
    public int getItemViewType(int position) {


        return TYPE_HEADER;


    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.iletisim_bilgileri_item_recyclerview, parent, false);
        return new RecyclerView.ViewHolder(view) {
        };

    }

/*    protected void loadMap(GoogleMap googleMap, Context context) {
        map = googleMap;
        if (googleMap != null) {
            // Map is ready
            map.getUiSettings().setScrollGesturesEnabled(false);
            map.getUiSettings().setZoomGesturesEnabled(false);
            map.getUiSettings().setZoomControlsEnabled(true);
            int fetchType;

            fetchType = GeocodeAddressIntentService.Constants.USE_ADDRESS_LOCATION;
            Intent intent = new Intent(context, GeocodeAddressIntentService.class);
            intent.putExtra(GeocodeAddressIntentService.Constants.RECEIVER, mResultReceiver);
            intent.putExtra(GeocodeAddressIntentService.Constants.FETCH_TYPE_EXTRA, fetchType);
            intent.putExtra(GeocodeAddressIntentService.Constants.LOCATION_NAME_DATA_EXTRA, name_Company);

            Log.e("SErvice", "Starting Service");
            context.startService(intent);


        } else {

        }
    }*/

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {


        name_Company = "Procolor";


        CallAction_Phone = (ImageView) holder.itemView.findViewById(R.id.call_tel);
        email_send = (ImageView) holder.itemView.findViewById(R.id.email_send);

        CallLayout_Phone = (RelativeLayout) holder.itemView.findViewById(R.id.call_bilgi);
        mail_bilgi = (RelativeLayout) holder.itemView.findViewById(R.id.mail_bilgi);


        CallAction_Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "0 (212) 438 48 29"));
                if (ActivityCompat.checkSelfPermission(holder.itemView.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    ActivityCompat.requestPermissions(activity,
                            new String[]{Manifest.permission.CALL_PHONE},
                            1);
                } else {

                    holder.itemView.getContext().startActivity(callIntent);

                }
            }
        });

        email_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("message/rfc822");

                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@malzemeiste.net"});
                try {

                    activity.startActivity(Intent.createChooser(intent, "E-posta ile Via ..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(activity, "Hiçbir e-posta istemcisi yüklü değil.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        CallLayout_Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "0 (212) 438 48 29"));
                if (ActivityCompat.checkSelfPermission(holder.itemView.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    ActivityCompat.requestPermissions(activity,
                            new String[]{Manifest.permission.CALL_PHONE},
                            1);
                } else {

                    holder.itemView.getContext().startActivity(callIntent);

                }
            }
        });


        CallLayout_Phone.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) holder.itemView.getContext().getSystemService(holder.itemView.getContext().CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("text", "0 (212) 438 48 29");
                clipboard.setPrimaryClip(clip);
                Vibrator vibe = (Vibrator) holder.itemView.getContext().getSystemService(Context.VIBRATOR_SERVICE);

                vibe.vibrate(50);
                view.clearFocus();
                view.clearAnimation();
                view.setPressed(false);
                Toast.makeText(activity, "Kopyalanan Telefon Numarası", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        mail_bilgi.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) holder.itemView.getContext().getSystemService(holder.itemView.getContext().CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("text", "info@malzemeiste.net");
                clipboard.setPrimaryClip(clip);
                Vibrator vibe = (Vibrator) holder.itemView.getContext().getSystemService(Context.VIBRATOR_SERVICE);

                vibe.vibrate(50);
                view.clearFocus();
                view.clearAnimation();
                view.setPressed(false);
                Toast.makeText(activity, "Kopyalanan Email", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mail_bilgi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("message/rfc822");

                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@malzemeiste.net"});
                try {

                    activity.startActivity(Intent.createChooser(intent, "E-posta ile Via ..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(activity, "Hiçbir e-posta istemcisi yüklü değil.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mMapView = (MapView) holder.itemView.findViewById(R.id.map_address);
        mResultReceiver = new AddressResultReceiver(null);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMapView.onCreate(bundle);

                mMapView.getMapAsync(new OnMapReadyCallback() {

                    @Override
                    public void onMapReady(GoogleMap googleMap) {

                        if (googleMap != null) {
                            googleMap.getUiSettings().setScrollGesturesEnabled(false);
                            googleMap.getUiSettings().setZoomGesturesEnabled(false);
                            googleMap.getUiSettings().setZoomControlsEnabled(true);
                            //41.038022, 28.882386
                            LatLng coordinates = new LatLng(41.037731, 28.882386);
                            googleMap.addMarker(new MarkerOptions().position(coordinates).title(name_Company));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15));
                            //   loadMap(googleMap,activity);
                        }


                        mMapView.onResume();


                    }
                });
            }
        }, 100);


    }

    @SuppressWarnings("all")
    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void getMyLocation() {
        if (map != null) {
            // Now that map has loaded, let's get our location!
            map.setMyLocationEnabled(true);
            mGoogleApiClient = new GoogleApiClient.Builder(activity)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();
            connectClient();

        }
    }

    protected void connectClient() {
        // Connect the client.
        if (isGooglePlayServicesAvailable() && mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    private boolean isGooglePlayServicesAvailable() {
        // Check that Google Play services is available
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            // In debug mode, log the status
            Log.d("Location Updates", "Google Play services is available.");
            return true;
        } else {
            // Get the error dialog from Google Play services


            return false;
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location != null) {
            Toast.makeText(activity, "GPS location was found!", Toast.LENGTH_SHORT).show();
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
            map.animateCamera(cameraUpdate);
        } else {
            Toast.makeText(activity, "Current location was null, enable GPS on emulator!", Toast.LENGTH_SHORT).show();
        }
        startLocationUpdates();
    }

    protected void startLocationUpdates() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (i == CAUSE_SERVICE_DISCONNECTED) {
            Toast.makeText(activity, "Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();
        } else if (i == CAUSE_NETWORK_LOST) {
            Toast.makeText(activity, "Network lost. Please re-connect.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
/*
         * Google Play services can resolve some errors it detects. If the error
		 * has a resolution, try sending an Intent to start a Google Play
		 * services activity that can resolve error.
		 */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(activity,
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
				 * PendingIntent
				 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            Toast.makeText(activity,
                    "Sorry. Location services not available to you", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();

    }

    // Define a DialogFragment that displays the error dialog
    public static class ErrorDialogFragment extends DialogFragment {

        // Global field to contain the error dialog
        private Dialog mDialog;

        // Default constructor. Sets the dialog field to null
        public ErrorDialogFragment() {
            super();
            mDialog = null;
        }

        // Set the dialog to display
        public void setDialog(Dialog dialog) {
            mDialog = dialog;
        }

        // Return a Dialog to the DialogFragment.
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return mDialog;
        }
    }

    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, final Bundle resultData) {
            if (resultCode == GeocodeAddressIntentService.Constants.SUCCESS_RESULT) {
                final Address address = resultData.getParcelable(GeocodeAddressIntentService.Constants.RESULT_ADDRESS);

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lattitude = address.getLatitude();

                        longitude = address.getLongitude();
                        Log.e("lattitude", String.valueOf(lattitude));
                        Log.e("longitude", String.valueOf(longitude));
                        Log.e("postal code", address.getPostalCode());

                        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
                        CameraUpdate center =
                                CameraUpdateFactory.newLatLng(new LatLng(lattitude, longitude));

                        map.moveCamera(center);
                        map.animateCamera(zoom);
                        map.getUiSettings().setMapToolbarEnabled(true);
                        map.addMarker(new MarkerOptions()

                                .position(new LatLng(lattitude, longitude))
                                .title(name_Company));


                        //info_map.setText(address.getAddressLine(0) + ", " + address.getAddressLine(1) + ", " + address.getAddressLine(2) + "/" + address.getAddressLine(3) + ".");

                        mMapView.onResume();
                    }
                });
            } else {

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                    }
                });
            }
        }
    }
}