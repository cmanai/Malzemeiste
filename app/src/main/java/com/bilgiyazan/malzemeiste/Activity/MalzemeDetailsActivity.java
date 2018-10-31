package com.bilgiyazan.malzemeiste.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bilgiyazan.malzemeiste.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;

public class MalzemeDetailsActivity extends AppCompatActivity {
    private static final int ITEM_COUNT = 100;
    public static boolean GRID_LAYOUT = false;
    Toolbar toolbar;
    TextView Details_fiat_name;
    TextView Details_Marka_value;
    TextView Details_Kod_value;
    TextView Details_Miktari_value;
    TextView Details_Renk_value;
    ImageView more_item_details_call;
    ImageView more_item_details_email;
    ImageView header_details;
    SharedPreferences prefs;
    String Dollar_Rate;
    String Euro_Rate;
    ImageView more_item_details_share;
    CollapsingToolbarLayout collapsingToolbarLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<Object> mContentItems = new ArrayList<>();
    FancyButton siparis_button;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.malzeme_details_layout);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar_details);
        Details_fiat_name = (TextView) findViewById(R.id.Details_fiat_name);
        Details_Marka_value = (TextView) findViewById(R.id.Details_Marka_value);
        Details_Kod_value = (TextView) findViewById(R.id.Details_Kod_value);
        Details_Renk_value = (TextView) findViewById(R.id.Details_Renk_value);
        Details_Miktari_value = (TextView) findViewById(R.id.Details_Miktari_value);
        header_details = (ImageView) findViewById(R.id.header_details);
        more_item_details_call = (ImageView) findViewById(R.id.more_item_details_call);
        more_item_details_email = (ImageView) findViewById(R.id.more_item_details_email);
        more_item_details_share = (ImageView) findViewById(R.id.more_item_details_share);
        siparis_button = (FancyButton) findViewById(R.id.siparis_button);
//        Details_Link_value.setText(getIntent().getStringExtra("Share_Link"));
        siparis_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(getIntent().getStringExtra("Share_Link")));
                startActivity(i);
            }
        });
        setSupportActionBar(toolbar);
        prefs = MalzemeDetailsActivity.this.getSharedPreferences("malzemeiste", Context.MODE_PRIVATE);

        Euro_Rate = prefs.getString("Euro_Rate", "");
        Dollar_Rate = prefs.getString("Dollar_Rate", "");
        if (toolbar != null) {

            setSupportActionBar(toolbar);

            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(true);

            }
        }
        toolbar.setTitle(" ");


        more_item_details_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "0(212) 438 48 29"));
                if (ActivityCompat.checkSelfPermission(MalzemeDetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    ActivityCompat.requestPermissions(MalzemeDetailsActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            1);
                } else {

                    MalzemeDetailsActivity.this.startActivity(callIntent);

                }
            }
        });
        more_item_details_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Ürün kodu: " + getIntent().getStringExtra("Kod"));

                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@malzemeiste.net"});
                try {

                    MalzemeDetailsActivity.this.startActivity(Intent.createChooser(intent, "E-posta ile Via ..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(MalzemeDetailsActivity.this, "Hiçbir e-posta istemcisi yüklü değil.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        more_item_details_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, getIntent().getStringExtra("Share_Link"));
                sendIntent.setType("text/plain");
                MalzemeDetailsActivity.this.startActivity(Intent.createChooser(sendIntent, "İle paylaş"));
            }
        });

        // Details_description_value.setText(getIntent().getStringExtra("Description"));
        if (getIntent().getStringExtra("Model").length() > 25) {

            collapsingToolbarLayout.setTitle(getIntent().getStringExtra("Model").substring(0, 24) + "...");
        } else {
            collapsingToolbarLayout.setTitle(getIntent().getStringExtra("Model"));
        }        Details_Renk_value.setText(getIntent().getStringExtra("Renk"));
        Details_Miktari_value.setText(getIntent().getStringExtra("Miktari"));


        Details_fiat_name.setText(getIntent().getStringExtra("Fiyat"));


        Details_Marka_value.setText(getIntent().getStringExtra("Marka"));
        Details_Kod_value.setText(getIntent().getStringExtra("Kod"));


        if (!getIntent().getStringExtra("Image").equals("------"))
            Glide.with(MalzemeDetailsActivity.this).load(getIntent().getStringExtra("Image")).into(header_details);

        RecyclerView.LayoutManager layoutManager;

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle the click on the back arrow click
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity

        super.onBackPressed();

    }

}