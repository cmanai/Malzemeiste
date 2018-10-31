package com.bilgiyazan.malzemeiste.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.bilgiyazan.malzemeiste.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.List;
import java.util.regex.Pattern;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class BizeUlasinAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_CELL = 1;
    private List<Object> contents;
    private MaterialEditText Name;
    private MaterialEditText Email;
    private MaterialEditText Konu;
    private MaterialEditText Message;
    private FancyButton Send_form;
    private String I_Want;
    private Activity activity;

    public BizeUlasinAdapter(List<Object> contents, Activity activity) {
        this.contents = contents;
        this.activity = activity;
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public int getItemViewType(int position) {


        return TYPE_HEADER;


    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bize_ulasin_item_recyclerview, parent, false);

        return new RecyclerView.ViewHolder(view) {
        };

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        CardView cardView = (CardView) holder.itemView.findViewById(R.id.card_view);

        setupUI(cardView);


        Name = (MaterialEditText) holder.itemView.findViewById(R.id.name);
        Email = (MaterialEditText) holder.itemView.findViewById(R.id.email);
        Konu = (MaterialEditText) holder.itemView.findViewById(R.id.Konu);

        Message = (MaterialEditText) holder.itemView.findViewById(R.id.message);
        Send_form = (FancyButton) holder.itemView.findViewById(R.id.send_form);


        Send_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Name.getText().toString().isEmpty() && !Email.getText().toString().isEmpty() && !Konu.getText().toString().isEmpty() && !Message.getText().toString().isEmpty()) {


                    if (isValidEmaillId(Email.getText().toString().trim())) {
                        Email.setError(null);
                        Konu.setError(null);

                        I_Want = " ";
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("message/rfc822");

                        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@malzemeiste.net"});
                        i.putExtra(Intent.EXTRA_SUBJECT, "İletişim Formu");
                        i.putExtra(Intent.EXTRA_TEXT, "Ad Soyad:\n" + Name.getText().toString() + "\nE-posta:\n" + Email.getText().toString()
                                + "\n\nKonu:\n" + Konu.getText().toString()
                                + "\n\nİleti Gövdesi:\n" + Message.getText().toString()
                                + "\n\n" + I_Want);
                        try {
                            holder.itemView.getContext().startActivity(Intent.createChooser(i, "Formu gönder..."));

                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(holder.itemView.getContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        if (!isValidEmaillId(Email.getText().toString().trim())) {
                            Email.setError("E-posta biçimi geçersiz");
                        }


                    }

                } else {

                    if (Name.getText().toString().isEmpty()) {
                        Name.setError("gerekli alan");
                    } else {
                        Name.setError(null);
                    }
                    if (Email.getText().toString().isEmpty()) {
                        Email.setError("gerekli alan");
                    } else {
                        Email.setError(null);
                    }

                    if (Konu.getText().toString().isEmpty()) {
                        Konu.setError("gerekli alan");
                    } else {
                        Konu.setError(null);
                    }


                    if (Message.getText().toString().isEmpty()) {
                        Message.setError("gerekli alan");
                    } else {
                        Message.setError(null);
                    }

                }


            }
        });



      /*  TextView textView =(TextView)holder.itemView.findViewById(R.id.policy) ;
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        textView.setText("Gizlilik Politikası");*/

    }

    private boolean isValidEmaillId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    private boolean isValidMobile(String phone2) {
        return android.util.Patterns.PHONE.matcher(phone2).matches();
    }

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof MaterialEditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(activity);
                    return false;
                }
            });
        }

       /* //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }*/
    }


}