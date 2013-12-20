package com.devmix.snapshot.fragments;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.devmix.snapshot.R;
import com.viewpagerindicator.IconPagerAdapter;

public class MenuAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
	
	private static final String[] CONTENT = new String[] { "Novidades", "Seguindo", "Configurações", "Perfil" };
    private static final int[] ICONS = new int[] {
            R.drawable.perm_group_profile,
            R.drawable.perm_group_profile,
            R.drawable.perm_group_profile,
            R.drawable.perm_group_profile
    };
    public MenuAdapter(FragmentManager fm) { 
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
    	return new FragmentHome_();
    	//return null; 
    	/*if(position == 0)return new FragmentHome_();
    	if(position == 1)return new FragmentProfile_();
    	return null;*/
    	
    }

    @SuppressLint("DefaultLocale")
	@Override
    public CharSequence getPageTitle(int position) {
        return CONTENT[position % CONTENT.length].toUpperCase();
    }

    @Override public int getIconResId(int index) {
      return ICONS[index];
    }

  @Override
    public int getCount() {
      return CONTENT.length;
    }
}
