package com.devmix.snapshot.adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.devmix.snapshot.R;
import com.devmix.snapshot.fragments.FragmentSettings;
import com.devmix.snapshot.model.ConfiguracaoCidade;

public class ConfigCidadesBAdapter extends BaseAdapter{

	private List<ConfiguracaoCidade> cidades;
	private Activity activity;
	private FragmentSettings instance;
	public ConfigCidadesBAdapter(Activity activity, Collection<ConfiguracaoCidade> collection,FragmentSettings instance){
		List<ConfiguracaoCidade> c = new ArrayList<ConfiguracaoCidade>();
		Iterator<ConfiguracaoCidade> iterator = collection.iterator();
		while(iterator.hasNext()){
			c.add(iterator.next());
		}
		this.instance = instance;
		this.cidades = c;
		this.activity = activity;
	}
	@Override
	public int getCount() {
		return cidades.size();
	}

	@Override
	public Object getItem(int arg0) {
		return cidades.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return ((ConfiguracaoCidade)getItem(arg0)).getId();
	}

	@Override
	public View getView(int position, View v, ViewGroup arg2) {
		
		View view = null;
		
		view = activity.getLayoutInflater().inflate(R.layout.config_cidade_item, null);
		final ConfiguracaoCidade cc = (ConfiguracaoCidade) getItem(position);
		
		TextView txt = (TextView) view.findViewById(R.id.txtConfigCidade);
		txt.setText(cc.getCidade().getEstado().getNomeEstado()+"/"+cc.getCidade().getEstado().getUfEstado()+" - "+cc.getCidade().getNomeCidade());
		txt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				instance.exibeDialogoExclusao(cc);
			}
		});
		return view;
	}

}
