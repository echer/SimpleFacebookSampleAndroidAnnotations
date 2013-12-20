package com.devmix.snapshot.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devmix.snapshot.R;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.res.StringRes;

@EFragment
public class FragmentProfile extends Fragment{

	@ViewById
	public TextView fragProfileEdNome;
	@ViewById
	public TextView fragProfileEdIdade;
	@ViewById
	public TextView fragProfileEdSexo;
	@ViewById
	public TextView fragProfileEdEmail;
	@StringRes(R.string.frag_profile_info_not_found)
	public String infoNotFound;
	
	@AfterViews
	public void afterViews(){
			fragProfileEdNome.setHint(infoNotFound);
			
			fragProfileEdEmail.setHint(infoNotFound);
			
			fragProfileEdIdade.setHint(infoNotFound);
			
			fragProfileEdSexo.setHint(infoNotFound);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_profile,container, false);
	}
}
