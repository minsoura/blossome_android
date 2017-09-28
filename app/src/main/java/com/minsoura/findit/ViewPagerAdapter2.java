package com.minsoura.findit;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter2 extends FragmentStatePagerAdapter {
    public Fragment getThisOne() {
        return thisOne;
    }

    public Fragment getThisTwo() {
        return thisTwo;
    }

    frag1 thisOne;
    frag2 thisTwo;
    frag3 thisThree;

    public frag3 getThisThree() {
        return thisThree;
    }

    public frag4 getThisFour() {
        return thisFour;
    }

    public frag5 getThisFive() {
        return thisFive;
    }

    public frag6 getThisSix() {
        return thisSix;
    }

    frag4 thisFour;
    frag5 thisFive;
    frag6 thisSix;



    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter2(FragmentManager fm,int mNumbOfTabsumb) {
        super(fm);
        NumbOfTabs =mNumbOfTabsumb;




    }


    @Override
    public Fragment getItem(int position) {


        if(position == 0)
        {
            thisOne = new frag1();
            return thisOne;
        }
        else if(position ==1)
        {
            thisTwo = new frag2();
            return thisTwo;
        }  else if(position ==2)
        {
            thisThree= new frag3();
            return thisThree;
        }
        else if(position ==3)
        {
            thisFour = new frag4();
            return thisFour;
        }
        else if(position ==4)
        {
            thisFive = new frag5();
            return thisFive;
        }
        else if(position ==5)
        {
            thisSix = new frag6();
            return thisSix;
        }

        return null;

    }





    @Override
    public int getCount() {
        return NumbOfTabs;

    }






}
