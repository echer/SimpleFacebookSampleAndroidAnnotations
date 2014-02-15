package com.devmix.snapshot.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.devmix.snapshot.R;
import com.devmix.snapshot.adapter.ConfigCidadesBAdapter;
import com.devmix.snapshot.dao.CidadeManager;
import com.devmix.snapshot.dao.ConfiguracoesManager;
import com.devmix.snapshot.dao.EstadoManager;
import com.devmix.snapshot.dialog.DialogDismissListener;
import com.devmix.snapshot.dialog.DialogNewCidade;
import com.devmix.snapshot.dialog.DialogNewCidade_;
import com.devmix.snapshot.model.Cidade;
import com.devmix.snapshot.model.CidadeList;
import com.devmix.snapshot.model.ConfiguracaoCidade;
import com.devmix.snapshot.model.Configuracoes;
import com.devmix.snapshot.model.Estado;
import com.devmix.snapshot.model.EstadoList;
import com.devmix.snapshot.model.Profile;
import com.devmix.snapshot.utils.Globals;
import com.devmix.snapshot.utils.InterfaceUtils;
import com.devmix.snapshot.ws.CidadeWS;
import com.devmix.snapshot.ws.EstadoWS;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DrawableRes;
import org.androidannotations.annotations.rest.RestService;
import com.orasystems.libs.utils.async.AsyncUtils;
import com.orasystems.libs.utils.async.GenericAsyncTask;

import eu.inmite.android.lib.dialogs.SimpleDialogFragment;

@EFragment
public class FragmentSettings extends SherlockFragment{

	/**
	 * INTERFACE COMPONENTS
	 */
	@ViewById
	public ListView listaCidades;
	@ViewById
	public TextView txtInfoCidades;
	
	private ConfiguracaoCidade cidadeExcluir;
	
	
	/**
	 * FRAGMENT PARAMETERS
	 */
	@FragmentArg
	public Configuracoes configuracoes;
	@FragmentArg
	public Profile profile;
	
	private ConfigCidadesBAdapter configCidadesBAdapter;
	
	/**
	 * INTERFACE UTILS
	 */
	@Bean
	public InterfaceUtils interfaceUtils;
	
	
	/**
	 * REST SERVICE
	 */
	@RestService
	public EstadoWS estadoWS;
	@RestService
	public CidadeWS cidadeWS;
	
	/**
	 * DAO
	 */
	@Bean
	public EstadoManager estadoManager;
	@Bean
	public CidadeManager cidadeManager;
	@Bean
	public ConfiguracoesManager configuracoesManager;
	
	/**
	 * DRAWABLE RESOURCES
	 */
	@DrawableRes
	public Drawable icLauncher;
	
	/**
	 * ASYNCTASK
	 */
	@Bean(AsyncUtils.class)
	public GenericAsyncTask asyncTask;
	
	@AfterInject
	public void afterInject(){
		estadoWS.setRootUrl(Globals.rootUrlEstadoWS);
		cidadeWS.setRootUrl(Globals.rootUrlCidadeWS);
	}

	@AfterViews
	@UiThread
	public void afterViews(){
		
		if(configuracoes == null)return;

		try {
			if(estadoManager.count() < 1 || cidadeManager.count() < 1){
				txtAtualizarCidades();
			}
		} catch (Exception ignored) {}
		
		if(configuracoes.getCidades() != null && configuracoes.getCidades().size() > 0){
			configCidadesBAdapter = new ConfigCidadesBAdapter(getActivity(), configuracoes.getCidades(),this);
			listaCidades.setAdapter(configCidadesBAdapter);
			configCidadesBAdapter.notifyDataSetChanged();
		}else{
			listaCidades.setAdapter(null);
		}
	}
	
	@Click 
	@Background
	public void txtAtualizarCidades(){
		asyncTask.showProgressBar(getActivity(), R.string.acvty_snapshot_progress_title, R.string.frag_settings_atualizando_cidades_estados, 100, icLauncher, false, true, false);
		try{
			EstadoList estadoList = estadoWS.list();
			for(Estado e:estadoList.getEstados()){
				estadoManager.saveIfNotExist(e);
			}
			CidadeList cidadeList = cidadeWS.list();
			for(Cidade c:cidadeList.getCidades()){
				cidadeManager.saveIfNotExist(c);
			}
			interfaceUtils.exibeMsg(R.string.frag_settings_cidades_estados_sucess);
		}catch(Exception e){
			e.printStackTrace();
			interfaceUtils.exibeMsg(R.string.frag_settings_cidades_estados_fail);
		}finally{
			asyncTask.hideProgressBar();
		}
		
	} 
	
	@Click 
	public void txtNovaCidade(){ 
		final DialogNewCidade dialog = DialogNewCidade_.builder().configuracoes(configuracoes).profile(profile).build();
		dialog.show(getActivity().getSupportFragmentManager(), "DialogNewCidade");
		dialog.setDialogDismissListener(new DialogDismissListener() {
			@Override
			public void onDismissDialog() {
				try {
					configuracoes = configuracoesManager.loadFirst();
				} catch (Exception e) {
					e.printStackTrace();
				}
				afterViews();
				dialog.dismiss();
			}
		});
	}
	
	public Configuracoes getConfiguracoes() {
		return configuracoes;
	}

	public void setConfiguracoes(Configuracoes configuracoes) {
		this.configuracoes = configuracoes;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_settings, container, false);
	}

	public void exibeDialogoExclusao(ConfiguracaoCidade cc) {
		setCidadeExcluir(cc);
		SimpleDialogFragment.createBuilder(getSherlockActivity(), getSherlockActivity().getSupportFragmentManager())
		.setTitle(R.string.acvty_snapshot_dialog_title)
		.setMessage(R.string.frag_settings_cidade_delete)
		.setCancelable(false)
		.setPositiveButtonText(R.string.positive_response)
		.setNegativeButtonText(R.string.negative_response)
		.setRequestCode(3)
		.show();
	}

	public ConfiguracaoCidade getCidadeExcluir() {
		return cidadeExcluir;
	}

	public void setCidadeExcluir(ConfiguracaoCidade cidadeExcluir) {
		this.cidadeExcluir = cidadeExcluir;
	}

	@UiThread
	public void reloadCidades() {
		try {
			configuracoes = configuracoesManager.loadFirst();
			afterViews();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
