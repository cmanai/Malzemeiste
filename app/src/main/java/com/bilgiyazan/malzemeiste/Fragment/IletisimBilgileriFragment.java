package com.bilgiyazan.malzemeiste.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bilgiyazan.malzemeiste.Adapter.IletisimBilgileriAdapter;
import com.bilgiyazan.malzemeiste.R;

import java.util.ArrayList;
import java.util.List;


public class IletisimBilgileriFragment extends Fragment {

    private static final int ITEM_COUNT = 100;
    public static boolean GRID_LAYOUT = false;
    RecyclerView.LayoutManager layoutManager;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<Object> mContentItems = new ArrayList<>();

    public static IletisimBilgileriFragment newInstance() {
        return new IletisimBilgileriFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.iletisim_bilgileri_fragment, container, false);



       /* if (GRID_LAYOUT) {
            layoutManager = new GridLayoutManager(getActivity(), 2);
            mRecyclerView.setLayoutManager(layoutManager);
        } else {*/
        mRecyclerView = (RecyclerView) view.findViewById(R.id.iletisim_bilgileri_recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        //  }

        mRecyclerView.setHasFixedSize(true);


        mAdapter = new IletisimBilgileriAdapter(mContentItems, savedInstanceState, getActivity());

        mRecyclerView.setAdapter(mAdapter);
        {

            mContentItems.add(new Object());

            mAdapter.notifyDataSetChanged();

        }


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

}