package com.magic.akash.magicbox;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Belal on 2/3/2016.
 */
//Extending FragmentStatePagerAdapter
public class Pager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public Pager(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                Tab_pie_chart Tab_pie_chart = new Tab_pie_chart();
                return Tab_pie_chart;
            case 1:
                Tab1 tab1 = new Tab1();
                return tab1;
            case 2:
                Tab2 tab2 = new Tab2();
                return tab2;
            case 3:
                Tab3 tab3 = new Tab3();
                return tab3;
            case 4:
                Tab4 tab4 = new Tab4();
                return tab4;
            case 5:
                Tab6 tab6 = new Tab6();
                return tab6;

            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }



}