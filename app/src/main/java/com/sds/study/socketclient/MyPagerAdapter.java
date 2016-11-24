package com.sds.study.socketclient;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * 뷰페이저가 껍에 불과하므로 어댑터에서 페이지 정보를 제공해줘야한다!!
 */

public class MyPagerAdapter extends FragmentStatePagerAdapter {
    Fragment[] fragments=new Fragment[2]; // 프래그먼트 배열 선언!! 페이지!! 고정적이기 때문에 배열로!!


    public MyPagerAdapter(FragmentManager fm) {
        super(fm);

        fragments[0]=new ChatFragment();
        fragments[1]=new ConfigFragment();
    }

    public int getCount() {
        return fragments.length;
    }

    public Fragment getItem(int position) {

        /*길이 만큼 호출!!*/
        return fragments[position];
    }
}
