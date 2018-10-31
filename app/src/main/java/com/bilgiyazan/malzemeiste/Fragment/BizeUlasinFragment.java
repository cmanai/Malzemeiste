package com.bilgiyazan.malzemeiste.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bilgiyazan.malzemeiste.Adapter.BizeUlasinAdapter;
import com.bilgiyazan.malzemeiste.R;

import java.util.ArrayList;
import java.util.List;


public class BizeUlasinFragment extends Fragment {

    private static final int ITEM_COUNT = 100;
    public static boolean GRID_LAYOUT = false;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<Object> mContentItems = new ArrayList<>();

    public static BizeUlasinFragment newInstance() {
        return new BizeUlasinFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bize_ulasin_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.bize_ulasin_recyclerView);
        RecyclerView.LayoutManager layoutManager;

       /* if (GRID_LAYOUT) {
            layoutManager = new GridLayoutManager(getActivity(), 2);
            mRecyclerView.setLayoutManager(layoutManager);
        } else {*/
        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        //  }

        mRecyclerView.setHasFixedSize(true);


        mAdapter = new BizeUlasinAdapter(mContentItems, getActivity());


        mRecyclerView.setAdapter(mAdapter);
        {

            mContentItems.add(new Object());

            mAdapter.notifyDataSetChanged();
        }


    }
}