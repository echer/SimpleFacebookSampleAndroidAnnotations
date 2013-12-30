package com.devmix.snapshot.fragments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.devmix.snapshot.R;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.FragmentArg;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.res.StringRes;
import com.sromku.simple.fb.entities.Profile;

@EFragment
public class FragmentProfile extends Fragment {

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
	@FragmentArg
	public Profile perfil;

	@SuppressLint("SimpleDateFormat")
	@SuppressWarnings("deprecation")
	@AfterViews
	public void afterViews() {
		
		if(perfil == null){
			fragProfileEdNome.setHint(infoNotFound);

			fragProfileEdEmail.setHint(infoNotFound);

			fragProfileEdIdade.setHint(infoNotFound);

			fragProfileEdSexo.setHint(infoNotFound);
		}else{
			//verifica nome
			String nome = "";
			if(perfil.getName() != null){
				nome += perfil.getName();
			}else{
				if(perfil.getFirstName() != null)nome += perfil.getFirstName();
				if(perfil.getMiddleName() != null)nome += " " + perfil.getMiddleName();
				if(perfil.getLastName() != null)nome += " " + perfil.getLastName();
			}
			fragProfileEdNome.setText(nome != null && nome.length() > 0 ? nome : infoNotFound);
			
			//verifica email
			fragProfileEdEmail.setText(perfil.getEmail() != null && perfil.getEmail().length() > 0 ? perfil.getEmail() : infoNotFound);
			
			//verifica data nascimento
			if(perfil.getBirthday() != null && perfil.getBirthday().length() > 0){
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				try {
					Date nascimento = format.parse(perfil.getBirthday());
					Date atual = new Date(System.currentTimeMillis());
					int idade = 0;
					if(atual.getDay() >= nascimento.getDay() && atual.getMonth() >= nascimento.getMonth()){
						idade = atual.getYear() - nascimento.getYear();
					}else{
						idade = (atual.getYear() - nascimento.getYear()) - 1;
					}
					if(idade > 0 ){
						fragProfileEdIdade.setText(String.format("%s anos", String.valueOf(idade)));
					}else{
						fragProfileEdIdade.setText(infoNotFound);
					}
				} catch (ParseException e) {
					e.printStackTrace();
					fragProfileEdIdade.setText(infoNotFound);
				}
			}else{
				fragProfileEdIdade.setText(infoNotFound);
			}
			
			//verifica sexo
			if(perfil.getGender() != null && perfil.getGender().length() > 0){
				if("male".equals(perfil.getGender()))fragProfileEdSexo.setText("Homem");
				if("female".equals(perfil.getGender()))fragProfileEdSexo.setText("Mulher");
			}else{
				fragProfileEdSexo.setText(infoNotFound);
			}
		}
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_profile, container, false);
	}
}
