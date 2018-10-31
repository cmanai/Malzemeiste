package com.bilgiyazan.malzemeiste.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bilgiyazan.malzemeiste.Activity.BoyalarDetailsActivity;
import com.bilgiyazan.malzemeiste.Activity.KampanyalarDetailsActivity;
import com.bilgiyazan.malzemeiste.Activity.MakinalarDetailsActivity;
import com.bilgiyazan.malzemeiste.Activity.MalzemeDetailsActivity;
import com.bilgiyazan.malzemeiste.Activity.YazilimDetailsActivity;
import com.bilgiyazan.malzemeiste.Activity.YedekDetailsActivity;
import com.bilgiyazan.malzemeiste.Model.SearchModel;
import com.bilgiyazan.malzemeiste.R;
import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class NormalItemAdapter_Search extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    List<SearchModel> SearchModel;
    int Size;
    SharedPreferences prefs;
    String Dollar_Rate;
    String Euro_Rate;
    TextView fiyat;
    String fiyat_intent;
    ImageView image;
    TextView title;
    TextView ref;
    RelativeLayout layout;
    CardView cardView;
    private int lastPosition = -1;
    private Activity activity;


    public NormalItemAdapter_Search(List<SearchModel> SearchModel, Activity activity) {
        prefs = activity.getSharedPreferences("malzemeiste", Context.MODE_PRIVATE);
        Euro_Rate = prefs.getString("Euro_Rate", "");
        Dollar_Rate = prefs.getString("Dollar_Rate", "");

        this.SearchModel = SearchModel;
        if (SearchModel != null) {

            // this.contentsRolands = contentsRolands;
            Log.e("roland size", SearchModel.size() + "");
            Size = SearchModel.size();

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
                .inflate(R.layout.normal_item_details_search, parent, false);

        return new RecyclerView.ViewHolder(view) {
        };

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        cardView = (CardView) holder.itemView.findViewById(R.id.normal_item_details_cardview);
        layout = (RelativeLayout) holder.itemView.findViewById(R.id.normal_item_details_layout);
        fiyat = (TextView) holder.itemView.findViewById(R.id.normal_item_details_fiyat);
        image = (ImageView) holder.itemView.findViewById(R.id.normal_item_details_image);
        title = (TextView) holder.itemView.findViewById(R.id.normal_item_details_title);
        ref = (TextView) holder.itemView.findViewById(R.id.normal_item_details_ref);
 /*       if (SearchModel.get(holder.getAdapterPosition()).getRate().equals("Dollar")) {
            double result = Double.valueOf(Dollar_Rate) * Double.valueOf(SearchModel.get(holder.getAdapterPosition()).getFiyat().substring(0, SearchModel.get(holder.getAdapterPosition()).getFiyat().length() - 1));
            Locale Turkish = new Locale("tr", "TR");
            NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
            String string_result = String.valueOf(numberFormatDutch.format(result));
            fiyat.setText(string_result);
        } else if (SearchModel.get(holder.getAdapterPosition()).getRate().equals("Euro")) {
            double result = Double.valueOf(Euro_Rate) * Double.valueOf(SearchModel.get(holder.getAdapterPosition()).getFiyat().substring(0, SearchModel.get(holder.getAdapterPosition()).getFiyat().length() - 1));
            Locale Turkish = new Locale("tr", "TR");
            NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
            String string_result = String.valueOf(numberFormatDutch.format(result));
            fiyat.setText(string_result);

        } else {*/
            fiyat.setText(SearchModel.get(holder.getAdapterPosition()).getFiyat());

       // }
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("what am touching", SearchModel.get(holder.getAdapterPosition()).getFrom());
            /*    if (SearchModel.get(holder.getAdapterPosition()).getRate().equals("Dollar")) {
                    double result = Double.valueOf(Dollar_Rate) * Double.valueOf(SearchModel.get(holder.getAdapterPosition()).getFiyat().substring(0, SearchModel.get(holder.getAdapterPosition()).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    fiyat_intent = string_result;
                } else if (SearchModel.get(holder.getAdapterPosition()).getRate().equals("Euro")) {
                    double result = Double.valueOf(Euro_Rate) * Double.valueOf(SearchModel.get(holder.getAdapterPosition()).getFiyat().substring(0, SearchModel.get(holder.getAdapterPosition()).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    fiyat_intent = string_result;

                } else {*/
                    fiyat_intent = SearchModel.get(holder.getAdapterPosition()).getFiyat();

               // }
                Log.e("to see2", SearchModel.get(holder.getAdapterPosition()).getFiyat());
                if (SearchModel.get(holder.getAdapterPosition()).getFrom().equals("Makinalar")) {

                    Intent intent = new Intent(activity, MakinalarDetailsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("Model", SearchModel.get(holder.getAdapterPosition()).getModel());
                    intent.putExtra("Image", SearchModel.get(holder.getAdapterPosition()).getURL());
                    intent.putExtra("Kod", SearchModel.get(holder.getAdapterPosition()).getKod());
                    intent.putExtra("Description", SearchModel.get(holder.getAdapterPosition()).getDescription());
                    intent.putExtra("Marka", SearchModel.get(holder.getAdapterPosition()).getMarka());
                    intent.putExtra("Fiyat", fiyat_intent);
                    intent.putExtra("Share_Link", SearchModel.get(holder.getAdapterPosition()).getShare_Link());
                    intent.putExtra("Rate", SearchModel.get(holder.getAdapterPosition()).getRate());
                    activity.startActivity(intent);
                } else if (SearchModel.get(holder.getAdapterPosition()).getFrom().equals("Boyalar")) {

                    Intent intent = new Intent(activity, BoyalarDetailsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("Model", SearchModel.get(holder.getAdapterPosition()).getModel());
                    intent.putExtra("Image", SearchModel.get(holder.getAdapterPosition()).getURL());
                    intent.putExtra("Kod", SearchModel.get(holder.getAdapterPosition()).getKod());

                    intent.putExtra("Renk", SearchModel.get(holder.getAdapterPosition()).getRenk());


                    intent.putExtra("Miktari", SearchModel.get(holder.getAdapterPosition()).getMiktari());
                    intent.putExtra("Ambalaj", SearchModel.get(holder.getAdapterPosition()).getAmbalaj_sekli());

                    intent.putExtra("Marka", SearchModel.get(holder.getAdapterPosition()).getMarka());
                    intent.putExtra("Fiyat", fiyat_intent);
                    intent.putExtra("Share_Link", SearchModel.get(holder.getAdapterPosition()).getShare_Link());
                    intent.putExtra("Rate", SearchModel.get(holder.getAdapterPosition()).getRate());
                    activity.startActivity(intent);
                } else if (SearchModel.get(holder.getAdapterPosition()).getFrom().equals("Yedek")) {
                    Intent intent = new Intent(activity, YedekDetailsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("Model", SearchModel.get(holder.getAdapterPosition()).getModel());
                    intent.putExtra("Image", SearchModel.get(holder.getAdapterPosition()).getURL());
                    intent.putExtra("Kod", SearchModel.get(holder.getAdapterPosition()).getKod());

                    intent.putExtra("Uyumlu", SearchModel.get(holder.getAdapterPosition()).getUyumlu());

                    intent.putExtra("Marka", SearchModel.get(holder.getAdapterPosition()).getMarka());
                    intent.putExtra("Fiyat", fiyat_intent);
                    intent.putExtra("Share_Link", SearchModel.get(holder.getAdapterPosition()).getShare_Link());
                    intent.putExtra("Rate", SearchModel.get(holder.getAdapterPosition()).getRate());
                    activity.startActivity(intent);

                } else if (SearchModel.get(holder.getAdapterPosition()).getFrom().equals("Malzeme")) {

                    Intent intent = new Intent(activity, MalzemeDetailsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    intent.putExtra("Model", SearchModel.get(holder.getAdapterPosition()).getModel());
                    intent.putExtra("Image", SearchModel.get(holder.getAdapterPosition()).getURL());
                    intent.putExtra("Kod", SearchModel.get(holder.getAdapterPosition()).getKod());
                    intent.putExtra("Marka", SearchModel.get(holder.getAdapterPosition()).getMarka());
                    intent.putExtra("Fiyat", fiyat_intent);
                    intent.putExtra("Renk", SearchModel.get(holder.getAdapterPosition()).getRenk());
                    intent.putExtra("Miktari", SearchModel.get(holder.getAdapterPosition()).getMiktari());
                    intent.putExtra("Share_Link", SearchModel.get(holder.getAdapterPosition()).getShare_Link());
                    intent.putExtra("Rate", SearchModel.get(holder.getAdapterPosition()).getRate());
                    activity.startActivity(intent);
                } else if (SearchModel.get(holder.getAdapterPosition()).getFrom().equals("Yazilim")) {

                    Intent intent = new Intent(activity, YazilimDetailsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("Model", SearchModel.get(holder.getAdapterPosition()).getModel());
                    intent.putExtra("Image", SearchModel.get(holder.getAdapterPosition()).getURL());
                    intent.putExtra("Kod", SearchModel.get(holder.getAdapterPosition()).getKod());
                    intent.putExtra("Marka", SearchModel.get(holder.getAdapterPosition()).getMarka());
                    intent.putExtra("Fiyat", fiyat_intent);
                    intent.putExtra("Share_Link", SearchModel.get(holder.getAdapterPosition()).getShare_Link());
                    activity.startActivity(intent);
                }else if (SearchModel.get(holder.getAdapterPosition()).getFrom().equals("Kampanyalar")) {

                    Intent intent = new Intent(activity, KampanyalarDetailsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("Model", SearchModel.get(holder.getAdapterPosition()).getModel());
                    intent.putExtra("Image", SearchModel.get(holder.getAdapterPosition()).getURL());
                    intent.putExtra("Kod", SearchModel.get(holder.getAdapterPosition()).getKod());
                    intent.putExtra("Fiyat", fiyat_intent);
                    intent.putExtra("Share_Link", SearchModel.get(holder.getAdapterPosition()).getShare_Link());
                    activity.startActivity(intent);
                }
            }
        });

        if (!SearchModel.get(holder.getAdapterPosition()).getURL().equals("------"))
            Glide.with(activity).load(SearchModel.get(holder.getAdapterPosition()).getURL()).into(image);
        title.setText(SearchModel.get(holder.getAdapterPosition()).getModel());
        ref.setText(SearchModel.get(holder.getAdapterPosition()).getKod());


    }


}