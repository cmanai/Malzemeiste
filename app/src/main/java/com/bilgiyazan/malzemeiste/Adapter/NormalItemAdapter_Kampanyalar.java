package com.bilgiyazan.malzemeiste.Adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bilgiyazan.malzemeiste.Activity.KampanyalarDetailsActivity;
import com.bilgiyazan.malzemeiste.Model.Kampanyalar;
import com.bilgiyazan.malzemeiste.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class NormalItemAdapter_Kampanyalar extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    List<Kampanyalar> kampanyalars;
    int Size;
    SharedPreferences prefs;
    String Dollar_Rate;
    String Euro_Rate;
    private int lastPosition = -1;
    private Activity activity;

    public NormalItemAdapter_Kampanyalar(List<Kampanyalar> kampanyalars, Activity activity) {
        prefs = activity.getSharedPreferences("malzemeiste", Context.MODE_PRIVATE);
        Euro_Rate = prefs.getString("Euro_Rate", "");
        Dollar_Rate = prefs.getString("Dollar_Rate", "");

        if (kampanyalars != null) {

            this.kampanyalars = kampanyalars;

            Size = kampanyalars.size();

        }

        this.activity = activity;
    }

    @Override
    public int getItemViewType(int position) {


        return TYPE_HEADER;


    }

    @Override
    public int getItemCount() {
        return Size;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.normal_item_details_kampanyalar, parent, false);

        return new RecyclerView.ViewHolder(view) {
        };

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        CardView cardView = (CardView) holder.itemView.findViewById(R.id.normal_item_details_kampanyalar_cardview);
        RelativeLayout kampanyalar_layout = (RelativeLayout) holder.itemView.findViewById(R.id.normal_item_details_kampanyalar_layout);
        final TextView kampanyalar_fiyat = (TextView) holder.itemView.findViewById(R.id.normal_item_details_kampanyalar_fiyat);
        ImageView kampanyalar_image = (ImageView) holder.itemView.findViewById(R.id.normal_item_details_kampanyalar_image);
        TextView kampanyalar_title = (TextView) holder.itemView.findViewById(R.id.normal_item_details_kampanyalar_title);
        TextView kampanyalar_ref = (TextView) holder.itemView.findViewById(R.id.normal_item_details_kampanyalar_ref);


        ImageView kampanyalar_detail_call;
        ImageView kampanyalar_detail_email;
        ImageView kampanyalar_detail_share;
        kampanyalar_detail_call = (ImageView) holder.itemView.findViewById(R.id.normal_item_details_kampanyalar_call);
        kampanyalar_detail_email = (ImageView) holder.itemView.findViewById(R.id.normal_item_details_kampanyalar_email);
        kampanyalar_detail_share = (ImageView) holder.itemView.findViewById(R.id.normal_item_details_kampanyalar_share);
        if (kampanyalars != null) {
            //
            kampanyalar_detail_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, kampanyalars.get(position).getShare_Link());
                    sendIntent.setType("text/plain");
                    activity.startActivity(Intent.createChooser(sendIntent, "İle paylaş"));
                }
            });
            kampanyalar_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, KampanyalarDetailsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("Model", kampanyalars.get(position).getModel());
                    intent.putExtra("Image", kampanyalars.get(position).getURL());
                    intent.putExtra("Kod", kampanyalars.get(position).getKod());
                    intent.putExtra("Fiyat", kampanyalar_fiyat.getText().toString());
                    intent.putExtra("Share_Link", kampanyalars.get(position).getShare_Link());
                    activity.startActivity(intent);
                }
            });
            if (!kampanyalars.get(position).getURL().equals("------"))
                Glide.with(activity).load(kampanyalars.get(position).getURL()).into(kampanyalar_image);
            kampanyalar_title.setText(kampanyalars.get(position).getModel());
            kampanyalar_ref.setText(kampanyalars.get(position).getKod());
       /*     if (corelDraws.get(position).getRate().equals("Dollar")&&!Dollar_Rate.isEmpty()) {
                double result = Double.valueOf(Dollar_Rate) * Double.valueOf(corelDraws.get(position).getFiyat().substring(0, corelDraws.get(position).getFiyat().length() - 1));
                Locale Turkish = new Locale("tr", "TR");
                NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                String string_result = String.valueOf(numberFormatDutch.format(result));
                kampanyalar_fiyat.setText(string_result);
            } else if (corelDraws.get(position).getRate().equals("Euro")&&!Euro_Rate.isEmpty()) {
                double result = Double.valueOf(Euro_Rate) * Double.valueOf(corelDraws.get(position).getFiyat().substring(0, corelDraws.get(position).getFiyat().length() - 1));
                Locale Turkish = new Locale("tr", "TR");
                NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                String string_result = String.valueOf(numberFormatDutch.format(result));
                kampanyalar_fiyat.setText(string_result);

            } else {*/
            kampanyalar_fiyat.setText(kampanyalars.get(position).getFiyat());

            // }

        }


        kampanyalar_detail_call.setOnClickListener(new View.OnClickListener() {
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
        kampanyalar_detail_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Ürün kodu: " + kampanyalars.get(position).getKod());

                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@malzemeiste.net"});
                try {

                    activity.startActivity(Intent.createChooser(intent, "E-posta ile Via ..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(activity, "Hiçbir e-posta istemcisi yüklü değil.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // kampanyalar_title.setText(contents.get(position));
        // kampanyalar_ref.setText(contents.get());

    }


}