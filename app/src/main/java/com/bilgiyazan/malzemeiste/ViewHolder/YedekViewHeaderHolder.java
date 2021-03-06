package com.bilgiyazan.malzemeiste.ViewHolder;

/**
 * Created by Toshiba on 20/12/2016.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bilgiyazan.malzemeiste.R;

/**
 * Simple view holder for a single text view.
 */
public class YedekViewHeaderHolder extends RecyclerView.ViewHolder {

    private TextView mTextView;

    public YedekViewHeaderHolder(View view) {
        super(view);

        mTextView = (TextView) view.findViewById(R.id.text_header);
    }

    public void bindItem(String text) {
        mTextView.setText(text);
    }

    @Override
    public String toString() {
        return mTextView.getText().toString();
    }
}