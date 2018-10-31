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
import com.bilgiyazan.malzemeiste.Activity.MakinalarDetailsActivity;
import com.bilgiyazan.malzemeiste.ModelAdapter.MoreDetailsAdapterModelMakinalar;
import com.bilgiyazan.malzemeiste.R;
import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.Locale;

public class MoreDetailsViewHolder_Makinalar extends ChildViewHolder {


    TextView makinalar_detail_title;
    TextView makinalar_detail_ref;
    TextView makinalar_detail_fiyat;
    ImageView makinalar_detail_call;
    ImageView makinalar_detail_email;
    ImageView makinalar_detail_share;
    ImageView makinalar_detail_image;
    SharedPreferences prefs;
    String Dollar_Rate;
    String Euro_Rate;
    RelativeLayout makinalar_more_detail_layout;
    TextView more_item_details_yedek_kdv;

    public MoreDetailsViewHolder_Makinalar(@NonNull View itemView) {
        super(itemView);

        makinalar_detail_title = (TextView) itemView.findViewById(R.id.more_item_details_makinalar_model);
        makinalar_detail_ref = (TextView) itemView.findViewById(R.id.more_item_details_makinalar_kod);
        makinalar_detail_fiyat = (TextView) itemView.findViewById(R.id.more_item_details_makinalar_fiyat);
        makinalar_detail_call = (ImageView) itemView.findViewById(R.id.more_item_details_makinalar_call);
        makinalar_detail_email = (ImageView) itemView.findViewById(R.id.more_item_details_makinalar_email);
        makinalar_detail_share = (ImageView) itemView.findViewById(R.id.more_item_details_makinalar_share);
        makinalar_detail_image = (ImageView) itemView.findViewById(R.id.makinalar_detail_image);
        makinalar_more_detail_layout = (RelativeLayout) itemView.findViewById(R.id.makinalar_more_detail_layout);

    }

    public void bind(@NonNull final MoreDetailsAdapterModelMakinalar moreDetailsAdapterModelMakinalar, final Activity activity, String Type, int size, int position) {

        makinalar_more_detail_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, MakinalarDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Model", moreDetailsAdapterModelMakinalar.getModel());
                intent.putExtra("Image", moreDetailsAdapterModelMakinalar.getURL());
                intent.putExtra("Kod", moreDetailsAdapterModelMakinalar.getKod());
                intent.putExtra("Description", moreDetailsAdapterModelMakinalar.getDescription());
                intent.putExtra("Marka", moreDetailsAdapterModelMakinalar.getMarka());
                intent.putExtra("Fiyat", makinalar_detail_fiyat.getText().toString());
                intent.putExtra("Share_Link", moreDetailsAdapterModelMakinalar.getShare_Link());
                intent.putExtra("Rate", moreDetailsAdapterModelMakinalar.getRate());
                activity.startActivity(intent);
            }
        });
        prefs = activity.getSharedPreferences("malzemeiste", Context.MODE_PRIVATE);
        Euro_Rate = prefs.getString("Euro_Rate", "");
        Dollar_Rate = prefs.getString("Dollar_Rate", "");

        if (!moreDetailsAdapterModelMakinalar.getURL().equals("------"))
            Glide.with(activity).load(moreDetailsAdapterModelMakinalar.getURL()).into(makinalar_detail_image);
        Log.e("URL", moreDetailsAdapterModelMakinalar.getURL());


        makinalar_detail_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, moreDetailsAdapterModelMakinalar.getShare_Link());
                sendIntent.setType("text/plain");
                activity.startActivity(Intent.createChooser(sendIntent, "İle paylaş"));
            }
        });
        makinalar_detail_title.setText(moreDetailsAdapterModelMakinalar.getModel());
        makinalar_detail_ref.setText(moreDetailsAdapterModelMakinalar.getKod());
        makinalar_detail_call.setOnClickListener(new View.OnClickListener() {
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
        makinalar_detail_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Ürün kodu: " + moreDetailsAdapterModelMakinalar.getKod());

                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@malzemeiste.net"});
                try {

                    activity.startActivity(Intent.createChooser(intent, "E-posta ile Via ..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(activity, "Hiçbir e-posta istemcisi yüklü değil.", Toast.LENGTH_SHORT).show();
                }
            }
        });

      /*  if (moreDetailsAdapterModelMakinalar.getRate().equals("Dollar")&&!Dollar_Rate.isEmpty()) {
            double result = Double.valueOf(Dollar_Rate) * Double.valueOf(moreDetailsAdapterModelMakinalar.getFiyat().substring(0, moreDetailsAdapterModelMakinalar.getFiyat().length() - 1));
            Locale Turkish = new Locale("tr", "TR");
            NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
            String string_result = String.valueOf(numberFormatDutch.format(result));
            makinalar_detail_fiyat.setText(string_result);
        } else if (moreDetailsAdapterModelMakinalar.getRate().equals("Euro")&&!Euro_Rate.isEmpty()) {
            double result = Double.valueOf(Euro_Rate) * Double.valueOf(moreDetailsAdapterModelMakinalar.getFiyat().substring(0, moreDetailsAdapterModelMakinalar.getFiyat().length() - 1));
            Locale Turkish = new Locale("tr", "TR");
            NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
            String string_result = String.valueOf(numberFormatDutch.format(result));
            makinalar_detail_fiyat.setText(string_result);

        } else {*/
            makinalar_detail_fiyat.setText(moreDetailsAdapterModelMakinalar.getFiyat());

        //}


    }


}


