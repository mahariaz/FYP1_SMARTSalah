package com.mahariaz.smartsalah;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class salahdua3 extends Fragment {
    CardView cardView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_salahdua3, container, false);
        cardView = view.findViewById(R.id.dua_cardview);
        cardView.setBackgroundResource(R.drawable.upper_roundedcorners);
        return view;
    }
}