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

public class YazilimDetailsActivity extends AppCompatActivity {
    private static final int ITEM_COUNT = 100;
    public static boolean GRID_LAYOUT = false;
    Toolbar toolbar;
    TextView Details_fiat_name;
    TextView Details_Marka_value;
    TextView Details_Kod_value;
    FancyButton siparis_button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yazilim_details_layout);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar_details);
        Details_fiat_name = (TextView) findViewById(R.id.Details_fiat_name);
        Details_Marka_value = (TextView) findViewById(R.id.Details_Marka_value);
        Details_Kod_value = (TextView) findViewById(R.id.Details_Kod_value);

        more_item_details_share = (ImageView) findViewById(R.id.more_item_details_share);

        header_details = (ImageView) findViewById(R.id.header_details);
        more_item_details_call = (ImageView) findViewById(R.id.more_item_details_call);
        more_item_details_email = (ImageView) findViewById(R.id.more_item_details_email);
        setSupportActionBar(toolbar);
        prefs = YazilimDetailsActivity.this.getSharedPreferences("malzemeiste", Context.MODE_PRIVATE);
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
                if (ActivityCompat.checkSelfPermission(YazilimDetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    ActivityCompat.requestPermissions(YazilimDetailsActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            1);
                } else {

                    YazilimDetailsActivity.this.startActivity(callIntent);

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

                    YazilimDetailsActivity.this.startActivity(Intent.createChooser(intent, "E-posta ile Via ..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(YazilimDetailsActivity.this, "Hiçbir e-posta istemcisi yüklü değil.", Toast.LENGTH_SHORT).show();
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
                YazilimDetailsActivity.this.startActivity(Intent.createChooser(sendIntent, "İle paylaş"));
            }
        });

        // Details_description_value.setText(getIntent().getStringExtra("Description"));
        if (getIntent().getStringExtra("Model").length() > 25) {

            collapsingToolbarLayout.setTitle(getIntent().getStringExtra("Model").substring(0, 24) + "...");
        }
        else{
            collapsingToolbarLayout.setTitle(getIntent().getStringExtra("Model"));
        }

        Details_fiat_name.setText(getIntent().getStringExtra("Fiyat"));


        Details_Marka_value.setText(getIntent().getStringExtra("Marka"));

        Details_Kod_value.setText(getIntent().getStringExtra("Kod"));


        if (!getIntent().getStringExtra("Image").equals("------"))
            Glide.with(YazilimDetailsActivity.this).load(getIntent().getStringExtra("Image")).into(header_details);

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