package com.devmix.snapshot.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.devmix.snapshot.R;
import com.devmix.snapshot.fragments.FragmentFollow;
import com.devmix.snapshot.fragments.FragmentFollow_;
import com.devmix.snapshot.fragments.FragmentHome;
import com.devmix.snapshot.fragments.FragmentHome_;
import com.devmix.snapshot.fragments.FragmentProfile;
import com.devmix.snapshot.fragments.FragmentProfile_;
import com.devmix.snapshot.fragments.FragmentSettings;
import com.devmix.snapshot.fragments.FragmentSettings_;
import com.devmix.snapshot.model.ConfiguracaoCidade;
import com.devmix.snapshot.model.ConfiguracaoCidadeList;
import com.devmix.snapshot.model.Configuracoes;
import com.devmix.snapshot.model.Profile;
import com.viewpagerindicator.IconPagerAdapter;

public class MenuAdapter extends FragmentPagerAdapter implements
		IconPagerAdapter {

	private List<Fragment> mFragments = new ArrayList<Fragment>();

	private static final String[] CONTENT = new String[] { "Novidades",
			"Seguindo", "Configurações", "Perfil" };
	private static final int[] ICONS = new int[] {
			R.drawable.perm_group_profile, R.drawable.perm_group_profile,
			R.drawable.perm_group_profile, R.drawable.perm_group_profile };
	
	/**
	 * FRAGMENTS
	 */
	public FragmentProfile fragmentProfile;
	public FragmentHome fragmentHome;
	public FragmentSettings fragmentSettings;
	public FragmentFollow fragmentFollow;
	
	private Configuracoes configuracoes;
	private Profile profile;

	public MenuAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case 0:
			//MONTA FRAGMENT HOME
			if(fragmentHome == null)fragmentHome = montaFragmentHome();
			return fragmentHome;
		case 1:
			//MONTA FRAGMENT FOLLOW 
			if(fragmentFollow == null)fragmentFollow = montaFragmentFollow();
			return fragmentFollow;
		case 2:
			//MONTA FRAGMENT SETTINGS
			if(fragmentSettings == null)fragmentSettings = montaFragmentSettings();
			return fragmentSettings;
		case 3:
			//MONTA FRAGMENT PROFILE
			if(fragmentProfile == null)fragmentProfile = montaFragmentProfile();
			return fragmentProfile;
		default:
			break;
		}
		return getmFragments().get(position);
	}
	
	private FragmentFollow montaFragmentFollow() {
		List<ConfiguracaoCidade> listCC = new ArrayList<ConfiguracaoCidade>();
		if(getConfiguracoes() != null){
			for(ConfiguracaoCidade cc:getConfiguracoes().getCidades()){
				listCC.add(cc);
			}
		}
		ConfiguracaoCidadeList ccList = new ConfiguracaoCidadeList(listCC);
		fragmentFollow = FragmentFollow_.builder().cidades(ccList).profile(getProfile()).build();
		fragmentFollow.setHasOptionsMenu(true);
		return fragmentFollow;
	}

	private FragmentHome montaFragmentHome() {
		fragmentHome = FragmentHome_.builder().build();
		fragmentHome.setHasOptionsMenu(true);
		return fragmentHome;
	}

	private FragmentSettings montaFragmentSettings() {
		fragmentSettings = FragmentSettings_.builder().configuracoes(configuracoes).profile(getProfile()).build();
		fragmentSettings.setHasOptionsMenu(true);
		return fragmentSettings;
	}

	private FragmentProfile montaFragmentProfile() {
		fragmentProfile = FragmentProfile_.builder().perfil(getProfile()).build();
		fragmentProfile.setHasOptionsMenu(true);
		return fragmentProfile;
	}

	@SuppressLint("DefaultLocale")
	@Override
	public CharSequence getPageTitle(int position) {
		return CONTENT[position % CONTENT.length].toUpperCase();
	}

	@Override
	public int getIconResId(int index) {
		return ICONS[index];
	}

	@Override
	public int getCount() {
		return CONTENT.length;
	}

	public List<Fragment> getmFragments() {
		return mFragments;
	}
	
	public void setmFragments(List<Fragment> fragments){
		this.mFragments = fragments;
	}

	public Configuracoes getConfiguracoes() {
		return configuracoes;
	}

	public void setConfiguracoes(Configuracoes configuracoes) {
		this.configuracoes = configuracoes;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
}
