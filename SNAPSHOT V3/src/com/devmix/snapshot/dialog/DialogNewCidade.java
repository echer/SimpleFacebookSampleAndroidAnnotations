package com.devmix.snapshot.dialog;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.devmix.snapshot.R;
import com.devmix.snapshot.adapter.EstadoSpinAdapter;
import com.devmix.snapshot.dao.CidadeManager;
import com.devmix.snapshot.dao.ConfiguracaoCidadeManager;
import com.devmix.snapshot.dao.EstadoManager;
import com.devmix.snapshot.model.Cidade;
import com.devmix.snapshot.model.ConfiguracaoCidade;
import com.devmix.snapshot.model.Configuracoes;
import com.devmix.snapshot.model.Estado;
import com.devmix.snapshot.model.Profile;
import com.devmix.snapshot.utils.Globals;
import com.devmix.snapshot.utils.InterfaceUtils;
import com.devmix.snapshot.ws.ConfiguracoesWS;
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
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.rest.RestService;
import com.kpbird.chipsedittextlibrary.ChipsAdapter;
import com.kpbird.chipsedittextlibrary.ChipsItem;
import com.kpbird.chipsedittextlibrary.ChipsMultiAutoCompleteTextview;
import com.orasystems.libs.utils.async.AsyncUtils;
import com.orasystems.libs.utils.async.GenericAsyncTask;

@EFragment
public class DialogNewCidade extends SherlockDialogFragment{

	@Bean
	public EstadoManager estadoManager;
	@Bean
	public CidadeManager cidadeManager;
	@Bean
	public ConfiguracaoCidadeManager configuracaoCidadeManager;
	
	private DialogDismissListener dialogDismissListener;
	
	@FragmentArg
	public Configuracoes configuracoes;
	@FragmentArg
	public Profile profile;
	
	/**
	 * ASYNCTASK
	 */
	@Bean(AsyncUtils.class)
	public GenericAsyncTask asyncTask;
	
	/**
	 * STRING RESOURCES
	 */
	@StringRes(R.string.acvty_snapshot_progress_title)
	public String progressTitle;
	@StringRes(R.string.dial_frag_cidade_new_carregando_estados)
	public String carregandoEstados;
	@StringRes(R.string.dial_frag_cidade_new_carregando_cidades)
	public String carregandoCidades;
	@StringRes(R.string.dial_frag_cidade_new_cidade_salva_sucess)
	public String cidadeSalvaSucess;
	@StringRes(R.string.dial_frag_cidade_new_cidade_salva_erro)
	public String cidadeSalvaFail;
	@StringRes(R.string.dial_frag_cidade_new_sem_cidade)
	public String semCidadesSalvar;
	@StringRes(R.string.dial_frag_cidade_new_salvando_cidades)
	public String salvandoCidades;
	
	/**
	 * INTERFACE COMPONENTS
	 */
	@ViewById
	public Spinner spEstado;
	@ViewById
	public ChipsMultiAutoCompleteTextview chipsCidade;
	
	@Bean
	public InterfaceUtils interfaceUtils;
	
	/**
	 * DRAWABLE RESOURCES
	 */
	@DrawableRes
	public Drawable icLauncher;
	
	private List<Estado> estados;
	private List<Cidade> cidades;
	
	private EstadoSpinAdapter estadoSpAdapter;
	
	/**
	 * RESTFULL SERVICES
	 */
	@RestService
	public ConfiguracoesWS configuracoesWS;
	
	//private CidadeSpinAdapter

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (getDialog() != null) {
			getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
			getDialog().getWindow().setBackgroundDrawableResource(
					android.R.color.transparent);
		}

		View root = inflater.inflate(R.layout.dialog_fragment_cidade_new,
				container, false);

