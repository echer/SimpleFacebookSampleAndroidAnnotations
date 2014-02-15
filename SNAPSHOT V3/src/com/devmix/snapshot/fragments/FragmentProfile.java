package com.devmix.snapshot.fragments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.devmix.snapshot.R;
import com.devmix.snapshot.model.Profile;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

@EFragment
public class FragmentProfile extends SherlockFragment {

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
		
		if(getPerfil() == null){
			fragProfileEdNome.setHint(infoNotFound);

			fragProfileEdEmail.setHint(infoNotFound);

			fragProfileEdIdade.setHint(infoNotFound);

			fragProfileEdSexo.setHint(infoNotFound);
		}else{
			//verifica nome
			String nome = "";
			if(getPerfil().getName() != null){
				nome += getPerfil().getName();
			}else{
				if(getPerfil().getFirstName() != null)nome += getPerfil().getFirstName();
				if(getPerfil().getMiddleName() != null)nome += " " + getPerfil().getMiddleName();
				if(getPerfil().getLastName() != null)nome += " " + getPerfil().getLastName();
			}
			fragProfileEdNome.setText(nome != null && nome.length() > 0 ? nome : infoNotFound);
			
			//verifica email
			fragProfileEdEmail.setText(getPerfil().getEmail() != null && getPerfil().getEmail().length() > 0 ? getPerfil().getEmail() : infoNotFound);
			
			//verifica data nascimento
			if(getPerfil().getBirthday() != null && getPerfil().getBirthday().length() > 0){
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				try {
					Date nascimento = format.parse(getPerfil().getBirthday());
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
			if(getPerfil().getGender() != null && getPerfil().getGender().length() > 0){
				if("male".equals(getPerfil().getGender()))fragProfileEdSexo.setText("Homem");
				if("female".equals(getPerfil().getGender()))fragProfileEdSexo.setText("Mulher");
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

	public Profile getPerfil() {
		return perfil;
	}

	public void setPerfil(Profile perfil) {
		this.perfil = perfil;
	}
}
