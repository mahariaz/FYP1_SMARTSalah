package com.mahariaz.smartsalah;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class TabLayoutAdapterSalah  extends FragmentPagerAdapter {
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
class TabLayoutAdapterMorning  extends FragmentPagerAdapter {
    private Context myContext;
    int totalTabs;
    public TabLayoutAdapterMorning(Context context, FragmentManager fm, int totalTabs){
        super(fm);
        myContext=context;
        this.totalTabs=totalTabs;
    }
    //This is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                morningDua1 morningDua1=new morningDua1();
                return morningDua1;
            case 1:
                morningDua2 morningDua2=new morningDua2();
                return morningDua2;
            case 2:
                morningDua3 morningDua3=new morningDua3();
                return morningDua3;
            case 3:
                morningDua4 morningDua4=new morningDua4();
                return morningDua4;
            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return totalTabs;
    }

}
class TabLayoutAdapterIllness  extends FragmentPagerAdapter {
    private Context myContext;
    int totalTabs;
    public TabLayoutAdapterIllness(Context context, FragmentManager fm, int totalTabs){
        super(fm);
        myContext=context;
        this.totalTabs=totalTabs;
    }
    //This is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                morningDua1 morningDua1=new morningDua1();
                return morningDua1;
            case 1:
                morningDua2 morningDua2=new morningDua2();
                return morningDua2;
            case 2:
                morningDua3 morningDua3=new morningDua3();
                return morningDua3;
            case 3:
                morningDua4 morningDua4=new morningDua4();
                return morningDua4;
            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return totalTabs;
    }

}
class TabLayoutAdapterJob  extends FragmentPagerAdapter {
    private Context myContext;
    int totalTabs;
    public TabLayoutAdapterJob(Context context, FragmentManager fm, int totalTabs){
        super(fm);
        myContext=context;
        this.totalTabs=totalTabs;
    }
    //This is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                jobDua1 jobDua1=new jobDua1();
                return jobDua1;
            case 1:
                jobDua2 jobDua2=new jobDua2();
                return jobDua2;
            case 2:
                jobDua3 jobDua3=new jobDua3();
                return jobDua3;
            case 3:
                jobDua4 jobDua4=new jobDua4();
                return jobDua4;
            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return totalTabs;
    }

}
class TabLayoutAdapterForgive  extends FragmentPagerAdapter {
    private Context myContext;
    int totalTabs;
    public TabLayoutAdapterForgive(Context context, FragmentManager fm, int totalTabs){
        super(fm);
        myContext=context;
        this.totalTabs=totalTabs;
    }
    //This is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                jobDua1 jobDua1=new jobDua1();
                return jobDua1;
            case 1:
                jobDua2 jobDua2=new jobDua2();
                return jobDua2;
            case 2:
                jobDua3 jobDua3=new jobDua3();
                return jobDua3;
            case 3:
                jobDua4 jobDua4=new jobDua4();
                return jobDua4;
            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return totalTabs;
    }

}
class TabLayoutAdapter extends FragmentPagerAdapter {
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
class TabLayoutAdapterHighlights extends FragmentPagerAdapter {
    private Context myContext;
    int totalTabs;
    public TabLayoutAdapterHighlights(Context context, FragmentManager fm, int totalTabs){
        super(fm);
        myContext=context;
        this.totalTabs=totalTabs;
    }
    //This is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                dailyStats dailyStats=new dailyStats();
                return dailyStats;
            case 1:
                weeklyStats weeklyStats =new weeklyStats();
                return weeklyStats;
            case 2:
                monStats monStats=new monStats();
                return monStats;
            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}