		return root;
	}
	
	@AfterInject
	public void afterInject(){
		configuracoesWS.setRootUrl(Globals.rootUrlConfiguracoesWS);
	}
	
	@SuppressWarnings("unchecked")
	@AfterViews
	@Background
	public void afterViews(){
		
		asyncTask.showProgressBar(getActivity(), progressTitle, carregandoEstados, 100, icLauncher, false, true, false);
		try {
			estados = estadoManager.list();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			asyncTask.hideProgressBar();
		}
		
		if(estados != null){
			carregaSpEstados(estados);
		}
		
		
	}

	@UiThread
	public void carregaSpEstados(List<Estado> estados2) {
		estadoSpAdapter = new EstadoSpinAdapter(getActivity(), android.R.layout.simple_spinner_item, estados);
		spEstado.setAdapter(estadoSpAdapter);
		spEstado.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				carregaCidades(estadoSpAdapter.getItem(position));
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});	
	}
	
	@SuppressWarnings("unchecked")
	@Background
	public void carregaCidades(Estado item) {
		asyncTask.showProgressBar(getActivity(), progressTitle, carregandoCidades, 100, icLauncher, false, true, false);
		try{
			cidades = cidadeManager.listByEstado(item);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			asyncTask.hideProgressBar();
		}
		
		if(cidades != null){
			carregaSpCidades();
		}
	} 
	
	@Click
	@Background
	public void btnSave(){
		asyncTask.showProgressBar(getActivity(), progressTitle, salvandoCidades, 100, icLauncher, false, true, false);
		try{
			List<Cidade> cidadesSalvar = new ArrayList<Cidade>();
			String chips[] = chipsCidade.getText().toString().trim().split(",");
			for(int i = 0;i<chips.length;i++){
				for(Cidade c:cidades){
					if(chips[i].equals(c.getNomeCidade()))cidadesSalvar.add(c);
				}
			}
			if(cidadesSalvar.size() < 1){
				exibeToast(semCidadesSalvar);
				return;
			}
			boolean salvouTudo = true;
			for(Cidade c:cidadesSalvar){
				try {
					ConfiguracaoCidade cc = configuracoesWS.saveCidade(c, profile.getId());
					if(cc != null){
						cc.setCidade(c);
						cc.setConfiguracoes(configuracoes);
						configuracaoCidadeManager.save(cc);
					}else{
						salvouTudo = false;
					}
				} catch (Exception e) {
					e.printStackTrace();
					salvouTudo = false;
				}
			}
			if(salvouTudo){
				exibeToast(cidadeSalvaSucess);
			}else{
				exibeToast(cidadeSalvaFail);
			}
			dialogDismissListener.onDismissDialog();
		}catch(Exception e){
			e.printStackTrace();
			exibeToast(cidadeSalvaFail);
		}finally{
			asyncTask.hideProgressBar();
		}
	}
	
	@UiThread
	public void exibeToast(String toast){
		interfaceUtils.exibeToast(toast);
	}

	@UiThread
	public void carregaSpCidades() {
		ArrayList<ChipsItem> arrCountry = new ArrayList<ChipsItem>();
		for(Cidade c:cidades){
			arrCountry.add(new ChipsItem(c.getNomeCidade(), R.drawable.ic_checkmark_holo_light));
		}
		ChipsAdapter chipsAdapter = new ChipsAdapter(getActivity(), arrCountry);
		chipsCidade.setAdapter(chipsAdapter);
	}

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	@Override
	public void onStart() {
		super.onStart();

		// change dialog width
		if (getDialog() != null) {

			int fullWidth = getDialog().getWindow().getAttributes().width;

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
				Display display = getActivity().getWindowManager()
						.getDefaultDisplay();
				Point size = new Point();
				display.getSize(size);
				fullWidth = size.x;
			} else {
				Display display = getActivity().getWindowManager()
						.getDefaultDisplay();
				fullWidth = display.getWidth();
			}

			final int padding = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
							.getDisplayMetrics());

			int w = fullWidth - padding;
			int h = getDialog().getWindow().getAttributes().height;

			getDialog().getWindow().setLayout(w, h);
		}
	}

	public DialogDismissListener getDialogDismissListener() {
		return dialogDismissListener;
	}

	public void setDialogDismissListener(DialogDismissListener dialogDismissListener) {
		this.dialogDismissListener = dialogDismissListener;
	}
	

}
