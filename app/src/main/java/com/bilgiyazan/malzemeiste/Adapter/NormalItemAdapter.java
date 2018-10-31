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
import com.bilgiyazan.malzemeiste.Activity.MakinalarDetailsActivity;
import com.bilgiyazan.malzemeiste.Activity.MalzemeDetailsActivity;
import com.bilgiyazan.malzemeiste.Model.Emerald;
import com.bilgiyazan.malzemeiste.Model.MLD;
import com.bilgiyazan.malzemeiste.Model.Pro_ink;
import com.bilgiyazan.malzemeiste.Model.Roland;
import com.bilgiyazan.malzemeiste.Model.Triangle;
import com.bilgiyazan.malzemeiste.R;
import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class        NormalItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    List<Roland> rolandList;
    List<Emerald> emeraldList;
    List<MLD> mldList;
    List<Pro_ink> pro_inkList;
    List<Triangle> triangleList;
    int Size;
    SharedPreferences prefs;
    String Dollar_Rate;
    String Euro_Rate;
    String From;
    private int lastPosition = -1;
    private Activity activity;

    public NormalItemAdapter(List<Roland> rolandList, List<Emerald> emeraldList, List<MLD> mldList, List<Pro_ink> pro_inkList, List<Triangle> triangleList, String From, Activity activity) {
        prefs = activity.getSharedPreferences("malzemeiste", Context.MODE_PRIVATE);
        Euro_Rate = prefs.getString("Euro_Rate", "");
        Dollar_Rate = prefs.getString("Dollar_Rate", "");
        this.From = From;
        if (rolandList != null) {

            this.rolandList = rolandList;
            Size = rolandList.size();

        }
        if (emeraldList != null) {

            this.emeraldList = emeraldList;
            Size = emeraldList.size();

        }
        if (mldList != null) {

            this.mldList = mldList;
            Size = mldList.size();

        }
        if (pro_inkList != null) {

            this.pro_inkList = pro_inkList;
            Size = pro_inkList.size();

        }
        if (triangleList != null) {

            this.triangleList = triangleList;
            Size = triangleList.size();

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
        if (From.equals("Boyalar")) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.normal_item_details_boyalar, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.normal_item_details_makinalar, parent, false);
        }


        return new RecyclerView.ViewHolder(view) {
        };

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {


        if (From.equals("Boyalar")) {
            CardView cardView = (CardView) holder.itemView.findViewById(R.id.normal_item_details_boyalar_cardview);
            RelativeLayout boyalar_layout = (RelativeLayout) holder.itemView.findViewById(R.id.normal_item_details_boyalar_layout);
            TextView boyalar_fiyat = (TextView) holder.itemView.findViewById(R.id.normal_item_details_boyalar_fiyat);
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

            if (emeraldList != null) {
                normal_item_details_boyalar_ambalaj.setText(emeraldList.get(position).getAmbalaj_sekli());
                boyalar_detail_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, emeraldList.get(position).getShare_Link());
                        sendIntent.setType("text/plain");
                        activity.startActivity(Intent.createChooser(sendIntent, "İle paylaş"));
                    }
                });


                boyalar_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, BoyalarDetailsActivity.class);
                        intent.putExtra("Model", emeraldList.get(position).getModel());
                        intent.putExtra("Image", emeraldList.get(position).getURL());
                        intent.putExtra("Kod", emeraldList.get(position).getKod());
                        intent.putExtra("Ambalaj", emeraldList.get(position).getAmbalaj_sekli());
                        intent.putExtra("Miktari", emeraldList.get(position).getMiktari());
                        intent.putExtra("Marka", "Emerald");
                        intent.putExtra("Fiyat", emeraldList.get(position).getFiyat());
                        intent.putExtra("Renk", emeraldList.get(position).getRenk());
                        intent.putExtra("Share_Link", emeraldList.get(position).getShare_Link());                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        activity.startActivity(intent);
                    }
                });


                boyalar_title.setText(emeraldList.get(position).getModel());
                boyalar_ref.setText(emeraldList.get(position).getKod());
                Glide.with(activity).load(emeraldList.get(position).getURL()).into(boyalar_image);
           /*     if (emeraldList.get(position).getRate().equals("Dollar")&&!Dollar_Rate.isEmpty()) {
                    double result = Double.valueOf(Dollar_Rate) * Double.valueOf(emeraldList.get(position).getFiyat().substring(0, emeraldList.get(position).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    boyalar_fiyat.setText(string_result);

                } else if (emeraldList.get(position).getRate().equals("Euro")&&!Euro_Rate.isEmpty()) {
                    double result = Double.valueOf(Euro_Rate) * Double.valueOf(emeraldList.get(position).getFiyat().substring(0, emeraldList.get(position).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    boyalar_fiyat.setText(string_result);


                } else {*/
                    boyalar_fiyat.setText(emeraldList.get(position).getFiyat());

                //}


            } else if (mldList != null) {
                normal_item_details_boyalar_ambalaj.setText(mldList.get(position).getAmbalaj_sekli());

                boyalar_detail_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, mldList.get(position).getShare_Link());
                        sendIntent.setType("text/plain");
                        activity.startActivity(Intent.createChooser(sendIntent, "İle paylaş"));
                    }
                });

                boyalar_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, BoyalarDetailsActivity.class);
                        intent.putExtra("Model", mldList.get(position).getModel());
                        intent.putExtra("Image", mldList.get(position).getURL());
                        intent.putExtra("Kod", mldList.get(position).getKod());
                        intent.putExtra("Ambalaj", mldList.get(position).getAmbalaj_sekli());
                        intent.putExtra("Miktari", mldList.get(position).getMiktari());
                        intent.putExtra("Marka", "MLD");
                        intent.putExtra("Fiyat", mldList.get(position).getFiyat());
                        intent.putExtra("Renk", mldList.get(position).getRenk());
                        intent.putExtra("Share_Link", mldList.get(position).getShare_Link());
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        activity.startActivity(intent);
                    }
                });
                Glide.with(activity).load(mldList.get(position).getURL()).into(boyalar_image);
                boyalar_title.setText(mldList.get(position).getModel());
                boyalar_ref.setText(mldList.get(position).getKod());
                boyalar_fiyat.setText(mldList.get(position).getFiyat());
               /* if (mldList.get(position).getRate().equals("Dollar")) {
                    double result = Double.valueOf(Dollar_Rate) * Double.valueOf(mldList.get(position).getFiyat().substring(0, mldList.get(position).getFiyat().length() - 1));
                    String string_result = String.valueOf(result);
                    boyalar_fiyat.setText(string_result);


                } else if (mldList.get(position).getRate().equals("Euro")) {
                    double result = Double.valueOf(Euro_Rate) * Double.valueOf(mldList.get(position).getFiyat().substring(0, mldList.get(position).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    boyalar_fiyat.setText(string_result);


                } else {*/
                    boyalar_fiyat.setText(mldList.get(position).getFiyat());

