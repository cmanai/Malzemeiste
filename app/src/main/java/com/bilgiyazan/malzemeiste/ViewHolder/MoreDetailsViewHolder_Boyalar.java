package com.bilgiyazan.malzemeiste.ViewHolder;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.bilgiyazan.malzemeiste.Activity.BoyalarDetailsActivity;
import com.bilgiyazan.malzemeiste.ModelAdapter.MoreDetailsAdapterModelBoyalar;
import com.bilgiyazan.malzemeiste.R;
import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.Locale;

import mehdi.sakout.fancybuttons.FancyButton;

public class MoreDetailsViewHolder_Boyalar extends ChildViewHolder {


    TextView detail_more_text;
    View view_separator_detail_more_item;
    RelativeLayout header;
    FancyButton header_title;
    TextView boyalar_detail_title;
    TextView boyalar_detail_ref;
    TextView boyalar_detail_fiyat;
    TextView more_item_details_boyalar_ambalaj;
    ImageView boyalar_detail_call;
    ImageView boyalar_detail_email;
    ImageView boyalar_detail_share;
    ImageView boyalar_detail_image;
    SharedPreferences prefs;
    String Dollar_Rate;
    String Euro_Rate;
    RelativeLayout boyalar_more_detail_layout;

    public MoreDetailsViewHolder_Boyalar(@NonNull View itemView) {
        super(itemView);


        header = (RelativeLayout) itemView.findViewById(R.id.card_header);
        header_title = (FancyButton) itemView.findViewById(R.id.header_title_boyalar);
        boyalar_detail_title = (TextView) itemView.findViewById(R.id.more_item_details_boyalar_model);
        boyalar_detail_ref = (TextView) itemView.findViewById(R.id.more_item_details_boyalar_kod);
        boyalar_detail_fiyat = (TextView) itemView.findViewById(R.id.more_item_details_boyalar_fiyat);
        more_item_details_boyalar_ambalaj = (TextView) itemView.findViewById(R.id.more_item_details_boyalar_ambalaj);
        boyalar_detail_call = (ImageView) itemView.findViewById(R.id.more_item_details_boyalar_call);
        boyalar_detail_email = (ImageView) itemView.findViewById(R.id.more_item_details_boyalar_email);
        boyalar_detail_share = (ImageView) itemView.findViewById(R.id.more_item_details_boyalar_share);
        boyalar_detail_image = (ImageView) itemView.findViewById(R.id.boyalar_detail_image);
        boyalar_more_detail_layout = (RelativeLayout) itemView.findViewById(R.id.boyalar_more_detail_layout);
    }

    public void bind(@NonNull final MoreDetailsAdapterModelBoyalar moreDetailsAdapterModelBoyalar, final Activity activity, String Type, int size, int position) {
        prefs = activity.getSharedPreferences("malzemeiste", Context.MODE_PRIVATE);
        Euro_Rate = prefs.getString("Euro_Rate", "");
        Dollar_Rate = prefs.getString("Dollar_Rate", "");

        if (moreDetailsAdapterModelBoyalar.getType().equals("Header")) {
            header.setVisibility(View.VISIBLE);
            boyalar_more_detail_layout.setVisibility(View.GONE);
            header_title.setText(moreDetailsAdapterModelBoyalar.getModel());
        } else {

            more_item_details_boyalar_ambalaj.setText(moreDetailsAdapterModelBoyalar.getAmbalaj_sekli());

            boyalar_detail_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, moreDetailsAdapterModelBoyalar.getShare_Link());
                    sendIntent.setType("text/plain");
                    activity.startActivity(Intent.createChooser(sendIntent, "İle paylaş"));
                }
            });
            boyalar_more_detail_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, BoyalarDetailsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("Model", moreDetailsAdapterModelBoyalar.getModel());
                    intent.putExtra("Image", moreDetailsAdapterModelBoyalar.getURL());
                    intent.putExtra("Kod", moreDetailsAdapterModelBoyalar.getKod());
                    intent.putExtra("Renk", moreDetailsAdapterModelBoyalar.getRenk());
                    intent.putExtra("Miktari", moreDetailsAdapterModelBoyalar.getMiktari());
                    intent.putExtra("Ambalaj", moreDetailsAdapterModelBoyalar.getAmbalaj_sekli());

                    intent.putExtra("Marka", moreDetailsAdapterModelBoyalar.getMarka());
                    intent.putExtra("Fiyat", boyalar_detail_fiyat.getText().toString());
                    intent.putExtra("Share_Link", moreDetailsAdapterModelBoyalar.getShare_Link());
                    intent.putExtra("Rate", moreDetailsAdapterModelBoyalar.getRate());
                    activity.startActivity(intent);
                }
            });
            if (!moreDetailsAdapterModelBoyalar.getURL().equals("------"))
                Glide.with(activity).load(moreDetailsAdapterModelBoyalar.getURL()).into(boyalar_detail_image);

            header.setVisibility(View.GONE);
            boyalar_more_detail_layout.setVisibility(View.VISIBLE);

            boyalar_detail_title.setText(moreDetailsAdapterModelBoyalar.getModel());
            boyalar_detail_ref.setText(moreDetailsAdapterModelBoyalar.getKod());

          /*  if (moreDetailsAdapterModelBoyalar.getRate().equals("Dollar")&&!Dollar_Rate.isEmpty()) {
                double result = Double.valueOf(Dollar_Rate) * Double.valueOf(moreDetailsAdapterModelBoyalar.getFiyat().substring(0, moreDetailsAdapterModelBoyalar.getFiyat().length() - 1));
                Locale Turkish = new Locale("tr", "TR");
                NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                String string_result = String.valueOf(numberFormatDutch.format(result));
                boyalar_detail_fiyat.setText(string_result);
            } else if (moreDetailsAdapterModelBoyalar.getRate().equals("Euro")&&!Euro_Rate.isEmpty()) {
                double result = Double.valueOf(Euro_Rate) * Double.valueOf(moreDetailsAdapterModelBoyalar.getFiyat().substring(0, moreDetailsAdapterModelBoyalar.getFiyat().length() - 1));
                Locale Turkish = new Locale("tr", "TR");
                NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                String string_result = String.valueOf(numberFormatDutch.format(result));
                boyalar_detail_fiyat.setText(string_result);


            } else {*/
                boyalar_detail_fiyat.setText(moreDetailsAdapterModelBoyalar.getFiyat());

            //}

            boyalar_detail_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + "0(212) 438 48 29"));
                    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                        // Should we show an explanation?
                        ActivityCompat.requestPermissions(activity,
                                new String[]{Manifest.permission.CALL_PHONE},
                                1);
                    } else {

                        activity.startActivity(callIntent);

                    }
                }
            });
            boyalar_detail_email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                    intent.setType("message/rfc822");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Ürün kodu: " + moreDetailsAdapterModelBoyalar.getKod());

                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@malzemeiste.net"});
                    try {

                        activity.startActivity(Intent.createChooser(intent, "E-posta ile Via ..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(activity, "Hiçbir e-posta istemcisi yüklü değil.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

        /*    if(position==size-1){
                view_separator_detail_more_item.setVisibility(View.GONE);

            }*/
//            detail_more_text.setText(moreDetailsAdapterModel.getInfo());


    }


}


