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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bilgiyazan.malzemeiste.Activity.BoyalarDetailsActivity;
import com.bilgiyazan.malzemeiste.Model.Emerald;
import com.bilgiyazan.malzemeiste.Model.MLD;
import com.bilgiyazan.malzemeiste.Model.Pro_ink;
import com.bilgiyazan.malzemeiste.Model.Triangle;
import com.bilgiyazan.malzemeiste.R;
import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by florentchampigny on 24/04/15.
 */

public class NormalItemAdapter_Boyalar extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private List<Emerald> contentsEmerald;
    private List<MLD> contentsMLD;
    private List<Pro_ink> contentsPro_ink;
    private List<Triangle> contentsTriangle;
    private int Size;
    private SharedPreferences prefs;
    private String Dollar_Rate;
    private String Euro_Rate;
    private int lastPosition = -1;
    private Activity activity;
    private int pos;

    NormalItemAdapter_Boyalar(List<Emerald> contentsEmerald, List<MLD> contentsMLD, List<Pro_ink> contentsPro_ink, List<Triangle> contentsTriangle, Activity activity) {
        prefs = activity.getSharedPreferences("malzemeiste", Context.MODE_PRIVATE);
        Euro_Rate = prefs.getString("Euro_Rate", "");
        Dollar_Rate = prefs.getString("Dollar_Rate", "");
        if (contentsEmerald != null) {
            this.contentsEmerald = contentsEmerald;
            Log.e("Pro_ink size", contentsEmerald.size() + "");
            Size = contentsEmerald.size();
        }
        if (contentsMLD != null) {

            this.contentsMLD = contentsMLD;
            Log.e("MLD size", contentsMLD.size() + "");
            Size = contentsMLD.size();

        }
        if (contentsPro_ink != null) {
            this.contentsPro_ink = contentsPro_ink;
            Log.e("Pro_ink size", contentsPro_ink.size() + "");
            Size = contentsPro_ink.size();


        }

        if (contentsTriangle != null) {
            this.contentsTriangle = contentsTriangle;
            Log.e("Triangle size", contentsTriangle.size() + "");
            Size = contentsTriangle.size();


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
                .inflate(R.layout.normal_item_details_boyalar, parent, false);

        return new RecyclerView.ViewHolder(view) {
        };

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        CardView cardView = (CardView) holder.itemView.findViewById(R.id.normal_item_details_boyalar_cardview);
        RelativeLayout boyalar_layout = (RelativeLayout) holder.itemView.findViewById(R.id.normal_item_details_boyalar_layout);
        final TextView boyalar_fiyat = (TextView) holder.itemView.findViewById(R.id.normal_item_details_boyalar_fiyat);
        ImageView boyalar_image = (ImageView) holder.itemView.findViewById(R.id.normal_item_details_boyalar_image);
        TextView boyalar_title = (TextView) holder.itemView.findViewById(R.id.normal_item_details_boyalar_title);
        TextView boyalar_ref = (TextView) holder.itemView.findViewById(R.id.normal_item_details_boyalar_ref);
        TextView normal_item_details_boyalar_ambalaj = (TextView) holder.itemView.findViewById(R.id.normal_item_details_boyalar_ambalaj);
        ImageView boyalar_detail_call;
        ImageView boyalar_detail_email;
        ImageView boyalar_detail_share;
        boyalar_detail_call = (ImageView) holder.itemView.findViewById(R.id.normal_item_details_boyalar_call);
        boyalar_detail_email = (ImageView) holder.itemView.findViewById(R.id.normal_item_details_boyalar_email);
        boyalar_detail_share = (ImageView) holder.itemView.findViewById(R.id.normal_item_details_boyalar_share);
        pos = holder.getAdapterPosition();

        if (contentsEmerald != null) {
            normal_item_details_boyalar_ambalaj.setText(contentsEmerald.get(position).getAmbalaj_sekli());
            boyalar_detail_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, contentsEmerald.get(pos).getShare_Link());
                    sendIntent.setType("text/plain");
                    activity.startActivity(Intent.createChooser(sendIntent, "İle paylaş"));
                }
            });


            boyalar_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, BoyalarDetailsActivity.class);

                    intent.putExtra("Model", contentsEmerald.get(position).getModel());
                    intent.putExtra("Image", contentsEmerald.get(position).getURL());
                    intent.putExtra("Kod", contentsEmerald.get(position).getKod());
                    intent.putExtra("Renk", contentsEmerald.get(position).getRenk());
                    intent.putExtra("Miktari", contentsEmerald.get(position).getMiktari());
                    intent.putExtra("Ambalaj", contentsEmerald.get(position).getAmbalaj_sekli());
                    //  intent.putExtra("Description",contentsEmerald.get(position).getDescription());
                    intent.putExtra("Marka", "Emerald");
                    intent.putExtra("Fiyat", boyalar_fiyat.getText().toString());
                    intent.putExtra("Share_Link", contentsEmerald.get(position).getShare_Link());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    activity.startActivity(intent);
                }
            });

            boyalar_title.setText(contentsEmerald.get(position).getModel());
            boyalar_ref.setText(contentsEmerald.get(position).getKod());
            Glide.with(activity).load(contentsEmerald.get(position).getURL()).into(boyalar_image);
        /*    if (contentsEmerald.get(position).getRate().equals("Dollar")&&!Dollar_Rate.isEmpty()) {

                double result;


                     result = Double.valueOf(Dollar_Rate) * Double.valueOf(contentsEmerald.get(position).getFiyat().substring(0, contentsEmerald.get(position).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    boyalar_fiyat.setText(string_result);


        } else if (contentsEmerald.get(position).getRate().equals("Euro")&&!Euro_Rate.isEmpty()) {
                double result;



                    result = Double.valueOf(Euro_Rate) * Double.valueOf(contentsEmerald.get(position).getFiyat().substring(0, contentsEmerald.get(position).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    boyalar_fiyat.setText(string_result);



            } else {*/
            boyalar_fiyat.setText(contentsEmerald.get(position).getFiyat());

            // }


        } else if (contentsMLD != null) {
            normal_item_details_boyalar_ambalaj.setText(contentsMLD.get(position).getAmbalaj_sekli());

            boyalar_detail_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, contentsMLD.get(pos).getShare_Link());
                    sendIntent.setType("text/plain");
                    activity.startActivity(Intent.createChooser(sendIntent, "İle paylaş"));
                }
            });

            boyalar_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, BoyalarDetailsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    intent.putExtra("Model", contentsMLD.get(position).getModel());
                    intent.putExtra("Image", contentsMLD.get(position).getURL());
                    intent.putExtra("Kod", contentsMLD.get(position).getKod());
                    intent.putExtra("Renk", contentsMLD.get(position).getRenk());
                    intent.putExtra("Miktari", contentsMLD.get(position).getMiktari());
                    intent.putExtra("Ambalaj", contentsMLD.get(position).getAmbalaj_sekli());

                    // intent.putExtra("Description",contentsMLD.get(position).getDescription());
                    intent.putExtra("Marka", "MLD");
                    intent.putExtra("Fiyat", boyalar_fiyat.getText().toString());
                    intent.putExtra("Share_Link", contentsMLD.get(position).getShare_Link());
                    activity.startActivity(intent);
                }
            });
            Glide.with(activity).load(contentsMLD.get(position).getURL()).into(boyalar_image);
            boyalar_title.setText(contentsMLD.get(position).getModel());
            boyalar_ref.setText(contentsMLD.get(position).getKod());
            boyalar_fiyat.setText(contentsMLD.get(position).getFiyat());
         /*   if (contentsMLD.get(position).getRate().equals("Dollar")) {
                double result = Double.valueOf(Dollar_Rate) * Double.valueOf(contentsMLD.get(position).getFiyat().substring(0, contentsMLD.get(position).getFiyat().length() - 1));
                String string_result = String.valueOf(result);
                boyalar_fiyat.setText(string_result);


            } else if (contentsMLD.get(position).getRate().equals("Euro")) {
                double result = Double.valueOf(Euro_Rate) * Double.valueOf(contentsMLD.get(position).getFiyat().substring(0, contentsMLD.get(position).getFiyat().length() - 1));
                Locale Turkish = new Locale("tr", "TR");
                NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                String string_result = String.valueOf(numberFormatDutch.format(result));
                boyalar_fiyat.setText(string_result);


            } else {*/
            boyalar_fiyat.setText(contentsMLD.get(position).getFiyat());

            // }

        } else if (contentsPro_ink != null) {
            normal_item_details_boyalar_ambalaj.setText(contentsPro_ink.get(position).getAmbalaj_sekli());

            boyalar_detail_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, contentsPro_ink.get(pos).getShare_Link());
                    sendIntent.setType("text/plain");
                    activity.startActivity(Intent.createChooser(sendIntent, "İle paylaş"));
                }
            });
            boyalar_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, BoyalarDetailsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    intent.putExtra("Model", contentsPro_ink.get(position).getModel());
                    intent.putExtra("Image", contentsPro_ink.get(position).getURL());
                    intent.putExtra("Kod", contentsPro_ink.get(position).getKod());
                    intent.putExtra("Renk", contentsPro_ink.get(position).getRenk());
                    intent.putExtra("Miktari", contentsPro_ink.get(position).getMiktari());
                    intent.putExtra("Ambalaj", contentsPro_ink.get(position).getAmbalaj_sekli());

                    //  intent.putExtra("Description",contentsPro_ink.get(position).getDescription());
                    intent.putExtra("Marka", "Pro Ink");
                    intent.putExtra("Fiyat", boyalar_fiyat.getText().toString());
                    intent.putExtra("Share_Link", contentsPro_ink.get(position).getShare_Link());
                    activity.startActivity(intent);
                }
            });
            Glide.with(activity).load(contentsPro_ink.get(position).getURL()).into(boyalar_image);

            boyalar_title.setText(contentsPro_ink.get(position).getModel());
            boyalar_ref.setText(contentsPro_ink.get(position).getKod());
            boyalar_fiyat.setText(contentsPro_ink.get(position).getFiyat());
       /*     if (contentsPro_ink.get(position).getRate().equals("Dollar")) {
                double result = Double.valueOf(Dollar_Rate) * Double.valueOf(contentsPro_ink.get(position).getFiyat().substring(0, contentsPro_ink.get(position).getFiyat().length() - 1));
                Locale Turkish = new Locale("tr", "TR");
                NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                String string_result = String.valueOf(numberFormatDutch.format(result));
                boyalar_fiyat.setText(string_result);


            } else if (contentsPro_ink.get(position).getRate().equals("Euro")) {
                double result = Double.valueOf(Euro_Rate) * Double.valueOf(contentsPro_ink.get(position).getFiyat().substring(0, contentsPro_ink.get(position).getFiyat().length() - 1));
                Locale Turkish = new Locale("tr", "TR");
                NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                String string_result = String.valueOf(numberFormatDutch.format(result));
                boyalar_fiyat.setText(string_result);


            } else {*/
            boyalar_fiyat.setText(contentsPro_ink.get(position).getFiyat());

            //  }

        } else if (contentsTriangle != null) {

            boyalar_detail_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, contentsTriangle.get(pos).getShare_Link());
                    sendIntent.setType("text/plain");
                    activity.startActivity(Intent.createChooser(sendIntent, "İle paylaş"));
                }
            });
            boyalar_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, BoyalarDetailsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("Model", contentsTriangle.get(position).getModel());
                    intent.putExtra("Image", contentsTriangle.get(position).getURL());
                    intent.putExtra("Kod", contentsTriangle.get(position).getKod());
                    intent.putExtra("Ambalaj", contentsTriangle.get(position).getAmbalaj_sekli());

                    intent.putExtra("Renk", contentsTriangle.get(position).getRenk());
                    intent.putExtra("Miktari", contentsTriangle.get(position).getMiktari());
                    // intent.putExtra("Description",contentsTriangle.get(position).getDescription());
                    intent.putExtra("Marka", "Triangle");
                    intent.putExtra("Fiyat", boyalar_fiyat.getText().toString());
                    intent.putExtra("Share_Link", contentsTriangle.get(position).getShare_Link());
                    activity.startActivity(intent);
                }
            });
            Glide.with(activity).load(contentsTriangle.get(position).getURL()).into(boyalar_image);

            boyalar_title.setText(contentsTriangle.get(position).getModel());
            boyalar_ref.setText(contentsTriangle.get(position).getKod());
            boyalar_fiyat.setText(contentsTriangle.get(position).getFiyat());
          /*  if (contentsTriangle.get(position).getRate().equals("Dollar")) {
                double result = Double.valueOf(Dollar_Rate) * Double.valueOf(contentsTriangle.get(position).getFiyat().substring(0, contentsTriangle.get(position).getFiyat().length() - 1));
                Locale Turkish = new Locale("tr", "TR");
                NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                String string_result = String.valueOf(numberFormatDutch.format(result));
                boyalar_fiyat.setText(string_result);
            } else if (contentsTriangle.get(position).getRate().equals("Euro")) {
                double result = Double.valueOf(Euro_Rate) * Double.valueOf(contentsTriangle.get(position).getFiyat().substring(0, contentsTriangle.get(position).getFiyat().length() - 1));
                Locale Turkish = new Locale("tr", "TR");
                NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                String string_result = String.valueOf(numberFormatDutch.format(result));
                boyalar_fiyat.setText(string_result);

            } else {*/
                boyalar_fiyat.setText(contentsTriangle.get(position).getFiyat());
           // }

        }


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

                if (contentsEmerald != null) {
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Ürün kodu: " + contentsEmerald.get(position).getKod());
                } else if (contentsPro_ink != null) {
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Ürün kodu: " + contentsPro_ink.get(position).getKod());

                } else if (contentsMLD != null) {
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Ürün kodu: " + contentsMLD.get(position).getKod());

                }


                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@malzemeiste.net"});
                try {

                    activity.startActivity(Intent.createChooser(intent, "E-posta ile Via ..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(activity, "Hiçbir e-posta istemcisi yüklü değil.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // boyalar_title.setText(contents.get(position));
        // boyalar_ref.setText(contents.get());

    }


}