//                }

            } else if (pro_inkList != null) {
                normal_item_details_boyalar_ambalaj.setText(pro_inkList.get(position).getAmbalaj_sekli());

                boyalar_detail_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, pro_inkList.get(position).getShare_Link());
                        sendIntent.setType("text/plain");
                        activity.startActivity(Intent.createChooser(sendIntent, "İle paylaş"));
                    }
                });
                boyalar_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, BoyalarDetailsActivity.class);
                        intent.putExtra("Model", pro_inkList.get(position).getModel());
                        intent.putExtra("Image", pro_inkList.get(position).getURL());
                        intent.putExtra("Kod", pro_inkList.get(position).getKod());
                        intent.putExtra("Ambalaj", pro_inkList.get(position).getAmbalaj_sekli());
                        intent.putExtra("Miktari", pro_inkList.get(position).getMiktari());
                        intent.putExtra("Marka", "Pro ink");
                        intent.putExtra("Fiyat", pro_inkList.get(position).getFiyat());
                        intent.putExtra("Renk", pro_inkList.get(position).getRenk());
                        intent.putExtra("Share_Link", pro_inkList.get(position).getShare_Link());
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        activity.startActivity(intent);
                    }
                });
                Glide.with(activity).load(pro_inkList.get(position).getURL()).into(boyalar_image);

                boyalar_title.setText(pro_inkList.get(position).getModel());
                boyalar_ref.setText(pro_inkList.get(position).getKod());
                boyalar_fiyat.setText(pro_inkList.get(position).getFiyat());
                if (pro_inkList.get(position).getRate().equals("Dollar")) {
                    double result = Double.valueOf(Dollar_Rate) * Double.valueOf(pro_inkList.get(position).getFiyat().substring(0, pro_inkList.get(position).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    boyalar_fiyat.setText(string_result);


                } else if (pro_inkList.get(position).getRate().equals("Euro")) {
                    double result = Double.valueOf(Euro_Rate) * Double.valueOf(pro_inkList.get(position).getFiyat().substring(0, pro_inkList.get(position).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    boyalar_fiyat.setText(string_result);


                } else {
                    boyalar_fiyat.setText(pro_inkList.get(position).getFiyat());

                }

            } else if (triangleList != null) {

                boyalar_detail_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, triangleList.get(position).getShare_Link());
                        sendIntent.setType("text/plain");
                        activity.startActivity(Intent.createChooser(sendIntent, "İle paylaş"));
                    }
                });
                boyalar_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, MakinalarDetailsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        activity.startActivity(intent);
                    }
                });
                Glide.with(activity).load(triangleList.get(position).getURL()).into(boyalar_image);

                boyalar_title.setText(triangleList.get(position).getModel());
                boyalar_ref.setText(triangleList.get(position).getKod());
                boyalar_fiyat.setText(triangleList.get(position).getFiyat());
                if (triangleList.get(position).getRate().equals("Dollar")) {
                    double result = Double.valueOf(Dollar_Rate) * Double.valueOf(triangleList.get(position).getFiyat().substring(0, triangleList.get(position).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    boyalar_fiyat.setText(string_result);
                } else if (triangleList.get(position).getRate().equals("Euro")) {
                    double result = Double.valueOf(Euro_Rate) * Double.valueOf(triangleList.get(position).getFiyat().substring(0, triangleList.get(position).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    boyalar_fiyat.setText(string_result);

                } else {
                    boyalar_fiyat.setText(triangleList.get(position).getFiyat());
                }

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
                    if (rolandList != null) {

                        intent.putExtra(Intent.EXTRA_SUBJECT, "Ürün kodu: " + rolandList.get(position).getKod());
                    } else if (emeraldList != null) {
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Ürün kodu: " + emeraldList.get(position).getKod());

                    } else if (mldList != null) {
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Ürün kodu: " + mldList.get(position).getKod());

                    } else if (pro_inkList != null) {
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Ürün kodu: " + pro_inkList.get(position).getKod());

                    }


                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@malzemeiste.net"});
                    try {

                        activity.startActivity(Intent.createChooser(intent, "E-posta ile Via ..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(activity, "Hiçbir e-posta istemcisi yüklü değil.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            CardView cardView = (CardView) holder.itemView.findViewById(R.id.normal_item_details_makinalar_cardview);
            RelativeLayout makinalar_layout = (RelativeLayout) holder.itemView.findViewById(R.id.normal_item_details_makinalar_layout);
            TextView makinalar_fiyat = (TextView) holder.itemView.findViewById(R.id.normal_item_details_makinalar_fiyat);
            ImageView makinalar_image = (ImageView) holder.itemView.findViewById(R.id.normal_item_details_makinalar_image);
            TextView makinalar_title = (TextView) holder.itemView.findViewById(R.id.normal_item_details_makinalar_title);
            TextView makinalar_ref = (TextView) holder.itemView.findViewById(R.id.normal_item_details_makinalar_ref);
            ImageView makinalar_detail_call;
            ImageView makinalar_detail_email;
            ImageView makinalar_detail_share;
            makinalar_detail_call = (ImageView) holder.itemView.findViewById(R.id.normal_item_details_makinalar_call);
            makinalar_detail_email = (ImageView) holder.itemView.findViewById(R.id.normal_item_details_makinalar_email);
            makinalar_detail_share = (ImageView) holder.itemView.findViewById(R.id.normal_item_details_makinalar_share);
            if (rolandList != null) {
                makinalar_detail_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, rolandList.get(position).getShare_Link());
                        sendIntent.setType("text/plain");
                        activity.startActivity(Intent.createChooser(sendIntent, "İle paylaş"));
                    }
                });
                if (!rolandList.get(position).getURL().equals("------"))
                    Glide.with(activity).load(rolandList.get(position).getURL()).into(makinalar_image);
                makinalar_title.setText(rolandList.get(position).getModel());
                makinalar_ref.setText(rolandList.get(position).getKod());
               /* if (rolandList.get(position).getRate().equals("Dollar")) {
                    double result = Double.valueOf(Dollar_Rate) * Double.valueOf(rolandList.get(position).getFiyat().substring(0, rolandList.get(position).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    makinalar_fiyat.setText(string_result);
                } else if (rolandList.get(position).getRate().equals("Euro")) {
                    double result = Double.valueOf(Euro_Rate) * Double.valueOf(rolandList.get(position).getFiyat().substring(0, rolandList.get(position).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    makinalar_fiyat.setText(string_result);

                } else {*/
                    makinalar_fiyat.setText(rolandList.get(position).getFiyat());

              //  }


                    makinalar_layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(activity, MakinalarDetailsActivity.class);
                            intent.putExtra("Model", rolandList.get(position).getModel());
                            intent.putExtra("Image", rolandList.get(position).getURL());
                            intent.putExtra("Kod", rolandList.get(position).getKod());
                            intent.putExtra("Description", rolandList.get(position).getDescription());
                            intent.putExtra("Marka", "Roland");
                            intent.putExtra("Fiyat", rolandList.get(position).getFiyat());
                            intent.putExtra("Share_Link", rolandList.get(position).getShare_Link());
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            activity.startActivity(intent);
                        }
                    });

            }
            if (emeraldList != null) {
                makinalar_detail_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, emeraldList.get(position).getShare_Link());
                        sendIntent.setType("text/plain");
                        activity.startActivity(Intent.createChooser(sendIntent, "İle paylaş"));
                    }
                });
                if (!emeraldList.get(position).getURL().equals("------"))
                    Glide.with(activity).load(emeraldList.get(position).getURL()).into(makinalar_image);
                makinalar_title.setText(emeraldList.get(position).getModel());
                makinalar_ref.setText(emeraldList.get(position).getKod());
                if (emeraldList.get(position).getRate().equals("Dollar")) {
                    double result = Double.valueOf(Dollar_Rate) * Double.valueOf(emeraldList.get(position).getFiyat().substring(0, emeraldList.get(position).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    makinalar_fiyat.setText(string_result);
                } else if (emeraldList.get(position).getRate().equals("Euro")) {
                    double result = Double.valueOf(Euro_Rate) * Double.valueOf(emeraldList.get(position).getFiyat().substring(0, emeraldList.get(position).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    makinalar_fiyat.setText(string_result);

                } else {
                    makinalar_fiyat.setText(rolandList.get(position).getFiyat());

                }

            }
            if (mldList != null) {
                makinalar_detail_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, mldList.get(position).getShare_Link());
                        sendIntent.setType("text/plain");
                        activity.startActivity(Intent.createChooser(sendIntent, "İle paylaş"));
                    }
                });
                if (!mldList.get(position).getURL().equals("------"))
                    Glide.with(activity).load(mldList.get(position).getURL()).into(makinalar_image);
                makinalar_title.setText(mldList.get(position).getModel());
                makinalar_ref.setText(mldList.get(position).getKod());
                if (mldList.get(position).getRate().equals("Dollar")) {
                    double result = Double.valueOf(Dollar_Rate) * Double.valueOf(mldList.get(position).getFiyat().substring(0, mldList.get(position).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    makinalar_fiyat.setText(string_result);
                } else if (mldList.get(position).getRate().equals("Euro")) {
                    double result = Double.valueOf(Euro_Rate) * Double.valueOf(mldList.get(position).getFiyat().substring(0, mldList.get(position).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    makinalar_fiyat.setText(string_result);

                } else {
                    makinalar_fiyat.setText(mldList.get(position).getFiyat());

                }

            }
            if (pro_inkList != null) {
                makinalar_detail_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, pro_inkList.get(position).getShare_Link());
                        sendIntent.setType("text/plain");
                        activity.startActivity(Intent.createChooser(sendIntent, "İle paylaş"));
                    }
                });
                if (!pro_inkList.get(position).getURL().equals("------"))
                    Glide.with(activity).load(pro_inkList.get(position).getURL()).into(makinalar_image);
                makinalar_title.setText(pro_inkList.get(position).getModel());
                makinalar_ref.setText(pro_inkList.get(position).getKod());
                if (pro_inkList.get(position).getRate().equals("Dollar")) {
                    double result = Double.valueOf(Dollar_Rate) * Double.valueOf(pro_inkList.get(position).getFiyat().substring(0, pro_inkList.get(position).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    makinalar_fiyat.setText(string_result);
                } else if (pro_inkList.get(position).getRate().equals("Euro")) {
                    double result = Double.valueOf(Euro_Rate) * Double.valueOf(pro_inkList.get(position).getFiyat().substring(0, pro_inkList.get(position).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    makinalar_fiyat.setText(string_result);

                } else {
                    makinalar_fiyat.setText(pro_inkList.get(position).getFiyat());

                }

            }
          /*  if (triangleList != null) {
                makinalar_detail_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, triangleList.get(position).getShare_Link());
                        sendIntent.setType("text/plain");
                        activity.startActivity(Intent.createChooser(sendIntent, "İle paylaş"));
                    }
                });
                if (!triangleList.get(position).getURL().equals("------"))
                    Glide.with(activity).load(triangleList.get(position).getURL()).into(makinalar_image);
                makinalar_title.setText(triangleList.get(position).getModel());
                makinalar_ref.setText(triangleList.get(position).getKod());
                if (triangleList.get(position).getRate().equals("Dollar")) {
                    double result = Double.valueOf(Dollar_Rate) * Double.valueOf(triangleList.get(position).getFiyat().substring(0, triangleList.get(position).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    makinalar_fiyat.setText(string_result);
                } else if (triangleList.get(position).getRate().equals("Euro")) {
                    double result = Double.valueOf(Euro_Rate) * Double.valueOf(triangleList.get(position).getFiyat().substring(0, triangleList.get(position).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    makinalar_fiyat.setText(string_result);

                } else {
                    makinalar_fiyat.setText(triangleList.get(position).getFiyat());

                }

            }*/


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
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("message/rfc822");
                    if (rolandList != null) {

                        intent.putExtra(Intent.EXTRA_SUBJECT, "Ürün kodu: " + rolandList.get(position).getKod());
                    } else if (emeraldList != null) {
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Ürün kodu: " + emeraldList.get(position).getKod());

                    } else if (mldList != null) {
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Ürün kodu: " + mldList.get(position).getKod());

                    } else if (pro_inkList != null) {
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Ürün kodu: " + pro_inkList.get(position).getKod());

                    }
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@malzemeiste.net"});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Ürün kodu: " + pro_inkList.get(position).getKod());

                    try {

                        activity.startActivity(Intent.createChooser(intent, "E-posta ile Via ..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(activity, "Hiçbir e-posta istemcisi yüklü değil.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            // makinalar_title.setText(contents.get(position));
            // makinalar_ref.setText(contents.get());

        }

    }


}