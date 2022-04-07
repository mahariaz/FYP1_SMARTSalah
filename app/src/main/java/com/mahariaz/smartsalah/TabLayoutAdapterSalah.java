package com.mahariaz.smartsalah;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabLayoutAdapterSalah  extends FragmentPagerAdapter {
    private Context myContext;
    int totalTabs;
    public TabLayoutAdapterSalah(Context context, FragmentManager fm, int totalTabs){
        super(fm);
        myContext=context;
        this.totalTabs=totalTabs;
    }
    //This is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                salahdua1 salahdua1=new salahdua1();
                return salahdua1;
            case 1:
                salahdua2 salahdua2=new salahdua2();
                return salahdua2;
            case 2:
                salahdua3 salahdua3=new salahdua3();
                return salahdua3;
            case 3:
                salahdua4 salahdua4=new salahdua4();
                return salahdua4;
            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return totalTabs;
    }

}
