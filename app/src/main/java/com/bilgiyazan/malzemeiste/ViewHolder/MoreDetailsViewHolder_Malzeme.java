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
import com.bilgiyazan.malzemeiste.Activity.MalzemeDetailsActivity;
import com.bilgiyazan.malzemeiste.ModelAdapter.MoreDetailsAdapterModelMalzeme;
import com.bilgiyazan.malzemeiste.R;
import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.Locale;

import mehdi.sakout.fancybuttons.FancyButton;

public class MoreDetailsViewHolder_Malzeme extends ChildViewHolder {


    TextView detail_more_text;
    View view_separator_detail_more_item;
    RelativeLayout header;
    FancyButton header_title;
    TextView malzeme_detail_title;
    TextView malzeme_detail_ref;
    TextView malzeme_detail_fiyat;
    TextView more_item_details_malzeme_renk;
    ImageView malzeme_detail_call;
    ImageView malzeme_detail_email;
    ImageView malzeme_detail_share;
    ImageView malzeme_detail_image;
    SharedPreferences prefs;
    String Dollar_Rate;
    String Euro_Rate;
    RelativeLayout malzeme_more_detail_layout;

    public MoreDetailsViewHolder_Malzeme(@NonNull View itemView) {
        super(itemView);


        header = (RelativeLayout) itemView.findViewById(R.id.card_header);
        header_title = (FancyButton) itemView.findViewById(R.id.header_title_malzeme);
        malzeme_detail_title = (TextView) itemView.findViewById(R.id.more_item_details_malzeme_model);
        malzeme_detail_ref = (TextView) itemView.findViewById(R.id.more_item_details_malzeme_kod);
        malzeme_detail_fiyat = (TextView) itemView.findViewById(R.id.more_item_details_malzeme_fiyat);
        more_item_details_malzeme_renk = (TextView) itemView.findViewById(R.id.more_item_details_malzeme_renk);
        malzeme_detail_call = (ImageView) itemView.findViewById(R.id.more_item_details_malzeme_call);
        malzeme_detail_email = (ImageView) itemView.findViewById(R.id.more_item_details_malzeme_email);
        malzeme_detail_share = (ImageView) itemView.findViewById(R.id.more_item_details_malzeme_share);
        malzeme_detail_image = (ImageView) itemView.findViewById(R.id.malzeme_detail_image);
        malzeme_more_detail_layout = (RelativeLayout) itemView.findViewById(R.id.malzeme_more_detail_layout);
    }

    public void bind(@NonNull final MoreDetailsAdapterModelMalzeme moreDetailsAdapterModelmalzeme, final Activity activity, String Type, int size, int position) {
        prefs = activity.getSharedPreferences("malzemeiste", Context.MODE_PRIVATE);
        Euro_Rate = prefs.getString("Euro_Rate", "");
        Dollar_Rate = prefs.getString("Dollar_Rate", "");

        if (moreDetailsAdapterModelmalzeme.getType().equals("Header")) {
            header.setVisibility(View.VISIBLE);
            malzeme_more_detail_layout.setVisibility(View.GONE);
            header_title.setText(moreDetailsAdapterModelmalzeme.getModel());
        } else {

            more_item_details_malzeme_renk.setText(moreDetailsAdapterModelmalzeme.getrenk());

            malzeme_detail_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, moreDetailsAdapterModelmalzeme.getShare_Link());
                    sendIntent.setType("text/plain");
                    activity.startActivity(Intent.createChooser(sendIntent, "İle paylaş"));
                }
            });
            malzeme_more_detail_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, MalzemeDetailsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    intent.putExtra("Model", moreDetailsAdapterModelmalzeme.getModel());
                    intent.putExtra("Image", moreDetailsAdapterModelmalzeme.getURL());
                    intent.putExtra("Kod", moreDetailsAdapterModelmalzeme.getKod());
                    intent.putExtra("Marka", moreDetailsAdapterModelmalzeme.getMarka());
                    intent.putExtra("Fiyat", malzeme_detail_fiyat.getText().toString());
                    intent.putExtra("Renk", moreDetailsAdapterModelmalzeme.getRenk());
                    intent.putExtra("Miktari", moreDetailsAdapterModelmalzeme.getMiktari());
                    intent.putExtra("Share_Link", moreDetailsAdapterModelmalzeme.getShare_Link());
                    intent.putExtra("Rate", moreDetailsAdapterModelmalzeme.getRate());
                    activity.startActivity(intent);
                }
            });
            if (!moreDetailsAdapterModelmalzeme.getURL().equals("------"))
                Glide.with(activity).load(moreDetailsAdapterModelmalzeme.getURL()).into(malzeme_detail_image);

            header.setVisibility(View.GONE);
            malzeme_more_detail_layout.setVisibility(View.VISIBLE);

            malzeme_detail_title.setText(moreDetailsAdapterModelmalzeme.getModel());
            malzeme_detail_ref.setText(moreDetailsAdapterModelmalzeme.getKod());

         /*   if (moreDetailsAdapterModelmalzeme.getRate().equals("Dollar")&&!Dollar_Rate.isEmpty()) {
                double result = Double.valueOf(Dollar_Rate) * Double.valueOf(moreDetailsAdapterModelmalzeme.getFiyat().substring(0, moreDetailsAdapterModelmalzeme.getFiyat().length() - 1));
                Locale Turkish = new Locale("tr", "TR");
                NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                String string_result = String.valueOf(numberFormatDutch.format(result));
                malzeme_detail_fiyat.setText(string_result);
            } else if (moreDetailsAdapterModelmalzeme.getRate().equals("Euro")&&!Euro_Rate.isEmpty()) {
                double result = Double.valueOf(Euro_Rate) * Double.valueOf(moreDetailsAdapterModelmalzeme.getFiyat().substring(0, moreDetailsAdapterModelmalzeme.getFiyat().length() - 1));
                Locale Turkish = new Locale("tr", "TR");
                NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                String string_result = String.valueOf(numberFormatDutch.format(result));
                malzeme_detail_fiyat.setText(string_result);


            } else {*/
                malzeme_detail_fiyat.setText(moreDetailsAdapterModelmalzeme.getFiyat());

           // }

            malzeme_detail_call.setOnClickListener(new View.OnClickListener() {
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
            malzeme_detail_email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("message/rfc822");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Ürün kodu: " + moreDetailsAdapterModelmalzeme.getKod());

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


