package com.minsoura.findit;

/**
 * Created by min on 2016-02-26.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;





public class ViewPagerAdapter3 extends FragmentStatePagerAdapter {
  the1 ha1;
    the2 ha2;
    the3 ha3;
    the4 ha4;

    public the1 getHa1() {
        return ha1;
    }

    public the2 getHa2() {
        return ha2;
    }

    public the3 getHa3() {
        return ha3;
    }

    public the4 getHa4() {
        return ha4;
    }

    public the5 getHa5() {
        return ha5;
    }

    public the6 getHa6() {
        return ha6;
    }

    public the7 getHa7() {
        return ha7;
    }

    public the8 getHa8() {
        return ha8;
    }

    public the9 getHa9() {
        return ha9;
    }

    public the10 getHa10() {
        return ha10;
    }

    public the11 getHa11() {
        return ha11;
    }

    public the12 getHa12() {
        return ha12;
    }

    public the13 getHa13() {
        return ha13;
    }

    public the14 getHa14() {
        return ha14;
    }

    public the15 getHa15() {
        return ha15;
    }

    public the16 getHa16() {
        return ha16;
    }

    public the17 getHa17() {
        return ha17;
    }

    public the18 getHa18() {
        return ha18;
    }

    the5 ha5;
    the6 ha6;
            the7 ha7;

    the8 ha8;
            the9 ha9;
    the10 ha10;
            the11 ha11;

    public the10dot5 getHa10dot5() {
        return ha10dot5;
    }

    the10dot5 ha10dot5;
    the12 ha12;
            the13 ha13;
    the14 ha14;
    the15 ha15;
            the16 ha16;
    the17 ha17;
                    the18 ha18;




    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter3(FragmentManager fm,int mNumbOfTabsumb) {
        super(fm);
        NumbOfTabs =mNumbOfTabsumb;




    }


    @Override
    public Fragment getItem(int position) {


        if(position == 0)
        {
            ha1 = new the1();
            return ha1;
        }
        else if(position ==1)
        {
            ha2 = new the2();
            return ha2;
        }  else if(position ==2)
        {
            ha3 = new the3();
            return ha3;
        }
        else if(position ==3)
        {
            ha4 = new the4();
            return ha4;
        }
        else if(position ==4)
        {
            ha5 = new the5();
            return ha5;
        }
        else if(position ==5)
        {
            ha6 = new the6();
            return ha6;
        }     else if(position ==6)
        {
            ha7 = new the7();
            return ha7;
        }
        else if(position ==7)
        {
            ha8 = new the8();
            return ha8;
        }
        else if(position ==8)
        {
            ha9 = new the9();
            return ha9;
        }
        else if(position ==9)
        {
            ha10 = new the10();
            return ha10;
        }

        else if(position ==10)
        {
            ha10dot5 = new the10dot5();
            return ha10dot5;
        }


        else if(position ==11)
        {
            ha11 = new the11();
            return ha11;
        }
        else if(position ==12)
        {
            ha12 = new the12();
            return ha12;
        }
        else if(position ==13)
        {
            ha13 = new the13();
            return ha13;
        }
        else if(position ==14)
        {
            ha14 = new the14();
            return ha14;
        }
        else if(position ==15)
        {
            ha15 = new the15();
            return ha15;
        }
        else if(position ==16)
        {
            ha16 = new the16();
            return ha16;
        }
        else if(position ==17)
        {
            ha17 = new the17();
            return ha17;
        }
        else if(position ==18)
        {
            ha18 = new the18();
            return ha18;
        }



        return null;

    }





    @Override
    public int getCount() {
        return NumbOfTabs;

    }






}
