package com.mahariaz.smartsalah;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabLayoutAdapter extends FragmentPagerAdapter {
    private Context myContext;
    int totalTabs;
    public TabLayoutAdapter(Context context, FragmentManager fm, int totalTabs){
        super(fm);
        myContext=context;
        this.totalTabs=totalTabs;
    }
    //This is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                f1 fr1=new f1();
                return fr1;
            case 1:
                f2 fr2=new f2();
                return fr2;
            case 2:
                f3 fr3=new f3();
                return fr3;
            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
