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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.bilgiyazan.malzemeiste.Activity.YedekDetailsActivity;
import com.bilgiyazan.malzemeiste.ModelAdapter.MoreDetailsAdapterModelYedek;
import com.bilgiyazan.malzemeiste.R;
import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.Locale;

public class MoreDetailsViewHolder_Yedek extends ChildViewHolder {


    TextView yedek_detail_title;
    TextView yedek_detail_ref;
    TextView yedek_detail_fiyat;
    TextView more_item_details_yedek_kdv;
    TextView more_item_details_yedek_uyumlu;
    ImageView yedek_detail_call;
    ImageView yedek_detail_email;
    ImageView yedek_detail_share;
    ImageView yedek_detail_image;
    SharedPreferences prefs;
    String Dollar_Rate;
    String Euro_Rate;
    RelativeLayout yedek_more_detail_layout;

    public MoreDetailsViewHolder_Yedek(@NonNull View itemView) {
        super(itemView);

        yedek_detail_title = (TextView) itemView.findViewById(R.id.more_item_details_yedek_model);
        yedek_detail_ref = (TextView) itemView.findViewById(R.id.more_item_details_yedek_kod);
        yedek_detail_fiyat = (TextView) itemView.findViewById(R.id.more_item_details_yedek_fiyat);
        more_item_details_yedek_kdv = (TextView) itemView.findViewById(R.id.more_item_details_yedek_kdv);
        more_item_details_yedek_uyumlu = (TextView) itemView.findViewById(R.id.more_item_details_yedek_uyumlu);
        yedek_detail_call = (ImageView) itemView.findViewById(R.id.more_item_details_yedek_call);
        yedek_detail_email = (ImageView) itemView.findViewById(R.id.more_item_details_yedek_email);
        yedek_detail_share = (ImageView) itemView.findViewById(R.id.more_item_details_yedek_share);
        yedek_detail_image = (ImageView) itemView.findViewById(R.id.yedek_detail_image);
        yedek_more_detail_layout = (RelativeLayout) itemView.findViewById(R.id.yedek_more_detail_layout);


    }

    public void bind(@NonNull final MoreDetailsAdapterModelYedek moreDetailsAdapterModelyedek, final Activity activity, String Type, int size, int position) {
        prefs = activity.getSharedPreferences("malzemeiste", Context.MODE_PRIVATE);
        Euro_Rate = prefs.getString("Euro_Rate", "");
        Dollar_Rate = prefs.getString("Dollar_Rate", "");

        if (!moreDetailsAdapterModelyedek.getURL().equals("------"))
            Glide.with(activity).load(moreDetailsAdapterModelyedek.getURL()).into(yedek_detail_image);
        Log.e("URL", moreDetailsAdapterModelyedek.getURL());
        yedek_detail_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, moreDetailsAdapterModelyedek.getShare_Link());
                sendIntent.setType("text/plain");
                activity.startActivity(Intent.createChooser(sendIntent, "İle paylaş"));
            }
        });
        yedek_more_detail_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, YedekDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Model", moreDetailsAdapterModelyedek.getModel());
                intent.putExtra("Image", moreDetailsAdapterModelyedek.getURL());
                intent.putExtra("Kod", moreDetailsAdapterModelyedek.getKod());

                intent.putExtra("Uyumlu", moreDetailsAdapterModelyedek.getUyumlu());

                intent.putExtra("Marka", moreDetailsAdapterModelyedek.getMarka());
                intent.putExtra("Fiyat", yedek_detail_fiyat.getText().toString());
                intent.putExtra("Share_Link", moreDetailsAdapterModelyedek.getShare_Link());
                intent.putExtra("Rate", moreDetailsAdapterModelyedek.getRate());
                activity.startActivity(intent);
            }
        });
        yedek_detail_title.setText(moreDetailsAdapterModelyedek.getModel());
        more_item_details_yedek_uyumlu.setText(moreDetailsAdapterModelyedek.getUyumlu());
        yedek_detail_ref.setText(moreDetailsAdapterModelyedek.getKod());
        yedek_detail_call.setOnClickListener(new View.OnClickListener() {
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
        yedek_detail_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Ürün kodu: " + moreDetailsAdapterModelyedek.getKod());

                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@malzemeiste.net"});
                try {

                    activity.startActivity(Intent.createChooser(intent, "E-posta ile Via ..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(activity, "Hiçbir e-posta istemcisi yüklü değil.", Toast.LENGTH_SHORT).show();
                }
            }
        });

     /*   if (moreDetailsAdapterModelyedek.getRate().equals("Dollar")&&!Dollar_Rate.isEmpty()) {
            double result = Double.valueOf(Dollar_Rate) * Double.valueOf(moreDetailsAdapterModelyedek.getFiyat().substring(0, moreDetailsAdapterModelyedek.getFiyat().length() - 1));
            Locale Turkish = new Locale("tr", "TR");
            NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
            String string_result = String.valueOf(numberFormatDutch.format(result));
            yedek_detail_fiyat.setText(string_result);
        } else if (moreDetailsAdapterModelyedek.getRate().equals("Euro")&&!Euro_Rate.isEmpty()) {
            double result = Double.valueOf(Euro_Rate) * Double.valueOf(moreDetailsAdapterModelyedek.getFiyat().substring(0, moreDetailsAdapterModelyedek.getFiyat().length() - 1));
            Locale Turkish = new Locale("tr", "TR");
            NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
            String string_result = String.valueOf(numberFormatDutch.format(result));
            yedek_detail_fiyat.setText(string_result);

        } else {*/
            yedek_detail_fiyat.setText(moreDetailsAdapterModelyedek.getFiyat());

        //}


    }


}


